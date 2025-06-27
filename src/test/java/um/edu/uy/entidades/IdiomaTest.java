package um.edu.uy.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdiomaTest {
    Idioma italiano;
    Idioma portugues;
    Idioma japones;
    Idioma coreano;

    Pelicula pelicula1;
    Pelicula pelicula2;
    Pelicula pelicula3;
    Pelicula pelicula4;
    Pelicula pelicula5;
    Pelicula pelicula6;

    @BeforeEach
    void setUp() {
        italiano = new Idioma("it");
        portugues = new Idioma("pt");
        japones = new Idioma("ja");
        coreano = new Idioma("ko");

        pelicula1 = new Pelicula(25, "El Eco del Silencio 25", "2018-04-05", 1264793391L);
        pelicula2 = new Pelicula(26, "La Fortaleza Olvidada 26", "2023-11-23", 376292772L);
        pelicula3 = new Pelicula(27, "El Laberinto Dorado 27", "2004-08-17", 636051310L);
        pelicula4 = new Pelicula(28, "El Espejo Roto 28", "2009-04-09", 608202852L);
    }

    @Test
    void getAcronym() {
        assertEquals("it", italiano.getAcronym());
    }

    @Test
    void getMovieList() {
        portugues.addMovie(pelicula1);
        portugues.addMovie(pelicula2);
        portugues.addMovie(pelicula3);

        assertEquals(pelicula1, portugues.getMovieList().get(0));
        assertEquals(pelicula2, portugues.getMovieList().get(1));
        assertEquals(pelicula3, portugues.getMovieList().get(2));
    }

    @Test
    void getEmptyMovieList() {
        assertEquals(0, japones.getMovieList().size());
    }

    @Test
    void addMovie() {
        coreano.addMovie(pelicula4);
        assertTrue(coreano.getMovieList().contains(pelicula4));
    }
}