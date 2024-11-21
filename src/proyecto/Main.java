package proyecto;

import java.util.Scanner;
import Persistencia.ManejoPersistencia;

public class Main {

    public static void menuProfesor(Profesor profesor, ManejoPersistencia persistencia, Scanner scanner, Registro registro) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\nMenú Profesor:");
            System.out.println("1. Crear un Learning Path");
            System.out.println("2. Crear una Actividad");
            System.out.println("3. Ver Learning Paths creados");
            System.out.println("4. Clonar una Actividad");
            System.out.println("5. Calificar una Actividad");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("Ingrese el título del Learning Path: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Ingrese la descripción: ");
                    String descripcion = scanner.nextLine();
                    System.out.print("Ingrese los objetivos: ");
                    String objetivos = scanner.nextLine();
                    System.out.print("Ingrese el nivel de dificultad (Bajo, Medio, Alto): ");
                    String nivel = scanner.nextLine();
                    System.out.print("Ingrese la duración estimada en horas: ");
                    int duracion = Integer.parseInt(scanner.nextLine());

                    LearningPath nuevoLP = profesor.crearLearningPath(titulo, descripcion, objetivos, nivel, duracion, registro);
                    persistencia.crearPathData(nuevoLP);
                    System.out.println("Learning Path creado exitosamente.");
                    break;

                case "2":
                    Actividad actividad = profesor.crearActividad(scanner);
                    if (actividad != null) {
                        persistencia.crearActividadData(actividad);
                        System.out.println("Actividad creada y añadida al Learning Path.");
                    }
                    break;

                case "3":
                    profesor.verLearningPaths();
                    break;

                case "4":
                    System.out.print("Ingrese el nombre de la actividad a clonar: ");
                    String actividadAClonar = scanner.nextLine();
                    Actividad original = persistencia.buscarActividad(actividadAClonar);
                    if (original != null) {
                        Actividad clonada = profesor.clonarActividad(original);
                        if (clonada != null) {
                            persistencia.crearActividadData(clonada);
                        }
                    } else {
                        System.out.println("Actividad no encontrada.");
                    }
                    break;

                case "5":
                    System.out.print("Ingrese el nombre de la actividad a calificar: ");
                    String actividadACalificar = scanner.nextLine();
                    Actividad actividadPorCalificar = persistencia.buscarActividad(actividadACalificar);
                    if (actividadPorCalificar != null) {
                        profesor.calificarActividad(actividadPorCalificar, scanner);
                    } else {
                        System.out.println("Actividad no encontrada.");
                    }
                    break;

                case "6":
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }

    public static void menuEstudiante(Estudiante estudiante, ManejoPersistencia persistencia, Scanner scanner) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\nMenú Estudiante:");
            System.out.println("1. Ver Learning Paths inscritos");
            System.out.println("2. Inscribirse en un Learning Path");
            System.out.println("3. Ver progreso en un Learning Path");
            System.out.println("4. Seleccionar una Actividad");
            System.out.println("5. Realizar una Actividad");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    estudiante.verLearningPaths();
                    break;

                case "2":
                    LearningPath lp = estudiante.inscribirseEnLearningPath(scanner, persistencia);
                    if (lp != null) {
                        persistencia.guardarPaths();
                        System.out.println("Inscripción realizada con éxito.");
                    }
                    break;

                case "3":
                    System.out.print("Ingrese el nombre del Learning Path para ver el progreso: ");
                    String nombreLP = scanner.nextLine();
                    LearningPath lpProgreso = persistencia.buscarPath(nombreLP);
                    if (lpProgreso != null) {
                        estudiante.pedirProgresoPath(lpProgreso);
                    } else {
                        System.out.println("Learning Path no encontrado.");
                    }
                    break;

                case "4":
                    System.out.print("Ingrese el nombre del Learning Path: ");
                    String nombreLPActividades = scanner.nextLine();
                    LearningPath lpActividades = persistencia.buscarPath(nombreLPActividades);
                    if (lpActividades != null) {
                        Actividad actividad = estudiante.seleccionarActividad(scanner, lpActividades);
                        if (actividad != null) {
                            persistencia.guardarActividades();
                            System.out.println("Actividad seleccionada: " + actividad.getDescripcion());
                        }
                    } else {
                        System.out.println("Learning Path no encontrado.");
                    }
                    break;

                case "5":
                    System.out.print("Ingrese el nombre de la actividad a realizar: ");
                    String actividadRealizar = scanner.nextLine();
                    Actividad act = persistencia.buscarActividad(actividadRealizar);
                    if (act != null) {
                        estudiante.realizarActividad(act);
                        persistencia.guardarActividades();
                    } else {
                        System.out.println("Actividad no encontrada.");
                    }
                    break;

                case "6":
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Registro registro = new Registro();
        ManejoPersistencia persistencia = new ManejoPersistencia();

        try {
            registro.cargarUsuarios("datos/usuarios.json");
            persistencia.cargarLearningPaths();
            persistencia.cargarProgresoPaths();
            persistencia.cargarProgresoActividades();
            System.out.println("Datos cargados exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al cargar los datos: " + e.getMessage());
        }

        boolean salir = false;
        while (!salir) {
            System.out.println("\nBienvenido al sistema de Learning Paths");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("Ingrese su correo: ");
                    String correo = scanner.nextLine();
                    System.out.print("Ingrese su contraseña: ");
                    String contrasena = scanner.nextLine();
                    System.out.print("Ingrese su tipo de usuario: ");
                    String tipousuario = scanner.nextLine();
                    
                    String tipousuario1 = tipousuario.toLowerCase();
                    try {
                    switch (tipousuario1) {
                    case "profesor":{
                    	Profesor profe = registro.loginProfesor(correo, contrasena);
                        menuProfesor((Profesor) profe, persistencia, scanner, registro);


                    }
                    case "estudiante":{
                    	Estudiante estu = registro.loginEstudiante(correo, contrasena);
                        menuEstudiante((Estudiante) estu, persistencia, scanner);
                    }}}
                     catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                   
                    
                    
                    

                case "2":
                    System.out.println("¿Cómo desea registrarse?");
                    System.out.println("1. Profesor");
                    System.out.println("2. Estudiante");
                    System.out.print("Seleccione una opción: ");
                    String tipoRegistro = scanner.nextLine();

                    System.out.print("Ingrese su nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese su correo: ");
                    correo = scanner.nextLine();
                    System.out.print("Ingrese su contraseña: ");
                    contrasena = scanner.nextLine();

                    if (tipoRegistro.equals("1")) {
                        Profesor nuevoProfesor = new Profesor(nombre, correo, contrasena);
                        registro.registrarProfesor(nuevoProfesor);
                    } else if (tipoRegistro.equals("2")) {
                        Estudiante nuevoEstudiante = new Estudiante(nombre, correo, contrasena);
                        registro.registrarEstudiante(nuevoEstudiante);
                    } else {
                        System.out.println("Opción no válida.");
                    }

                    try {
                        registro.salvarUsuarios("datos/usuarios.json");
                        System.out.println("Usuario registrado exitosamente.");
                    } catch (Exception e) {
                        System.out.println("Error al guardar los datos: " + e.getMessage());
                    }
                    break;

                case "3":
                    salir = true;
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }

        scanner.close();
    }
}

