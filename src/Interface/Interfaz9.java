package Interface;

import javax.swing.*;
import java.awt.*;
import proyecto.Estudiante;
import proyecto.Profesor;
import proyecto.Usuario;
import Persistencia.ManejoPersistencia;

@SuppressWarnings("serial")
public class Interfaz9 extends JFrame {
    private static final ManejoPersistencia persistencia = new ManejoPersistencia();
    private JPanel centralPanel;

    public Interfaz9() {
        // Load data from CSV
        cargarDatos();

        // Configure the main window
        setTitle("Learning Path - Inicio");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Banner
        JLabel banner = new JLabel(new ImageIcon("img/LP.jpg"), SwingConstants.CENTER);
        add(banner, BorderLayout.NORTH);

        // Left and Right Panels
        JPanel leftPanel = createSidePanel("Profesor");
        JPanel rightPanel = createSidePanel("Estudiante");

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        // Central Panel
        centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralPanel.setBackground(new Color(245, 245, 255));
        add(centralPanel, BorderLayout.CENTER);

        // Initial View

        setVisible(true);
    }

    private JPanel createSidePanel(String role) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 0, 0, 0); // Spacing
        gbc.anchor = GridBagConstraints.CENTER;

        JButton button = createCircularButton(role);
        button.addActionListener(e -> mostrarOpcionesUsuario(role));
        panel.add(button, gbc);

        return panel;
    }

    private void cargarDatos() {
        try {
            System.out.println("Cargando datos del sistema...");
            persistencia.cargarLearningPaths();
            persistencia.cargarEstudiantes();
            persistencia.cargarProfesores();
            persistencia.cargarProgresoPaths();
            persistencia.cargarProgresoActividades();
            System.out.println("Datos cargados exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarOpcionesUsuario(String perfil) {
        // Clear central panel
        centralPanel.removeAll();

        JLabel titulo = new JLabel("Opciones para " + perfil+ " ", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botonLogin = createStyledButton("Iniciar Sesión");
        JButton botonRegistro = createStyledButton("Registrarse");

        botonLogin.addActionListener(e -> mostrarFormulario(perfil, "login"));
        botonRegistro.addActionListener(e -> mostrarFormulario(perfil, "registro"));

        centralPanel.add(Box.createVerticalStrut(50)); // Spacer
        centralPanel.add(titulo);
        centralPanel.add(Box.createVerticalStrut(30));
        centralPanel.add(botonLogin);
        centralPanel.add(Box.createVerticalStrut(15));
        centralPanel.add(botonRegistro);

        centralPanel.revalidate();
        centralPanel.repaint();
    }

    private void mostrarFormulario(String perfil, String accion) {
        // Clear central panel
        centralPanel.removeAll();

        // Configure layout
        centralPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel((accion.equals("login") ? "Inicio de Sesión" : "Registro") + " - " + perfil, SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 2;
        centralPanel.add(titulo, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;

        if (accion.equals("login")) {
            addLoginForm(gbc, perfil);
        } else {
            addRegisterForm(gbc, perfil);
        }

        centralPanel.revalidate();
        centralPanel.repaint();
    }

    private void addLoginForm(GridBagConstraints gbc, String perfil) {
        // Email
        JLabel labelCorreo = new JLabel("Correo:");
        centralPanel.add(labelCorreo, gbc);
        gbc.gridx++;
        JTextField textCorreo = new JTextField(15);
        centralPanel.add(textCorreo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;

        // Password
        JLabel labelContrasena = new JLabel("Contraseña:");
        centralPanel.add(labelContrasena, gbc);
        gbc.gridx++;
        JPasswordField textContrasena = new JPasswordField(15);
        centralPanel.add(textContrasena, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        // Button
        JButton botonIngresar = createStyledButton("Ingresar");
        botonIngresar.addActionListener(e -> {
            String correo = textCorreo.getText().trim();
            String contrasena = new String(textContrasena.getPassword()).trim();
            if (correo.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            Usuario usuario = perfil.equals("Estudiante") ? persistencia.buscarEstu(correo) : persistencia.buscarProfe(correo);

            if (usuario == null || !usuario.getContrasena().equals(contrasena)) {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
            } else {
                if (usuario instanceof Profesor) {
                    new VentanaProfesor((Profesor) usuario, persistencia).setVisible(true);
                } else {
                    new VentanaEstudiante().setVisible(true);
                }
                this.dispose();
            }
        });
        centralPanel.add(botonIngresar, gbc);
    }

    private void addRegisterForm(GridBagConstraints gbc, String perfil) {
        // Name
        JLabel labelNombre = new JLabel("Nombre:");
        centralPanel.add(labelNombre, gbc);
        gbc.gridx++;
        JTextField textNombre = new JTextField(15);
        centralPanel.add(textNombre, gbc);

        gbc.gridy++;
        gbc.gridx = 0;

        // Email
        JLabel labelCorreo = new JLabel("Correo:");
        centralPanel.add(labelCorreo, gbc);
        gbc.gridx++;
        JTextField textCorreo = new JTextField(15);
        centralPanel.add(textCorreo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;

        // Password
        JLabel labelContrasena = new JLabel("Contraseña:");
        centralPanel.add(labelContrasena, gbc);
        gbc.gridx++;
        JPasswordField textContrasena = new JPasswordField(15);
        centralPanel.add(textContrasena, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        // Button
        JButton botonRegistrar = createStyledButton("Registrar");
        botonRegistrar.addActionListener(e -> {
            String nombre = textNombre.getText().trim();
            String correo = textCorreo.getText().trim();
            String contrasena = new String(textContrasena.getPassword()).trim();

            if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            if (!correo.endsWith("@uniandes.edu.co")) {
                JOptionPane.showMessageDialog(this, "El correo debe ser institucional (@uniandes.edu.co).");
                return;
            }

            if (perfil.equals("Estudiante") && persistencia.buscarEstu(correo) == null) {
                persistencia.crearEstudianteData(nombre, correo, contrasena);
                JOptionPane.showMessageDialog(this, "Estudiante registrado exitosamente.");
            } else if (perfil.equals("Profesor") && persistencia.buscarProfe(correo) == null) {
                persistencia.crearProfesorData(nombre, correo, contrasena);
                JOptionPane.showMessageDialog(this, "Profesor registrado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Ya existe un usuario con este correo.");
            }
        });
        centralPanel.add(botonRegistrar, gbc);
    }

    private JButton createCircularButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 100));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(100, 150, 250));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        return button;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(new Color(100, 150, 250));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Interfaz9 ventana = new Interfaz9();
            ventana.setVisible(true);
        });
    }
}
