package um.edu.uy.entities;

import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.List.MyList;

public class Coleccion {
    private int id;
    private String titulo;
    private MyList<Pelicula> listaDePeliculas;
    public Coleccion(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
        this.listaDePeliculas = new MyLinkedListImpl<>();
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

    public MyList<Pelicula> getListaDePeliculas() {
        return listaDePeliculas;
    }

    public void setListaDePeliculas(MyList<Pelicula> listaDePeliculas) {
        this.listaDePeliculas = listaDePeliculas;
    }

    public void agregarPelicula(Pelicula tempPeli){
        listaDePeliculas.add(tempPeli);
    }

    public int getCantidadPeliculas(){
        return listaDePeliculas.size();
    }
}
