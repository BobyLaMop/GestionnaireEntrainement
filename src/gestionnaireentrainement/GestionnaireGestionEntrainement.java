/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionnaireentrainement;

import controller.ControleurBD;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Exercice;
import model.Presence;

/**
 *
 * @author Simon
 */
public class GestionnaireGestionEntrainement implements Initializable
{
    @FXML
    private TableView<Presence> tableDispo;
    
    @FXML
    private TableView<Exercice> tableEntrainement;
    
    @FXML
    private TableColumn<Exercice, String> tableColNom;
    
    @FXML
    private TableColumn<Exercice, String> tableColDate;
    
    @FXML
    private TableColumn<Presence, String> tableColNomDispo;
    
    @FXML
    private TableColumn<Presence, String> tableColRaison;
    
    @FXML
    private TableColumn<Presence, String> tableColDispo;
    
    @FXML
    private Label lblEntrainement;
    
    @FXML
    private Button btnCreer;
    
    @FXML
    private Button btnQuitter;
    
    @FXML
    private Button btnModif;

    private ArrayList<Exercice> exercices;
    private ArrayList<Presence> presences;
    
    private ObservableList<Exercice> exercicesData = FXCollections.observableArrayList();
    private ObservableList<Presence> presencesData = FXCollections.observableArrayList();
    
    
    /*
    * Quitte la fenetre et fait apparaitre la fenetre de creation d'entrainement
    */
    @FXML
    private void handleCreer(ActionEvent event)
    {
        Parent root;
        try{
            root = (Parent) FXMLLoader.load(getClass().getResource("FenetreCreationEntrainement.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Création d'entraînement");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleModif(ActionEvent event)
    {
    
    }
    
    /*
    * Quitter la fenetre et retourne a la fenetre d'authentifiacation
    */
    @FXML
    private void handleQuitter(ActionEvent event)
    {
        Parent root;
        try{
            root = (Parent) FXMLLoader.load(getClass().getResource("FenetreAuthentification.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Authentification");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /*
    *Event listener lorsqu'on clique sur le tableview
    *Change le tableview des présences selon l'entrainement selectionne
    */
    @FXML
    private void handleSelect(MouseEvent event) throws SQLException
    {
        lblEntrainement.setText(tableEntrainement.getSelectionModel().getSelectedItem().getDates() 
                + " " + tableEntrainement.getSelectionModel().getSelectedItem().getNom());
        btnModif.setDisable(false);
        presences = ControleurBD.getPresence(tableEntrainement.getSelectionModel().getSelectedItem());
        
        tableDispo.getItems().clear();
        
        for(Presence p: presences)
        {
            presencesData.add(p);
        }
        tableDispo.setItems(presencesData);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            exercices = ControleurBD.getExercice();
        } catch (SQLException ex)
        {
            Logger.getLogger(GestionnaireGestionEntrainement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(Exercice e: exercices)
        {
            exercicesData.add(e);
        }
        
        
        
        tableColNom.setCellValueFactory(new PropertyValueFactory<Exercice, String>("nom"));
        tableColDate.setCellValueFactory(new PropertyValueFactory<Exercice, String>("dates"));
        
        tableColNomDispo.setCellValueFactory(new PropertyValueFactory<Presence, String>("matricule"));
        tableColRaison.setCellValueFactory(new PropertyValueFactory<Presence, String>("strPresent"));
        tableColDispo.setCellValueFactory(new PropertyValueFactory<Presence, String>("raison"));
        
        tableEntrainement.setItems(exercicesData);
    }    
    
}
