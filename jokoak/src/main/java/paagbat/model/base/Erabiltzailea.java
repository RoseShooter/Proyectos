package paagbat.model.base;

public class Erabiltzailea {
    private int id;
    private String erabiltzaileIzena;
    private String pasahitza;
    private String rola;

    public Erabiltzailea(int id, String erabiltzaileIzena, String pasahitza, String rola){
        this.id = id;
        this.erabiltzaileIzena = erabiltzaileIzena;
        this.pasahitza = pasahitza;
        this.rola = rola;
    }

    public int getId(){
        return id;
    }

    public String getErabiltzaileIzena(){
        return erabiltzaileIzena;
    }

    public String getPasahitza(){
        return pasahitza;
    }

    public String getRola(){
        return rola;
    }

    public void setErabiltzaileIzena(String erabiltzaileIzena){
        this.erabiltzaileIzena = erabiltzaileIzena;
    }

    public void setPasahitza(String pasahitza){
        this.pasahitza = pasahitza;
    }

    public void setRola(String rola){
        this.rola = rola;
    }
}
