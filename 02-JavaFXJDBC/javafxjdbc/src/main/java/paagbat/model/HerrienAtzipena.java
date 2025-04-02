package paagbat.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import paagbat.model.base.Herria;
import paagbat.model.base.KostakoHerria;

public class HerrienAtzipena {
    private String server = "localhost";
    private String db = "HerrienDBa";
    private String taula = "Herriak";

    String user = "ikaslea";
    String pass = "ikaslea";

    public HerrienAtzipena() {

    }

    public HerrienAtzipena(String server, String db, String taula, String user, String pass) {
        this.server = server;
        this.db = db;
        this.taula = taula;
        this.user = user;
        this.pass = pass;
    }

    /**
     * Metodo honek datu basearen herriak taulan dauden erregistroak zerrenda batean
     * sartuko ditu
     * 
     * @return herrien zerrenda itzuliko du
     */
    public List<Herria> getHerriak() {
        List<Herria> hZerrenda = new ArrayList<>();
        String sql = "SELECT * FROM " + taula;
    
        try (Connection conn = konektatu();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                String izena = rs.getString("Herria");
                String probintzia = rs.getString("Probintzia");
                String hondartza1 = rs.getString("Hondartza1");
                String hondartza2 = rs.getString("Hondartza2");
                String hondartza3 = rs.getString("Hondartza3");
    
                String[] hondartzak = {hondartza1, hondartza2, hondartza3};
    
                if ((hondartza1 != null && !hondartza1.isEmpty()) || 
                    (hondartza2 != null && !hondartza2.isEmpty()) || 
                    (hondartza3 != null && !hondartza3.isEmpty())) {
                    hZerrenda.add(new KostakoHerria(izena, probintzia, hondartzak));
                } else {
                    hZerrenda.add(new Herria(izena, probintzia));
                }
            }
    
        } catch (Exception e) {
            System.out.println("Errorea getHerriak(): " + e.getMessage());
        }
        return hZerrenda;
    }
    
    

    /**
     * Datu basearekin konekzioa egiten den zihurtatzeko
     * 
     * @return konekzioa egin den edo ez itzuliko du
     */
    public Connection konektatu() {

        String url = "jdbc:mariadb://" + server + "/" + db;
        Connection conn = null;
        {
            try {
                conn = DriverManager.getConnection(url, user, pass);
                // System.out.println(server + " zerbidoreko " + db + " datu-basera konektatu
                // zara.");
            } catch (SQLException e) {
                if (e.getErrorCode() == 1045){
                    System.out.println("Erabiltzaile edo pasahitz okerrak");
                }else if (e.getErrorCode() == 0){
                    System.out.println("Ezin zerbitzariarekin konektatu");
                }else {
                    System.out.println(e.getErrorCode() + "-" + e.getMessage());
                }
            }
            return conn;
        }
    }

    /**
     * Datu basearen taulan dauden erregistro kopurua itzultzen du.
     */
    public void herriKopurua() {
        String sql = "SELECT COUNT(*) AS Kopurua FROM " + taula; // sql-an jarri beharko litzatekeen sententzia

        // try-with-resources (closes all the resources when try finishes)
        try (Connection conn = konektatu(); // datu basera konektatzeko
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery(); // query-a exekutatzeko

            erregistratuLog("Herrial taulako erregistro kopurua zenbatu da");

            rs.next();
            System.out.println(taula + " taulako erregistro kopurua: " + rs.getString(1));// edo rs.getString("Kopurua")
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            erregistratuLog("Errorea zenbaketa egiterakoan: " + e.getMessage());
        }
    }

    /**
     * Datu basearen Herriak taulara erregistro berria sartzeko erabiltzen den
     * metodoa
     * 
     * @param herria objektua
     */
    public int txertatu(Herria herria) {
        String sql = "INSERT INTO Herriak(Herria, Probintzia, Hondartza1, Hondartza2, Hondartza3) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = konektatu(); // datu basera konektatzeko
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, herria.getHerriIzena()); // herriaren izena idazteko
            pstmt.setString(2, herria.getProbintzia()); // probintzia idazteko

            //kostako herria bada hondartzak ateratzen ditugu, bestela null jarriko dugu
            if (herria instanceof KostakoHerria) {
                String[] hondartzak = ((KostakoHerria)herria).getHondartzak();
                pstmt.setString(3, hondartzak.length > 0 ? hondartzak[0] : null);
                pstmt.setString(4, hondartzak.length > 1 ? hondartzak[1] : null);
                pstmt.setString(5, hondartzak.length > 2 ? hondartzak[2] : null);
            } else{
                pstmt.setNull(3, java.sql.Types.VARCHAR);
                pstmt.setNull(4, java.sql.Types.VARCHAR);
                pstmt.setNull(5, java.sql.Types.VARCHAR);
            }
            
            pstmt.executeUpdate(); // insert-a exekutatzeko

            erregistratuLog("Herria txertatu: " + herria.getHerriIzena() + " (" + herria.getProbintzia() + ")");
            return 1;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { 
                erregistratuLog("Txertatze huts egina (duplikatuta): " + herria.getHerriIzena());
                return 0;
            } else {
                erregistratuLog("SQL errorea: " + e.getErrorCode());
                return -1; 
            }
        }

    }

    /**
     * Datu basean jada herria existitzen den konprobatzeko
     * 
     * @param herria konprobatuko den objektua
     * @return itzuliko duen erantzuna (true edo false)
     */
    public boolean herriaBadago(Herria herria) {
        String sql = "SELECT COUNT(*) FROM Herriak WHERE Herria = ? AND Probintzia = ?";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, herria.getHerriIzena());
            pstmt.setString(2, herria.getProbintzia());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true; // Herria eta Probintzia existitzen dira
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false; // Herria edo Probintzia ez da existitzen
    }

    /**
     * Datu basearen herriak taulan dauden erregistro guztiak erakusten ditu
     * kontsolan
     */
    public void kontsulta() {
        String sql = "SELECT * FROM " + taula;

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.println("Herria (Probintzia)");
            System.out.println("===================================");
            ResultSet rs = pstmt.executeQuery();

            erregistratuLog("Herriak taula kontsultatu da");
            
            while (rs.next()) {
                System.out.println(new Herria(rs.getString("herria"), rs.getString("probintzia")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            erregistratuLog("Errorea kontsulta egiterakoan: " + e.getMessage());
        }
    }

    /**
     * Metodo honekin datu basean dagoen herri bat ezabatuko da.
     * 
     * @param herria datua lortzeko
     */
    public void ezabatu(String herria) {
        String sql = "DELETE FROM herriak WHERE Herria = ?";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, herria);
            pstmt.executeUpdate();

            erregistratuLog("Herria ezabatuta: " + herria);
            System.out.println(herria + " exabatu da.");
        } catch (SQLException e) {
            erregistratuLog("Errorea ezabatzerakoan: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    /**
     * Herri baten izena sartuta herri horren probintzia lortzeko metodoa
     * 
     * @param herria sartuko den datua
     * @return probintzia itzuliko du String moduan
     */
    public String getProbintzia(Herria herria) {
        String sql = "SELECT Probintzia FROM herriak WHERE Herria = ?";
        String probintzia = "";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, herria.getHerriIzena());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { // emaitza bakarra lortzen da
                    probintzia = rs.getString("Probintzia");
                    erregistratuLog("Probintzia aurkitu: " + herria.getHerriIzena() + " -> " + probintzia);
                } else {
                    erregistratuLog("Ez da probintziarik aurkitu herri honetarako: " + herria.getHerriIzena());
                }
            }
        } catch (SQLException e) {
            System.out.println("Errorea SQL-an: " + e.getMessage());
            erregistratuLog("Errorea probintzia bilatzerakoan: " + e.getMessage());
        }
        return probintzia;
    }

    /**
     * Herrien lista lortzeko metodoa
     * @return
     */
    public List<String> getHerriaLista() {
        List<String> herriak = new ArrayList<>();

        String sql = "SELECT Herria FROM herriak";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                herriak.add(rs.getString("Herria"));
            }
            erregistratuLog("Herrien zerrenda atera da.");

        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            erregistratuLog("Errorea zerrenda lortzerakoan: " + e.getErrorCode());
        }
        return herriak;
    }

    /**
     * Herrien lista lortzeko metodoa
     * @return
     */
    public List<String> getProbintziaLista() {
        List<String> probintziak = new ArrayList<>();

        String sql = "SELECT Probintzia FROM herriak";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                probintziak.add(rs.getString("Probintzia"));
            }
            erregistratuLog("Probintzien zerrenda atera da.");

        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            erregistratuLog("Errorea zerrenda lortzerakoan: " + e.getErrorCode());
        }
        return probintziak;
    }

    /**
     * Probintzia batean dauden herriak lortzeko metodoa.
     * 
     * @param herria probintzia lortzeko
     * @return herrien lista itzuliko du.
     */
    public List<String> getProbintziaBatekoHerriak(Herria herria) {
        String sql = "SELECT Herria FROM herriak WHERE Probintzia = ?";
        List<String> herriak = new ArrayList<>();

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, herria.getProbintzia());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    herriak.add(rs.getString("Herria")); // herriak arraylist-era gehitzen ditugu
                }
                erregistratuLog("Probintziako herriak atera dira.");
            }
        } catch (SQLException e) {
            System.out.println("Errorea SQL-an: " + e.getMessage());
            erregistratuLog("Errorea probintziako herriak ateratzerakoan: " + e.getErrorCode());
        }
        return herriak;
    }

    /**
     * Metodo honekin herriak probintziaka erakutziko dira eta probintzia bakoitzean dauden herri kopurua zenbatuko da
     * @return lista bat itzuliko du
     */
    public List<String> getHerriakProbintziaka() {
        String sql = "SELECT * FROM herriak ORDER BY Probintzia"; //probintziaka ordenatzeko
        List<String> emaitza = new ArrayList<>();

        String momentukoProbintzia = "";
        int count = 0; //herrien kopurua zenbatzeko

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String probintzia = rs.getString("Probintzia");
                String herria = rs.getString("Herria");

                /**
                 * probintzia aurretik gordetako momentukoProbintziaren desberdina bada probintzia berria dela esan nahi du
                 * probintzia berria erregistratzen denean herri kopurua count-eran gehitzen da
                 * momentukoProbintzia eguneratu egiten da probintzia berriarekin
                 * probintzia berriaren izena emaitza listara gehitzen da
                 */
                if (!probintzia.equals(momentukoProbintzia)) { 
                    if (!momentukoProbintzia.equals("")) {
                        emaitza.add("Herri kopurua: " + count + "\n");
                    }
                    momentukoProbintzia = probintzia;
                    emaitza.add("\n" + probintzia + ":");
                    count = 0;
                }

                /**
                 * herriaren izena listara gehitzen da marratzo batekin
                 * herrien kopurua daraman aladgaia eguneratzen da 
                 */
                emaitza.add("  -" + herria);
                count++;
            }

            // while-a amaitzerakoan azken probintziaren herri kopurua gehitzen da listara
            if (!momentukoProbintzia.equals("")) {
                emaitza.add("Herri kopurua: " + count + "\n");
            }

            // log taulan gordetzeko erregistroa
            erregistratuLog("Herriak probintziaka taldekatuta eta zenbatuta atera dira.");

        }catch (SQLException e){
            System.out.println(e.getErrorCode());
            erregistratuLog("Errorea herriak probintziaka ateratzerakoan: " + e.getErrorCode());
        }

        return emaitza;
    }

    /**
     * Log taulan erregistratzeko egiten den guztia
     * @param ekintza
     */
    public void erregistratuLog(String ekintza){
        String sql = "INSERT INTO Log (ekintza) VALUES(?)";

        try (Connection conn = konektatu();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, ekintza);
            pstmt.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
    }

    /**
     * Log taulan gordetzen diren erregistroak ikusteko
     */
    public void ikusiLogak(){
        String sql = "SELECT * FROM Log ORDER BY data DESC";

        try (Connection conn = konektatu();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()){
            System.out.println("=== LOG TAULA ===");
            while (rs.next()) {
                System.out.println(rs.getTimestamp("data") + " - " + rs.getString("ekintza"));
            }
        } catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
    }

    /**
     * Datu basean herri baten izena eguneratzeko
     * @param herria objektua erreferentziatzat
     * @param izenBerria herriari jarriko zaion izen berria
     */
    public void herriaAldatu(Herria herria, String izenBerria){
        String sql = "UPDATE Herriak SET Herria = ? WHERE Herria = ? AND Probintzia = ?";

        try (Connection conn = konektatu();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            //Izen berria ezartzen dugu
            pstmt.setString(1, izenBerria);
            //Eguneratzeko izena hartzen dugu erreferentziatzat
            pstmt.setString(2, herria.getHerriIzena());
            //Probintzia ere hartzen dugu erreferentziatzat
            pstmt.setString(3, herria.getProbintzia());

            int eguneratutakoZutabeak = pstmt.executeUpdate();

            //eguneratzen diren zutabeak 0 baino handiagoa bada
            if (eguneratutakoZutabeak > 0) {
                erregistratuLog("Herriaren izena aldatu da: " + herria.getHerriIzena() + " -> " + izenBerria + "(" + herria.getProbintzia() + ")");
                System.out.println("Izena aldatu da: " + herria.getHerriIzena() + " -> " + izenBerria + "(" + herria.getProbintzia() + ")");
            } else {
                System.out.println("Ez da aurkitu herririk aldaketa egiteko");
            }
        } catch (SQLException e){
            erregistratuLog("Errorea izena aldatzerakoan: " + e.getErrorCode());
            System.out.println(e.getMessage());
        }
    }

    /**
     * Herri bat guztiz eguneratzeko
     * @param herriZahar herriak aurretik zituen izena eta probintzia lortzeko
     * @param herriBerri herriari jarri nahi zaizkion izen eta probintzia berriak lortzeko
     */
    public void herriaEguneratu(Herria herriZahar, Herria herriBerri){
        String sql = "UPDATE Herriak SET Herria = ?, Probintzia = ? WHERE Herria = ? AND Probintzia = ?";

        try (Connection conn = konektatu();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            //herriaren izen berria
            pstmt.setString(1, herriBerri.getHerriIzena());
            //herriaren probintzia berria
            pstmt.setString(2, herriBerri.getProbintzia());
            //herriak zeukan izena
            pstmt.setString(3, herriZahar.getHerriIzena());
            //herriak zeukan probintzia
            pstmt.setString(4, herriZahar.getProbintzia());

            int eguneratutakoZutabeak = pstmt.executeUpdate();

            //eguneratzen diren zutabeak 0 baino handiagoa bada
            if (eguneratutakoZutabeak > 0) {
                erregistratuLog("Herria eguneratu da: " + herriZahar + " -> " + herriBerri);
                System.out.println("Herria eguneratu da: " + herriZahar + " -> " + herriBerri);
            } else {
                System.out.println("Ez da aurkitu herririk aldaketa egiteko");
            }
        } catch (SQLException e){
            erregistratuLog("Errorea herria eguneratzerakoan: " + e.getErrorCode());
            System.out.println(e.getMessage());
        }
    }

    public void kostakoHerriaEguneratu(Herria herriZahar, Herria herriBerri) {
        String sql = "UPDATE Herriak SET Herria = ?, Probintzia = ?, Hondartza1 = ?, Hondartza2 = ?, Hondartza3 = ? WHERE Herria = ? AND Probintzia = ?";
    
        try (Connection conn = konektatu(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, herriBerri.getHerriIzena());
            pstmt.setString(2, herriBerri.getProbintzia());
    
            // herriBerri kostako herria bada hondartzak eguneratuko dira
            if (herriBerri instanceof KostakoHerria) {
                String[] hondartzak = ((KostakoHerria) herriBerri).getHondartzak();
                pstmt.setString(3, hondartzak.length > 0 ? hondartzak[0] : null);
                pstmt.setString(4, hondartzak.length > 1 ? hondartzak[1] : null);
                pstmt.setString(5, hondartzak.length > 2 ? hondartzak[2] : null);
            } else { 
                // herriBerri herria bihurtzen bada hondartzak ezabatuko dira
                pstmt.setNull(3, java.sql.Types.VARCHAR);
                pstmt.setNull(4, java.sql.Types.VARCHAR);
                pstmt.setNull(5, java.sql.Types.VARCHAR);
            }
    
            pstmt.setString(6, herriZahar.getHerriIzena());
            pstmt.setString(7, herriZahar.getProbintzia());
    
            int eguneratutakoZutabeak = pstmt.executeUpdate();
            if (eguneratutakoZutabeak > 0) {
                erregistratuLog("Herria eguneratu da: " + herriZahar + " -> " + herriBerri);
                System.out.println("Herria eguneratu da: " + herriZahar + " -> " + herriBerri);
            } else {
                System.out.println("Ez da aurkitu herririk aldaketa egiteko");
            }
        } catch (SQLException e) {
            erregistratuLog("Errorea herria eguneratzerakoan: " + e.getErrorCode());
            System.out.println(e.getMessage());
        }
    }
       
}
