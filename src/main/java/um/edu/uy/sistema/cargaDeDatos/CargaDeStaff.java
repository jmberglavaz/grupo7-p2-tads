package um.edu.uy.sistema.cargaDeDatos;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.excepciones.ElementAlreadyExists;
import um.edu.uy.adt.hash.MyHashImplCloseLineal;
import um.edu.uy.adt.hash.MyHash;
import um.edu.uy.entidades.Actor;
import um.edu.uy.entidades.Director;
import um.edu.uy.entidades.Pelicula;

import java.io.*;

public class CargaDeStaff {
    private CSVReader csvReader;
    private final boolean developerMode; // Indica si se debe mostrar información adicional para desarrolladores
    private final MyHash<String, Director> directors;// Hash para almacenar directores por nombre
    private final MyHash<Integer, Actor> actors; // Hash para almacenar actores por ID

    public CargaDeStaff(boolean developerMode) {
        this.developerMode = developerMode;
        this.directors = new MyHashImplCloseLineal<>(59999);
        this.actors = new MyHashImplCloseLineal<>(59999);

        try {
            // Se abre el archivo de créditos y se inicializa el lector CSV
            FileInputStream file = new FileInputStream("credits.csv");
            this.csvReader = new CSVReader(new InputStreamReader(file));
            this.csvReader.readNext(); // Se descarta la cabecera del archivo
        } catch (IOException | CsvValidationException ignored) {
            // Si ocurre un error crítico al cargar el archivo, se notifica al usuario
            System.out.println("Error crítico al cargar el archivo de créditos. Asegúrese de que el archivo credits.csv se encuentre en la carpeta raiz del proyecto.");
        }
    }

    /**
     * Carga los datos de actores y directores desde el archivo CSV y los asocia a las películas.
     * @param movies Hash de películas existentes, indexadas por ID.
     */
    public void cargarDatos(MyHash<Integer, Pelicula> movies) throws CsvValidationException, IOException {
        long start = developerMode ? System.currentTimeMillis() : 0;
        System.out.println("Iniciando carga de créditos...");

        String[] dataLine;
        // Se procesa cada línea del archivo CSV
        while ((dataLine = csvReader.readNext()) != null) {
            if (dataLine.length < 3) continue; // Línea inválida

            int movieId;
            try {
                movieId = Integer.parseInt(dataLine[2]);
            } catch (NumberFormatException e) {
                // Si el ID de la película no es válido, se omite la línea
                continue;
            }

            Pelicula movie = movies.get(movieId);
            if (movie == null) {continue;} // Si la película no existe, se omite

            String actorsRaw = dataLine[0];
            if (actorsRaw != null && !actorsRaw.isEmpty()) {
                // Procesa y asocia los actores a la película
                parserActors(actorsRaw, movie);
            }

            String crewRaw = dataLine[1];
            if (crewRaw != null && crewRaw.contains("Director")) {
                // Procesa y asocia los directores a la película
                parserDirectors(crewRaw, movie);
            }
        }

        if (developerMode) {
            showLoadStats(start, System.currentTimeMillis());
        }
    }

    public MyHash<String, Director> getDirectores() {
        return directors;
    }

    public MyHash<Integer, Actor> getActores() {
        return actors;
    }

