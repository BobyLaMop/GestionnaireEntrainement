/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Simon
 */
public class Presence
{
    private Utilisateur user;
    private Exercice ex;
    private String raison = null;
    private Boolean present;
    private String matricule;
    private String strPresent;
    

    public Presence(Utilisateur user, Exercice ex, Boolean present, String raison)
    {
        this.user = user;
        this.ex = ex;
        this.present = present;
        matricule = user.getMatricule();
        this.raison = raison;
        if(present)
            strPresent = "Oui";
        else
            strPresent = "Non";
    }

    public String getMatricule()
    {
        return matricule;
    }

    public void setMatricule(String matricule)
    {
        this.matricule = matricule;
    }

    public String getStrPresent()
    {
        return strPresent;
    }

    public void setStrPresent(String strPresent)
    {
        this.strPresent = strPresent;
    }

    
    public Utilisateur getUser()
    {
        return user;
    }

    public void setUser(Utilisateur user)
    {
        this.user = user;
    }

    public Exercice getEx()
    {
        return ex;
    }

    public void setEx(Exercice ex)
    {
        this.ex = ex;
    }

    public String getRaison()
    {
        return raison;
    }

    public void setRaison(String raison)
    {
        this.raison = raison;
    }

    public Boolean getPresent()
    {
        return present;
    }

    public void setPresent(Boolean present)
    {
        this.present = present;
    }
    
    
}
