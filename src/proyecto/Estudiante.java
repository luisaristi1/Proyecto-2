package proyecto;

import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Estudiante extends Usuario {
	

	//Atributos
    private List<LearningPath> learningPathsInscritos;
	private Map<Actividad, ProgresoActividad> progresosAct;
	private Map<LearningPath, ProgresoPath> progresoPaths;
	private List<Actividad> realizadas;
	private boolean actividadEnProgreso;
	//Constructor
	public Estudiante(String nombre, String correo, String contrasena) {
		super(nombre, correo, contrasena);
		this.learningPathsInscritos = new ArrayList<>();
		this.progresosAct = new HashMap<Actividad, ProgresoActividad>();
		this.progresoPaths = new HashMap<LearningPath, ProgresoPath>();
		this.actividadEnProgreso = false;
		this.realizadas = new ArrayList<>();
		// TODO Auto-generated constructor stub
	}

	//Get and set
	public Map<Actividad, ProgresoActividad> getProgresosAct() {
		return progresosAct;
	}
	
	public Map<LearningPath, ProgresoPath> getProgresoPaths() {
		return progresoPaths;
	}
	
	public List<LearningPath> getLearningPathsInscritos() {
		return learningPathsInscritos;
	}

	//Metodos
	
	@Override
	public String getTipoUsuario() {
		return "Estudiante";
	}
	
	@Override
    public void verLearningPaths() {
        System.out.println("Learning Paths en los que estás inscrito:");
        for (LearningPath lp : learningPathsInscritos) {
            System.out.println("- " + lp.getTitulo());
        }
    }
	
	public void darReseñaActividad(Actividad actividad, String texto, float rating) {
		ProgresoActividad prog = progresosAct.get(actividad);
		
		if (prog.isCompletada()) {
			Reseña reseña = new Reseña(texto, rating);
			reseña.hacerReseña(actividad);
		} else {
			System.out.println("Debes realizar una actividad para poder darle una reseña.");
		}
		
	}

	
    public LearningPath inscribirseEnLearningPath(Scanner scanner, Registro sistema) {
    	
    	LearningPath rta = null;
    	List<LearningPath> catalogo = sistema.getPaths();
    	
    	if (catalogo.isEmpty()) {
            System.out.println("No hay Learning Paths disponibles en este momento.");
            return null;
        }
    	
    	//Mostrar catalogo
        System.out.println("Learning Paths disponibles:");
        for (int i = 0; i < catalogo.size(); i++) {
            LearningPath lp = catalogo.get(i);
            System.out.println((i + 1) + ". " + lp.getTitulo() + " - " + lp.getDescripcion());
        }
        
        //Seleccion de opcion
        System.out.print("Ingrese el número del Learning Path al que desea inscribirse: ");
        
        //validacion
        while (true) {
            try {
            	int seleccion = Integer.parseInt(scanner.nextLine());
                if (seleccion < 1 || seleccion > catalogo.size()) {
                	System.out.println("Selección no válida. Por favor, intente nuevamente.");
                } else {
                	rta = catalogo.get(seleccion - 1);
                	inscripcion(rta);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
    	        }
        }
        return rta;
    }
    		 
    public void inscripcion(LearningPath learningPath) {

        if (!learningPathsInscritos.contains(learningPath)) {
            learningPathsInscritos.add(learningPath);
            System.out.println("Te has inscrito exitosamente en el Learning Path: " + learningPath.getTitulo());
			learningPath.mostrarEstructura();
			ProgresoPath avance = new ProgresoPath(learningPath, new Date(), this);
			progresoPaths.put(learningPath, avance);
            for (Actividad actividad : learningPath.getActividades()) {
                ProgresoActividad progreso = new ProgresoActividad(actividad,this);
                progresosAct.put(actividad, progreso);
            }
            
        } else {
            System.out.println("Ya estás inscrito en este Learning Path.");
        }
    }
    
    public Actividad seleccionarActividad(Scanner scanner, LearningPath learningPath){
    	
    	ProgresoPath path = progresoPaths.get(learningPath);
    	
		if (path.getPorcentajePath() == 100) {
			System.out.println("Aviso: ¡Ya has terminado todas las actividades obligatorias del learning Path!");
		}

		if (path.isCompletado()) {
			System.out.println("¡Ya has terminado todas las actividades del learning Path!");
			return null;
		}
    	
    	Actividad rta = null;
    	List<Actividad> yaRealizadas = new ArrayList<>();
		if (!actividadEnProgreso) { 
			if (!learningPathsInscritos.contains(learningPath)) {
	            System.out.println("No puedes realizar esta actividad porque no estás inscrito en su learning path.");
	            return null;
	        }

	        // Mostrar las actividades disponibles dentro del Learning Path
	        List<Actividad> actividades = learningPath.getActividades();
	        if (actividades.isEmpty()) {
	            System.out.println("No hay actividades disponibles en este Learning Path.");
	            return null;
	        }
	        
	        System.out.println("Actividades por realizar en el Learning Path: " + learningPath.getTitulo());
	        for (int i = 0; i < actividades.size(); i++) {
	            Actividad actividad = actividades.get(i);
	            if (!realizadas.contains(actividad)) {
	            	System.out.println((i + 1) + ". " + actividad.getDescripcion());
	            } else {
	            	yaRealizadas.add(actividad);
	            }
	        }
	        
	        int seleccion = 0;
	        boolean opcionValida = false;
	        while (!opcionValida) {
	            System.out.print("Ingrese el número de la actividad que desea realizar: ");
	            try {
	                seleccion = scanner.nextInt();
	                scanner.nextLine(); 

	                if (seleccion < 1 || seleccion > actividades.size()) {
	                    System.out.println("Selección no válida. Por favor, intente nuevamente.");
	                } 
	                else if (yaRealizadas.contains(actividades.get(seleccion - 1))) {
	                    System.out.println("Error. Ya realizó esta actividad.");
	                } 
	                else {
	                    opcionValida = true; //
	                }
	            } catch (InputMismatchException e) {
	                System.out.println("Entrada no válida. Por favor, ingrese un número.");
	                scanner.nextLine();
	            }
	        }
		    
	        rta = actividades.get(seleccion - 1);

	        iniciarActividad(rta);
	        return rta;
	        
		} else {
			System.out.println("No puedes iniciar 2 actividades a la vez.");
		}
		
		return rta;
    }
    
    public void iniciarActividad(Actividad actividad) {
    	
		boolean faltan = faltanPrerrequisitos(actividad);
    	
    	boolean esta = false;
    	Actividad previa = null;
    	Date fecha = null;
    	

    	if  (progresoPaths.containsKey(actividad.getLearningPath())) {
    		ProgresoPath path = progresoPaths.get(actividad.getLearningPath());
    		List<Actividad> lst = path.getActividadesRealizadas();
			if (lst.size()>0) {
    			previa = path.getActividadesRealizadas().get(lst.size() - 1);
			}
    	}
    	
       	if (previa != null) {
       		if (progresosAct.containsKey(previa)) {
        		ProgresoActividad progresoPrevia = progresosAct.get(previa);
        		if (!progresoPrevia.isCompletada()) {
                	fecha = progresoPrevia.getFechaFin();
        		}
       		}
        }
    	
       	ProgresoActividad progreso = progresosAct.get(actividad);
       	
        if (progreso.getActividad().equals(actividad) && !progreso.isCompletada()) {
        	if (faltan) {
        		List<Actividad> pre = actividad.getPrerrequisitos();
        		System.out.println("Advertencia: Te recomendamos completar los prerrequisitos para esta actividad: " );
        		for (Actividad act : pre) {
        			ProgresoActividad prog = progresosAct.get(act);
        			if (!prog.isCompletada())
        				System.out.println(act.descripcion);
        		}
        	}
            System.out.println("\nIniciando actividad: " + actividad.getDescripcion());
            progreso.setFechaInicio(new Date());
            esta =  true;
            actividad.establecerFechaLimite(fecha);
            this.actividadEnProgreso = true;
        }
        
        if (!esta) {
            System.out.println("Ya has completado esta actividad o no está disponible.");
        }
    	
    }
    
    public boolean faltanPrerrequisitos(Actividad actividad) {
    	if (!actividad.getPrerrequisitos().isEmpty()) {
            for (Actividad prerrequisito : actividad.getPrerrequisitos()) {
            	ProgresoActividad progreso = progresosAct.get(prerrequisito);
        		if (!progreso.isCompletada()) {
        			return true;
        		}
            }
    	}
	return false;
    }
    
    public void realizarActividad(Actividad actividad) {
    	if (!actividad.equals(null)) {
        	ProgresoActividad progreso= progresosAct.get(actividad);
        	if (!progreso.isCompletada()) {
                	actividad.realizar(progreso);
                	realizadas.add(actividad);
                	
                	ProgresoPath path = progresoPaths.get(actividad.getLearningPath());
        			path.agregarActividadRealizada(actividad);
        			path.marcarCompletado();
        			
                	this.actividadEnProgreso = false;
                	return ;
                } else {
                	System.out.println("No puedes realizar una actividad sin antes comenzarla.");
                	return ;
            }
        	
        } 
        System.out.println("No se encontró la actividad o ya ha sido completada.");	
    } 
    
    public void pedirRecomendacionActividad(LearningPath lp) {
    	if (learningPathsInscritos.contains(lp)) {
    		List<Actividad> lst = null;
        	ProgresoActividad p1 = null;
    		Actividad ultima = null;
    		Actividad anterior = null;
        	
    		//Obtener actividades realizadas
        	Collection<ProgresoPath> paths = progresoPaths.values();
        	for (ProgresoPath p: paths) {
                if (p.getLp().equals(lp)) {
                	lst = p.getActividadesRealizadas();
                }
        	}
        	
        	//Obtener ultima actividad y su progreso
        	if (lst != null && lst.size()>0) {
        		ultima = lst.get(lst.size() -1);
            	Collection<ProgresoActividad> acts = progresosAct.values();
            	for (ProgresoActividad q: acts) {
        			if (q.getActividad().equals(ultima)) {
        				p1 = q;
        			}
    			}
        	} 
        	
        	//Obtener actividad anterior 
        	if (lst.size()>1) {
        		anterior = lst.get(lst.size() -2); //revisar index
    		}
        	
        	//Casos
        	if (p1 != null && anterior != null) {
        		ultima.recomendarActividad(p1, anterior);
        	} else if (p1 != null && anterior == null) {
        		ultima.recomendarActividad(p1, ultima);
        	} else if (p1 == null && anterior == null) {
        		System.out.println("Te recomendamos empezar por la actividad: " + lp.getActividades().get(0).descripcion);
        	}
        	
    	} else {
    		System.out.println("No te encuentras inscrito en este learning path.");
    	}
    }

    public void pedirProgresoPath(LearningPath lp) {
    	if (learningPathsInscritos.contains(lp)){
    		ProgresoPath prog = progresoPaths.get(lp);
    		prog.calcularProgreso();
    		prog.actualizarTasas();
    		System.out.println("Llevas un " + prog.getPorcentajePath() + "% del learning path " + lp.getDescripcion() +"\n");
    		System.out.println("Tienes una tasa de exito de: "+ prog.getTasaExito());

    	}
    }
    
    public Boolean verificarPathExistente(LearningPath lp) {
    	Boolean rta = false; //por defecto se asume que no está
		for (LearningPath path: learningPathsInscritos) {
			if (path.equals(lp)) {
				rta = true;
			}
		}
		return rta;
    }
    

}
