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
    private final boolean developerMode; // Si se quiere usar el modo desarrollador, se puede cambiar a true para imprimir mas detalles
    private final MyHash<String, Director> directores;
    private final MyHash<Integer, Actor> actores;

    public CargaDeStaff(boolean developerMode) {
        this.developerMode = developerMode;
        this.directores = new MyHashImplCloseLineal<>(59999);
        this.actores = new MyHashImplCloseLineal<>(59999);

        try {
            FileInputStream archivoCSV = new FileInputStream("credits.csv");
            this.lectorCSV = new CSVReader(new InputStreamReader(archivoCSV));
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
            if (pelicula == null) {continue;}

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

    public MyHash<Integer, Actor> getActores() {
        return actores;
    }

    private void procesarActores(String entrada, Pelicula tempPeli) {
        String claveNombre = "'name': '";
        String claveNombreComillas = "'name': \"";
        String claveId = "'id': ";
        int posicionInicial = 0;
        int longitud = entrada.length();

        while (posicionInicial < longitud) {
            int posId = entrada.indexOf(claveId, posicionInicial);
            if (posId == -1) break;

            int inicioId = posId + claveId.length();

            int finId = entrada.indexOf(",", inicioId);

            if (finId == -1) {
                posicionInicial = posId + claveId.length();
                continue;
            }

            String idStr = entrada.substring(inicioId, finId).trim();
            int idActor;
            try {
                idActor = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                System.out.println(idStr);

                posicionInicial = posId + claveId.length();
                continue;
            }

            int posNombre = entrada.indexOf(claveNombre, finId);
            int posNombreComillas = entrada.indexOf(claveNombreComillas, finId);

            boolean tieneComillasEnNombre = false;
            int inicioNombre;

            if (posNombre == -1 && posNombreComillas == -1) {
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
                posicionInicial = posId + claveId.length();
                continue;
            }

            String nombreActor = entrada.substring(inicioNombre, finNombre);

            try {
                Actor actor = actores.get(idActor);
                if (actor == null) {
                    actor = new Actor(idActor, nombreActor);
                    Pattern pattern = Pattern.compile("[^a-zA-Z\\s]");
                    Matcher matcher = pattern.matcher(nombreActor);
                    if (matcher.find()){
                        System.out.println(nombreActor + " - " + idActor);
                    }
                    actores.insert(idActor, actor);

                }
                actor.agregarPelicula(tempPeli);
            } catch (ElementAlreadyExist ignored) {
                Actor actor = actores.get(idActor);
                if (actor != null) {
                    actor.agregarPelicula(tempPeli);
                }
            }
            posicionInicial = posId + claveId.length();
        }
    }

    private void procesarDirectores(String entrada, Pelicula tempPeli) {
        String trabajoDirector = "'job': 'Director'";
        String claveNombre = "'name': '";
        String claveNombreComillas = "'name': \"";
        int posicionInicial = 0;
        int longitud = entrada.length();

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

            posicionInicial = posDirector + trabajoDirector.length();
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