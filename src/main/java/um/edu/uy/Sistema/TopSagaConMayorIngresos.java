package um.edu.uy.Sistema;

import um.edu.uy.Exceptions.EmptyHeapException;
import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.HeapKT.HeapNode;
import um.edu.uy.TADs.HeapKT.MyHeapKT;
import um.edu.uy.TADs.HeapKT.MyHeapKTImplementation;
import um.edu.uy.entities.Coleccion;
import um.edu.uy.entities.Pelicula;

public class TopSagaConMayorIngresos {

    public static void realizarConsulta(MyHash<Integer, Pelicula> listaPeliculas, MyHash<Integer, Coleccion> listaColecciones){
        MyHeapKT<Long, Coleccion> coleccionesPorIngresos = new MyHeapKTImplementation<>(listaColecciones.size(), false); // cambiar a <K, T>

        for (int iter = 0; iter < listaColecciones.size(); iter++) { // no sé si es size ak
            if (!(listaColecciones.obtain(iter) == null)) {
                Coleccion coleccion = listaColecciones.obtain(iter);
                long ingresosColeccion = 0;

                for (Pelicula pelicula : coleccion.getListaDePeliculas()) {
                    if (pelicula != null) {
                        ingresosColeccion += pelicula.getIngresos();
                    }
                }

                coleccionesPorIngresos.insert(ingresosColeccion, coleccion);
            }
        }

        // Imprimir top 5:
        try {
            for (int top = 1; top <= 5; top++) {
                HeapNode<Long, Coleccion> coleccionConIngreso = coleccionesPorIngresos.deleteAndObtainNode();
                Coleccion coleccionTop = coleccionConIngreso.getData();
                long ingresosColeccion = coleccionConIngreso.getKey();
                System.out.println("\n\n Top " + top + "\n     ID de la Colección: " + coleccionTop.getId() + "\n     Título de la Colección: " + coleccionTop.getTitulo() + "\n     Cantidad de películas: " + coleccionTop.getCantidadPeliculas() + "\n     Películas que la componen: " +  coleccionTop.getListaDePeliculas() + "\n     Ingresos generados: " + ingresosColeccion);
            }
        } catch (EmptyHeapException e) {
            System.err.println("Error en el heap: elemento vacío");
        }
    }
//    private static void imprimirListaDePeliculas(MyList<?> listaPeliculas) {
//        for (idPelicula : listaPeliculas) {
//            System.out.println(idPelicula);
//        }
//    }

}
