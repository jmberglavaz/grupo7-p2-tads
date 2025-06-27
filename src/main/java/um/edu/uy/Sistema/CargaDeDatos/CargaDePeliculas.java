package um.edu.uy.Sistema.CargaDeDatos;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import um.edu.uy.Exceptions.ElementAlreadyExist;
import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.Hash.MyHashImplCloseLineal;
import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.entities.Coleccion;
import um.edu.uy.entities.Genero;
import um.edu.uy.entities.Idioma;
import um.edu.uy.entities.Pelicula;

/**
 * Clase encargada de cargar los datos de películas desde un archivo CSV.
 */
public class CargaDePeliculas {
    private CSVReader lectorCSV;
    private final boolean developerMode;   // Indica si se debe mostrar información adicional para desarrolladores
    private final MyHash<Integer, Pelicula> peliculas;  // Hash para almacenar películas por ID
    private final MyHash<Integer, Genero> generos;  // Hash para almacenar géneros por ID
    private final MyHash<String, Idioma> idiomas;   // Hash para almacenar idiomas por acrónimo
    private final MyHash<Integer, Coleccion> colecciones;   // Hash para almacenar colecciones por ID

    // Patrones para extraer colecciones y géneros de las cadenas
    private final Pattern patterenColeccion = Pattern.compile("'id':\\s*(\\d+),\\s*'name':\\s*'([^']+)'");
    private final Pattern patternGenero = Pattern.compile("'id':\\s*(\\d+),\\s*'name':\\s*'([^']+)'");

    /**
     * Constructor. Inicializa los hashes y carga los datos desde el archivo CSV.
     * @param developerMode Si es true, imprime estadísticas y detalles adicionales.
     */
    public CargaDePeliculas(boolean developerMode) {
        this.developerMode = developerMode;
        try{
            // Se abre el archivo de películas y se inicializa el lector CSV
            FileInputStream archivoCSV = new FileInputStream("movies_metadata.csv");
            this.lectorCSV = new CSVReader(new InputStreamReader(archivoCSV));
            lectorCSV.readNext(); // Se descarta la cabecera
        } catch (IOException | CsvValidationException e) {
            System.out.println("Error critico al cargar el archivo de peliculas. Asegurese de que el archivo movies_metadata.csv se encuentre en la raiz del proyecto.");
            System.out.println("Error detallado: " + e.getMessage());
        }

        this.peliculas = new MyHashImplCloseLineal<>(59999);
        this.generos = new MyHashImplCloseLineal<>(53);
        this.idiomas = new MyHashImplCloseLineal<>(97);
        this.colecciones = new MyHashImplCloseLineal<>(1709);

        try{
            cargarDatos();
        } catch (IOException | CsvValidationException e) {
            System.out.println("Error al cargar los datos de las peliculas: " + e.getMessage());
        }
    }

