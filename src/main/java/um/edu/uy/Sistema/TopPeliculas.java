package um.edu.uy.Sistema;

import um.edu.uy.Exceptions.EmptyHeapException;
import um.edu.uy.TADs.Hash.HashNode;
import um.edu.uy.TADs.HeapKT.HeapNode;
import um.edu.uy.TADs.HeapKT.MyHeapKT;
import um.edu.uy.TADs.HeapKT.MyHeapKTImplementation;
import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.Tree.SimpleBinaryNode;
import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.entities.UMovie;

public class TopPeliculas {
    // Top 10 de las películas que mejor calificación media tienen por parte de los usuarios, considerando solo las películas con mas de 100 calificaciones

    public static void realizarConsulta(MyHash<Integer, Pelicula> listaPeliculas) {
        MyHeapKT<Float, Pelicula> peliculasPorCalificacionMedia = new MyHeapKTImplementation<>(listaPeliculas.size(), false);

        // Agrego películas:
        for (Pelicula pelicula : listaPeliculas) {
            if (pelicula != null) {
                if (pelicula.getCantidadEvaluaciones() > 100) {
                    // agrego al heap con promedio de evaluaciones como clave
                    peliculasPorCalificacionMedia.insert(pelicula.getPromedioDeEvaluaciones(), pelicula);
                }
            }
        }
        // Saco las 10 más altas del heap:
        System.out.println("\nTop 10 de las películas que mejor calificación media tienen por parte de los usuarios");
        System.out.println("Top 1: " + imprimirPeliculaConTop(peliculasPorCalificacionMedia.deleteAndObtainNode()));
        System.out.println("Top 2: " + imprimirPeliculaConTop(peliculasPorCalificacionMedia.deleteAndObtainNode()));
        System.out.println("Top 3: " + imprimirPeliculaConTop(peliculasPorCalificacionMedia.deleteAndObtainNode()));
        System.out.println("Top 4: " + imprimirPeliculaConTop(peliculasPorCalificacionMedia.deleteAndObtainNode()));
        System.out.println("Top 5: " + imprimirPeliculaConTop(peliculasPorCalificacionMedia.deleteAndObtainNode()));
        System.out.println("Top 6: " + imprimirPeliculaConTop(peliculasPorCalificacionMedia.deleteAndObtainNode()));
        System.out.println("Top 7: " + imprimirPeliculaConTop(peliculasPorCalificacionMedia.deleteAndObtainNode()));
        System.out.println("Top 8: " + imprimirPeliculaConTop(peliculasPorCalificacionMedia.deleteAndObtainNode()));
        System.out.println("Top 9: " + imprimirPeliculaConTop(peliculasPorCalificacionMedia.deleteAndObtainNode()));
        System.out.println("Top 10: " + imprimirPeliculaConTop(peliculasPorCalificacionMedia.deleteAndObtainNode()) + "\n");
    }

    private static String imprimirPeliculaConTop(HeapNode<Float, Pelicula> peliculaConCalificacion) {
        Pelicula pelicula = peliculaConCalificacion.getData();
        Float promedioPelicula = peliculaConCalificacion.getKey();
        return pelicula.getId() + ", " + pelicula.getTitulo() + ", " + promedioPelicula;
        //<id_pelicula>, <titulo_pelicula>, <calificacion_media>

    }
}
