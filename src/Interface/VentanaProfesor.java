package Interface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Persistencia.ManejoPersistencia;
import proyecto.LearningPath;
import proyecto.Profesor;

public class VentanaProfesor extends JFrame {
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
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 255));
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

        // Botón para crear Learning Path
        JButton crearLPButton = new JButton("Crear Learning Path");
        crearLPButton.setBackground(new Color(180, 150, 250));
        crearLPButton.setForeground(Color.WHITE);
        crearLPButton.setFocusPainted(false);
        crearLPButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        crearLPButton.setFont(new Font("Arial", Font.BOLD, 14));
        crearLPButton.addActionListener(e -> abrirFormularioCrearLearningPath());
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(crearLPButton);

        setVisible(true);
    }

    private JPanel crearSeccion(String titulo, List<LearningPath> cursos, boolean esCreador) {
        JPanel seccionPanel = new JPanel();
        seccionPanel.setLayout(new BorderLayout());
        seccionPanel.setBackground(new Color(235, 225, 255));
        seccionPanel.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 200), 2));

        // Encabezado de la sección
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(220, 220, 250));
        JLabel titleLabel = new JLabel(titulo);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(100, 100, 200));
        headerPanel.add(titleLabel, BorderLayout.WEST);

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
            JPanel cursoPanel = new JPanel(new BorderLayout());
            cursoPanel.setBackground(new Color(245, 245, 255));
            cursoPanel.setBorder(BorderFactory.createLineBorder(new Color(180, 150, 250), 1));

            JLabel cursoLabel = new JLabel(lp.getTitulo());
            cursoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            cursoPanel.add(cursoLabel, BorderLayout.CENTER);

            JButton opcionesButton = new JButton("Opciones");
            opcionesButton.setBackground(new Color(150, 120, 255));
            opcionesButton.setForeground(Color.WHITE);
            opcionesButton.setFocusPainted(false);
            opcionesButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            cursoPanel.add(opcionesButton, BorderLayout.EAST);

            cursosPanel.add(cursoPanel);
        }

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
            crearActividad.addActionListener(e -> crearActividadDesdeGUI(lp));
            menu.add(crearActividad);

            JMenuItem eliminarActividad = new JMenuItem("Eliminar Actividad");
            eliminarActividad.addActionListener(e -> eliminarActividadDesdeGUI(lp));
            menu.add(eliminarActividad);
        }

        JMenuItem verActividades = new JMenuItem("Ver Actividades");
        verActividades.addActionListener(e -> mostrarActividades(lp));
        menu.add(verActividades);

        menu.show(this, getWidth() / 2, getHeight() / 2);
    }

    private void abrirFormularioCrearLearningPath() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBackground(new Color(235, 225, 255));

        JLabel tituloLabel = new JLabel("Título:");
        JLabel descripcionLabel = new JLabel("Descripción:");
        JLabel objetivosLabel = new JLabel("Objetivos:");
        JLabel nivelDificultadLabel = new JLabel("Nivel de Dificultad:");
        JLabel duracionLabel = new JLabel("Duración Estimada (min):");

        tituloLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tituloLabel.setForeground(new Color(100, 100, 200));
        descripcionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        descripcionLabel.setForeground(new Color(100, 100, 200));
        objetivosLabel.setFont(new Font("Arial", Font.BOLD, 14));
        objetivosLabel.setForeground(new Color(100, 100, 200));
        nivelDificultadLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nivelDificultadLabel.setForeground(new Color(100, 100, 200));
        duracionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        duracionLabel.setForeground(new Color(100, 100, 200));

        JTextField tituloField = new JTextField();
        JTextField descripcionField = new JTextField();
        JTextField objetivosField = new JTextField();
        String[] nivelesDificultad = {"Bajo", "Medio", "Alto"};
        JComboBox<String> nivelDificultadComboBox = new JComboBox<>(nivelesDificultad);
        JTextField duracionField = new JTextField();

        estilizarCampoTexto(tituloField);
        estilizarCampoTexto(descripcionField);
        estilizarCampoTexto(objetivosField);
        estilizarCampoTexto(duracionField);

        panel.add(tituloLabel);
        panel.add(tituloField);
        panel.add(descripcionLabel);
        panel.add(descripcionField);
        panel.add(objetivosLabel);
        panel.add(objetivosField);
        panel.add(nivelDificultadLabel);
        panel.add(nivelDificultadComboBox);
        panel.add(duracionLabel);
        panel.add(duracionField);

        JButton okButton = new JButton("Crear");
        JButton cancelButton = new JButton("Cancelar");
        okButton.setBackground(new Color(150, 120, 255));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        cancelButton.setBackground(new Color(200, 200, 200));
        cancelButton.setFocusPainted(false);

        panel.add(okButton);
        panel.add(cancelButton);

        JOptionPane.showConfirmDialog(this, panel, "Crear Nuevo Learning Path", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    private void estilizarCampoTexto(JTextField field) {
        field.setBorder(BorderFactory.createLineBorder(new Color(150, 120, 255), 2));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setForeground(new Color(50, 50, 150));
        field.setHorizontalAlignment(SwingConstants.CENTER);
    }


    private void actualizarVentana() {
        getContentPane().removeAll();
        new VentanaProfesor(profesor, persistencia).setVisible(true);
        dispose();
    }

    private void crearActividadDesdeGUI(LearningPath lp) {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBackground(new Color(245, 245, 255));

        // Componentes de entrada
        JTextField nombreField = new JTextField();
        JTextField descripcionField = new JTextField();
        JTextField objetivoField = new JTextField();
        String[] nivelesDificultad = {"Bajo", "Medio", "Alto"};
        JComboBox<String> nivelDificultadComboBox = new JComboBox<>(nivelesDificultad);
        JTextField duracionField = new JTextField();
        JCheckBox obligatorioCheckBox = new JCheckBox("¿Es obligatorio?");
        obligatorioCheckBox.setBackground(new Color(245, 245, 255));
        String[] tiposDeActividad = {"Recurso educativo", "Encuesta", "Tarea", "Quiz", "Examen"};
        JComboBox<String> tipoActividadComboBox = new JComboBox<>(tiposDeActividad);

        // Añadir componentes al panel
        panel.add(new JLabel("Tipo de Actividad:"));
        panel.add(tipoActividadComboBox);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Descripción:"));
        panel.add(descripcionField);
        panel.add(new JLabel("Objetivo:"));
        panel.add(objetivoField);
        panel.add(new JLabel("Nivel de dificultad:"));
        panel.add(nivelDificultadComboBox);
        panel.add(new JLabel("Duración (min):"));
        panel.add(duracionField);
        panel.add(new JLabel(""));
        panel.add(obligatorioCheckBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Crear Actividad", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String tipoActividad = (String) tipoActividadComboBox.getSelectedItem();
                String nombre = nombreField.getText().trim();
                String descripcion = descripcionField.getText().trim();
                String objetivo = objetivoField.getText().trim();
                String nivelDificultad = (String) nivelDificultadComboBox.getSelectedItem();
                int duracion = Integer.parseInt(duracionField.getText().trim());
                boolean obligatorio = obligatorioCheckBox.isSelected();

                Scanner fakeScanner = createFakeScanner(tipoActividad, nombre, descripcion, objetivo, nivelDificultad, duracion, obligatorio);
                profesor.crearActividad(fakeScanner);

                JOptionPane.showMessageDialog(this, "Actividad creada exitosamente.");
                actualizarVentana();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al crear la actividad: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarActividadDesdeGUI(LearningPath lp) {
        List<String> actividadesNombres = new ArrayList<>();
        for (var actividad : lp.getActividades()) {
            actividadesNombres.add(actividad.getNombre());
        }

        if (actividadesNombres.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay actividades en este Learning Path para eliminar.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String actividadSeleccionada = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione la actividad a eliminar:",
                "Eliminar Actividad",
                JOptionPane.PLAIN_MESSAGE,
                null,
                actividadesNombres.toArray(),
                actividadesNombres.get(0)
        );

        if (actividadSeleccionada != null) {
            lp.getActividades().removeIf(actividad -> actividad.getNombre().equals(actividadSeleccionada));
            JOptionPane.showMessageDialog(this, "Actividad eliminada exitosamente.");
            actualizarVentana();
        }
    }

    private void mostrarActividades(LearningPath lp) {
        JPanel panel = new JPanel(new GridLayout(lp.getActividades().size(), 1, 10, 10));
        panel.setBackground(new Color(245, 245, 255));

        for (var actividad : lp.getActividades()) {
            JLabel actividadLabel = new JLabel("• " + actividad.getNombre());
            actividadLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            actividadLabel.setForeground(new Color(100, 100, 200));
            panel.add(actividadLabel);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Actividades en " + lp.getTitulo(), JOptionPane.PLAIN_MESSAGE);
    }

    private Scanner createFakeScanner(String tipoActividad, String nombre, String descripcion, String objetivo, String nivelDificultad, int duracion, boolean obligatorio) {
        String obligatorioTexto = obligatorio ? "si" : "no";
        String inputSimulado = tipoActividad + "\n" + nombre + "\n" + descripcion + "\n" + objetivo + "\n" +
                nivelDificultad + "\n" + duracion + "\n" + obligatorioTexto;
        return new Scanner(inputSimulado);
    }
}
