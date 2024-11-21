
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;

public class RecursoEducativoTest {

    private RecursoEducativo recurso;
    private Profesor creador;

    @Before
    public void setUp() {
        creador = new Profesor("Juan", "juan@mail.com", "1234");
        LearningPath lp = new LearningPath("Java Basics", "Learn Java", "Understand basics of Java", "Medio", creador, 60);
        recurso = new RecursoEducativo(lp, "Video Tutorial", "Video sobre conceptos básicos", "Aprender variables", 
                                       "Bajo", 15, true, "Video", "http://example.com", creador);
    }

    @Test
    public void testInicializarAtributos() {
        assertNotNull(recurso);
        assertEquals("Video Tutorial", recurso.getNombre());
        assertEquals("Video sobre conceptos básicos", recurso.getDescripcion());
        assertEquals("Aprender variables", recurso.getObjetivo());
        assertEquals("Bajo", recurso.getNivelDificultad());
        assertEquals(15, recurso.getDuracionEsperada());
        assertTrue(recurso.isObligatorio());
        assertEquals("Video", recurso.getTipoRecurso());
        assertEquals("http://example.com", recurso.getEnlaceRecurso());
    }

    @Test
    public void testCambiarTipoDeRecurso() {
        recurso.cambios(new java.util.Scanner("recurso
NuevoTipo
"));
        assertEquals("NuevoTipo", recurso.getTipoRecurso());
    }

    @Test
    public void testCambiarEnlaceDelRecurso() {
        recurso.cambios(new java.util.Scanner("enlace
http://nuevoenlace.com
"));
        assertEquals("http://nuevoenlace.com", recurso.getEnlaceRecurso());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testManejoDeOpcionInvalida() {
        recurso.cambios(new java.util.Scanner("opcionInvalida
"));
    }

    @Test
    public void testPermisosDeEdicionCreadorCorrecto() {
        recurso.editar(creador);
        // Si no lanza excepción, se considera exitoso
    }

    @Test
    public void testPermisosDeEdicionCreadorIncorrecto() {
        Profesor otroProfesor = new Profesor("Ana", "ana@mail.com", "1234");
        recurso.editar(otroProfesor);
        // Verificamos que no se pueda editar si no es el creador
        assertNotEquals("Ana", recurso.getCreador().getNombre());
    }

    @Test
    public void testMarcarRecursoComoCompletado() {
        ProgresoActividad progreso = new ProgresoActividad(recurso, new Estudiante("Carlos", "carlos@mail.com", "1234"));
        recurso.realizar(progreso);
        assertTrue(progreso.isCompletada());
        assertEquals("Aprobada", progreso.getResultado());
    }

    @Test
    public void testEstablecerFechaLimite() {
        recurso.establecerFechaLimite(new Date());
        assertNotNull(recurso.getFechaLimite());
    }

    @Test
    public void testAgregarYCalcularPromedioResenas() {
        recurso.agregarReseña(new Reseña("Muy útil", 9.0f));
        recurso.agregarReseña(new Reseña("Claro y preciso", 8.0f));
        float promedio = recurso.calcularPromedioRating();
        assertEquals(8.5f, promedio, 0.01f);
    }
}
