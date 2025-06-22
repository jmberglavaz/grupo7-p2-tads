package um.edu.uy.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import um.edu.uy.Exceptions.ListOutOfIndex;
import um.edu.uy.entities.Director;
import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Pelicula;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class DirectorTest {

    private Director director;
    private Pelicula pelicula1;
    private Pelicula pelicula2;

    @BeforeEach
    void setUp() {
        director = new Director("Director de Prueba");
        pelicula1 = new Pelicula(101, "Peli 1", "2025-01-01", 0);
        pelicula2 = new Pelicula(102, "Peli 2", "2025-01-01", 0);
    }
     // Metodo auxiliar donde se crean evaluaciones las cuales no nos interesa ni la fecha ni el id, solo la calificación
    private Evaluacion createRealEvaluation(float calificacion) {
        return new Evaluacion(1, calificacion, new Date());
    }

    @Test
    void testObtainMedianaCantidadImpar() {
        // 3 evaluaciones con calificaciones [2.0, 5.0, 4.0]. Ordenado: [2.0, 4.0, 5.0] -> Mediana = 4.0
        pelicula1.agregarEvaluacion(createRealEvaluation(2.0f));
        pelicula1.agregarEvaluacion(createRealEvaluation(5.0f));
        pelicula1.agregarEvaluacion(createRealEvaluation(4.0f));
        director.agregarPelicula(pelicula1);

        float mediana = director.obtainMediana();

        assertEquals(4.0f, mediana);
    }

    @Test
    void testObtainMedianaCantidadPar() {
        // 4 evaluaciones [2.0, 5.0, 4.0, 3.0]. Ordenado: [2.0, 3.0, 4.0, 5.0] -> Mediana = (3.0+4.0)/2 = 3.5
        pelicula1.agregarEvaluacion(createRealEvaluation(2.0f));
        pelicula1.agregarEvaluacion(createRealEvaluation(5.0f));
        pelicula2.agregarEvaluacion(createRealEvaluation(4.0f));
        pelicula2.agregarEvaluacion(createRealEvaluation(3.0f));
        director.agregarPelicula(pelicula1);
        director.agregarPelicula(pelicula2);

        float mediana = director.obtainMediana();

        assertEquals(3.5f, mediana);
    }

    @Test
    void testObtainMediana_NoRatings_ThrowsException() {
        // El director tiene una película pero sin evaluaciones
        director.agregarPelicula(pelicula1);

        assertThrows(ListOutOfIndex.class, () -> {director.obtainMediana();});
    }
}