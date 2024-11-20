package proyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LearningPath {
	
	//Atributos
	private String titulo;
    private String descripcion;
    private String objetivos;
    private String nivelDificultad;
    private int duracionEstimada;
    private double rating;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private int version;
    private List<Actividad> actividades;
    private Profesor creador; // El profesor que creó el Learning Path
    
	//Constructor
    public LearningPath(String titulo, String descripcion, String objetivos, String nivelDificultad, Profesor creador, int duracionEstimada) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.objetivos = objetivos;
		this.nivelDificultad = nivelDificultad;
		this.creador = creador;
        this.actividades = new ArrayList<>();
        this.fechaCreacion = new Date();
        this.fechaModificacion = new Date();
        this.duracionEstimada = duracionEstimada;
        this.version = 1;
        this.rating = calcularPromedioRating();
        //TO-DO RATING
	}
    
    //Get and set
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
        this.titulo = titulo;
        this.fechaModificacion = new Date(); // Actualiza la fecha de modificación
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        this.fechaModificacion = new Date(); // Actualiza la fecha de modificación
	}
	public String getObjetivos() {
		return objetivos;
	}
	public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
        this.fechaModificacion = new Date(); // Actualiza la fecha de modificación
	}
	public String getNivelDificultad() {
		return nivelDificultad;
	}
	public void setNivelDificultad(String nivelDificultad) {
		this.nivelDificultad = nivelDificultad;
	}
	public int getDuracionEstimada() {
		return duracionEstimada;
	}
	public double getRating() {
		return rating;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date date) {
		this.fechaCreacion= date ;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date date) {
		this.fechaModificacion= date ;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version= version ;
	}
	public List<Actividad> getActividades() {
		return actividades;
	}
	public Profesor getCreador() {
		return creador;
	}
	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}

	//Metodos
    public void añadirTiempoLp(Actividad actividad) {
        this.duracionEstimada += actividad.getDuracionEsperada();
    }
    
    public void reducirTiempoLp(Actividad actividad) {
        this.duracionEstimada -= actividad.getDuracionEsperada();
    }
    
    public void mostrarEstructura() {
        System.out.println("Estructura del Learning Path: " + this.titulo);
        for (Actividad actividad : actividades) {
            System.out.println("Actividad: " + actividad.getDescripcion());
            System.out.println(" - Nivel de dificultad: " + actividad.getNivelDificultad());
            System.out.println(" - Duración: " + actividad.getDuracionEsperada() + " minutos");
            System.out.println(" - Objetivo: " + actividad.getObjetivo());
        }
    }
    
    //Rating
    public double calcularPromedioRating() {
        if (actividades.isEmpty()) {
            return 0; // Si no hay actividades, el promedio es 0
        }
        float total = 0;
        int count = 0;
        for (Actividad actividad : actividades) {
            float rating = actividad.calcularPromedioRating();
            if (rating > 0) {
                total += rating;
                count++;
            }
        }
        if (count > 0) {
        	this.rating = total/count;
        	return this.rating;
        } else {
        	this.rating = 0;
        	return this.rating;
        }
    }
}

