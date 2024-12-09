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
        // Crear un nuevo JDialog como ventana independiente
        JDialog dialog = new JDialog(this, "Crear Nuevo Learning Path", true);
        dialog.setSize(400, 550);
        dialog.setLocationRelativeTo(this);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Panel principal del formulario
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 235, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título del formulario
        JLabel headerLabel = new JLabel("Crear Nuevo Learning Path");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setForeground(new Color(100, 50, 150));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(headerLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Sección 1: Información Básica
        JPanel infoPanel = crearSeccion("Información Básica");
        infoPanel.add(crearFila("Título:", crearCampoTexto()));
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(crearFila("Descripción:", crearCampoTexto()));
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(crearFila("Objetivos:", crearCampoTexto()));
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Sección 2: Nivel de Dificultad
        JPanel nivelDificultadPanel = crearSeccion("Nivel de Dificultad");
        JLabel nivelLabel = new JLabel("Nivel de Dificultad:");
        nivelLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nivelLabel.setForeground(new Color(100, 50, 150));
        nivelLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JComboBox<String> nivelDificultadComboBox = new JComboBox<>(new String[]{"Bajo", "Medio", "Alto"});
        nivelDificultadComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        nivelDificultadPanel.add(nivelLabel);
        nivelDificultadPanel.add(Box.createVerticalStrut(10));
        nivelDificultadPanel.add(nivelDificultadComboBox);
        mainPanel.add(nivelDificultadPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Sección 3: Duración Estimada
        JPanel duracionPanel = crearSeccion("Duración Estimada");
        JLabel duracionLabel = new JLabel("Duración Estimada (horas):");
        duracionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        duracionLabel.setForeground(new Color(100, 50, 150));
        duracionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField duracionField = crearCampoTexto();
        duracionPanel.add(duracionLabel);
        duracionPanel.add(Box.createVerticalStrut(10));
        duracionPanel.add(duracionField);
        mainPanel.add(duracionPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Botones Crear y Cancelar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 235, 255));
        JButton crearButton = new JButton("Crear");
        JButton cancelarButton = new JButton("Cancelar");
        estilizarBoton(crearButton, new Color(150, 120, 255));
        estilizarBoton(cancelarButton, new Color(200, 200, 200));
        buttonPanel.add(crearButton);
        buttonPanel.add(cancelarButton);

        // Acción de cerrar el formulario
        cancelarButton.addActionListener(e -> dialog.dispose());

        mainPanel.add(buttonPanel);

        // Añadir panel principal al diálogo y mostrarlo
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    // Método para crear secciones con título
    private JPanel crearSeccion(String titulo) {
        JPanel seccionPanel = new JPanel();
        seccionPanel.setLayout(new BoxLayout(seccionPanel, BoxLayout.Y_AXIS));
        seccionPanel.setBackground(new Color(230, 220, 255));
        seccionPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(150, 120, 255), 2),
                titulo,
                0,
                0,
                new Font("Arial", Font.BOLD, 14),
                new Color(100, 50, 150)
        ));
        return seccionPanel;
    }

    // Método para crear filas con etiqueta y componente
    private JPanel crearFila(String texto, JComponent componente) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setBackground(new Color(245, 235, 255));

        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(100, 50, 150));
        label.setPreferredSize(new Dimension(150, 30)); // Ajustar tamaño para alineación
        componente.setPreferredSize(new Dimension(200, 30));

        rowPanel.add(label);
        rowPanel.add(componente);
        return rowPanel;
    }

    // Método para crear campos estilizados
    private JTextField crearCampoTexto() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setForeground(new Color(50, 50, 150));
        textField.setBorder(BorderFactory.createLineBorder(new Color(150, 120, 255), 2));
        return textField;
    }

    // Método para estilizar botones
    private void estilizarBoton(JButton boton, Color color) {
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(120, 30));
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
