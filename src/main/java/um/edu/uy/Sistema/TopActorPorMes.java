package um.edu.uy.Sistema;

import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.HeapKT.HeapNode;
import um.edu.uy.TADs.HeapKT.MyHeapKT;
import um.edu.uy.TADs.HeapKT.MyHeapKTImplementation;
import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Pelicula;

public class TopActorPorMes {

    public static void realizarConsulta(MyHash<Integer, Pelicula> listaPeliculas, MyHash<String, Actor> listaActores) {
        MyHeapKT<Integer, Actor> actoresPorRatingEnero = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingFebrero = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingMarzo = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingAbril = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingMayo = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingJunio = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingJulio = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingAgosto = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingSetiembre = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingOctubre = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingNoviembre = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
        MyHeapKT<Integer, Actor> actoresPorRatingDiciembre = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);

        for (int iter = 0; iter < listaActores.size(); iter++) {
            // Agrego el actor a cada heap de mes usando la cantidad de evaluaciones en el mes como key
            if (listaActores.get(String.valueOf(iter)) != null) {
                actoresPorRatingEnero.insert(listaActores.get(String.valueOf(iter)).getCantidadEvaluacionesActorPorMes(1), listaActores.get(String.valueOf(iter)));
                actoresPorRatingFebrero.insert(listaActores.get(String.valueOf(iter)).getCantidadEvaluacionesActorPorMes(2), listaActores.get(String.valueOf(iter)));
                actoresPorRatingMarzo.insert(listaActores.get(String.valueOf(iter)).getCantidadEvaluacionesActorPorMes(3), listaActores.get(String.valueOf(iter)));
                actoresPorRatingAbril.insert(listaActores.get(String.valueOf(iter)).getCantidadEvaluacionesActorPorMes(4), listaActores.get(String.valueOf(iter)));
                actoresPorRatingMayo.insert(listaActores.get(String.valueOf(iter)).getCantidadEvaluacionesActorPorMes(5), listaActores.get(String.valueOf(iter)));
                actoresPorRatingJunio.insert(listaActores.get(String.valueOf(iter)).getCantidadEvaluacionesActorPorMes(6), listaActores.get(String.valueOf(iter)));
                actoresPorRatingJulio.insert(listaActores.get(String.valueOf(iter)).getCantidadEvaluacionesActorPorMes(7), listaActores.get(String.valueOf(iter)));
                actoresPorRatingAgosto.insert(listaActores.get(String.valueOf(iter)).getCantidadEvaluacionesActorPorMes(8), listaActores.get(String.valueOf(iter)));
                actoresPorRatingSetiembre.insert(listaActores.get(String.valueOf(iter)).getCantidadEvaluacionesActorPorMes(9), listaActores.get(String.valueOf(iter)));
                actoresPorRatingOctubre.insert(listaActores.get(String.valueOf(iter)).getCantidadEvaluacionesActorPorMes(10), listaActores.get(String.valueOf(iter)));
                actoresPorRatingNoviembre.insert(listaActores.get(String.valueOf(iter)).getCantidadEvaluacionesActorPorMes(11), listaActores.get(String.valueOf(iter)));
                actoresPorRatingDiciembre.insert(listaActores.get(String.valueOf(iter)).getCantidadEvaluacionesActorPorMes(12), listaActores.get(String.valueOf(iter)));
            }
        }
        HeapNode<Integer, Actor> actorEne = actoresPorRatingEnero.deleteAndObtainNode();
        HeapNode<Integer, Actor> actorFeb = actoresPorRatingFebrero.deleteAndObtainNode();
        HeapNode<Integer, Actor> actorMar = actoresPorRatingMarzo.deleteAndObtainNode();
        HeapNode<Integer, Actor> actorAbr = actoresPorRatingAbril.deleteAndObtainNode();
        HeapNode<Integer, Actor> actorMay = actoresPorRatingMayo.deleteAndObtainNode();
        HeapNode<Integer, Actor> actorJun = actoresPorRatingJunio.deleteAndObtainNode();
        HeapNode<Integer, Actor> actorJul = actoresPorRatingJulio.deleteAndObtainNode();
        HeapNode<Integer, Actor> actorAgo = actoresPorRatingAgosto.deleteAndObtainNode();
        HeapNode<Integer, Actor> actorSet = actoresPorRatingSetiembre.deleteAndObtainNode();
        HeapNode<Integer, Actor> actorOct = actoresPorRatingOctubre.deleteAndObtainNode();
        HeapNode<Integer, Actor> actorNov = actoresPorRatingNoviembre.deleteAndObtainNode();
        HeapNode<Integer, Actor> actorDic = actoresPorRatingDiciembre.deleteAndObtainNode();

        System.out.println("Enero: " + imprimirActor(actorEne));
        System.out.println("Feb: " + imprimirActor(actorFeb));
        System.out.println("Marzo: " + imprimirActor(actorMar));
        System.out.println("Abril: " + imprimirActor(actorAbr));
        System.out.println("Mayo: " + imprimirActor(actorMay));
        System.out.println("Junio: " + imprimirActor(actorJun));
        System.out.println("Julio: " + imprimirActor(actorJul));
        System.out.println("Agosto: " + imprimirActor(actorAgo));
        System.out.println("Setiembre: " + imprimirActor(actorSet));
        System.out.println("Octubre: " + imprimirActor(actorOct));
        System.out.println("Noviembre: " + imprimirActor(actorNov));
        System.out.println("Diciembre: " + imprimirActor(actorDic));
    }

    private static String imprimirActor(HeapNode<Integer, Actor> actorMes) {
        if (actorMes != null) {
            return actorMes.getData().getNombre() + ", " + actorMes.getData().getCantidadPeliculasActor() + ", " + actorMes.getKey();
        }
        return "No hay actores en este mes";
    }


}
