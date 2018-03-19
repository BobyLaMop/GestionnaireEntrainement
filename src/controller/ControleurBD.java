/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleurbd;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

/**
 *
 * @author Pierre-Luc
 */
public class ControleurBD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
         if (args.length == 0)
            {   
               System.out.println(
                     "Usage: java -classpath driver_class_path" 
                     + File.pathSeparator 
                     + ". TestDB database.properties");
               return;
            }
        else 
            applicationexamen.SimpleDataSource.init(args[0]);
         
        Utilisateur vg = new Utilisateur("v28798592","Vachon-Gagnon","Vachon@gagnon" ,5100);
//        Exercice ct = new Exercice("Champ de tir", "CT annuelle", "Valcartier", 2018-05-15, 2018-05-17, 19:00:00);
        
//        vg.ajouterUser();
//        vg.suprimerUser();
//        vg.modUser("v28798592", "Vachon-Gagnon", "vachon_gagnon@hotmail.com", 5100);
        
//        Semaine sem = new Semaine();
//        System.out.println(sem.getJour(1));
         
        Connection conn = applicationexamen.SimpleDataSource.getConnection();
        try{            
            afficherUser();
            afficherExercice();
        }
         finally
        {
           conn.close();
        }

    }
    
    public static void afficherUser() throws SQLException{
        Connection conn = applicationexamen.SimpleDataSource.getConnection();
        try{
            Statement stat = conn.createStatement();
            String nm;
            String couriel;
            int org;
        
            ResultSet result = stat.executeQuery("SELECT * FROM `utilisateur`");
            while(result.next()){
            nm = result.getString(1);
            couriel = result.getString(3);
            org = result.getInt(4);
            System.out.println(nm + " " + couriel + " " + org );
            }
        }
        finally
        {
           conn.close();
        }
    }
    
    public static void afficherExercice() throws SQLException{
        Connection conn = applicationexamen.SimpleDataSource.getConnection();
        try{
            Statement stat = conn.createStatement();
            ResultSet resulta = stat.executeQuery("SELECT * FROM `entrainement`");
            String nom;
            String descript;
            String lieu;
            Date debut;
            Date fin;
            Time depart;
            while(resulta.next()){
                nom = resulta.getString(2);
                descript = resulta.getString(3);
                lieu = resulta.getString(4);
                debut = resulta.getDate(5);
                fin = resulta.getDate(6);
                depart = resulta.getTime(7);
                System.out.println(nom + " " + lieu + " " + debut + " " + fin + " "+ depart + " " + descript );
            }
        }
        finally
        {
           conn.close();
        }
    }
}
