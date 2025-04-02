package paagbat.model.base;

import paagbat.model.enumerazioa.Hizkuntza;

public class ErantzunAnitzekoGaldera extends Galdera {
    private String[] aukerak;
    private String irudia;

    public ErantzunAnitzekoGaldera(String galderaEus, String galderaIng, String galderaEs, String[] aukerak, String erantzunZuzena, String kategoriaEus, String kategoriaIng, String kategoriaEs, String maila, String irudia) {
        super(galderaEus, galderaIng, galderaEs, erantzunZuzena, kategoriaEus, kategoriaIng, kategoriaEs, maila);
        this.aukerak = (aukerak != null) ? aukerak : new String[0];
        this.irudia = irudia;
    }

    public String[] getAukerak(Hizkuntza Hizkunta) {
        return aukerak;
    }

    public String getIrudia() {
        return irudia;
    }

}
