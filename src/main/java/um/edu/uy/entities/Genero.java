package um.edu.uy.entities;

import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.Hash.MyHashImplCloseLineal;
import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.List.MyArrayListImpl;
import um.edu.uy.TADs.List.MyList;

public class Genero {
    private final int id;
    private final String nombre;
    private final MyList<Pelicula> listaPeliculas;
    private int cantEvaluciones;

    public Genero(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.listaPeliculas = new MyArrayListImpl<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarPelicula(Pelicula tempPeli) {
        listaPeliculas.add(tempPeli);
    }

    public int cantEvaluaciones(){
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

    public MyList<Pelicula> getListaPeliculas() {
        return listaPeliculas;
    }
}
