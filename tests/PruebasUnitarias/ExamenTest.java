package PruebasUnitarias;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

import proyecto.Examen;
import proyecto.LearningPath;
import proyecto.ProgresoActividad;

public class ExamenTest {

    private Examen examen;

    @Before
    public void setUp() {
        LearningPath lp = new LearningPath("Test Path", "Descripción Path", "Objetivo Path", "Media", null, 60);
        examen = new Examen(lp, "Examen Test", "Descripción Examen", "Objetivo Examen", "Media", 60, true, null);
    }

    @Test
    public void testGetPreguntas() {
        assertEquals(new ArrayList<>(), examen.getPreguntasAbiertas());
    }

    @Test
    public void testAgregarPregunta() {
        Scanner scanner = new Scanner("¿Pregunta de prueba?\n");
        examen.agregarPregunta(scanner);
        assertTrue(examen.getPreguntasAbiertas().contains("¿Pregunta de prueba?"));
        assertEquals(1, examen.getPreguntasAbiertas().size());
    }

    @Test
    public void testFormatoDetallesExamen() {
        String detalles = examen.getDescripcion() + " " + examen.getObjetivo();
        assertTrue(detalles.contains("Descripción Examen"));
        assertTrue(detalles.contains("Objetivo Examen"));
    }

    @Test
    public void testDescripcionConstructor() {
        assertEquals("Descripción Examen", examen.getDescripcion());
    }

    @Test
    public void testObjetivoConstructor() {
        assertEquals("Objetivo Examen", examen.getObjetivo());
    }

    @Test
    public void testIsCompletada() {
        ProgresoActividad progreso = new ProgresoActividad(examen, null);
        assertFalse(progreso.isCompletada());
        progreso.marcarRealizada("Aprobada", new java.util.Date());
        assertTrue(progreso.isCompletada());
    }

    @Test
    public void testObtenerResultadoSobrescrito() {
        ProgresoActividad progreso = new ProgresoActividad(examen, null);
        progreso.marcarRealizada("Aprobada", new java.util.Date());
        assertEquals("Aprobada", progreso.getResultado());
    }

    @Test
    public void testInicializarConListaVacia() {
        assertTrue(examen.getPreguntasAbiertas().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarPreguntaNulaOVacia() {
        Scanner scanner = new Scanner("");
        examen.agregarPregunta(scanner);
    }

    @Test
    public void testLongitudMaximaDeUnaPregunta() {
        Scanner scanner = new Scanner("Pregunta extremadamente larga que debería ser rechazada porque supera el límite aceptable para una pregunta en el examen...\n");
        examen.agregarPregunta(scanner);
        assertFalse(examen.getPreguntasAbiertas().contains("Pregunta extremadamente larga que debería ser rechazada porque supera el límite aceptable para una pregunta en el examen..."));
    }

    @Test
    public void testFormatoCorrectoDetalleCompleto() {
        Scanner scanner = new Scanner("¿Primera pregunta?\n");
        examen.agregarPregunta(scanner);
        String detalles = examen.getDescripcion() + " " + examen.getObjetivo() + " " + examen.getPreguntasAbiertas();
        assertTrue(detalles.contains("Descripción Examen"));
        assertTrue(detalles.contains("Objetivo Examen"));
        assertTrue(detalles.contains("¿Primera pregunta?"));
    }

    @Test
    public void testDetalleConListaVaciaDePreguntas() {
        String detalles = examen.getDescripcion();
        assertTrue(detalles.contains("Descripción Examen"));
        assertTrue(examen.getPreguntasAbiertas().isEmpty());
    }

    @Test
    public void testResultadoDelExamenCompletado() {
        ProgresoActividad progreso = new ProgresoActividad(examen, null);
        progreso.marcarRealizada("Aprobada", new java.util.Date());
        assertEquals("Aprobada", progreso.getResultado());
    }
}
