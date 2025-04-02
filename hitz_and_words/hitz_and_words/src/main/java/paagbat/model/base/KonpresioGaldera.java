package paagbat.model.base;

public class KonpresioGaldera extends Galdera {
    private String esaldi;
    private String[][] galderakEtaErantzunak = new String[2][3]; 
    // Galdera: [0][x]
    // Erantzun zuzena: [1][x]

    private String[][] erantzunak = new String[3][4];
    // Erantzunen taldea 1: [0][x]
    // Erantzunen taldea 2: [1][x]
    // Erantzunen taldea 3: [2][x]
    

    // KonpresioGaldera egin
    public KonpresioGaldera(String esaldi, String[][] galderakEtaErantzunak, String[][] erantzunak, String kategoriaEus,
            String kategoriaIng, String kategoriaEs, String maila) {
        super(kategoriaEus, kategoriaIng, kategoriaEs, maila);
        this.galderakEtaErantzunak = galderakEtaErantzunak;
        this.erantzunak = erantzunak;
        this.esaldi = esaldi;
    }

    public String getEsaldi() { // Esaldi lortzeko
        return esaldi;
    }

    public String[][] getGalderakEtaEraZuz() { // Esaldi Galderak eta erantzun zuzenak lortzeko
        return galderakEtaErantzunak;
    }

    public String[][] getErantzunak() { // Erantzun taldea lortzeko
        return erantzunak;
    }
}
