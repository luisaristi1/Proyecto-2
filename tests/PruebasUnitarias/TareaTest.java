package PruebasUnitarias;
import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

import proyecto.Actividad;
import proyecto.Estudiante;
import proyecto.Profesor;
import proyecto.ProgresoActividad;
import proyecto.Reseña;
import proyecto.Tarea;

public class TareaTest {
    private Tarea tarea;
    private Profesor profesor;
    private Actividad actividad;

    @Before
    public void setUp() {
        profesor = new Profesor("Prueba Profe", "profe@example.com", "profe123");
        tarea = new Tarea(null, "Tarea 1", "Descripción inicial", "Objetivo inicial", "Media", 60, true, profesor);
    }

    @Test
    public void testConstructor() {
        assertEquals("Tarea 1", tarea.getNombre());
        assertEquals("Descripción inicial", tarea.getDescripcion());
        assertEquals("Objetivo inicial", tarea.getObjetivo());
        assertEquals("Media", tarea.getNivelDificultad());
        assertEquals(60, tarea.getDuracionEsperada());
        assertTrue(tarea.isObligatorio());
        assertNotNull(tarea.getCreador());
    }

    @Test
    public void testEnviarTarea() {
        tarea.enviarTarea(new java.util.Scanner("Correo electrónico"));
        assertNotNull(tarea.getMedioEntrega());
        assertEquals("Correo electrónico", tarea.getMedioEntrega());
    }

    @Test
    public void testRealizarTarea() {
        ProgresoActividad progreso = new ProgresoActividad(tarea, new Estudiante("Est Prueba", "est@ejemplo.com", "est123"));
        tarea.realizar(progreso);
        assertNotNull(progreso.getFechaFin());
        assertEquals("Enviada", progreso.getResultado());
    }

    @Test
    public void testEditarConPermisos() {
        tarea.editar(profesor);
        tarea.cambios(new java.util.Scanner("descripcion\nNueva descripción"));
        assertEquals("Nueva descripción", tarea.getDescripcion());
    }

    @Test
    public void testEditarSinPermisos() {
        Profesor otroProfesor = new Profesor("Profe2 Prueba", "profe2@example.com", "profe2456");
        tarea.editar(otroProfesor);
        assertFalse(otroProfesor.equals(tarea.getCreador()));
    }

    @Test
    public void testCambiosDescripcion() {
        tarea.cambios(new java.util.Scanner("descripcion\nNueva descripción"));
        assertEquals("Nueva descripción", tarea.getDescripcion());
    }

    @Test
    public void testCambiosObjetivo() {
        tarea.cambios(new java.util.Scanner("objetivo\nNuevo objetivo"));
        assertEquals("Nuevo objetivo", tarea.getObjetivo());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCambiosOpcionInvalida() {
        tarea.cambios(new java.util.Scanner("opcionInvalida\nNueva información"));
    }

    @Test
    public void testEstablecerFechaLimite() {
        tarea.establecerFechaLimite(new Date());
        assertNotNull(tarea.getFechaLimite());
    }

    @Test
    public void testRecordatorioActividad() {
        ProgresoActividad progreso = new ProgresoActividad(tarea, new Estudiante("Est Prueba", "est@ejemplo.com", "est123"));
        tarea.establecerFechaLimite(new Date(System.currentTimeMillis() + 3600 * 1000)); // 1 hour from now
        tarea.RecordatorioActividad(progreso);
        assertNotNull(tarea.getFechaLimite());
    }

    @Test
    public void testAgregarActividadSeguimiento() {
        Actividad seguimiento = new Tarea(null, "Seguimiento", "Descripción", "Objetivo", "Media", 30, true, profesor);
        tarea.agregarActividadSeguimiento(seguimiento);
        assertTrue(tarea.getActividadSeguimiento().contains(seguimiento));
    }

    @Test
    public void testRecomendarActividad() {
        ProgresoActividad progreso = new ProgresoActividad(tarea, new Estudiante("Est Prueba", "est@ejemplo.com", "est123"));
        tarea.recomendarActividad(progreso, tarea);
        assertEquals("Descripción inicial", tarea.getDescripcion());
    }

    @Test
    public void testClonarActividad() {
        Tarea clon = (Tarea) tarea.clonarActividad(profesor);
        assertNotNull(clon);
        assertEquals(tarea.getNombre(), clon.getNombre());
        assertEquals(tarea.getDescripcion(), clon.getDescripcion());
    }

    @Test
    public void testAgregarYCalcularPromedioReseñas() {
        tarea.agregarReseña(new Reseña("Buena tarea", 8));
        tarea.agregarReseña(new Reseña("Excelente tarea", 10));
        assertEquals(9.0, tarea.calcularPromedioRating(), 0.01);
    }
}
