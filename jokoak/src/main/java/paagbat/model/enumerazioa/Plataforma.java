package paagbat.model.enumerazioa;

public enum Plataforma {
    PS5("PlayStation5"), 
    STEAM("Steam"),
    SWITCH("Nintendo Switch");

    private final String izena;

    Plataforma(String izena){
        this.izena = izena;
    }

    public String getIzena(){
        return izena;
    }
}
