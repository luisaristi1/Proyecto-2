package PruebasUnitarias;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import proyecto.Actividad;
import proyecto.Estudiante;
import proyecto.LearningPath;
import proyecto.Profesor;
import proyecto.Reseña;
import proyecto.Tarea;
import proyecto.Usuario;

public class UsuarioTest {

    private Estudiante estudiante;
    private Profesor profesor;
    private Actividad actividad;

    @Before
    public void setUp() {
        estudiante = new Estudiante("Estudiante Test", "estudiante@test.com", "password");
        profesor = new Profesor("Profesor Test", "profesor@test.com", "password");
    }

    @Test
    public void testInicializacionUsuario() {
        assertEquals("Estudiante Test", estudiante.getNombre());
        assertEquals("estudiante@test.com", estudiante.getCorreo());
        assertEquals("password", estudiante.getContrasena());

        assertEquals("Profesor Test", profesor.getNombre());
        assertEquals("profesor@test.com", profesor.getCorreo());
        assertEquals("password", profesor.getContrasena());
    }

    @Test
    public void testGetNombre() {
        assertEquals("Estudiante Test", estudiante.getNombre());
        assertEquals("Profesor Test", profesor.getNombre());
    }

    @Test
    public void testGetCorreo() {
        assertEquals("estudiante@test.com", estudiante.getCorreo());
        assertEquals("profesor@test.com", profesor.getCorreo());
    }

    @Test
    public void testGetContrasena() {
        assertEquals("password", estudiante.getContrasena());
        assertEquals("password", profesor.getContrasena());
    }

    @Test
    public void testVerLearningPaths() {
        estudiante.verLearningPaths();
        profesor.verLearningPaths();
        // assertNotNull is used to ensure methods execute without errors
        assertNotNull(estudiante.getLearningPathsInscritos());
        assertNotNull(profesor.getLearningPathsCreados());
    }

    @Test
    public void testGetTipoUsuario() {
        assertEquals("Estudiante", estudiante.getTipoUsuario());
        assertEquals("Profesor", profesor.getTipoUsuario());
    }

    @Test
    public void testDarReseñaActividad() {
        LearningPath lp = new LearningPath("LP Test", "Descripción", "Objetivo", "Media", (Profesor) profesor, 60);
        Actividad actividad = new Tarea(lp, "Tarea Test", "Descripción", "Objetivo", "Media", 30, true, (Profesor) profesor);
        
        Reseña reseñaEstudiante = new Reseña("Buen trabajo", 8.0f);
        estudiante.darReseñaActividad(actividad, "Buen trabajo", 8.0f);
        assertNotNull(reseñaEstudiante);
        assertEquals(8.0f, reseñaEstudiante.getRating(), 0.01);
        
        Reseña reseñaProfesor = new Reseña("Excelente contenido", 9.0f);
        profesor.darReseñaActividad(actividad, "Excelente contenido", 9.0f);
        assertNotNull(reseñaProfesor);
        assertEquals(9.0f, reseñaProfesor.getRating(), 0.01);
    }
}
