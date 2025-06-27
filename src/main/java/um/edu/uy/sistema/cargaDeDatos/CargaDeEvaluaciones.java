package um.edu.uy.sistema.cargaDeDatos;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.adt.hash.MyHash;
import um.edu.uy.entidades.Evaluacion;
import um.edu.uy.entidades.Pelicula;

import java.io.*;
import java.util.Date;

/**
 * Clase encargada de cargar los datos de evaluaciones desde un archivo CSV.
 * Sigue el formato de comentarios de CargaDeStaff.
 */
public class CargaDeEvaluaciones {
    private CSVReader csvReader;
    private String[] dataLine;
    // Indica si se debe mostrar información adicional para desarrolladores
    private final boolean devMode;

    /**
     * Constructor. Inicializa el lector CSV para el archivo de evaluaciones.
     * @param devMode Si es true, imprime estadísticas y detalles adicionales.
     */
    public CargaDeEvaluaciones(boolean devMode) {
        this.devMode = devMode;
        try {
            // Se abre el archivo de evaluaciones y se inicializa el lector CSV
            FileInputStream file = new FileInputStream("ratings_1mm.csv");
            this.csvReader = new CSVReader(new InputStreamReader(file));
            this.dataLine = csvReader.readNext(); // Se descarta la cabecera
        } catch (IOException | CsvValidationException ignored) {
            System.out.println("Error crítico al cargar el archivo de evaluaciones. Asegúrese de que el archivo ratings_1mm.csv se encuentre en la carpeta resources del proyecto.");
        }
    }

    /**
     * Carga los datos de evaluaciones desde el archivo CSV y los asocia a las películas.
     * @param movies Hash de películas existentes, indexadas por ID.
     */
    public void cargarDatos(MyHash<Integer, Pelicula> movies) throws CsvValidationException, IOException {
        long start = devMode ? System.currentTimeMillis() : 0;
        int validCount = 0;
        System.out.println("Iniciando carga de evaluaciones...");

        // Procesa cada línea del archivo CSV
        while ((dataLine = csvReader.readNext()) != null) {
            int userId;
            Date date;
            int movieId;
            float rating;
            try {
                userId = Integer.parseInt(dataLine[0]);
                movieId = Integer.parseInt(dataLine[1]);
                rating = Float.parseFloat(dataLine[2]);
                date = new Date(Long.parseLong(dataLine[3]) * 1000);
                validCount++;
            } catch (Exception e) { continue; }

            // Si el usuario es válido, se asocia la evaluación a la película correspondiente
            if (userId >= 0) {
                Pelicula movie = movies.get(movieId);
                if (movie != null) {
                    try {
                        movie.addReview(new Evaluacion(userId, rating, date));
                    } catch (Exception e) {
                        System.out.println("Error");
                    }
                }
            }
        }

        if (devMode) {
            showLoadStats(start, System.currentTimeMillis(), validCount);
        }
    }

    /**
     * Muestra estadísticas de la carga de evaluaciones si el modo desarrollador está activo.
     * @param start Tiempo de inicio de la carga.
     * @param end Tiempo de finalización de la carga.
     * @param validCount Cantidad de evaluaciones válidas procesadas.
     */
    private void showLoadStats(long start, long end, int validCount) {
        System.out.println("\n===== ESTADÍSTICAS DE CARGA DE EVALUACIONES =====");
        System.out.println("Tiempo total de carga: " + (end - start) + " ms");
        System.out.println("Cantidad de evaluaciones procesadas: " + (csvReader.getRecordsRead() - 1));
        System.out.println("Cantidad de evaluaciones válidas: " + validCount);
        System.out.println("==================================================\n");
    }
}
