package people;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import people.generated.HealthprofileType;
import people.generated.PersonType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import java.io.File;
import java.io.IOException;

public class PeopleJSON {

    private static List<PersonType> people;

    private void initializePeople() throws DatatypeConfigurationException {
        people = new ArrayList<>();

        PersonType piero = new PersonType();
        piero.setId(new Long(1));
        piero.setFirstname("Piero");
        piero.setLastname("Angela");

        GregorianCalendar gregorianCalendar = GregorianCalendar.from(randomDate(1970, 39));
        XMLGregorianCalendar xmldate1 = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        piero.setBirthdate(xmldate1);

        HealthprofileType hp = new HealthprofileType();
        gregorianCalendar = GregorianCalendar.from(randomDate(2010, 4));
        XMLGregorianCalendar xmldate2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        hp.setLastupdate(xmldate2);
        hp.setWeight(74);
        hp.setHeight(new Float(1.80));
        hp.setBmi(new Float(74 * 1.0 / (1.80 * 1.80)));
        piero.setHealthprofile(hp);

        PersonType pinco = new PersonType();
        pinco.setFirstname("Pinco");
        pinco.setLastname("Pallino");
        pinco.setId(new Long(2));

        people.add(piero);
        people.add(pinco);
    }

    public static void main(String[] args) throws /*DatatypeConfigurationException, IOException, JsonProcessingException*/Exception {
        PeopleJSON test = new PeopleJSON(); 
        // put some person in our logical java db of people
        test.initializePeople();
        // Jackson Object Mapper
        ObjectMapper mapper = new ObjectMapper();

        // Adding the Jackson Module to process JAXB annotations
        JaxbAnnotationModule module = new JaxbAnnotationModule();

        // configure as necessary
        mapper.registerModule(module);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        //thanks to Jackson module we are able to marshal our list of person into JSON
        String result = mapper.writeValueAsString(people);
        System.out.println(result);
        mapper.writeValue(new File("people.json"), people);
    }

    private static ZonedDateTime randomDate(int minYear, int range) {
        int randomYear = new Random().nextInt(range) + minYear;
        int dayOfMonth = new Random().nextInt(28) + 1;
        int month = new Random().nextInt(12) + 1;
        int hour = new Random().nextInt(23) + 1;
        int minutes = new Random().nextInt(59);
        LocalDateTime date = LocalDateTime.of(randomYear, month, dayOfMonth, hour, minutes);
        ZonedDateTime z = ZonedDateTime.of(date, ZoneId.systemDefault());
        String text = z.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return z;
    }

}
