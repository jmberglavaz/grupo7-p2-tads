package um.edu.uy.Sistema;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Pelicula;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class CargaDeEvaluaciones {
    private CSVReader lectorCSV;
    private String[] lineaDatos;
    private final boolean DeveloperMode;

    public CargaDeEvaluaciones(boolean developerMode) {
        this.DeveloperMode = developerMode;
        try{
            InputStream archivoDatos = CargaDePeliculas.class.getResourceAsStream("/ratings_1mm.csv");
            assert archivoDatos != null;
            BufferedReader bufferLectura = new BufferedReader(new InputStreamReader(archivoDatos));
            this.lectorCSV = new CSVReader(bufferLectura);
            this.lineaDatos = lectorCSV.readNext();
        } catch (IOException | CsvValidationException ignored) { // No deberia de ocurrir, pero si ocurre, se imprime el error
            System.out.println("Error crítico al cargar el archivo de evaluaciones. Asegúrese de que el archivo ratings_1mm.csv se encuentre en la carpeta resources del proyecto.");
        }
    }

    public void cargarDatos(MyHash<Integer, Pelicula> peliculas) throws CsvValidationException, IOException {
        long tiempoInicio = DeveloperMode ? System.currentTimeMillis() : 0;
        int cantidadValida = 0;

        System.out.println("Iniciando carga de evaluaciones...");

        while ((lineaDatos = lectorCSV.readNext()) != null) {
            int idUsuario;
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


            if (idUsuario >= 0) {
                Pelicula pelicula = peliculas.get(idPelicula);

                if (pelicula != null) {
                    pelicula.agregarEvaluacion(new Evaluacion(idUsuario, calificacion, fecha));
                }
            }
        }

        if (DeveloperMode) {
          mostrarEstadisticasCarga(tiempoInicio, System.currentTimeMillis(), cantidadValida);
        }

    }

    private void mostrarEstadisticasCarga(long tiempoInicio, long tiempoFin, int cantidadValida){
        System.out.println("\n===== ESTADISTICAS DE CARGA DE EVALUACIONES =====");
        System.out.println("Tiempo total de carga: " + (tiempoFin - tiempoInicio) + " ms");
        System.out.println("Cantidad de evaluaciones procesadas: " + (lectorCSV.getRecordsRead() - 1));
        System.out.println("Cantidad de evaluaciones válidas: " + cantidadValida);
        System.out.println("==================================================\n");
    }
}
