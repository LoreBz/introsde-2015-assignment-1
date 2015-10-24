/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package people;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.time.format.DateTimeFormatter;
import java.io.File;

/**
 * this class is used to simply randomly combine some predefined names, lastnames etc
 * in order to create an xml db of people
 * @author Lorenzo
 */
public class XMLPeopleDBGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        // TODO code application logic here
        ArrayList<String> names = new ArrayList<>();
        names.add("Lorenzo");
        names.add("Alberto");
        names.add("Fabio");
        names.add("Erica");
        names.add("Luca");
        names.add("Alessandro");
        names.add("Maria Luisa");
        names.add("Verena");
        names.add("Marco");
        names.add("Giuseppe");
        names.add("Francesca");
        names.add("Aldo");
        names.add("Carlo");
        names.add("Laura");
        names.add("Linda");
        names.add("Elena");
        names.add("Stefano");
        names.add("Valeria");
        names.add("Giuliano");
        names.add("Matteo");

        ArrayList<String> lastnames = new ArrayList<>();
        lastnames.add("Battan");
        lastnames.add("Carpinelli");
        lastnames.add("Davì");
        lastnames.add("Fallucca");
        lastnames.add("Fedrizzi");
        lastnames.add("Gatti");
        lastnames.add("Ghiro");
        lastnames.add("Losci");
        lastnames.add("Passadore");
        lastnames.add("Roilo");
        lastnames.add("Procida");
        lastnames.add("Perrone");
        lastnames.add("Sorze");
        lastnames.add("Castellani");
        lastnames.add("Peiti");
        lastnames.add("Santoro");
        lastnames.add("Cremonini");
        lastnames.add("Garbin");
        lastnames.add("Palandri");
        lastnames.add("Casarano");

        String filename="people.xml";
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        writer.println("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>\n"
                + "<people>");

        for (int i = 1; i <= 20; i++) {
            String out = "";
            if (i < 10) {
                out += "\t<person id=\"000" + i + "\">\n";
            } else {
                out += "\t<person id=\"00" + i + "\">\n";
            }
            Collections.shuffle(names);
            String name = names.get(0);
            names.remove(0);
            out += "\t\t<firstname>" + name + "</firstname>\n";

            Collections.shuffle(lastnames);
            String lastname = lastnames.get(0);
            lastnames.remove(0);
            out += "\t\t<lastname>" + lastname + "</lastname>\n";

            out += "\t\t<birthdate>" + randomDate(1970, 39) + "</birthdate>\n";

            out += "\t\t<healthprofile>\n";
            out += "\t\t\t<lastupdate>" + randomDate(2010, 4) + "</lastupdate>\n";
            int weight = new Random().nextInt(70) + 40;
            out += "\t\t\t<weight>" + weight + "</weight>\n";
            int heigth = new Random().nextInt(60) + 140;
            out += "\t\t\t<height>" + (new BigDecimal(heigth/100.0).setScale(2, BigDecimal.ROUND_HALF_UP)) + "</height>\n";

            BigDecimal bd = new BigDecimal(weight * 1.0 / (heigth/100.0 * heigth/100.0));
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            out += "\t\t\t<bmi>" + bd + "</bmi>\n";
            out += "\t\t</healthprofile>\n";
            out += "\t</person>\n";
            writer.print(out);
        }
        writer.println("</people>\n");
        writer.close();
        File f=new File(filename);
        System.out.println(filename+" output in "+f.getAbsolutePath());
    }

    private static String randomDate(int minYear, int range) {
        int randomYear = new Random().nextInt(range) + minYear;
        int dayOfMonth = new Random().nextInt(28) + 1;
        int month = new Random().nextInt(12) + 1;
        int hour = new Random().nextInt(23) + 1;
        int minutes = new Random().nextInt(59);
        LocalDateTime date = LocalDateTime.of(randomYear, month, dayOfMonth, hour, minutes);
        ZonedDateTime z = ZonedDateTime.of(date, ZoneId.systemDefault());
        String text = z.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return text;
    }

}
