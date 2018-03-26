/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionnaireentrainement;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Exercice;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class GestionnaireCreationEntrainement implements Initializable
{

    
    @FXML
    private ComboBox cbHeure;
           
    @FXML
    private TextField txtNom;
    
    @FXML
    private DatePicker dateDebut;
    
    @FXML
    private DatePicker dateFin;
    
    @FXML
    private TextArea txtDescription;
    
    @FXML
    private TextField txtLieu;
    
    @FXML
    private Label lblVide;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //On peuple la combobox
        ObservableList<String> options =
                    FXCollections.observableArrayList(
                            "7:00","7:30","8:00","19:00","19:30","20:00");
            
            cbHeure.getItems().clear();
            cbHeure.setItems(options);
            cbHeure.getSelectionModel().select(0);
            
        //On restreind le date picker
        dateDebut.setValue(LocalDate.now());
        final Callback<DatePicker, DateCell> dayCellFactory;

        dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isBefore(dateDebut.getValue())) { //Disable les dates avant celle du depart
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); //Met un fond different
                }
            }
        };
        dateDebut.setDayCellFactory(dayCellFactory);
        dateFin.setDayCellFactory(dayCellFactory);
    }    
    
    /*
    * Event listener lorsqu'on choisit une date sur date debut.
      On désactive les dates qui sont avant la date debut dans le date picker
      de date fin.
    */
    @FXML
    public void handleDateDebut(ActionEvent e)
    {
        final Callback<DatePicker, DateCell> dayCellFactory;

        dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isBefore(dateDebut.getValue())) { //Disable les dates avant celle du depart
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); //Met un fond different
                }
            }
        };
        dateFin.setDayCellFactory(dayCellFactory);
    }
    
    /*
    * Event listener lorsqu'on choisit une date sur date fin.
      On désactive les dates qui sont apres la date fin dans le date picker
      de date debut.
    */
    @FXML
    public void handleDateFin(ActionEvent e)
    {
        final Callback<DatePicker, DateCell> dayCellFactory;

        dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isAfter(dateFin.getValue()) || item.isBefore(LocalDate.now())) { //Disable les dates avant celle du depart
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); //Met un fond different
                }
            }
        };
        dateDebut.setDayCellFactory(dayCellFactory);
    }
    
    @FXML
    public void handleCreer(ActionEvent event) throws ParseException, SQLException
    {
        if(champsVides())
        {
            lblVide.setVisible(true);
        }
        else
        {
            //On convertie l'heure de depart et les dates
            DateFormat formatHeure = new SimpleDateFormat("HH:mm");
            Date sqlDateDebut = Date.valueOf(dateDebut.getValue());
            Date sqlDateFin = Date.valueOf(dateFin.getValue());   
            java.sql.Time timeValue = new java.sql.Time(formatHeure.parse(String.valueOf(cbHeure.getValue())).getTime());
            
            //On crée l'entrainement
            Exercice ex = new Exercice(1,txtNom.getText(),txtDescription.getText(),txtLieu.getText(),sqlDateDebut,sqlDateFin,timeValue);
            
            //On ajoute l'entrainement à la BD
            ex.ajouterEntrainement();
            
            Parent root;
            try{
                root = (Parent) FXMLLoader.load(getClass().getResource("FenetreGestionEntrainement.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Gestion d'entraînement");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    public void handleAnnuler(ActionEvent event)
    {
        Parent root;
        try{
            root = (Parent) FXMLLoader.load(getClass().getResource("FenetreGestionEntrainement.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Gestion d'entraînement");
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
    * Renvoit vrai si il y a un ou plusieurs champs vides faux sinon
    */
    public boolean champsVides()
    {
        return txtNom.getText()== null || txtNom.getText().trim().isEmpty() ||
                txtDescription.getText()== null || txtDescription.getText().trim().isEmpty() ||
                txtLieu.getText()== null || txtLieu.getText().trim().isEmpty() ||
                txtNom.getText()== null || txtNom.getText().trim().isEmpty() ||
                dateDebut.getValue() == null || dateFin.getValue() == null;
        
    }
}
