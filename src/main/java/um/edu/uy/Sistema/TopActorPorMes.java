package um.edu.uy.Sistema;

import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.HeapKT.MyHeapKT;
import um.edu.uy.TADs.HeapKT.MyHeapKTImplementation;
import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Pelicula;

public class TopActorPorMes {
    public void realizarConsulta(MyHash<Integer, Pelicula> listaPeliculas, MyHash<Integer, Actor> listaActores){
        MyHeapKT<Integer, Actor> actoresPorRatingEnero = new MyHeapKTImplementation<>(listaPeliculas.size()/12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingFebrero = new MyHeapKTImplementation<>(listaPeliculas.size()/12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingMarzo = new MyHeapKTImplementation<>(listaPeliculas.size()/12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingAbril = new MyHeapKTImplementation<>(listaPeliculas.size()/12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingMayo = new MyHeapKTImplementation<>(listaPeliculas.size()/12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingJunio = new MyHeapKTImplementation<>(listaPeliculas.size()/12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingJulio = new MyHeapKTImplementation<>(listaPeliculas.size()/12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingAgosto = new MyHeapKTImplementation<>(listaPeliculas.size()/12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingSetiembre = new MyHeapKTImplementation<>(listaPeliculas.size()/12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingOctubre = new MyHeapKTImplementation<>(listaPeliculas.size()/12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingNoviembre = new MyHeapKTImplementation<>(listaPeliculas.size()/12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingDiciembre = new MyHeapKTImplementation<>(listaPeliculas.size()/12, false);

        for (int iter = 0; iter < listaActores.size(); iter++) {
            // Agrego el actor a cada heap de mes usando la cantidad de evaluaciones en el mes como key
            actoresPorRatingEnero.insert(listaActores.get(iter).getCantidadEvaluacionesActorPorMes(1), listaActores.get(iter));
            actoresPorRatingFebrero.insert(listaActores.get(iter).getCantidadEvaluacionesActorPorMes(2), listaActores.get(iter));
            actoresPorRatingMarzo.insert(listaActores.get(iter).getCantidadEvaluacionesActorPorMes(3), listaActores.get(iter));
            actoresPorRatingAbril.insert(listaActores.get(iter).getCantidadEvaluacionesActorPorMes(4), listaActores.get(iter));
            actoresPorRatingMayo.insert(listaActores.get(iter).getCantidadEvaluacionesActorPorMes(5), listaActores.get(iter));
            actoresPorRatingJunio.insert(listaActores.get(iter).getCantidadEvaluacionesActorPorMes(6), listaActores.get(iter));
            actoresPorRatingJulio.insert(listaActores.get(iter).getCantidadEvaluacionesActorPorMes(7), listaActores.get(iter));
            actoresPorRatingAgosto.insert(listaActores.get(iter).getCantidadEvaluacionesActorPorMes(8), listaActores.get(iter));
            actoresPorRatingSetiembre.insert(listaActores.get(iter).getCantidadEvaluacionesActorPorMes(9), listaActores.get(iter));
            actoresPorRatingOctubre.insert(listaActores.get(iter).getCantidadEvaluacionesActorPorMes(10), listaActores.get(iter));
            actoresPorRatingNoviembre.insert(listaActores.get(iter).getCantidadEvaluacionesActorPorMes(11), listaActores.get(iter));
            actoresPorRatingDiciembre.insert(listaActores.get(iter).getCantidadEvaluacionesActorPorMes(12), listaActores.get(iter));
            }
        }
    }