    /**
     * Carga los datos de películas, géneros, idiomas y colecciones desde el archivo CSV.
     */
    public void cargarDatos() throws IOException, CsvValidationException {
        long inicio = developerMode ? System.currentTimeMillis() : 0;

        System.out.println("Iniciando carga de peliculas...");
        String[] dataLine;
        // Procesa cada línea del archivo CSV
        while ((dataLine = lectorCSV.readNext()) != null) {

            int idPelicula;
            try {
                idPelicula = Integer.parseInt(dataLine[5]);
            } catch (NumberFormatException e) {
                continue;
            }

            long ganancias = 0;
            try {
                ganancias = Long.parseLong(dataLine[13]);
            } catch (NumberFormatException ignored) {}

            // Se crea la película y se inserta en el hash
            Pelicula pelicula = new Pelicula(idPelicula, dataLine[8], dataLine[12], ganancias);
            try {
                peliculas.insert(idPelicula, pelicula);
            } catch (ElementAlreadyExist ignored) {
                continue;
            }

            // Procesa y asocia los géneros a la película
            MyList<Genero> listaGeneros = searchGeneros(dataLine[3]);
            for (Genero genero : listaGeneros) {
                try{
                    this.generos.insert(genero.getId(), genero);
                    genero.agregarPelicula(pelicula);
                } catch (ElementAlreadyExist ignored) {
                    genero = generos.get(genero.getId());
                    genero.agregarPelicula(pelicula);
                }
            }

            // Procesa y asocia el idioma a la película
            String acronimoIdioma = dataLine[7];
            if (acronimoIdioma != null && !acronimoIdioma.trim().isEmpty()) {
                Idioma idioma = new Idioma(acronimoIdioma);
                try {
                    idiomas.insert(acronimoIdioma, idioma);
                    idioma.agregarPelicula(pelicula);
                } catch (ElementAlreadyExist ignored) {
                    idioma = idiomas.get(acronimoIdioma);
                    idioma.agregarPelicula(pelicula);
                }
            }

            // Procesa y asocia la colección a la película
            Coleccion coleccion = searchColecciones(dataLine[1], idPelicula, dataLine[8]);
            if (coleccion != null){
                try {
                    colecciones.insert(coleccion.getId(), coleccion);
                    coleccion.agregarPelicula(pelicula);
                } catch (ElementAlreadyExist ignored) {
                    coleccion = colecciones.get(coleccion.getId());
                    coleccion.agregarPelicula(pelicula);
                }
            }
        }

        if (developerMode) {
            mostrarEstadisticasCarga(inicio, System.currentTimeMillis());
        }

    }

    // Getters para acceder a los hashes de películas, géneros, idiomas y colecciones
    public MyHash<Integer, Pelicula> getPeliculas() {
        return peliculas;
    }

    public MyHash<Integer, Genero> getGeneros() {
        return generos;
    }

    public MyHash<String, Idioma> getIdiomas() {
        return idiomas;
    }

    public MyHash<Integer, Coleccion> getColecciones() {
        return colecciones;
    }

    /**
     * Extrae los géneros de la linea dada y los devuelve en una lista.
     * @param entrada Cadena con los datos de géneros.
     * @return Lista de géneros encontrados.
     */
    private MyList<Genero> searchGeneros(String entrada){
        MyList<Genero> listaGeneros = new MyLinkedListImpl<>();
        if (entrada == null || entrada.trim().isEmpty()) {
            return listaGeneros;
        }

        Matcher matcher = patternGenero.matcher(entrada);
        while (matcher.find()) {
            try {
                int id = Integer.parseInt(matcher.group(1));
                String nombre = matcher.group(2);
                listaGeneros.add(new Genero(id, nombre));
            } catch (NumberFormatException ignored) {}
        }
        return listaGeneros;

    }

    /**
     * Extrae la colección de la entrada dada.
     * @param entrada Cadena con los datos de la colección.
     * @param idPelicula ID de la película.
     * @param nombrePelicula Nombre de la película.
     * @return Colección encontrada o null si no hay.
     */
    private Coleccion searchColecciones(String entrada, int idPelicula, String nombrePelicula){
        if (entrada == null) {
            return null;
        } else if (entrada.trim().isEmpty()) {
            return new Coleccion(idPelicula, nombrePelicula);
        }

        Matcher matcher = patterenColeccion.matcher(entrada);
        if (matcher.find()){
            try {
                int id = Integer.parseInt(matcher.group(1));
                String nombre = matcher.group(2);
                return new Coleccion(id, nombre);
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
        return null;
    }

    /**
     * Muestra estadísticas de la carga de películas si el modo desarrollador está activo.
     * @param inicio Tiempo de inicio de la carga.
     * @param fin Tiempo de finalización de la carga.
     */
    private void mostrarEstadisticasCarga(long inicio, long fin){
        System.out.println("\n=== ESTADISTICAS DE CARGA DE PELICULAS ===");
        System.out.println("Tiempo total de carga: " + (fin - inicio) + " ms");
        System.out.println("Peliculas procesadas: " + (lectorCSV.getRecordsRead()-1));
        System.out.println("Peliculas validas cargadas: " + peliculas.size());
        System.out.println("Generos unicos: " + generos.size());
        System.out.println("Idiomas unicos: " + idiomas.size());
        System.out.println("Colecciones unicas: " + colecciones.size());
        System.out.println("========================================\n");
    }
}
