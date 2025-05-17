package paagbat.model.base;

import paagbat.model.enumerazioa.Rola;

public class Erabiltzailea {

    private int id;
    private String erabiltzaileIzena;
    private String email;
    private String pasahitza;
    private String profilIrudi;
    private Rola rola;

    public Erabiltzailea(int id, String erabiltzaileIzena, Rola rola, String profilIrudi, String email){
        this.id = id;
        this.erabiltzaileIzena = erabiltzaileIzena;
        this.rola = rola;
        this.profilIrudi = profilIrudi;
        this.email = email;
    }

    public Erabiltzailea(int id, String erabiltzaileIzena, String email, Rola rola){
        this.id = id;
        this.erabiltzaileIzena = erabiltzaileIzena;
        this.email = email;
        this.rola = rola;
    }

    public Erabiltzailea(String erabiltzaileIzena, String email, String pasahitza){
        this.erabiltzaileIzena = erabiltzaileIzena;
        this.email = email;
        this.pasahitza = pasahitza;
    }

    public Erabiltzailea(String erabiltzaileIzena){
        this.erabiltzaileIzena = erabiltzaileIzena;
    }

    public int getId(){
        return id;
    }

    public String getErabiltzaileIzena(){
        return erabiltzaileIzena;
    }

    public String getEmail(){
        return email;
    }

    public String getPasahitza(){
        return pasahitza;
    }

    public Rola getRola(){
        return rola;
    }

    public String getProfilIrudi(){
        return profilIrudi;
    }

    public void setErabiltzaileIzena(String erabiltzaileIzena){
        this.erabiltzaileIzena = erabiltzaileIzena;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPasahitza(String pasahitza){
        this.pasahitza = pasahitza;
    }

    public void setRola(Rola rola){
        this.rola = rola;
    }

    public void setProfilIrudi(String profilIrudi){
        this.profilIrudi = profilIrudi;
    }
}
