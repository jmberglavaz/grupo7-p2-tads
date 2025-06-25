package um.edu.uy.Sistema;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import um.edu.uy.Sistema.Consultas.TopActorPorMes;
import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.Hash.MyHashImplCloseLineal;
import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Pelicula;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TopActorPorMesTest {

    private MyHash<Integer, Pelicula> listaPeliculas;
    private MyHash<String, Actor> listaActores;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        // Configurar captura de salida
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Inicializar estructuras de datos de prueba
        listaPeliculas = new MyHashImplCloseLineal<>(100);
        listaActores = new MyHashImplCloseLineal<>(100);

        // Crear películas de prueba
        try {
            Pelicula peli1 = new Pelicula(1, "Pelicula Enero", null, 2023);
            Pelicula peli2 = new Pelicula(2, "Pelicula Febrero", null, 2023);
            Pelicula peli3 = new Pelicula(3, "Pelicula Marzo", null, 2023);

            listaPeliculas.insert(1, peli1);
            listaPeliculas.insert(2, peli2);
            listaPeliculas.insert(3, peli3);
        } catch (Exception e) {
            fail("Error configurando películas de prueba: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test con actores diferentes dominando cada mes")
    void testActoresDiferentesPorMes() {
        try {
            // Crear actores mock con diferentes evaluaciones por mes
            ActorMock actor1 = new ActorMock("Leonardo DiCaprio");
            actor1.setEvaluacionesPorMes(1, 100); // Domina Enero
            actor1.setEvaluacionesPorMes(2, 10);
            actor1.setEvaluacionesPorMes(3, 5);

            ActorMock actor2 = new ActorMock("Tom Hanks");
            actor2.setEvaluacionesPorMes(1, 20);
            actor2.setEvaluacionesPorMes(2, 150); // Domina Febrero
            actor2.setEvaluacionesPorMes(3, 15);

            ActorMock actor3 = new ActorMock("Meryl Streep");
            actor3.setEvaluacionesPorMes(1, 30);
            actor3.setEvaluacionesPorMes(2, 25);
            actor3.setEvaluacionesPorMes(3, 200); // Domina Marzo

            listaActores.insert("Leonardo DiCaprio", actor1);
            listaActores.insert("Tom Hanks", actor2);
            listaActores.insert("Meryl Streep", actor3);

            // Ejecutar consulta
            TopActorPorMes.realizarConsulta(listaPeliculas, listaActores);

            String output = outputStream.toString();

            // Verificar que cada actor aparece en el mes correcto
            assertTrue(output.contains("Enero: Leonardo DiCaprio"),
                    "Leonardo DiCaprio debería dominar Enero");
            assertTrue(output.contains("Feb: Tom Hanks"),
                    "Tom Hanks debería dominar Febrero");
            assertTrue(output.contains("Marzo: Meryl Streep"),
                    "Meryl Streep debería dominar Marzo");

        } catch (Exception e) {
            fail("Error en test de actores diferentes: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test con un actor dominando todos los meses")
    void testUnActorDominaTodos() {
        try {
            ActorMock actorDominante = new ActorMock("Actor Dominante");
            ActorMock actorNormal = new ActorMock("Actor Normal");

            // Actor dominante tiene más evaluaciones en todos los meses
            for (int mes = 1; mes <= 12; mes++) {
                actorDominante.setEvaluacionesPorMes(mes, 1000);
                actorNormal.setEvaluacionesPorMes(mes, 10);
            }

            listaActores.insert("Actor Dominante", actorDominante);
            listaActores.insert("Actor Normal", actorNormal);

            TopActorPorMes.realizarConsulta(listaPeliculas, listaActores);

            String output = outputStream.toString();

            // Verificar que el actor dominante aparece en todos los meses
            String[] meses = {"Enero", "Feb", "Marzo", "Abril", "Mayo", "Junio",
                    "Julio", "Agosto", "Setiembre", "Octubre", "Noviembre", "Diciembre"};

            for (String mes : meses) {
                assertTrue(output.contains(mes + ": Actor Dominante"),
                        "Actor Dominante debería aparecer en " + mes);
            }

        } catch (Exception e) {
            fail("Error en test de actor dominante: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test con actores sin evaluaciones")
    void testActoresSinEvaluaciones() {
        try {
            ActorMock actorSinEval = new ActorMock("Actor Sin Evaluaciones");
            ActorMock actorConEval = new ActorMock("Actor Con Evaluaciones");

            // Un actor sin evaluaciones (todos 0)
            for (int mes = 1; mes <= 12; mes++) {
                actorSinEval.setEvaluacionesPorMes(mes, 0);
            }

            // Otro actor con pocas evaluaciones solo en algunos meses
            actorConEval.setEvaluacionesPorMes(1, 5);
            actorConEval.setEvaluacionesPorMes(6, 3);
            for (int mes = 2; mes <= 5; mes++) {
                actorConEval.setEvaluacionesPorMes(mes, 0);
            }
            for (int mes = 7; mes <= 12; mes++) {
                actorConEval.setEvaluacionesPorMes(mes, 0);
            }

            listaActores.insert("Actor Sin Evaluaciones", actorSinEval);
            listaActores.insert("Actor Con Evaluaciones", actorConEval);

            TopActorPorMes.realizarConsulta(listaPeliculas, listaActores);

            String output = outputStream.toString();

            // En enero y junio debería aparecer el actor con evaluaciones
            assertTrue(output.contains("Enero: Actor Con Evaluaciones"));
            assertTrue(output.contains("Junio: Actor Con Evaluaciones"));

        } catch (Exception e) {
            fail("Error en test de actores sin evaluaciones: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test con hash vacío")
    void testHashVacio() {
        // Hash de actores vacío
        MyHash<String, Actor> hashVacio = new MyHashImplCloseLineal<>(10);

        TopActorPorMes.realizarConsulta(listaPeliculas, hashVacio);

        String output = outputStream.toString();

        // Debería mostrar "No hay actores" o heap vacío para todos los meses
        // (dependiendo de cómo maneja tu implementación los heaps vacíos)
        assertFalse(output.isEmpty(), "Debería producir alguna salida");
    }

    @Test
    @DisplayName("Test de consistencia - ejecutar dos veces debería dar mismo resultado")
    void testConsistencia() {
        try {
            // Configurar datos de prueba
            ActorMock actor1 = new ActorMock("Actor Consistente");
            actor1.setEvaluacionesPorMes(1, 50);
            actor1.setEvaluacionesPorMes(2, 75);

            listaActores.insert("Actor Consistente", actor1);

            // Primera ejecución
            TopActorPorMes.realizarConsulta(listaPeliculas, listaActores);
            String primerResultado = outputStream.toString();

            // Limpiar output y ejecutar segunda vez
            outputStream.reset();
            TopActorPorMes.realizarConsulta(listaPeliculas, listaActores);
            String segundoResultado = outputStream.toString();

            assertEquals(primerResultado, segundoResultado,
                    "Los resultados deberían ser consistentes entre ejecuciones");

        } catch (Exception e) {
            fail("Error en test de consistencia: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test con muchos actores - verificar rendimiento")
    void testRendimiento() {
        try {
            long inicio = System.currentTimeMillis();

            // Crear muchos actores (1000)
            for (int i = 0; i < 1000; i++) {
                ActorMock actor = new ActorMock("Actor" + i);
                // Dar valores aleatorios pero predecibles
                for (int mes = 1; mes <= 12; mes++) {
                    actor.setEvaluacionesPorMes(mes, (i * mes) % 100);
                }
                listaActores.insert("Actor" + i, actor);
            }

            TopActorPorMes.realizarConsulta(listaPeliculas, listaActores);

            long tiempoTotal = System.currentTimeMillis() - inicio;

            // Verificar que no toma más de 5 segundos (ajustar según necesidad)
            assertTrue(tiempoTotal < 5000,
                    "La consulta con 1000 actores no debería tomar más de 5 segundos. Tomó: " + tiempoTotal + "ms");

        } catch (Exception e) {
            fail("Error en test de rendimiento: " + e.getMessage());
        }
    }

    // Clase mock para simular Actor con evaluaciones controladas
    private static class ActorMock extends Actor {
        private int[] evaluacionesPorMes = new int[13]; // índice 0 no se usa, 1-12 para meses

        public ActorMock(String nombre) {
            super(nombre);
        }

        public void setEvaluacionesPorMes(int mes, int cantidad) {
            if (mes >= 1 && mes <= 12) {
                evaluacionesPorMes[mes] = cantidad;
            }
        }

        @Override
        public int getCantidadEvaluacionesActorPorMes(int mes) {
            if (mes >= 1 && mes <= 12) {
                return evaluacionesPorMes[mes];
            }
            return 0;
        }

        @Override
        public int getCantidadPeliculasActor() {
            return 10; // Valor fijo para tests
        }
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        // Restaurar salida original
        System.setOut(originalOut);
    }
}

// Clase adicional para tests de integración con datos reales
class TopActorPorMesIntegrationTest {
    @Test
    @DisplayName("Test de integración - verificar que los heaps mantienen orden correcto")
    void testOrdenHeaps() {
        // Este test requiere acceso a los heaps internos o métodos adicionales
        // para verificar que el orden es correcto

        MyHash<Integer, Pelicula> peliculas = new MyHashImplCloseLineal<>(10);
        MyHash<String, Actor> actores = new MyHashImplCloseLineal<>(10);

        try {
            // Configurar datos donde sabemos el orden esperado
            peliculas.insert(1, new Pelicula(1, "Test", null, 2023));

            Actor actor1 = new Actor("Primer Actor");
            Actor actor2 = new Actor("Segundo Actor");
            Actor actor3 = new Actor("Tercer Actor");

            // Enero: actor3 > actor2 > actor1
            //actor1.setEvaluacionesPorMes(1, 10);
            //actor2.setEvaluacionesPorMes(1, 20);
            //actor3.setEvaluacionesPorMes(1, 30);

            actores.insert("Primer Actor", actor1);
            actores.insert("Segundo Actor", actor2);
            actores.insert("Tercer Actor", actor3);

            // Capturar salida
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(output));

            TopActorPorMes.realizarConsulta(peliculas, actores);

            String resultado = output.toString();
            System.setOut(originalOut);

            // El actor con más evaluaciones (30) debería aparecer en enero
            assertTrue(resultado.contains("Enero: Tercer Actor"),
                    "El actor con más evaluaciones debería ganar enero");

        } catch (Exception e) {
            fail("Error en test de integración: " + e.getMessage());
        }
    }
}