package paagbat.model.base;

import java.util.Set;

import paagbat.model.enumerazioa.Plataforma;

public class Jokoa {
    protected int id;
    protected String titulua;
    protected String garatzailea;
    protected String generoa;
    protected Set<Plataforma> plataforma;
    protected int pegi;
    protected String irteeraData;
    protected double prezioa;

    public Jokoa(int id, String titulua, String garatzailea, String generoa, Set<Plataforma> plataforma, int pegi, String irteeraData, double prezioa){
        this.id = id;
        this.titulua = titulua;
        this.garatzailea = garatzailea;
        this.generoa = generoa;
        this.plataforma = plataforma;
        this.pegi = pegi;
        this.irteeraData = irteeraData;
        this.prezioa = prezioa;
    }

    public int getId(){
        return id;
    }

    public String getTitulua(){
        return titulua;
    }

    public String getGeneroa(){
        return generoa;
    }

    public Set<Plataforma> getPlataforma() {
        return plataforma;
    }

    public int getPegi(){
        return pegi;
    }

    public String getIrteeraData(){
        return irteeraData;
    }

    public double getPrezioa(){
        return prezioa;
    }

    public void setTitulua(String titulua){
        this.titulua = titulua;
    }

    public void setGeneroa(String generoa){
        this.generoa = generoa;
    }
   
    public void setPlataforma(Set<Plataforma> plataforma) {
        this.plataforma = plataforma;
    }

    public void setPegi(int pegi){
        this.pegi = pegi;
    }

    public void setIrteeraData(String irteeraData){
        this.irteeraData = irteeraData;
    }
    

}
