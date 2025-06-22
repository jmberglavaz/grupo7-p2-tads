package um.edu.uy.entities;

import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.List.MyList;

public class Actor {
    private String nombre;
    private final MyList<Pelicula> peliculas;

    public Actor(String nombre) {
        this.nombre = nombre;
        this.peliculas = new MyLinkedListImpl<>();
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public MyList<Pelicula> getPeliculas() {
        return peliculas;
    }
    public void agregarPelicula(Pelicula tempPeli) {
        peliculas.add(tempPeli);
    }

    public int getCantidadEvaluacionesActorPorMes(int nroMes) {
        int cantidadEvaluacionesEnMes = 0;
        for (Pelicula pelicula : peliculas) {
            cantidadEvaluacionesEnMes += pelicula.getListaEvaluacionesEnMes(nroMes).size();
        }
        return cantidadEvaluacionesEnMes;
    }
}
