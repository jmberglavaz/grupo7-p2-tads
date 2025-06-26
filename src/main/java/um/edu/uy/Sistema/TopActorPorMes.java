package um.edu.uy.Sistema;

import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.HeapKT.HeapNode;
import um.edu.uy.TADs.HeapKT.MyHeapKT;
import um.edu.uy.TADs.HeapKT.MyHeapKTImplementation;
import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Pelicula;

public class TopActorPorMes {

//    public static void realizarConsulta(MyHash<Integer, Pelicula> listaPeliculas, MyHash<String, Actor> listaActores) {
//        long inicio = System.currentTimeMillis();
//
//        MyHeapKT<Integer, Actor> actoresPorRatingEnero = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
//        MyHeapKT<Integer, Actor> actoresPorRatingFebrero = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
//        MyHeapKT<Integer, Actor> actoresPorRatingMarzo = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
//        MyHeapKT<Integer, Actor> actoresPorRatingAbril = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
//        MyHeapKT<Integer, Actor> actoresPorRatingMayo = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
//        MyHeapKT<Integer, Actor> actoresPorRatingJunio = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
//        MyHeapKT<Integer, Actor> actoresPorRatingJulio = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
//        MyHeapKT<Integer, Actor> actoresPorRatingAgosto = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
//        MyHeapKT<Integer, Actor> actoresPorRatingSetiembre = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
//        MyHeapKT<Integer, Actor> actoresPorRatingOctubre = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
//        MyHeapKT<Integer, Actor> actoresPorRatingNoviembre = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
//        MyHeapKT<Integer, Actor> actoresPorRatingDiciembre = new MyHeapKTImplementation<>(listaPeliculas.size() / 12, false);
//
//        for (Actor actor : listaActores) {
//            // Agrego el actor a cada heap de mes usando la cantidad de evaluaciones en el mes como key
//            if (actor != null) {
//                actoresPorRatingEnero.insert(actor.getCantidadEvaluacionesActorPorMes(1), actor);
//                actoresPorRatingFebrero.insert(actor.getCantidadEvaluacionesActorPorMes(2), actor);
//                actoresPorRatingMarzo.insert(actor.getCantidadEvaluacionesActorPorMes(3), actor);
//                actoresPorRatingAbril.insert(actor.getCantidadEvaluacionesActorPorMes(4), actor);
//                actoresPorRatingMayo.insert(actor.getCantidadEvaluacionesActorPorMes(5), actor);
//                actoresPorRatingJunio.insert(actor.getCantidadEvaluacionesActorPorMes(6), actor);
//                actoresPorRatingJulio.insert(actor.getCantidadEvaluacionesActorPorMes(7), actor);
//                actoresPorRatingAgosto.insert(actor.getCantidadEvaluacionesActorPorMes(8), actor);
//                actoresPorRatingSetiembre.insert(actor.getCantidadEvaluacionesActorPorMes(9), actor);
//                actoresPorRatingOctubre.insert(actor.getCantidadEvaluacionesActorPorMes(10), actor);
//                actoresPorRatingNoviembre.insert(actor.getCantidadEvaluacionesActorPorMes(11), actor);
//                actoresPorRatingDiciembre.insert(actor.getCantidadEvaluacionesActorPorMes(12), actor);
//            }
//        }
//        HeapNode<Integer, Actor> actorEne = actoresPorRatingEnero.deleteAndObtainNode();
//        HeapNode<Integer, Actor> actorFeb = actoresPorRatingFebrero.deleteAndObtainNode();
//        HeapNode<Integer, Actor> actorMar = actoresPorRatingMarzo.deleteAndObtainNode();
//        HeapNode<Integer, Actor> actorAbr = actoresPorRatingAbril.deleteAndObtainNode();
//        HeapNode<Integer, Actor> actorMay = actoresPorRatingMayo.deleteAndObtainNode();
//        HeapNode<Integer, Actor> actorJun = actoresPorRatingJunio.deleteAndObtainNode();
//        HeapNode<Integer, Actor> actorJul = actoresPorRatingJulio.deleteAndObtainNode();
//        HeapNode<Integer, Actor> actorAgo = actoresPorRatingAgosto.deleteAndObtainNode();
//        HeapNode<Integer, Actor> actorSet = actoresPorRatingSetiembre.deleteAndObtainNode();
//        HeapNode<Integer, Actor> actorOct = actoresPorRatingOctubre.deleteAndObtainNode();
//        HeapNode<Integer, Actor> actorNov = actoresPorRatingNoviembre.deleteAndObtainNode();
//        HeapNode<Integer, Actor> actorDic = actoresPorRatingDiciembre.deleteAndObtainNode();
//
//        System.out.println("Enero: " + imprimirActor(actorEne));
//        System.out.println("Feb: " + imprimirActor(actorFeb));
//        System.out.println("Marzo: " + imprimirActor(actorMar));
//        System.out.println("Abril: " + imprimirActor(actorAbr));
//        System.out.println("Mayo: " + imprimirActor(actorMay));
//        System.out.println("Junio: " + imprimirActor(actorJun));
//        System.out.println("Julio: " + imprimirActor(actorJul));
//        System.out.println("Agosto: " + imprimirActor(actorAgo));
//        System.out.println("Setiembre: " + imprimirActor(actorSet));
//        System.out.println("Octubre: " + imprimirActor(actorOct));
//        System.out.println("Noviembre: " + imprimirActor(actorNov));
//        System.out.println("Diciembre: " + imprimirActor(actorDic));
//
//        System.out.println("Tiempo total de consulta: " + (System.currentTimeMillis() - inicio) + "ms");
//    }
//
//    private static String imprimirActor(HeapNode<Integer, Actor> actorMes) {
//        if (actorMes != null) {
//            return actorMes.getData().getNombre() + ", " + actorMes.getData().getCantidadPeliculasActor() + ", " + actorMes.getKey();
//        }
//        return "No hay actores en este mes";
//    }
//
//    public static void realizarConsultav2(MyHash<Integer, Pelicula> listaPeliculas, MyHash<String, Actor> listaActores) {
//        long inicio = System.currentTimeMillis();
//        Object[] actorEne = {null, 0, 0};
//        Object[] actorFeb = {null, 0, 0};
//        Object[] actorMar = {null, 0, 0};
//        Object[] actorAbr = {null, 0, 0};
//        Object[] actorMay = {null, 0, 0};
//        Object[] actorJun = {null, 0, 0};
//        Object[] actorJul = {null, 0, 0};
//        Object[] actorAgo = {null, 0, 0};
//        Object[] actorSet = {null, 0, 0};
//        Object[] actorOct = {null, 0, 0};
//        Object[] actorNov = {null, 0, 0};
//        Object[] actorDic = {null, 0, 0};
//        // [<nombre_actor>, <cant_peliculas>, <cant_de_calificaciones>
//
//
//        for (Actor actor : listaActores) {
//            // guardo solo el top por mes
//            if (actor != null) {
//                if (actorEne[0] == null || actor.getCantidadEvaluacionesActorPorMes(1) > (Integer) actorEne[2]) {
//                    actorEne[0] = actor.getNombre();
//                    actorEne[1] = actor.getCantidadPeliculasActor();
//                    actorEne[2] = actor.getCantidadEvaluacionesActorPorMes(1);
//                }
//                if (actorFeb[0] == null || actor.getCantidadEvaluacionesActorPorMes(2) > (Integer) actorFeb[2]) {
//                    actorFeb[0] = actor.getNombre();
//                    actorFeb[1] = actor.getCantidadPeliculasActor();
//                    actorFeb[2] = actor.getCantidadEvaluacionesActorPorMes(2);
//                }
//                if (actorMar[0] == null || actor.getCantidadEvaluacionesActorPorMes(3) > (Integer) actorMar[2])) {
//                    actorMar[0] = actor.getNombre();
//                    actorMar[1] = actor.getCantidadPeliculasActor();
//                    actorMar[2] = actor.getCantidadEvaluacionesActorPorMes(3);
//                }
//                if (actorAbr[0] == null || actor.getCantidadEvaluacionesActorPorMes(4) > (Integer) actorAbr[2]) {
//                    actorAbr[0] = actor.getNombre();
//                    actorAbr[1] = actor.getCantidadPeliculasActor();
//                    actorAbr[2] = actor.getCantidadEvaluacionesActorPorMes(4);
//                }
//                if (actorMay[0] == null || actor.getCantidadEvaluacionesActorPorMes(5) > (Integer) actorMay[2]) {
//                    actorMay[0] = actor.getNombre();
//                    actorMay[1] = actor.getCantidadPeliculasActor();
//                    actorMay[2] = actor.getCantidadEvaluacionesActorPorMes(5);
//                }
//                if (actorJun[0] == null || actor.getCantidadEvaluacionesActorPorMes(6) > (Integer) actorJun[2])) {
//                    actorJun[0] = actor.getNombre();
//                    actorJun[1] = actor.getCantidadPeliculasActor();
//                    actorJun[2] = actor.getCantidadEvaluacionesActorPorMes(6);
//                }
//                if (actorJul[0] == null || actor.getCantidadEvaluacionesActorPorMes(7) > (Integer) actorJul[2]) {
//                    actorJul[0] = actor.getNombre();
//                    actorJul[1] = actor.getCantidadPeliculasActor();
//                    actorJul[2] = actor.getCantidadEvaluacionesActorPorMes(7);
//                }
//                if (actorAgo[0] == null || actor.getCantidadEvaluacionesActorPorMes(8) > (Integer) actorAgo[2]) {
//                    actorAgo[0] = actor.getNombre();
//                    actorAgo[1] = actor.getCantidadPeliculasActor();
//                    actorAgo[2] = actor.getCantidadEvaluacionesActorPorMes(8);
//                }
//                if (actorSet[0] == null || actor.getCantidadEvaluacionesActorPorMes(9) > (Integer) actorSet[2])) {
//                    actorSet[0] = actor.getNombre();
//                    actorSet[1] = actor.getCantidadPeliculasActor();
//                    actorSet[2] = actor.getCantidadEvaluacionesActorPorMes(9);
//                }
//                if (actorOct[0] == null || actor.getCantidadEvaluacionesActorPorMes(10) > (Integer) actorOct[2]) {
//                    actorOct[0] = actor.getNombre();
//                    actorOct[1] = actor.getCantidadPeliculasActor();
//                    actorOct[2] = actor.getCantidadEvaluacionesActorPorMes(10);
//                }
//                if (actorNov[0] == null || actor.getCantidadEvaluacionesActorPorMes(11) > (Integer) actorNov[2]) {
//                    actorNov[0] = actor.getNombre();
//                    actorNov[1] = actor.getCantidadPeliculasActor();
//                    actorNov[2] = actor.getCantidadEvaluacionesActorPorMes(11);
//                }
//                if (actorDic[0] == null || actor.getCantidadEvaluacionesActorPorMes(12) > (Integer) actorDic[2])) {
//                    actorDic[0] = actor.getNombre();
//                    actorDic[1] = actor.getCantidadPeliculasActor();
//                    actorDic[2] = actor.getCantidadEvaluacionesActorPorMes(12);
//                }
//            }
//            }
//
//        System.out.println("Tiempo total de consulta: " + (System.currentTimeMillis() - inicio) + "ms");
//    }

