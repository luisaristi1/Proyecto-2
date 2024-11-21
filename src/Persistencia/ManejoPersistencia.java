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
		   String nombreCSV = "data/datosActividades.csv";
		   
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
	        String nombreCSV = "data/datosActividades.csv";
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
	        String nombreCSV = "data/datosPaths.csv";

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
	    public Map<String, LearningPath> crearMapaPaths() {
	        String nombreCSV = "data/datosPaths.csv";

	        try (BufferedReader br = new BufferedReader(new FileReader(nombreCSV))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] values = line.split(";");
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

	                // Crear el LearningPath
	                LearningPath lp = new LearningPath(titulo, descripcion, objetivos, nivelDificultad, creador, duracionEstimada);
	                lp.setFechaCreacion(fechaCreacion);
	                lp.setFechaModificacion(fechaModificacion);

	                // Procesar actividades
	                List<Actividad> actividades = new ArrayList<>();
	                String[] nombresActividades = values[8].split(",");
	                for (String nombreActividad : nombresActividades) {
	                    Actividad actividad = cargarActividad(nombreActividad);
	                    if (actividad != null) actividades.add(actividad);
	                }
	                lp.setActividades(actividades);

	                // Procesar reseñas
	               

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
	    public Map<String, Estudiante> crearEstudianteData(String nombre, String correo, String contrasena){
	    	Estudiante estu = new Estudiante(nombre, correo, contrasena);
			mapaEstudiantes.put(estu.getNombre(), estu);
			guardarEstudiante();
			return mapaEstudiantes;
	    }
	    
	    public Estudiante buscarEstu(String nombre) {
			   Estudiante estu = mapaEstudiantes.get(nombre);
			   return estu;
		   }
	    
	    public Map<String, Estudiante> modificarEstudiante( String modificar, String nombreuser){
			   Estudiante estu =  mapaEstudiantes.get(nombreuser);
			   estu.setContrasena(modificar);
			   
			   guardarEstudiante();
			   
			   return mapaEstudiantes;
	}
	    public ArrayList<String> formatoEstudiante(Estudiante estu){
	    	
			   ArrayList<String> rta = new ArrayList<>(); 

			   
			   String nombre = estu.getNombre();
			   String email = estu.getCorreo();
			   String clave = estu.getContrasena();
			   
			   String progresoPaths = "";
			   String progresoActividades = "";
			   String realizadas= "";
			   
			   HashMap<LearningPath, ProgresoPath> control = (HashMap<LearningPath, ProgresoPath>) estu.getProgresoPaths();
			   ArrayList<ProgresoPath> progPath = (ArrayList<ProgresoPath>) control.values();	
			   
			   
			   
			   for (ProgresoPath c: progPath) {
				   String nombrePath = c.getLp().getTitulo();
				   progresoPaths = progresoPaths + "," + nombrePath;}

			       
			   ArrayList<ProgresoActividad> progresoAct = (ArrayList<ProgresoActividad>) estu.getProgresosAct().values();
			       
			   for (ProgresoActividad ca: progresoAct) {
			    	String nombreAct = ca.getActividad().getNombre();
					progresoActividades = progresoActividades + "," + nombreAct;
			    }
			   
			   ArrayList<Actividad> realizada = (ArrayList<Actividad>) estu.getRealizadas();
			   
			   for (Actividad actRea: realizada) {
				   String nombreAct = actRea.getNombre();
				   realizadas = realizadas + "," + nombreAct;
	
				   
			   }
				   
			   
			   
			   rta.add(nombre);
			   rta.add(email);
			   rta.add(clave);
			   rta.add(progresoPaths);
			   rta.add(progresoActividades);
			   rta.add(realizadas);

			   return rta;

		   }

	    public Map<String, Estudiante> guardarEstudiante() {
			// TODO Auto-generated method stub
				String nombreCSV = "data/datosProfesores.csv";
				   
				   try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreCSV))) {
			           for (String llave: mapaEstudiantes.keySet()) {
			        	   ArrayList<String> lineaPath = formatoEstudiante(mapaEstudiantes.get(llave));
			        	   String line = String.join(";", lineaPath);
			               writer.write(line);
			               writer.newLine();
			           }
			      
			           System.out.println("Se ha guardado exitosamente.");
			           
			       } catch (IOException e) {
			           e.printStackTrace();
			       }
				   
				   return mapaEstudiantes;
		}
	    
	    
	    public Map<String, Estudiante> cargarEstudiantes() {
	        String nombreCSV = "data/datosEstudiantes.csv";

	        try (BufferedReader br = new BufferedReader(new FileReader(nombreCSV))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] values = line.split(";");
	                String nombre = values[0];
	                String email = values[1];
	                String clave = values[2];

	                // Crear el estudiante
	                Estudiante estudiante = new Estudiante(nombre, email, clave);

	                // Cargar progresoAct (Mapa <Actividad, ProgresoActividad>)
	                String[] progresoActData = values[3].split(",");
	                Map<Actividad, ProgresoActividad> progresoAct = new HashMap<>();
	                for (String act : progresoActData) {
	                    Actividad actividad = mapaActividades.get(act.trim());
	                    if (actividad != null) {
	                        ProgresoActividad progreso = new ProgresoActividad(actividad, estudiante); // Suponiendo que el constructor usa Actividad
	                        progresoAct.put(actividad, progreso);
	                    } else {
	                        System.out.println("Advertencia: Actividad '" + act + "' no encontrada para el estudiante " + nombre);
	                    }
	                }
	                estudiante.setProgresoAct(progresoAct);

	                // Cargar progresoPaths (Mapa <LearningPath, ProgresoPath>)
	                String[] progresoPathsData = values[4].split(",");
	                Map<LearningPath, ProgresoPath> progresoPaths = new HashMap<>();
	                List<LearningPath> lp = new ArrayList<>();
	                
	                for (String path : progresoPathsData) {
	                    LearningPath learningPath = mapaPaths.get(path.trim());
	                    if (learningPath != null) {
	                        ProgresoPath progreso = new ProgresoPath(learningPath, null, estudiante); // Suponiendo que el constructor usa LearningPath
	                        progresoPaths.put(learningPath, progreso);
	                        lp.add(learningPath);
	                    } else {
	                        System.out.println("Advertencia: LearningPath '" + path + "' no encontrado para el estudiante " + nombre);
	                    }
	                }
	                estudiante.setLP(lp);
	                estudiante.setProgresoPaths(progresoPaths);

	                // Cargar actividades realizadas
	                String[] actividadesRealizadasData = values[5].split(",");
	                List<Actividad> realizadas = new ArrayList<>();
	                for (String act : actividadesRealizadasData) {
	                    Actividad actividad = mapaActividades.get(act.trim());
	                    if (actividad != null) {
	                        realizadas.add(actividad);
	                    } else {
	                        System.out.println("Advertencia: Actividad realizada '" + act + "' no encontrada para el estudiante " + nombre);
	                    }
	                }
	                estudiante.setRealizadas(realizadas);


	                // Añadir estudiante al mapa
	                mapaEstudiantes.put(estudiante.getNombre(), estudiante);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        System.out.println("Estudiantes cargados exitosamente.");
	        return mapaEstudiantes;
	    }
	    
	    
	    
	 
	    
	    
	 //FIN SECCION ESTUDIANTES   
	    
	    
	
	
	
	

		//SECCION PROFESORES
	    
	    
	    public Map<String, Profesor> crearProfesorData(String nombre, String correo, String contrasena){
	    	Profesor profe = new Profesor(nombre, correo, contrasena);
			mapaProfesores.put(profe.getNombre(), profe);
			
			guardarProfesor();
			
			return mapaProfesores;
	    }
	    
	  //Buscar un usuario
	    public Profesor buscarProfe(String nombre) {
			   Profesor profe = mapaProfesores.get(nombre);
			   return profe;
		   }
	    
	    public Map<String, Profesor> modificarProfe( String modificar, String nombreuser){
				   Profesor profe = (Profesor) mapaProfesores.get(nombreuser);
				   profe.setContrasena(modificar);
				   
				   guardarProfesor();
				   
				   return mapaProfesores;
		}
	    public ArrayList<String> formatoProfesor(Profesor prof){
	    	
			   ArrayList<String> rta = new ArrayList<>(); 

			   
			   String nombre = prof.getNombre();
			   String email = prof.getCorreo();
			   String clave = prof.getContrasena();
			   
			   String lpCreados = "";
			   
			   ArrayList<LearningPath> path = (ArrayList<LearningPath>) prof.getLearningPathsCreados();	
			   
			   
			   
			   for (LearningPath c: path) {
				   String nombrePath = c.getTitulo();
				   lpCreados = lpCreados + ", " + nombrePath;

			      }
			   
			   rta.add(nombre);
			   rta.add(email);
			   rta.add(clave);
			   rta.add(lpCreados);
			   return rta;
			   
			   
			   
			   }
	    

	
			
			public Map<String, Profesor> guardarProfesor() {
			// TODO Auto-generated method stub
				String nombreCSV = "data/datosProfesores.csv";
				   
				   try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreCSV))) {
			           for (String llave: mapaProfesores.keySet()) {
			        	   ArrayList<String> lineaPath = formatoProfesor(mapaProfesores.get(llave));
			        	   String line = String.join(";", lineaPath);
			               writer.write(line);
			               writer.newLine();
			           }
			      
			           System.out.println("Se ha guardado exitosamente.");
			           
			       } catch (IOException e) {
			           e.printStackTrace();
			       }
				   
				   return mapaProfesores;
		}

			/**
		     * Cargar Profesores desde el archivo CSV.
		     */
		    public Map<String, Profesor> cargarProfesores() {
		        String nombreCSV = "data/datosUsuariosProfesores.csv";

		        try (BufferedReader br = new BufferedReader(new FileReader(nombreCSV))) {
		            String line;
		            while ((line = br.readLine()) != null) {
		                String[] values = line.split(";");
		                String nombre = values[0];
		                String email = values[1];
		                String clave = values[2];
		                String lpCreados = values[3];
		                // Crear profesor y añadir al mapa
		                Profesor profesor = new Profesor(nombre, email, clave);
		             // Asociar LearningPaths creados
		                String[] titulosLP = lpCreados.split(",");
		                List<LearningPath> listaLPCreados = new ArrayList<>();
		                for (String titulo : titulosLP) {
		                    LearningPath lp = mapaPaths.get(titulo.trim());
		                    if (lp != null) {
		                        listaLPCreados.add(lp);
		                    } else {
		                        System.out.println("Advertencia: LearningPath '" + titulo + "' no encontrado para el profesor " + nombre);
		                    }
		                }
		                profesor.setLearningPathsCreados(listaLPCreados);

		                // Añadir profesor al mapa
		                mapaProfesores.put(profesor.getNombre(), profesor);
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		        }

		        System.out.println("Profesores cargados exitosamente.");
		        return mapaProfesores;
		    }

			
			 //formato en string de cada usuario
				
				
				
				//Guardar usuarios
				   
			
  
				   

	//FIN SECCION PARA USUARIOS
	}
	