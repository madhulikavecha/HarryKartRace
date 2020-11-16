package se.atg.service.harrykart.rest;


import org.junit.Before;
import org.junit.Test;
import se.atg.service.harrykart.exception.HarryKartException;
import se.atg.service.harrykart.model.HarryKart;
import se.atg.service.harrykart.model.Rank;
import se.atg.service.harrykart.services.HarryKartResultService;
import se.atg.service.harrykart.services.SerializeService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * class to handle JUnit test cases
 *
 * @author Sudha Madhulika
 * @version 1.0
 * @date 15/11/2020
 */
public class HarryKartControllerTest {

    SerializeService serializeService;

    @Before
    public void setUp() {

        serializeService = new SerializeService();
    }

    /**
     * @param xmlFileName XML filename to be read from /resources
     * @return
     */
    private String readXmlToString(String xmlFileName) {
        InputStream in = this.getClass().getResourceAsStream(xmlFileName);
        String xmlString = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            xmlString = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception e) {
            e.printStackTrace();
            return xmlString;
        }
        return xmlString;
    }

    /**
     * Lane 1 is first, Lane 2 is second, Lane 4 is third
     *
     * @throws HarryKartException
     */
    @Test
    public void topResultTest() throws HarryKartException{
        String inputXml;
        inputXml = readXmlToString("/result_test.xml");
        HarryKart hk = serializeService.deserializeFromXml(inputXml);
        List<Rank> actualRankList = new HarryKartResultService(hk).getHorseRanks();
        String actualJson = serializeService.serializeToJson(actualRankList);
        List<Rank> expectedRankList = new ArrayList<Rank>();
        expectedRankList.add(new Rank(1, "TIMETOBELUCKY", 0));
        expectedRankList.add(new Rank(2, "CARGO DOOR", 0));
        expectedRankList.add(new Rank(3, "WAIKIKI SILVIO", 0));
        String expectedJson = serializeService.serializeToJson(expectedRankList);
        assertEquals(actualJson, expectedJson);
    }

    /**
     * Two participants finish at the same time
     *
     * @throws HarryKartException
     */
    @Test
    public void positionTieTest() throws HarryKartException {
        String inputXml;
        inputXml = readXmlToString("/tie_test.xml");
        HarryKart hk = serializeService.deserializeFromXml(inputXml);
        List<Rank> actualRankList = new HarryKartResultService(hk).getHorseRanks();
        String actualJson = serializeService.serializeToJson(actualRankList);
        List<Rank> expectedRankList = new ArrayList<Rank>();
        expectedRankList.add(new Rank(1, "TIMETOBELUCKY", 0));
        expectedRankList.add(new Rank(1, "CARGO DOOR", 0));
        expectedRankList.add(new Rank(2, "HERCULES BOKO", 0));
        expectedRankList.add(new Rank(3, "WAIKIKI SILVIO", 0));
        expectedRankList.add(new Rank(3, "RACE WINNER", 0));
        String expectedJson = serializeService.serializeToJson(expectedRankList);
        assertEquals(actualJson, expectedJson);
    }


    /**
     * All participants finish at the same time
     *
     * @throws HarryKartException
     */
    @Test
    public void sameRankTieTest() throws HarryKartException {
        String inputXml;
        inputXml = readXmlToString("/same_rank_tie_test.xml");
        HarryKart hk = serializeService.deserializeFromXml(inputXml);
        List<Rank> actualRankList = new HarryKartResultService(hk).getHorseRanks();
        actualRankList.forEach(rank -> assertEquals(rank.getPosition(), 1));
    }


    /**
     * Less than 3 participants doesn't make a race (it throws an exception)
     *
     * @throws HarryKartException
     */
    @Test(expected = HarryKartException.class)
    public void minimunParticipants() throws HarryKartException {
        String inputXml;
        inputXml = readXmlToString("/minimum_participants.xml");
        HarryKart hk = serializeService.deserializeFromXml(inputXml);
    }


    /**
     * XML file is not in a valid format: <numberOfLoops> does not match the actual number of loops listed
     *
     * @throws HarryKartException
     */
    @Test(expected = HarryKartException.class)
    public void invalidLoopTest() throws HarryKartException {
        String inputXml;
        inputXml = readXmlToString("/invalid_loops.xml");
        HarryKart hk = serializeService.deserializeFromXml(inputXml);
    }


    /**
     * When the base power is less than 1 on a loop, the participant hasn't completed the lap and is out of the race
     *
     * @throws HarryKartException
     */
    @Test
    public void speedBoundaryTest() throws HarryKartException {
        String inputXml;
        inputXml = readXmlToString("/speed_boundary.xml");
        HarryKart hk = serializeService.deserializeFromXml(inputXml);
        List<Rank> actualRankList = new HarryKartResultService(hk).getHorseRanks();
        String actualJson = serializeService.serializeToJson(actualRankList);
        List<Rank> expectedRankingList = new ArrayList<Rank>();
        expectedRankingList.add(new Rank(1, "TIMETOBELUCKY", 0.0));
        expectedRankingList.add(new Rank(2, "HERCULES BOKO", 0.0));
        expectedRankingList.add(new Rank(3, "WAIKIKI SILVIO", 0.0));
        String expectedJson = serializeService.serializeToJson(expectedRankingList);
        assertEquals(actualJson, expectedJson);
    }


}


