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

    private static final long serialVersionUID = 1L;
    private Profesor profesor;
    private ManejoPersistencia persistencia;
    private JPanel contentPanel;

    // Constructor
    public VentanaProfesor(Profesor profesor, ManejoPersistencia persistencia) {
        this.profesor = profesor;
        this.persistencia = persistencia;

        // Configure the main window
        setTitle("Learning Paths - Profesor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 255)); // Light background
        add(mainPanel);

        // Header with image
        JLabel headerLabel = new JLabel(new ImageIcon("img/LP2.jpg"));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(245, 245, 255));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add sections
        updateSections();

        setVisible(true);
    }

    private void updateSections() {
        // Clear the panel
        contentPanel.removeAll();

        // "Cursos creados" section
        contentPanel.add(createSection("Cursos creados", profesor.getLearningPathsCreados(), true));

        // Spacer
        contentPanel.add(Box.createVerticalStrut(20));

        // "Otros cursos" section
        contentPanel.add(createSection("Otros cursos", persistencia.getMapaPaths().values().stream().toList(), false));

        // Add "Create Learning Path" button
        JButton createLPButton = new JButton("Crear Learning Path");
        createLPButton.setFont(new Font("Arial", Font.BOLD, 14));
        createLPButton.setBackground(new Color(200, 170, 255));
        createLPButton.setForeground(new Color(100, 50, 150));
        createLPButton.setFocusPainted(false);
        createLPButton.addActionListener(e -> showCreateLPDialog());
        contentPanel.add(Box.createVerticalStrut(20)); // Spacer
        contentPanel.add(createLPButton);

        // Refresh the panel
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createSection(String title, List<LearningPath> courses, boolean isCreator) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BorderLayout());
        sectionPanel.setBackground(new Color(245, 245, 255));
        sectionPanel.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 200), 2));

        // Section header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(220, 220, 250));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(100, 100, 200));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Collapse/expand icon
        JLabel toggleIcon = new JLabel(new ImageIcon("img/despliegue1.jpg"));
        headerPanel.add(toggleIcon, BorderLayout.EAST);

        sectionPanel.add(headerPanel, BorderLayout.NORTH);

        // Courses panel
        JPanel coursesPanel = new JPanel();
        coursesPanel.setLayout(new BoxLayout(coursesPanel, BoxLayout.Y_AXIS));
        coursesPanel.setBackground(new Color(245, 245, 255));
        sectionPanel.add(coursesPanel, BorderLayout.CENTER);
        coursesPanel.setVisible(false);

        for (LearningPath lp : courses) {
            JButton courseButton = new JButton(lp.getTitulo());
            courseButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            coursesPanel.add(courseButton);
            courseButton.addActionListener(e -> showCourseOptions(lp, isCreator));
            coursesPanel.add(Box.createVerticalStrut(5));
        }

        // Collapse/expand action
        toggleIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            private boolean expanded = false;

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                expanded = !expanded;
                coursesPanel.setVisible(expanded);
                toggleIcon.setIcon(new ImageIcon(expanded ? "img/despliegue2.jpg" : "img/despliegue1.jpg"));
                sectionPanel.revalidate();
                sectionPanel.repaint();
            }
        });

        return sectionPanel;
    }

    private void showCreateLPDialog() {
        JDialog dialog = new JDialog(this, "Crear Nuevo Learning Path", true);
        dialog.setSize(450, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        dialog.getContentPane().setBackground(new Color(245, 235, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Crear Nuevo Learning Path", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(100, 50, 150));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel labelTitulo = new JLabel("Título:");
        labelTitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        dialog.add(labelTitulo, gbc);

        gbc.gridx = 1;
        JTextField textTitulo = createStyledTextField();
        dialog.add(textTitulo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel labelDescripcion = new JLabel("Descripción:");
        labelDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        dialog.add(labelDescripcion, gbc);

        gbc.gridx = 1;
        JTextField textDescripcion = createStyledTextField();
        dialog.add(textDescripcion, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel labelObjetivos = new JLabel("Objetivos:");
        labelObjetivos.setFont(new Font("Arial", Font.PLAIN, 14));
        dialog.add(labelObjetivos, gbc);

        gbc.gridx = 1;
        JTextField textObjetivos = createStyledTextField();
        dialog.add(textObjetivos, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel labelNivelDificultad = new JLabel("Nivel de Dificultad:");
        labelNivelDificultad.setFont(new Font("Arial", Font.PLAIN, 14));
        dialog.add(labelNivelDificultad, gbc);

        gbc.gridx = 1;
        JComboBox<String> comboNivelDificultad = new JComboBox<>(new String[]{"Bajo", "Medio", "Alto"});
        comboNivelDificultad.setBackground(new Color(220, 200, 255));
        comboNivelDificultad.setForeground(new Color(70, 30, 100));
        comboNivelDificultad.setFont(new Font("Arial", Font.PLAIN, 14));
        dialog.add(comboNivelDificultad, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel labelDuracion = new JLabel("Duración Estimada (min):");
        labelDuracion.setFont(new Font("Arial", Font.PLAIN, 14));
        dialog.add(labelDuracion, gbc);

        gbc.gridx = 1;
        JTextField textDuracion = createStyledTextField();
        dialog.add(textDuracion, gbc);

        // Buttons
        gbc.gridy++;
        gbc.gridx = 0;
        JButton crearButton = createStyledButton("Crear");
        dialog.add(crearButton, gbc);

        gbc.gridx = 1;
        JButton cancelarButton = createStyledButton("Cancelar");
        dialog.add(cancelarButton, gbc);

        crearButton.addActionListener(e -> {
            try {
                String titulo = textTitulo.getText().trim();
                String descripcion = textDescripcion.getText().trim();
                String objetivos = textObjetivos.getText().trim();
                String nivelDificultad = (String) comboNivelDificultad.getSelectedItem();
                int duracion = Integer.parseInt(textDuracion.getText().trim());

                if (titulo.isEmpty() || descripcion.isEmpty() || objetivos.isEmpty() || duracion <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Todos los campos son obligatorios y la duración debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                LearningPath nuevoLP = profesor.crearLearningPath(titulo, descripcion, objetivos, nivelDificultad, duracion, persistencia);
                persistencia.getMapaPaths().put(nuevoLP.getTitulo(), nuevoLP);

                JOptionPane.showMessageDialog(dialog, "Learning Path creado exitosamente.");
                dialog.dispose();
                updateSections(); // Update the main window
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Duración debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelarButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBackground(new Color(240, 230, 255));
        textField.setForeground(new Color(80, 40, 120));
        textField.setBorder(BorderFactory.createLineBorder(new Color(150, 100, 200)));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        return textField;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBackground(new Color(150, 120, 250));
        button.setOpaque(true);
        return button;
    }

    private void showCourseOptions(LearningPath lp, boolean isCreator) {
        JPopupMenu menu = new JPopupMenu();
        if (isCreator) {
            JMenuItem crearActividad = new JMenuItem("Crear Actividad");
            crearActividad.addActionListener(e -> JOptionPane.showMessageDialog(this, "Crear actividad para el curso: " + lp.getTitulo()));
            menu.add(crearActividad);

            JMenuItem modificarCurso = new JMenuItem("Modificar Curso");
            modificarCurso.addActionListener(e -> JOptionPane.showMessageDialog(this, "Modificar curso: " + lp.getTitulo()));
            menu.add(modificarCurso);
        }

        JMenuItem verActividades = new JMenuItem("Ver Actividades");
        verActividades.addActionListener(e -> JOptionPane.showMessageDialog(this, "Actividades del curso: " + lp.getTitulo()));
        menu.add(verActividades);

        menu.show(this, getWidth() / 2, getHeight() / 2);
    }
}
