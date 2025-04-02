package paagbat.model.base;

public class Herria {

    private String herriIzena;
    private String probintzia;

    public Herria(String herriIzena, String probintzia) {
        this.herriIzena = herriIzena;
        this.probintzia = probintzia;
    }
    
    public String getHerriIzena() {
        return herriIzena;
    }

    public String getProbintzia() {
        return probintzia;
    }

    public void setHerriIzena(String herria) {
        this.herriIzena = herria;
    }

    public void setProbintzia(String probintzia) {
        this.probintzia = probintzia;
    }
    
    public String toString() {
        return herriIzena + " (" + probintzia + ")";
    }
    

}
