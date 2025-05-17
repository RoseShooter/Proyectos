package paagbat.model.enumerazioa;

public enum Rola {
    ADMIN("admin"),
    USER("erabiltzaile");

    private final String label;

    Rola(String label){
        this.label = label;
    }

    @Override
    public String toString(){
        return label;
    }
}
