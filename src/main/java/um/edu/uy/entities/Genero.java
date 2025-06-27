package um.edu.uy.entities;

import um.edu.uy.TADs.List.MyArrayListImpl;
import um.edu.uy.TADs.List.MyList;

/**
 * Representa un género cinematográfico (ej. Acción, Comedia, etc.).
 */
public class Genero {
    private final int id;
    private final String name;
    private final MyList<Pelicula> movieList;

    // Variable para optimizar y no recalcular
    private int reviewCountCache;

    /**
     * Constructor para crear una nueva instancia de Genero.
     * @param id El ID único del género.
     * @param name El nombre del género.
     */
    public Genero(int id, String name) {
        this.id = id;
        this.name = name;
        this.movieList = new MyArrayListImpl<>();
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public MyList<Pelicula> getMovieList() {
        return movieList;
    }

    /**
     * Agrega una película a la lista de películas de este género.
     * @param movie La película a agregar.
     */
    public void addMovie(Pelicula movie) {
        movieList.add(movie);
    }

    /**
     * Obtiene el número total de evaluaciones de todas las películas de este género.
     * Utiliza una variable para evitar recalcular este valor.
     * @return El número total de evaluaciones.
     */
    public int getTotalReviewCount(){
        // Si el valor ya fue calculado, se devuelve directamente
        if (this.reviewCountCache != 0){
            return this.reviewCountCache;
        }

        // Si no, se calcula, se guarda en la variable y se devuelve
        int count = 0;
        for (Pelicula movie : movieList){
            count += movie.getTotalReviewCount();
        }
        this.reviewCountCache = count;
        return reviewCountCache;
    }

    public int getId() {
        return id;
    }

}