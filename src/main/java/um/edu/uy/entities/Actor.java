package um.edu.uy.entities;

import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.List.MyList;

/**
 * Representa a un actor, con su ID, nombre y una lista de películas en las que ha participado.
 */
public class Actor {
    private String name;
    private int id;
    private final MyList<Pelicula> movieList;

    /**
     * Constructor para crear una nueva instancia de Actor.
     * @param id El identificador único del actor.
     * @param name El nombre del actor.
     */
    public Actor(int id, String name) {
        this.name = name;
        this.id = id;
        this.movieList = new MyLinkedListImpl<>();
    }

    // GETTERS

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    /**
     * Obtiene las estadísticas de un actor para un mes específico.
     * @param month El mes (1-12) para el cual se calculan las estadísticas.
     * @return Un array de enteros donde:
     * - El índice 0 es la cantidad total de calificaciones recibidas en ese mes.
     * - El índice 1 es la cantidad de películas con calificaciones en ese mes.
     */
    public int[] getStatsForMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
        }
        int ratingCount = 0;
        int movieCount = 0;

        // Itera sobre las películas del actor para sumar las calificaciones del mes
        for (Pelicula movie : movieList) {
            int movieRatingsInMonth = movie.getReviewsForMonth(month).size();
            if (movieRatingsInMonth > 0) {
                ratingCount += movieRatingsInMonth;
                movieCount++;
            }
        }
        return new int[]{ratingCount, movieCount};
    }

    /**
     * Agrega una película a la lista de películas en las que el actor ha trabajado.
     * @param movie La película a agregar.
     */
    public void addMovie(Pelicula movie) {
        movieList.add(movie);
    }
}