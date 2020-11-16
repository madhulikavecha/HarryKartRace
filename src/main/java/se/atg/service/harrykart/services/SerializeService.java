package se.atg.service.harrykart.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.xml.sax.SAXException;
import se.atg.service.harrykart.exception.HarryKartException;
import se.atg.service.harrykart.model.HarryKart;
import se.atg.service.harrykart.model.Rank;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.List;

/**
 * Class to handle the conversion of Harry Kart races from XML to POJO to JSON
 *
 * @author Sudha Madhulika
 * @version 1.0
 * @date 15/11/2020
 */
public class SerializeService {

    private static final String SCHEMA_FILE = "input.xsd";
    private XmlMapper xmlMapper;
    private static final String INPUT_SCHEMA ="input.xsd";

    public SerializeService(){
        xmlMapper = new XmlMapper();
    }

    /**
     * Checking the input xml is valid
     * @param xml XML representing a Harry Kart race
     *
     */
    private boolean isValidXml(String xml) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try{
            Schema schema =schemaFactory.newSchema(new File(getResource(SCHEMA_FILE)));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xml)));
            return true;
        } catch (IOException | SAXException e) {
            return false;
        }

    }

    private String getResource(String schemaFileName) {
        URL resource = getClass().getClassLoader().getResource(schemaFileName);
        return resource.getFile();

    }

    private boolean isValidLoops(HarryKart harryKart) {
        return harryKart.getNumberOfLoops()==harryKart.getPowerUps().size()+1;
    }

    /**
     * Map Harry Cart Race XML to a HarryKart object
     * @param xml XML representing a Harry Kart race
     * @return HarryKart    Java representation of a Harry Kart race
     * @throws HarryKartException
     */
    public HarryKart deserializeFromXml(String xml) throws HarryKartException {
        HarryKart harryKart =null;
        // Before de-serializing, check the XML validity
        if(!this.isValidXml(xml)){
            throw new HarryKartException("Input XML format is not valid");
        }
        try{
             harryKart = xmlMapper.readValue(xml,HarryKart.class);
              /*
                After de-serializing the XML:
                 1) Verify that the number of loops matches the specified value
                 2) Verify that there are at least three race participants
             */
            if(!this.isValidLoops(harryKart)){
                throw new HarryKartException("<Number of loops> value in input XML does match with number of loops provided");
            }
            if(harryKart.getStartList().size()<3){
                throw  new HarryKartException("Harry Kart race cannot have less than 3 participants.");
            }
        }catch (IOException e) {
            System.out.println("IOException while trying to de-serialize input XML");
            System.out.println(e);
        }
        return harryKart;
    }



    /**
     * Converts a collection of ranked participants to JSON format
     * @param rankList Collection of race participants ordered by finish time
     * @return  String  JSON representation of the first-, second- and third-place ranking
     */
    public String serializeToJson(List<Rank> rankList){
        ObjectMapper mapper =new ObjectMapper();
        String jsonRank ="[]";
        try{
            jsonRank = mapper.writeValueAsString(rankList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{ Error  during json parsing :"+e.getMessage()+"}";
        }
        return "{\"ranking\": " + jsonRank + "}";
    }
}
