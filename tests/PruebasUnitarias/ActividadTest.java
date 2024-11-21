package PruebasUnitarias;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import proyecto.Actividad;
import proyecto.Profesor;
import proyecto.ProgresoActividad;
import proyecto.Reseña;

public class ActividadTest {

    private Actividad actividad;

    @Before
    public void setUp() {
        actividad = new Actividad(null, "Actividad Test", "Descripción inicial", "Objetivo inicial", "Baja", 60, true, null) {
            @Override
            public void realizar(ProgresoActividad progreso) {}

            @Override
            public void editar(Profesor editor) {}
        };
    }

    @Test
    public void testSetAndGetDescripcion() {
        actividad.setDescripcion("Nueva Descripción");
        assertEquals("Nueva Descripción", actividad.getDescripcion());
    }

    @Test
    public void testSetAndGetObjetivo() {
        actividad.setObjetivo("Nuevo Objetivo");
        assertEquals("Nuevo Objetivo", actividad.getObjetivo());
    }

    @Test
    public void testSetAndGetNivelDificultad() {
        actividad.setNivelDificultad("Media");
        assertEquals("Media", actividad.getNivelDificultad());
    }

    @Test
    public void testSetAndGetDuracionEsperada() {
        actividad.setDuracionEsperada(90);
        assertEquals(90, actividad.getDuracionEsperada());
    }

    @Test
    public void testSetAndGetPrerrequisitos() {
        List<Actividad> prerrequisitos = new ArrayList<>();
        prerrequisitos.add(new Actividad(null, "Prerrequisito", "Descripción", "Objetivo", "Alta", 45, true, null) {
            @Override
            public void realizar(ProgresoActividad progreso) {}

            @Override
            public void editar(Profesor editor) {}
        });
        actividad.setPrerrequisitos(prerrequisitos);
        assertEquals(prerrequisitos, actividad.getPrerrequisitos());
    }

    @Test
    public void testSetAndGetFechaLimite() {
        Date fechaLimite = new Date();
        actividad.establecerFechaLimite(fechaLimite);
        assertEquals(fechaLimite, actividad.getFechaLimite());
    }

    @Test
    public void testSetAndGetObligatorio() {
        actividad.setObligatorio(false);
        assertFalse(actividad.isObligatorio());
    }

    @Test
    public void testIsCompletada() {
        ProgresoActividad progreso = new ProgresoActividad(actividad, null);
        assertFalse(progreso.isCompletada());
        progreso.marcarRealizada("Aprobada", new Date());
        assertTrue(progreso.isCompletada());
    }

    @Test
    public void testAdvertenciaPrerequisitos() {
        assertFalse(actividad.faltanPrerrequisitos(actividad));
    }

    @Test
    public void testObtenerResultado() {
        ProgresoActividad progreso = new ProgresoActividad(actividad, null);
        progreso.marcarRealizada("Aprobada", new Date());
        assertEquals("Aprobada", progreso.getResultado());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testModificarCreadorNoPermitido() {
        Profesor profesor = new Profesor("Profesor Test", "correo@test.com", "1234");
        actividad.setCreador(profesor);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testModificarSumaRatingNoPermitido() {
        actividad.setSumaRating(100);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testModificarNombreNoPermitido() {
        actividad.setNombre("Nuevo Nombre");
    }

    @Test
    public void testCalculoCorrectoRatingPromedio() {
        actividad.agregarReseña(new Reseña("Buena", 8));
        actividad.agregarReseña(new Reseña("Excelente", 10));
        assertEquals(9.0, actividad.calcularPromedioRating(), 0.01);
    }

    @Test
    public void testSumaAcumuladaDeRatings() {
        actividad.agregarReseña(new Reseña("Buena", 8));
        actividad.agregarReseña(new Reseña("Excelente", 10));
        assertEquals(18, actividad.getSumaRating());
    }

    @Test
    public void testAgregarPrerequisitoCorrectamente() {
        Actividad prerrequisito = new Actividad(null, "Prerrequisito Test", "Descripción", "Objetivo", "Media", 30, false, null) {
            @Override
            public void realizar(ProgresoActividad progreso) {}

            @Override
            public void editar(Profesor editor) {}
        };
        actividad.agregarPrerrequisito(prerrequisito);
        assertTrue(actividad.getPrerrequisitos().contains(prerrequisito));
    }

    @Test
    public void testManejoPrerequisitosVacios() {
        Actividad prerrequisitoVacio = null;
        actividad.agregarPrerrequisito(prerrequisitoVacio);
        assertFalse(actividad.getPrerrequisitos().contains(prerrequisitoVacio));
    }
}
