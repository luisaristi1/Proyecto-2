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

    public Interfaz9() {
        // Load data from CSV
        cargarDatos();

        // Configure the main window
        setTitle("Learning Path - Inicio");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for image
        JPanel panelImagen = new JPanel(new FlowLayout());
        ImageIcon imagen = new ImageIcon("img/LP.jpg");
        JLabel labelImagen = new JLabel(imagen);
        panelImagen.add(labelImagen);

        // Center panel for buttons
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 20, 20));
        JButton botonEstudiante = new JButton("Perfil Estudiante");
        JButton botonProfesor = new JButton("Perfil Profesor");

        botonEstudiante.addActionListener(e -> abrirVentanaPerfil("Estudiante"));
        botonProfesor.addActionListener(e -> abrirVentanaPerfil("Profesor"));

        panelBotones.add(botonEstudiante);
        panelBotones.add(botonProfesor);

        // Add components to the main frame
        add(panelImagen, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
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

    private void abrirVentanaPerfil(String perfil) {
        JFrame ventanaPerfil = new JFrame("Opciones - " + perfil);
        ventanaPerfil.setSize(400, 300);
        ventanaPerfil.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaPerfil.setLayout(new GridLayout(2, 1, 20, 20));

        JButton botonLogin = new JButton("Ya tengo una cuenta");
        JButton botonRegistro = new JButton("Registrarme");

        botonLogin.addActionListener(e -> abrirVentanaLogin(perfil));
        botonRegistro.addActionListener(e -> abrirVentanaRegistro(perfil));

        ventanaPerfil.add(botonLogin);
        ventanaPerfil.add(botonRegistro);
        ventanaPerfil.setVisible(true);
    }

    private void abrirVentanaRegistro(String perfil) {
        JFrame ventanaRegistro = new JFrame("Registro - " + perfil);
        ventanaRegistro.setSize(500, 400);
        ventanaRegistro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaRegistro.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel labelNombre = new JLabel("Nombre:");
        JTextField textNombre = new JTextField();

        JLabel labelCorreo = new JLabel("Correo:");
        JTextField textCorreo = new JTextField();

        JLabel labelContrasena = new JLabel("Contraseña:");
        JPasswordField textContrasena = new JPasswordField();

        JButton botonRegistrar = new JButton("Registrar");

        botonRegistrar.addActionListener(e -> {
            String nombre = textNombre.getText().trim();
            String correo = textCorreo.getText().trim();
            String contrasena = new String(textContrasena.getPassword()).trim();

            if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(ventanaRegistro, "Todos los campos son obligatorios.");
                return;
            }

            if (!correo.endsWith("@uniandes.edu.co")) {
                JOptionPane.showMessageDialog(ventanaRegistro, "El correo debe ser institucional (@uniandes.edu.co).");
                return;
            }

            if (perfil.equals("Estudiante")) {
                if (persistencia.buscarEstu(correo) != null) {
                    JOptionPane.showMessageDialog(ventanaRegistro, "Ya existe un estudiante con este correo.");
                } else {
                    persistencia.crearEstudianteData(nombre, correo, contrasena);
                    JOptionPane.showMessageDialog(ventanaRegistro, "Estudiante registrado exitosamente.");
                    ventanaRegistro.dispose();
                }
            } else if (perfil.equals("Profesor")) {
                if (persistencia.buscarProfe(correo) != null) {
                    JOptionPane.showMessageDialog(ventanaRegistro, "Ya existe un profesor con este correo.");
                } else {
                    persistencia.crearProfesorData(nombre, correo, contrasena);
                    JOptionPane.showMessageDialog(ventanaRegistro, "Profesor registrado exitosamente.");
                    ventanaRegistro.dispose();
                }
            }
        });

        ventanaRegistro.add(labelNombre);
        ventanaRegistro.add(textNombre);
        ventanaRegistro.add(labelCorreo);
        ventanaRegistro.add(textCorreo);
        ventanaRegistro.add(labelContrasena);
        ventanaRegistro.add(textContrasena);
        ventanaRegistro.add(new JLabel());
        ventanaRegistro.add(botonRegistrar);
        ventanaRegistro.setVisible(true);
    }

    private void abrirVentanaLogin(String perfil) {
        JFrame ventanaLogin = new JFrame("Inicio de Sesión - " + perfil);
        ventanaLogin.setSize(400, 300);
        ventanaLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaLogin.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel labelCorreo = new JLabel("Correo:");
        JTextField textCorreo = new JTextField();

        JLabel labelContrasena = new JLabel("Contraseña:");
        JPasswordField textContrasena = new JPasswordField();

        JButton botonIngresar = new JButton("Ingresar");

        botonIngresar.addActionListener(e -> {
            String correo = textCorreo.getText().trim();
            String contrasena = new String(textContrasena.getPassword()).trim();

            if (correo.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(ventanaLogin, "Todos los campos son obligatorios.");
                return;
            }

            Usuario usuario = perfil.equals("Estudiante") ? persistencia.buscarEstu(correo) : persistencia.buscarProfe(correo);

            if (usuario == null || !usuario.getContrasena().equals(contrasena)) {
                JOptionPane.showMessageDialog(ventanaLogin, "Usuario o contraseña incorrectos.");
            } else {
                JOptionPane.showMessageDialog(ventanaLogin, "Se ha ingresado correctamente.");
                ventanaLogin.dispose();
                if (usuario instanceof Profesor) {
                    new VentanaProfesor((Profesor) usuario, persistencia);
                } else if (usuario instanceof Estudiante) {
                    new VentanaEstudiante();
                }
            }
        });

        ventanaLogin.add(labelCorreo);
        ventanaLogin.add(textCorreo);
        ventanaLogin.add(labelContrasena);
        ventanaLogin.add(textContrasena);
        ventanaLogin.add(new JLabel());
        ventanaLogin.add(botonIngresar);
        ventanaLogin.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Interfaz9 ventana = new Interfaz9();
            ventana.setVisible(true);
        });
    }
}
