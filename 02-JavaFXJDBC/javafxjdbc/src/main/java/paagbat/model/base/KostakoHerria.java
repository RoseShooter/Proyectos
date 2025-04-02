package paagbat.model.base;


public class KostakoHerria extends Herria{

    private String[] hondartzak;

    public KostakoHerria(String izena, String probintzia, String[] hondartzak) {
        super(izena, probintzia);
        this.hondartzak = new String[]{ "", "", "" }; // Por defecto 3 elementos vacíos

        if (hondartzak != null) {
            for (int i = 0; i < this.hondartzak.length && i < hondartzak.length; i++) {
                this.hondartzak[i] = hondartzak[i];
            }
        }
    }

    public String[] getHondartzak() {
        return hondartzak;
    }

    public void setHondartzak(String[] hondartzak) {
        for (int i = 0; i < this.hondartzak.length && i < hondartzak.length; i++) {
            this.hondartzak[i] = hondartzak[i];
        }
    }

    public String toStringKompletoagoa(){
        String[] hondar = getHondartzak();
        String hondartzak = "";
        for (int i = 0; i < hondar.length; i++) {
            if (hondar[i] != null) {
                hondartzak += "\n\t- " + hondar[i]; 
            }
        }
        
        return getHerriIzena() + "(" + getProbintzia() + ")" + hondartzak;
    }
}
