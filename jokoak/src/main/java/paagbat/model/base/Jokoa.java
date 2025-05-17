package paagbat.model.base;

import paagbat.model.enumerazioa.Plataforma;

public class Jokoa {
    protected int id;
    protected String titulua;
    protected String garatzailea;
    protected String generoa;
    protected String deskribapena;
    protected Plataforma plataforma;
    protected int pegi;
    protected String irteeraData;
    protected double prezioa;
    protected String irudia;
    protected String trailer;

    public Jokoa(int id, String titulua){
        this.id = id;
        this.titulua = titulua;
    }

    public Jokoa(int id, String titulua, String generoa, Plataforma plataforma) {
        this.id = id;
        this.titulua = titulua;
        this.generoa = generoa;
        this.plataforma = plataforma;
    }

    public Jokoa(int id, String titulua, String garatzailea, String generoa, Plataforma plataforma, int pegi,
            String irteeraData, double prezioa) {
        this.id = id;
        this.titulua = titulua;
        this.garatzailea = garatzailea;
        this.generoa = generoa;
        this.plataforma = plataforma;
        this.pegi = pegi;
        this.irteeraData = irteeraData;
        this.prezioa = prezioa;
    }

    public Jokoa(int id, String titulua, String garatzailea, String generoa, Plataforma plataforma, int pegi,
            String irteeraData, double prezioa, String irudia, String trailer, String deskribapena) {
        this.id = id;
        this.titulua = titulua;
        this.garatzailea = garatzailea;
        this.generoa = generoa;
        this.plataforma = plataforma;
        this.pegi = pegi;
        this.irteeraData = irteeraData;
        this.prezioa = prezioa;
        this.irudia = irudia;
        this.trailer = trailer;
        this.deskribapena = deskribapena;
    }

    public Jokoa(String titulua, String generoa, String garatzailea, String deskribapena, Plataforma plataforma,
            int pegi,
            String irteeraData, double prezioa, String irudia, String trailer) {
        this.titulua = titulua;
        this.generoa = generoa;
        this.garatzailea = garatzailea;
        this.deskribapena = deskribapena;
        this.plataforma = plataforma;
        this.pegi = pegi;
        this.irteeraData = irteeraData;
        this.prezioa = prezioa;
        this.irudia = irudia;
        this.trailer = trailer;
    }

    public Jokoa(int id, String titulua, String generoa, Plataforma plataforma, int pegi, String irteeraData,
            double prezioa) {
        this.id = id;
        this.titulua = titulua;
        this.generoa = generoa;
        this.plataforma = plataforma;
        this.pegi = pegi;
        this.irteeraData = irteeraData;
        this.prezioa = prezioa;
    }

    public Jokoa(int id, String titulua, String garatzailea, String generoa, Plataforma plataforma, String irteeraData,
            double prezioa) {
        this.id = id;
        this.titulua = titulua;
        this.garatzailea = garatzailea;
        this.generoa = generoa;
        this.plataforma = plataforma;
        this.irteeraData = irteeraData;
        this.prezioa = prezioa;
    }

    public int getId() {
        return id;
    }

    public String getTitulua() {
        return titulua;
    }

    public String getGaratzailea() {
        return garatzailea;
    }

    public String getGeneroa() {
        return generoa;
    }

    public String getDeskribapena() {
        return deskribapena;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public int getPegi() {
        return pegi;
    }

    public String getIrteeraData() {
        return irteeraData;
    }

    public double getPrezioa() {
        return prezioa;
    }

    public String getIrudia() {
        return irudia;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTitulua(String titulua) {
        this.titulua = titulua;
    }

    public void setGaratzailea(String garatzailea) {
        this.garatzailea = garatzailea;
    }

    public void setGeneroa(String generoa) {
        this.generoa = generoa;
    }

    public void setDeskribapena(String deskribapena) {
        this.deskribapena = deskribapena;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    public void setPegi(int pegi) {
        this.pegi = pegi;
    }

    public void setIrteeraData(String irteeraData) {
        this.irteeraData = irteeraData;
    }

    public void setPrezioa(double prezioa) {
        this.prezioa = prezioa;
    }

    public void setIrudia(String irudia) {
        this.irudia = irudia;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

}
