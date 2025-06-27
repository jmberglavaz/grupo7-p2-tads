package um.edu.uy.sistema.consultas;

import um.edu.uy.adt.hash.MyHash;
import um.edu.uy.entidades.Actor;

/**
 * Consulta que determina el actor con más calificaciones recibidas en cada mes del año.
 * Recorre todos los actores y, para cada mes, identifica cuál recibió más evaluaciones.
 */
public class TopActorPorMes {
    // Nombres de los meses para mostrar en la salida
    private static final String[] MONTH_NAMES = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Setiembre", "Octubre", "Noviembre", "Diciembre"
    };

    /**
     * Ejecuta la consulta y muestra el actor con más calificaciones por cada mes.
     * @param actorHash Hash con todos los actores del sistema.
     */
    public static void realizarConsulta(MyHash<Integer, Actor> actorHash) {
        long startTime = System.currentTimeMillis();

        // Para cada mes, busca el actor con más calificaciones
        for (int month = 1; month <= 12; month++) {
            Actor topActorOfMonth = null;
            int maxRatings = -1;
            int movieCountForTopActor = 0;

            // Recorre todos los actores y obtiene sus estadísticas para el mes actual
            for (Actor currentActor : actorHash) {
                if (currentActor != null) {
                    int[] stats = currentActor.getStatsForMonth(month);
                    int currentRatings = stats[0];

                    if (currentRatings > maxRatings) {
                        maxRatings = currentRatings;
                        topActorOfMonth = currentActor;
                        movieCountForTopActor = stats[1];
                    }
                }
            }
            String monthName = MONTH_NAMES[month - 1];
            // Imprime el actor top del mes si hay alguno con calificaciones
            if (topActorOfMonth != null && maxRatings > 0) {
                System.out.println(monthName + ", " + topActorOfMonth.getName() + ", " + movieCountForTopActor + ", " + maxRatings);
            }
        }
        System.out.println("Tiempo de ejecución de la consulta: " + (System.currentTimeMillis() - startTime));
    }
}
