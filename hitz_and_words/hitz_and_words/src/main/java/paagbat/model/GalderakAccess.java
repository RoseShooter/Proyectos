package paagbat.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import paagbat.model.base.BeteGaldera;
import paagbat.model.base.ErantzunAnitzekoGaldera;
import paagbat.model.base.KonpresioGaldera;

public class GalderakAccess {

        public GalderakAccess() {

        }

        public static List<ErantzunAnitzekoGaldera> galderakErantzunAnitz;

        static {

                galderakErantzunAnitz = Arrays.asList(

                                /*******************************************************************************
                                 *                                                                             *
                                 *                                 ANIMALIAK                                   *
                                 *                                                                             *
                                 ******************************************************************************/
                                new ErantzunAnitzekoGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?",
                                                new String[] { "Katua", "Txakurra", "Txoria", "Elefantea" }, "Txakurra",
                                                "Animaliak", "Animals", "Animales", "A1",
                                                "/img/animaliak/dog.png"),

                                new ErantzunAnitzekoGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?",
                                                new String[] { "Txakurra", "Leoia", "Xagua", "Katua" },
                                                "Katua", "Animaliak", "Animals", "Animales", "A1",
                                                "/img/animaliak/gato.png"),

                                new ErantzunAnitzekoGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?",
                                                new String[] { "Elefantea", "Sugea", "Txoria", "Oiloa" },
                                                "Elefantea", "Animaliak", "Animals", "Animales",
                                                "A1",
                                                "/img/animaliak/elefante.png"),

                                new ErantzunAnitzekoGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?",
                                                new String[] { "Elefantea", "Sugea", "Txoria", "Oiloa" }, "Txoria",
                                                "Animaliak", "Animals", "Animales", "A1",
                                                "/img/animaliak/pajaro.png"),

                                new ErantzunAnitzekoGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?",
                                                new String[] { "Txakurra", "Sugea", "Katua", "Oiloa" }, "Oiloa",
                                                "Animaliak", "Animals", "Animales", "A1",
                                                "/img/animaliak/gallina.png"),

                                new ErantzunAnitzekoGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?",
                                                new String[] { "Sugea", "Katua", "Ardia", "Xagua" }, "Sugea",
                                                "Animaliak", "Animals", "Animales", "A1",
                                                "/img/animaliak/sugea.png"),

                                new ErantzunAnitzekoGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?",
                                                new String[] { "Izurdea", "Leoia", "Katua", "Txoria" }, "Leoia",
                                                "Animaliak", "Animals", "Animales", "A1",
                                                "/img/animaliak/leon.png"),

                                new ErantzunAnitzekoGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?",
                                                new String[] { "Izurdea", "Txakurra", "Katua", "Ahatea" }, "Izurdea",
                                                "Animaliak", "Animals", "Animales", "A1",
                                                "/img/animaliak/delfin.png"),

                                new ErantzunAnitzekoGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?",
                                                new String[] { "Elefantea", "Izurdea", "Ardia", "Ahatea" }, "Ardia",
                                                "Animaliak", "Animals", "Animales", "A1",
                                                "/img/animaliak/oveja.png"),

                                new ErantzunAnitzekoGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?",
                                                new String[] { "Jirafa", "Katua", "Txoria", "Elefantea" }, "Jirafa",
                                                "Animaliak", "Animals", "Animales", "A1",
                                                "/img/animaliak/jirafa.png"),

                                /*******************************************************************************
                                 *                                                                             *
                                 *                                 KOLOREAK                                    *
                                 *                                                                             *
                                 ******************************************************************************/

                                new ErantzunAnitzekoGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?",
                                                new String[] { "Urdina", "Gorria", "Berdea", "Arrosa" }, "Urdina",
                                                "Koloreak", "Colours", "Colores", "A1",
                                                "/img/koloreak/blue.png"),

                                new ErantzunAnitzekoGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?",
                                                new String[] { "Marroia", "Laranja", "Berdea", "Gorria" }, "Gorria",
                                                "Koloreak", "Colours", "Colores", "A1",
                                                "/img/koloreak/red.png"),

                                new ErantzunAnitzekoGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?",
                                                new String[] { "Arrosa", "Morea", "Berdea", "Beltza" }, "Morea",
                                                "Koloreak", "Colours", "Colores", "A1",
                                                "/img/koloreak/morea.png"),

                                new ErantzunAnitzekoGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?",
                                                new String[] { "Zuria", "Gorria", "Berdea", "Marroia" }, "Berdea",
                                                "Koloreak", "Colours", "Colores", "A1",
                                                "/img/koloreak/green.png"),

                                new ErantzunAnitzekoGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?",
                                                new String[] { "Gorria", "Beltza", "Urdina", "Arrosa" }, "Arrosa",
                                                "Koloreak", "Colours", "Colores", "A1",
                                                "/img/koloreak/pink.png"),

                                new ErantzunAnitzekoGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?",
                                                new String[] { "Grisa", "Gorria", "Laranja", "Zuria" }, "Grisa",
                                                "Koloreak", "Colours", "Colores", "A1",
                                                "/img/koloreak/gris.png"),

                                new ErantzunAnitzekoGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?",
                                                new String[] { "Zuria", "Urdina", "Berdea", "Arrosa" }, "Zuria",
                                                "Koloreak", "Colours", "Colores", "A1",
                                                "/img/koloreak/zuria.png"),

                                new ErantzunAnitzekoGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?",
                                                new String[] { "Urdina", "Gorria", "Horia", "Morea" }, "Horia",
                                                "Koloreak", "Colours", "Colores", "A1",
                                                "/img/koloreak/horia.png"),

                                new ErantzunAnitzekoGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?",
                                                new String[] { "Horia", "Laranja", "Berdea", "Arrosa" }, "Laranja",
                                                "Koloreak", "Colours", "Colores", "A1",
                                                "/img/koloreak/laranja.png"),

                                new ErantzunAnitzekoGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?",
                                                new String[] { "Marroia", "Gorria", "Zuria", "Beltza" }, "Marroia",
                                                "Koloreak", "Colours", "Colores", "A1",
                                                "/img/koloreak/brown.png"),

                                new ErantzunAnitzekoGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?",
                                                new String[] { "Urdina", "Horia", "Gorria", "Beltza" }, "Beltza",
                                                "Koloreak", "Colours", "Colores", "A1",
                                                "/img/koloreak/black.png"),

                                /*******************************************************************************
                                 *                                                                             *
                                 *                                ZENBAKIAK                                    *
                                 *                                                                             *
                                 ******************************************************************************/

                                new ErantzunAnitzekoGaldera("Ze zenbaki da hau?", "What number is this?",
                                                "¿Qué número es este?",
                                                new String[] { "Bat", "Lau", "Zazpi", "Hamar" },
                                                "Lau", "Zenbakiak", "Numbers", "Numeros", "A1",
                                                "/img/zenbakiak/cuatro.png"),

                                new ErantzunAnitzekoGaldera("Ze zenbaki da hau?", "What number is this?",
                                                "¿Qué número es este?",
                                                new String[] { "Bost", "Hiru", "Sei", "Zero" },
                                                "Zero", "Zenbakiak", "Numbers", "Numeros", "A1",
                                                "/img/zenbakiak/zero.png"),

                                new ErantzunAnitzekoGaldera("Ze zenbaki da hau?", "What number is this?",
                                                "¿Qué número es este?",
                                                new String[] { "Bi", "Bat", "Zortzi", "Bost" },
                                                "Bi", "Zenbakiak", "Numbers", "Numeros", "A1",
                                                "/img/zenbakiak/dos.png"),

                                new ErantzunAnitzekoGaldera("Ze zenbaki da hau?", "What number is this?",
                                                "¿Qué número es este?",
                                                new String[] { "Hamar", "Zazpi", "Bederatzi", "Lau" },
                                                "Zazpi", "Zenbakiak", "Numbers", "Numeros", "A1",
                                                "/img/zenbakiak/siete.png"),

                                new ErantzunAnitzekoGaldera("Ze zenbaki da hau?", "What number is this?",
                                                "¿Qué número es este?",
                                                new String[] { "Zero", "Bat", "Bederatzi", "Hiru" },
                                                "Bederatzi", "Zenbakiak", "Numbers", "Numeros", "A1",
                                                "/img/zenbakiak/nueve.png"),

                                new ErantzunAnitzekoGaldera("Ze zenbaki da hau?", "What number is this?",
                                                "¿Qué número es este?",
                                                new String[] { "Bost", "Zortzi", "Zazpi", "Bat" },
                                                "Bost", "Zenbakiak", "Numbers", "Numeros", "A1",
                                                "/img/zenbakiak/cinco.png"),

                                new ErantzunAnitzekoGaldera("Ze zenbaki da hau?", "What number is this?",
                                                "¿Qué número es este?",
                                                new String[] { "Sei", "Hamar", "Zortzi", "Hiru" },
                                                "Zortzi", "Zenbakiak", "Numbers", "Numeros", "A1",
                                                "/img/zenbakiak/ocho.png"),

                                new ErantzunAnitzekoGaldera("Ze zenbaki da hau?", "What number is this?",
                                                "¿Qué número es este?",
                                                new String[] { "Sei", "Bi", "Zero", "Lau" },
                                                "Sei", "Zenbakiak", "Numbers", "Numeros", "A1",
                                                "/img/zenbakiak/seis.png"),

                                new ErantzunAnitzekoGaldera("Ze zenbaki da hau?", "What number is this?",
                                                "¿Qué número es este?",
                                                new String[] { "Lau", "Hamar", "Bat", "Bederatzi" },
                                                "Hamar", "Zenbakiak", "Numbers", "Numeros", "A1",
                                                "/img/zenbakiak/diez.png"),

                                new ErantzunAnitzekoGaldera("Ze zenbaki da hau?", "What number is this?",
                                                "¿Qué número es este?",
                                                new String[] { "Zero", "Zortzi", "Hiru", "Bat" },
                                                "Bat", "Zenbakiak", "Numbers", "Numeros", "A1",
                                                "/img/zenbakiak/uno.png"),

                                /*******************************************************************************
                                 *                                                                             *
                                 *                                 Gauzak                                      *
                                 *                                                                             *
                                 ******************************************************************************/
                                new ErantzunAnitzekoGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?",
                                                new String[] { "Liburua", "Aulkia", "Telebista", "Ispilua" },
                                                "Aulkia", "Gauzak", "Things", "Cosas", "A1",
                                                "/img/gauzak/silla.png"),

                                new ErantzunAnitzekoGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?",
                                                new String[] { "Mahaia", "Arkatza", "Ohea", "Erlojua" },
                                                "Mahaia", "Gauzak", "Things", "Cosas", "A1",
                                                "/img/gauzak/mesa.png"),

                                new ErantzunAnitzekoGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?",
                                                new String[] { "Lehioa", "Giltza", "Liburua", "Atea" },
                                                "Liburua", "Gauzak", "Things", "Cosas", "A1",
                                                "/img/gauzak/libro.png"),

                                new ErantzunAnitzekoGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?",
                                                new String[] { "Ispilua", "Telebista", "Mahaia", "Lehioa" },
                                                "Telebista", "Gauzak", "Things", "Cosas", "A1",
                                                "/img/gauzak/tele.png"),

                                new ErantzunAnitzekoGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?",
                                                new String[] { "Erlojua", "Giltza", "Ohea", "Telefonoa" },
                                                "Ohea", "Gauzak", "Things", "Cosas", "A1",
                                                "/img/gauzak/cama.png"),

                                new ErantzunAnitzekoGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?",
                                                new String[] { "Atea", "Arkatza", "Liburua", "Aulkia" },
                                                "Atea", "Gauzak", "Things", "Cosas", "A1",
                                                "/img/gauzak/puerta.png"),

                                new ErantzunAnitzekoGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?",
                                                new String[] { "Mahaia", "Telebista", "Ispilua", "Erlojua" },
                                                "Erlojua", "Gauzak", "Things", "Cosas", "A1",
                                                "/img/gauzak/reloj.png"),

                                new ErantzunAnitzekoGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?",
                                                new String[] { "Giltza", "Ohea", "Lehioa", "Atea" },
                                                "Giltza", "Gauzak", "Things", "Cosas", "A1",
                                                "/img/gauzak/llave.png"),

                                new ErantzunAnitzekoGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?",
                                                new String[] { "Liburua", "Mahaia", "Telefonoa", "Arkatza" },
                                                "Telefonoa", "Gauzak", "Things", "Cosas", "A1",
                                                "/img/gauzak/phone.png"),

                                new ErantzunAnitzekoGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?",
                                                new String[] { "Erlojua", "Ispilua", "Giltza", "Telebista" },
                                                "Ispilua", "Gauzak", "Things", "Cosas", "A1",
                                                "/img/gauzak/espejo.png"),

                                new ErantzunAnitzekoGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?",
                                                new String[] { "Atea", "Lehioa", "Ohea", "Arkatza" },
                                                "Arkatza", "Gauzak", "Things", "Cosas", "A1",
                                                "/img/gauzak/lapiz.png"),

                                new ErantzunAnitzekoGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?",
                                                new String[] { "Liburua", "Telefonoa", "Lehioa", "Ahulkia" },
                                                "Lehioa", "Gauzak", "Things", "Cosas", "A1",
                                                "/img/gauzak/ventana.png"));

                ;
        }

        /**
         * Maila eta kategoriaren arabera galderak filtratu eta nahasi
         * @param kategoria galderen kategoria
         * @param maila galderen maila
         * @return itzultzen dena, kasu honetan arraylist bat
         */
        public List<ErantzunAnitzekoGaldera> getGalderak(String kategoria, String maila) {
                List<ErantzunAnitzekoGaldera> galderakFiltratuak = new ArrayList<>();

                // Galderak maila eta kategoriagatik filtratu
                for (ErantzunAnitzekoGaldera galdera : galderakErantzunAnitz) {
                        if ((galdera.getKategoriaEus().equals(kategoria) || galdera.getKategoriaIng().equals(kategoria))
                                        && galdera.getMaila().equals(maila)) {
                                galderakFiltratuak.add(galdera);
                        }
                }

                Collections.shuffle(galderakFiltratuak); // Galderak nahastu
                return galderakFiltratuak.size() > 12 ? galderakFiltratuak.subList(0, 10) : galderakFiltratuak;
        }

        public static List<BeteGaldera> galderaBete;

        static {
                galderaBete = Arrays.asList(
                                /*******************************************************************************
                                 *                                                                             *
                                 *                                 ANIMALIAK                                   *
                                 *                                                                             *
                                 ******************************************************************************/
                                new BeteGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?", "Txakurra", "Animaliak 2", "Animals 2",
                                                "Animales 2", "A2",
                                                "/img/animaliak/dog.png"),

                                new BeteGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?", "Katua", "Animaliak 2", "Animals 2",
                                                "Animales 2", "A2",
                                                "/img/animaliak/gato.png"),

                                new BeteGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?", "Leoia", "Animaliak 2", "Animals 2",
                                                "Animales 2", "A2",
                                                "/img/animaliak/leon.png"),

                                new BeteGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?", "Xagua", "Animaliak 2", "Animals 2",
                                                "Animales 2", "A2",
                                                "/img/animaliak/xagua.png"),

                                new BeteGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?", "Jirafa", "Animaliak 2", "Animals 2",
                                                "Animales 2", "A2",
                                                "/img/animaliak/jirafa.png"),

                                new BeteGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?", "Kokodriloa", "Animaliak 2", "Animals 2",
                                                "Animales 2", "A2",
                                                "/img/animaliak/cocodrilo.png"),

                                new BeteGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?", "Izurdea", "Animaliak 2", "Animals 2",
                                                "Animales 2", "A2",
                                                "/img/animaliak/delfin.png"),

                                new BeteGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?", "Oiloa", "Animaliak 2", "Animals 2",
                                                "Animales 2", "A2",
                                                "/img/animaliak/gallina.png"),

                                new BeteGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?", "Ardia", "Animaliak 2", "Animals 2",
                                                "Animales 2", "A2",
                                                "/img/animaliak/oveja.png"),

                                new BeteGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?", "Ahatea", "Animaliak 2", "Animals 2",
                                                "Animales 2", "A2",
                                                "/img/animaliak/pato.png"),

                                new BeteGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?", "Txoria", "Animaliak 2", "Animals 2",
                                                "Animales 2", "A2",
                                                "/img/animaliak/pajaro.png"),

                                new BeteGaldera("Zer animalia da hau?", "What animal is this?",
                                                "¿Que animal es este?", "Sugea", "Animaliak 2", "Animals 2",
                                                "Animales 2", "A2",
                                                "/img/animaliak/sugea.png"),

                                /*******************************************************************************
                                 *                                                                             *
                                 *                                 KOLOREAK                                    *
                                 *                                                                             *
                                 ******************************************************************************/

                                new BeteGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?", "Urdina", "Koloreak 2", "Colours 2",
                                                "Colores 2", "A2",
                                                "/img/koloreak/blue.png"),

                                new BeteGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?", "Gorria", "Koloreak 2", "Colours 2",
                                                "Colores 2", "A2",
                                                "/img/koloreak/red.png"),

                                new BeteGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?", "Morea", "Koloreak 2", "Colours 2", "Colores 2",
                                                "A2",
                                                "/img/koloreak/morea.png"),

                                new BeteGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?", "Berdea", "Koloreak 2", "Colours 2",
                                                "Colores 2", "A2",
                                                "/img/koloreak/green.png"),

                                new BeteGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?", "Arrosa", "Koloreak 2", "Colours 2",
                                                "Colores 2", "A2",
                                                "/img/koloreak/pink.png"),

                                new BeteGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?", "Grisa", "Koloreak 2", "Colours 2", "Colores 2",
                                                "A2",
                                                "/img/koloreak/gris.png"),

                                new BeteGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?", "Zuria", "Koloreak 2", "Colours 2", "Colores 2",
                                                "A2",
                                                "/img/koloreak/zuria.png"),

                                new BeteGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?", "Horia", "Koloreak 2", "Colours 2", "Colores 2",
                                                "A2",
                                                "/img/koloreak/horia.png"),

                                new BeteGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?", "Laranja", "Koloreak 2", "Colours 2",
                                                "Colores 2", "A2",
                                                "/img/koloreak/laranja.png"),

                                new BeteGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?", "Marroia", "Koloreak 2", "Colours 2",
                                                "Colores 2", "A2",
                                                "/img/koloreak/brown.png"),

                                new BeteGaldera("Zer kolorea da hau?", "What colour is this?",
                                                "¿Qué color es este?", "Beltza", "Koloreak 2", "Colours 2",
                                                "Colores 2", "A2",
                                                "/img/koloreak/black.png"),

                                /*******************************************************************************
                                 *                                                                             *
                                 *                                ZENBAKIAK                                    *
                                 *                                                                             *
                                 ******************************************************************************/

                                new BeteGaldera("Zenbaki hau zer da?", "What number is this?",
                                                "¿Qué número es este?", "Lau", "Zenbakiak 2", "Numbers 2", "Numeros 2",
                                                "A2",
                                                "/img/zenbakiak/cuatro.png"),

                                new BeteGaldera("Zenbaki hau zer da?", "What number is this?",
                                                "¿Qué número es este?", "Zero", "Zenbakiak 2", "Numbers 2", "Numeros 2",
                                                "A2",
                                                "/img/zenbakiak/zero.png"),

                                new BeteGaldera("Zenbaki hau zer da?", "What number is this?",
                                                "¿Qué número es este?", "Bi", "Zenbakiak 2", "Numbers 2", "Numeros 2",
                                                "A2",
                                                "/img/zenbakiak/dos.png"),

                                new BeteGaldera("Zenbaki hau zer da?", "What number is this?",
                                                "¿Qué número es este?", "Zazpi", "Zenbakiak 2", "Numbers 2",
                                                "Numeros 2", "A2",
                                                "/img/zenbakiak/siete.png"),

                                new BeteGaldera("Zenbaki hau zer da?", "What number is this?",
                                                "¿Qué número es este?", "Bederatzi", "Zenbakiak 2", "Numbers 2",
                                                "Numeros 2", "A2",
                                                "/img/zenbakiak/nueve.png"),

                                new BeteGaldera("Zenbaki hau zer da?", "What number is this?",
                                                "¿Qué número es este?", "Bost", "Zenbakiak 2", "Numbers 2", "Numeros 2",
                                                "A2",
                                                "/img/zenbakiak/cinco.png"),

                                new BeteGaldera("Zenbaki hau zer da?", "What number is this?",
                                                "¿Qué número es este?", "Zortzi", "Zenbakiak 2", "Numbers 2",
                                                "Numeros 2", "A2",
                                                "/img/zenbakiak/ocho.png"),

                                new BeteGaldera("Zenbaki hau zer da?", "What number is this?",
                                                "¿Qué número es este?", "Sei", "Zenbakiak 2", "Numbers 2", "Numeros 2",
                                                "A2",
                                                "/img/zenbakiak/seis.png"),

                                new BeteGaldera("Zenbaki hau zer da?", "What number is this?",
                                                "¿Qué número es este?", "Hamar", "Zenbakiak 2", "Numbers 2",
                                                "Numeros 2", "A2",
                                                "/img/zenbakiak/diez.png"),

                                new BeteGaldera("Zenbaki hau zer da?", "What number is this?",
                                                "¿Qué número es este?", "Bat", "Zenbakiak 2", "Numbers 2", "Numeros 2",
                                                "A2",
                                                "/img/zenbakiak/uno.png"),

                                /*******************************************************************************
                                 *                                                                             *
                                 *                                 Gauzak                                      *
                                 *                                                                             *
                                 ******************************************************************************/

                                new BeteGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?", "Aulkia", "Gauzak 2", "Things 2", "Cosas 2",
                                                "A2",
                                                "/img/gauzak/silla.png"),

                                new BeteGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?", "Mahaia", "Gauzak 2", "Things 2", "Cosas 2",
                                                "A2",
                                                "/img/gauzak/mesa.png"),

                                new BeteGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?", "Liburua", "Gauzak 2", "Things 2", "Cosas 2",
                                                "A2",
                                                "/img/gauzak/libro.png"),

                                new BeteGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?", "Telebista", "Gauzak 2", "Things 2", "Cosas 2",
                                                "A2",
                                                "/img/gauzak/tele.png"),

                                new BeteGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?", "Ohea", "Gauzak 2", "Things 2", "Cosas 2", "A2",
                                                "/img/gauzak/cama.png"),

                                new BeteGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?", "Atea", "Gauzak 2", "Things 2", "Cosas 2", "A2",
                                                "/img/gauzak/puerta.png"),

                                new BeteGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?", "Erlojua", "Gauzak 2", "Things 2", "Cosas 2",
                                                "A2",
                                                "/img/gauzak/reloj.png"),

                                new BeteGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?", "Giltza", "Gauzak 2", "Things 2", "Cosas 2",
                                                "A2",
                                                "/img/gauzak/llave.png"),

                                new BeteGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?", "Telefonoa", "Gauzak 2", "Things 2", "Cosas 2",
                                                "A2",
                                                "/img/gauzak/phone.png"),

                                new BeteGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?", "Ispilua", "Gauzak 2", "Things 2", "Cosas 2",
                                                "A2",
                                                "/img/gauzak/espejo.png"),

                                new BeteGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?", "Arkatza", "Gauzak 2", "Things 2", "Cosas 2",
                                                "A2",
                                                "/img/gauzak/lapiz.png"),

                                new BeteGaldera("Zer gauza da hau?", "What thing is this?",
                                                "¿Qué cosa es esta?", "Lehioa", "Gauzak 2", "Things 2", "Cosas 2",
                                                "A2",
                                                "/img/gauzak/ventana.png"));
        }

        /**
         * Maila eta kategoriaren arabera galderak filtratu eta nahasi
         * @param kategoria galderen kategoria
         * @param maila galderen maila
         * @return itzultzen dena, kasu honetan arraylist bat
         */
        public static List<BeteGaldera> getGaldera(String kategoria, String maila) {
                List<BeteGaldera> galderakFiltratuak = new ArrayList<>();

                // Galderak maila eta kategoriagatik filtratu
                for (BeteGaldera galdera : galderaBete) {
                        if ((galdera.getKategoriaEus().equals(kategoria) || galdera.getKategoriaIng().equals(kategoria))
                                        && galdera.getMaila().equals(maila)) {
                                galderakFiltratuak.add(galdera);
                        }
                }

                Collections.shuffle(galderakFiltratuak); // Galderak nahastu
                return galderakFiltratuak.size() > 12 ? galderakFiltratuak.subList(0, 10) : galderakFiltratuak;

        }

        public static List<KonpresioGaldera> konpresioGaldera;

        // 3 galdera eta 3 erantzun bakarrik jar ditzakezu

        // galderakEtaErantzunak[][]
        // Galdera: [0][x]
        // Erantzun zuzena: [1][x]

        // erantzunak[][]
        // Erantzunen taldea 1: [0][x]
        // Erantzunen taldea 2: [1][x]
        // Erantzunen taldea 3: [2][x]

        static {
                konpresioGaldera = Arrays.asList(
                                /*******************************************************************************
                                 *                                                                             *
                                 *                                 ANIMALIAK                                   *
                                 *                                                                             *
                                 ******************************************************************************/
                                new KonpresioGaldera(
                                                "Munduan animalia mota asko daude. Batzuk etxean bizi dira, hala nola txakurrak eta katuak, gure lagun onenak izanik. Beste batzuk basoan edo itsasoan bizi dira. Adibidez, hartzak mendietan bizi dira eta arrainak itsasoan igeri egiten dute. Txoriak hegan egiten dute eta zeruan askatasunez mugitzen dira. Animalia bakoitzak bere ezaugarriak eta bizimodua ditu, eta natura aberatsa egiten dute.",
                                                new String[][] { { "Zein animalia bizi da mendietan?",
                                                                "Zer egiten dute txoriek?",
                                                                "Zein animalia da ohikoa etxean bizitzeko?" },
                                                                { "Hartza", "Hegan egin", "Katua" } },
                                                new String[][] { { "Txakurra", "Hartza", "Arraina", "Katua" },
                                                                { "Igeri egin", "Hegan egin", "Lurrikara egin",
                                                                                "Kosk egin" },
                                                                { "Marrazoa", "Katua", "Hartza", "Dortoka" } },
                                                "Animaliak 3", "Animals 3",
                                                "Animales 3", "B1"),
                                /*******************************************************************************
                                 *                                                                             *
                                 *                                 KOLOREAK                                    *
                                 *                                                                             *
                                 ******************************************************************************/
                                new KonpresioGaldera(
                                                "Mundua kolorez beteta dago. Eguzkia horia da eta zerua urdina. Belarra berdea da eta loretan kolore askotako petaloak aurki ditzakegu: gorriak, horiak, arrosak... Arku-izurria agertzen denean, kolore guztiak batera ikusten dira: gorria, laranja, horia, berdea, urdina, indigoa eta morea. Koloreek sentimenduak adierazten dituzte; adibidez, gorria indarra eta maitasuna da, eta urdina lasaitasuna.",
                                                new String[][] { { "Zer kolorekoa da eguzkia?",
                                                                "Zer kolorekoa da belarra?",
                                                                "Zein kolore adierazten du lasaitasuna?" },
                                                                { "Horia", "Berdea", "Urdina" } },
                                                new String[][] { { "Urdina", "Horia", "Gorria", "Berdea" },
                                                                { "Arrosa", "Urdina", "Berdea", "Laranja" },
                                                                { "Urdina", "Gorria", "Horia", "Arrosa" } },
                                                "Koloreak 3", "Colours 3",
                                                "Colores 3", "B1"),
                                /*******************************************************************************
                                 *                                                                             *
                                 *                                ZENBAKIAK                                    *
                                 *                                                                             *
                                 ******************************************************************************/
                                new KonpresioGaldera(
                                                "Zenbakiak gure eguneroko bizitzan oso garrantzitsuak dira. Egunean zehar hainbat aldiz erabiltzen ditugu: ordutegiak jakiteko, erosketak egiteko edo telefono zenbakiak markatzeko. Astean zazpi egun daude: astelehena, asteartea, asteazkena, osteguna, ostirala, larunbata eta igandea. Eskuan bost atzamar ditugu, eta urteak hamabi hilabete ditu. Zenbakiek kontatzen, neurtzen eta antolatzen laguntzen digute.",
                                                new String[][] { { "Zenbat egun daude aste batean?",
                                                                "Zenbat atzamar ditugu esku batean?",
                                                                "Zenbat hilabete ditu urte batek?" },
                                                                { "Zazpi", "Bost", "Hamabi" } },
                                                new String[][] { { "Lau", "Zazpi", "Bost", "Hamar" },
                                                                { "Hiru", "Bost", "Sei", "Zortzi" },
                                                                { "Bost", "Hamabi", "Zazpi", "Hamaika" } },
                                                "Zenbakiak 3", "Numbers 3", "Numeros 3", "B1"),
                                /*******************************************************************************
                                 *                                                                             *
                                 *                                 Gauzak                                      *
                                 *                                                                             *
                                 ******************************************************************************/

                                new KonpresioGaldera(
                                                "Gaur egungo eguneroko bizitzan hainbat objektu erabiltzen ditugu. Goizean, iratzargailuak esnatzen gaitu. Gosaltzeko, katilu batean esnea edo kafea hartzen dugu. Eskolara edo lanera joateko, motxila erabiltzen dugu beharrezko materialekin. Eguraldi euritsua bada, aterkia lagungarria da busti ez gaitezen. Arratsaldean, etxean lasai egoteko, liburu bat irakurri edo telebista ikus dezakegu. Objektu txiki eta sinpleek gure egunerokoa errazten dute.",
                                                new String[][] { { "Zein objektu erabiltzen dugu goizean esnatzeko?",
                                                                "Zer eramaten dugu eskolara edo lanera?",
                                                                "Zein objektu da erabilgarria euria egiten duenean?" },
                                                                { "Iratzargailua", "Motxila", "Aterkia" } },
                                                new String[][] { { "Aterkia", "Katilua", "Iratzargailua", "Motxila" },
                                                                { "Liburua", "Aterkia", "Motxila", "Katilua" },
                                                                { "Aterkia", "Iratzargailua", "Liburua", "Motxila" } },
                                                "Gauzak 3", "Things 3",
                                                "Cosas 3", "B1"));
        }
}
