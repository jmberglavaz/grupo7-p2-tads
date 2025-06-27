package um.edu.uy.entidades;

import org.junit.jupiter.api.*;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {


    private Actor actor1;
    private Actor actor2;
    private Actor actorSinPeliculas;

    // Datos de prueba para actores
    private static final int ACTOR_ID_1 = 1;
    private static final String ACTOR_NAME_1 = "Leonardo DiCaprio";

    private static final int ACTOR_ID_2 = 2;
    private static final String ACTOR_NAME_2 = "Scarlett Johansson";

    private static final int ACTOR_ID_3 = 3;
    private static final String ACTOR_NAME_3 = "Actor Sin Películas";

    @BeforeEach
    void setUp() {
        actor1 = new Actor(ACTOR_ID_1, ACTOR_NAME_1);
        actor2 = new Actor(ACTOR_ID_2, ACTOR_NAME_2);
        actorSinPeliculas = new Actor(ACTOR_ID_3, ACTOR_NAME_3);

        // Configurar películas de prueba (necesitarás ajustar según tu clase Pelicula)
        setupMoviesForActor1();
        setupMoviesForActor2();
    }

    private void setupMoviesForActor1() {
        // Crear películas para Leonardo DiCaprio
        Pelicula inception = new Pelicula(1, "Inception", "2010-07-16", 836800000L);
        Pelicula titanic = new Pelicula(2, "Titanic", "1997-12-19", 2187000000L);
        Pelicula revenant = new Pelicula(3, "The Revenant", "2015-12-25", 533000000L);

        // Crear fechas para diferentes meses usando Calendar
        Calendar cal = Calendar.getInstance();

        // Mes 1 (Enero): 3 evaluaciones en Inception, 2 en Titanic
        cal.set(2024, Calendar.JANUARY, 15);
        inception.addReview(new Evaluacion(1, 5.0f, cal.getTime()));
        cal.set(2024, Calendar.JANUARY, 20);
        inception.addReview(new Evaluacion(2, 4.5f, cal.getTime()));
        cal.set(2024, Calendar.JANUARY, 25);
        inception.addReview(new Evaluacion(3, 5.0f, cal.getTime()));

        cal.set(2024, Calendar.JANUARY, 10);
        titanic.addReview(new Evaluacion(4, 5.0f, cal.getTime()));
        cal.set(2024, Calendar.JANUARY, 12);
        titanic.addReview(new Evaluacion(5, 4.0f, cal.getTime()));

        // Mes 3 (Marzo): 1 evaluación en The Revenant
        cal.set(2024, Calendar.MARCH, 5);
        revenant.addReview(new Evaluacion(6, 5.0f, cal.getTime()));

        // Mes 6 (Junio): 2 evaluaciones más en Inception
        cal.set(2024, Calendar.JUNE, 10);
        inception.addReview(new Evaluacion(7, 4.0f, cal.getTime()));
        cal.set(2024, Calendar.JUNE, 15);
        inception.addReview(new Evaluacion(8, 4.5f, cal.getTime()));

        actor1.addMovie(inception);
        actor1.addMovie(titanic);
        actor1.addMovie(revenant);
    }

    private void setupMoviesForActor2() {
        // Crear películas para Scarlett Johansson
        Pelicula blackWidow = new Pelicula(4, "Black Widow", "2021-07-09", 379800000L);
        Pelicula lostInTranslation = new Pelicula(5, "Lost in Translation", "2003-09-12", 119700000L);

        Calendar cal = Calendar.getInstance();

        // Mes 2 (Febrero): 4 evaluaciones en Black Widow
        cal.set(2024, Calendar.FEBRUARY, 5);
        blackWidow.addReview(new Evaluacion(7, 4.0f, cal.getTime()));
        cal.set(2024, Calendar.FEBRUARY, 10);
        blackWidow.addReview(new Evaluacion(8, 3.5f, cal.getTime()));
        cal.set(2024, Calendar.FEBRUARY, 15);
        blackWidow.addReview(new Evaluacion(9, 5.0f, cal.getTime()));
        cal.set(2024, Calendar.FEBRUARY, 20);
        blackWidow.addReview(new Evaluacion(10, 4.5f, cal.getTime()));

        // Mes 6 (Junio): 2 evaluaciones en Lost in Translation
        cal.set(2024, Calendar.JUNE, 5);
        lostInTranslation.addReview(new Evaluacion(11, 5.0f, cal.getTime()));
        cal.set(2024, Calendar.JUNE, 12);
        lostInTranslation.addReview(new Evaluacion(12, 4.0f, cal.getTime()));

        // Mes 9 (Septiembre): 1 evaluación en Black Widow
        cal.set(2024, Calendar.SEPTEMBER, 3);
        blackWidow.addReview(new Evaluacion(13, 3.0f, cal.getTime()));

        actor2.addMovie(blackWidow);
        actor2.addMovie(lostInTranslation);
    }

    // CASOS DE PRUEBA PARA CONSTRUCTOR Y GETTERS

    @Test
    void testConstructorAndGetters() {
        assertEquals(ACTOR_ID_1, actor1.getId());
        assertEquals(ACTOR_NAME_1, actor1.getName());

        assertEquals(ACTOR_ID_2, actor2.getId());
        assertEquals(ACTOR_NAME_2, actor2.getName());
    }

    // CASOS DE PRUEBA PARA getStatsForMonth()

    @Test
    void testGetStatsForMonthWithValidMonth() {
        // Mes 1 (Enero): actor1 debería tener 5 calificaciones en 2 películas
        // (3 en Inception + 2 en Titanic)
        int[] stats = actor1.getStatsForMonth(1);
        assertEquals(5, stats[0]); // total de calificaciones
        assertEquals(2, stats[1]); // películas con calificaciones

        // Mes 2 (Febrero): actor2 debería tener 4 calificaciones en 1 película
        // (4 en Black Widow)
        int[] stats2 = actor2.getStatsForMonth(2);
        assertEquals(4, stats2[0]);
        assertEquals(1, stats2[1]);

        // Mes 3 (Marzo): actor1 debería tener 1 calificación en 1 película
        // (1 en The Revenant)
        int[] stats3 = actor1.getStatsForMonth(3);
        assertEquals(1, stats3[0]);
        assertEquals(1, stats3[1]);

        // Mes 6 (Junio): actor1 debería tener 2 calificaciones en 1 película
        // actor2 debería tener 2 calificaciones en 1 película
        int[] stats4 = actor1.getStatsForMonth(6);
        assertEquals(2, stats4[0]); // 2 en Inception
        assertEquals(1, stats4[1]);

        int[] stats5 = actor2.getStatsForMonth(6);
        assertEquals(2, stats5[0]); // 2 en Lost in Translation
        assertEquals(1, stats5[1]);
    }

    @Test
    void testGetStatsForMonthWithNoMovies() {
        int[] stats = actorSinPeliculas.getStatsForMonth(1);
        assertEquals(0, stats[0]); // sin calificaciones
        assertEquals(0, stats[1]); // sin películas
    }

    @Test
    void testGetStatsForMonthWithNoRatingsInMonth() {
        // Test para un mes sin evaluaciones
        int[] stats = actor1.getStatsForMonth(12); // Diciembre sin evaluaciones
        assertEquals(0, stats[0]);
        assertEquals(0, stats[1]);

        // Mes 4 (Abril) - ningún actor tiene evaluaciones
        int[] stats2 = actor1.getStatsForMonth(4);
        assertEquals(0, stats2[0]);
        assertEquals(0, stats2[1]);

        int[] stats3 = actor2.getStatsForMonth(4);
        assertEquals(0, stats3[0]);
        assertEquals(0, stats3[1]);
    }

    @Test
    void testGetStatsForMonthInvalidMonth() {
        // Casos límite para meses inválidos
        assertThrows(IllegalArgumentException.class, () -> {
            actor1.getStatsForMonth(0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            actor1.getStatsForMonth(13);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            actor1.getStatsForMonth(-1);
        });
    }

    // CASOS DE PRUEBA PARA addMovie()

    @Test
    void testAddMovie() {
        Actor newActor = new Actor(999, "Actor Nuevo");
        Pelicula newMovie = new Pelicula(999, "Nueva Película", "2024-01-01", 100000000L);

        // Agregar evaluación a la nueva película
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.MAY, 10);
        newMovie.addReview(new Evaluacion(999, 4.5f, cal.getTime()));

        newActor.addMovie(newMovie);

        // Verificar que la película se agregó correctamente
        int[] stats = newActor.getStatsForMonth(5); // Mayo
        assertEquals(1, stats[0]); // 1 calificación
        assertEquals(1, stats[1]); // 1 película

        // Verificar que otros meses no tienen evaluaciones
        int[] statsOtherMonth = newActor.getStatsForMonth(1);
        assertEquals(0, statsOtherMonth[0]);
        assertEquals(0, statsOtherMonth[1]);
    }

    // DATOS DE PRUEBA ADICIONALES PARA DIFERENTES ESCENARIOS

    /**
     * Escenario 1: Actor con muchas películas y evaluaciones distribuidas
     */
    private Actor createActorWithManyMovies() {
        Actor actor = new Actor(100, "Actor Prolífico");
        Calendar cal = Calendar.getInstance();

        for (int i = 1; i <= 5; i++) {
            Pelicula movie = new Pelicula(i + 100, "Película " + i, "202" + i + "-01-01", 500000000L + i * 1000000L);

            // Agregar evaluaciones en diferentes meses
            for (int month = 0; month < 12; month++) {
                if (i % 3 == month % 3) { // Distribución irregular
                    cal.set(2024, month, 15);
                    movie.addReview(new Evaluacion(i * 12 + month, 4.0f + (month % 2), cal.getTime()));
                }
            }
            actor.addMovie(movie);
        }
        return actor;
    }

    /**
     * Escenario 2: Actor con películas pero sin evaluaciones
     */
    private Actor createActorWithMoviesButNoReviews() {
        Actor actor = new Actor(200, "Actor Sin Evaluaciones");

        Pelicula movie1 = new Pelicula(201, "Película Sin Reviews 1", "2020-01-01", 100000000L);
        Pelicula movie2 = new Pelicula(202, "Película Sin Reviews 2", "2021-01-01", 200000000L);

        actor.addMovie(movie1);
        actor.addMovie(movie2);

        return actor;
    }

    /**
     * Escenario 3: Actor con evaluaciones concentradas en un solo mes
     */
    private Actor createActorWithReviewsInSingleMonth() {
        Actor actor = new Actor(300, "Actor Mes Específico");
        Calendar cal = Calendar.getInstance();

        Pelicula movie = new Pelicula(301, "Película Popular", "2023-01-01", 800000000L);

        // Todas las evaluaciones en marzo (mes 2 en Calendar)
        for (int i = 1; i <= 10; i++) {
            cal.set(2024, Calendar.MARCH, i);
            movie.addReview(new Evaluacion(300 + i, 4.0f + (i % 2 == 0 ? 0.5f : 0.0f), cal.getTime()));
        }

        actor.addMovie(movie);
        return actor;
    }

    // TESTS ADICIONALES PARA ESCENARIOS ESPECIALES

    @Test
    void testActorWithManyMovies() {
        Actor actorProlifico = createActorWithManyMovies();

        // Verificar algunos meses específicos
        int[] stats1 = actorProlifico.getStatsForMonth(1); // Enero
        assertTrue(stats1[0] > 0); // Debería tener evaluaciones
        assertTrue(stats1[1] > 0); // Debería tener películas con evaluaciones

        int[] stats3 = actorProlifico.getStatsForMonth(3); // Marzo
        assertTrue(stats3[0] > 0);
        assertTrue(stats3[1] > 0);
    }

    @Test
    void testActorWithMoviesButNoReviews() {
        Actor actorSinReviews = createActorWithMoviesButNoReviews();

        // Todos los meses deberían devolver 0
        for (int month = 1; month <= 12; month++) {
            int[] stats = actorSinReviews.getStatsForMonth(month);
            assertEquals(0, stats[0], "Mes " + month + " debería tener 0 calificaciones");
            assertEquals(0, stats[1], "Mes " + month + " debería tener 0 películas con calificaciones");
        }
    }

    @Test
    void testActorWithReviewsInSingleMonth() {
        Actor actorMesEspecifico = createActorWithReviewsInSingleMonth();

        // Marzo debería tener 10 evaluaciones en 1 película
        int[] stats = actorMesEspecifico.getStatsForMonth(3);
        assertEquals(10, stats[0]);
        assertEquals(1, stats[1]);

        // Otros meses deberían estar vacíos
        int[] statsOtherMonth = actorMesEspecifico.getStatsForMonth(1);
        assertEquals(0, statsOtherMonth[0]);
        assertEquals(0, statsOtherMonth[1]);

        int[] statsAnotherMonth = actorMesEspecifico.getStatsForMonth(12);
        assertEquals(0, statsAnotherMonth[0]);
        assertEquals(0, statsAnotherMonth[1]);
    }
}