package PruebasUnitarias;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import proyecto.Actividad;
import proyecto.Estudiante;
import proyecto.LearningPath;
import proyecto.Profesor;
import proyecto.ProgresoPath;
import proyecto.Tarea;

import java.util.Date;

public class ProgresoPathTest {

    private ProgresoPath progresoPath;
    private LearningPath learningPath;
    private Estudiante estudiante;
    private Actividad actividad;

    @Before
    public void setUp() {
        Profesor creador = new Profesor("Profesor Test", "profesor@test.com", "password");
        estudiante = new Estudiante("Estudiante Test", "estudiante@test.com", "password");
        learningPath = new LearningPath("Path Test", "Descripción inicial", "Objetivo inicial", "Media", creador, 120);
        progresoPath = new ProgresoPath(learningPath, new Date(), estudiante);
    }

    @Test
    public void testConstructor() {
        assertNotNull(progresoPath);
        assertEquals(learningPath, progresoPath.getLp());
        assertEquals(estudiante, progresoPath.getEstudiante());
        assertFalse(progresoPath.isCompletado());
        assertEquals(0, progresoPath.getPorcentajePath(), 0.01);
        assertEquals(0, progresoPath.getTasaExito(), 0.01);
        assertEquals(0, progresoPath.getTasaFracaso(), 0.01);
    }

    @Test
    public void testAgregarActividadRealizada() {
        Actividad actividad = new Tarea(learningPath, "Tarea 1", "Descripción", "Objetivo", "Media", 30, true, null);
        progresoPath.agregarActividadRealizada(actividad);
        assertTrue(progresoPath.getActividadesRealizadas().contains(actividad));
    }

    @Test
    public void testCalcularProgreso() {
        Actividad actividad1 = new Tarea(learningPath, "Tarea 1", "Descripción", "Objetivo", "Media", 30, true, null);
        Actividad actividad2 = new Tarea(learningPath, "Tarea 2", "Descripción", "Objetivo", "Media", 30, true, null);
        learningPath.getActividades().add(actividad1);
        learningPath.getActividades().add(actividad2);

        progresoPath.agregarActividadRealizada(actividad1);
        progresoPath.calcularProgreso();
        assertEquals(50, progresoPath.getPorcentajePath(), 0.01);

        progresoPath.agregarActividadRealizada(actividad2);
        progresoPath.calcularProgreso();
        assertEquals(100, progresoPath.getPorcentajePath(), 0.01);
    }

    @Test
    public void testMarcarCompletado() {
        Actividad actividad = new Tarea(learningPath, "Tarea 1", "Descripción", "Objetivo", "Media", 30, true, null);
        learningPath.getActividades().add(actividad);

        progresoPath.agregarActividadRealizada(actividad);
        progresoPath.marcarCompletado();
        assertTrue(progresoPath.isCompletado());
        assertNotNull(progresoPath.getFechaFinPath());
    }

    @Test
    public void testActualizarTasas() {
        Actividad actividad1 = new Tarea(learningPath, "Tarea 1", "Descripción", "Objetivo", "Media", 30, true, null);
        Actividad actividad2 = new Tarea(learningPath, "Tarea 2", "Descripción", "Objetivo", "Media", 30, true, null);
        learningPath.getActividades().add(actividad1);
        learningPath.getActividades().add(actividad2);

        progresoPath.agregarActividadRealizada(actividad1);
        progresoPath.calcularProgreso();
        progresoPath.actualizarTasas();

        assertTrue(progresoPath.getTasaExito() >= 0);
        assertTrue(progresoPath.getTasaFracaso() >= 0);
    }
}
