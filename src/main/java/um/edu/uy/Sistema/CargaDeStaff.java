package um.edu.uy.Sistema;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.Exceptions.ElementAlreadyExist;
import um.edu.uy.TADs.Hash.MyHashImplCloseLineal;
import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Director;
import um.edu.uy.entities.Pelicula;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CargaDeStaff {
    private CSVReader lectorCSV;
    private final boolean developerMode; // Si se quiere usar el modo desarrollador, se puede cambiar a true para imprimir mas detalles
    private final MyHash<String, Director> directores;
    private final MyHash<String, Actor> actores;

    private static final String TRABAJO_DIRECTOR = "'job': 'Director'";
    private static final String CLAVE_NOMBRE = "'name': '";
    private static final String CLAVE_NOMBRE_ACTOR = "'name': '"; //Se usa una distinta respecto a director por formatos distintos

    public CargaDeStaff(boolean developerMode) {
        this.developerMode = developerMode;
        this.directores = new MyHashImplCloseLineal<>(59999);
        this.actores = new MyHashImplCloseLineal<>(59999);

        try {
            InputStream direccionArchivoDatos = CargaDeStaff.class.getResourceAsStream("/credits.csv");
            assert direccionArchivoDatos != null;
            BufferedReader bufferLectura = new BufferedReader(new InputStreamReader(direccionArchivoDatos));
            this.lectorCSV = new CSVReader(bufferLectura);
            this.lectorCSV.readNext(); // Se lee la primera línea (cabecera) y se descarta
        } catch (IOException | CsvValidationException ignored) { //No deberia de ocurrir, pero si ocurre, se imprime el error
            System.out.println("Error crítico al cargar el archivo de créditos. Asegúrese de que el archivo credits.csv se encuentre en la carpeta resources del proyecto.");
        }
    }

    public void cargarDatos(MyHash<Integer, Pelicula> listaDePeiculas) throws CsvValidationException, IOException {
        long inicio = developerMode ? System.currentTimeMillis() : 0;

        System.out.println("Iniciando carga de créditos...");

        String[] dataLine;
        while ((dataLine = lectorCSV.readNext()) != null) {
            if (dataLine.length < 3) continue;

            int idPelicula;
            try {
                idPelicula = Integer.parseInt(dataLine[2]);
            } catch (NumberFormatException e) {
                continue;
            }

            Pelicula pelicula = listaDePeiculas.get(idPelicula);
            if (pelicula == null) continue;

            String actoresRaw = dataLine[0];
            if (actoresRaw != null && !actoresRaw.isEmpty()) {
                procesarActores(actoresRaw, pelicula);
            }

            String equipoRaw = dataLine[1];
            if (equipoRaw != null && equipoRaw.contains("Director")) {
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

    public MyHash<String, Actor> getActores() {
        return actores;
    }

    private void procesarActores(String entrada, Pelicula tempPeli) {
        int posicionInicial = 0;
        int longitud = entrada.length();
        MyHash<String, Boolean> actoresVistos = new MyHashImplCloseLineal<>(100);

        while (posicionInicial < longitud) {
            int inicioNombre = entrada.indexOf(CLAVE_NOMBRE_ACTOR, posicionInicial);
            if (inicioNombre == -1) break;

            inicioNombre += CLAVE_NOMBRE_ACTOR.length();
            int finNombre = entrada.indexOf("'", inicioNombre);

            if (finNombre == -1 || finNombre <= inicioNombre) {
                posicionInicial = inicioNombre;
                continue;
            }

            String nombreActor = entrada.substring(inicioNombre, finNombre); // Extraigo el nombre del actor

            if (nombreActor.length() >= 3 && actoresVistos.get(nombreActor) == null) {
                try {
                    actoresVistos.insert(nombreActor, true);
                    Actor actor = actores.get(nombreActor);
                    if (actor == null) {
                        actor = new Actor(nombreActor);
                        actores.insert(nombreActor, actor);
                    }
                    actor.agregarPelicula(tempPeli);
                } catch (ElementAlreadyExist ignored) {}
            }

            posicionInicial = finNombre + 1;
        }
    }

    private void procesarDirectores(String entrada, Pelicula tempPeli) {
        int posicionInicial = 0;
        int longitud = entrada.length();

        while (posicionInicial < longitud) {
            int posDirector = entrada.indexOf(TRABAJO_DIRECTOR, posicionInicial);
            if (posDirector == -1) break;

            int posNombre = entrada.indexOf(CLAVE_NOMBRE, posDirector);
            if (posNombre == -1) {
                posicionInicial = posDirector + TRABAJO_DIRECTOR.length();
                continue;
            }

            int inicioNombre = posNombre + CLAVE_NOMBRE.length();
            int finNombre = entrada.indexOf("'", inicioNombre);
            if (finNombre == -1) {
                posicionInicial = posDirector + TRABAJO_DIRECTOR.length();
                continue;
            }

            String nombreDirector = entrada.substring(inicioNombre, finNombre);

            try {
                Director director = new Director(nombreDirector);
                directores.insert(nombreDirector, director);
                director.agregarPelicula(tempPeli);
            } catch (ElementAlreadyExist ignored) {
                Director director = directores.get(nombreDirector);
                if (director != null) { //Siempre se deberia cumplir esta condicion
                    director.agregarPelicula(tempPeli);
                }
            }

            posicionInicial = posDirector + TRABAJO_DIRECTOR.length();
        }
    }

    private void mostrarEstadisticasCarga(long inicio, long fin) {
        System.out.println("\n=== ESTADISTICAS DE CARGA DE CREDITOS ===");
        System.out.println("Tiempo total de carga: " + (fin - inicio) + " ms");
        System.out.println("Registros procesados: " + (lectorCSV.getRecordsRead() - 1));
        System.out.println("Directores únicos: " + directores.size());
        System.out.println("Actores únicos: " + actores.size());
        System.out.println("========================================\n");
    }
}