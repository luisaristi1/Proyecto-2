package PruebasUnitarias;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;

public class ProfesorTest {

    private Profesor profesor;
    private LearningPath learningPath;

    @Before
    public void setUp() {
        profesor = new Profesor("Profesor Test", "profesor@test.com", "password");
        learningPath = profesor.crearLearningPath("LP Test", "Descripción", "Objetivo", "Media", 120, new Registro());
    }

    @Test
    public void testCrearLearningPath() {
        assertNotNull(learningPath);
        assertEquals("LP Test", learningPath.getTitulo());
        assertEquals("Descripción", learningPath.getDescripcion());
        assertEquals("Media", learningPath.getNivelDificultad());
    }

    @Test
    public void testCrearRecurso() {
        RecursoEducativo recurso = (RecursoEducativo) profesor.crearActividad(new Scanner(System.in));
        assertNotNull(recurso);
        assertEquals("Recurso", recurso.getTipo());
    }

    @Test
    public void testCrearEncuesta() {
        Encuesta encuesta = (Encuesta) profesor.crearActividad(new Scanner(System.in));
        assertNotNull(encuesta);
        assertEquals("Encuesta", encuesta.getTipo());
    }

    @Test
    public void testAsignarActividadALearningPath() {
        Actividad actividad = new Tarea(learningPath, "Tarea Test", "Descripción", "Objetivo", "Media", 30, true, profesor);
        profesor.añadirActividadALearningPath(actividad);
        assertTrue(learningPath.getActividades().contains(actividad));
    }

    @Test
    public void testObtenerRecursosPorLearningPath() {
        Actividad recurso = new RecursoEducativo(learningPath, "Recurso 1", "Descripción", "Objetivo", "Media", 30, true, "Video", "https://ejemplo.com", profesor);
        learningPath.getActividades().add(recurso);
        assertTrue(learningPath.getActividades().contains(recurso));
    }

    @Test
    public void testObtenerLearningPathPorTitulo() {
        Registro registro = new Registro();
        registro.agregarPaths(learningPath);
        assertNotNull(learningPath);
    }

    @Test
    public void testModificarRecurso() {
        RecursoEducativo recurso = new RecursoEducativo(learningPath, "Recurso Test", "Descripción", "Objetivo", "Media", 30, true, "PDF", "https://example.com", profesor);
        recurso.cambios(new Scanner(System.in));
        assertEquals("PDF", recurso.getTipoRecurso());
    }

    @Test
    public void testEliminarLearningPath() {
        Registro registro = new Registro();
        registro.agregarPaths(learningPath);
        registro.getPaths().remove(learningPath);
        assertFalse(registro.getPaths().contains(learningPath));
    }

    @Test
    public void testAsignarTarea() {
        Actividad tarea = new Tarea(learningPath, "Tarea Test", "Descripción", "Objetivo", "Media", 30, true, profesor);
        profesor.añadirActividadALearningPath(tarea);
        assertTrue(learningPath.getActividades().contains(tarea));
    }

    @Test
    public void testObtenerTareasAsignadas() {
        List<Actividad> tareas = new ArrayList<>();
        Actividad tarea = new Tarea(learningPath, "Tarea 1", "Descripción", "Objetivo", "Media", 30, true, profesor);
        tareas.add(tarea);
        assertTrue(tareas.contains(tarea));
    }
}
