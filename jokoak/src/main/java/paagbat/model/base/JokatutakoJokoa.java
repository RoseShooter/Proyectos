package paagbat.model.base;

import java.util.Set;

import paagbat.model.enumerazioa.Plataforma;

public class JokatutakoJokoa extends Jokoa {
    private int jokatutakoOrduak;
    private String egoera;

    public JokatutakoJokoa(int id, String titulua, String garatzailea, String generoa, Set<Plataforma> plataforma, int pegi, String irteeraData, double prezioa, int jokatutakoOrduak, String egoera) {
        super(id, titulua, garatzailea, generoa, plataforma, pegi, irteeraData, prezioa);
        this.jokatutakoOrduak = jokatutakoOrduak;
        this.egoera = egoera;
    }

    public int getJokatutakoOrduak(){
        return jokatutakoOrduak;
    }

    public String getEgoera(){
        return egoera;
    }

    public void setJokatutakoOrduak(int jokatutakoOrduak){
        this.jokatutakoOrduak = jokatutakoOrduak;
    }

    public void setEgoera(String egoera){
        this.egoera = egoera;
    }
    
}
