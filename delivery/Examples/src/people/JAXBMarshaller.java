package people;

import javax.xml.bind.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import people.generated.HealthprofileType;
import people.generated.PeopleType;
import people.generated.PersonType;

public class JAXBMarshaller {

    /*
    / with the use of JAXB (Java Architecture for XML Binding) we marshall some
    / java instances of Person generating an XML document. This document records
    / the info related to those specific persons created with Java. This "marshalling"
    / operation could be useful to transfer data at a networking level or to store
    / persistently information about tha Java-handeled persons
    */
    public void generateXMLDocument(File xmlDocument) throws DatatypeConfigurationException {
        try {
            //JAXB works only if it can understand how to marshal
            //tha Java objects it has to work with.
            //That's why we should specify in which context the JAXB can work.
            //For instance the context is a folder where xjc has written some java classes
            //These classes have annotations inside, that are used by the marshaller
            //to map specific info into xml elements or attributes etc.
            //The mapping (defined by the annotations present in the java classes) is
            //governed by an xml schema (XSD), in the present case by people.xsd
            JAXBContext jaxbContext = JAXBContext
                    .newInstance("people.generated");//people.generated is the context folder
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", new Boolean(true));
            people.generated.ObjectFactory factory = new people.generated.ObjectFactory();

            //xjc produces also an ObjectFactory that should be used to create marshable objects
            //for example if we want to marshal a collection of persons, i.e. a people element,
            //we should create a PeopleType object invoking the ObjectFactory
            PeopleType people = factory.createPeopleType();

            //Then we create some other marshable objects
            HealthprofileType hp1 = factory.createHealthprofileType();
            GregorianCalendar gregorianCalendar = GregorianCalendar.from(randomDate(2010, 4));
            XMLGregorianCalendar xmldate1 = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            hp1.setLastupdate(xmldate1);
            hp1.setWeight(74);
            hp1.setHeight(new Float(1.81));
            hp1.setBmi(new Float(74 * 1.0 / (1.81 * 1.81)));

            HealthprofileType hp2 = factory.createHealthprofileType();
            gregorianCalendar = GregorianCalendar.from(randomDate(2010, 4));
            XMLGregorianCalendar xmldate2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            hp2.setLastupdate(xmldate2);
            hp2.setWeight(102);
            hp2.setHeight(new Float(1.84));
            hp2.setBmi(new Float(102 * 1.0 / (1.84 * 1.84)));

            HealthprofileType hp3 = factory.createHealthprofileType();
            gregorianCalendar = GregorianCalendar.from(randomDate(2010, 4));
            XMLGregorianCalendar xmldate3 = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            hp3.setLastupdate(xmldate3);
            hp3.setWeight(42);
            hp3.setHeight(new Float(1.75));
            hp3.setBmi(new Float(42 * 1.0 / (1.75 * 1.75)));

            PersonType piero = factory.createPersonType();
            piero.setId(new Long(1));
            piero.setFirstname("Piero");
            piero.setLastname("Angela");
            gregorianCalendar = GregorianCalendar.from(randomDate(1970, 39));
            XMLGregorianCalendar xmldate4 = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            piero.setBirthdate(xmldate4);
            piero.setHealthprofile(hp1);

            PersonType fdomino = factory.createPersonType();
            fdomino.setId(new Long(2));
            fdomino.setFirstname("Fats");
            fdomino.setLastname("Domino");
            gregorianCalendar = GregorianCalendar.from(randomDate(1970, 39));
            XMLGregorianCalendar xmldate5 = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            fdomino.setBirthdate(xmldate5);
            fdomino.setHealthprofile(hp2);

            PersonType olivia = factory.createPersonType();
            olivia.setId(new Long(3));
            olivia.setFirstname("Olivia");
            olivia.setLastname("Oyl");
            gregorianCalendar = GregorianCalendar.from(randomDate(1970, 39));
            XMLGregorianCalendar xmldate6 = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            olivia.setBirthdate(xmldate6);
            olivia.setHealthprofile(hp3);

            people.getPerson().add(piero);
            people.getPerson().add(fdomino);
            people.getPerson().add(olivia);

            //finally we put all toghether and we effectively execute the marshaller
            JAXBElement<PeopleType> peopleElement = factory
                    .createPeople(people);
            marshaller.marshal(peopleElement,
                    new FileOutputStream(xmlDocument));//output to file
            marshaller.marshal(peopleElement,
                   System.out);//output to terminal

        } catch (IOException e) {
            System.out.println(e.toString());

        } catch (JAXBException e) {
            System.out.println(e.toString());

        }
        System.out.println("xmlDocument output in: " + xmlDocument.getAbsolutePath());
    }

    public static void main(String[] argv) throws DatatypeConfigurationException {
        String xmlDocument = "jaxb_people.xml";
        JAXBMarshaller jaxbMarshaller = new JAXBMarshaller();
        jaxbMarshaller.generateXMLDocument(new File(xmlDocument));
    }

    //to get a random date compatible with XML proper way of representing dates and times
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
