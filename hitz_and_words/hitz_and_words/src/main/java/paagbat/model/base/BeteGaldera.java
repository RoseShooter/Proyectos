package paagbat.model.base;

public class BeteGaldera extends Galdera{
    
    private String irudia;

    public BeteGaldera(String galderaEus, String galderaIng, String galderaEs, String erantzunZuzenaEus, String kategoriaEus, String kategoriaIng, String kategoriaEs, String maila, String irudia){
        super(galderaEus, galderaIng, galderaEs, erantzunZuzenaEus, kategoriaEus, kategoriaIng, kategoriaEs, maila);
        this.irudia = irudia;
    }

    public String getIrudia(){
        return irudia;
    }
}