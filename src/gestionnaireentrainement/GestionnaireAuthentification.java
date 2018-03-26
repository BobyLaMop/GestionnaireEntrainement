/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionnaireentrainement;

import controller.ControleurBD;
import controller.SimpleDataSource;
import java.io.IOException;
import model.Utilisateur;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class GestionnaireAuthentification implements Initializable
{

    @FXML
    private Button btnConnexion;
    
    @FXML
    private Button btnQuitter;
    
    @FXML
    private TextField txtUserName;
    
    @FXML
    private TextField txtPassword;
    
    @FXML
    private Label lblErreur;
    
    //Liste des utilisateurs
    private ArrayList<Utilisateur> users = new ArrayList<Utilisateur>();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //Initialisation de la BD
        try
        {
            
            SimpleDataSource.init("D:/Cégèp/Session 4/Environnement graphique/TP1/GestionnaireEntrainement/src/controller/database.properties");
        } catch (IOException ex)
        {
            Logger.getLogger(GestionnaireAuthentification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(GestionnaireAuthentification.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //On charge touts les users de la BD
        try
        {
            users = ControleurBD.getUsers();
        } catch (SQLException ex)
        {
            Logger.getLogger(GestionnaireAuthentification.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
    @FXML
    private void handleConnexion(ActionEvent event)
    {
        //Gestion des 
        if(txtUserName.getText()== null || txtUserName.getText().trim().isEmpty() 
                || txtPassword.getText()==null || txtPassword.getText().trim().isEmpty())
            lblErreur.setVisible(true);
        else
        {
            //On parcourt la liste des users
            for(Utilisateur u: users)
            {
                //Si l'utilisateur est trouve avec le bon username et le bon mot de passe
                if(u.getMatricule().equals(txtUserName.getText()) && u.getMdp().equals(txtPassword.getText()))
                {
                    //Si on est admin, on ouvre FenetreGestionEntrainement
                    if("admin".equals(u.getRole()))
                    {
                        Parent root;
                        try{
                            root = (Parent) FXMLLoader.load(getClass().getResource("FenetreGestionEntrainement.fxml"));
                            Stage stage = new Stage();
                            stage.setTitle("Gestion d'entraînement - " + u.getMatricule());
                            stage.setScene(new Scene(root));
                            stage.setResizable(false);
                            stage.show();
                            ((Node)(event.getSource())).getScene().getWindow().hide();
                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                    
                    break;
                }
            }
            lblErreur.setVisible(true);
        }
    }
    
    @FXML
    private void handleQuitter()
    {
        Platform.exit();
    }
}
