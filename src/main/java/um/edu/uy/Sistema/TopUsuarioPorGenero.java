package um.edu.uy.Sistema;

import um.edu.uy.Exceptions.ElementAlreadyExist;
import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.Hash.MyHashImplCloseLineal;
import um.edu.uy.TADs.HeapKT.MyHeapKT;
import um.edu.uy.TADs.HeapKT.MyHeapKTImplementation;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Genero;
import um.edu.uy.entities.Pelicula;

public class TopUsuarioPorGenero {

    public static void realizarConsulta(MyHash<Integer, Genero> listaDeGeneros){
        long inicio = System.currentTimeMillis();
        MyHeapKT<Integer, Genero> tempHeap = new MyHeapKTImplementation<>(listaDeGeneros.size(), false);

        for (Genero generoActual : listaDeGeneros){
            int totalEvaluacionesGenero = 0;
            for (Pelicula peli : generoActual.getListaPeliculas()){
                totalEvaluacionesGenero += peli.getCantidadEvaluaciones();
            }
            if (totalEvaluacionesGenero > 0) {
                tempHeap.insert(totalEvaluacionesGenero, generoActual);
            }
        }

        System.out.println("Usuarios con más calificaciones por género: ");
        MyHash<Integer, Integer> conteoUsuarios = new MyHashImplCloseLineal<>(83);

        for (int iter = 1 ; iter <= 10 ; iter++){
            if (tempHeap.size() == 0) {
                break;
            }
            Genero tempGenero = tempHeap.deleteAndObtain();

            int[] usuarioTop = {-1,0};

            for (Pelicula pelicula : tempGenero.getListaPeliculas()) {
                for (int mesIndex = 0; mesIndex < 12; mesIndex++) {
                    MyList<Evaluacion> evaluacionesDelMes = pelicula.getListaEvaluacionesEnMes(mesIndex + 1);

                    if (evaluacionesDelMes != null) {
                        for (Evaluacion evaluacion : evaluacionesDelMes) {
                            int userId = evaluacion.getIdUsuario();

                            int nuevoConteo;
                            Integer conteoActualObj = conteoUsuarios.get(userId);

                            if (conteoActualObj == null) {
                                nuevoConteo = 1;
                                try {
                                    conteoUsuarios.insert(userId, 1);
                                } catch (ElementAlreadyExist ignored) {}
                            } else {
                                nuevoConteo = conteoActualObj + 1;
                                conteoUsuarios.changeValue(userId, nuevoConteo);
                            }

                            if (nuevoConteo > usuarioTop[1]) {
                                usuarioTop[1] = nuevoConteo;
                                usuarioTop[0] = userId;
                            }
                        }
                    }
                }
            }
            System.out.println(usuarioTop[0] + ", " + tempGenero.getNombre() + ", " + usuarioTop[1]);
            conteoUsuarios.clean();
        }
        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + "ms");
    }
}
