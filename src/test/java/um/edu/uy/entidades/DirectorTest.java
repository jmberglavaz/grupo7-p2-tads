package um.edu.uy.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import um.edu.uy.excepciones.ListOutOfIndexException;

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
    void testObtainMedianCantidadImpar() {
        // 3 evaluaciones con calificaciones [2.0, 5.0, 4.0]. Ordenado: [2.0, 4.0, 5.0] -> Mediana = 4.0
        pelicula1.addReview(createRealEvaluation(2.0f));
        pelicula1.addReview(createRealEvaluation(5.0f));
        pelicula1.addReview(createRealEvaluation(4.0f));
        director.agregarPelicula(pelicula1);

        float mediana = director.obtainMedian();

        assertEquals(4.0f, mediana);
    }

    @Test
    void testObtainMedianCantidadPar() {
        // 4 evaluaciones [2.0, 5.0, 4.0, 3.0]. Ordenado: [2.0, 3.0, 4.0, 5.0] -> Mediana = (3.0+4.0)/2 = 3.5
        pelicula1.addReview(createRealEvaluation(2.0f));
        pelicula1.addReview(createRealEvaluation(5.0f));
        pelicula2.addReview(createRealEvaluation(4.0f));
        pelicula2.addReview(createRealEvaluation(3.0f));
        director.agregarPelicula(pelicula1);
        director.agregarPelicula(pelicula2);

        float mediana = director.obtainMedian();

        assertEquals(3.5f, mediana);
    }

    @Test
    void testObtainMedian_NoRatings_ThrowsException() {
        // El director tiene una película pero sin evaluaciones
        director.agregarPelicula(pelicula1);

        assertThrows(ListOutOfIndexException.class, () -> {director.obtainMedian();});
    }
}