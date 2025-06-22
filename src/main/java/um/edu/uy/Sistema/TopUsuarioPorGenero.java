package um.edu.uy.Sistema;

import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.HeapKT.MyHeapKT;
import um.edu.uy.TADs.HeapKT.MyHeapKTImplementation;
import um.edu.uy.entities.Genero;

public class TopUsuarioPorGenero {

    public static void realizarConsulta(MyHash<Integer, Genero> listaDeGeneros){
        MyHeapKT<Integer, Genero> tempHeap = new MyHeapKTImplementation<>(listaDeGeneros.size(), false);
        for (Genero generoActual : listaDeGeneros){
            tempHeap.insert(generoActual.cantEvaluaciones(), generoActual);
        }

        System.out.println("Usuarios con más calificaciones por género: ");
        for (int iter = 1 ; iter <= 10 ; iter++){
            Genero tempGenero = tempHeap.deleteAndObtain();
            int[] topUsuario = tempGenero.topUsuario();
            System.out.println(topUsuario[0] + " " + tempGenero.getNombre() + " " + topUsuario[1]);
        }


    }

}
