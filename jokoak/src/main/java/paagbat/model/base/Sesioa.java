package paagbat.model.base;

import paagbat.model.enumerazioa.Rola;

public class Sesioa {
    private static Erabiltzailea erabiltzailea;

    public static void setErabiltzailea(Erabiltzailea e){
        erabiltzailea = e;
    }

    public static Erabiltzailea getErabiltzailea(){
        return erabiltzailea;
    }

    public static int getErabiltzaileId(){
        return erabiltzailea != null ? erabiltzailea.getId() : -1;
    }

    public static String getErabiltzaileIzen(){
        return erabiltzailea != null ? erabiltzailea.getErabiltzaileIzena() : null;
    }

    public static String getErabiltzaileEmail(){
        return erabiltzailea != null ? erabiltzailea.getEmail() : null;
    }

    public static Rola getRol(){
        return erabiltzailea != null ? erabiltzailea.getRola() : null;
    }

    public static String getProfilIrudia(){
        return erabiltzailea != null ? erabiltzailea.getProfilIrudi() : null;
    }

    public static boolean isAdmin(){
        return erabiltzailea != null && erabiltzailea.getRola() == Rola.ADMIN;
    }

    public static void logOut(){
        erabiltzailea = null;
    }
}
