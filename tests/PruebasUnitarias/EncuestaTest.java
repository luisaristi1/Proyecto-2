package PruebasUnitarias;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

import proyecto.Encuesta;
import proyecto.LearningPath;
import proyecto.ProgresoActividad;

public class EncuestaTest {

    private Encuesta encuesta;

    @Before
    public void setUp() {
        LearningPath lp = new LearningPath("Test Path", "Descripción Path", "Objetivo Path", "Media", null, 60);
        encuesta = new Encuesta(lp, "Encuesta Test", "Descripción Encuesta", "Objetivo Encuesta", "Media", 45, true, null);
    }

    @Test
    public void testGetPreguntas() {
        assertEquals(new ArrayList<>(), encuesta.getPreguntasAbiertas());
    }

    @Test
    public void testAgregarPregunta() {
        Scanner scanner = new Scanner("¿Pregunta de prueba?\n");
        encuesta.agregarPregunta(scanner);
        assertTrue(encuesta.getPreguntasAbiertas().contains("¿Pregunta de prueba?"));
        assertEquals(1, encuesta.getPreguntasAbiertas().size());
    }

    @Test
    public void testFormatoDetallesEncuesta() {
        String detalles = encuesta.getDescripcion() + " " + encuesta.getObjetivo();
        assertTrue(detalles.contains("Descripción Encuesta"));
        assertTrue(detalles.contains("Objetivo Encuesta"));
    }

    @Test
    public void testDescripcionConstructor() {
        assertEquals("Descripción Encuesta", encuesta.getDescripcion());
    }

    @Test
    public void testObjetivoConstructor() {
        assertEquals("Objetivo Encuesta", encuesta.getObjetivo());
    }

    @Test
    public void testIsCompletada() {
        ProgresoActividad progreso = new ProgresoActividad(encuesta, null);
        assertFalse(progreso.isCompletada());
        progreso.marcarRealizada("Aprobada", new java.util.Date());
        assertTrue(progreso.isCompletada());
    }

    @Test
    public void testObtenerResultadoSobrescrito() {
        ProgresoActividad progreso = new ProgresoActividad(encuesta, null);
        progreso.marcarRealizada("Aprobada", new java.util.Date());
        assertEquals("Aprobada", progreso.getResultado());
    }

    @Test
    public void testInicializarConPreguntas() {
        assertEquals(0, encuesta.getPreguntasAbiertas().size());
    }

    @Test
    public void testValidarPreguntasVacias() {
        assertTrue(encuesta.getPreguntasAbiertas().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarPreguntaNulaOVacia() {
        Scanner scanner = new Scanner("");
        encuesta.agregarPregunta(scanner);
    }

    @Test
    public void testFormatoCorrectoDetalle() {
        String detalles = encuesta.getDescripcion() + " " + encuesta.getObjetivo();
        assertTrue(detalles.contains("Descripción Encuesta"));
        assertTrue(detalles.contains("Objetivo Encuesta"));
    }

    @Test
    public void testDetalleConListaVaciaDePreguntas() {
        String detalles = encuesta.getDescripcion();
        assertTrue(detalles.contains("Descripción Encuesta"));
        assertTrue(encuesta.getPreguntasAbiertas().isEmpty());
    }
}
