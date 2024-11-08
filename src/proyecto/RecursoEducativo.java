package proyecto;

import java.util.Date;
import java.util.Scanner;

public class RecursoEducativo extends Actividad{
	

	//Atributos
	private String tipoRecurso;
	private String enlaceRecurso;

	//Cosntructor
	public RecursoEducativo(LearningPath lp, String descripcion, String objetivo, String nivelDificultad, int duracionEsperada,
			boolean obligatorio, String tipoRecurso, String enlaceRecurso, Profesor creador) {
		super(lp, descripcion, objetivo, nivelDificultad, duracionEsperada, obligatorio, creador);
        this.tipoRecurso = tipoRecurso;
        this.enlaceRecurso = enlaceRecurso;
		// TODO Auto-generated constructor stub
	}
	
	//get and set
	public String getTipoRecurso() {
		return tipoRecurso;
	}

	public String getEnlaceRecurso() {
		return enlaceRecurso;
	}
	
	public void cambios(Scanner scanner) {
	    System.out.print("Que desea editar (Recurso, Enlace): ");
	    String editar = scanner.nextLine().toLowerCase();
	    if (editar.equals("recurso")) {
	    	System.out.print("Ingrese el nuevo tipo de recurso: ");
		    String recurso = scanner.nextLine();
		    this.tipoRecurso = recurso;
	    } else if (editar.equals("enlace")) {
	    	System.out.print("Ingrese el nuevo enlace de la actividad: ");
		    String enlace = scanner.nextLine();
		    this.enlaceRecurso = enlace;
	    } else {
	    	System.out.println("Opción no válida");
	    }
	}

	@Override
	public void realizar(ProgresoActividad progreso) {
		progreso.marcarRealizada("Aprobada", new Date());
		progreso.completarActividad("Aprobada");
        System.out.println("Has completado el recurso educativo: " + descripcion);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editar(Profesor editor) {
		// TODO Auto-generated method stub
		if (this.creador == editor) {
			Scanner scanner = new Scanner(System.in);
			cambios(scanner);
		} else {
			System.out.println("No tiene los permisos para editar la actividad");
		}
	}
}
