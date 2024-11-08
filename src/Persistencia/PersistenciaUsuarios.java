package Persistencia;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import proyecto.Usuario;
import proyecto.Estudiante;
import proyecto.Profesor;

public class PersistenciaUsuarios {

	private static final String NOMBRE = "nombre";
    private static final String CORREO = "correo";
    private static final String CONTRASENA = "password";
    private static final String TIPO_USUARIO = "tipoUsuario";
    
    
    public List<Usuario> cargarUsuarios(String archivo) throws IOException {
        List<Usuario> usuarios = new ArrayList<>();
        String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
        JSONObject raiz = new JSONObject( jsonCompleto );

        cargarUsuarios(usuarios, raiz.getJSONArray("usuarios"));
        return usuarios;
    }
    
    private void cargarUsuarios(List<Usuario> usuarios, JSONArray jUsuarios) {
        int numUsuarios = jUsuarios.length();
        for (int i = 0; i < numUsuarios; i++) {
            JSONObject usuario = jUsuarios.getJSONObject(i);
            String tipoUsuario = usuario.getString(TIPO_USUARIO);
            String nombre = usuario.getString(NOMBRE);
            String username = usuario.getString(CORREO);
            String password = usuario.getString(CONTRASENA);
            Usuario nuevoUsuario = null;

            if ("Profesor".equals(tipoUsuario)) {
                nuevoUsuario = new Profesor(nombre, username, password);
            } else if ("Estudiante".equals(tipoUsuario)) {
                nuevoUsuario = new Estudiante(nombre, username, password);
            }

            usuarios.add(nuevoUsuario);
        }
    }
    
    public void salvarUsuarios(String archivo, List<Usuario> usuarios) throws IOException {
        JSONObject jobject = new JSONObject();
        JSONArray jUsuarios = new JSONArray();

        for (Usuario usuario : usuarios) {
        	
            JSONObject jUsuario = new JSONObject();
            jUsuario.put(NOMBRE, usuario.getNombre());
            jUsuario.put(CORREO, usuario.getCorreo());
            jUsuario.put(CONTRASENA, usuario.getContrasena());
            jUsuario.put(TIPO_USUARIO, usuario.getTipoUsuario());
            jUsuarios.put(jUsuario);
        }

        jobject.put("usuarios", jUsuarios);
        try (PrintWriter pw = new PrintWriter(archivo)) {
            jobject.write(pw, 2, 0);
        }
    }

}
