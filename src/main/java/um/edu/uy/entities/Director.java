package um.edu.uy.entities;

import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.List.MyArrayListImpl;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.TADs.Sorting;

public class Director {
    private String nombre;
    private final MyList<Pelicula> listaPeliculas;

    public Director(String nombre) {
        this.nombre = nombre;
        this.listaPeliculas = new MyLinkedListImpl<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public MyList<Pelicula> getListaPeliculas() {
        return listaPeliculas;
    }

    public void agregarPelicula(Pelicula tempPelicula) {
        this.listaPeliculas.add(tempPelicula);
    }

    public int getCantidadPeliculas(){
        return listaPeliculas.size();
    }

    public int getCantidadEvaluaciones(){
        int cant = 0;
        for (Pelicula peliActual : listaPeliculas){
            cant += peliActual.getCantidadEvaluaciones();
        }
        return cant;
    }

    public float obtainMediana(){
        int largo = getCantidadEvaluaciones();
        MyList<Float> evaluaciones = new MyArrayListImpl<>(largo);
        for (Pelicula tempPelicula : listaPeliculas){
            if (tempPelicula.getCantidadEvaluaciones() == 0){continue;}
            for (Evaluacion tempEvaluacion : tempPelicula.getListaEvaluaciones()){
                evaluaciones.add(tempEvaluacion.getCalificacion());
            }
        }

        Sorting<Float> ordenamiento = new Sorting<>();
        evaluaciones = ordenamiento.quickSort(evaluaciones);
        return (largo % 2 == 0) ?
                (evaluaciones.get(largo/2) + evaluaciones.get((largo/2) + 1))/2 :
                evaluaciones.get((largo + 1)/2);
    }
}
