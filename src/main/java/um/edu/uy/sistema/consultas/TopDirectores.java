package um.edu.uy.sistema.consultas;

import um.edu.uy.adt.hash.MyHash;
import um.edu.uy.adt.heap.MyHeap;
import um.edu.uy.adt.heap.MyHeapImpl;
import um.edu.uy.adt.list.MyList;
import um.edu.uy.entidades.Director;

/**
 * Consulta que muestra el top 10 de directores con mejor calificación mediana.
 * Solo considera directores con más de una película y al menos 100 calificaciones.
 */
public class TopDirectores {
    /**
     * Ejecuta la consulta y muestra los directores top según la mediana de calificaciones.
     * @param directorHash Hash con todos los directores del sistema.
     */
    public static void realizarConsulta(MyHash<String, Director> directorHash) {
        long startTime = System.currentTimeMillis();
        MyList<Director> directorList = directorHash.getValues();
        MyHeap<Director> directorHeap = new MyHeapImpl<>(directorList.size(), false);

        // Inserta en el heap solo los directores que cumplen los requisitos
        for (Director currentDirector : directorList) {
            if (currentDirector.getMovieCount() > 1 && currentDirector.getTotalReviewCount() >= 100) {
                directorHeap.insert(currentDirector);
            }
        }

        // Imprime los 10 mejores directores según la mediana de calificaciones
        for (int i = 0; i < 10 && directorHeap.size() > 0; i++) {
            Director topDirector = directorHeap.deleteAndObtain();
            // Formato de salida: <nombre_director>, <cantidad_peliculas>, <mediana_calificacion>
            System.out.println(topDirector.getName() + ", " + topDirector.getMovieCount() + ", " + topDirector.getRatingMedian());
        }
        System.out.println("Tiempo de ejecución de la consulta: " + (System.currentTimeMillis() - startTime));
    }
}
