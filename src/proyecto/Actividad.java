package proyecto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class Actividad implements Cloneable{
	
	//Atributos
	protected LearningPath learningPath;
	protected String nombre;
	protected String descripcion;
	protected String objetivo;
	protected String nivelDificultad;
	protected int duracionEsperada; //En minutos
	protected Date fechaLimite;
	protected Boolean obligatorio;
    protected List<Actividad> actividadesSeguimiento;
    protected List<Actividad> prerrequisitos;
    protected List<String> nombrePrerrequisitos;
    protected Profesor creador;
    protected List<Reseña> reseñas;
    protected Map<Estudiante, String> respuesta;
    protected String tipo;
	
	//Constructor
	public Actividad(LearningPath lp, String nombre, String descripcion, String objetivo, String nivelDificultad,
			int duracionEsperada, boolean obligatorio, Profesor creador) {
		this.learningPath = lp;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.objetivo = objetivo;
		this.nivelDificultad = nivelDificultad;
		this.duracionEsperada = duracionEsperada;
		this.fechaLimite = null;
		this.nombrePrerrequisitos = null;
		this.obligatorio = obligatorio;
		this.actividadesSeguimiento = new ArrayList<Actividad>();
		this.prerrequisitos = new ArrayList<Actividad>();
		this.creador = creador;
		this.respuesta = new HashMap <Estudiante, String>();
		this.tipo = "";
	}
	
	//Get and set
	
	public String getNombre() {
		return nombre;
	}
	
	public LearningPath getLearningPath() {
		return learningPath;
	}
	
	public void setLearningPath(LearningPath lp) {
		this.learningPath = lp;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public String getNivelDificultad() {
		return nivelDificultad;
	}
	public Date getFechaLimite() {
		return fechaLimite;
	}
	public int getDuracionEsperada() {
		return duracionEsperada;
	}
	public boolean isObligatorio() {
		return obligatorio;
	}
	public void setObligatorio(Boolean obligatorio) {
		this.obligatorio = obligatorio;
	}
	
	public List<Actividad> getPrerrequisitos() {
		return prerrequisitos;
	}
	
	public List<Actividad> getActividadSeguimiento() {
		return actividadesSeguimiento;
	}
	public Profesor getCreador() {
		return creador;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	public void setNombre(String nuevoNombre) {
		this.nombre = nuevoNombre;
	}
	
	public void setNivelDificultad(String nivel) {
		this.nivelDificultad = nivel;
	}
	
	public void setDuracionEsperada(int duracion) {
		this.duracionEsperada = duracion;
	}
	
	public void setCreador(Profesor nuevo) {
		this.creador = nuevo;
	}
	public List<Reseña> getReseñas() {
		return reseñas;
	}
	public Map<Estudiante, String> getRespuesta() {
		return respuesta;
	}
	public String getTipo() {
		return tipo;
	}
	public List<String> getPrerrequisitosNombres(){
		return nombrePrerrequisitos;
	}
	public void setPrerrequisitosNombre(List<String> prerrequisitosString) {
		this.nombrePrerrequisitos = prerrequisitosString;
	}
	//Metodos
    public abstract void realizar(ProgresoActividad progreso);
    public abstract void editar(Profesor editor);
    
    public void establecerFechaLimite(Date fechaAnterior) {
        Calendar calendar = Calendar.getInstance();
    	if (fechaAnterior!= null) {
            calendar.setTime(fechaAnterior);
            calendar.add(Calendar.HOUR_OF_DAY, 3);
    	} else {
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR_OF_DAY, 3);
            
    	}
    	this.fechaLimite = calendar.getTime();
        
    }
    
    public void RecordatorioActividad(ProgresoActividad progreso) {
        if (fechaLimite != null && !progreso.isCompletada()) {
            long tiempoRestante = fechaLimite.getTime() - new Date().getTime();
            if (tiempoRestante <= TimeUnit.HOURS.toMillis(1)) {
                System.out.println("Recordatorio: La actividad '" + descripcion + "' tiene una hora para su fecha límite recomendada.");
            }
        }
    }
    
    public void agregarActividadSeguimiento(Actividad actividad) {
    	if (actividad.getLearningPath().equals(this.learningPath)){
            actividadesSeguimiento.add(actividad);
    	} else {
    		System.out.println("La actividad de seguimiento no pertenece al mismo learning path");
    	}
    }

    public void agregarPrerrequisito(Actividad actividad) {
    	if (actividad.getLearningPath().equals(this.learningPath)){
            prerrequisitos.add(actividad);
    	} else {
    		System.out.println("La actividad prerrequisito no pertenece al mismo learning path");
    	}
    }
    
    public void recomendarActividad(ProgresoActividad pUltima, Actividad anterior) {
        if (pUltima.isCompletada()) {
            if ("Aprobada".equals(pUltima.getResultado())) {
                if (!actividadesSeguimiento.isEmpty()) {
                    System.out.println("Recomendación: Realiza la siguiente actividad -> " + actividadesSeguimiento.get(0).getDescripcion());
                } else {
                	System.out.println("No hay actvidades de seguimiento para recomendar");
                }
            } else if ("Reprobada".equals(pUltima.getResultado())) {
                	String act = anterior.getDescripcion(); //La ultima actividad que completó.
                    System.out.println("Recomendación: Vuelve a realizar la actividad: " + act);
            } else {
            	System.out.println("Aún no te podemos recomendar una actividad porque estás a la espera de la calificación de "+ pUltima.getActividad().descripcion + ".\nInténtalo de nuevo cuando te den el resultado.");
            }
        } else {
        	System.out.println("Aún no has completado la última actividad.");
        }
	} 
    
    public Actividad clonarActividad(Profesor nuevo) {
		try {
			Actividad clon = (Actividad) this.clone(); // Clonar la actividad
			clon.setCreador(nuevo); 
			return clon;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    //Reseñas
    public void agregarReseña(Reseña reseña) {
        if (reseñas == null) {
            reseñas = new ArrayList<>();
        }
        reseñas.add(reseña);
    }
    

	public float calcularPromedioRating() {
	    if (reseñas == null || reseñas.isEmpty()) {
	        return 0; // Si no hay reseñas, el promedio es 0
	    }
	    float total = 0;
	    for (Reseña reseña : reseñas) {
	        total += reseña.getRating();
	    }
	    return total / reseñas.size();
	}

}
