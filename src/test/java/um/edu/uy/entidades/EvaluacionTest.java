package um.edu.uy.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EvaluacionTest {
    Evaluacion evaluacion1;
    Evaluacion evaluacion2;
    Evaluacion evaluacion3;
    Evaluacion evaluacion4;
    
    Pelicula pelicula1;
    Pelicula pelicula2;
    Pelicula pelicula3;

    @BeforeEach
    void setUp() {
        evaluacion1 = new Evaluacion(89, 6.2f, new Date(2023, 10, 15));
        evaluacion2 = new Evaluacion(92, 8.8f, new Date(2021, 4, 27));
        evaluacion3 = new Evaluacion(12, 2.4f, new Date(2020, 2, 18));
        evaluacion4 = new Evaluacion(98, 8.7f, new Date(2021, 0, 9));
    }
    
    @Test
    void getUserId() {
        assertEquals(89, evaluacion1.getUserId());
    }

    @Test
    void getRating() {
        assertEquals(8.8f, evaluacion2.getRating());
    }

    @Test
    void getReviewMonth() {
        assertEquals(2, evaluacion3.getReviewMonth());
    }
}