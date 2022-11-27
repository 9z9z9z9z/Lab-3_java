package model;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;


public class JaxbWorker{
    // Restoring object from Jaxb
    protected static WashingMachine fromXMLToObj(String PATH) throws JAXBException {
        try {
            // Creating enter_point to JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(WashingMachine.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();
            return (WashingMachine)un.unmarshal(new File(PATH));
        } catch (JAXBException Jex){
            Jex.printStackTrace();
        }
        return null;
    }

    // Saving to XML
    protected static void convertToXML(WashingMachine machine, String PATH) throws JAXBException {
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(WashingMachine.class);
            Marshaller marshaller = jaxbContext.createMarshaller();

            // Put the flag for reading from XML to JAXB
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException Jex){
            Jex.printStackTrace();
        }
    }
}

