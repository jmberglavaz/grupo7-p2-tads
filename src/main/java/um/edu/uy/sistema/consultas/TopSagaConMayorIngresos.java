package um.edu.uy.sistema.consultas;

import um.edu.uy.adt.hash.MyHash;
import um.edu.uy.adt.heapKT.HeapNode;
import um.edu.uy.adt.heapKT.MyHeapKT;
import um.edu.uy.adt.heapKT.MyHeapKTImplementation;
import um.edu.uy.adt.list.MyList;
import um.edu.uy.entidades.Coleccion;
import um.edu.uy.entidades.Pelicula;
import java.util.StringJoiner;

/**
 * Consulta que muestra el top 5 de colecciones (sagas) con mayor ingreso total.
 * Suma los ingresos de todas las películas de cada colección.
 */
public class TopSagaConMayorIngresos {
    /**
     * Ejecuta la consulta y muestra las colecciones top según el ingreso total.
     * @param collectionHash Hash con todas las colecciones del sistema.
     */
    public static void realizarConsulta(MyHash<Integer, Coleccion> collectionHash) {
        long startTime = System.currentTimeMillis();
        MyList<Coleccion> collectionList = collectionHash.getValues();
        MyHeapKT<Long, Coleccion> collectionsByRevenue = new MyHeapKTImplementation<>(collectionList.size(), false);

        // Calcula el ingreso total de cada colección y la inserta en el heap
        for (Coleccion currentCollection : collectionList) {
            long collectionRevenue = 0;
            for (Pelicula movie : currentCollection.getMovieList()) {
                if (movie != null) collectionRevenue += movie.getRevenue();
            }
            if (collectionRevenue > 0) collectionsByRevenue.insert(collectionRevenue, currentCollection);
        }

        // Imprime las 5 colecciones con más ingresos
        for (int top = 1; top <= 5 && collectionsByRevenue.size() > 0; top++) {
            HeapNode<Long, Coleccion> node = collectionsByRevenue.deleteAndObtainNode();
            Coleccion topCollection = node.getData();
            long totalRevenue = node.getKey();

            StringJoiner movieIds = new StringJoiner(",");
            for (int i = 0; i < topCollection.getMovieCount(); i++) {
                movieIds.add(String.valueOf(topCollection.getMovieList().get(i).getId()));
            }
            System.out.println(topCollection.getId() + ", " + topCollection.getTitle() + ", " + topCollection.getMovieCount() + ", [" + movieIds.toString() + "], " + totalRevenue);
        }
        System.out.println("Tiempo de ejecución de la consulta: " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
