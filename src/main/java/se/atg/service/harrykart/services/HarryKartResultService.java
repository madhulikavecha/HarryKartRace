package se.atg.service.harrykart.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import se.atg.service.harrykart.model.*;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Class to handle Harry Kart race result calculation
 *
 * @author Sudha Madhulika
 * @version 1.0
 * @date 15/11/2020
 */
public class HarryKartResultService {
    private static final double TRACK_LENGTH = 1000.00;
    private static final int MAXIMUM_RANKS = 3;
    private List<Rank> raceRankingList;
    HarryKart race;

    /**
     * The race to be ranked
     *
     * @param race The Harry Kart race to be tabulated
     */
    public HarryKartResultService(HarryKart race) {
        this.race = race;
        raceRankingList = new ArrayList<>();
    }

    /**
     * Calculate the rank of the race participants
     *
     * @return List<Rank>   List of participants ranked by their order of race completion
     */
    public List<Rank> getHorseRanks() {
        ArrayList<Loop> powerUpList = race.getPowerUps();

        race.getStartList().forEach(
                horse -> {
                    Rank rank = new Rank(1, horse.getName(), TRACK_LENGTH / horse.getBaseSpeed());
                    raceRankingList.add(rank);
                }
        );

        race.getStartList().forEach(
                horse -> {
                    powerUpList.stream().forEach(
                            loop -> {
                                loop.getLanes().stream().forEach(
                                        lane -> {
                                            if (lane.getNumber() == horse.getLane()) {
                                                horse.setBaseSpeed(horse.getBaseSpeed() + lane.getPowerValue());
                                                raceRankingList.stream().forEach(
                                                        horseRank -> {
                                                            if (horseRank.getHorseName().equalsIgnoreCase(horse.getName())) {
                                                                if(horse.getBaseSpeed()<=0){
                                                                    horseRank.setRaceTime(horseRank.getRaceTime()+Double.MAX_VALUE);
                                                                }else{
                                                                    horseRank.setRaceTime(horseRank.getRaceTime() + (TRACK_LENGTH / horse.getBaseSpeed()));
                                                                }
                                                            }
                                                        }
                                                );
                                            }
                                        }
                                );
                            }
                    );
                }
        );

        Collections.sort(raceRankingList);
        //removing participants whose base speed is less than "ZERO".
        raceRankingList.removeIf(rank ->rank.getRaceTime()>=Double.MAX_VALUE );

        //  Rank each participant by time. Equal times have the same rank.
        for (int rank = 0; rank < raceRankingList.size(); rank++) {
            if (rank > 0) {
                Rank currentHorseRankObj = raceRankingList.get(rank);
                Rank previousHorseRankObj = raceRankingList.get(rank-1);
                if (currentHorseRankObj.getRaceTime() == previousHorseRankObj.getRaceTime()) {
                    currentHorseRankObj.setPosition(previousHorseRankObj.getPosition());
                } else {
                    currentHorseRankObj.setPosition(previousHorseRankObj.getPosition() + 1);
                }
            }
        }

        //returning list of all participants whose position is less than or equal to MAXIMUM_RANKS
        return raceRankingList.stream()
                .filter(rank -> rank.getPosition() <= MAXIMUM_RANKS)
                .collect(Collectors.toList());
    }
}
