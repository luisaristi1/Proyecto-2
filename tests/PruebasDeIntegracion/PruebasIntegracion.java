package PruebasDeIntegracion;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Persistencia.ManejoPersistencia;
import proyecto.Actividad;
import proyecto.Estudiante;
import proyecto.LearningPath;
import proyecto.Profesor;
import proyecto.RecursoEducativo;
import proyecto.Registro;
import proyecto.Reseña;

class PruebasIntegracion {

	ManejoPersistencia registro = new ManejoPersistencia();
	Profesor p1 = new Profesor("Carlos Dias", "c.dias@gmail.com", "zaq12wsx");
	Profesor p2 = new Profesor("Laura Diaz", "l.diaz@gmail.com", "12345678");
	Profesor p3 = new Profesor("Manuel Villamil", "m.villamil@gmail.com", "87654321");
	
	Estudiante e1 = new Estudiante("Natalia Perez", "n.perez@gmail.com", "12345678");
	Estudiante e2 = new Estudiante("Julian Tellez", "j.tellez@gmail.com", "09876544");
	Estudiante e3 = new Estudiante("Claudia Montero", "n.perez@gmail.com", "12345678");
	
	LearningPath lp1 = p1.crearLearningPath("Java Programming", 
            "Aprende los fundamentos de Java", 
            "Dominar los conceptos de java y POO\nAprender un nuevo lenguaje", 
            "Intermedio",8, registro);
	
	RecursoEducativo r1 = new RecursoEducativo(lp1, "Sobre Java", "Corta introdución a Java",
			"Familiarizarse con Java", "1", 30, true, "Lectura", "lecturasJava.com", p1);
	
	
	
	
	//registrar profesor
	@Test
	void test1() {
		registro.registrarProfesor(p1);
		Boolean rta = registro.verificarUsuarioExistente(p1);
		
		assertEquals(true, rta);
		
	}
	
	//registrar estudiante
	@Test
	void test2() throws Exception {
		registro.registrarEstudiante(e1);
		Boolean rta = registro.verificarUsuarioExistente(e1);
			
		assertEquals(true, rta);
			
	}
	
	
	//login profesor existente
		@Test
		void test3() throws Exception {
			registro.loginProfesor("c.dias@gail.com", "zaq12wsx");
			Boolean rta = registro.verificarUsuarioExistente(p1);
			assertEquals(true, rta);
				
		}
		
		//login estudiante existente
				@Test
				void test4() throws Exception {
					registro.loginEstudiante("n.perez@gmail.com", "12345678");
					Boolean rta = registro.verificarUsuarioExistente(e1);
					assertEquals(true, rta);
						
				}
				
				//login profesor no existente
				@Test
				void test5() throws Exception {
					registro.loginProfesor("l.diaz@gmail.com", "12345678");
					Boolean rta = registro.verificarUsuarioExistente(p2);
					assertEquals(false, rta);
						
				}	
				
				//login estudiante no existente
				@Test
				void test6() throws Exception {
					registro.loginProfesor("j.tellez@gmail.com", "09876544");
					Boolean rta = registro.verificarUsuarioExistente(e2);
					assertEquals(false, rta);
						
				}	
				
				//registrar estudiante ya registrado
				@Test
				void test7() throws Exception {
					registro.registrarEstudiante(e1);
					registro.registrarEstudiante(e1);
					Boolean rta = registro.verificarUsuarioExistente(e1);
						
					assertEquals(true, rta);
						
				}
	
				//registrar profesor ya registrado
				@Test
				void test8() {
					registro.registrarProfesor(p1);
					registro.registrarProfesor(p1);
					Boolean rta = registro.verificarUsuarioExistente(p1);
					
					assertEquals(true, rta);
					
				}
				
	//RUTAS DE APRENDIZAJE
				//Crear paths con actividades validas.
				@Test
				void test9() {
					
					Scanner scanner = new Scanner(System.in);
					Actividad a1 = p1.crearActividad(scanner);
					
					Boolean rta = p1.verificarActividadExistente(lp1.getTitulo(), a1);
					
					assertEquals(true, rta);
					
				}	
				
				void test10() {
					p1.editarAtributosPath(lp1);
					
					Scanner scanner = new Scanner(System.in);
					Actividad a1 = p1.crearActividad(scanner);
					
					Boolean rta = p1.verificarActividadExistente(lp1.getTitulo(), a1);
					
					assertEquals(true, rta);
					
				}	
				
				void test11() {
					List<LearningPath> p = p1.getLearningPathsCreados();
					LearningPath l = p.getFirst();
					
					List<Actividad> as = l.getActividades();
					Actividad a = as.getFirst();
					
					p1.editarActividad(a);
					
					Boolean rta = p1.verificarActividadExistente(l.getTitulo(), a);
					
					assertEquals(true, rta);
					
				}	
			
				void test12() {
					Scanner scanner = new Scanner(System.in);
					Actividad a1 = p2.crearActividad(scanner);
					
					Boolean rta = p2.verificarActividadExistente(lp1.getTitulo(), a1);
					
					assertEquals(false, rta);
					
				}	
				
				void test13() {
					e1.inscripcion(lp1, persistencia);
					Boolean rta = e1.verificarPathExistente(lp1);
					
					assertEquals(true, rta);
					
					p1.añadirActividadALearningPath(r1);
			
				}	
				
				void test14() {
					p1.añadirActividadALearningPath(r1);
					e1.iniciarActividad(r1);
					e1.realizarActividad(r1);
					e1.darReseñaActividad(r1, "Muy buena, me gustó mucho", 9);
				
				}	
				
				
				
				
				
			
				
				
				
				
				
	
				
		
		
		
		
	
	

}
