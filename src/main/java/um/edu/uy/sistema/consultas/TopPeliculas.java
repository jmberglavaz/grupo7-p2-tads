package um.edu.uy.sistema.consultas;

import um.edu.uy.adt.heapKT.HeapNode;
import um.edu.uy.adt.heapKT.MyHeapKT;
import um.edu.uy.adt.heapKT.MyHeapKTImplementation;
import um.edu.uy.adt.hash.MyHash;
import um.edu.uy.adt.list.MyList;
import um.edu.uy.entidades.Pelicula;

/**
 * Consulta que muestra el top 10 de películas con mejor calificación media.
 * Solo considera películas con más de 100 calificaciones.
 */
public class TopPeliculas {
    /**
     * Ejecuta la consulta y muestra las películas top según el promedio de calificaciones.
     * @param movieHash Hash con todas las películas del sistema.
     */
    public static void realizarConsulta(MyHash<Integer, Pelicula> movieHash) {
        long startTime = System.currentTimeMillis();
        MyList<Pelicula> movieList = movieHash.getValues();
        MyHeapKT<Float, Pelicula> moviesByAverageRating = new MyHeapKTImplementation<>(movieList.size(), false);

        // Inserta en el heap solo las películas que cumplen los requisitos
        for (Pelicula movie : movieList) {
            if (movie != null && movie.getTotalReviewCount() > 100) {
                moviesByAverageRating.insert(movie.getAverageRating(), movie);
            }
        }

        // Imprime las 10 mejores películas según el promedio de calificaciones
        for (int i = 0; i < 10 && moviesByAverageRating.size() > 0; i++) {
            HeapNode<Float, Pelicula> movieNode = moviesByAverageRating.deleteAndObtainNode();
            Pelicula movie = movieNode.getData();
            Float averageRating = movieNode.getKey();
            System.out.println(movie.getId() + ", " + movie.getTitle() + ", " + averageRating);
        }
        System.out.println("Tiempo de ejecución de la consulta: " + (System.currentTimeMillis() - startTime));
    }
}
