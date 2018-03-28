/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionnairesFenetres;

import controller.ControleurBD;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Exercice;
import model.Presence;
import model.Utilisateur;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class GestionnaireGestionPresence implements Initializable
{

    @FXML
    private Label lblNom;
    
    @FXML
    private Label lblDebut;
    
    @FXML
    private Label lblFin;
    
    @FXML
    private TextArea txtDescription;
    
    @FXML
    private Label lblLieu;
    
    @FXML
    private Label lblHeure;
    
    @FXML
    private TableView<Exercice> tableEntrainement;
    
    @FXML
    private TableColumn<Exercice, String> tableColNom;
    
    @FXML
    private TableColumn<Exercice, String> tableColDate;
    
    @FXML
    private RadioButton rdPresent;
    
    @FXML
    private RadioButton rdNonPresent;
    
    @FXML
    private ComboBox cbRaison;
    
    @FXML
    private Button btnQuitter;
    
    @FXML
    private Button btnEnregistrer;
    
    @FXML
    private ToggleGroup present;
    
    @FXML
    private Label lblErreur;
    
    private Exercice exerciceSelectionne;
    private Presence presence;
    private Utilisateur currentUser;
    
 
    private ArrayList<Exercice> exercices;
    private ObservableList<Exercice> exercicesData = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //Load les entrainements dans le table view
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
        
        tableEntrainement.setItems(exercicesData);
        
        //On peuple la combobox
        ObservableList<String> options =
                    FXCollections.observableArrayList(
                            "famille","travail","école","autre");
            
        cbRaison.getItems().clear();
        cbRaison.setItems(options);
        cbRaison.setValue("");
        
        //On disable le text area de description on on le fait wrap
        txtDescription.setWrapText(true);
    }    
    
    
    /*
    *Event listener lorsqu'on clique sur le tableview
    *Change les lables pour l'information sur l'entrainement selectionné
    */
    @FXML
    private void handleSelect(MouseEvent event) throws SQLException
    {
        exerciceSelectionne = tableEntrainement.getSelectionModel().getSelectedItem();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        lblNom.setText(exerciceSelectionne.getNom());
        lblDebut.setText(df.format(exerciceSelectionne.getDebut()));
        lblFin.setText(df.format(exerciceSelectionne.getFin()));
        txtDescription.setText(exerciceSelectionne.getDescription());
        lblLieu.setText(exerciceSelectionne.getLieu());
        lblHeure.setText(exerciceSelectionne.getDepart().toString());
        
        //On va chercher la presence de l'utilisateur a l'exercice
        presence = ControleurBD.getPresence(exerciceSelectionne, currentUser);
        
        //Si il a une presence pour l'utilisateur
        if(presence!=null)
        {
           //On lit la presence
            boolean present = presence.getPresent();
            
            //Si l'utilisateur est present
            //On coche le radio button present
            if(present)
                rdPresent.setSelected(true);
            else
            {
                //Sinon on coche le radio button non preson
                rdNonPresent.setSelected(true);
                
                //On active le combo box pour les raisons et on met la bonne valeur
                cbRaison.setDisable(false);
                cbRaison.getSelectionModel().select(presence.getIdRaison());
            }
        }
        //Sinon
        else
        {
            
            //On met les radio buttons vides
            rdPresent.setSelected(false);
            rdNonPresent.setSelected(false);
            
            //On desactive le combobox
            cbRaison.setDisable(true);
            cbRaison.setValue("");
        }
    }
    
    /*
    * Action quand on clique sur le radio button present
    */
    @FXML
    private void handleSelectPresent(ActionEvent e)
    {
        cbRaison.setDisable(true);
        cbRaison.setValue("");
    }
    
    
    /*
    * Action quand on clique sur le radio button non present
    */
    @FXML
    private void handleSelectNonPresent(ActionEvent e)
    {
        cbRaison.setDisable(false);
        cbRaison.getSelectionModel().select(0);
    }
    
    /*
    * Quitte la fenetre et retourne a la fenetre d'authentifiacation
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
    
    @FXML
    private void handleEnregistrer(ActionEvent event) throws SQLException
    {
        boolean boolPresent;
        String raison = null;
        
        if(present.getSelectedToggle()!= null)
        {
            boolPresent = present.getSelectedToggle() == rdPresent;
            
            if(!boolPresent)
            {
                raison = cbRaison.getSelectionModel().getSelectedItem().toString();
            }
            
            //Si il y a une presence
            if(presence!=null)
            {
                //On update la presence
                
            }
            else
            {
                //Sinon on la crée et on l'ajoute
                Presence p = new Presence(currentUser,exerciceSelectionne,boolPresent,raison);
                p.ajouterPresence(currentUser, exerciceSelectionne);
            }

        }
        else
        {
            lblErreur.setText("Veuillez sélectionne votre présence!");
        }
        
        
    }
    
    //Methode pour envoyer les données de l'utilisateur à la fenêtre
    public void setCurrentUser(Utilisateur u)
    {
        currentUser = u;
    }
}
