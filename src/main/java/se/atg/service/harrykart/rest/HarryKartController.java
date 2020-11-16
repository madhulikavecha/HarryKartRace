package se.atg.service.harrykart.rest;

import org.springframework.web.bind.annotation.*;
import se.atg.service.harrykart.exception.HarryKartException;
import se.atg.service.harrykart.model.HarryKart;
import se.atg.service.harrykart.model.Rank;
import se.atg.service.harrykart.services.HarryKartResultService;
import se.atg.service.harrykart.services.SerializeService;

import java.io.IOException;
import java.util.List;

/**
 * Entry-point for the application. API accepts XML POST requests at http://localhost:8080/api/play
 *
 * @author Sudha Madhulika
 * @version 1.0
 * @date 15/11/2020
 */
@RestController
@RequestMapping("/api")
public class HarryKartController {

    /**
     * HTTP Request handler at the /play endpoint. Only accepts POST requests with XML payload and returns JSON
     *
     * @param xml XML representation of a Harry Kart race
     * @return String  JSON representation of the race results top placers
     */

    @RequestMapping(method = RequestMethod.POST, path = "/play", consumes = "application/xml", produces = "application/json")
    public String playHarryKart(@RequestBody String xml){
        SerializeService serializeService = new SerializeService();
        try{
            // De-serialize the XML
            HarryKart harryKart = serializeService.deserializeFromXml(xml);
            // Calculate the race results
            List<Rank> topRankHorses = new HarryKartResultService(harryKart).getHorseRanks();
            // serialize to JSON  return results
            return serializeService.serializeToJson(topRankHorses);

        } catch (HarryKartException e) {
            System.out.println("HarryKart Exception"+e.getMessage());
            return "{\"message\": " + e.getMessage() + " }";
        }


    }

}
