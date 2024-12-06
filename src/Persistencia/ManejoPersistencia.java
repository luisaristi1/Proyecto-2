package Persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;




import proyecto.Actividad;
import proyecto.LearningPath;
import proyecto.Profesor;
import proyecto.ProgresoActividad;
import proyecto.ProgresoPath;
import proyecto.Estudiante;


import proyecto.Encuesta;
import proyecto.Quiz;
import proyecto.RecursoEducativo;
import proyecto.Reseña;
import proyecto.Tarea;
import proyecto.Usuario;

public class ManejoPersistencia {
	private Map<String, LearningPath> mapaPaths = new HashMap<>();
	private Map<String, Actividad> mapaActividades = new HashMap<>();
	private Map<String, Profesor> mapaProfesores = new HashMap<>();
	private Map<String, Estudiante> mapaEstudiantes = new HashMap<>();
	private Map<String, ProgresoPath> mapaProgresoPath = new HashMap<>();
	private Map<String, ProgresoActividad> mapaProgresoActividad = new HashMap<>();

	
	//SECCI0N PARA ACTIVIDADES
	
	
	//Resolver parametro creador desde controlador
	//Método para crear Actividad
	   public Map<String, Actividad> crearActividadData(Actividad actividad) {
		   mapaActividades.put(actividad.getNombre(), actividad);
		   guardarActividades();
		   return mapaActividades;
	   }
	   
	   //Buscar una actividad
	   public Actividad buscarActividad(String nombre) {
		   return mapaActividades.get(nombre);
	   }
	   
	//Modificar actividad
	   
