package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.ListCell;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ListView;

public class ControladorVentanaPrincipal {

	private IControlador controladorMVC;
	
	private ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
	private ObservableList<Prestamo> prestamospendientes = FXCollections.observableArrayList();
	private ObservableList<Prestamo> prestamosdevueltos = FXCollections.observableArrayList();
	private ObservableList<Prestamo> prestamos = FXCollections.observableArrayList();
	private ObservableList<Libro> libros = FXCollections.observableArrayList();
	
	@FXML private TableView<Prestamo> tvPrestamosEnCurso;
    @FXML private TableColumn<Prestamo, String> tcPCtipolibro;
    @FXML private TableColumn<Prestamo, String> tcPCtitulolibro;
    @FXML private TableColumn<Prestamo, String> tcPCalumno;
    @FXML private TableColumn<Prestamo, String> tcPCfechaprestamo;

    @FXML private TableView<Prestamo> tvPrestamosDevueltos;
    @FXML private TableColumn<Prestamo, String> tcPDtipolibro;
    @FXML private TableColumn<Prestamo, String> tcPDtitulolibro;
    @FXML private TableColumn<Prestamo, String> tcPDalumno;
    @FXML private TableColumn<Prestamo, String> tcPDfechaprestamo;
    @FXML private TableColumn<Prestamo, String> tcPDfechadevolucion;

    @FXML private TableView<Libro> tvLibros;
    @FXML private TableColumn<Libro, String> tcTLtipolibro;
    @FXML private TableColumn<Libro, String> tcTLtitulo;
    @FXML private TableColumn<Libro, String> tcTLautor;
    @FXML private TableColumn<Libro, String> tcTLpaginas;
    @FXML private TableColumn<Libro, String> tcTLduracion;
    @FXML private TableColumn<Libro, Float> tcTLpuntos;

    @FXML private TableView<Alumno> tvAlumnos;
    @FXML private TableColumn<Alumno, String> tcTAnombre;
    @FXML private TableColumn<Alumno, String> tcTAcorreo;
    @FXML private TableColumn<Alumno, Curso> tcTAcurso;

	@FXML private DatePicker dpEstadistica;
	@FXML private TextField tfPrimero;
	@FXML private TextField tfSegundo;
	@FXML private TextField tfTercero;
	@FXML private TextField tfCuarto;
	
	@FXML    private RadioButton rbAlumno;
    @FXML    private RadioButton rbLibro;
    @FXML    private RadioButton rbFecha;
    @FXML    private DatePicker dpFechaBuscar;
    
    @FXML    private ListView<Alumno> lvAlumnos;
    
    @FXML    private TableView<Libro> tvLibrosBusqueda;
    @FXML    private TableColumn<Libro, String> tcAutorBusqueda;
    @FXML    private TableColumn<Libro, String> tcTituloBusqueda;
    
    @FXML    private TableView<Prestamo> tvPrestamosBusqueda;
    @FXML    private TableColumn<Prestamo, String> tcTPtipo;
    @FXML    private TableColumn<Prestamo, String> tcTPtitulo;
    @FXML    private TableColumn<Prestamo, String> tcTPalumno;
    @FXML    private TableColumn<Prestamo, String> tcTPfechaPrestamo;
    @FXML    private TableColumn<Prestamo, String> tcTPfechaDevolucion;

	private ToggleGroup tgEleccion = new ToggleGroup();

    private Stage anadirAlumno;
    private ControladorAnadirAlumno cAnadirAlumno;
    private Stage anadirLibro;
    private ControladorAnadirLibro cAnadirLibro;
    private Stage anadirPrestamo;
    private ControladorAnadirPrestamo cAnadirPrestamo;
    private Stage insertarFechaDevolucion;
    private ControladorFechaDevolucion cFechaDevolucion;
    
