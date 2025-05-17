package paagbat.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import paagbat.model.base.Erabiltzailea;
import paagbat.model.base.JokatutakoJokoa;
import paagbat.model.base.Jokoa;
import paagbat.model.base.Sesioa;
import paagbat.model.enumerazioa.Egoera;
import paagbat.model.enumerazioa.Plataforma;
import paagbat.model.enumerazioa.Rola;

public class DBKonektatu {
    private static final String DB_URL = "jdbc:sqlite:src/main/java/paagbat/sql/GameHive.db";
    public static final String IMG_RUTA = "jokoak/src/main/resources/paagbat/img/erabiltzaileak/";
    public static final String JOKOAK_RUTA = "jokoak/src/main/resources/paagbat/img/jokoak/";

    /**
     * Datu basearekin konexioa egiteko
     * @return
     */
    public static Connection konektatu() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("Errorea konektatzean: " + e.getMessage());
            return null;
        }
    }

    /**************************************************/
    /*                                                */
    /* ERABILTZAILEAK */
    /*                                                */
    /**************************************************/

    /**
     * Erabiltzaile berri bat sortzeko datu basean
     * @param e erabiltzaile berriaren datuak
     * @return
     * @throws SQLException
     */
    public boolean gehituErabiltzailea(Erabiltzailea e) throws SQLException {
        String sql = "INSERT INTO erabiltzaileak(Erabiltzailea, Email, Pasahitza) VALUES (?, ?, ?)";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, e.getErabiltzaileIzena());
            pstmt.setString(2, e.getEmail());
            pstmt.setString(3, e.getPasahitza());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException s) {
            String mezua = s.getMessage();
            if (mezua != null && mezua.contains("UNIQUE constraint failed")) {
                throw new SQLException("Erabiltzaile izena jada existitzen da.");
            }
            throw s; // beste erroreak bota berdin-berdin
        }
    }

    /**
     * Aplikazioan sesioa hasteko datuak baieztatzeko
     * @param username erabiltzaile izena
     * @param password erabiltzailearen pasahitza
     * @return
     * @throws SQLException
     */
    public boolean erabiltzaileaEgiaztatu(String username, String password) throws SQLException {
        String sql = "SELECT * FROM erabiltzaileak WHERE Erabiltzailea = ? AND Pasahitza = ?";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            // Erabiltzailea existitzen bada eta kredentzialak zuzenak badira
            if (rs.next()) {
                int id = rs.getInt("Id");
                String rolStr = rs.getString("Rola");
                String imgPath = rs.getString("Profil_irudia");
                String gmail = rs.getString("Email");

                Rola rol = Rola.valueOf(rolStr);

                Erabiltzailea user = new Erabiltzailea(id, username, rol, imgPath, gmail);

                Sesioa.setErabiltzailea(user);

                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Datu basean erroreren bat
        }
    }

    /**
     * Erabiltzailearen profil irudia eguneratzeko
     * @param erabiltzaileIzena sesioko erabiltzailearen izena
     * @param fitxategiIzena aukeratutako fitxategiaren izena
     */
    public void eguneratuIrudia(String erabiltzaileIzena, String fitxategiIzena) {
        String sql = "UPDATE erabiltzaileak SET Profil_irudia = ? WHERE Erabiltzailea = ?";
        try (Connection conn = DBKonektatu.konektatu();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fitxategiIzena);
            stmt.setString(2, erabiltzaileIzena);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            ;
        }
    }

    /**
     * Erabiltzaile guztien zerrenda erakisteko
     * @return
     * @throws SQLException
     */
    public List<Erabiltzailea> erabiltzaileakErakutsi() throws SQLException {
        ArrayList<Erabiltzailea> erabiltzaileak = new ArrayList<>();
        String sql = "SELECT Id, Erabiltzailea, Email, Rola FROM Erabiltzaileak";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                erabiltzaileak.add(new Erabiltzailea(
                        rs.getInt("Id"),
                        rs.getString("Erabiltzailea"),
                        rs.getString("Email"),
                        Rola.valueOf(rs.getString("Rola").toUpperCase())));

            }
        }
        return erabiltzaileak;

    }

    /**
     * Erabiltzailearen datuak eguneratzeko
     * @param erabiltzailea aldatutako erabiltzailea
     * @return
     * @throws SQLException
     */
    public boolean erabiltzaileaEguneratu(Erabiltzailea erabiltzailea) throws SQLException {
        String sql = "UPDATE Erabiltzaileak SET Erabiltzailea = ?, Email = ?, Rola = ? WHERE Id = ?";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, erabiltzailea.getErabiltzaileIzena());
            pstmt.setString(2, erabiltzailea.getEmail());
            pstmt.setString(3, erabiltzailea.getRola().name());
            pstmt.setInt(4, erabiltzailea.getId());

            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Aukeratutako erabiltzailearen datuak ezabatzeko
     * @param erabiltzailea aukeratutako erabiltzailea
     * @throws SQLException
     */
    public void erabiltzaileaEzabatu(Erabiltzailea erabiltzailea) throws SQLException {
        String sql = "DELETE FROM Erabiltzaileak WHERE Erabiltzailea = ?";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, erabiltzailea.getErabiltzaileIzena());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erabiltzailearen pasahitza baieztatzeko
     * @param erabiltzailea erabiltzailearen izena
     * @param pasahitza sartutako pasahitza
     * @return
     * @throws SQLException
     */
    public boolean pasahitzaEgiaztatu(String erabiltzailea, String pasahitza) throws SQLException {
        String sql = "SELECT Pasahitza FROM Erabiltzaileak WHERE Erabiltzailea = ?";
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, erabiltzailea);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String pasGordea = rs.getString("Pasahitza");
                return Objects.equals(pasGordea, pasahitza);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Erabiltzailearen pasahitza aldatzeko
     * @param erabiltzailea erabiltzailearen izena
     * @param pasahitzBerria sartutako pasahitz berria
     * @throws SQLException
     */
    public void eguneratuPasahitza(String erabiltzailea, String pasahitzBerria) throws SQLException {
        String sql = "UPDATE Erabiltzaileak SET Pasahitza = ? WHERE Erabiltzailea = ?";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pasahitzBerria);
            pstmt.setString(2, erabiltzailea);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erabiltzailearen datuak aldatzeko
     * @param erabiltzailea aukeratutako erabiltzailea
     * @return
     * @throws SQLException
     */
    public boolean eguneratuErabiltzaileDatuak(Erabiltzailea erabiltzailea) throws SQLException{
        String sql = "UPDATE Erabiltzaileak SET Erabiltzailea = ?, Email = ? WHERE Id = ?";

        try(Connection conn = konektatu();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, erabiltzailea.getErabiltzaileIzena());
            pstmt.setString(2, erabiltzailea.getEmail());
            pstmt.setInt(3, erabiltzailea.getId());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**************************************************/
    /*                                                */
    /* JOKOAK */
    /*                                                */
    /**************************************************/

    /**
     * Joko berriak datu basera sartzeko metodoa
     * 
     * @param joko emango diogun datua, kasu honentan Jokoa motako objektu bat
     * @throws SQLException datu baseko gauzak
     */

    public void jokoaSortu(Jokoa joko) throws SQLException {

        String sql = "INSERT INTO Jokoak (Titulua, Generoa, Garatzailea, Deskribapena, Plataforma, Pegi, Irteera_data, Prezioa, Irudia, Trailer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, joko.getTitulua());
            pstmt.setString(2, joko.getGeneroa());
            pstmt.setString(3, joko.getGaratzailea());
            pstmt.setString(4, joko.getDeskribapena());
            pstmt.setString(5, joko.getPlataforma().name());
            pstmt.setInt(6, joko.getPegi());
            pstmt.setString(7, joko.getIrteeraData());
            pstmt.setDouble(8, joko.getPrezioa());
            pstmt.setString(9, joko.getIrudia());
            pstmt.setString(10, joko.getTrailer());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Aukeratutako jokoaren datuak aldatzeko
     * @param joko aukeratutako jokoa
     * @throws SQLException
     */
    public void jokoaEguneratu(Jokoa joko) throws SQLException {
        String sql = "UPDATE Jokoak SET Titulua = ?, Generoa = ?, Garatzailea = ?, Deskribapena = ?, Plataforma = ?, Pegi = ?, Irteera_data = ?, Prezioa = ?, Irudia = ?, Trailer = ? WHERE Id = ?";
    
        try (Connection conn = konektatu();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, joko.getTitulua());
            pstmt.setString(2, joko.getGeneroa());
            pstmt.setString(3, joko.getGaratzailea());
            pstmt.setString(4, joko.getDeskribapena());
            pstmt.setString(5, joko.getPlataforma().name());
            pstmt.setInt(6, joko.getPegi());
            pstmt.setString(7, joko.getIrteeraData());
            pstmt.setDouble(8, joko.getPrezioa());
            pstmt.setString(9, joko.getIrudia());
            pstmt.setString(10, joko.getTrailer());
            pstmt.setInt(11, joko.getId());
    
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Jokoen zerrenda bat erakisteko
     * @return
     * @throws SQLException
     */
    public List<Jokoa> jokoakErakutsi() throws SQLException {
        ArrayList<Jokoa> jokoak = new ArrayList<>();
        String sql = "SELECT Id, Titulua, Generoa, Plataforma, Pegi, Irteera_data, Prezioa FROM Jokoak";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                jokoak.add(new Jokoa(
                        rs.getInt("Id"),
                        rs.getString("Titulua"),
                        rs.getString("Generoa"),
                        Plataforma.valueOf(rs.getString("Plataforma")),
                        rs.getInt("Pegi"),
                        rs.getString("Irteera_data"),
                        rs.getDouble("Prezioa")));
            }
        }
        return jokoak;
    }

    /**
     * Aukeratutako jokoa lortzeko id-aren bitartez
     * @param id aukeratutako jokoaren id-a
     * @return
     * @throws SQLException
     */
    public Jokoa jokoaLortu(int id) throws SQLException {
        String sql = "SELECT * FROM Jokoak WHERE Id = ?";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Jokoa(rs.getInt("Id"),
                        rs.getString("Titulua"),
                        rs.getString("Garatzailea"),
                        rs.getString("Generoa"),
                        Plataforma.valueOf(rs.getString("Plataforma")),
                        rs.getInt("Pegi"),
                        rs.getString("Irteera_data"),
                        rs.getDouble("Prezioa"),
                        rs.getString("Irudia"),
                        rs.getString("Trailer"),
                        rs.getString("Deskribapena"));
            }
        }
        return null;
    }

    /**
     * Desiratutako taulara jokoak gehitzeko erabiltzailearen id-aren arabera
     * @param erabiltzaileaId jokoa gehitu nahi duen erabiltzailearen id-a
     * @param jokoa gehituko den jokoaren id-a
     * @throws SQLException
     */
    public void gehituDesirora(int erabiltzaileaId, Jokoa jokoa) throws SQLException {
        String sql = "INSERT INTO Desiratutakoak (Erabiltzaile_id, Jokoa_id) VALUES (?, ?)";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, erabiltzaileaId);
            pstmt.setInt(2, jokoa.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Desiratutako taulatik jokoak ezabatzeko
     * @param erabiltzaileaId sesioko erabiltzailearen id-a
     * @param jokoId aukeratutako jokoaren id-a
     * @throws SQLException
     */
    public void ezabatuDesiroa(int erabiltzaileaId, int jokoId) throws SQLException {
        String sql = "DELETE FROM Desiratutakoak WHERE Erabiltzaile_id = ?  AND Jokoa_id = ?";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, erabiltzaileaId);
            pstmt.setInt(2, jokoId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Desiratutako taulan dauden jokoen zerrenda lortzeko sesioko erabiltzailearen id-aren arabera
     * @param erabiltzaileId sesioan dagoen erabiltzailearen di-a
     * @return
     * @throws SQLException
     */
    public List<Jokoa> lortuDesiratutakoak(int erabiltzaileId) throws SQLException {
        List<Jokoa> jokoak = new ArrayList<>();
        String sql = "SELECT j.Id, j.Titulua, j.Garatzailea, j.Generoa, j.Plataforma, j.Irteera_data, j.Prezioa FROM Desiratutakoak d INNER JOIN Jokoak j ON d.Jokoa_id = j.Id WHERE d.Erabiltzaile_id = ?";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, erabiltzaileId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Jokoa j = new Jokoa(
                        rs.getInt("Id"),
                        rs.getString("Titulua"),
                        rs.getString("Garatzailea"),
                        rs.getString("Generoa"),
                        Plataforma.valueOf(rs.getString("Plataforma")),
                        rs.getString("Irteera_data"),
                        rs.getDouble("Prezioa"));
                jokoak.add(j);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jokoak;

    }

    /**
     * Nire Lista taulara jokoak gehitzeko
     * @param joko aukeratutako jokoaren id-a
     * @param erabiltzaileId sesioko erabiltzailearen id-a
     * @throws SQLException
     */
    public void gehituNireJokoa(Jokoa joko, int erabiltzaileId) throws SQLException {
        String sql = "INSERT INTO Nire_Lista (Erabiltzaile_id, Jokoa_id) VALUES (?, ?)";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, erabiltzaileId);
            pstmt.setInt(2, joko.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Nire Listan dauden jokoen zerrenda lortzeko
     * @param erabiltzaileId sesioko erabiltzailearen id-a
     * @return
     * @throws SQLException
     */
    public List<JokatutakoJokoa> lortuNireJokoak(int erabiltzaileId) throws SQLException {
        List<JokatutakoJokoa> jokoak = new ArrayList<>();
        String sql = "SELECT nl.Id as NireListaId, j.Id as JokoaId, j.Titulua, j.Generoa, j.Plataforma, nl.Orduak, nl.Egoera " +
                     "FROM Nire_Lista nl INNER JOIN Jokoak j ON nl.Jokoa_id = j.Id WHERE nl.Erabiltzaile_id = ?";
    
        try (Connection conn = konektatu();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setInt(1, erabiltzaileId);
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                JokatutakoJokoa j = new JokatutakoJokoa(
                    rs.getInt("JokoaId"),
                    rs.getString("Titulua"),
                    rs.getString("Generoa"),
                    Plataforma.valueOf(rs.getString("Plataforma")),
                    rs.getInt("Orduak"),
                    Egoera.valueOf(rs.getString("Egoera")),
                    rs.getInt("NireListaId")
                );
    
                // Komprobaketa, datuak inprimatu
                System.out.println("JokatutakoJokoa sortuta: ");
                System.out.println("  - JokoaId: " + j.getId());
                System.out.println("  - Titulua: " + j.getTitulua());
                System.out.println("  - Generoa: " + j.getGeneroa());
                System.out.println("  - Plataforma: " + j.getPlataforma());
                System.out.println("  - Orduak: " + j.getJokatutakoOrduak());
                System.out.println("  - Egoera: " + j.getEgoera());
                System.out.println("  - NireListaId: " + j.getNireListaId());
                System.out.println("--------------------------------");
    
                jokoak.add(j);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return jokoak;
    }
    
    /**
     * Nire Listan dauden jokoen datuak eguneratzeko
     * @param jokoa aukeratutako jokoaren datuak
     * @return
     * @throws SQLException
     */
    public boolean jokatutakoaEguneratu(JokatutakoJokoa jokoa) throws SQLException {
        String sql = "UPDATE Nire_Lista SET Egoera = ?, Orduak = ? WHERE Id = ?";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jokoa.getEgoera().name());
            pstmt.setInt(2, jokoa.getJokatutakoOrduak());
            pstmt.setInt(3, jokoa.getNireListaId());

            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Nire Listan dauden jokoak erakusteko
     * @param nireListaId listako id-aren arabera lortu
     * @return
     * @throws SQLException
     */
    public JokatutakoJokoa jokatutakoJokoaErakutzi(int nireListaId) throws SQLException {
        String sql = "SELECT j.Titulua, nl.Orduak, nl.Egoera, nl.Id AS NireListaId " +
                     "FROM Nire_Lista nl " +
                     "INNER JOIN Jokoak j ON nl.Jokoa_id = j.Id " +
                     "WHERE nl.Id = ?";
    
        try (Connection conn = konektatu();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setInt(1, nireListaId);
            ResultSet rs = pstmt.executeQuery();
    
            if (rs.next()) {
                System.out.println("DEBUG -> Titulua: " + rs.getString("Titulua"));
                System.out.println("DEBUG -> Orduak: " + rs.getInt("Orduak"));
                System.out.println("DEBUG -> Egoera: " + rs.getString("Egoera"));
                System.out.println("DEBUG -> NireListaId: " + rs.getInt("NireListaId"));
    
                return new JokatutakoJokoa(
                    -1, 
                    rs.getString("Titulua"),
                    rs.getInt("Orduak"),
                    Egoera.valueOf(rs.getString("Egoera")),
                    rs.getInt("NireListaId")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return null;
    }

    /**
     * Jokoa nire listatik ezabatzeko
     * @param nireListaId nire listan dagoen id-aren arabera ezabatzeko
     * @throws SQLException
     */
    public void ezabatuNireJokoa(int nireListaId) throws SQLException {
        String sql = "DELETE FROM Nire_Lista WHERE Id = ?";
    
        try (Connection conn = konektatu();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nireListaId);
            int affectedRows = pstmt.executeUpdate();
            
            // Filak ezbatu badira ezabaketa exitosoa izateko
            if (affectedRows == 0) {
                System.out.println("Ez da ezabatzeko filarik aurkitu.");
            } else {
                System.out.println("Fila ondo ezabatu da.");
            }
        } catch (SQLException e) {
            System.out.println("Errora jokoaren ezabaketan.");
            e.printStackTrace();
        }
    }

    /**
     * Desiratutako taulan dauden joko kopurua zenbatzeko erabiltzailearen id-aren arabera
     * @param erabiltzaileId sesioan dagoen erabiltzailearen id-a
     * @return
     * @throws SQLException
     */
    public int desiroKantitatea(int erabiltzaileId) throws SQLException{
        int desiroak = 0;
        String sql = "SELECT COUNT(*) FROM Desiratutakoak WHERE Erabiltzaile_id = ?";

        try(Connection conn = konektatu();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, erabiltzaileId);

            try(ResultSet rs = pstmt.executeQuery()){
                if (rs.next()) {
                    desiroak = rs.getInt(1);
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }
        return desiroak;
    }

    /**
     * Nire listan dauden jokoen egoeraren araberako kontaketa
     * @param erabiltzaileId sesioko erabiltzailearen id-a
     * @param egoera zenbatzeko aukeratu zen egoera
     * @return
     * @throws SQLException
     */
    public int jokoKopEgoera(int erabiltzaileId, String egoera) throws SQLException {
        int kopurua = 0;
        String sql = "SELECT COUNT(*) FROM Nire_Lista WHERE Egoera = ? AND Erabiltzaile_id = ?";
    
        System.out.println("Consultando con Erabiltzaile_id: " + erabiltzaileId + " y Egoera: " + egoera);
    
        try (Connection conn = konektatu();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, egoera);  // Verifica que el tipo sea correcto
            pstmt.setInt(2, erabiltzaileId); // Verifica que este valor sea correcto
    
            ResultSet rs = pstmt.executeQuery();
    
            if (rs.next()) {
                kopurua = rs.getInt(1);
                System.out.println("Resultado de la consulta: " + kopurua);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return kopurua;
    }

    /**
     * Aukeratutako jokoa jada nire lista taulan dagoen edo ez ikusteko
     * @param erabiltzaileId sesioan dagoen erabiltzailearen id-a
     * @param jokoaId aukeratutako jokoaren id-a
     * @return
     */
    public boolean jokoaDagoenekoGehitutaDago(int erabiltzaileId, int jokoaId) {
        String sql = "SELECT COUNT(*) FROM Nire_Lista WHERE Erabiltzaile_id = ? AND Jokoa_id = ?";
        try (Connection conn = konektatu();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, erabiltzaileId);
            stmt.setInt(2, jokoaId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