	   public Map<String, Actividad> modificarActividad(int parametro, String modificar, String nombreActividad){
		   Actividad act = mapaActividades.get(nombreActividad);  
		   
		   if (parametro == 1) {
			   act.setNombre(modificar);
		   }
		   
		   else if(parametro == 2) {
			   act.setDescripcion(modificar);
		   }
		   
		   else if(parametro == 3) {
			   act.setObjetivo(modificar);
		   }
		   
		   else if(parametro == 4) {
			   act.setNivelDificultad(modificar);
		   }
		   
		   else if(parametro == 5) {
			   int m = Integer.parseInt(modificar);
			   act.setDuracionEsperada(m);
		   }
		   
		   else if(parametro == 6) {
			   if (act.getPrerrequisitosNombres().equals(null)) {
				   ArrayList<Actividad> prerequisitos = (ArrayList<Actividad>) act.getPrerrequisitos();
				   ArrayList<String> prerequisitosString = new ArrayList<>();
				   
				   for (Actividad prerequisito: prerequisitos) {
					   String pre = prerequisito.getNombre();
					   prerequisitosString.add(pre);
				   }
				   
				   
				   
				   String[] p = modificar.split(",");
				   
				   System.out.println("Ingrese 1 para agregar los prerrequisitos ingresados, o 2 para eliminarlos.");
				   
				   Scanner scanner = new Scanner(System.in);
			       int numero = scanner.nextInt();
			       scanner.close();
				   
				   if (numero == 1) {
					   for (String pre: p) {
						   prerequisitosString.add(pre);
					   }
				   }
				   
				   else if (numero == 2) {
					   for (String pre: p){
						   prerequisitosString.remove(pre);
					   }
				   }
				   
				   act.setPrerrequisitosNombre(prerequisitosString);
				   
			   }
			   else {
				   ArrayList<String> prerrequisitos =(ArrayList<String>) act.getPrerrequisitosNombres();
				   String[] p = modificar.split(",");
				   
				   System.out.println("Ingrese 1 para agregar los prerrequisitos ingresados, o 2 para eliminarlos.");
				   
				   Scanner scanner = new Scanner(System.in);
			       int numero = scanner.nextInt();
			       scanner.close();
				   
				   if (numero == 1) {
					   for (String pre: p) {
						   prerrequisitos.add(pre);
					   }
				   }
				   
				   else if (numero == 2) {
					   for (String pre: p){
						   prerrequisitos.remove(pre);
					   }
				   }
				   
			   }
		   }
		   
		   else if(parametro == 7) {
			   
			   long milisegundos = 1636627200000L; //para inicializar date
		       Date fecha = new Date(milisegundos);
		       
			   SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
	           
	           try {
	        	   fecha = formato.parse(modificar);
	           } catch (ParseException e) {
				e.printStackTrace();
	           		}
	           act.establecerFechaLimite(fecha);
		   }
		   
		   else if(parametro == 8) {
			   Boolean bol = Boolean.parseBoolean(modificar);
			   act.setObligatorio(bol);
		   }

		   
		   guardarActividades();
		   
		   return mapaActividades;
	   }
	   
	   
	//Guardar actividades
	   public Map<String, Actividad> guardarActividades(){
		   String nombreCSV = "datos/datosActividades.csv";
		   
		   try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreCSV))) {
	           for (String llave: mapaActividades.keySet()) {
	        	   ArrayList<String> lineaActividad = formatoActividad(mapaActividades.get(llave));
	        	   String line = String.join(";", lineaActividad);
	               writer.write(line);
	               writer.newLine();
	           }
	      
	           System.out.println("Se ha guardado exitosamente.");
	           
	       } catch (IOException e) {
	           e.printStackTrace();
	       }
		   
		   return mapaActividades;
	   }
	
	   public ArrayList<String> formatoActividad(Actividad a) {
		    ArrayList<String> rta = new ArrayList<>();

		    // Información general de la actividad
		    String nombre = a.getNombre();
		    String descripcion = a.getDescripcion();
		    String objetivo = a.getObjetivo();
		    String nivel = a.getNivelDificultad();
		    String duracion = String.valueOf(a.getDuracionEsperada());

		    // Formatear fecha
		    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		    String fecha = formato.format(a.getFechaLimite());

		    String obligatorio = String.valueOf(a.isObligatorio());
		    String creador = a.getCreador().getNombre();

		    // Prerrequisitos
		    String prerequisitos = String.join(", ", a.getPrerrequisitosNombres());

		    // Añadir información general al formato
		    rta.add("Nombre: " + nombre);
		    rta.add("Descripción: " + descripcion);
		    rta.add("Objetivo: " + objetivo);
		    rta.add("Nivel: " + nivel);
		    rta.add("Duración: " + duracion);
		    rta.add("Fecha límite: " + fecha);
		    rta.add("Obligatorio: " + obligatorio);
		    rta.add("Creador: " + creador);
		    rta.add("Prerrequisitos: " + prerequisitos);

		    // Respuestas de estudiantes
		    rta.add("Respuestas de estudiantes:");
		    Map<Estudiante, String> respuestas = a.getRespuesta();
		    for (Map.Entry<Estudiante, String> entry : respuestas.entrySet()) {
		        Estudiante estudiante = entry.getKey();
		        String respuesta = entry.getValue();
		        rta.add("Estudiante: " + estudiante.getNombre() + ", Respuesta: " + respuesta);
		    }

		    // Reseñas
		    rta.add("Reseñas:");
		    List<Reseña> reseñas = a.getReseñas();
		    for (Reseña reseña : reseñas) {
		        String rating = String.valueOf(reseña.getRating());
		        String comentario = reseña.getTexto() != null && !reseña.getTexto().isEmpty() ? reseña.getTexto() : "\"\"";
		        rta.add("Rating: " + rating + ", Comentario: " + comentario);
		    }

		    return rta;
		}
	
	//Se lee el archivo y se crea el mapa de actividades

	    public Map<String, Actividad> crearMapaActividades(Profesor profesor) {
	        String nombreCSV = "datos/datosActividades.csv";
	        Scanner scanner = new Scanner(System.in);

	        try (BufferedReader br = new BufferedReader(new FileReader(nombreCSV))) {
	            String line;

	            if ((line = br.readLine()) == null) {
	                System.out.println("No existen actividades. Debe primero crear una.");

	                boolean agregarMasActividades = true;
	                while (agregarMasActividades) {
	                    // Usar el método crearActividad del profesor
	                    Actividad nuevaActividad = profesor.crearActividad(scanner);
	                    if (nuevaActividad != null) {
	                        crearActividadData(nuevaActividad);
	                        System.out.println("Actividad añadida al mapa.");
	                    }

	                    System.out.print("¿Desea agregar otra actividad? (si/no): ");
	                    String continuar = scanner.nextLine().toLowerCase();
	                    if (!continuar.equals("si")) {
	                        agregarMasActividades = false;
	                    }
	                }
	            } else {
	                // Leer actividades del archivo CSV
	                while ((line = br.readLine()) != null) {
	                    String[] values = line.split(";");
	                    Scanner csvScanner = new Scanner(String.join("\n", values));

	                    // Delegar la creación de la actividad al profesor
	                    Actividad actividad = profesor.crearActividad(csvScanner);
	                    if (actividad != null) {
	                        crearActividadData(actividad);
	                    }
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        System.out.println("Las actividades se han cargado exitosamente.");
	        return mapaActividades;
	    }
		
	//FIN SECCION PARA ACTIVIDADES
		
		
	//SECCION PARA LEARNING PATHS
	    /**
	     * Crear o actualizar un LearningPath en el mapa y persistir los cambios.
	     */
	    public Map<String, LearningPath> crearPathData(LearningPath lp) {
	        mapaPaths.put(lp.getTitulo(), lp);
	        guardarPaths();
	        return mapaPaths;
	    }

	    /**
	     * Buscar un LearningPath por nombre.
	     */
	    public LearningPath buscarPath(String nombre) {
	        return mapaPaths.get(nombre);
	    }
	    
	    public ArrayList<String> mostrarPaths(){
	    	ArrayList<LearningPath> lps = (ArrayList<LearningPath>) mapaPaths.values();
	    	ArrayList<String> nombrespaths = new ArrayList<>();
	    	for (LearningPath lp: lps) {
	    		nombrespaths.add(lp.getTitulo());
	    	}
	    	
	    			
	    	return nombrespaths;
	    	}

	    /**
	     * Modificar un LearningPath según un parámetro específico.
	     */
	    public Map<String, LearningPath> modificarPath(int parametro, String modificar, String path) {
	        LearningPath lp = mapaPaths.get(path);
	        if (lp == null) {
	            System.out.println("Error: LearningPath no encontrado.");
	            return mapaPaths;
	        }

	        Date fechaHoy = new Date();
	        lp.setFechaModificacion(fechaHoy);

	        switch (parametro) {
	            case 1 -> lp.setTitulo(modificar);
	            case 2 -> lp.setDescripcion(modificar);
	            case 3 -> lp.setObjetivos(modificar);
	            case 4 -> {
	                String nivel = switch (modificar.toLowerCase()) {
	                    case "bajo" -> "Bajo";
	                    case "medio" -> "Medio";
	                    case "alto" -> "Alto";
	                    default -> throw new IllegalArgumentException("Nivel de dificultad no válido.");
	                };
	                lp.setNivelDificultad(nivel);
	            }
	            case 5 -> {
	                
	                lp.añadirTiempoLp(mapaActividades.get(modificar)); // Debes pasar una instancia de Actividad que modifique el tiempo.
	            }
	            case 6 -> {
	                List<Actividad> actividades = lp.getActividades();
	                Actividad actividad = mapaActividades.get(modificar);
	                if (actividad == null) {
	                    System.out.println("Error: Actividad no encontrada.");
	                    return mapaPaths;
	                }

	                System.out.println("Ingrese 1 para agregar la actividad o 2 para eliminarla:");
	                Scanner scanner = new Scanner(System.in);
	                int opcion = scanner.nextInt();
	                if (opcion == 1) actividades.add(actividad);
	                else if (opcion == 2) actividades.remove(actividad);
	                else System.out.println("Opción no válida.");
	                lp.setActividades(actividades);
	            }
	            default -> System.out.println("Parámetro no válido.");
	        }

	        guardarPaths();
	        return mapaPaths;
	    }

	    /**
	     * Formatear un LearningPath en una lista de cadenas para guardar en un archivo.
	     */
	    public ArrayList<String> formatoPath(LearningPath lp) {
	        ArrayList<String> rta = new ArrayList<>();
	        rta.add(lp.getTitulo());
	        rta.add(lp.getDescripcion());
	        rta.add(lp.getObjetivos());
	        rta.add(lp.getNivelDificultad());
	        rta.add(String.valueOf(lp.getDuracionEstimada()));
	        rta.add(new SimpleDateFormat("dd-MM-yyyy").format(lp.getFechaCreacion()));
	        rta.add(new SimpleDateFormat("dd-MM-yyyy").format(lp.getFechaModificacion()));
	        rta.add(lp.getCreador().getNombre());

	        // Formatear actividades asociadas
	        String actividades = String.join(",", lp.getActividades().stream().map(Actividad::getNombre).toList());
	        rta.add(actividades);

	        return rta;
	    }

	    /**
	     * Guardar el mapa de LearningPaths en un archivo CSV.
	     */
	    public Map<String, LearningPath> guardarPaths() {
	        String nombreCSV = "datos/datosPaths.csv";

	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreCSV))) {
	            for (String llave : mapaPaths.keySet()) {
	                ArrayList<String> lineaPath = formatoPath(mapaPaths.get(llave));
	                String line = String.join(";", lineaPath);
	                writer.write(line);
	                writer.newLine();
	            }
	            System.out.println("LearningPaths guardados exitosamente.");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return mapaPaths;
	    }
		
	    /**
	     * Leer un archivo CSV y crear el mapa de LearningPaths.
	     */
	    public Map<String, LearningPath> cargarLearningPaths() {
	        String nombreCSV = "datos/datosPaths.csv";

	        try (BufferedReader br = new BufferedReader(new FileReader(nombreCSV))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] values = line.split(";");
	                if (values.length < 8) {
	                    System.out.println("Error: Línea con formato incorrecto en el archivo de Learning Paths.");
	                    continue; // Skip this line and move to the next one
	                }

	                String titulo = values[0];
	                String descripcion = values[1];
	                String objetivos = values[2];
	                String nivelDificultad = values[3];
	                int duracionEstimada = Integer.parseInt(values[4]);
	                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
	                Date fechaCreacion = formatoFecha.parse(values[5]);
	                Date fechaModificacion = formatoFecha.parse(values[6]);

	                // Profesor creador
	                String nombreCreador = values[7];
	                Profesor creador = mapaProfesores.get(nombreCreador);
	                if (creador == null) {
	                    System.out.println("Advertencia: Profesor '" + nombreCreador + "' no encontrado.");
	                    continue;
	                }

	                // Crear el LearningPath
	                LearningPath lp = new LearningPath(titulo, descripcion, objetivos, nivelDificultad, creador, duracionEstimada);
	                lp.setFechaCreacion(fechaCreacion);
	                lp.setFechaModificacion(fechaModificacion);

	                // Procesar actividades (if present)
	                if (values.length > 8) {
	                    List<Actividad> actividades = new ArrayList<>();
	                    String[] nombresActividades = values[8].split(",");
	                    for (String nombreActividad : nombresActividades) {
	                        Actividad actividad = cargarActividad(nombreActividad.trim());
	                        if (actividad != null) {
	                            actividades.add(actividad);
	                        } else {
	                            System.out.println("Advertencia: Actividad '" + nombreActividad + "' no encontrada.");
	                        }
	                    }
	                    lp.setActividades(actividades);
	                }

	                mapaPaths.put(lp.getTitulo(), lp);
	            }
	        } catch (IOException | ParseException e) {
	            e.printStackTrace();
	        }

	        System.out.println("LearningPaths cargados exitosamente.");
	        return mapaPaths;
	    }


	    /**
	     * Cargar una actividad desde el mapa de actividades.
	     */
	    private Actividad cargarActividad(String nombreActividad) {
	        return mapaActividades.getOrDefault(nombreActividad, null);
	    }
	
	
	//FIN SECCION PARA LEARNING PATHS
	    

		
	//SECCION PARA ESTUDIANTES
	 // SECCION ESTUDIANTES

	    public Map<String, Estudiante> crearEstudianteData(String nombre, String correo, String contrasena) {
	        Estudiante estu = new Estudiante(nombre, correo, contrasena);
	        mapaEstudiantes.put(estu.getCorreo(), estu); // Use correo as the key
	        guardarEstudiante();
	        return mapaEstudiantes;
	    }

	    // Buscar un estudiante por correo
	    public Estudiante buscarEstu(String correo) {
	        return mapaEstudiantes.get(correo); // Use correo as the key
	    }

	    // Modificar un estudiante por correo
	    public Map<String, Estudiante> modificarEstudiante(String modificar, String correo) {
	        Estudiante estu = mapaEstudiantes.get(correo); // Use correo as the key
	        if (estu != null) {
	            estu.setContrasena(modificar);
	            guardarEstudiante();
	        } else {
	            System.out.println("Error: Estudiante con el correo '" + correo + "' no encontrado.");
	        }
	        return mapaEstudiantes;
	    }

	    // Formato de estudiante para guardar
	    public ArrayList<String> formatoEstudiante(Estudiante estu) {
	        ArrayList<String> rta = new ArrayList<>();

	        String nombre = estu.getNombre();
	        String email = estu.getCorreo();
	        String clave = estu.getContrasena();

	        String progresoPaths = "";
	        String progresoActividades = "";
	        String realizadas = "";

	        // Convert progresoPaths to String
	        Collection<ProgresoPath> progPathsCollection = estu.getProgresoPaths().values();
	        for (ProgresoPath progPath : progPathsCollection) {
	            String nombrePath = progPath.getLp().getTitulo();
	            progresoPaths += "," + nombrePath;
	        }

	        // Convert progresoAct to String
	        Collection<ProgresoActividad> progresoActCollection = estu.getProgresosAct().values();
	        for (ProgresoActividad progresoAct : progresoActCollection) {
	            String nombreAct = progresoAct.getActividad().getNombre();
	            progresoActividades += "," + nombreAct;
	        }

	        // Process actividades realizadas
	        List<Actividad> realizadasList = estu.getRealizadas();
	        for (Actividad actRea : realizadasList) {
	            String nombreAct = actRea.getNombre();
	            realizadas += "," + nombreAct;
	        }

	        // Add all fields to the return list
	        rta.add(nombre);
	        rta.add(email);
	        rta.add(clave);
	        rta.add(progresoPaths.isEmpty() ? "" : progresoPaths.substring(1)); // Remove leading comma
	        rta.add(progresoActividades.isEmpty() ? "" : progresoActividades.substring(1)); // Remove leading comma
	        rta.add(realizadas.isEmpty() ? "" : realizadas.substring(1)); // Remove leading comma

	        return rta;
	    }

	    // Guardar estudiantes
	    public Map<String, Estudiante> guardarEstudiante() {
	        String nombreCSV = "datos/datosEstudiantes.csv";

	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreCSV))) {
	            for (Estudiante estudiante : mapaEstudiantes.values()) {
	                ArrayList<String> lineaEstudiante = formatoEstudiante(estudiante);
	                String line = String.join(";", lineaEstudiante);
	                writer.write(line);
	                writer.newLine();
	            }

	            System.out.println("Estudiantes guardados exitosamente.");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return mapaEstudiantes;
	    }

	    // Cargar estudiantes desde el archivo CSV
	    public Map<String, Estudiante> cargarEstudiantes() {
	        String nombreCSV = "datos/datosEstudiantes.csv";

	        try {
	            // Check if the file exists, create it if not
	            java.io.File file = new java.io.File(nombreCSV);
	            if (!file.exists()) {
	                System.out.println("Archivo 'datosEstudiantes.csv' no encontrado. Creando archivo vacío...");
	                file.getParentFile().mkdirs(); // Create directories if they don't exist
	                file.createNewFile(); // Create the file
	                return mapaEstudiantes; // Return empty map if no data exists
	            }

	            try (BufferedReader br = new BufferedReader(new FileReader(nombreCSV))) {
	                String line;
	                while ((line = br.readLine()) != null) {
	                    String[] values = line.split(";");
	                    if (values.length < 6) { 
	                        // Skip lines that don't have enough data
	                        System.out.println("Línea malformada, omitiendo: " + line);
	                        continue;
	                    }

	                    String nombre = values[0];
	                    String email = values[1];
	                    String clave = values[2];

	                    // Crear el estudiante
	                    Estudiante estudiante = new Estudiante(nombre, email, clave);

	                    // Cargar progresoAct (Mapa <Actividad, ProgresoActividad>)
	                    Map<Actividad, ProgresoActividad> progresoAct = new HashMap<>();
	                    if (!values[3].isEmpty()) {
	                        String[] progresoActData = values[3].split(",");
	                        for (String act : progresoActData) {
	                            Actividad actividad = mapaActividades.get(act.trim());
	                            if (actividad != null) {
	                                String llave = email + "_" + actividad.getNombre(); // Use email as part of the key
	                                ProgresoActividad progreso = mapaProgresoActividad.get(llave);
	                                progresoAct.put(actividad, progreso);
	                            } else {
	                                System.out.println("Advertencia: Actividad '" + act + "' no encontrada para el estudiante " + email);
	                            }
	                        }
	                    }
	                    estudiante.setProgresoAct(progresoAct);

	                    // Cargar progresoPaths (Mapa <LearningPath, ProgresoPath>)
	                    Map<LearningPath, ProgresoPath> progresoPaths = new HashMap<>();
	                    List<LearningPath> lp = new ArrayList<>();
	                    if (!values[4].isEmpty()) {
	                        String[] progresoPathsData = values[4].split(",");
	                        for (String path : progresoPathsData) {
	                            LearningPath learningPath = mapaPaths.get(path.trim());
	                            if (learningPath != null) {
	                                String llaveLP = email + "_" + learningPath.getTitulo(); // Use email as part of the key
	                                ProgresoPath progreso = mapaProgresoPath.get(llaveLP);
	                                progresoPaths.put(learningPath, progreso);
	                                lp.add(learningPath);
	                            } else {
	                                System.out.println("Advertencia: LearningPath '" + path + "' no encontrado para el estudiante " + email);
	                            }
	                        }
	                    }
	                    estudiante.setLP(lp);
	                    estudiante.setProgresoPaths(progresoPaths);

	                    // Cargar actividades realizadas
	                    List<Actividad> realizadas = new ArrayList<>();
	                    if (!values[5].isEmpty()) {
	                        String[] actividadesRealizadasData = values[5].split(",");
	                        for (String act : actividadesRealizadasData) {
	                            Actividad actividad = mapaActividades.get(act.trim());
	                            if (actividad != null) {
	                                realizadas.add(actividad);
	                            } else {
	                                System.out.println("Advertencia: Actividad realizada '" + act + "' no encontrada para el estudiante " + email);
	                            }
	                        }
	                    }
	                    estudiante.setRealizadas(realizadas);

	                    // Añadir estudiante al mapa usando el correo como clave
	                    mapaEstudiantes.put(estudiante.getCorreo(), estudiante);
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        System.out.println("Estudiantes cargados exitosamente.");
	        return mapaEstudiantes;
	    }

	    
	 
	    
	    
	 //FIN SECCION ESTUDIANTES   
	    
	    
	
	
	
	

		//SECCION PROFESORES
	    
	    
	 // SECCION PROFESORES

	    public Map<String, Profesor> crearProfesorData(String nombre, String correo, String contrasena) {
	        Profesor profe = new Profesor(nombre, correo, contrasena);
	        mapaProfesores.put(profe.getCorreo(), profe); // Use correo as the key
	        guardarProfesor();
	        return mapaProfesores;
	    }

	    // Buscar un profesor por correo
	    public Profesor buscarProfe(String correo) {
	        return mapaProfesores.get(correo); // Use correo as the key
	    }

	    // Modificar un profesor por correo
	    public Map<String, Profesor> modificarProfe(String modificar, String correo) {
	        Profesor profe = mapaProfesores.get(correo); // Use correo as the key
	        if (profe != null) {
	            profe.setContrasena(modificar);
	            guardarProfesor();
	        } else {
	            System.out.println("Error: Profesor con el correo '" + correo + "' no encontrado.");
	        }
	        return mapaProfesores;
	    }

	    // Formato de profesor para guardar
	    public ArrayList<String> formatoProfesor(Profesor prof) {
	        ArrayList<String> rta = new ArrayList<>();

	        String nombre = prof.getNombre();
	        String email = prof.getCorreo();
	        String clave = prof.getContrasena();

	        String lpCreados = "";
	        List<LearningPath> paths = prof.getLearningPathsCreados();

	        for (LearningPath lp : paths) {
	            lpCreados += "," + lp.getTitulo();
	        }

	        rta.add(nombre);
	        rta.add(email);
	        rta.add(clave);
	        rta.add(lpCreados.isEmpty() ? "" : lpCreados.substring(1)); // Remove leading comma if present

	        return rta;
	    }

	    // Guardar los profesores
	    public Map<String, Profesor> guardarProfesor() {
	        String nombreCSV = "datos/datosProfesores.csv";

	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreCSV))) {
	            for (Profesor profesor : mapaProfesores.values()) {
	                ArrayList<String> lineaProfesor = formatoProfesor(profesor);
	                String line = String.join(";", lineaProfesor);
	                writer.write(line);
	                writer.newLine();
	            }

	            System.out.println("Profesores guardados exitosamente.");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return mapaProfesores;
	    }

	    // Cargar profesores desde el archivo CSV
	    public Map<String, Profesor> cargarProfesores() {
	        String nombreCSV = "datos/datosUsuariosProfesores.csv";

	        try {
	            // Check if the file exists, create it if not
	            java.io.File file = new java.io.File(nombreCSV);
	            if (!file.exists()) {
	                System.out.println("Archivo 'datosUsuariosProfesores.csv' no encontrado. Creando archivo vacío...");
	                file.getParentFile().mkdirs(); // Create directories if they don't exist
	                file.createNewFile(); // Create the file
	                return mapaProfesores; // Return empty map if no data exists
	            }

	            try (BufferedReader br = new BufferedReader(new FileReader(nombreCSV))) {
	                String line;
	                while ((line = br.readLine()) != null) {
	                    String[] values = line.split(";");
	                    if (values.length < 4) {
	                        // Skip lines that don't have enough data
	                        System.out.println("Línea malformada, omitiendo: " + line);
	                        continue;
	                    }

	                    String nombre = values[0];
	                    String email = values[1];
	                    String clave = values[2];
	                    String lpCreados = values[3];

	                    // Crear profesor
	                    Profesor profesor = new Profesor(nombre, email, clave);

	                    // Asociar LearningPaths creados
	                    List<LearningPath> listaLPCreados = new ArrayList<>();
	                    if (!lpCreados.isEmpty()) {
	                        String[] titulosLP = lpCreados.split(",");
	                        for (String titulo : titulosLP) {
	                            LearningPath lp = mapaPaths.get(titulo.trim());
	                            if (lp != null) {
	                                listaLPCreados.add(lp);
	                            } else {
	                                System.out.println("Advertencia: LearningPath '" + titulo + "' no encontrado para el profesor " + email);
	                            }
	                        }
	                    }
	                    profesor.setLearningPathsCreados(listaLPCreados);

	                    // Añadir profesor al mapa usando el correo como clave
	                    mapaProfesores.put(profesor.getCorreo(), profesor);
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        System.out.println("Profesores cargados exitosamente.");
	        return mapaProfesores;
	    }

				   

	//FIN SECCION PARA PROFESORES
		    
		    
		    
 // SECCION PROGRESOPATH
	 // SECCION PROGRESOPATH

	    public void crearProgresoPathData(ProgresoPath pPath) {
	        // Generate the key using correoEstudiante + "_" + lpNombre
	        String key = pPath.getEstudiante().getCorreo() + "_" + pPath.getLp().getTitulo();
	        mapaProgresoPath.put(key, pPath);
	        guardarProgresoPath();
	    }
	    // Buscar un progresoPath por clave
	    public ProgresoPath buscarProgPath(String clave) {
	        return mapaProgresoPath.get(clave); // Use correo as the key
	    }

	    public ArrayList<String> formatoProgresoPath(ProgresoPath progreso) {
	        ArrayList<String> rta = new ArrayList<>();

	        String nombreLP = progreso.getLp().getTitulo();
	        String fechaInicio = new SimpleDateFormat("dd-MM-yyyy").format(progreso.getFechaInicioPath());
	        String fechaFin = progreso.getFechaFinPath() != null
	                ? new SimpleDateFormat("dd-MM-yyyy").format(progreso.getFechaFinPath())
	                : "N/A"; // If fechaFin is null, use "N/A"

	        String porcentaje = String.valueOf(progreso.getPorcentajePath());
	        String tasaExito = String.valueOf(progreso.getTasaExito());
	        String tasaFracaso = String.valueOf(progreso.getTasaFracaso());

	        // Convert list of completed activities to a comma-separated string
	        String actividadesRealizadas = progreso.getActividadesRealizadas() != null
	                ? String.join(",", progreso.getActividadesRealizadas().stream().map(Actividad::getNombre).toArray(String[]::new))
	                : "";

	        String completado = String.valueOf(progreso.isCompletado());
	        String correoEstudiante = progreso.getEstudiante().getCorreo(); // Use correo instead of nombre

	        rta.add(nombreLP);
	        rta.add(fechaInicio);
	        rta.add(fechaFin);
	        rta.add(porcentaje);
	        rta.add(tasaExito);
	        rta.add(tasaFracaso);
	        rta.add(actividadesRealizadas);
	        rta.add(completado);
	        rta.add(correoEstudiante); // Store correoEstudiante

	        return rta;
	    }

	    public Map<String, ProgresoPath> guardarProgresoPath() {
	        String nombreCSV = "datos/datosProgresoPath.csv";

	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreCSV))) {
	            for (ProgresoPath progreso : mapaProgresoPath.values()) {
	                ArrayList<String> lineaProgreso = formatoProgresoPath(progreso);
	                String line = String.join(";", lineaProgreso);
	                writer.write(line);
	                writer.newLine();
	            }

	            System.out.println("Progresos de LearningPaths guardados exitosamente.");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return mapaProgresoPath;
	    }

	    public Map<String, ProgresoPath> cargarProgresoPaths() {
	        String nombreCSV = "datos/datosProgresoPaths.csv";

	        try (BufferedReader br = new BufferedReader(new FileReader(nombreCSV))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] values = line.split(";");

	                // Extract ProgresoPath attributes
	                String nombreLP = values[0];
	                Date fechaInicio = new SimpleDateFormat("dd-MM-yyyy").parse(values[1]);
	                Date fechaFin = values[2].equals("N/A") ? null : new SimpleDateFormat("dd-MM-yyyy").parse(values[2]);
	                float porcentaje = Float.parseFloat(values[3]);
	                float tasaExito = Float.parseFloat(values[4]);
	                float tasaFracaso = Float.parseFloat(values[5]);

	                // Load completed activities
	                String[] actividadesRealizadasData = values[6].split(",");
	                List<Actividad> actividadesRealizadas = new ArrayList<>();
	                for (String act : actividadesRealizadasData) {
	                    Actividad actividad = mapaActividades.get(act.trim());
	                    if (actividad != null) {
	                        actividadesRealizadas.add(actividad);
	                    } else {
	                        System.out.println("Advertencia: Actividad '" + act + "' no encontrada para ProgresoPath " + nombreLP);
	                    }
	                }

	                boolean completado = Boolean.parseBoolean(values[7]);
	                String correoEstudiante = values[8];
	                Estudiante estudiante = mapaEstudiantes.get(correoEstudiante); // Use correo as the key

	                if (estudiante == null) {
	                    System.out.println("Advertencia: Estudiante con correo '" + correoEstudiante + "' no encontrado.");
	                    continue;
	                }

	                LearningPath lp = mapaPaths.get(nombreLP);
	                if (lp == null) {
	                    System.out.println("Advertencia: LearningPath '" + nombreLP + "' no encontrado.");
	                    continue;
	                }

	                // Create the ProgresoPath with basic attributes
	                ProgresoPath progreso = new ProgresoPath(lp, fechaInicio, estudiante);

	                // Set additional attributes
	                progreso.setFechaFinPath(fechaFin);
	                progreso.setPorcentajePath(porcentaje);
	                progreso.setTasaExito(tasaExito);
	                progreso.setTasaFracaso(tasaFracaso);
	                progreso.setActividadesRealizadas(actividadesRealizadas);
	                progreso.setCompletado(completado);

	                // Generate the unique key
	                String clave = correoEstudiante + "_" + lp.getTitulo();
	                mapaProgresoPath.put(clave, progreso);
	            }
	        } catch (IOException | ParseException e) {
	            e.printStackTrace();
	        }

	        System.out.println("Progresos de LearningPaths cargados exitosamente.");
	        return mapaProgresoPath;
	    }


		    //FIN SECCION PROGRESOPATH
		    
		    
		    
		    //INICIO SECCION PROGRESOACTIVIDAD
	    public ArrayList<String> formatoProgresoActividad(ProgresoActividad progreso) {
	        ArrayList<String> rta = new ArrayList<>();

	        String nombreActividad = progreso.getActividad().getNombre();
	        String completada = String.valueOf(progreso.isCompletada());
	        String resultado = progreso.getResultado();
	        String tiempoDedicado = String.valueOf(progreso.getTiempoDedicado());

	        String fechaInicio = progreso.getFechaInicio() != null
	                ? new SimpleDateFormat("dd-MM-yyyy").format(progreso.getFechaInicio())
	                : "N/A";
	        String fechaFin = progreso.getFechaFin() != null
	                ? new SimpleDateFormat("dd-MM-yyyy").format(progreso.getFechaFin())
	                : "N/A";

	        String nombreEstudiante = progreso.getEstudiante().getNombre();

	        rta.add(nombreActividad);
	        rta.add(completada);
	        rta.add(resultado);
	        rta.add(tiempoDedicado);
	        rta.add(fechaInicio);
	        rta.add(fechaFin);
	        rta.add(nombreEstudiante);

	        return rta;
	    }
	    public Map<String, ProgresoActividad> guardarProgresoActividad() {
	        String nombreCSV = "datos/datosProgresoActividades.csv";

	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreCSV))) {
	            for (ProgresoActividad progreso : mapaProgresoActividad.values()) {
	                ArrayList<String> lineaProgreso = formatoProgresoActividad(progreso);
	                String line = String.join(";", lineaProgreso);
	                writer.write(line);
	                writer.newLine();
	            }

	            System.out.println("Progresos de Actividades guardados exitosamente.");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return mapaProgresoActividad;
	    }
	    public Map<String, ProgresoActividad> cargarProgresoActividades() {
	        String nombreCSV = "datos/datosProgresoActividades.csv";

	        try (BufferedReader br = new BufferedReader(new FileReader(nombreCSV))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] values = line.split(";");

	                // Extraer atributos de ProgresoActividad
	                String nombreActividad = values[0];
	                boolean completada = Boolean.parseBoolean(values[1]);
	                String resultado = values[2];
	                long tiempoDedicado = Long.parseLong(values[3]);

	                Date fechaInicio = values[4].equals("N/A") ? null : new SimpleDateFormat("dd-MM-yyyy").parse(values[4]);
	                Date fechaFin = values[5].equals("N/A") ? null : new SimpleDateFormat("dd-MM-yyyy").parse(values[5]);

	                String nombreEstudiante = values[6];
	                Estudiante estudiante = mapaEstudiantes.get(nombreEstudiante);

	                if (estudiante == null) {
	                    System.out.println("Advertencia: Estudiante '" + nombreEstudiante + "' no encontrado.");
	                    continue;
	                }

	                Actividad actividad = mapaActividades.get(nombreActividad);
	                if (actividad == null) {
	                    System.out.println("Advertencia: Actividad '" + nombreActividad + "' no encontrada.");
	                    continue;
	                }

	                // Crear el ProgresoActividad y establecer atributos
	                ProgresoActividad progreso = new ProgresoActividad(actividad, estudiante);
	                progreso.setCompletado(completada);
	                progreso.setResultado(resultado);
	                progreso.setTiempoDedicado(tiempoDedicado);
	                progreso.setFechaInicio(fechaInicio);
	                progreso.setFechaFin(fechaFin);

	                // Generar la clave única
	                String clave = estudiante.getNombre() + "_" + actividad.getNombre();
	                mapaProgresoActividad.put(clave, progreso);
	            }
	        } catch (IOException | ParseException e) {
	            e.printStackTrace();
	        }

	        System.out.println("Progresos de Actividades cargados exitosamente.");
	        return mapaProgresoActividad;
	    }

			public Map<String, LearningPath> getMapaPaths() {
				// TODO Auto-generated method stub
				return mapaPaths;
			}

			



		
		    
	}
	