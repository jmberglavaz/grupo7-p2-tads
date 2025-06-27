package um.edu.uy.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class GeneroTest {

    private Genero genero;

    @BeforeEach
    void setUp() {
        genero = new Genero(28, "Acción");
    }

    // Metodo auxiliar para crear una evaluacion con un id especifico y resto de datos irrelevantes
    private Evaluacion createRealEvaluation(int userId) {
        return new Evaluacion(userId, 4.0f, new Date());
    }

    @Test
    void testTopUsuario_FindsCorrectUser() {
        Pelicula peli1 = new Pelicula(1, "Peli 1", "", 0);
        Pelicula peli2 = new Pelicula(2, "Peli 2", "", 0);

        // Usuario 101 hace 3 reviews
        peli1.addReview(createRealEvaluation(101));
        peli1.addReview(createRealEvaluation(101));
        peli2.addReview(createRealEvaluation(101));

        // Usuario 202 hace 5 reviews (el que mas)
        peli1.addReview(createRealEvaluation(202));
        peli1.addReview(createRealEvaluation(202));
        peli2.addReview(createRealEvaluation(202));
        peli2.addReview(createRealEvaluation(202));
        peli2.addReview(createRealEvaluation(202));

        // Usuario 303 hace 1 review
        peli1.addReview(createRealEvaluation(303));

        genero.agregarPelicula(peli1);
        genero.agregarPelicula(peli2);

        int[] topUsuario = genero.topUsuario();

        assertNotNull(topUsuario);
        assertEquals(2, topUsuario.length);
        assertEquals(202, topUsuario[0]); // Id del usuario
        assertEquals(5, topUsuario[1]);   // Cantidad de reviews
    }

    @Test
    void testTopUsuario_NoEvaluations() {
        Pelicula peli1 = new Pelicula(1, "Peli 1", "", 0);
        genero.agregarPelicula(peli1);

        // No se agregan evaluaciones a la película
        int[] topUsuario = genero.topUsuario();

        // Assert: Devuelve el valor por defecto
        assertEquals(-1, topUsuario[0]);
        assertEquals(0, topUsuario[1]);
    }
}