package um.edu.uy.entities;

import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.List.MyList;

/**
 * Representa una colección o saga de películas.
 */
public class Coleccion {
    private int id;
    private String title;
    private MyList<Pelicula> movieList;

    /**
     * Constructor para crear una nueva instancia de Coleccion.
     * @param id El identificador único de la colección.
     * @param title El título de la colección.
     */
    public Coleccion(int id, String title) {
        this.id = id;
        this.title = title;
        this.movieList = new MyLinkedListImpl<>();
    }

    // GETTERS

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public MyList<Pelicula> getMovieList() {
        return movieList;
    }

    public int getMovieCount(){
        return movieList.size();
    }


    // MÉTODOS PARA MODIFICAR LA LISTA

    /**
     * Agrega una película a la colección.
     * @param movie La película a agregar.
     */
    public void addMovie(Pelicula movie){
        movieList.add(movie);
    }
}