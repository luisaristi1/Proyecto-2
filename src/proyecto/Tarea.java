package proyecto;

import java.util.Date;
import java.util.Scanner;

public class Tarea extends Actividad {
	
	//Atributos
    private String medioEntrega;

    //Constructor
	public Tarea(LearningPath lp,String nombre, String descripcion, String objetivo, String nivelDificultad, int duracionEsperada,
			boolean obligatorio, Profesor creador) {
		super(lp, nombre, descripcion, objetivo, nivelDificultad, duracionEsperada, obligatorio, creador);
		// TODO Auto-generated constructor stub
		this.medioEntrega = "";
		this.tipo = "Tarea";
	}
	
	//Get and set
	public String getMedioEntrega() {
		return medioEntrega;
	}

	//Metodos

    public void enviarTarea(Scanner scanner, ProgresoActividad progreso) {
	    System.out.print("Ingrese el medio de envío de la tarea: ");
    	this.medioEntrega = scanner.nextLine();
    	respuesta.put(progreso.getEstudiante(), medioEntrega);
        System.out.println("Has enviado la tarea: " + descripcion);
    }
    
	public void cambios(Scanner scanner) {
	    System.out.print("Que desea editar (Descripcion, Objetivo): ");
	    String editar = scanner.nextLine().toLowerCase();
	    if (editar.equals("descripcion")) {
	    	System.out.print("Ingrese la nueva descripcion: ");
		    String d = scanner.nextLine();
		    this.descripcion = d;
	    } else if (editar.equals("objetivo")) {
	    	System.out.print("Ingrese el objetivo de la actividad: ");
		    String o = scanner.nextLine();
		    this.objetivo = o;
	    } else {
	    	System.out.println("Opción no válida");
	    }
	}


	@Override
	public void realizar(ProgresoActividad progreso) {
		// TODO Auto-generated method stub
        Scanner scanner = new Scanner(System.in);
		enviarTarea(scanner, progreso);
		progreso.marcarRealizada("Enviada", new Date());
	}

	@Override
	public void editar(Profesor editor) {
		if (this.creador == editor) {
			Scanner scanner = new Scanner(System.in);
			cambios(scanner);
		} else {
			System.out.println("No tiene los permisos para editar la actividad");
		}
		
	}

	
}

