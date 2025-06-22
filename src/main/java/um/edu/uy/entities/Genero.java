package um.edu.uy.entities;

import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.Hash.MyHashImplCloseLineal;
import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.List.MyList;

public class Genero {
    private int id;
    private String nombre;
    private MyList<Pelicula> listaPeliculas;

    public Genero(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.listaPeliculas = new MyLinkedListImpl<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setListaPeliculas(MyList<Pelicula> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
    }

    public void agregarPelicula(Pelicula tempPeli) {
        listaPeliculas.add(tempPeli);
    }

    public int cantEvaluaciones(){
        int cant = 0;
        for (Pelicula peliActual : listaPeliculas){
            cant += peliActual.getCantidadEvaluaciones();
        }
        return cant;
    }

    public int[] topUsuario() {
        MyHash<Integer, Integer> conteoUsuarios = new MyHashImplCloseLineal<>(1000); // Tamaño inicial flexible

        int[] usuarioTop = {-1,0};

        for (Pelicula pelicula : listaPeliculas) {
            for (Evaluacion evaluacion : pelicula.getListaEvaluaciones()) {
                int userId = evaluacion.getIdUsuario();

                Integer conteoActual = conteoUsuarios.get(userId);
                int nuevoConteo;
                if (conteoActual == null){
                    nuevoConteo = 1;
                    conteoUsuarios.insert(userId, nuevoConteo);
                } else {
                    nuevoConteo = conteoActual + 1;
                    conteoUsuarios.changeValue(userId, nuevoConteo);
                }

                if (nuevoConteo > usuarioTop[1]) {
                    usuarioTop[1] = nuevoConteo;
                    usuarioTop[0] = userId;
                }
            }
        }
        return usuarioTop;
    }
}
