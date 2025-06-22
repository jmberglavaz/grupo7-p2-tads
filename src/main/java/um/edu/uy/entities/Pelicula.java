package um.edu.uy.entities;

import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.List.MyArrayListImpl;
import um.edu.uy.TADs.List.MyList;

public class Pelicula implements Comparable<Pelicula>{
    private int id;
    private String titulo;
    private String fechaDeEstreno;
    private long ingresos;
    private MyList<MyList<Evaluacion>> listaEvaluaciones;
    private MyList<String> listaDeActores;

    public Pelicula(int id, String titulo, String fechaDeEstreno, long ingresos) {
        this.id = id;
        this.titulo = titulo;
        this.fechaDeEstreno = fechaDeEstreno;
        this.ingresos = ingresos;
        listaEvaluaciones = new MyArrayListImpl<>(12);
        for (int iter = 0 ; iter < 12 ; iter++){
            listaEvaluaciones.add(new MyLinkedListImpl<>());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFechaDeEstreno() {
        return fechaDeEstreno;
    }

    public void setFechaDeEstreno(String fechaDeEstreno) {
        this.fechaDeEstreno = fechaDeEstreno;
    }

    public long getIngresos() {
        return ingresos;
    }

    public void setIngresos(long ingresos) {
        this.ingresos = ingresos;
    }

    public MyList<Evaluacion> getListaEvaluaciones() {
        MyList<Evaluacion> listaResultado = new MyLinkedListImpl<>();
        for (MyList<Evaluacion> tempLista : this.listaEvaluaciones){
            for (Evaluacion tempEvaluacion : tempLista){
                listaResultado.add(tempEvaluacion);
            }
        }
        return listaResultado;
    }

    public MyList<Evaluacion> getListaEvaluacionesEnMes(int mes){
        if (mes < 1 || mes > 12){
            throw new IllegalArgumentException("El mes tiene que estar entre 1 y 12");
        }
        return listaEvaluaciones.get(mes-1);
    }

    public int getCantidadEvaluaciones() {
        int size = 0;
        for (MyList<Evaluacion> evalucionesPorMes : listaEvaluaciones){
            size += evalucionesPorMes.size();
        }
        return size;
    }

    public float getPromedioDeEvaluaciones() {
        float sumaDeCalificaciones = 0;
        int size = 0;
        for (MyList<Evaluacion> tempListaEvaluacion : listaEvaluaciones) {
            size += tempListaEvaluacion.size();
            for (Evaluacion tempEvaluacion : tempListaEvaluacion)
                sumaDeCalificaciones += tempEvaluacion.getCalificacion();
        }
        return sumaDeCalificaciones / size;
    }

    public void agregarEvaluacion(Evaluacion tempEvaluacion) {
        int mes = tempEvaluacion.getFecha().getMonth();
        listaEvaluaciones.get(mes).add(tempEvaluacion);
    }
  
   public void setListaDeActores(MyList<String> actores) {
       listaDeActores = actores;
   }

    public MyList<String> getListaDeActores() {
         return listaDeActores;
    }


    @Override
    public int compareTo(Pelicula tempPelicula) {
        return Integer.compare(this.getCantidadEvaluaciones(), tempPelicula.getCantidadEvaluaciones());
    }

}