package paagbat.model.base;

public class Erabiltzailea {
    private int id;
    private String erabiltzaileIzena;
    private String pasahitza;

    public Erabiltzailea(int id, String erabiltzaileIzena, String pasahitza){
        this.id = id;
        this.erabiltzaileIzena = erabiltzaileIzena;
        this.pasahitza = pasahitza;
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

    public void setErabiltzaileIzena(String erabiltzaileIzena){
        this.erabiltzaileIzena = erabiltzaileIzena;
    }

    public void setPasahitza(String pasahitza){
        this.pasahitza = pasahitza;
    }
}
