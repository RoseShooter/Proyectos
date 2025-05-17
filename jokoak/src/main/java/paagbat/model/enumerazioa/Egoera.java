package paagbat.model.enumerazioa;

public enum Egoera {
    EROSITA ("erosita"), 
    HASITA ("hasita"), 
    JOLASTEN ("jolasten"),
    AMAITUTA("amaituta"), 
    DROPEATUTA ("dropeatuta");

    private final String izena;

    Egoera(String izena){
        this.izena = izena;
    }

    public String getIzena(){
        return izena;
    }
}