	private class CeldaAlumno extends ListCell<Alumno> {
    	@Override
    	public void updateItem(Alumno alumno, boolean vacio) {
    		super.updateItem(alumno, vacio);
    		if (vacio) {
    			setText("");
    		} else {
    			setText(alumno.getCorreo());
    		}
    	}
    }

    @FXML
    private void initialize() {
    	
    	tcPCtipolibro.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getLibro().getClass().getSimpleName()));
    	tcPCtitulolibro.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getLibro().getTitulo()));
    	tcPCalumno.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getAlumno().getCorreo()));
    	tcPCfechaprestamo.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getFechaPrestamo().toString()));
    	tvPrestamosEnCurso.setItems(prestamospendientes);
    	
    	tcPDtipolibro.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getLibro().getNombreClase()));
    	tcPDtitulolibro.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getLibro().getTitulo()));
    	tcPDalumno.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getAlumno().getCorreo()));
    	tcPDfechaprestamo.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getFechaPrestamo().toString()));
    	tcPDfechadevolucion.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getFechaDevolucion().toString()));
    	tvPrestamosDevueltos.setItems(prestamosdevueltos);
    	
    	tcTLtipolibro.setCellValueFactory(libro -> new SimpleStringProperty(libro.getValue().getNombreClase()));
    	tcTLtitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
    	tcTLautor.setCellValueFactory(new PropertyValueFactory<>("autor"));
    	tcTLpaginas.setCellValueFactory(new PropertyValueFactory<>("numPaginas"));
    	tcTLduracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
    	tcTLpuntos.setCellValueFactory(libro -> new SimpleFloatProperty(libro.getValue().getPuntos()).asObject());
    	tvLibros.setItems(libros);
    	
    	tcTAnombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    	tcTAcorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
    	tcTAcurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
    	tvAlumnos.setItems(alumnos);

		tfPrimero.setDisable(true);
		tfSegundo.setDisable(true);
		tfTercero.setDisable(true);
		tfCuarto.setDisable(true);
		dpEstadistica.valueProperty().addListener((ob, ov, nv) -> comprobarFecha(dpEstadistica));
		
		rbAlumno.setToggleGroup(tgEleccion);
    	rbLibro.setToggleGroup(tgEleccion);
    	rbFecha.setToggleGroup(tgEleccion);
		tgEleccion.selectToggle(rbFecha);
    	tgEleccion.selectedToggleProperty().addListener((ob, ov, nv) -> comprobarSeleccionBotonBuscar());

		lvAlumnos.setDisable(true);
    	tvLibrosBusqueda.setDisable(true);

		lvAlumnos.setItems(alumnos);
    	lvAlumnos.setCellFactory(l -> new CeldaAlumno());
    	lvAlumnos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    	lvAlumnos.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> actualizaBuscarPrestamos());

		tcTituloBusqueda.setCellValueFactory(new PropertyValueFactory<>("titulo"));
    	tcAutorBusqueda.setCellValueFactory(new PropertyValueFactory<>("autor"));
    	tvLibrosBusqueda.setItems(libros);
    	tvLibrosBusqueda.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> actualizaBuscarPrestamos());

		dpFechaBuscar.valueProperty().addListener((ob, ov , nv) -> actualizaBuscarPrestamos());

		tcTPtipo.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getLibro().getNombreClase()));
    	tcTPtitulo.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getLibro().getTitulo()));
    	tcTPalumno.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getAlumno().getCorreo()));
    	tcTPfechaPrestamo.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getFechaPrestamo().toString()));
    	tcTPfechaDevolucion.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getFechaDevolucionString()));
    	tvPrestamosBusqueda.setItems(prestamos);
    }
    
    public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
    
    @FXML
    private void nuevoPrestamo() throws IOException {
    	crearAnadirPrestamo();
    	anadirPrestamo.showAndWait();
    }
    
    @FXML
    private void nuevoLibro() throws IOException {
    	crearAnadirLibro();
    	anadirLibro.showAndWait();
    }
    
    @FXML
    private void nuevoAlumno() throws IOException {
    	crearAnadirAlumno();
    	anadirAlumno.showAndWait();
    }
    
    @FXML
	private void salir() {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?", null)) {
			controladorMVC.terminar();
			System.exit(0);
		}
	}
    
    @FXML
    private void acercaDe() throws IOException {
    	AnchorPane contenido = FXMLLoader.load(LocalizadorRecursos.class.getResource("vistas/AcercaDe.fxml"));
    	Dialogos.mostrarDialogoInformacionPersonalizado("Acerca De", contenido);
    }
    
    @FXML
    private void devolverPrestamo() {
    	Prestamo prestamo = null;
    	try {
    		prestamo = tvPrestamosEnCurso.getSelectionModel().getSelectedItem();
    			if (prestamo != null) {
    				crearDevolucion(prestamo);
    				insertarFechaDevolucion.showAndWait();
    				actualizarPrestamosEnCurso();
    				actualizaPrestamosDevueltos();
    			}
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError("Devolver Préstamo", e.getMessage());
    	}
    }
    
    @FXML
    private void borrarPrestamo() {
    	Prestamo prestamo = null;
    	try {
    		prestamo = tvPrestamosEnCurso.getSelectionModel().getSelectedItem();
    		if (prestamo != null && Dialogos.mostrarDialogoConfirmacion("Borrar Préstamo", "¿Seguro que desea borrar el préstamo?", null)) {
    			controladorMVC.borrar(prestamo);
    			prestamospendientes.remove(prestamo);
    			actualizarPrestamosEnCurso();
    			Dialogos.mostrarDialogoInformacion("Borrar Préstamo", "Préstamo borrado con éxito");
    		}
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError("Borrar Prestamo", e.getMessage());
    	}
    }
    
    @FXML
    private void borrarDevolucion() {
    	Prestamo prestamo = null;
    	try {
    		prestamo = tvPrestamosDevueltos.getSelectionModel().getSelectedItem();
    		if (prestamo != null && Dialogos.mostrarDialogoConfirmacion("Borrar Préstamo", "¿Seguro que desea borrar el préstamo?", null)) {
    			controladorMVC.borrar(prestamo);
    			prestamospendientes.remove(prestamo);
    			actualizaPrestamosDevueltos();
    			Dialogos.mostrarDialogoInformacion("Borrar Préstamo", "Préstamo borrado con éxito");
    		}
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError("Borrar Prestamo", e.getMessage());
    	}
    }
    
    @FXML
    private void borrarLibro() {
    	Libro libro = null;
    	try {
    		libro = tvLibros.getSelectionModel().getSelectedItem();
    		if (libro != null && Dialogos.mostrarDialogoConfirmacion("Borrar Libro", "¿Seguro que desear borrar el libro?", null)) {
    			controladorMVC.borrar(libro);
    			libros.remove(libro);
    			actualizarLibros();
    			Dialogos.mostrarDialogoInformacion("Borrar Libro", "Libro borrado correctamente");    			
    		}
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError("Borrar Libro", e.getMessage());
    	}
    }
    
    @FXML
    private void borrarAlumno() {
    	Alumno alumno = null;
    	try {
    		alumno = tvAlumnos.getSelectionModel().getSelectedItem();
    		if (alumno != null && Dialogos.mostrarDialogoConfirmacion("Borrar Alumno", "¿Seguro que desea borrar el alumno?", null)) {
    			controladorMVC.borrar(alumno);
    			alumnos.remove(alumno);
    			actualizarAlumnos();
    			Dialogos.mostrarDialogoInformacion("Borrar Alumno", "Alumno borrado correctamente");
    		}
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError("Borrar Alumno", e.getMessage());
    	}
    }
    
	public void actualizarPrestamos() {
		prestamos.clear();
		tvPrestamosEnCurso.getSelectionModel().clearSelection();
		prestamos.setAll(controladorMVC.getPrestamos());
	}

    public void actualizarPrestamosEnCurso() {
    	prestamospendientes.clear();
    	tvPrestamosEnCurso.getSelectionModel().clearSelection();
    	List<Prestamo> prestamos = controladorMVC.getPrestamos();
    	for (Prestamo prestamo : prestamos) {
    		if (prestamo.getFechaDevolucion() == null) {
    			prestamospendientes.add(prestamo);
    		}
    	}
    }
    
    public void actualizaPrestamosDevueltos() {
    	prestamosdevueltos.clear();
    	tvPrestamosEnCurso.getSelectionModel().clearSelection();
    	List<Prestamo> prestamos = controladorMVC.getPrestamos();
    	for (Prestamo prestamo : prestamos) {
    		if (prestamo.getFechaDevolucion() != null) {
    			prestamosdevueltos.add(prestamo);
    		}
    	}
    }
    
    public void actualizarLibros() {
    	libros.clear();
    	tvLibros.getSelectionModel().clearSelection();
    	libros.setAll(controladorMVC.getLibros());
    }
    
    public void actualizarAlumnos() {
    	alumnos.clear();
    	tvAlumnos.getSelectionModel().clearSelection();
    	alumnos.setAll(controladorMVC.getAlumnos());
    }

	public void actualizarEstadisticas() {
		dpEstadistica.setValue(LocalDate.now());
		Map<Curso, Integer> mapa = controladorMVC.getEstadisticasMensualPorCurso(LocalDate.now());
    	tfPrimero.setText(mapa.get(Curso.PRIMERO).toString());
    	tfSegundo.setText(mapa.get(Curso.SEGUNDO).toString());
    	tfTercero.setText(mapa.get(Curso.TERCERO).toString());
    	tfCuarto.setText(mapa.get(Curso.CUARTO).toString());
	}
	
	private void actualizaBuscarPrestamos() {
    	prestamos.clear();
    	tvPrestamosBusqueda.getSelectionModel().clearSelection();
    	if (rbAlumno.isSelected()) {
    		Alumno alumno = null;
    		alumno = lvAlumnos.getSelectionModel().getSelectedItem();
    		prestamos.setAll(controladorMVC.getPrestamos(alumno));
    	} else if (rbLibro.isSelected()) {
    		Libro libro = null;
    		libro = tvLibrosBusqueda.getSelectionModel().getSelectedItem();
    		prestamos.setAll(controladorMVC.getPrestamos(libro));
    	} else if (rbFecha.isSelected()) {
			LocalDate fecha = dpFechaBuscar.getValue();
			prestamos.setAll(controladorMVC.getPrestamos(fecha));
		}
	}
	
	private void comprobarFecha(DatePicker dp) {
    	Map<Curso, Integer> mapa = controladorMVC.getEstadisticasMensualPorCurso(dp.getValue());
    	tfPrimero.setText(mapa.get(Curso.PRIMERO).toString());
    	tfSegundo.setText(mapa.get(Curso.SEGUNDO).toString());
    	tfTercero.setText(mapa.get(Curso.TERCERO).toString());
    	tfCuarto.setText(mapa.get(Curso.CUARTO).toString());
    }
    
	private void comprobarSeleccionBotonBuscar() {
    	if (rbAlumno.isSelected()) {
    		lvAlumnos.setDisable(false);
    		tvLibrosBusqueda.setDisable(true);
    		dpFechaBuscar.setDisable(true);
    	} else if (rbLibro.isSelected()) {
    		lvAlumnos.setDisable(true);
    		tvLibrosBusqueda.setDisable(false);
    		dpFechaBuscar.setDisable(true);
		} else if (rbFecha.isSelected()) {
			lvAlumnos.setDisable(true);
    		tvLibrosBusqueda.setDisable(true);
    		dpFechaBuscar.setDisable(false);
		}
    }

    private void crearAnadirAlumno() throws IOException {
    	if (anadirAlumno == null) {
    		anadirAlumno = new Stage();
    		FXMLLoader cargadorAnadirAlumno = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/AnadirAlumno.fxml"));
    		VBox raizAnadirAlumno = cargadorAnadirAlumno.load();
    		cAnadirAlumno = cargadorAnadirAlumno.getController();
    		cAnadirAlumno.setControladorMVC(controladorMVC);
    		cAnadirAlumno.setAlumnos(alumnos);
    		cAnadirAlumno.inicializa();
    		
    		Scene escenaAnadirAlumno = new Scene(raizAnadirAlumno);
    		anadirAlumno.setTitle("Añadir Alumno");
    		anadirAlumno.initModality(Modality.APPLICATION_MODAL);
    		anadirAlumno.setScene(escenaAnadirAlumno);
    	} else {
    		cAnadirAlumno.inicializa();
    	}
    }
    
    private void crearAnadirLibro() throws IOException {
    	if (anadirLibro == null) {
    		anadirLibro = new Stage();
    		FXMLLoader cargadorAnadirLibro = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/AnadirLibro.fxml"));
    		VBox raizAnadirLibro = cargadorAnadirLibro.load();
    		cAnadirLibro = cargadorAnadirLibro.getController();
    		cAnadirLibro.setControladorMVC(controladorMVC);
    		cAnadirLibro.setLibros(libros);
    		cAnadirLibro.inicializa();
    		
    		Scene escenaAnadirAlumno = new Scene(raizAnadirLibro);
    		anadirLibro.setTitle("Añadir Alumno");
    		anadirLibro.initModality(Modality.APPLICATION_MODAL);
    		anadirLibro.setScene(escenaAnadirAlumno);
    	} else {
    		cAnadirLibro.inicializa();
    	}
    }
    
    private void crearAnadirPrestamo() throws IOException {
    	if (anadirPrestamo == null) {
    		anadirPrestamo = new Stage();
    		FXMLLoader cargadorAnadirPrestamo = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/anadirPrestamo.fxml"));
    		VBox raizAnadirPrestamo = cargadorAnadirPrestamo.load();
    		cAnadirPrestamo = cargadorAnadirPrestamo.getController();
    		cAnadirPrestamo.setControladorMVC(controladorMVC);
    		cAnadirPrestamo.actualizaAlumnos();
    		cAnadirPrestamo.actualizaLibros();
    		cAnadirPrestamo.setPadre(this);
    		cAnadirPrestamo.inicializa();
    		
    		Scene escenaAnadirPrestamo = new Scene(raizAnadirPrestamo);
    		anadirPrestamo.setTitle("Añadir Préstamo");
    		anadirPrestamo.initModality(Modality.APPLICATION_MODAL);
    		anadirPrestamo.setScene(escenaAnadirPrestamo);
    	} else {
    		cAnadirPrestamo.actualizaAlumnos();
    		cAnadirPrestamo.actualizaLibros();
    		cAnadirPrestamo.inicializa();
    	}
    }
    
    private void crearDevolucion(Prestamo prestamo) throws IOException {
    	if (insertarFechaDevolucion == null) {
    		insertarFechaDevolucion = new Stage();
    		FXMLLoader cargadorFechaDevolucion = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/DevolverPrestamo.fxml"));
    		VBox raizFechaDevolucion = cargadorFechaDevolucion.load();
    		cFechaDevolucion = cargadorFechaDevolucion.getController();
    		cFechaDevolucion.setControladorMVC(controladorMVC);
    		cFechaDevolucion.setPrestamo(prestamo);
    		cFechaDevolucion.inicializa();
    		
    		Scene escenaDevolucion = new Scene(raizFechaDevolucion);
    		insertarFechaDevolucion.setTitle("Devolución de Préstamo");
    		insertarFechaDevolucion.initModality(Modality.APPLICATION_MODAL);
    		insertarFechaDevolucion.setScene(escenaDevolucion);
    	} else {
    		cFechaDevolucion.setPrestamo(prestamo);
    		cFechaDevolucion.inicializa();
    	}
    }
}
