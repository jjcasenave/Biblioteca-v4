package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ControladorAnadirLibro {

	private IControlador controladorMVC;
	private ObservableList<Libro> libros;
	
    @FXML    private TextField tfAutor;
    @FXML	 private Label lbLibro;
    @FXML    private TextField tfNombre;
    @FXML    private RadioButton rbEscrito;
    @FXML    private RadioButton rbAudio;
    @FXML    private TextField tfNumeroPaginas;
    @FXML    private TextField tfDuracion;
    @FXML    private Button btAtras;
    @FXML    private Button btAnadir;

    private ToggleGroup tgTipoLibro = new ToggleGroup();
    
    @FXML
    private void initialize() {
    	rbEscrito.setToggleGroup(tgTipoLibro);
    	rbAudio.setToggleGroup(tgTipoLibro);
    	tgTipoLibro.selectedToggleProperty().addListener((ob, ov, nv) -> comprobarRadioButton());
    	tfDuracion.textProperty().addListener((ob, ov, nv) -> comprobarNumero(tfDuracion));
    	tfNumeroPaginas.textProperty().addListener((ob, ov, nv) -> comprobarNumero(tfNumeroPaginas));
    	tfAutor.textProperty().addListener((ob , ov, nv) -> comprobarTexto(tfAutor));
    	tfNombre.textProperty().addListener((ob , ov, nv) -> comprobarTexto(tfNombre));
    }
    
    public void setControladorMVC(IControlador controladorMVC) {
    	this.controladorMVC = controladorMVC;
    }
    
    public void setLibros(ObservableList<Libro> libros) {
    	this.libros = libros;
    }
    
    @FXML
    void anadirLibro() {
    	Libro libro = null;
    	try {
    		libro = getLibro();
    		controladorMVC.insertar(libro);
    		libros.setAll(controladorMVC.getLibros());
    		Stage propietario = ((Stage) btAnadir.getScene().getWindow());
    		Dialogos.mostrarDialogoInformacion("Añadir Libro", "Libro añadido de forma correcta", propietario);
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError("Añadir Libro", e.getMessage());
    	}
    }

    @FXML
    void atras() {
    	((Stage) btAtras.getScene().getWindow()).close();
    }

    public void inicializa() {
    	lbLibro.setText("Prueba");
		tfNombre.setText("");
		tfAutor.setText("");
		tgTipoLibro.selectToggle(rbEscrito);
		comprobarRadioButton();
		tfDuracion.setText("0");
		comprobarNumero(tfDuracion);
		tfNumeroPaginas.setText("0");
		comprobarNumero(tfNumeroPaginas);
		
	}
    
    private void comprobarRadioButton() {
    	if (rbEscrito.isSelected()) {
    		tfDuracion.setDisable(true);
    		tfDuracion.setVisible(false);
    		tfNumeroPaginas.setDisable(false);
    		tfNumeroPaginas.setVisible(true);
    		lbLibro.setText("Número de Páginas");
    	} else if (rbAudio.isSelected()) {
    		tfDuracion.setDisable(false);
    		tfDuracion.setVisible(true);
    		tfNumeroPaginas.setVisible(false);
    		tfNumeroPaginas.setDisable(true);
    		lbLibro.setText("Duración:");
    	}
    }
    
    private void comprobarNumero(TextField campo) {
    	try {
    		int numero = Integer.parseInt(campo.getText());
    		if(numero > 0) {
    			campo.setStyle("-fx-border-color: green");
    		} else {
    			campo.setStyle("-fx-border-color: red");
    		}
    	} catch (Exception e){
    		campo.setStyle("-fx-border-color: red");
    	}
    }
    
    private void comprobarTexto(TextField campo) {
    	String texto = campo.getText().trim();
    	String vacio = "";
    	if (texto.equals(vacio)) {
    		campo.setStyle("-fx-border-color: red");
    	} else {
    		campo.setStyle("-fx-border-color: green");
    	}
    }
    
    private Libro getLibro() {
    	AudioLibro audioLibro = null;
		LibroEscrito libroEscrito = null;
    	String titulo = tfNombre.getText();
    	String autor = tfAutor.getText();
    	int paginas = Integer.parseInt(tfNumeroPaginas.getText());
    	int duracion = Integer.parseInt(tfDuracion.getText());
    	if (rbAudio.isSelected()) {
    		audioLibro = new AudioLibro(titulo, autor, duracion);
    		return audioLibro;
    	} else {
    		libroEscrito = new LibroEscrito(titulo, autor, paginas);
    		return libroEscrito;
    	}
    }
    
}

