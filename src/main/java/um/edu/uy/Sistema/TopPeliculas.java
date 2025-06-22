package um.edu.uy.Sistema;

import um.edu.uy.TADs.Hash.HashNode;
import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.Tree.SimpleBinaryNode;
import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.entities.UMovie;

public class TopPeliculas {
    public TopPeliculas(UMovie uMovie) {
        this.uMovie = uMovie;
        imprimirTop10();
    }

    UMovie uMovie = new UMovie();

    private MyList<SimpleBinaryNode<Float, Pelicula>> procesarEvaluaciones() {
        MyList<SimpleBinaryNode<Float, Pelicula>> peliculasPorCalificacion = new MyLinkedListImpl<>(); // me gustaría hacer que me queden solo las 10 con mayor rating e ir eliminando las otras, xq sino re larga la lista.
        // lo voy a hacer con un bubbleSort dentro del método por ahora xq no tengo del todo pronto el práctico de sorting todavía, pero esto tendría que ser una lista ordenada
        for (int counter = 0; counter < uMovie.cantPeliculas(); counter++) {
            MyHash<Integer, Pelicula> catalogo = uMovie.getCatalogoDePeliculas();
            HashNode<Integer, Pelicula> nodo = (HashNode<Integer, Pelicula>) catalogo;
            if (nodo != null && nodo.getData() != null && nodo.getKey() != null) {
                Pelicula pelicula = nodo.getData();
                SimpleBinaryNode<Float, Pelicula> peliculaConCalificacion = new SimpleBinaryNode<>(pelicula.getPromedioDeEvaluaciones(), pelicula);
                peliculasPorCalificacion.add(peliculaConCalificacion);
            }
        }
        return peliculasPorCalificacion;
    }

    private MyList<SimpleBinaryNode<Float, Pelicula>> bubbleSort(MyList<SimpleBinaryNode<Float, Pelicula>> listaAOrdenar) {
        int nroPasada = 0;
        while (nroPasada < listaAOrdenar.size()) {
            for (int indice = 0; indice < (listaAOrdenar.size() - nroPasada - 1); indice++) {
                if (listaAOrdenar.get(indice + 1).getKey() < listaAOrdenar.get(indice).getKey()) {
                    listaAOrdenar.intercambiate(indice, indice + 1);
                }
            }
            nroPasada++;
        }
        return listaAOrdenar;
    }

    private void imprimirTop10(){
        int cantPeliculas = uMovie.cantPeliculas();
        int puesto = 0;
        for (int indice = cantPeliculas - 10; indice < cantPeliculas; indice++) {
            SimpleBinaryNode<Float, Pelicula> topPuesto = procesarEvaluaciones().get(indice);
            System.out.println("\n Puesto " + puesto + ": \n Título: " + topPuesto.getData().getTitulo() + "\n Calificación media: " + topPuesto.getData().getPromedioDeEvaluaciones());
        }
    }
}
