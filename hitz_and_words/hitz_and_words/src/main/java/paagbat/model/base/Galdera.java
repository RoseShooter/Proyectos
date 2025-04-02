package paagbat.model.base;
import paagbat.model.enumerazioa.Hizkuntza;

public abstract class Galdera {
    protected String galderaEus;
    protected String galderaIng;
    protected String galderaEs;
    protected String erantzunZuzena;
    protected String kategoriaEus;
    protected String kategoriaIng;
    protected String kategoriaEs;
    protected String maila;

    public Galdera(String galderaEus, String galderaIng, String galderaEs, String erantzunZuzena, String kategoriaEus, String kategoriaIng, String kategoriaEs, String maila){
        this.galderaEus = galderaEus;
        this.galderaIng = galderaIng;
        this.galderaEs = galderaEs;
        this.erantzunZuzena = erantzunZuzena;
        this.kategoriaEus = kategoriaEus;
        this.kategoriaIng = kategoriaIng;
        this.kategoriaEs = kategoriaEs;
        this.maila = maila;
    }

    public Galdera(String kategoriaEus, String kategoriaIng, String kategoriaEs, String maila){
        this.kategoriaEus = kategoriaEus;
        this.kategoriaIng = kategoriaIng;
        this.kategoriaEs = kategoriaEs;
        this.maila = maila;
    }

    public String getKategoriaEus(){
        return kategoriaEus;
    }

    public String getKategoriaIng(){
        return kategoriaIng;
    }

    public String getKategoriaEs(){
        return kategoriaEs;
    }

    public String getErantzunZuzena(){
        return erantzunZuzena;
    }


    public String getMaila(){
        return maila;
    }

    public String getGaldera(Hizkuntza hizkuntza){
        switch (hizkuntza) {
            case EUSKERA:
                return galderaEus;
    
            case INGLES:
                return galderaIng;
    
            case ESPAÑOL:
                return galderaEs;
    
            default:
                throw new IllegalArgumentException("Hizkuntza ezezaguna: " + hizkuntza);
        }
    }

    public boolean zuzenduErantzuna(String erantzuna){
        if (erantzuna == null) {
            return false;
        }
        return erantzuna.equalsIgnoreCase(erantzunZuzena);
    }
}
