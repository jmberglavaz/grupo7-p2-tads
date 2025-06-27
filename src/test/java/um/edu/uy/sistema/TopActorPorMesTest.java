package um.edu.uy.sistema;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import um.edu.uy.sistema.consultas.TopActorPorMes;
import um.edu.uy.adt.hash.MyHash;
import um.edu.uy.adt.hash.MyHashImplCloseLineal;
import um.edu.uy.entidades.Actor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase TopActorPorMes.
 * Se verifica la correcta identificación del actor con más calificaciones por mes.
 */
public class TopActorPorMesTest {

    // El hash de actores ahora usa Integer como clave (ID del actor)
    private MyHash<Integer, Actor> listaActores;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        // Redirige la salida de la consola para poder capturarla y verificarla
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Inicializa el hash de actores para cada test
        listaActores = new MyHashImplCloseLineal<>(100);
    }

    @AfterEach
    void tearDown() {
        // Restaura la salida original de la consola después de cada test
        System.setOut(originalOut);
    }

    /**
     * Clase interna para simular un Actor con datos controlados para las pruebas.
     * Esta es la forma correcta de hacer un "mock" sin usar librerías externas.
     */
    private static class ActorMock extends Actor {
        // La estructura interna simula la respuesta del método getStatsForMonth
        // statsPorMes[mes][0] = cantidad de calificaciones
        // statsPorMes[mes][1] = cantidad de películas
        private final int[][] statsPorMes = new int[13][2];

        public ActorMock(int id, String nombre) {
            super(id, nombre);
        }

        /**
         * Permite configurar las estadísticas para un mes específico en el mock.
         * @param mes El mes (1-12).
         * @param cantidadCalificaciones El total de calificaciones para ese mes.
         * @param cantidadPeliculas La cantidad de películas con calificaciones en ese mes.
         */
        public void setEstadisticas(int mes, int cantidadCalificaciones, int cantidadPeliculas) {
            if (mes >= 1 && mes <= 12) {
                statsPorMes[mes][0] = cantidadCalificaciones;
                statsPorMes[mes][1] = cantidadPeliculas;
            }
        }

        /**
         * Sobrescribe el método real para devolver los datos que configuramos en el test.
         */
        @Override
        public int[] getStatsForMonth(int mes) {
            if (mes >= 1 && mes <= 12) {
                return statsPorMes[mes];
            }
            return new int[]{0, 0};
        }
    }


    @Test
    @DisplayName("Test con actores diferentes dominando cada mes")
    void testActoresDiferentesPorMes() throws Exception {
        // Crear actores mock con diferentes evaluaciones por mes
        ActorMock actor1 = new ActorMock(1, "Leonardo DiCaprio");
        actor1.setEstadisticas(1, 100, 2); // Domina Enero
        actor1.setEstadisticas(2, 10, 1);

        ActorMock actor2 = new ActorMock(2, "Tom Hanks");
        actor2.setEstadisticas(1, 20, 1);
        actor2.setEstadisticas(2, 150, 3); // Domina Febrero

        listaActores.insert(1, actor1);
        listaActores.insert(2, actor2);

        // Ejecutar la consulta (ahora solo necesita el hash de actores)
        TopActorPorMes.realizarConsulta(listaActores);

        String output = outputStream.toString();

        // Verificar la salida con el formato del PDF: <mes>, <nombre_actor>, <cantidad_peliculas>, <cantidad de calificaciones>
        assertTrue(output.contains("Enero, Leonardo DiCaprio, 2, 100"), "Leonardo DiCaprio deberia dominar Enero.");
        assertTrue(output.contains("Febrero, Tom Hanks, 3, 150"), "Tom Hanks deberia dominar Febrero.");
    }

    @Test
    @DisplayName("Test con un actor dominando todos los meses")
    void testUnActorDominaTodos() throws Exception {
        ActorMock actorDominante = new ActorMock(1, "Meryl Streep");
        ActorMock actorNormal = new ActorMock(2, "Actor Secundario");

        // El actor dominante tiene más evaluaciones en todos los meses
        for (int mes = 1; mes <= 12; mes++) {
            actorDominante.setEstadisticas(mes, 1000, 5);
            actorNormal.setEstadisticas(mes, 10, 1);
        }

        listaActores.insert(1, actorDominante);
        listaActores.insert(2, actorNormal);

        TopActorPorMes.realizarConsulta(listaActores);

        String output = outputStream.toString();

        // Verificar que el actor dominante aparece en la salida de todos los meses
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Setiembre", "Octubre", "Noviembre", "Diciembre"};

        for (String mes : meses) {
            assertTrue(output.contains(mes + ", Meryl Streep, 5, 1000"), "Meryl Streep deberia dominar " + mes);
        }
    }

    @Test
    @DisplayName("Test con meses sin evaluaciones")
    void testMesesSinEvaluaciones() throws Exception {
        ActorMock actorConEvaluaciones = new ActorMock(1, "Brad Pitt");

        // El actor solo tiene evaluaciones en Marzo y Diciembre
        actorConEvaluaciones.setEstadisticas(3, 50, 2);
        actorConEvaluaciones.setEstadisticas(12, 100, 4);

        listaActores.insert(1, actorConEvaluaciones);

        TopActorPorMes.realizarConsulta(listaActores);

        String output = outputStream.toString();

        // Verificar que el actor gana en los meses que tiene evaluaciones
        assertTrue(output.contains("Marzo, Brad Pitt, 2, 50"));
        assertTrue(output.contains("Diciembre, Brad Pitt, 4, 100"));

        // Verificar que en otros meses no aparece (porque no hay datos y el mock devuelve {0,0})
        assertFalse(output.contains("Enero, Brad Pitt"));
        assertFalse(output.contains("Febrero, Brad Pitt"));
    }

    @Test
    @DisplayName("Test con hash de actores vacio")
    void testHashVacio() {
        // Se llama a la consulta con el hash de actores vacío
        TopActorPorMes.realizarConsulta(listaActores);

        String output = outputStream.toString().trim();

        // La salida debe contener solo la línea del tiempo de ejecución,
        // ya que no hay actores que procesar.
        assertTrue(output.startsWith("Tiempo de ejecucion de la consulta:"), "La salida deberia estar casi vacia, solo con el tiempo de ejecucion.");
    }
}