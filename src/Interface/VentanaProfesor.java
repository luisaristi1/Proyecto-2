	package Interface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Persistencia.ManejoPersistencia;
import proyecto.LearningPath;
import proyecto.Profesor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class VentanaProfesor extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Profesor profesor;
    private ManejoPersistencia persistencia;

    // Constructor
    public VentanaProfesor(Profesor profesor, ManejoPersistencia persistencia) {
        this.profesor = profesor;
        this.persistencia = persistencia;

        // Configuración de la ventana
        setTitle("Learning Paths - Profesor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 255)); // Fondo claro
        add(mainPanel);

        // Encabezado con imagen
        JLabel headerLabel = new JLabel(new ImageIcon("img/LP2.jpg"));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Panel central
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(245, 245, 255));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Sección de "Cursos creados"
        contentPanel.add(crearSeccion("Cursos creados", profesor.getLearningPathsCreados(), true));

        // Espaciador
        contentPanel.add(Box.createVerticalStrut(20));

        // Sección de "Otros cursos"
        contentPanel.add(crearSeccion("Otros cursos", persistencia.getMapaPaths().values().stream().toList(), false));

        setVisible(true);
    }

    private JPanel crearSeccion(String titulo, List<LearningPath> cursos, boolean esCreador) {
        JPanel seccionPanel = new JPanel();
        seccionPanel.setLayout(new BorderLayout());
        seccionPanel.setBackground(new Color(245, 245, 255));
        seccionPanel.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 200), 2));

        // Encabezado de la sección
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(220, 220, 250));
        JLabel titleLabel = new JLabel(titulo);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(100, 100, 200));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Ícono de despliegue
        JLabel despliegueIcon = new JLabel(new ImageIcon("img/despliegue1.jpg"));
        headerPanel.add(despliegueIcon, BorderLayout.EAST);

        seccionPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel de cursos
        JPanel cursosPanel = new JPanel();
        cursosPanel.setLayout(new BoxLayout(cursosPanel, BoxLayout.Y_AXIS));
        cursosPanel.setBackground(new Color(245, 245, 255));
        seccionPanel.add(cursosPanel, BorderLayout.CENTER);
        cursosPanel.setVisible(false);

        for (LearningPath lp : cursos) {
            JButton cursoButton = new JButton(lp.getTitulo());
            cursoButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            cursosPanel.add(cursoButton);
            cursoButton.addActionListener(e -> mostrarOpcionesCurso(lp, esCreador));
            cursosPanel.add(Box.createVerticalStrut(5));
        }

        // Despliegue/colapso
        despliegueIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            private boolean desplegado = false;

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                desplegado = !desplegado;
                cursosPanel.setVisible(desplegado);
                despliegueIcon.setIcon(new ImageIcon(desplegado ? "img/despliegue2.jpg" : "img/despliegue1.jpg"));
                seccionPanel.revalidate();
                seccionPanel.repaint();
            }
        });

        return seccionPanel;
    }

    private void mostrarOpcionesCurso(LearningPath lp, boolean esCreador) {
        JPopupMenu menu = new JPopupMenu();
        if (esCreador) {
            JMenuItem crearActividad = new JMenuItem("Crear Actividad");
            crearActividad.addActionListener(e -> crearActividad(lp));
            menu.add(crearActividad);

            JMenuItem modificarCurso = new JMenuItem("Modificar Curso");
            modificarCurso.addActionListener(e -> modificarCurso(lp));
            menu.add(modificarCurso);
        }

        JMenuItem verActividades = new JMenuItem("Ver Actividades");
        verActividades.addActionListener(e -> verActividades(lp));
        menu.add(verActividades);

        menu.show(this, getWidth() / 2, getHeight() / 2);
    }

    private void crearActividad(LearningPath lp) {
        JOptionPane.showMessageDialog(this, "Crear actividad para el curso: " + lp.getTitulo());
        // Lógica para crear una actividad
    }

    private void modificarCurso(LearningPath lp) {
        JOptionPane.showMessageDialog(this, "Modificar curso: " + lp.getTitulo());
        // Lógica para modificar el curso
    }

    private void verActividades(LearningPath lp) {
        JOptionPane.showMessageDialog(this, "Actividades del curso: " + lp.getTitulo());
        // Lógica para ver actividades del curso
    }
}
