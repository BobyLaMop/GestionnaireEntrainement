/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleurbd;

/**
 *
 * @author Pierre-Luc
 */
public class Semaine {
    private String[] jour;
    private String[] horaire;

    public Semaine() {
        jour = new String[]{"lundi", "mardi", "mercredi", "jeudi", "vendredi"};
        horaire = new String[]{"avant-midi","après-midi", "soir"};
    }

    public String[] getJour() {
        return jour;
    }

    public String[] getHoraire() {
        return horaire;
    } 
    
    public String getJour(int index){
        return jour[index];
    }
    
    public String getHoraire(int index){
        return horaire[index];
    }
}
