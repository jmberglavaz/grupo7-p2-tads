package um.edu.uy.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import um.edu.uy.adt.list.MyList;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PeliculaTest {

    private Pelicula pelicula;

    @BeforeEach
    void setUp() {
        pelicula = new Pelicula(1, "Pelicula de Prueba", "2025-01-01", 100000L);
    }

    @Test
    void testGetCantidadEvaluaciones() {

        Evaluacion evaluacion1 = new Evaluacion(101, 5.0f, new Date(2025, 0, 10)); // Mes 0 = Enero
        Evaluacion evaluacion2 = new Evaluacion(102, 4.0f, new Date(2025, 0, 15)); // Mes 0 = Enero
        Evaluacion evaluacion3 = new Evaluacion(103, 3.0f, new Date(2025, 1, 20)); // Mes 1 = Febrero

        pelicula.addReview(evaluacion1);
        pelicula.addReview(evaluacion2);
        pelicula.addReview(evaluacion3);

        int cantidad = pelicula.getCantidadEvaluaciones();

        assertEquals(3, cantidad);
    }

    @Test
    void testGetCantidadEvaluaciones_NoEvaluations() {
        int cantidad = pelicula.getCantidadEvaluaciones();

        assertEquals(0, cantidad);
    }

    @Test
    void testGetPromedioDeEvaluaciones() {
        Evaluacion evaluacion1 = new Evaluacion(101, 3.0f, new Date());
        Evaluacion evaluacion2 = new Evaluacion(102, 4.0f, new Date());
        Evaluacion evaluacion3 = new Evaluacion(103, 5.0f, new Date());

        pelicula.addReview(evaluacion1);
        pelicula.addReview(evaluacion2);
        pelicula.addReview(evaluacion3);

        // Promedio = (3.0 + 4.0 + 5.0) / 3 = 4.0
        float promedio = pelicula.getAverageRating();

        assertEquals(4.0, promedio);
    }

    @Test
    void testGetPromedioDeEvaluacionesSinEvaluaciones() {
        float promedio = pelicula.getAverageRating();
        assertEquals(0,promedio);
    }

    @Test
    void testGetListaEvaluacionesEnMes() {
        Evaluacion evalEnero1 = new Evaluacion(1, 5f, new Date(2025, 0, 10)); // Enero (mes 0)
        Evaluacion evalEnero2 = new Evaluacion(2, 4f, new Date(2025, 0, 20)); // Enero (mes 0)
        Evaluacion evalMarzo = new Evaluacion(3, 3f, new Date(2025, 2, 5));   // Marzo (mes 2)

        pelicula.addReview(evalEnero1);
        pelicula.addReview(evalEnero2);
        pelicula.addReview(evalMarzo);

        MyList<Evaluacion> evalsEnero = pelicula.getListaEvaluacionesEnMes(1);
        MyList<Evaluacion> evalsFebrero = pelicula.getListaEvaluacionesEnMes(2);
        MyList<Evaluacion> evalsMarzo = pelicula.getListaEvaluacionesEnMes(3);

        assertEquals(2, evalsEnero.size());
        assertEquals(0, evalsFebrero.size());
        assertEquals(1, evalsMarzo.size());
    }
}