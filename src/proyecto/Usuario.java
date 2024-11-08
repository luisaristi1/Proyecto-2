package proyecto;

public abstract class Usuario {
	
	//Atributos
	protected String nombre;
	protected String correo;
	protected String contrasena;
	
	//Constructor
    public Usuario(String nombre, String correo, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }
    
	//Get y set
	public String getNombre() {
		return nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public String getContrasena() {
		return contrasena;
	}

	//Metodos
	public abstract void verLearningPaths();
	public abstract String getTipoUsuario();
	
	//Reseña
	public abstract void darReseñaActividad(Actividad actividad, String texto, float rating);

	
	
	
}
