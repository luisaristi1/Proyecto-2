package proyecto;

public class Reseña {
	
	//Atributos
	private String texto;
    private float rating;
    
    //Constructor
	public Reseña(String texto, float rating) {
		this.texto = texto;
		this.rating = rating;
	}
	
	//Get and set
	public String getTexto() {
		return texto;
	}
	public float getRating() {
		return rating;
	}
	
	//Metodos
	public void hacerReseña(Actividad actividad){
		if (rating < 0 || rating > 10) {
	        System.out.println("El rating debe estar entre 0 y 10.");
	    }
	    actividad.agregarReseña(this);
	    System.out.println("Reseña agregada con éxito. Gracias por ayudarnos a mejorar!");
	}
	
}
