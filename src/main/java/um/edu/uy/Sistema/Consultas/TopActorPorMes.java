package um.edu.uy.Sistema.Consultas;

import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.HeapKT.HeapNode;
import um.edu.uy.TADs.HeapKT.MyHeapKT;
import um.edu.uy.TADs.HeapKT.MyHeapKTImplementation;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Pelicula;

public class TopActorPorMes {

    public static void realizarConsulta(MyHash<Integer, Pelicula> listaPeliculas, MyHash<Integer, Actor> almacenActores) {
        long inicio = System.currentTimeMillis();

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


        MyList<Actor> listaActores = almacenActores.getValues();
        for (Actor actor : listaActores) {
            // Agrego el actor a cada heap de mes usando la cantidad de evaluaciones en el mes como key
            if (actor != null) {
                actoresPorRatingEnero.insert(actor.getCantidadEvaluacionesActorPorMes(1), actor);
                actoresPorRatingFebrero.insert(actor.getCantidadEvaluacionesActorPorMes(2), actor);
                actoresPorRatingMarzo.insert(actor.getCantidadEvaluacionesActorPorMes(3), actor);
                actoresPorRatingAbril.insert(actor.getCantidadEvaluacionesActorPorMes(4), actor);
                actoresPorRatingMayo.insert(actor.getCantidadEvaluacionesActorPorMes(5), actor);
                actoresPorRatingJunio.insert(actor.getCantidadEvaluacionesActorPorMes(6), actor);
                actoresPorRatingJulio.insert(actor.getCantidadEvaluacionesActorPorMes(7), actor);
                actoresPorRatingAgosto.insert(actor.getCantidadEvaluacionesActorPorMes(8), actor);
                actoresPorRatingSetiembre.insert(actor.getCantidadEvaluacionesActorPorMes(9), actor);
                actoresPorRatingOctubre.insert(actor.getCantidadEvaluacionesActorPorMes(10), actor);
                actoresPorRatingNoviembre.insert(actor.getCantidadEvaluacionesActorPorMes(11), actor);
                actoresPorRatingDiciembre.insert(actor.getCantidadEvaluacionesActorPorMes(12), actor);
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

        System.out.println("Tiempo total de consulta: " + (System.currentTimeMillis() - inicio) + "ms");
    }

    private static String imprimirActor(HeapNode<Integer, Actor> actorMes) {
        if (actorMes != null) {
            return actorMes.getData().getNombre() + ", " + actorMes.getData().getCantidadPeliculasActor() + ", " + actorMes.getKey();
        }
        return "No hay actores en este mes";
    }


}
