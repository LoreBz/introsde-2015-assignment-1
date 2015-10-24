package people;

import people.generated.*;

import javax.xml.bind.*;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;

import org.xml.sax.SAXException;

import java.io.*;
import java.util.List;

public class JAXBUnMarshaller {

     /*
    / with the use of JAXB (Java Architecture for XML Binding) we unmarshall
    / a previously written xml file containing some information related to
    / persons. In the xml file, persons are represented with elements attributes etc
    / and by unmarshalling the file we instantiate some Java objects that represent
    / the same info. We could do this because we could be asked to do some elaboration
    / on data that are sent on the web in xml format and it is convenient to do the
    / elaboration with Java.
    */

    public void unMarshall(File xmlDocument) {
        try {

            // JAXB need to know how to map xml elements attribute etc into
            // object of certain classes. To do this it requires the annotations
            // already used for the people.JAXBMarshaller and available in the .java
            // files that are located in people.generated folder
            JAXBContext jaxbContext = JAXBContext
                    .newInstance("people.generated");

            Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
            SchemaFactory schemaFactory = SchemaFactory
                    .newInstance("http://www.w3.org/2001/XMLSchema");
            //schema to use to perform unmarshalling
            Schema schema = schemaFactory.newSchema(new File("people.xsd"));
            unMarshaller.setSchema(schema);
            CustomValidationEventHandler validationEventHandler = new CustomValidationEventHandler();
            unMarshaller.setEventHandler(validationEventHandler);

            //by executing the following line of code the peopleElement variable should be
            //a list of java objects instance of PeopleType and therefore it become possible
            //to perform some elaboration with Java on these objects
            @SuppressWarnings("unchecked")
            JAXBElement<PeopleType> peopleElement = (JAXBElement<PeopleType>) unMarshaller
                    .unmarshal(xmlDocument);

            PeopleType people = peopleElement.getValue();
            //for instance we perform the most stupid elabration...we just print
            //info related to the "unmarshalled persons"
            //NB: we are not priting the content of the xml file (from which we
            //got the info) with a file reader, but we are using the unmarshalled 
            //java objects and java code to do this
            for (PersonType p : people.getPerson()) {
                System.out.println(p.getFirstname() + " " + p.getLastname()
                        + "\nborn in " + p.getBirthdate());
            }

        } catch (JAXBException e) {
            System.out.println(e.toString());
        } catch (SAXException e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] argv) {
        File xmlDocument = new File("jaxb_people.xml");
        JAXBUnMarshaller jaxbUnmarshaller = new JAXBUnMarshaller();
        jaxbUnmarshaller.unMarshall(xmlDocument);
    }

    class CustomValidationEventHandler implements ValidationEventHandler {

        public boolean handleEvent(ValidationEvent event) {
            if (event.getSeverity() == ValidationEvent.WARNING) {
                return true;
            }
            if ((event.getSeverity() == ValidationEvent.ERROR)
                    || (event.getSeverity() == ValidationEvent.FATAL_ERROR)) {

                System.out.println("Validation Error:" + event.getMessage());

                ValidationEventLocator locator = event.getLocator();
                System.out.println("at line number:" + locator.getLineNumber());
                System.out.println("Unmarshalling Terminated");
                return false;
            }
            return true;
        }

    }
}
