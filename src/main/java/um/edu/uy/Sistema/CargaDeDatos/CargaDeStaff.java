package um.edu.uy.Sistema.CargaDeDatos;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.Exceptions.ElementAlreadyExist;
import um.edu.uy.TADs.Hash.MyHashImplCloseLineal;
import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Director;
import um.edu.uy.entities.Pelicula;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CargaDeStaff {
    private CSVReader lectorCSV;
    // Indica si se debe mostrar información adicional para desarrolladores
    private final boolean developerMode;
    // Hash para almacenar directores por nombre
    private final MyHash<String, Director> directores;
    // Hash para almacenar actores por ID
    private final MyHash<Integer, Actor> actores;

    public CargaDeStaff(boolean developerMode) {
        this.developerMode = developerMode;
        this.directores = new MyHashImplCloseLineal<>(59999);
        this.actores = new MyHashImplCloseLineal<>(59999);

        try {
            // Se abre el archivo de créditos y se inicializa el lector CSV
            FileInputStream archivoCSV = new FileInputStream("credits.csv");
            this.lectorCSV = new CSVReader(new InputStreamReader(archivoCSV));
            this.lectorCSV.readNext(); // Se descarta la cabecera del archivo
        } catch (IOException | CsvValidationException ignored) {
            // Si ocurre un error crítico al cargar el archivo, se notifica al usuario
            System.out.println("Error crítico al cargar el archivo de créditos. Asegúrese de que el archivo credits.csv se encuentre en la carpeta raiz del proyecto.");
        }
    }

    /**
     * Carga los datos de actores y directores desde el archivo CSV y los asocia a las películas.
     * @param listaDePeiculas Hash de películas existentes, indexadas por ID.
     */
    public void cargarDatos(MyHash<Integer, Pelicula> listaDePeiculas) throws CsvValidationException, IOException {
        long inicio = developerMode ? System.currentTimeMillis() : 0;
        System.out.println("Iniciando carga de créditos...");

        String[] dataLine;
        // Se procesa cada línea del archivo CSV
        while ((dataLine = lectorCSV.readNext()) != null) {
            if (dataLine.length < 3) continue; // Línea inválida

            int idPelicula;
            try {
                idPelicula = Integer.parseInt(dataLine[2]);
            } catch (NumberFormatException e) {
                // Si el ID de la película no es válido, se omite la línea
                continue;
            }

            Pelicula pelicula = listaDePeiculas.get(idPelicula);
            if (pelicula == null) {continue;} // Si la película no existe, se omite

            String actoresRaw = dataLine[0];
            if (actoresRaw != null && !actoresRaw.isEmpty()) {
                // Procesa y asocia los actores a la película
                procesarActores(actoresRaw, pelicula);
            }

            String equipoRaw = dataLine[1];
            if (equipoRaw != null && equipoRaw.contains("Director")) {
                // Procesa y asocia los directores a la película
                procesarDirectores(equipoRaw, pelicula);
            }
        }

        if (developerMode) {
            mostrarEstadisticasCarga(inicio, System.currentTimeMillis());
        }
    }

    public MyHash<String, Director> getDirectores() {
        return directores;
    }

    public MyHash<Integer, Actor> getActores() {
        return actores;
    }

    /**
     * Procesa la linea con los actores, extrae sus datos y los asocia a la película correspondiente.
     * @param entrada Cadena con los datos de los actores en formato especial.
     * @param tempPeli Película a la que se asociarán los actores.
     */
    private void procesarActores(String entrada, Pelicula tempPeli) {
        // Claves para buscar los campos de nombre e ID en la cadena
        String claveNombre = "'name': '";
        String claveNombreComillas = "'name': \"";
        String claveId = "'id': ";
        int posicionInicial = 0;
        int longitud = entrada.length();

        // Se recorre la cadena buscando cada aparición de un actor
        while (posicionInicial < longitud) {
            int posId = entrada.indexOf(claveId, posicionInicial);
            if (posId == -1) break; // No hay más actores

            int inicioId = posId + claveId.length();
            int finId = entrada.indexOf(",", inicioId);
            if (finId == -1) {
                posicionInicial = posId + claveId.length();
                continue;
            }

            // Se extrae el ID del actor
            String idStr = entrada.substring(inicioId, finId).trim();
            int idActor;
            try {
                idActor = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                // Si el ID no es válido, se omite este actor
                System.out.println(idStr);
                posicionInicial = posId + claveId.length();
                continue;
            }

            // Se busca el nombre del actor, considerando posibles comillas simples o dobles
            int posNombre = entrada.indexOf(claveNombre, finId);
            int posNombreComillas = entrada.indexOf(claveNombreComillas, finId);

            boolean tieneComillasEnNombre = false;
            int inicioNombre;

            if (posNombre == -1 && posNombreComillas == -1) {
                // No se encontró el nombre, se pasa al siguiente actor
                posicionInicial = posId + claveId.length();
                continue;
            } else if (posNombre == -1) {
                tieneComillasEnNombre = true;
                inicioNombre = posNombreComillas + claveNombreComillas.length();
            } else if (posNombreComillas == -1) {
                tieneComillasEnNombre = false;
                inicioNombre = posNombre + claveNombre.length();
            } else {
                if (posNombre < posNombreComillas) {
                    tieneComillasEnNombre = false;
                    inicioNombre = posNombre + claveNombre.length();
                } else {
                    tieneComillasEnNombre = true;
                    inicioNombre = posNombreComillas + claveNombreComillas.length();
                }
            }

            int finNombre;
            if (tieneComillasEnNombre) {
                finNombre = entrada.indexOf("\"", inicioNombre);
            } else {
                finNombre = entrada.indexOf("'", inicioNombre);
            }

            if (finNombre == -1) {
                // No se encontró el final del nombre, se pasa al siguiente actor
                posicionInicial = posId + claveId.length();
                continue;
            }

            String nombreActor = entrada.substring(inicioNombre, finNombre);

            // Se registra el actor en el hash y se asocia la película
            try {
                Actor actor = actores.get(idActor);
                if (actor == null) {
                    actor = new Actor(idActor, nombreActor);
                    actores.insert(idActor, actor);
                }
                actor.agregarPelicula(tempPeli);
            } catch (ElementAlreadyExist ignored) {
                // Si el actor ya existe, solo se agrega la película
                Actor actor = actores.get(idActor);
                if (actor != null) { // Siempre se deberia cumplir esta condicion
                    actor.agregarPelicula(tempPeli);
                }
            }
            posicionInicial = posId + claveId.length();
        }
    }

    /**
     * Procesa la linea con el de equipo, extrae los directores y los asocia a la película.
     * @param entrada Cadena con los datos del equipo.
     * @param tempPeli Película a la que se asociarán los directores.
     */
    private void procesarDirectores(String entrada, Pelicula tempPeli) {
        String trabajoDirector = "'job': 'Director'";
        String claveNombre = "'name': '";
        String claveNombreComillas = "'name': \"";
        int posicionInicial = 0;
        int longitud = entrada.length();

        // Se recorre la cadena buscando cada vez que aparece un director
        while (posicionInicial < longitud) {
            int posDirector = entrada.indexOf(trabajoDirector, posicionInicial);
            if (posDirector == -1) break;

            int posNombre = entrada.indexOf(claveNombre, posDirector);
            int posNombreComillas = entrada.indexOf(claveNombreComillas, posDirector);

            boolean tieneComillasEnNombre = false;
            int inicioNombre;

            if (posNombre == -1 && posNombreComillas == -1) {
                posicionInicial = posDirector + trabajoDirector.length();
                continue;
            } else if (posNombre == -1) {
                tieneComillasEnNombre = true;
                inicioNombre = posNombreComillas + claveNombreComillas.length();
            } else if (posNombreComillas == -1) {
                tieneComillasEnNombre = false;
                inicioNombre = posNombre + claveNombre.length();
            } else {
                if (posNombre < posNombreComillas) {
                    tieneComillasEnNombre = false;
                    inicioNombre = posNombre + claveNombre.length();
                } else {
                    tieneComillasEnNombre = true;
                    inicioNombre = posNombreComillas + claveNombreComillas.length();
                }
            }

            int finNombre;
            if (tieneComillasEnNombre) {
                finNombre = entrada.indexOf("\"", inicioNombre);
            } else {
                finNombre = entrada.indexOf("'", inicioNombre);
            }

            if (finNombre == -1) {
                posicionInicial = posDirector + trabajoDirector.length();
                continue;
            }

            String nombreDirector = entrada.substring(inicioNombre, finNombre);

            // Se registra el director en el hash y se asocia la película
            try {
                Director director = new Director(nombreDirector);
                directores.insert(nombreDirector, director);
                director.agregarPelicula(tempPeli);
            } catch (ElementAlreadyExist ignored) {
                // Si el director ya existe, solo se agrega la película
                Director director = directores.get(nombreDirector);
                if (director != null) { // Siempre se debería cumplir esta condición
                    director.agregarPelicula(tempPeli);
                }
            }

            posicionInicial = posDirector + trabajoDirector.length();
        }
    }

    /**
     * Muestra estadísticas de la carga de créditos si el modo desarrollador está activo.
     * @param inicio Tiempo de inicio de la carga.
     * @param fin Tiempo de finalización de la carga.
     */
    private void mostrarEstadisticasCarga(long inicio, long fin) {
        System.out.println("\n=== ESTADISTICAS DE CARGA DE CREDITOS ===");
        System.out.println("Tiempo total de carga: " + (fin - inicio) + " ms");
        System.out.println("Registros procesados: " + (lectorCSV.getRecordsRead() - 1));
        System.out.println("Directores únicos: " + directores.size());
        System.out.println("Actores únicos: " + actores.size());
        System.out.println("========================================\n");
    }
}
