package um.edu.uy.entities;

import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.List.MyList;

/**
 * Representa un idioma en el que una película fue filmada o doblada.
 */
public class Idioma {
    private String acronym;
    private MyList<Pelicula> movieList;

    /**
     * Constructor para crear una nueva instancia de Idioma.
     * @param acronym El acrónimo del idioma (ej. "en" para inglés).
     */
    public Idioma(String acronym) {

        this.acronym = acronym;
        this.movieList = new MyLinkedListImpl<>();
    }

    public String getAcronym() {
        return acronym;
    }

    public MyList<Pelicula> getMovieList() {
        return movieList;
    }

    /**
     * Agrega una película a la lista de películas en este idioma.
     * @param movie La película a agregar.
     */
    public void addMovie(Pelicula movie) {
        movieList.add(movie);
    }
}