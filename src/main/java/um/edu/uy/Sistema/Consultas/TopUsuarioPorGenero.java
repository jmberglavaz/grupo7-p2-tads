package um.edu.uy.Sistema.Consultas;

import um.edu.uy.Exceptions.ElementAlreadyExist;
import um.edu.uy.Exceptions.ValueNoExist;
import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.Hash.MyPrimitiveIntHash;
import um.edu.uy.TADs.Hash.MyPrimitiveIntHashImpl; // Asumimos que la implementación está en este archivo
import um.edu.uy.TADs.HeapKT.MyHeapKT;
import um.edu.uy.TADs.HeapKT.MyHeapKTImplementation;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Genero;
import um.edu.uy.entities.Pelicula;

public class TopUsuarioPorGenero {

    public static void realizarConsulta(MyHash<Integer, Genero> almacenDeGeneros) {
        long inicio = System.currentTimeMillis();
        MyList<Genero> listaDeGeneros = almacenDeGeneros.getValues();

        MyHeapKT<Integer, Genero> tempHeap = new MyHeapKTImplementation<>(listaDeGeneros.size(), false);


        for (Genero generoActual : listaDeGeneros) {
            if (generoActual != null) {
                int totalEvaluacionesGenero = generoActual.cantEvaluaciones();
                if (totalEvaluacionesGenero > 0) {
                    tempHeap.insert(totalEvaluacionesGenero, generoActual);
                }
            }
        }

        System.out.println("Usuarios con más calificaciones por género: ");


        MyPrimitiveIntHash conteoUsuarios = new MyPrimitiveIntHashImpl(83);

        for (int iter = 1; iter <= 10; iter++) {
            if (tempHeap.size() == 0) {
                break;
            }
            Genero tempGenero = tempHeap.deleteAndObtain();

            int[] usuarioTop = {-1, 0};

            for (Pelicula pelicula : tempGenero.getListaPeliculas()) {
                for (Evaluacion evaluacion : pelicula.getListaEvaluaciones()) {

                    int userId = evaluacion.getIdUsuario();
                    if (userId == 0) continue;

                    int nuevoConteo;
                    try {
                        int conteoActual = conteoUsuarios.get(userId);
                        nuevoConteo = conteoActual + 1;
                        conteoUsuarios.changeValue(userId, nuevoConteo);
                    } catch (ValueNoExist e) {
                        nuevoConteo = 1;
                        try {
                            conteoUsuarios.insert(userId, nuevoConteo);
                        } catch (ElementAlreadyExist exist) {
                            // No debería ocurrir
                        }
                    }

                    if (nuevoConteo > usuarioTop[1]) {
                        usuarioTop[1] = nuevoConteo;
                        usuarioTop[0] = userId;
                    }
                }
            }
            System.out.println(usuarioTop[0] + ", " + tempGenero.getNombre() + ", " + usuarioTop[1]);
            conteoUsuarios.clean(); // Limpiamos el mapa para el siguiente género
        }
        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + "ms");
    }
}