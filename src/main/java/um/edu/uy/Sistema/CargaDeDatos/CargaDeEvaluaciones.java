package um.edu.uy.Sistema.CargaDeDatos;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Pelicula;

import java.io.*;
import java.util.Date;

public class CargaDeEvaluaciones {
    private CSVReader lectorCSV;
    private String[] lineaDatos;
    // Indica si se debe mostrar información adicional para desarrolladores
    private final boolean DeveloperMode;

    /**
     * Constructor. Inicializa el lector CSV para el archivo de evaluaciones.
     * @param developerMode Si es true, imprime estadísticas y detalles adicionales.
     */
    public CargaDeEvaluaciones(boolean developerMode) {
        this.DeveloperMode = developerMode;
        try{
            // Se abre el archivo de evaluaciones y se inicializa el lector CSV
            FileInputStream archivoCSV = new FileInputStream("ratings_1mm.csv");
            this.lectorCSV = new CSVReader(new InputStreamReader(archivoCSV));
            this.lineaDatos = lectorCSV.readNext(); // Se descarta la cabecera
        } catch (IOException | CsvValidationException ignored) {
            System.out.println("Error crítico al cargar el archivo de evaluaciones. Asegúrese de que el archivo ratings_1mm.csv se encuentre en la carpeta resources del proyecto.");
        }
    }

    /**
     * Carga los datos de evaluaciones desde el archivo CSV y los asocia a las películas.
     * @param peliculas Hash de películas existentes, indexadas por ID.
     */
    public void cargarDatos(MyHash<Integer, Pelicula> peliculas) throws CsvValidationException, IOException {
        long tiempoInicio = DeveloperMode ? System.currentTimeMillis() : 0;
        int cantidadValida = 0;
        System.out.println("Iniciando carga de evaluaciones...");

        // Procesa cada línea del archivo CSV
        while ((lineaDatos = lectorCSV.readNext()) != null) {
            Integer idUsuario;
            Date fecha;
            int idPelicula;
            float calificacion;
            try {
                idUsuario = Integer.parseInt(lineaDatos[0]);
                idPelicula = Integer.parseInt(lineaDatos[1]);
                calificacion = Float.parseFloat(lineaDatos[2]);
                fecha = new Date(Long.parseLong(lineaDatos[3])*1000);
                cantidadValida++;
            } catch (Exception e) {continue;}

            // Si el usuario es válido, se asocia la evaluación a la película correspondiente
            if (idUsuario >= 0) {
                Pelicula pelicula = peliculas.get(idPelicula);

                if (pelicula != null) {
                    try {
                        pelicula.agregarEvaluacion(new Evaluacion(idUsuario, calificacion, fecha));
                    } catch (Exception e){
                        System.out.println("Error");
                    }
                }
            }
        }

        if (DeveloperMode) {
            mostrarEstadisticasCarga(tiempoInicio, System.currentTimeMillis(), cantidadValida);
        }

    }

    /**
     * Muestra estadísticas de la carga de evaluaciones si el modo desarrollador está activo.
     * @param tiempoInicio Tiempo de inicio de la carga.
     * @param tiempoFin Tiempo de finalización de la carga.
     * @param cantidadValida Cantidad de evaluaciones válidas procesadas.
     */
    private void mostrarEstadisticasCarga(long tiempoInicio, long tiempoFin, int cantidadValida){
        System.out.println("\n===== ESTADISTICAS DE CARGA DE EVALUACIONES =====");
        System.out.println("Tiempo total de carga: " + (tiempoFin - tiempoInicio) + " ms");
        System.out.println("Cantidad de evaluaciones procesadas: " + (lectorCSV.getRecordsRead() - 1));
        System.out.println("Cantidad de evaluaciones válidas: " + cantidadValida);
        System.out.println("==================================================\n");
    }
}
