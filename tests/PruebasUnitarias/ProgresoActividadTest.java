package PruebasUnitarias;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import proyecto.Actividad;
import proyecto.Estudiante;
import proyecto.ProgresoActividad;
import proyecto.Tarea;

import java.util.Date;

public class ProgresoActividadTest {

    private ProgresoActividad progresoActividad;
    private Actividad actividad;
    private Estudiante estudiante;

    @Before
    public void setUp() {
        estudiante = new Estudiante("Estudiante Test", "estudiante@test.com", "password");
        actividad = new Tarea(null, "Tarea Test", "Descripción de prueba", "Objetivo de prueba", "Media", 60, true, null);
        progresoActividad = new ProgresoActividad(actividad, estudiante);
    }

    @Test
    public void testConstructor() {
        assertNotNull(progresoActividad);
        assertEquals(actividad, progresoActividad.getActividad());
        assertEquals(estudiante, progresoActividad.getEstudiante());
        assertEquals("Por completar", progresoActividad.getResultado());
        assertFalse(progresoActividad.isCompletada());
    }

    @Test
    public void testMarcarRealizada() {
        progresoActividad.marcarRealizada("Aprobada", new Date());
        assertTrue(progresoActividad.isCompletada());
        assertEquals("Aprobada", progresoActividad.getResultado());
        assertNotNull(progresoActividad.getFechaFin());
    }

    @Test
    public void testCalcularTiempoDedicado() {
        Date inicio = new Date();
        Date fin = new Date(inicio.getTime() + 3600 * 1000); // 1 hora después
        long tiempo = progresoActividad.calcularTiempoDedicado(inicio, fin);
        assertEquals(1, tiempo);
    }

    @Test
    public void testCompletarActividad() {
        progresoActividad.completarActividad("Aprobada");
        assertEquals("Aprobada", progresoActividad.getResultado());
    }

    @Test
    public void testSetFechaInicio() {
        Date fechaInicio = new Date();
        progresoActividad.setFechaInicio(fechaInicio);
        assertEquals(fechaInicio, progresoActividad.getFechaInicio());
    }

    @Test
    public void testSetFechaFin() {
        Date fechaFin = new Date();
        progresoActividad.setFechaFin(fechaFin);
        assertEquals(fechaFin, progresoActividad.getFechaFin());
    }
}
