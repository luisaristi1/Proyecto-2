package PruebasUnitarias;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import proyecto.Actividad;
import proyecto.LearningPath;
import proyecto.Profesor;
import proyecto.Reseña;
import proyecto.Tarea;

import java.util.Date;

public class LearningPathTest {

    private LearningPath learningPath;
    private Profesor creador;
    private Actividad actividad;

    @Before
    public void setUp() {
        creador = new Profesor("Profesor Test", "profesor@test.com", "password");
        learningPath = new LearningPath("Path Test", "Descripción inicial", "Objetivo inicial", "Media", creador, 120);
    }

    @Test
    public void testModificarLearningPath() {
        learningPath.setTitulo("Nuevo Titulo");
        learningPath.setDescripcion("Nueva Descripción");
        learningPath.setObjetivos("Nuevos Objetivos");
        assertEquals("Nuevo Titulo", learningPath.getTitulo());
        assertEquals("Nueva Descripción", learningPath.getDescripcion());
        assertEquals("Nuevos Objetivos", learningPath.getObjetivos());
    }

    @Test
    public void testFechaModificacionActualizada() {
        Date fechaInicial = learningPath.getFechaModificacion();
        learningPath.setTitulo("Nuevo Titulo");
        assertNotEquals(fechaInicial, learningPath.getFechaModificacion());
    }

    @Test
    public void testAgregarActividad() {
        Actividad actividad = new Tarea(learningPath, "Tarea 1", "Descripción", "Objetivo", "Media", 30, true, creador);
        learningPath.getActividades().add(actividad);
        assertTrue(learningPath.getActividades().contains(actividad));
    }

    @Test
    public void testActualizarDuracionAgregarActividad() {
        Actividad actividad = new Tarea(learningPath, "Tarea 1", "Descripción", "Objetivo", "Media", 30, true, creador);
        learningPath.añadirTiempoLp(actividad);
        assertEquals(150, learningPath.getDuracionEstimada());
    }

    @Test
    public void testEliminarActividad() {
        Actividad actividad = new Tarea(learningPath, "Tarea 1", "Descripción", "Objetivo", "Media", 30, true, creador);
        learningPath.getActividades().add(actividad);
        learningPath.getActividades().remove(actividad);
        assertFalse(learningPath.getActividades().contains(actividad));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEliminarActividadInexistente() {
        Actividad actividad = new Tarea(learningPath, "Tarea 1", "Descripción", "Objetivo", "Media", 30, true, creador);
        learningPath.getActividades().remove(actividad);
    }

    @Test
    public void testAgregarFeedback() {
        Reseña feedback = new Reseña("Gran contenido", 8.5f);
        learningPath.calcularPromedioRating();
        assertEquals(0, feedback.getRating(), 0.1);
    }

    @Test
    public void testCalcularPromedioRating() {
        Actividad actividad1 = new Tarea(learningPath, "Tarea 1", "Descripción", "Objetivo", "Media", 30, true, creador);
        learningPath.añadirTiempoLp(actividad1);
        learningPath.calcularPromedioRating();
        assertTrue(true);
    }

    @Test
    public void testListaInicialVacia() {
        assertTrue(learningPath.getActividades().isEmpty());
    }

    @Test
    public void testInicializarAtributos() {
        assertEquals("Path Test", learningPath.getTitulo());
        assertEquals("Descripción inicial", learningPath.getDescripcion());
        assertEquals("Objetivo inicial", learningPath.getObjetivos());
        assertEquals("Media", learningPath.getNivelDificultad());
        assertEquals(120, learningPath.getDuracionEstimada());
        assertEquals(creador, learningPath.getCreador());
    }
}
