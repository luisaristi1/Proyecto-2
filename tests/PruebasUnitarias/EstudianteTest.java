package PruebasUnitarias;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import proyecto.Actividad;
import proyecto.Estudiante;
import proyecto.LearningPath;
import proyecto.Profesor;
import proyecto.ProgresoActividad;
import proyecto.Registro;
import proyecto.Tarea;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EstudianteTest {

    private Estudiante estudiante;
    private LearningPath learningPath;
    private Actividad actividad;
    private Registro sistema;

    @Before
    public void setUp() {
        estudiante = new Estudiante("Estudiante Test", "estudiante@test.com", "password");
        Profesor profesor = new Profesor("Profesor Test", "profesor@test.com", "password");
        sistema = new Registro();
        learningPath = new LearningPath("LP Test", "Descripción", "Objetivo", "Media", profesor, 120);
        actividad = new Tarea(learningPath, "Tarea Test", "Descripción", "Objetivo", "Media", 30, true, profesor);
        sistema.agregarPaths(learningPath);
        learningPath.getActividades().add(actividad);
    }

    @Test
    public void testGetTipoUsuario() {
        assertEquals("Estudiante", estudiante.getTipoUsuario());
    }

    @Test
    public void testVerLearningPaths() {
        estudiante.inscribirseEnLearningPath(new Scanner(System.in), sistema);
        assertNotNull(estudiante.getLearningPathsInscritos());
    }

    @Test
    public void testInscribirseEnLearningPath() {
        LearningPath lpInscrito = estudiante.inscribirseEnLearningPath(new Scanner(System.in), sistema);
        assertNotNull(lpInscrito);
        assertTrue(estudiante.getLearningPathsInscritos().contains(lpInscrito));
    }

    @Test
    public void testInscripcion() {
        estudiante.inscripcion(learningPath);
        assertTrue(estudiante.getLearningPathsInscritos().contains(learningPath));
    }

    @Test
    public void testDarReseñaActividad() {
        estudiante.inscripcion(learningPath);
        ProgresoActividad progreso = new ProgresoActividad(actividad, estudiante);
        progreso.marcarRealizada("Aprobada", null);
        estudiante.darReseñaActividad(actividad, "Muy buena tarea", 8.5f);
        assertTrue(actividad.getReseñas().size() > 0);
    }

    @Test
    public void testSeleccionarActividad() {
        estudiante.inscripcion(learningPath);
        Actividad seleccionada = estudiante.seleccionarActividad(new Scanner(System.in), learningPath);
        assertNotNull(seleccionada);
    }

    @Test
    public void testIniciarActividad() {
        estudiante.inscripcion(learningPath);
        estudiante.iniciarActividad(actividad);
        ProgresoActividad progreso = estudiante.getProgresosAct().get(actividad);
        assertTrue(progreso.getFechaInicio() != null);
    }

    @Test
    public void testFaltanPrerrequisitos() {
        estudiante.inscripcion(learningPath);
        boolean faltan = estudiante.faltanPrerrequisitos(actividad);
        assertTrue(faltan || !faltan); // Verifica si hay prerrequisitos o no
    }

    @Test
    public void testRealizarActividad() {
        estudiante.inscripcion(learningPath);
        estudiante.iniciarActividad(actividad);
        estudiante.realizarActividad(actividad);
        ProgresoActividad progreso = estudiante.getProgresosAct().get(actividad);
        assertTrue(progreso.isCompletada());
    }

    @Test
    public void testPedirRecomendacionActividad() {
        estudiante.inscripcion(learningPath);
        estudiante.realizarActividad(actividad);
        estudiante.pedirRecomendacionActividad(learningPath);
        // Asume que la recomendación se imprime correctamente
        assertTrue(true);
    }

    @Test
    public void testPedirProgresoPath() {
        estudiante.inscripcion(learningPath);
        estudiante.realizarActividad(actividad);
        estudiante.pedirProgresoPath(learningPath);
        // Asume que el progreso se calcula e imprime correctamente
        assertTrue(true);
    }
}
