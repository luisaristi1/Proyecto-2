package proyecto;

import Persistencia.ManejoPersistencia;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ManejoPersistencia persistencia = new ManejoPersistencia();
        Scanner scanner = new Scanner(System.in);

        // Load data
        persistencia.cargarProfesores();
        persistencia.cargarEstudiantes();
        persistencia.cargarLearningPaths();
        persistencia.cargarProgresoPaths();
        persistencia.cargarProgresoActividades();

        boolean salir = false;

        while (!salir) {
            System.out.println("\nBienvenido al sistema de Learning Paths");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcionPrincipal = Integer.parseInt(scanner.nextLine());

            switch (opcionPrincipal) {
                case 1 -> iniciarSesion(persistencia, scanner);
                case 2 -> registrarse(persistencia, scanner);
                case 3 -> {
                    System.out.println("Gracias por usar el sistema. Hasta luego.");
                    salir = true;
                }
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
        scanner.close();
    }

    private static void iniciarSesion(ManejoPersistencia persistencia, Scanner scanner) {
        System.out.println("\nIniciar sesión");
        System.out.print("Ingrese su correo: ");
        String correo = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        // Try as Professor
        Profesor profesor = persistencia.buscarProfe(correo);
        if (profesor != null && profesor.getContrasena().equals(contrasena)) {
            System.out.println("Inicio de sesión exitoso como Profesor.");
            menuProfesor(profesor, persistencia, scanner);
            return;
        }

        // Try as Student
        Estudiante estudiante = persistencia.buscarEstu(correo);
        if (estudiante != null && estudiante.getContrasena().equals(contrasena)) {
            System.out.println("Inicio de sesión exitoso como Estudiante.");
            menuEstudiante(estudiante, persistencia, scanner);
            return;
        }

        System.out.println("Error: Correo o contraseña incorrectos.");
    }

    private static void registrarse(ManejoPersistencia persistencia, Scanner scanner) {
        System.out.println("\nRegistro de usuario");
        System.out.println("1. Registrarse como Profesor");
        System.out.println("2. Registrarse como Estudiante");
        System.out.print("Seleccione una opción: ");
        int opcion = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese su correo: ");
        String correo = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        if (opcion == 1) {
            // Register as Professor
            persistencia.crearProfesorData(nombre, correo, contrasena);
            System.out.println("Profesor registrado exitosamente.");
        } else if (opcion == 2) {
            // Register as Student
            persistencia.crearEstudianteData(nombre, correo, contrasena);
            System.out.println("Estudiante registrado exitosamente.");
        } else {
            System.out.println("Opción inválida. Registro cancelado.");
        }
    }

    private static void menuProfesor(Profesor profesor, ManejoPersistencia persistencia, Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\nMenú Profesor");
            System.out.println("1. Crear Learning Path");
            System.out.println("2. Crear Actividad");
            System.out.println("3. Ver Learning Paths creados");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese el título del Learning Path: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Ingrese la descripción del Learning Path: ");
                    String descripcion = scanner.nextLine();
                    System.out.print("Ingrese los objetivos del Learning Path: ");
                    String objetivos = scanner.nextLine();
                    System.out.print("Ingrese el nivel de dificultad (Bajo, Medio, Alto): ");
                    String nivel = scanner.nextLine();
                    System.out.print("Ingrese la duración estimada en horas: ");
                    int duracion = Integer.parseInt(scanner.nextLine());

                    LearningPath lp = profesor.crearLearningPath(titulo, descripcion, objetivos, nivel, duracion, persistencia);
                    persistencia.crearPathData(lp);
                }
                case 2 -> {
                    Actividad actividad = profesor.crearActividad(scanner);
                    if (actividad != null) {
                        persistencia.crearActividadData(actividad);
                    }
                }
                case 3 -> profesor.verLearningPaths();
                case 4 -> salir = true;
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }



    private static void menuEstudiante(Estudiante estudiante, ManejoPersistencia persistencia, Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\nMenú Estudiante");
            System.out.println("1. Inscribirse en un Learning Path");
            System.out.println("2. Ver progreso de Learning Paths");
            System.out.println("3. Ver actividades disponibles");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> {
                    System.out.println("Learning Paths disponibles:");
                    for (String titulo : persistencia.mostrarPaths()) {
                        System.out.println("- " + titulo);
                    }
                    System.out.print("Ingrese el título del Learning Path al que desea inscribirse: ");
                    String titulo = scanner.nextLine();
                    LearningPath lp = persistencia.buscarPath(titulo);
                    if (lp != null) {
                        estudiante.inscripcion(lp, persistencia);
                        persistencia.crearProgresoPathData(new ProgresoPath(lp, new java.util.Date(), estudiante));
                    } else {
                        System.out.println("Learning Path no encontrado.");
                    }
                }
                case 2 -> {
                    for (ProgresoPath progreso : estudiante.getProgresoPaths().values()) {
                        System.out.println("Learning Path: " + progreso.getLp().getTitulo()); System.out.println("Progreso: " + progreso.getPorcentajePath() + "%"); System.out.println("Completado: " + (progreso.isCompletado() ? "Sí" : "No")); } } case 3 -> { System.out.println("Actividades disponibles en los Learning Paths:"); for (LearningPath lp : estudiante.getLearningPathsInscritos()) { System.out.println("Learning Path: " + lp.getTitulo()); for (Actividad actividad : lp.getActividades()) { System.out.println("- " + actividad.getNombre()); } } } case 4 -> salir = true; default -> System.out.println("Opción inválida. Intente nuevamente."); } } } }

