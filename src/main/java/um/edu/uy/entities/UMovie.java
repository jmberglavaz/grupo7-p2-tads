package um.edu.uy.entities;

import um.edu.uy.Sistema.CargaDeDatos.CargaDeEvaluaciones;
import um.edu.uy.Sistema.CargaDeDatos.CargaDePeliculas;
import um.edu.uy.Sistema.CargaDeDatos.CargaDeStaff;
import um.edu.uy.Sistema.Consultas.*;
import um.edu.uy.TADs.Hash.MyHash;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UMovie {
    private static MyHash<Integer, Pelicula> peliculas;
    private static MyHash<Integer, Genero> generos;
    private static MyHash<Integer, Coleccion> colecciones;
    private static MyHash<String, Idioma> idiomas;
    private static MyHash<String,Director> directores;
    private static MyHash<Integer, Actor> actores;
    private static boolean datosCargados = false;

    public UMovie() {
    }

    public static void iniciar(){
        boolean encendido = true;

        while (encendido) {
            System.out.println("""
                    Seleccione una opción:
                    1. Carga de Datos
                    2. Ejecutar Consultas
                    3. Salir
                    """);
            Scanner scanner = new Scanner(System.in);
            try {
                int opcion = scanner.nextInt();
                encendido = verificarOpcionPrincipal(opcion);
            } catch (InputMismatchException e){
                System.out.println("ERROR: Opción no válida, ingrese un número entre 1 y 3.");
            }
        }
    }


    private static boolean verificarOpcionPrincipal(int opcion){
        switch (opcion) {
            case 1 -> {
                if(!datosCargados){
                    cargarDatos();
                    datosCargados = true;
                } else {
                    System.out.println("Los datos ya estan cargados.");
                }
            }
            case 2 -> iniciarMenuConsultas();
            case 3 -> {
                System.out.println("Saliendo del sistema...");
                return false;
            }
            case 3435 -> {
                if (!datosCargados) {
                    cargarDatos(true);
                    datosCargados = true;
                }
            }
            default -> System.out.println("ERROR: Opción no válida, ingrese un número entre 1 y 3.");
        }
        return true;
    }

    private static void iniciarMenuConsultas(){
        boolean encendido = true;

        while (encendido) {
            System.out.println("""
                    Menú de opciones
                    1. Top 5 de las películas que más calificaciones por idioma
                    2. Top 10 de las películas que mejor calificación media tienen por parte de los usuarios
                    3. Top 5 de las colecciones que más ingresos generaron
                    4. Top 10 de los directores que mejor calificación tienen
                    5. Actor con más calificaciones recibidas en cada mes del año
                    6. Usuarios con más calificaciones por género
                    7. Salir
                    Elija una opción(1-7):\s""");
            Scanner scanner = new Scanner(System.in);
            try {
                int opcion = scanner.nextInt();
                encendido = verificarOpcionConsultas(opcion);
            } catch (InputMismatchException e){
                System.out.println("ERROR: Opción no válida, ingrese un número entre 1 y 7.");
            }
        }
    }

    private static boolean verificarOpcionConsultas(int opcion){
        switch (opcion) {
            case 1 -> TopPeliculasPorIdioma.realizarConsulta(idiomas);
            case 2 -> TopPeliculas.realizarConsulta(peliculas);
            case 3 -> System.out.println("La tengo que arreglar con los de las FK"); //.realizarConsulta(peliculas, colecciones);
            case 4 -> TopDirectores.realizarConsulta(directores);
            case 5 -> TopActorPorMes.realizarConsulta(peliculas, actores); //System.out.println("Funcion de actor mejor calificado por cada mes (Pendiente)");
            case 6 -> TopUsuarioPorGenero.realizarConsulta(generos);
            case 7 -> {
                System.out.println("Volviendo atras...");
                return false;
            }
            default -> System.out.println("ERROR: Opción no válida, ingrese un número entre 1 y 7.");
        }
        return true;
    }

    private static void cargarDatos() {
        cargarDatos(false);
    }

    private static void cargarDatos(boolean DeveloperMode) {
        long inicio = DeveloperMode ? System.currentTimeMillis() : 0;
        CargaDePeliculas cargaPeliculas = new CargaDePeliculas(DeveloperMode);
        CargaDeEvaluaciones cargaEvaluaciones = new CargaDeEvaluaciones(DeveloperMode);
        CargaDeStaff cargaDeStaff = new CargaDeStaff(DeveloperMode);

        peliculas = cargaPeliculas.getPeliculas();
        generos = cargaPeliculas.getGeneros();
        idiomas = cargaPeliculas.getIdiomas();
        colecciones = cargaPeliculas.getColecciones();
        System.out.println("Carga de peliculas completada");

        try {
            cargaEvaluaciones.cargarDatos(peliculas);
        } catch (Exception ignored) {}
        System.out.println("Carga de evaluaciones completada.");

        try {
            cargaDeStaff.cargarDatos(peliculas);
            directores = cargaDeStaff.getDirectores();
            actores = cargaDeStaff.getActores();
        } catch (Exception ignored) {}
        System.out.println("Carga de creditos completada.");

        if (DeveloperMode) {
            System.out.println("\n ===== TIEMPO TOTAL DE CARGA: " + (System.currentTimeMillis() - inicio) + "ms =====\n");
        }
    }
}
