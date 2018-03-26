/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.SimpleDataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Pierre-Luc
 */
public class Utilisateur {
    private String matricule;
    private String mdp;
    private String couriel;
    private int organisation;
    private Estival disponibilite;
    private int[][] hebdo;
    private String role;

    public Utilisateur(String matricule, String mdp, String couriel, int organisation, String role) {
        this.matricule = matricule;
        this.mdp = mdp;
        this.couriel = couriel;
        this.organisation = organisation;
        this.disponibilite = null;
        this.hebdo = null;
        this.role = role;
    }

    public String getMatricule() {
        return matricule;
    }

    public String getMdp() {
        return mdp;
    }

    public String getCouriel() {
        return couriel;
    }

    public int getOrganisation() {
        return organisation;
    }

    public Estival getDisponibilite() {
        return disponibilite;
    }

    public int[][] getHebdo() {
        return hebdo;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setCouriel(String couriel) {
        this.couriel = couriel;
    }

    public void setOrganisation(int organisation) {
        this.organisation = organisation;
    }

    public void setDisponibilite(Estival disponibilite) {
        this.disponibilite = disponibilite;
    }

    public void setHebdo(int[][] hebdo) {
        this.hebdo = hebdo;
    }  

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }
    
    public void ajouterUser() throws SQLException{
        Connection conn = SimpleDataSource.getConnection();
        try{            
            PreparedStatement statprep = conn.prepareStatement("INSERT INTO `utilisateur`"
                    + "(`matricule`, `mdp`, `courriel`, `id_organisation`)"
                    + " VALUES (?,?,?,?)");            
            statprep.setString(1, matricule);
            statprep.setString(2, mdp);
            statprep.setString(3, couriel);
            statprep.setInt(4, organisation);
            statprep.execute();            
            
        }
         finally
        {
           conn.close();
        }      
    }
    
    public void suprimerUser() throws SQLException{
        Connection conn = SimpleDataSource.getConnection();
        try{            
            PreparedStatement statprep = conn.prepareStatement("DELETE FROM `présence` "
                    + "WHERE `matricule`= ?");
            statprep.setString(1, matricule);
            statprep.execute(); 
            
            statprep = conn.prepareStatement("DELETE FROM `utilisateur` WHERE "
                    + "`matricule` = ?");
            statprep.setString(1, matricule);
            statprep.execute();            
            
        }
         finally
        {
           conn.close();
        }
    }
 
    public void modUser(String nMatricule, String nMdp, String nCouriel, int nOrganisation, int nEstival) throws SQLException{
        Connection conn = SimpleDataSource.getConnection();
        try{            
            PreparedStatement statprep = conn.prepareStatement("UPDATE `utilisateur` SET "
                    + "`matricule`= ?,`mdp`= ?,`courriel`= ?,`id_organisation`= ?,"
                    + "`id_dispo_estivale`= ? WHERE `matricule` = ?");            
            statprep.setString(1, nMatricule);
            statprep.setString(2, nMdp);
            statprep.setString(3, nCouriel);
            statprep.setInt(4, nOrganisation);
            statprep.setInt(5, nEstival);
            statprep.setString(6, matricule);
            statprep.execute();  
        }
         finally
        {
           conn.close();
        }      
    }
    
    public void ajouterEstival(Estival date) throws SQLException{
        int estivalID = 2;
        int idRes;
        Date dateDebRes;
        Date dateFinRes;
        
        Connection conn = SimpleDataSource.getConnection();
        try{ 
            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("SELECT * FROM `dispo_estivale` ");
            
            while(result.next()){
                idRes = result.getInt(1);
                dateDebRes = result.getDate(2);
                dateFinRes = result.getDate(3);
                if(dateDebRes == date.getDebut() && dateFinRes == date.getFin()){
                    estivalID = idRes;
                }
            }
            
            PreparedStatement statprep = conn.prepareStatement("UPDATE `utilisateur` "
                    + "SET `id_dispo_estivale`=? WHERE `matricule`=?");            
            statprep.setInt(1, estivalID);
            statprep.setString(2, matricule);
            statprep.execute();          
        }
         finally
        {
           conn.close();
        }   
    }
    
    public void ajouterDispoSem() throws SQLException{       
        
        Connection conn = SimpleDataSource.getConnection();
        try{             
            PreparedStatement statprep = conn.prepareStatement("INSERT INTO "
                    + "`dispo_semaine`(`jour`, `plage`, `matricule_membre`) "
                    + "VALUES (?,?,?)");
            for (int i = 0; i < hebdo.length; i++) {
                statprep.setInt(1, hebdo[0][i]);
            statprep.setInt(2, hebdo[1][i]);
            statprep.execute(); 
            }
                     
        }
         finally
        {
           conn.close();
        }   
    }
}
