package proyecto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

	//Atributos
	public class ProgresoPath {
	private LearningPath lp;
    private Date fechaInicioPath;
    private Date fechaFinPath;
	private float porcentajePath;
    private float tasaExito;
    private float tasaFracaso;
    private List<Actividad> actividadesRealizadas;
    private boolean completado;
    private Estudiante estudiante;
  
    //Constructor
    public ProgresoPath(LearningPath lp, Date fechaInicioPath, Estudiante estudiante) {
		super();
		this.lp = lp;
		this.fechaInicioPath = fechaInicioPath;
		this.fechaFinPath = null;
		this.porcentajePath = 0;
		this.tasaExito = 0;
		this.tasaFracaso = 0;
		this.actividadesRealizadas = new ArrayList<Actividad>();
		this.completado = false;
		this.estudiante = estudiante;
	}
    
    //Getter and setter
	public Date getFechaInicioPath() {
		return fechaInicioPath;
	}
	public Date getFechaFinPath() {
		return fechaFinPath;
	}
	public void setFechaFinPath(Date fechaFinPath) {
		this.fechaFinPath = fechaFinPath;
	}
	public float getPorcentajePath() {
		return porcentajePath;
	}
	public float getTasaExito() {
		return tasaExito;
	}
	public float getTasaFracaso() {
		return tasaFracaso;
	}

	public List<Actividad> getActividadesRealizadas() {
		return actividadesRealizadas;
	}

	public LearningPath getLp() {
		return lp;
	}

	public boolean isCompletado() {
		return completado;
	}
	
	public Estudiante getEstudiante() {
		return estudiante;
	}
	
	//Métodos
	public void agregarActividadRealizada(Actividad actividad) {
		actividadesRealizadas.add(actividad);
	}
	
	public void marcarCompletado() {
		if(actividadesRealizadas.size() == lp.getActividades().size()) {
			this.completado = true;
			this.fechaFinPath = new Date();
			System.out.println("¡Felicidades! Has completado el learning path: " + lp.getTitulo());
		}
	}
	
    public void calcularProgreso() {
        int totalObligatorias = 0;
        int completadasExitosas = 0;

        for (Actividad actividad : lp.getActividades()) {
            if (actividad.isObligatorio()) {
                totalObligatorias++;
                String resultado = estudiante.getProgresosAct().get(actividad).getResultado();
                if (actividadesRealizadas.contains(actividad) && resultado.equals("Aprobada") ) {
                    completadasExitosas++;
                }
            }
        }
        if (totalObligatorias > 0) {
            this.porcentajePath = (float) completadasExitosas / totalObligatorias * 100;
        }
    }

    public void actualizarTasas() {
    	int exito = 0;
    	int fracaso = 0;
    	for (Actividad actividad : actividadesRealizadas) {
    		String resultado = estudiante.getProgresosAct().get(actividad).getResultado();
    		if (resultado.equals("Aprobada")) {
    			exito++;
    		} else if (resultado.equals("Reprobada")){
    			fracaso++;
    		} 
    	}
    	this.tasaExito = (float) (exito * 100)/(actividadesRealizadas.size());
    	this.tasaFracaso = (float) (fracaso * 100)/(actividadesRealizadas.size());
    }
    
    
}