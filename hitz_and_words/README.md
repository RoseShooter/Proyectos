# AURKIBIDEA

- [SARRERA](#sarrera)
  - [APLIKAZIOAREN HELBURUA](#aplikazioaren-helburua)
  - [ERABILITAKO TEKNOLOGIA](#erabilitako-teknologia)
  - [DATUEN ITURRIA](#datuen-iturria)
- [INTERFAZE GRAFIKOAREN FLUXUA](#interfaze-grafikoaren-fluxua)
  - [HASIERAKO MENUA](#1--hasierako-menua)
  - [KATEGORIEN MENUA](#2--kategorien-menua)
    - [A1 MAILA](#21--a1-maila)
      - [A1 ARIKETA MOTA](#211--a1-ariketa-mota)
    - [A2 MAILA](#22--a2-maila)
      - [A2 ARIKETA MOTA](#221--a2-ariketa-mota)
    - [B1 MAILA](#23--b1-maila)
      - [B1 ARIKETA MOTA](#231--b1-ariketa-mota)
  - [EMAITZAK](#emaitzak)
- [KLASE DIAGRAMA](#klase-diagrama)
- [ONDORIOAK](#ondorioak)
- [BIDEOA](#bideoa)
- [ERABILITAKO DATUEN FITXATEGIAK](#erabilitako-datuen-fitxategia)
  - [ERANTZUN ANITZEKO GALDERAK](#erantzun-anitzeko-galderak)
  - [BETETZEKO GALDERAK](#betetzeko-galderak)
  - [KONPRESIO GALDERAK](#konpresio-galderak)



# **Sarrera**
## Aplikazioaren helburua:
Aplikazioa Hitz & Words izena dauka, honen helburua euskera ikasten hasi berri diren pertsonentzat laguntza extra bat izatea da. 

Aplikazioan hiru maila desberdin aurkituko dituzte A1, A2 eta B1, hauen barruan 4 kategoria desberdin egongo dira, kategoria aukeratu ondoren galdera sorta bat agertuko da. Lehenik aukeratutako mailaren arabera galdera mota aldatu egingo da.

A1 mailan agertuko diren galderak erantzun anitz dituzten galderak izango dira. Hauetan argazki bat agertuko da, erabiltzaileak argazkian agertzen denaren izena aukeratu beharko du erantzunen artean, ondo badago erantzuna berdez jarriko da, txarto badago gorriz.

A2 mailan agertuko diren galderak betetzekoak izango dira. Hauetan aurrekoetan bezala argazki bat agertuko da, argazkian agertzen denaren izena zuzen idatzi beharko du erabiltzaileak, goikoetan bezala erantzunaren arabera honen kolorea aldatu egingo da.

B1 mailan agertiko diren galderak konpresio galderak izango dira. Hauetan textu txiki bat irakurri ondoren galdera batzuk erantzun beharko dira, galdera hauek erantzun anitzak edukiko dituzte, aurrekoetan bezala erantzunaren araberan honen kolorea aldatu egingo da.

Behin edozein kategoriaren galdetegian ezingo da atzera itzuli, hori egiteko bertako galdera guztiak erantzun beharko dira. Galdera guztiak erantzun ondoren emaitza atalean atzera itzultzeko botoia agertuko da.

Edozein kategoriaren galderak erantzun ondoren emaitzak agertuko dira, honetan erantzundako galdera kopuruaren arabera botoiak agertuko dira, botoi hauek aurretik erantzundako galderen arabera kolore berdea edo gorria edukiko dute erabiltzaileak totalean zenbat galdera ondo eta zenbat txarto dituen ikus dezan. Ez zaio txarto dauden galderen erantzun zuzena emango, hauek jakiteko berriro egin beharko ditu galdera erantzun zuzena asmatu arte.

Aplikazioan zehar hizkuntza aldatu daiteke, momentuan dauden hizkuntzak euskera, ingelesa eta gaztelera dira. Ezin izango da aplikazio guztian zehar hizkuntza aldatu, B1 mailatik aurrera hizkuntza aldatzeko botoia desagertu egingo da galdetegian sartu bezain laster. Aurreko mailetan hizkuntza aldatu daiteke baina galderaren hizkuntza baino ez da aldatuko.

Aplikazioa denbora guztiak pantaila osoan egongo da eta ezingo da txikitu, aplikaziotik irteteko irten botoiari heman beharko zaio.

## Erabilitako teknologia:
Aplikazioa sortzeko JavaFX erabili dugu. 

Aplikazioaren atal grafikoan agertzen diren elementu guztiak jartzeko SceneBuilder izeneko programa bat erabili dugu, honekin .fxml motako fitxategiak sortu eta gure proiektuan jarri ditugu. Atal hauen itzura aldatzeko Modena izeneko css motako fitxategi bat erabili dugu. Horretan elementuen id-arekin edota elementuaren izenareik horren itzura aldatu dugu.

Aplikazioaren atal lojikoa java erabiliz aurrera atera dugu. Honetan .fxml motako fitxategi bakoitzeko .java motako controller bat sortu dugu, honekin fxml-an gertatzen diren gauzak kontrolatzen ditugu ala nola irudiak erakutzi, botoiek egin beharreko akzioa sortu...

## Datuen iturria:
Aplikazioan agertzen diren galdera guztiak taldekideak pentsatutako galderak dira, ez dira ez liburu batetik ezta internetetik atera baizik eta guk geuk asmatutakoak dira.

Galderetan agertzen diren irudiak google-etik artutako irudiak dira, guztiak atzealdea kenduta daukate horregatik .png motako argazkiak dira.

# **Interfaze Grafikoaren fluxua**
## Hau da gure aplikazioaren eszeen fluxua.

### 1- Hasierako menua

Bertan gure aplikazioak diruen 3 maila desberdinak agertzen dira.

![Hasierako menua.](https://github.com/PAAG1-Ethazi-24-25/2_Erronka_Mireia_Unai_MikelCorrales_IkerEgana/blob/Program/Hitz_%26_Words/kapturak/mainMenu.png)

Bertan segun eta zer maila aukeratzen duzun aplikazioan dauzkagun 4 kategoria desberdinen maila desberdineko aukerak agertuko dira.

### **2- Kategorien menua**

Aukeratutako mailaren arabera hemen agertzen diren kategoriak pixka bat desberdinduko dira.

#### **2.1- A1 Maila**

![A1 mailaren kategorien menua.](https://github.com/PAAG1-Ethazi-24-25/2_Erronka_Mireia_Unai_MikelCorrales_IkerEgana/blob/Program/Hitz_%26_Words/kapturak/a1-maila.png)


Maila honen barruko ariketa guztiak erantzun anitzeko galderak izango dira. Galderan argazki bat agertuko da, bertan dauden aukeren artean bat erantzun zuzena da, beste hirurak okerrak dira.

##### **2.1.1- A1 Ariketa mota**

![A1 mailaren ariketa mota.](https://github.com/PAAG1-Ethazi-24-25/2_Erronka_Mireia_Unai_MikelCorrales_IkerEgana/blob/Program/Hitz_%26_Words/kapturak/a1-ariketa.png)

#### **2.2- A2 Maila**

![A2 mailaren kategorien menua.](https://github.com/PAAG1-Ethazi-24-25/2_Erronka_Mireia_Unai_MikelCorrales_IkerEgana/blob/Program/Hitz_%26_Words/kapturak/a2-maila.png)

Maila honen barruko ariketa guztiak betetzeko galderak izango dira. Galderan argazki bat agertuko da eta argazkian agertzen denaren izena ondo idatzi beharko da erantzuna zuzentzat hartzekoa.

##### **2.2.1- A2 Ariketa mota**

![A2 mailaren ariketa mota.](https://github.com/PAAG1-Ethazi-24-25/2_Erronka_Mireia_Unai_MikelCorrales_IkerEgana/blob/Program/Hitz_%26_Words/kapturak/a2-ariketa.png)

#### **2.3- B1 Maila**

![B1 mailaren kategorien menua.](https://github.com/PAAG1-Ethazi-24-25/2_Erronka_Mireia_Unai_MikelCorrales_IkerEgana/blob/Program/Hitz_%26_Words/kapturak/b1-maila.png)

Maila honetan agertzen diren ariketak konpresio galderak dira. Hauetan textu txiki bat agertuko da, textua irakurri ondoren hiru galdera erantzun beharko dira, galdera hauek erantzun anitz edukiko dituzte eta bakarra izango da zuzena.

##### **2.3.1- B1 Ariketa mota**

![B1 mailaren ariketa mota.](https://github.com/PAAG1-Ethazi-24-25/2_Erronka_Mireia_Unai_MikelCorrales_IkerEgana/blob/Program/Hitz_%26_Words/kapturak/b1-ariketa.png)

### **3-Emaitza**

Eszena hau aukeratutako maila eta kategoriaren galdera guztiak erantzun ondoren agertzen da, bertan erantzundako galdera kopuruaren araberako botoi kopuru berdina agertuko dira, hauek zuzen edo oker erantzun duzunaren arabera kolore berdea (zuzen) edo gorriz (oker) egongo dira.

![Emaitzen eszena.](https://github.com/PAAG1-Ethazi-24-25/2_Erronka_Mireia_Unai_MikelCorrales_IkerEgana/blob/Program/Hitz_%26_Words/kapturak/emaitzak.png)

# **Klase diagrama**
Hau da gure aplikazioaren klase diagrama.

![Aplikazioaren klase diagrama.](https://github.com/PAAG1-Ethazi-24-25/2_Erronka_Mireia_Unai_MikelCorrales_IkerEgana/blob/Program/Hitz_%26_Words/Hitz_%26_Words.svg)

# **Ondorioak**
Mota honetako aplikazioa egitea ez da erraza, aplikazioa beraren estruktuta eta karpeteoa ondo ordenatuta eta izendatuta edukitzea oso garrantzitsua da edozein pertsonak beharrezkoak dituen fitxategiak aurkitu ditzan.

Lojikaren aldetik gauzak ondo pentsadu behar dira aplikazio mota hauek egiteko ez baitago ondo egiteko modu bakarra, ziur nago gure klasean hau egiteko hainbat modu desberdin aurkitu daitezkeela eta guztiak funtzionatzen dutela. Horregatik ezin dira agetzen diren ideaiak oso azkar baztertu guztiak modu egokian eginda zuzenak izan ahal direlako.

Gure aplikazioari buruz hitz eginez gero arazoak eduki genituen hasiera batean gure ideia nagusia funtzionatu zezan. Hasiera batetik ahalik eta fxml eta controller gutxien erabiltzea zen gure ideia proiektua handiegia ez izateko eta gauzak errazago aurkitzeko.

Galderen inguruan pentsatu genuenean galdera guztiak fitxategi bakarrean egotea eta gero controller-a erabilita galderak fitxategitik hartzea eta pantailan erakustea, horrela controller-ean metodoak bakarrik agertuko ziren eta ez galderen ArrayList luze bat. Fitxategi horretan gordetako galderak kategoria eta galdera motaren arabera daude banatuta aplikazioan hiru galdera mota egotea erabaki baikenuen: erantzun anitzeko galderak, betetzeko galderak eta konpresio galderak. Esan daiteke galderak eta erantzunak asmatzea izan dela aplikazioa egiterakoan aurkitu dugun lan gogorrenetarikoa.

Galderak eta erantzunak pantailan erakusteko ere arazoak eduki genituen zeren era agertzen diren galderak kategoriaren araberakoak dira, behin kategoria bat aukeratzen denean kategoria horretako galderak baino ez dira agertuko. Hau lortzeko ActionEvent bat erabili dugu aukeratutako kategoriaren string-a datuak hauek bakarrik gordetzeko sortu dugun clase batean gordetzeko. Hori egin ondoren controller-aren barruan gordetako datu hori erabiliz galderak kategoriare arabera bilatu al ditugu arazorik barik.


# **Bideoa**

Hona hemen aplikazioaren funtzionamendua modu oso azkar batean erakusten duen bideo txiki bat.

https://youtu.be/GwKEKCLz6Qo

# **Erabilitako datuen fitxategia**

Aplikazioan zehar erabilitako galderen, esaldien, hitzen eta abar-en csv fitxategi bat egin dugu aurrerago erabilitako galdera hauek berriro erabili nahi izanez gero errazago edukitzeko.

## **Erantzun anitzeko galderak**

https://github.com/PAAG1-Ethazi-24-25/2_Erronka_Mireia_Unai_MikelCorrales_IkerEgana/blob/Program/Hitz_%26_Words/csv/erantzunAnitz.csv

## **Betetzeko galderak**

https://github.com/PAAG1-Ethazi-24-25/2_Erronka_Mireia_Unai_MikelCorrales_IkerEgana/blob/Program/Hitz_%26_Words/csv/beteGaldera.csv

## **Konpresio galderak**

https://github.com/PAAG1-Ethazi-24-25/2_Erronka_Mireia_Unai_MikelCorrales_IkerEgana/blob/Program/Hitz_%26_Words/csv/konpresioGalderak.csv
