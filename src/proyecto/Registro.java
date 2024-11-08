package proyecto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Persistencia.PersistenciaUsuarios;

public class Registro {
	
	//Atributos
	private List<Profesor> profesores;
	private List<Estudiante> estudiantes;
	private List<Usuario> usuarios;
	private List<LearningPath> paths;
    private PersistenciaUsuarios persistencia;

    
    //Constructor
    public Registro() {
    	profesores = new ArrayList<>();
		estudiantes = new ArrayList<>();
        persistencia = new PersistenciaUsuarios();
        paths = new ArrayList<>();
        
	}

    public List<LearningPath> getPaths() {
		return paths;
	}

	//Mteodos
	public void registrarProfesor(Profesor profesor) {
        for (Usuario u : usuarios) {
            if (u instanceof Profesor) {
                Profesor p = (Profesor) u;
                if (p.getCorreo().equals(profesor.getCorreo())) {
                    System.out.println("El profesor ya está registrado.");
                }
            }
        }
        profesores.add(profesor);
        usuarios.add(profesor);
    }
	
	public void registrarEstudiante(Estudiante estudiante) throws Exception {
        for (Usuario u : usuarios) {
            if (u instanceof Estudiante) {
	            if (u.getCorreo().equals(estudiante.getCorreo())) {
	                System.out.println("El estudiante ya está registrado.");
	            }
            }
        }
        estudiantes.add(estudiante);
        usuarios.add(estudiante);
    }
	
    public Profesor loginProfesor(String correo, String contrasena) throws Exception {
        for (Usuario u : usuarios) {
            if (u instanceof Profesor) {
                Profesor p = (Profesor) u;
                if (p.getCorreo().equals(correo) && p.getContrasena().equals(contrasena)) {
                    return p;
                }
            }
        }
        throw new Exception("Login fallido. Usuario o contraseña incorrectos.");
    }


    public Estudiante loginEstudiante(String correo, String contrasena) throws Exception {
        for (Usuario u : usuarios) {
            if (u instanceof Estudiante) {
            	Estudiante e = (Estudiante) u;
	            if (e.getCorreo().equals(correo) && e.getContrasena().equals(contrasena)) {
	                return  e;
	            }
            }
        }
        throw new Exception("Login fallido. Usuario o contraseña incorrectos.");
    }

    public void cargarUsuarios(String archivo) throws Exception {
        usuarios = persistencia.cargarUsuarios(archivo);
    }

    public void salvarUsuarios(String archivo) throws Exception {
        persistencia.salvarUsuarios(archivo, usuarios);
    }
    
    public void agregarPaths(LearningPath lp) {
    	this.paths.add(lp);
    }



}
