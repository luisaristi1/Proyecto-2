package proyecto;

import java.util.Scanner;


public class Main {
	
	public void correrAplicacion(Scanner scanner, Registro sistema)
	{
	    try
	    {
	        String archivoUsuarios = "usuarios.json"; 
	        sistema.cargarUsuarios( "./datos/" + archivoUsuarios);
	        //Usuarios ya registrados
	        //sistema.registrarProfesor(new Profesor("Gomez", "gomez@example.com", "password123"));
	        //sistema.registrarEstudiante(new Estudiante("Luis", "luis@example.com", "pass456"));
	        
	        //sistema.salvarUsuarios("./datos/" + archivoUsuarios);
	        
	     // Inicio de sesión
            System.out.print("¿Ya tiene una cuenta? (si/no)\n");
            String cuenta = scanner.nextLine().toLowerCase();
            if (cuenta.equals("si")) {
                System.out.print("Ingrese su correo: \n");
                String correo = scanner.nextLine();
                System.out.print("Ingrese su contraseña: \n");
                String contrasena = scanner.nextLine();
                System.out.print("¿Cómo desea entrar?\n1.Estudiante\n2.Profesor\n");
                String tipoUsuario = scanner.nextLine();
                
                if (tipoUsuario.equals("1")) {
                	Estudiante a = sistema.loginEstudiante(correo, contrasena);
                	System.out.println("¡Bienvenido " + a.getNombre() + "!");
                	//logicaEstudiante(a, scanner, sistema);
                } 
                else {
                	Profesor p = sistema.loginProfesor(correo, contrasena);
                	System.out.println("Bienvenido " + p.getNombre() + "!");
                	//logicaProfesor(p, scanner, sistema);
                }
            
            } else if (cuenta.equals("no")) {
                // Registro de usuario
                System.out.print("¿Cómo desea registrarse?\n1. Estudiante\n2. Profesor\n");
                String tipoRegistro = scanner.nextLine();
                System.out.print("Ingrese su nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("Ingrese su correo: ");
                String correo = scanner.nextLine();
                System.out.print("Ingrese su contraseña: ");
                String contrasena = scanner.nextLine();

                if (tipoRegistro.equals("1")) {
                    Estudiante nuevoEstudiante = new Estudiante(nombre, correo, contrasena);
                    sistema.registrarEstudiante(nuevoEstudiante);
                    System.out.println("Estudiante registrado exitosamente.");
                } else if (tipoRegistro.equals("2")) {
                    Profesor nuevoProfesor = new Profesor(nombre, correo, contrasena);
                    sistema.registrarProfesor(nuevoProfesor);
                    System.out.println("Profesor registrado exitosamente.");
                } else {
                    System.out.println("Opción no válida.");
                }
                sistema.salvarUsuarios("./datos/" + archivoUsuarios);
            } else {
                System.out.println("Opción no válida.");
            }
	    }
	    catch(Exception e )
	    {
	        e.printStackTrace( );
	    }
	}
	
    public static void main(String[] args) {
        
    	// Funciones de la aplicacion
    	
        System.out.println("Bienvenido a la aplicación de Learning Path\n");
        

        System.out.println("1. Prueba de Registro e Inicio de sesión\n");
    	Registro sistema = new Registro();
    	Main main = new Main();
    	Scanner scanner = new Scanner(System.in);
    	main.correrAplicacion(scanner, sistema);
        
        System.out.println("\n2. Funcionalidades de los Profesores\n");
    	
    	// Profesores para el ejemplo
    	Profesor p = new Profesor("Profe. Ivan", "ivancho@example.com", "password123");
        Profesor p2 = new Profesor("Profe. Castillo", "castillo@example.com", "pass456");
    	      
        System.out.println("\n2.1 Creación de Learning Paths\n");
        LearningPath lp = p.crearLearningPath("Java Programming", 
            "Aprende los fundamentos de Java", 
            "Dominar los conceptos de java y POO\nAprender un nuevo lenguaje", 
            "Intermedio", 8, sistema);


        System.out.println("\n2.2 Creación de Actividades para el Learning Path\n");
        
        //Creación de un examen para poder mostrar siempre la funcion de calificar actividades
        Tarea tarea = new Tarea(lp, "Tarea: ejercicios de práctica", "Aprender tecnicas de resolución de problemas con programación", "Bajo", 60, true, p);
        Actividad a1 = p.crearActividad(scanner);
        Actividad a2 = p.crearActividad(scanner);
        
        System.out.println("\n2.3 Agregar Prerrequisitos y Actividades de Seguimiento\n");
        p.agregarPrerrequisitoActividad(a2, a1);
        p.agregarPrerrequisitoActividad(a2, tarea);
        p.agregarPrerrequisitoActividad(tarea, a1);
        
        p.agregarActividadSeguimiento(a1, tarea);
        p.agregarActividadSeguimiento(tarea, a2);
        
        p.añadirActividadALearningPath(tarea);

        System.out.println("\n2.4 Calificación de Actividades (caso: vacio) \n");
        p.calificarActividad(tarea, scanner);

    	
    	System.out.println("\n3. Funcionalidades de los Estudiantes\n");
    	
    	 // Creación de un estudiante de prueba
        Estudiante estudiante = new Estudiante("Laura", "correo@example.com", "pswrd123");

        
    	//Inscribirse a un learning path
        System.out.println("\n3.1 Inscripción en un Learning Path\n");
        LearningPath learningPathEstudiante = estudiante.inscribirseEnLearningPath(scanner, sistema);

        System.out.println("\n3.2 Pedir Recomendación de Actividades\n");
        estudiante.pedirRecomendacionActividad(learningPathEstudiante);
        
        System.out.println("\n3.3 Realizar una Actividad\n");
        estudiante.iniciarActividad(tarea);
        estudiante.realizarActividad(tarea);
        
        System.out.println("\n3.4 Pedir otra recomendación\n");
        estudiante.pedirRecomendacionActividad(learningPathEstudiante);

        System.out.println("\n3.5 Selección y Realización de Actividades\n");
        Actividad act1 = estudiante.seleccionarActividad(scanner, learningPathEstudiante);
        estudiante.realizarActividad(act1);

        System.out.println("\n3.6 Pedir progreso\n");
        estudiante.pedirProgresoPath(lp);;

        System.out.println("\n3.7 Selección y Realización de Actividades\n");
        Actividad act2 = estudiante.seleccionarActividad(scanner, learningPathEstudiante);
        estudiante.realizarActividad(act2);

        System.out.println("\n3.8 Reseñar una Actividad\n");
        estudiante.darReseñaActividad(act2, "Muy buena tarea, me gustó mucho", (float) 9.5);

        System.out.println("\nPromedio de rating de la tarea: " + act2.calcularPromedioRating());
        System.out.println("\nPromedio de rating del Learning Path: " + learningPathEstudiante.calcularPromedioRating());
    
 
        System.out.println("\n3.8 Empezar una actividad después de terminar el learning path\n");
        estudiante.seleccionarActividad(scanner, learningPathEstudiante);
            
        System.out.println("\nCalificación de actividad\n");
        p.calificarActividad(tarea, scanner);
        
        System.out.println("\n3.9. Nuevo progreso luego de calificación \n");
        estudiante.pedirProgresoPath(lp);
        
        System.out.println("\n4. Casos de Error y Manejo del Programa\n");

        System.out.println("4.1 Error: Intentar editar una actividad sin permisos\n");
        p2.editarActividad(tarea); // 

        System.out.println("\n4.2 Error: Intentar agregar actividad a un Learning Path sin ser creador\n");
        p2.añadirActividadALearningPath(tarea); 
 
        System.out.println("\n4.3 Clonar una actividad para poder editarla\n");
        Actividad tareaClonada = p2.clonarActividad(tarea);
        System.out.print("Descripción orginal: "+ tareaClonada.descripcion +"\n");
        System.out.print("Objetivo orginal: "+ tareaClonada.objetivo +"\n");
        p2.editarActividad(tareaClonada);
        System.out.print(tareaClonada.descripcion +"\n");
        System.out.print(tareaClonada.objetivo +"\n");

        System.out.println("\n4. Error: Intentar calificar una actividad sin ser el creador\n");
        p2.calificarActividad(tarea, scanner);

        
    }
    
}
