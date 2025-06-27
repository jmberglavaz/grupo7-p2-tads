package um.edu.uy.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class GeneroTest {
    private Genero accion;
    private Genero aventura;
    private Genero animacion;
    private Genero biografia;

    private Pelicula pelicula1;
    private Pelicula pelicula2;
    private Pelicula pelicula3;
    private Pelicula pelicula4;
    private Pelicula pelicula5;

    @BeforeEach
    void setUp() {
        accion = new Genero(1, "Acción");
        aventura = new Genero(2, "Aventura");
        animacion = new Genero(3, "Animación");
        biografia = new Genero(4, "Biografía");

        pelicula1 = new Pelicula(1, "Fuego en el Horizonte 1", "2022-08-22", 1839725903L);
        pelicula2 = new Pelicula(2, "Luces de la Ciudad 2", "2017-08-15", 1642545662L);
        pelicula3 = new Pelicula(3, "Sombras del Pasado 3", "2011-07-11", 1417832793L);
        pelicula4 = new Pelicula(4, "El Secreto de Luna 4", "2015-09-30", 1820927531L);
        pelicula5 = new Pelicula(5, "Corazones Perdidos 5", "2015-06-30", 861781966L);
    }

    @Test
    void addMovie() {
        accion.addMovie(pelicula1);
        assertTrue(accion.getMovieList().contains(pelicula1));
    }

    @Test
    void getTotalReviewCount() {
        aventura.addMovie(pelicula2);
        aventura.addMovie(pelicula3);
        pelicula2.addReview(new Evaluacion(89, 9.7f, new Date(2024, 3, 4)));
        pelicula2.addReview(new Evaluacion(100, 3.2f, new Date(2021, 7, 3)));
        pelicula3.addReview(new Evaluacion(79, 8.9f, new Date(2021, 3, 27)));

        assertEquals(3, aventura.getTotalReviewCount());
    }

    @Test
    void noReviewsTotalReviewCount() {
        animacion.addMovie(pelicula1);
        animacion.addMovie(pelicula2);
        animacion.addMovie(pelicula3);

        assertEquals(0, animacion.getTotalReviewCount());
    }

    @Test
    void noMoviesReviewCount() {
        assertEquals(0, accion.getTotalReviewCount());
    }

    @Test
    void getName() {
        assertEquals("Biografía", biografia.getName());
    }

    @Test
    void getId() {
        assertEquals(1, accion.getId());
    }

    @Test
    void getPeliculas() {
        aventura.addMovie(pelicula3);
        aventura.addMovie(pelicula4);
        aventura.addMovie(pelicula5);
        assertEquals(3, aventura.getMovieList().size());
        assertEquals(pelicula3, aventura.getMovieList().get(0));
        assertEquals(pelicula4, aventura.getMovieList().get(1));
        assertEquals(pelicula5, aventura.getMovieList().get(2));
    }

    @Test
    void getPeliculasVacia() {
        assertEquals(0, animacion.getMovieList().size());
    }
}