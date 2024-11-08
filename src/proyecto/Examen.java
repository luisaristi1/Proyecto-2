package proyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Examen extends Actividad {
	

	//Atributos
	private List<String> preguntasAbiertas;
	
	//Constructor
	public Examen(LearningPath lp, String descripcion, String objetivo, String nivelDificultad, int duracionEsperada,
			boolean obligatorio, Profesor creador) {
		super(lp, descripcion, objetivo, nivelDificultad, duracionEsperada, obligatorio, creador);
		this.preguntasAbiertas = new ArrayList<String>();
		// TODO Auto-generated constructor stub
	}
	
	//Get and set
	public List<String> getPreguntasAbiertas() {
		return preguntasAbiertas;
	}
	
	
	//Metodos
	
    public void agregarPregunta(Scanner scanner) {
	    System.out.print("Ingrese la nueva pregunta del examen: ");
	    String texto = scanner.nextLine();
        preguntasAbiertas.add(texto);
    }
    
	public void realizarExamen(ProgresoActividad progreso) {
        try (Scanner scanner = new Scanner(System.in)) {
			for (int i = 0; i < preguntasAbiertas.size(); i++) {
			    System.out.println((i + 1) + ". " + preguntasAbiertas.get(i));
			    System.out.print("Ingrese su respuesta: ");
			    String rta = scanner.nextLine();
			    respuesta.put(progreso.getEstudiante(), rta);
			}
		    System.out.print("Has terminado el examen. Tu resultado se mostrará una vez el profesor califique tus respuestas.\n");
		}
	}

	@Override
	public void realizar(ProgresoActividad progresoEstudiante) {
		// TODO Auto-generated method stub
		realizarExamen(progresoEstudiante);
		progresoEstudiante.marcarRealizada("Enviada", new Date());
		
	}
	
	@Override
	public void editar(Profesor editor) {
		if (this.creador == editor) {
			Scanner scanner = new Scanner(System.in);
			agregarPregunta(scanner);
		} else {
			System.out.println("No tiene los permisos para editar la actividad");
		}
		
	}

}
