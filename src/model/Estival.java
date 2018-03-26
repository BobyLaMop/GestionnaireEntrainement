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
import java.sql.SQLException;

/**
 *
 * @author Pierre-Luc
 */
public class Estival {
    private Date debut;
    private Date fin;

    public Estival(Date debut, Date fin) {
        this.debut = debut;
        this.fin = fin;
    }
    
    public Date getDebut() {
        return debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }    
    
    public void ajouterEstival(Estival date) throws SQLException{
        Connection conn = SimpleDataSource.getConnection();
        try{            
            PreparedStatement statprep = conn.prepareStatement("INSERT INTO `dispo_estivale`"
                    + "(`debut`, `fin`) VALUES (?,?)");            
            statprep.setDate(1, date.getDebut());
            statprep.setDate(2, date.getFin());
            statprep.execute();  
        }
         finally
        {
           conn.close();
        }   
    }
}
