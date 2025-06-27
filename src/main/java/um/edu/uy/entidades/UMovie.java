package um.edu.uy.entidades;

import um.edu.uy.sistema.cargaDeDatos.CargaDeEvaluaciones;
import um.edu.uy.sistema.cargaDeDatos.CargaDePeliculas;
import um.edu.uy.sistema.cargaDeDatos.CargaDeStaff;
import um.edu.uy.sistema.consultas.*;
import um.edu.uy.adt.hash.MyHash;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase principal que orquesta la aplicación, gestionando la carga de datos y el menú de usuario.
 */
public class UMovie {
    // Hashes para almacenar todos los datos cargados del sistema
    private static MyHash<Integer, Pelicula> movies;
    private static MyHash<Integer, Genero> genres;
    private static MyHash<Integer, Coleccion> collections;
    private static MyHash<String, Idioma> languages;
    private static MyHash<String, Director> directors;
    private static MyHash<Integer, Actor> actors;

    // Bandera para controlar que los datos se carguen una sola vez
    private static boolean dataLoaded = false;

    /**
     * Punto de entrada para iniciar la aplicación.
     * Muestra el menú principal y gestiona el flujo del programa.
     */
    public static void iniciar() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while (!exit) {
            System.out.println("\nSeleccione la opcion que desee:");
            System.out.println("1. Carga de datos");
            System.out.println("2. Ejecutar consultas");
            System.out.println("3. Salir");
            System.out.print("Opcion: ");

            try {
                int option = scanner.nextInt();
                exit = handleMainMenuOption(option);
            } catch (InputMismatchException e) {
                System.out.println("\nERROR: Opcion no valida. Ingrese un numero entre 1 y 3.");
                scanner.next(); // Limpia el buffer del scanner
            }
        }
        scanner.close();
    }

    /**
     * Procesa la opción seleccionada en el menú principal.
     */
    private static boolean handleMainMenuOption(int option) {
        switch (option) {
            case 1:
                if (!dataLoaded) {
                    long startTime = System.currentTimeMillis();
                    cargarDatos(false); // Carga en modo normal
                    long endTime = System.currentTimeMillis();
                    System.out.println("Carga de datos exitosa, tiempo de ejecución de la carga: " + (endTime - startTime) + " ms");
                    dataLoaded = true;
                } else {
                    System.out.println("\nAVISO: Los datos ya han sido cargados previamente.");
                }
                break;
            case 2:
                if (dataLoaded) {
                    showQueriesMenu();
                } else {
                    System.out.println("\nERROR: Debe cargar los datos primero (Opcion 1).");
                }
                break;
            case 3:
                return true; // Señal para salir del bucle principal
            case 3435: // Opción oculta para modo desarrollador
                if (!dataLoaded) {
                    cargarDatos(true);
                    dataLoaded = true;
                }
                break;
            default:
                System.out.println("\nERROR: Opcion no valida. Ingrese un numero entre 1 y 3.");
        }
        return false;
    }

    /**
     * Muestra el menú de consultas y gestiona la selección del usuario.
     */
    private static void showQueriesMenu() {
        boolean back = false;
        Scanner scanner = new Scanner(System.in);

        while (!back) {
            System.out.println("\n1. Top 5 de las peliculas que mas calificaciones por idioma.");
            System.out.println("2. Top 10 de las peliculas que mejor calificacion media tienen por parte de los usuarios.");
            System.out.println("3. Top 5 de las colecciones que mas ingresos generaron.");
            System.out.println("4. Top 10 de los directores que mejor calificacion tienen.");
            System.out.println("5. Actor con mas calificaciones recibidas en cada mes del año.");
            System.out.println("6. Usuarios con mas calificaciones por genero");
            System.out.println("7. Salir");
            System.out.print("Opcion: ");

            try {
                int option = scanner.nextInt();
                back = handleQueryMenuOption(option);
            } catch (InputMismatchException e) {
                System.out.println("\nERROR: Opcion no valida. Ingrese un numero entre 1 y 7.");
                scanner.next(); // Limpia el buffer
            }
        }
    }

    /**
     * Llama a la clase de consulta correspondiente según la opción del usuario.
     */
    private static boolean handleQueryMenuOption(int option) {
        switch (option) {
            case 1: TopPeliculasPorIdioma.realizarConsulta(languages); break;
            case 2: TopPeliculas.realizarConsulta(movies); break;
            case 3: TopSagaConMayorIngresos.realizarConsulta(collections); break;
            case 4: TopDirectores.realizarConsulta(directors); break;
            case 5: TopActorPorMes.realizarConsulta(actors); break;
            case 6: TopUsuarioPorGenero.realizarConsulta(genres); break;
            case 7:
                return true;
            default:
                System.out.println("ERROR: Opcion no valida. Ingrese un numero entre 1 y 7.");
        }
        return false;
    }

    /**
     * Orquesta la carga de todos los datos desde los archivos CSV.
     */
    private static void cargarDatos(boolean isDeveloperMode) {
        if (!isDeveloperMode) System.out.println("\nIniciando proceso de carga de datos...");

        CargaDePeliculas movieLoader = new CargaDePeliculas(isDeveloperMode);
        movies = movieLoader.getPeliculas();
        genres = movieLoader.getGeneros();
        languages = movieLoader.getIdiomas();
        collections = movieLoader.getColecciones();
        if (!isDeveloperMode) System.out.println("Carga de peliculas completada.");

        CargaDeEvaluaciones reviewLoader = new CargaDeEvaluaciones(isDeveloperMode);
        try { reviewLoader.cargarDatos(movies); } catch (Exception ignored) {}
        if (!isDeveloperMode) System.out.println("Carga de evaluaciones completada.");

        CargaDeStaff staffLoader = new CargaDeStaff(isDeveloperMode);
        try {
            staffLoader.cargarDatos(movies);
            directors = staffLoader.getDirectores();
            actors = staffLoader.getActores();
        } catch (Exception ignored) {}
        if (!isDeveloperMode) System.out.println("Carga de creditos completada.");
    }
}