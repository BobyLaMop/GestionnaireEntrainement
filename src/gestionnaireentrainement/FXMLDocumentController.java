/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionnaireentrainement;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Simon
 */
public class FXMLDocumentController implements Initializable
{
    @FXML
    private TableView<String> tableDispo;
    
    @FXML
    private TableView<String> tableEntrainement;
    
    @FXML
    private TableColumn<String, String> tableColNom;
    
    @FXML
    private TableColumn<String, String> tableColDate;
    
    @FXML
    private TableColumn<String, String> tableColNomDispo;
    
    @FXML
    private TableColumn<String, String> tableColRaison;
    
    @FXML
    private TableColumn<String, String> tableColDispo;
    
    @FXML
    private Label label;

    
    
    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
}
