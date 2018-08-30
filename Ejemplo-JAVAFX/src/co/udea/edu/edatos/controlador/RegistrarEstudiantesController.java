package co.udea.edu.edatos.controlador;

import co.udea.edu.edatos.modelo.Estudiante;
import co.udea.edu.edatos.negocio.EstudianteBsn;
import co.udea.edu.edatos.negocio.impl.EstudianteBsnImpl;
import co.udea.edu.edatos.negocio.impl.EstudianteYaExisteException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegistrarEstudiantesController {

    @FXML
    private TextField txtIdentificacion;
    @FXML
    private TextField txtNombres;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtEdad;
    @FXML
    private TextField txtProgramaAcademico;
    @FXML
    private Button btnGuardar;

    private EstudianteBsn estudianteBsn;

    public RegistrarEstudiantesController(){
        estudianteBsn = new EstudianteBsnImpl();
    }


    @FXML
    private void btnGuardarEstudiante_action(){
        String identificacion = txtIdentificacion.getText().trim();
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        int edad =Integer.parseInt(txtEdad.getText().trim());
        int programaAcademico = Integer.parseInt(txtProgramaAcademico.getText().trim());
        //TODO validar todos los campos
        Estudiante estudiante = new Estudiante(identificacion, nombres, apellidos, edad, programaAcademico);
        try{
            estudianteBsn.crearEstudiante(estudiante);
        }catch(EstudianteYaExisteException eyee){
            eyee.printStackTrace();
        }
    }

}


