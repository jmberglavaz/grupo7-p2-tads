package um.edu.uy.entidades;

import java.util.Date;
import java.util.Calendar;
/**
 * Clase utilitaria para generar datos ficticios para tests de JUnit
 * Mantiene las relaciones entre Actor, Pelicula, Director, Genero, Idioma, Coleccion y Evaluacion */
public class TestDataGenerator {
    // Datos de prueba para Actores
    public static Actor crearActorConPeliculas() {
        Actor actor = new Actor("Leonardo DiCaprio");
        // Nota: Necesitarás agregar las películas después de crear el actor
        return actor;
    }
    public static Actor crearActorSinPeliculas() {
        return new Actor("Ryan Gosling");
    }

    // Datos de prueba para Directores
    public static Director crearDirectorConPeliculas() {
        Director director = new Director("Christopher Nolan");
        // Las películas se agregan después de crear el director
        return director;
    }
    public static Director crearDirectorSinPeliculas() {
        return new Director("Denis Villeneuve");
    }
    // Datos de prueba para Géneros
    public static Genero crearGeneroAccion() {
        return new Genero(1, "Acción");
    }
    public static Genero crearGeneroDrama() {
        return new Genero(2, "Drama");
    }
    public static Genero crearGeneroComedia() {
        return new Genero(3, "Comedia");
    }
    public static Genero crearGeneroScienceFiction() {
        return new Genero(4, "Ciencia Ficción");
    }
    // Datos de prueba para Idiomas
    public static Idioma crearIdiomaIngles() {
        Idioma idioma = new Idioma("EN");
        // Nota: El nombre se setea a null en el constructor, podrías necesitar un setter
        return idioma;
    }
    public static Idioma crearIdiomaEspanol() {
        return new Idioma("ES");
    }
    public static Idioma crearIdiomaFrances() {
        return new Idioma("FR");
    }
    // Datos de prueba para Evaluaciones
    public static Evaluacion crearEvaluacionAlta() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.JANUARY, 15);
        return new Evaluacion(1001, 4.5f, cal.getTime());
    }
    public static Evaluacion crearEvaluacionMedia() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.FEBRUARY, 20);
        return new Evaluacion(1002, 3.2f, cal.getTime());
    }

    public static Evaluacion crearEvaluacionBaja() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.MARCH, 10);
        return new Evaluacion(1003, 2.1f, cal.getTime());
    }
    public static Evaluacion crearEvaluacionReciente() {
        return new Evaluacion(1004, 4.8f, new Date());
    }

    // Datos de prueba para Películas
    public static Pelicula crearPeliculaInception() {
        return new Pelicula(101, "Inception", "2010-07-16", 836800000L);
    }

    public static Pelicula crearPeliculaInterstellar() {
        return new Pelicula(102, "Interstellar", "2014-11-07", 701800000L);
    }

    public static Pelicula crearPeliculaTitanic() {
        return new Pelicula(103, "Titanic", "1997-12-19", 2264750000L);
    }

    public static Pelicula crearPeliculaAvengers() {
        return new Pelicula(104, "Avengers: Endgame", "2019-04-26", 2797800000L);
    }

    public static Pelicula crearPeliculaParasite() {
        return new Pelicula(105, "Parasite", "2019-05-30", 262000000L);
    }

    // Película con pocos ingresos para tests de edge cases
    public static Pelicula crearPeliculaIndependiente() {
        return new Pelicula(106, "Película Independiente", "2023-01-01", 50000L);
    }

    // Datos de prueba para Colecciones
    public static Coleccion crearColeccionMarvel() {
        return new Coleccion(1, "Marvel Cinematic Universe");
    }

    public static Coleccion crearColeccionNolan() {
        return new Coleccion(2, "Películas de Christopher Nolan");
    }

    public static Coleccion crearColeccionClasicos() {
        return new Coleccion(3, "Clásicos del Cine");
    }

    public static Coleccion crearColeccionVacia() {
        return new Coleccion(4, "Colección Vacía");
    }

    // Métodos para crear conjuntos de datos relacionados
    public static TestDataSet crearConjuntoDatosCompleto() {
        TestDataSet dataSet = new TestDataSet();
        // Crear películas
        Pelicula inception = crearPeliculaInception();
        Pelicula interstellar = crearPeliculaInterstellar();
        Pelicula titanic = crearPeliculaTitanic();
        // Crear evaluaciones y agregarlas a las películas
        // Nota: Necesitarás métodos para agregar evaluaciones a las películas
        // Crear actores
        Actor leonardo = crearActorConPeliculas();
        Actor ryan = crearActorSinPeliculas();
        // Crear directores
        Director nolan = crearDirectorConPeliculas();
        Director villeneuve = crearDirectorSinPeliculas();
        // Crear géneros
        Genero accion = crearGeneroAccion();
        Genero drama = crearGeneroDrama();
        Genero scifi = crearGeneroScienceFiction();
        // Crear idiomas
        Idioma ingles = crearIdiomaIngles();
        Idioma espanol = crearIdiomaEspanol();
        // Crear colecciones
        Coleccion coleccionNolan = crearColeccionNolan();
        Coleccion coleccionMarvel = crearColeccionMarvel();
        // Crear evaluaciones
        Evaluacion evalAlta = crearEvaluacionAlta();
        Evaluacion evalMedia = crearEvaluacionMedia();
        Evaluacion evalBaja = crearEvaluacionBaja();
        // Almacenar todo en el dataset
        dataSet.peliculas = new Pelicula[]{inception, interstellar, titanic};
        dataSet.actores = new Actor[]{leonardo, ryan};
        dataSet.directores = new Director[]{nolan, villeneuve};
        dataSet.generos = new Genero[]{accion, drama, scifi};
        dataSet.idiomas = new Idioma[]{ingles, espanol};
        dataSet.colecciones = new Coleccion[]{coleccionNolan, coleccionMarvel};
        dataSet.evaluaciones = new Evaluacion[]{evalAlta, evalMedia, evalBaja};
        return dataSet;
    }
    // Métodos para casos edge
    public static Pelicula crearPeliculaConIdNegativo() {
        return new Pelicula(-1, "Película con ID negativo", "2023-01-01", 1000000L);
    }

    public static Pelicula crearPeliculaConTituloVacio() {
        return new Pelicula(999, "", "2023-01-01", 1000000L);
    }

    public static Pelicula crearPeliculaConIngresoCero() {
        return new Pelicula(998, "Película sin ingresos", "2023-01-01", 0L);
    }

    public static Evaluacion crearEvaluacionConCalificacionMaxima() {
        return new Evaluacion(2001, 5.0f, new Date());
    }

    public static Evaluacion crearEvaluacionConCalificacionMinima() {
        return new Evaluacion(2002, 0.0f, new Date());
    }

    public static Genero crearGeneroConIdCero() {
        return new Genero(0, "Género sin ID");
    }

    public static Coleccion crearColeccionConIdNegativo() {
        return new Coleccion(-1, "Colección con ID negativo");
    }

    // Clase interna para organizar conjuntos de datos de prueba
    public static class TestDataSet {
        public Pelicula[] peliculas;
        public Actor[] actores;
        public Director[] directores;
        public Genero[] generos;
        public Idioma[] idiomas;
        public Coleccion[] colecciones;
        public Evaluacion[] evaluaciones;
    }

    // Método para crear fechas específicas
    public static Date crearFecha(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        // month es 0-based en Calendar
        return cal.getTime();
    }

    // Arrays de datos para tests que necesiten múltiples elementos
    public static String[] getNombresActores() {
        return new String[]{
                "Leonardo DiCaprio", "Brad Pitt", "Meryl Streep", "Robert De Niro",
                "Scarlett Johansson", "Tom Hanks", "Julia Roberts", "Will Smith"
        };
    }

    public static String[] getNombresDirectores() {
        return new String[]{
                "Christopher Nolan", "Quentin Tarantino", "Martin Scorsese",
                "Steven Spielberg", "Denis Villeneuve", "Greta Gerwig"
        };
    }

    public static String[] getTitulosPeliculas() {
        return new String[]{
                "The Dark Knight", "Pulp Fiction", "The Godfather", "Casablanca",
                "Citizen Kane", "The Shawshank Redemption", "Schindler's List"
        };
    }

    public static String[] getNombresGeneros() {
        return new String[]{
                "Acción", "Drama", "Comedia", "Terror", "Ciencia Ficción",
                "Romance", "Thriller", "Aventura", "Animación", "Documental"
        };
    }}
