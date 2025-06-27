package um.edu.uy.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColeccionTest {

    Coleccion coleccion1;
    Coleccion coleccion2;
    Coleccion coleccion3;

    Pelicula pelicula1;
    Pelicula pelicula2;
    Pelicula pelicula3;
    Pelicula pelicula4;
    Pelicula pelicula5;
    Pelicula pelicula6;

    @BeforeEach
    void setUp() {
        coleccion1 = new Coleccion(13, "Colección 13");
        coleccion2 = new Coleccion(14, "Colección 14");
        coleccion3 = new Coleccion(15, "Colección 15");

        pelicula1 = new Pelicula(45, "El Secreto de Luna 45", "2014-09-12", 1917091417L);
        pelicula2 = new Pelicula(46, "Danza de Sombras 46", "2015-01-02", 1260164449L);
        pelicula3 = new Pelicula(47, "El Laberinto Dorado 47", "2014-03-11", 552015215L);
        pelicula4 = new Pelicula(48, "Huellas en la Arena 48", "2009-06-20", 254856303L);
        pelicula5 = new Pelicula(49, "El Viaje Infinito 49", "2005-08-12", 283179724L);
        pelicula6 = new Pelicula(50, "El Secreto de Luna 50", "2000-11-26", 392724406L);
    }

    @Test
    void getId() {
        assertEquals(15, coleccion3.getId());
    }

    @Test
    void getTitle() {
        assertEquals("Colección 13", coleccion1.getTitle());
    }

    @Test
    void getMovieList() {
        coleccion2.addMovie(pelicula1);
        coleccion2.addMovie(pelicula2);
        coleccion2.addMovie(pelicula3);
        assertEquals(3, coleccion2.getMovieList().size());
        assertEquals(coleccion2.getMovieList().get(0), pelicula1);
        assertEquals(coleccion2.getMovieList().get(1), pelicula2);
        assertEquals(coleccion2.getMovieList().get(2), pelicula3);
    }

    @Test
    void getEmptyMovieList() {
        assertTrue(coleccion3.getMovieList().isEmpty());
    }

    @Test
    void getMovieCount() {
        coleccion1.addMovie(pelicula4);
        coleccion1.addMovie(pelicula5);
        coleccion1.addMovie(pelicula6);
        coleccion1.addMovie(pelicula1);
        assertEquals(4, coleccion1.getMovieCount());
    }

    @Test
    void addMovie() {
        coleccion2.addMovie(pelicula2);
        assertTrue(coleccion2.getMovieList().contains(pelicula2));
    }
}