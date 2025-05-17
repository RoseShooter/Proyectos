package paagbat.model.base;

import paagbat.model.enumerazioa.Egoera;
import paagbat.model.enumerazioa.Plataforma;

public class JokatutakoJokoa extends Jokoa {
    private int jokatutakoOrduak;
    private Egoera egoera;
    private int nireListaId;

    public JokatutakoJokoa(int id, String titulua, String generoa, Plataforma plataforma, int jokatutakoOrduak, Egoera egoera) {
        super(id, titulua,generoa, plataforma);
        this.jokatutakoOrduak = jokatutakoOrduak;
        this.egoera = egoera;
    }

    public JokatutakoJokoa(int id, String titulua, String generoa, Plataforma plataforma, int jokatutakoOrduak, Egoera egoera, int nireListaId) {
        super(id, titulua,generoa, plataforma);
        this.jokatutakoOrduak = jokatutakoOrduak;
        this.egoera = egoera;
        this.nireListaId = nireListaId;
    }

    public JokatutakoJokoa(int id, String titulua, int jokatutakoOrduak, Egoera egoera, int nireListaId) {
        super(id, titulua);
        this.jokatutakoOrduak = jokatutakoOrduak;
        this.egoera = egoera;
        this.nireListaId = nireListaId;
    }

    public int getJokatutakoOrduak(){
        return jokatutakoOrduak;
    }

    public Egoera getEgoera(){
        return egoera;
    }

    public int getNireListaId(){
        return nireListaId;
    }

    public void setJokatutakoOrduak(int jokatutakoOrduak){
        this.jokatutakoOrduak = jokatutakoOrduak;
    }

    public void setEgoera(Egoera egoera){
        this.egoera = egoera;
    }

    public void setNireListaId(int nireListaId){
        this.nireListaId = nireListaId;
    }
    
}
