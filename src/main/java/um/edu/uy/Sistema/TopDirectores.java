package um.edu.uy.Sistema;

import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.Heap.MyHeap;
import um.edu.uy.TADs.Heap.MyHeapImpl;
import um.edu.uy.TADs.HeapKT.MyHeapKT;
import um.edu.uy.TADs.HeapKT.MyHeapKTImplementation;
import um.edu.uy.entities.Director;

public class TopDirectores {

    public static void realizarConsulta(MyHash<String, Director> listaDeDirectores){
        long inicio = System.currentTimeMillis();
        MyHeap<Director> resultadoDirectores = new MyHeapImpl<>(listaDeDirectores.size(),false);
        for (Director tempDirector : listaDeDirectores){
            if (tempDirector.getCantidadPeliculas() <= 1 || tempDirector.getCantidadEvaluaciones() < 100){continue;}
            resultadoDirectores.insert(tempDirector);
        }

        System.out.println("Top 10 Directores con mejor calificacion");
        for (int iter  = 1; iter <= 10 ; iter++){
            Director tempDirector = resultadoDirectores.deleteAndObtain();
            System.out.println("Top " + iter + ": " + tempDirector.getNombre() + " " + tempDirector.getCantidadEvaluaciones() + " " + tempDirector.obtainMediana());
        }
        System.out.println("Tiempo total de consulta: " + (System.currentTimeMillis() - inicio) + "ms");
    }
}
