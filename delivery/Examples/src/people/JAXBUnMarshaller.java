package people;

import people.generated.*;

import javax.xml.bind.*;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;

import org.xml.sax.SAXException;

import java.io.*;
import java.util.List;

public class JAXBUnMarshaller {

    public void unMarshall(File xmlDocument) {
        try {

            JAXBContext jaxbContext = JAXBContext
                    .newInstance("people.generated");

            Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
            SchemaFactory schemaFactory = SchemaFactory
                    .newInstance("http://www.w3.org/2001/XMLSchema");
            Schema schema = schemaFactory.newSchema(new File("people.xsd"));
            unMarshaller.setSchema(schema);
            CustomValidationEventHandler validationEventHandler = new CustomValidationEventHandler();
            unMarshaller.setEventHandler(validationEventHandler);

            @SuppressWarnings("unchecked")
            JAXBElement<PeopleType> peopleElement = (JAXBElement<PeopleType>) unMarshaller
                    .unmarshal(xmlDocument);

            PeopleType people = peopleElement.getValue();

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
