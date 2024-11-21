package PruebasUnitarias;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import proyecto.PreguntaMultiple;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PreguntaMultipleTest {

    private PreguntaMultiple pregunta;
    private List<String> opciones;
    private List<String> explicaciones;

    @Before
    public void setUp() {
        opciones = new ArrayList<>();
        explicaciones = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            opciones.add("Opción " + i);
            explicaciones.add("Explicación " + i);
        }
        pregunta = new PreguntaMultiple("¿Cuál es la respuesta correcta?", opciones, 1, explicaciones);
    }

    @Test
    public void testConstructorCorrecto() {
        assertNotNull(pregunta);
        assertEquals("¿Cuál es la respuesta correcta?", pregunta.getTextoPregunta());
        assertEquals(4, pregunta.getOpciones().size());
        assertEquals(4, pregunta.getExplicaciones().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConOpcionesInvalidas() {
        List<String> opcionesInvalidas = new ArrayList<>();
        List<String> explicacionesInvalidas = new ArrayList<>();
        opcionesInvalidas.add("Opción 1");
        explicacionesInvalidas.add("Explicación 1");
        new PreguntaMultiple("Pregunta inválida", opcionesInvalidas, 1, explicacionesInvalidas);
    }

    @Test
    public void testMostrarYResolverCorrecto() {
        Scanner scanner = new Scanner("1");
        boolean resultado = pregunta.mostrarYResolver(scanner);
        assertTrue(resultado);
    }

    @Test
    public void testMostrarYResolverEntradaInvalida() {
        Scanner scanner = new Scanner("51");
        boolean resultado = pregunta.mostrarYResolver(scanner);
        assertTrue(resultado);
    }

    @Test
    public void testMostrarYResolverRespuestaCorrecta() {
        Scanner scanner = new Scanner("1");
        boolean resultado = pregunta.mostrarYResolver(scanner);
        assertTrue(resultado);
    }

    @Test
    public void testMostrarYResolverRespuestaIncorrecta() {
        Scanner scanner = new Scanner("2");
        boolean resultado = pregunta.mostrarYResolver(scanner);
        assertFalse(resultado);
    }

    @Test
    public void testMostrarExplicaciones() {
        Scanner scanner = new Scanner("2");
        pregunta.mostrarYResolver(scanner);
        assertEquals("Explicación 2", pregunta.getExplicaciones().get(1));
    }
}
