/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package controller;

import model.Utilisateur;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import model.Exercice;
import model.Presence;

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
            SimpleDataSource.init(args[0]);
        
        Utilisateur vg = new Utilisateur("v28798592","Vachon-Gagnon","Vachon@gagnon" ,5100,"user");
//        Exercice ct = new Exercice("Champ de tir", "CT annuelle", "Valcartier", 2018-05-15, 2018-05-17, 19:00:00);

//        vg.ajouterUser();
//        vg.suprimerUser();
//        vg.modUser("v28798592", "Vachon-Gagnon", "vachon_gagnon@hotmail.com", 5100);

//        Semaine sem = new Semaine();
//        System.out.println(sem.getJour(1));

        Connection conn = SimpleDataSource.getConnection();
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
        Connection conn = SimpleDataSource.getConnection();
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
        Connection conn = SimpleDataSource.getConnection();
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
    
    //Retourne un arraylist des utilisateurs de la BD
    public static ArrayList<Utilisateur> getUsers() throws SQLException {
        Connection conn = SimpleDataSource.getConnection();
        ArrayList<Utilisateur> users = new ArrayList<Utilisateur>();
        try
        {
            Statement stat = conn.createStatement();
            ResultSet resultUser = stat.executeQuery("SELECT * FROM `utilisateur`");
            while(resultUser.next())
            {
                users.add(new Utilisateur(resultUser.getString(1),resultUser.getString(2),
                        resultUser.getString(3),resultUser.getInt(4), resultUser.getString(6)));
            }
        }
        finally
        {
            conn.close();
            return users;
        }
    }
    
    //Retourne un arraylist des entrainements de la BD
    public static ArrayList<Exercice> getExercice() throws SQLException {
        Connection conn = SimpleDataSource.getConnection();
        ArrayList<Exercice> exercices = new ArrayList<Exercice>();
        try
        {
            Statement stat = conn.createStatement();
            ResultSet resultEx = stat.executeQuery("SELECT * FROM `entrainement`");
            while(resultEx.next())
            {
                exercices.add(new Exercice(resultEx.getInt(1),resultEx.getString(2),
                        resultEx.getString(3),resultEx.getString(4), resultEx.getDate(5),
                resultEx.getDate(6),resultEx.getTime(7)));
            }
        }
        finally
        {
            conn.close();
            return exercices;
        }
    }
    
    //Retourne un arraylist des presences de la BD
    public static ArrayList<Presence> getPresence(Exercice e) throws SQLException {
        int id = e.getId();
        System.out.println(String.valueOf(id));
        Connection conn = SimpleDataSource.getConnection();
        ArrayList<Presence> presences = new ArrayList<Presence>();
        try
        {
            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("SELECT u.*, e.*,p.Present, r.nom "
                    + "FROM `présence` p INNER JOIN raison r " +
                    "ON p.id_raison = r.id_raison " +
                    "INNER JOIN entrainement e " +
                    "ON p.id_entrainement = e.id_entrainement " +
                    "INNER JOIN utilisateur u " +
                    "ON p.matricule = u.matricule " +
                    "WHERE p.id_entrainement = " + String.valueOf(id));
            
            while(result.next())
            {
                presences.add(new Presence(new Utilisateur(result.getString(1),
                        result.getString(2),result.getString(3),
                        result.getInt(4),result.getString(6)), 
                        new Exercice(result.getInt(7),result.getString(8),
                        result.getString(9),result.getString(10), result.getDate(11),
                result.getDate(12),result.getTime(13)), result.getBoolean(14), result.getString(15)));
            }
        }
        finally
        {
            conn.close();
            return presences;
        }
    }

    
}
