/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleurbd;

import java.sql.Connection;
import java.sql.Time;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Pierre-Luc
 */
public class Exercice {
    private int id;
    private String nom;
    private String description;
    private String lieu;
    private Date debut;
    private Date fin;
    private Time depart;

    public Exercice(int id, String nom, String description, String lieu, Date debut, Date fin, Time depart) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.lieu = lieu;
        this.debut = null;
        this.fin = null;
        this.depart = null;        
    }

    public int getId() {
        return id;
    }
    
    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getLieu() {
        return lieu;
    }

    public Date getDebut() {
        return debut;
    }

    public Date getFin() {
        return fin;
    }

    public Time getDepart() {
        return depart;
    }

    public void setId(int id) {
        this.id = id;
    }    
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public void setDepart(Time depart) {
        this.depart = depart;
    }
    
    public void ajouterEntrainement() throws SQLException{
        Connection conn = applicationexamen.SimpleDataSource.getConnection();
        try{            
            PreparedStatement statprep = conn.prepareStatement("INSERT INTO `entrainement`"
                    + "( `nom`, `description`, `lieu`, `date_debut`, `date_fin`, "
                    + "`heure_dep`) VALUES (?,?,?,?,?,?)");            
            statprep.setString(1, nom);
            statprep.setString(2, description);
            statprep.setString(3, lieu);
            statprep.setDate(4, debut);
            statprep.setDate(5, fin);
            statprep.setTime(6, depart);
            statprep.execute();            
            
        }
         finally
        {
           conn.close();
        }      
    }
    
    public void supEntrainement() throws SQLException{
        Connection conn = applicationexamen.SimpleDataSource.getConnection();
        try{            
            PreparedStatement statprep = conn.prepareStatement("DELETE FROM "
                    + "`entrainement` WHERE `id_entrainement` = ?");            
            statprep.setInt(1, id);           
            statprep.execute();            
            
        }
         finally
        {
           conn.close();
        }      
    }
    
    public void modifierEntrainement(String nNom, String nDescription, String nLieu, 
            Date nDebut, Date nFin, Time nDepart) throws SQLException{
        Connection conn = applicationexamen.SimpleDataSource.getConnection();
        try{            
            PreparedStatement statprep = conn.prepareStatement("UPDATE `entrainement`"
                    + " SET `nom`=?,`description`=?,`lieu`=?,`date_debut`=?,"
                    + "`date_fin`=?,`heure_dep`=? WHERE `id_entrainement` = ?");            
            statprep.setString(1, nNom);
            statprep.setString(2, nDescription);
            statprep.setString(3, nLieu);
            statprep.setDate(4, nDebut);
            statprep.setDate(5, nFin);
            statprep.setTime(6, nDepart);
            statprep.execute();          
            
        }
         finally
        {
           conn.close();
        }    
    }
}
