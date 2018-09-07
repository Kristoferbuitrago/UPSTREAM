package co.edu.udea.edatos.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javax.annotation.PostConstruct;
import java.io.IOException;

public class MainContainerController {
    public static final String REGISTRAR_PERSONA = "registrarPe";
    public static final String REGISTRAR_VEHICULO = "registrarVe";
    @FXML
    private BorderPane mainContainer;

    private FXMLLoader loader;

    @PostConstruct
    public void postConstruct(){

    }

    @FXML
    public void initialize(){
        System.out.println("me inicialicé");
        changeView(REGISTRAR_PERSONA);
    }

    private void changeView(String name){
        switch (name){
            case REGISTRAR_PERSONA:
                loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../vista/registrarPersona.fxml"));
                try {
                    AnchorPane registrarPersona = loader.load();
                    mainContainer.setCenter(registrarPersona);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case REGISTRAR_VEHICULO:
                loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../vista/registrarVehiculo.fxml"));
                try {
                    AnchorPane registrarVehiculo = loader.load();
                    mainContainer.setCenter(registrarVehiculo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @FXML
    public void cmdPagina1_action(){
        changeView(REGISTRAR_PERSONA);
    }

    @FXML
    public void cmdPagina2_action(){
        changeView(REGISTRAR_VEHICULO);
    }

}
