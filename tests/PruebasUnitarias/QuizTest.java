package PruebasUnitarias;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

import proyecto.Actividad;
import proyecto.LearningPath;
import proyecto.Profesor;
import proyecto.ProgresoActividad;
import proyecto.Quiz;
import proyecto.Reseña;

public class QuizTest {

    private Quiz quiz;
    private Profesor creador;

    @Before
    public void setUp() {
        creador = new Profesor("Profesor Test", "profesor@test.com", "password");
        LearningPath lp = new LearningPath("Test Path", "Descripción Path", "Objetivo Path", "Media", creador, 60);
        quiz = new Quiz(lp, "Quiz Test", "Descripción Quiz", "Objetivo Quiz", "Media", 30, true, 70.0, creador);
    }

    @Test
    public void testInicializarAtributos() {
        assertNotNull(quiz);
        assertEquals("Quiz Test", quiz.getNombre());
        assertEquals("Descripción Quiz", quiz.getDescripcion());
        assertEquals("Objetivo Quiz", quiz.getObjetivo());
        assertEquals("Media", quiz.getNivelDificultad());
        assertEquals(30, quiz.getDuracionEsperada());
        assertEquals(70.0, quiz.getNotaAprobacion(), 0.01);
        assertNotNull(quiz.getPreguntas());
    }

    @Test
    public void testValidarHerenciaYAsignacionDeTipo() {
        assertEquals("Quiz", quiz.getTipo());
    }

    @Test
    public void testAgregarPregunta() {
        Scanner scanner = new Scanner("¿Pregunta de prueba?"
        		+ "Opción 1"
        		+ "Opción 2"
        		+ "Opción 3"
        		+ "Opción 4"
        		+ "1");
        quiz.agregarPregunta(scanner);
        assertEquals(1, quiz.getPreguntas().size());
        assertTrue(quiz.getPreguntas().get(0).getTextoPregunta().contains("¿Pregunta de prueba?"));
    }

    @Test
    public void testCalcularNotaObtenidaCorrectamente() {
        Scanner scanner = new Scanner("¿Pregunta de prueba?"
        		+ "Opción 1"
        		+ "Opción 2"
        		+ "Opción 3"
        		+ "Opción 4"
        		+ "1");
        quiz.agregarPregunta(scanner);
        Scanner respuestaScanner = new Scanner("1");
        String resultado = quiz.realizarQuiz(respuestaScanner);
        assertEquals("Aprobada", resultado);
        assertEquals(100.0, quiz.getNotaObtenida(), 0.01);
    }

    @Test
    public void testDeterminarResultado() {
        Scanner scanner = new Scanner("¿Pregunta de prueba?"
        		+ "Opción 1"
        		+ "Opción 2"
        		+ "Opción 3"
        		+ "Opción 4"
        		+ "1");
        quiz.agregarPregunta(scanner);
        Scanner respuestaScanner = new Scanner("2"); // Respuesta incorrecta
        String resultado = quiz.realizarQuiz(respuestaScanner);
        assertEquals("Reprobada", resultado);
        assertEquals(0.0, quiz.getNotaObtenida(), 0.01);
    }

    @Test
    public void testRegistrarProgresoActividad() {
        ProgresoActividad progreso = new ProgresoActividad(quiz, null);
        quiz.realizar(progreso);
        assertNotNull(progreso.getFechaInicio());
        assertEquals("Enviada", progreso.getResultado());
    }

    @Test
    public void testVerificarPermisosDeEdicionCorrecto() {
        assertTrue(quiz.getCreador().equals(creador));
    }

    @Test
    public void testVerificarPermisosDeEdicionIncorrecto() {
        Profesor otroProfesor = new Profesor("Otro Profesor", "otro@test.com", "password");
        assertFalse(quiz.getCreador().equals(otroProfesor));
    }

    @Test
    public void testFechaLimiteCalculadaCorrectamente() {
        quiz.establecerFechaLimite(new Date());
        assertNotNull(quiz.getFechaLimite());
    }

    @Test
    public void testRecordarActividadAntesFechaLimite() {
        ProgresoActividad progreso = new ProgresoActividad(quiz, null);
        quiz.RecordatorioActividad(progreso);
        assertNotNull(quiz.getFechaLimite());
    }

    @Test
    public void testAgregarActividadSeguimiento() {
        LearningPath lp = quiz.getLearningPath();
        Quiz seguimiento = new Quiz(lp, "Seguimiento Quiz", "Descripción", "Objetivo", "Media", 30, true, 70.0, creador);
        quiz.agregarActividadSeguimiento(seguimiento);
        assertTrue(quiz.getActividadSeguimiento().contains(seguimiento));
    }

    @Test
    public void testRecomendarActividad() {
        ProgresoActividad progreso = new ProgresoActividad(quiz, null);
        progreso.marcarRealizada("Aprobada", new Date());
        quiz.recomendarActividad(progreso, quiz);
        assertNotNull(progreso);
    }

    @Test
    public void testClonarActividadYVerificarAtributos() {
        Actividad clon = quiz.clonarActividad(creador);
        assertNotNull(clon);
        assertEquals(quiz.getDescripcion(), clon.getDescripcion());
        assertEquals(quiz.getObjetivo(), clon.getObjetivo());
    }

    @Test
    public void testAgregarYCalcularPromedioReseñas() {
        Reseña reseña = new Reseña("Buen Quiz", 8);
        quiz.agregarReseña(reseña);
        assertEquals(8.0, quiz.calcularPromedioRating(), 0.01);
    }
}
