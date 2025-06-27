package um.edu.uy.sistema.cargaDeDatos;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.excepciones.ElementAlreadyExists;
import um.edu.uy.adt.hash.MyHashImplCloseLineal;
import um.edu.uy.adt.hash.MyHash;
import um.edu.uy.adt.list.linked.MyLinkedListImpl;
import um.edu.uy.adt.list.MyList;
import um.edu.uy.entidades.Coleccion;
import um.edu.uy.entidades.Genero;
import um.edu.uy.entidades.Idioma;
import um.edu.uy.entidades.Pelicula;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Se encarga de la carga de datos de películas, géneros, idiomas y colecciones
 * desde el archivo "movies_metadata.csv".
 */
public class CargaDePeliculas {
    private CSVReader csvReader;
    private final boolean isDevMode;
    private final MyHash<Integer, Pelicula> movies;
    private final MyHash<Integer, Genero> genres;
    private final MyHash<String, Idioma> languages;
    private final MyHash<Integer, Coleccion> collections;

    // Patrones Regex para extraer datos de los campos de texto del CSV
    private final Pattern patternCollection = Pattern.compile("'id':\\s*(\\d+),\\s*'name':\\s*'([^']+)'");
    private final Pattern patternGenre = Pattern.compile("'id':\\s*(\\d+),\\s*'name':\\s*'([^']+)'");

    /**
     * Constructor que inicializa los hashes y comienza la carga de datos.
     * @param isDevMode Si es true, imprime estadísticas de la carga.
     */
    public CargaDePeliculas(boolean isDevMode) {
        this.isDevMode = isDevMode;
        try {
            // Se abre el archivo CSV y se salta la primera línea (cabecera)
            FileInputStream file = new FileInputStream("movies_metadata.csv");
            this.csvReader = new CSVReader(new InputStreamReader(file));
            csvReader.readNext();
        } catch (IOException | CsvValidationException e) {
            System.out.println("Error critico al cargar 'movies_metadata.csv'. Asegurese de que el archivo este en la raiz del proyecto.");
            System.out.println("Error detallado: " + e.getMessage());
        }

        // Inicialización de las tablas de hash con capacidades estimadas
        this.movies = new MyHashImplCloseLineal<>(59999);
        this.genres = new MyHashImplCloseLineal<>(53);
        this.languages = new MyHashImplCloseLineal<>(97);
        this.collections = new MyHashImplCloseLineal<>(1709);

        // Inicia el proceso de carga
        try {
            cargarDatos();
        } catch (IOException | CsvValidationException e) {
            System.out.println("Error durante la carga de datos de peliculas: " + e.getMessage());
        }
    }

    /**
     * Lee el archivo CSV línea por línea, procesando cada película y asociando sus
     * géneros, idioma y colección.
     */
    public void cargarDatos() throws IOException, CsvValidationException {
        long start = isDevMode ? System.currentTimeMillis() : 0;
        System.out.println("Iniciando carga de peliculas...");
        String[] dataLine;

        while ((dataLine = csvReader.readNext()) != null) {
            int movieId;
            try {
                // El ID de la película está en la columna 5
                movieId = Integer.parseInt(dataLine[5]);
            } catch (NumberFormatException e) {
                continue; // Si el ID no es un número válido, se salta la línea
            }

            long revenue = 0;
            try {
                // Los ingresos (revenue) están en la columna 13
                revenue = Long.parseLong(dataLine[13]);
            } catch (NumberFormatException ignored) {
                // Si no hay dato de revenue, se deja en 0
            }

            // Se crea la película y se inserta en el hash
            Pelicula movie = new Pelicula(movieId, dataLine[8], dataLine[12], revenue);
            try {
                movies.insert(movieId, movie);
            } catch (ElementAlreadyExists ignored) {
                continue; // Si la película ya existe, se salta
            }

            // Procesa y asocia los géneros a la película
            MyList<Genero> genreList = parseGenres(dataLine[3]);
            for (Genero genre : genreList) {
                try {
                    // Si el género no existe en el hash, se inserta
                    this.genres.insert(genre.getId(), genre);
                    genre.addMovie(movie);
                } catch (ElementAlreadyExists ignored) {
                    // Si ya existe, se obtiene y se le asocia la película
                    genre = genres.get(genre.getId());
                    if (genre != null) genre.addMovie(movie);
                }
            }

            // Procesa y asocia el idioma a la película
            String langAcronym = dataLine[7];
            if (langAcronym != null && !langAcronym.trim().isEmpty()) {
                Idioma lang = new Idioma(langAcronym);
                try {
                    languages.insert(langAcronym, lang);
                    lang.addMovie(movie);
                } catch (ElementAlreadyExists ignored) {
                    lang = languages.get(langAcronym);
                    if (lang != null) lang.addMovie(movie);
                }
            }

            // Procesa y asocia la colección a la película
            Coleccion collection = parseCollection(dataLine[1], movieId, dataLine[8]);
            if (collection != null) {
                try {
                    collections.insert(collection.getId(), collection);
                    collection.addMovie(movie);
                } catch (ElementAlreadyExists ignored) {
                    collection = collections.get(collection.getId());
                    if (collection != null) collection.addMovie(movie);
                }
            }
        }

        if (isDevMode) {
            printLoadStats(start, System.currentTimeMillis());
        }
    }

    //Getters para acceder a los datos cargados
    public MyHash<Integer, Pelicula> getPeliculas() { return movies; }
    public MyHash<Integer, Genero> getGeneros() { return genres; }
    public MyHash<String, Idioma> getIdiomas() { return languages; }
    public MyHash<Integer, Coleccion> getColecciones() { return collections; }

    //Métodos privados de parseo

    private MyList<Genero> parseGenres(String input) {
        MyList<Genero> genreList = new MyLinkedListImpl<>();
        if (input == null || input.trim().isEmpty()) return genreList;
        Matcher matcher = patternGenre.matcher(input);
        while (matcher.find()) {
            try {
                int id = Integer.parseInt(matcher.group(1));
                String name = matcher.group(2);
                genreList.add(new Genero(id, name));
            } catch (NumberFormatException ignored) {}
        }
        return genreList;
    }

    private Coleccion parseCollection(String input, int movieId, String movieName) {
        if (input == null) return null;
        if (input.trim().isEmpty()) return new Coleccion(movieId, movieName);
        Matcher matcher = patternCollection.matcher(input);
        if (matcher.find()) {
            try {
                int id = Integer.parseInt(matcher.group(1));
                String name = matcher.group(2);
                return new Coleccion(id, name);
            } catch (NumberFormatException ignored) { return null; }
        }
        return null;
    }

    private void printLoadStats(long start, long end) {
        System.out.println("\n=== ESTADISTICAS DE CARGA DE PELICULAS ===");
        System.out.println("Tiempo total de carga: " + (end - start) + " ms");
        System.out.println("Peliculas procesadas: " + (csvReader.getRecordsRead() - 1));
        System.out.println("Peliculas validas cargadas: " + movies.size());
        System.out.println("Generos unicos: " + genres.size());
        System.out.println("Idiomas unicos: " + languages.size());
        System.out.println("Colecciones unicas: " + collections.size());
        System.out.println("========================================\n");
    }
}