    /**
     * Procesa la linea con los actores, extrae sus datos y los asocia a la película correspondiente.
     * @param input Cadena con los datos de los actores en formato especial.
     * @param movie Película a la que se asociarán los actores.
     */
    private void parserActors(String input, Pelicula movie) {
        String keyName = "'name': '";
        String keyNameQuotes = "'name': \"";
        String keyId = "'id': ";
        int pos = 0;
        int len = input.length();

        // Se recorre la cadena buscando cada aparición de un actor
        while (pos < len) {
            int posId = input.indexOf(keyId, pos);
            if (posId == -1) break; // No hay más actores

            int startId = posId + keyId.length();
            int endId = input.indexOf(",", startId);
            if (endId == -1) {
                pos = posId + keyId.length();
                continue;
            }

            // Se extrae el ID del actor
            String idStr = input.substring(startId, endId).trim();
            int actorId;
            try {
                actorId = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                pos = posId + keyId.length();
                continue;
            }

            // Se busca el nombre del actor, considerando posibles comillas simples o dobles
            int posName = input.indexOf(keyName, endId);
            int posNameQuotes = input.indexOf(keyNameQuotes, endId);

            boolean hasQuotes = false;
            int startName;

            if (posName == -1 && posNameQuotes == -1) {
                pos = posId + keyId.length();
                continue;
            } else if (posName == -1) {
                hasQuotes = true;
                startName = posNameQuotes + keyNameQuotes.length();
            } else if (posNameQuotes == -1) {
                hasQuotes = false;
                startName = posName + keyName.length();
            } else {
                if (posName < posNameQuotes) {
                    hasQuotes = false;
                    startName = posName + keyName.length();
                } else {
                    hasQuotes = true;
                    startName = posNameQuotes + keyNameQuotes.length();
                }
            }

            int endName;
            if (hasQuotes) {
                endName = input.indexOf("\"", startName);
            } else {
                endName = input.indexOf("'", startName);
            }

            if (endName == -1) {
                pos = posId + keyId.length();
                continue;
            }

            String actorName = input.substring(startName, endName);

            // Se registra el actor en el hash y se asocia la película
            try {
                Actor actor = actors.get(actorId);
                if (actor == null) {
                    actor = new Actor(actorId, actorName);
                    actors.insert(actorId, actor);
                }
                actor.addMovie(movie);
            } catch (ElementAlreadyExists ignored) {
                Actor actor = actors.get(actorId);
                if (actor != null) {
                    actor.addMovie(movie);
                }
            }
            pos = posId + keyId.length();
        }
    }

    /**
     * Procesa la linea con el de equipo, extrae los directores y los asocia a la película.
     * @param input Cadena con los datos del equipo.
     * @param movie Película a la que se asociarán los directores.
     */
    private void parserDirectors(String input, Pelicula movie) {
        String directorJob = "'job': 'Director'";
        String keyName = "'name': '";
        String keyNameQuotes = "'name': \"";
        int pos = 0;
        int len = input.length();

        // Se recorre la cadena buscando cada vez que aparece un director
        while (pos < len) {
            int posDirector = input.indexOf(directorJob, pos);
            if (posDirector == -1) break;

            int posName = input.indexOf(keyName, posDirector);
            int posNameQuotes = input.indexOf(keyNameQuotes, posDirector);

            boolean hasQuotes = false;
            int startName;

            if (posName == -1 && posNameQuotes == -1) {
                pos = posDirector + directorJob.length();
                continue;
            } else if (posName == -1) {
                hasQuotes = true;
                startName = posNameQuotes + keyNameQuotes.length();
            } else if (posNameQuotes == -1) {
                hasQuotes = false;
                startName = posName + keyName.length();
            } else {
                if (posName < posNameQuotes) {
                    hasQuotes = false;
                    startName = posName + keyName.length();
                } else {
                    hasQuotes = true;
                    startName = posNameQuotes + keyNameQuotes.length();
                }
            }

            int endName;
            if (hasQuotes) {
                endName = input.indexOf("\"", startName);
            } else {
                endName = input.indexOf("'", startName);
            }

            if (endName == -1) {
                pos = posDirector + directorJob.length();
                continue;
            }

            String directorName = input.substring(startName, endName);

            // Se registra el director en el hash y se asocia la película
            try {
                Director director = new Director(directorName);
                directors.insert(directorName, director);
                director.addMovie(movie);
            } catch (ElementAlreadyExists ignored) {
                Director director = directors.get(directorName);
                if (director != null) {
                    director.addMovie(movie);
                }
            }

            pos = posDirector + directorJob.length();
        }
    }

    /**
     * Muestra estadísticas de la carga de créditos si el modo desarrollador está activo.
     * @param start Tiempo de inicio de la carga.
     * @param end Tiempo de finalización de la carga.
     */
    private void showLoadStats(long start, long end) {
        System.out.println("\n=== ESTADISTICAS DE CARGA DE CREDITOS ===");
        System.out.println("Tiempo total de carga: " + (end - start) + " ms");
        System.out.println("Registros procesados: " + (csvReader.getRecordsRead() - 1));
        System.out.println("Directores únicos: " + directors.size());
        System.out.println("Actores únicos: " + actors.size());
        System.out.println("========================================\n");
    }
}
