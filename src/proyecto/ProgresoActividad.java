package proyecto;
import java.util.Date;
import java.util.concurrent.TimeUnit;

	//Atributos
	public class ProgresoActividad {
	    private Actividad actividad;
	    private boolean completada;
	    private String resultado;
	    private long tiempoDedicado;
	    private Date fechaInicio;
	    private Date fechaFin;
	    private Estudiante estudiante;
  
    //Constructor
    public ProgresoActividad(Actividad actividad, Estudiante estudiante) {
        this.actividad = actividad;
        this.completada = false; 
        this.resultado = "Por completar"; 
        this.tiempoDedicado = 0; 
        this.fechaInicio = null;
        this.fechaFin = null;
        this.estudiante = estudiante;
        
    }
    
    //Get y set
    public Actividad getActividad() {
        return actividad;
    }

    public boolean isCompletada() {
        return completada;
    }

    public String getResultado() {
        return resultado;
    }
    
    public void setResultado(String resultado) {
        this.resultado= resultado;
    }

    public long getTiempoDedicado() {
        return tiempoDedicado;
    }
    
	public Date getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public Date getFechaFin() {
		return fechaFin;
	}
	
	public void setFechaFin(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Estudiante getEstudiante() {
		return estudiante;
	}

	//Métodos
	
	public long calcularTiempoDedicado(Date inicio, Date fin) {
        long diff = fin.getTime() - inicio.getTime(); // Diferencia en milisegundos
        return TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS); // Convertir a horas
		
	}
	
    public void completarActividad(String rta) {
        this.resultado = rta;
    }
    
    public void marcarRealizada(String resultado, Date fecha) {
        this.completada = true;
        this.resultado = resultado;
        this.fechaFin = fecha;
        this.tiempoDedicado = calcularTiempoDedicado(fechaInicio, fechaFin);
    }


}
