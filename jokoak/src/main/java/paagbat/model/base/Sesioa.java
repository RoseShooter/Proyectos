package paagbat.model.base;

public class Sesioa {
    private static Erabiltzailea erabiltzailea;

    public static void setErabiltzailea(Erabiltzailea e){
        erabiltzailea = e;
    }

    public static Erabiltzailea getErabiltzailea(){
        return erabiltzailea;
    }

    public static boolean isAdmin(){
        return erabiltzailea != null && "admin".equalsIgnoreCase(erabiltzailea.getRola());
    }
}
