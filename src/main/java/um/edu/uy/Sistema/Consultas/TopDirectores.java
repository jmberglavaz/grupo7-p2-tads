package um.edu.uy.Sistema.Consultas;

import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.Heap.MyHeap;
import um.edu.uy.TADs.Heap.MyHeapImpl;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.entities.Director;

public class TopDirectores {

    public static void realizarConsulta(MyHash<String, Director> almacenDeDirectores){
        long inicio = System.currentTimeMillis();
        MyList<Director> listaDeDirectores = almacenDeDirectores.getValues();
        MyHeap<Director> resultadoDirectores = new MyHeapImpl<>(listaDeDirectores.size(),false);

        for (Director tempDirector : listaDeDirectores){
            if (tempDirector.getCantidadPeliculas() <= 1 || tempDirector.getCantidadEvaluaciones() < 100){continue;}
            resultadoDirectores.insert(tempDirector);
        }

        System.out.println("Top 10 Directores con mejor calificacion");
        for (int iter  = 1; iter <= 10 ; iter++){
            Director tempDirector = resultadoDirectores.deleteAndObtain();
            System.out.println("Top " + iter + ": " + tempDirector.getNombre() + " " + tempDirector.getCantidadPeliculas() + " " + tempDirector.obtainMediana());
        }
        System.out.println("Tiempo total de consulta: " + (System.currentTimeMillis() - inicio) + "ms");
    }
}