    public static void realizarConsultav3(MyHash<Integer, Pelicula> listaPeliculas, MyHash<String, Actor> listaActores) {
        Actor[] actoresMayorCantEvaluaciones = new Actor[12];

        for (Actor actor : listaActores) {
            if (actor != null) {
                for (int mesActor = 1; mesActor <= 12; mesActor++) {
                    if (actoresMayorCantEvaluaciones[mesActor] == null) {
                        actoresMayorCantEvaluaciones[mesActor] = actor;
                    } else {
                        if (actoresMayorCantEvaluaciones[mesActor].compararCalificaciones(actor, mesActor) < 0) {
                            actoresMayorCantEvaluaciones[mesActor] = actor;
                        }
                    }
                }
            }
        }

        for (int mes = 1; mes < actoresMayorCantEvaluaciones.length; mes++) {
            System.out.println(imprimirActorv2(actoresMayorCantEvaluaciones[mes - 1], mes));
        }
    }
    private static String imprimirActorv2(Actor actor, int mes) {
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Setiembre", "Octubre", "Noviembre", "Diciembre"};
        return meses[mes-1] + ", " + actor.getNombre() + ", " + actor.getCantidadPeliculasActor() + ", " + actor.getCantidadEvaluacionesActorPorMes(mes);
    }

}
