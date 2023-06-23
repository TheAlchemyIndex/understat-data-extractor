package org.tai.ude.understat.util;

public class PlayerNameFormatter {

    public static String formatName(String name) {

        if (name.contains("Thiago Alc")) {
            name = "Thiago Alcantara";
        }

        return switch (name) {
            case "Ahmed Hegazy" -> "Ahmed Hegazi";
            case "Aleksandar Mitrovic" -> "Aleksandar Mitrović";
            case "Ãlex Moreno" -> "Álex Moreno";
            case "Amad Diallo Traore" -> "Amad Diallo";
            case "Angelino" -> "Angeliño";
            case "Armel Bella Kotchap" -> "Armel Bella-Kotchap";
            case "Arnaut Danjuma Groeneveld" -> "Arnaut Danjuma";
            case "Asmir Begovic" -> "Asmir Begović";
            case "Benoit Badiashile Mukinayi" -> "Benoît Badiashile";
            case "Boubacar Traore" -> "Boubacar Traoré";
            case "Boubakary Soumare" -> "Boubakary Soumaré";
            case "Bruno Jordao" -> "Bruno Jordão";
            case "Carlos Vinicius" -> "Carlos Vinícius";
            case "Caglar Söyüncü" -> "Çaglar Söyüncü";
            case "Cheick Oumar Doucoure" -> "Cheick Doucouré";
            case "Chicharito" -> "Javier Hernández";
            case "Clement Lenglet" -> "Clément Lenglet";
            case "Daniel N&#039;Lundulu" -> "Daniel N'Lundulu";
            case "Dara O&#039;Shea" -> "Dara O'Shea";
            case "Djibril Sidibe" -> "Djibril Sidibé";
            case "Emile Smith-Rowe" -> "Emile Smith Rowe";
            case "Emiliano Martinez" -> "Emiliano Martínez";
            case "Estupiñán" -> "Pervis Estupiñán";
            case "Fabio Carvalho" -> "Fábio Carvalho";
            case "Ferrán Torres" -> "Ferran Torres";
            case "Franck Zambo" -> "André-Frank Zambo Anguissa";
            case "Frederic Guilbert" -> "Frédéric Guilbert";
            case "Halil Dervisoglu" -> "Halil Dervişoğlu";
            case "Hamed Junior Traore" -> "Hamed Traorè";
            case "Hee-Chan Hwang" -> "Hwang Hee-chan";
            case "Imran Louza" -> "Imrân Louza";
            case "Ismaila Sarr" -> "Ismaïla Sarr";
            case "Ivan Perisic" -> "Ivan Perišić";
            case "Jack O&#039;Connell" -> "Jack O'Connell";
            case "Júnior Firpo" -> "Junior Firpo";
            case "Kepa" -> "Kepa Arrizabalaga";
            case "Lewis O&#039;Brien" -> "Lewis O'Brien";
            case "Marc Guehi" -> "Marc Guéhi";
            case "Martin Odegaard" -> "Martin Ødegaard";
            case "Matias Viña" -> "Matías Viña";
            case "Matthew Cash" -> "Matty Cash";
            case "Moussa Niakhate" -> "Moussa Niakhaté";
            case "Muhamed Besic" -> "Muhamed Bešić";
            case "Naif Aguerd" -> "Nayef Aguerd";
            case "N&#039;Golo Kanté" -> "N'Golo Kanté";
            case "Nicolas N&#039;Koulou" -> "Nicolas Nkoulou";
            case "Nicolas Pepe" -> "Nicolas Pépé";
            case "Pape Sarr" -> "Pape Matar Sarr";
            case "Raphael Varane" -> "Raphaël Varane";
            case "Rayan Ait Nouri" -> "Rayan Aït-Nouri";
            case "Romeo Lavia" -> "Roméo Lavia";
            case "Romain Saiss" -> "Romain Saïss";
            case "Said Benrahma" -> "Saïd Benrahma";
            case "Sergi Canos" -> "Sergi Canós";
            case "Tanguy NDombele Alvaro" -> "Tanguy Ndombele";
            case "Thiago AlcÃ¡ntara", "Thiago Alcântara", "Thiago AlcÃ¢ntara" -> "Thiago Alcantara";
            case "Trincão" -> "Francisco Trincão";
            case "Valentino Livramento" -> "Tino Livramento";
            case "Zanka" -> "Mathias Jørgensen";
            default -> name;
        };
    }
}
