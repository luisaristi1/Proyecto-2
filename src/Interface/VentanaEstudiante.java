package Interface;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import Persistencia.ManejoPersistencia;
import proyecto.Estudiante;

public class VentanaEstudiante extends JFrame  {
	private Estudiante estudiante;
    private ManejoPersistencia persistencia;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Learning Path - Estudiante");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            // Panel Izquierdo: Menú de opciones
            JPanel menuPanel = new JPanel();
            menuPanel.setLayout(new GridLayout(3, 1, 10, 10));
            menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JButton btnInscripcion = new JButton("Inscripción de Cursos");
            JButton btnMisCursos = new JButton("Mis Cursos");
            JButton btnProgreso = new JButton("Progreso Actividades");

            menuPanel.add(btnInscripcion);
            menuPanel.add(btnMisCursos);
            menuPanel.add(btnProgreso);

            // Panel Derecho: Contenido dinámico
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new CardLayout());

            // Panel Inscripción de Cursos
            JPanel inscripcionPanel = new JPanel();
            inscripcionPanel.setLayout(new BorderLayout());
            inscripcionPanel.add(new JLabel("Inscripción de Cursos"), BorderLayout.NORTH);

            JList<String> listaCursos = new JList<>(new String[]{"Curso 1", "Curso 2", "Curso 3"});
            inscripcionPanel.add(new JScrollPane(listaCursos), BorderLayout.CENTER);

            JButton btnDetallesCurso = new JButton("Ver Detalles del Curso");
            inscripcionPanel.add(btnDetallesCurso, BorderLayout.SOUTH);

            // Panel Mis Cursos
            JPanel misCursosPanel = new JPanel();
            misCursosPanel.setLayout(new BorderLayout());
            misCursosPanel.add(new JLabel("Mis Cursos"), BorderLayout.NORTH);

            JList<String> listaMisCursos = new JList<>(new String[]{"Curso 6", "Curso 8", "Curso 9"});
            misCursosPanel.add(new JScrollPane(listaMisCursos), BorderLayout.CENTER);

            JButton btnProgresoCurso = new JButton("Ver Progreso del Curso");
            misCursosPanel.add(btnProgresoCurso, BorderLayout.SOUTH);

            // Panel Progreso Actividades
            JPanel progresoPanel = new JPanel();
            progresoPanel.setLayout(new BorderLayout());
            progresoPanel.add(new JLabel("Progreso del Curso"), BorderLayout.NORTH);

            JPanel progresoDetalles = new JPanel();
            progresoDetalles.setLayout(new GridLayout(3, 2, 10, 10));
            progresoDetalles.add(new JLabel("Progreso:"));
            JProgressBar progressBar = new JProgressBar(0, 100);
            progressBar.setValue(28); // Ejemplo
            progresoDetalles.add(progressBar);

            progresoDetalles.add(new JLabel("% Éxito:"));
            progresoDetalles.add(new JLabel("72%"));

            progresoDetalles.add(new JLabel("% Fracaso:"));
            progresoDetalles.add(new JLabel("28%"));

            progresoPanel.add(progresoDetalles, BorderLayout.CENTER);

            // Añadir paneles al contentPanel
            contentPanel.add(inscripcionPanel, "Inscripcion");
            contentPanel.add(misCursosPanel, "MisCursos");
            contentPanel.add(progresoPanel, "Progreso");

            // Cambiar contenido dinámicamente con botones
            btnInscripcion.addActionListener(e -> {
                CardLayout cl = (CardLayout) (contentPanel.getLayout());
                cl.show(contentPanel, "Inscripcion");
            });

            btnMisCursos.addActionListener(e -> {
                CardLayout cl = (CardLayout) (contentPanel.getLayout());
                cl.show(contentPanel, "MisCursos");
            });

            btnProgreso.addActionListener(e -> {
                CardLayout cl = (CardLayout) (contentPanel.getLayout());
                cl.show(contentPanel, "Progreso");
            });

            // Añadir paneles al marco principal
            frame.add(menuPanel, BorderLayout.WEST);
            frame.add(contentPanel, BorderLayout.CENTER);

            // Mostrar ventana
            frame.setVisible(true);
        });
    }

	
}
