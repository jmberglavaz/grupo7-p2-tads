package um.edu.uy.entities;

import um.edu.uy.TADs.List.MyArrayListImpl;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.TADs.Sorting;

public class Director implements Comparable<Director>{
    private String nombre;
    private final MyList<Pelicula> listaPeliculas;
    private float mediana;
    private int cantEvaluciones;

    public Director(String nombre) {
        this.nombre = nombre;
        this.listaPeliculas = new MyArrayListImpl<>();
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
        if (this.cantEvaluciones != 0){
            return this.cantEvaluciones;
        }

        int cant = 0;
        for (Pelicula peliActual : listaPeliculas){
            cant += peliActual.getCantidadEvaluaciones();
        }

        this.cantEvaluciones = cant;
        return cantEvaluciones;
    }

    public float obtainMediana(){
        if (this.mediana != 0){
            return mediana;
        }


        int largo = getCantidadEvaluaciones();
        if (listaPeliculas.size() <= 1 || largo <=100){
            return 0;
        }

        MyList<Float> evaluaciones = new MyArrayListImpl<>(largo);
        for (Pelicula tempPelicula : listaPeliculas){
            if (tempPelicula.getCantidadEvaluaciones() == 0){continue;}
            for (Evaluacion tempEvaluacion : tempPelicula.getListaEvaluaciones()){
                evaluaciones.add(tempEvaluacion.getCalificacion());
            }
        }

        Sorting<Float> ordenamiento = new Sorting<>();
        evaluaciones = ordenamiento.quickSort(evaluaciones);

        this.mediana = (largo % 2 == 0) ?
                (evaluaciones.get(largo/2) + evaluaciones.get((largo/2) - 1))/2 :
                evaluaciones.get(((largo + 1)/2)-1);
        return this.mediana;
    }

    @Override
    public int compareTo(Director tempDirector) {
        return Float.compare(this.obtainMediana(),tempDirector.obtainMediana());
    }
}
