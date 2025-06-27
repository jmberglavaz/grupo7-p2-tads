package um.edu.uy.Sistema.Consultas;

import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.HeapKT.HeapNode;
import um.edu.uy.TADs.HeapKT.MyHeapKT;
import um.edu.uy.TADs.HeapKT.MyHeapKTImplementation;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.entities.Coleccion;
import um.edu.uy.entities.Pelicula;
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
            System.out.println(topCollection.getId() + ", " + topCollection.getTitle() + ", " + topCollection.getMovieCount() + ", [" + movieIds.toString() + "]" + totalRevenue);
        }
        System.out.println("Tiempo de ejecucion de la consulta: " + (System.currentTimeMillis() - startTime));
    }
}
