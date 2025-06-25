package um.edu.uy.entities;

import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.List.MyList;

public class Actor {
    private String nombre;
    private int id;
    private final MyList<Pelicula> peliculas;

    public Actor(int id, String nombre) {
        this.nombre = nombre;
        this.id = id;
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


    public int[] getStatsForMonth(int mes) {
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
        }
        int ratingCount = 0;
        int movieCount = 0;
        for (Pelicula pelicula : peliculas) {
            int movieRatingsInMonth = pelicula.getListaEvaluacionesEnMes(mes).size();
            if (movieRatingsInMonth > 0) {
                ratingCount += movieRatingsInMonth;
                movieCount++;
            }
        }
        return new int[]{ratingCount, movieCount};
    }


    public int getCantidadPeliculasActor() {
        return peliculas.size();
    }
}