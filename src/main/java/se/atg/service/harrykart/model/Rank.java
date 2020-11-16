package se.atg.service.harrykart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Representation of the ranking of a participant based on their order of race completion
 *
 * @author Sudha Madhulika
 * @version 1.0
 * @date 15/11/2020
 */
@JsonPropertyOrder({"position", "horseName"})
public class Rank implements Comparable<Rank> {

    private int position;
    private String horseName;
    @JsonIgnore
    private double raceTime;

    public Rank() {
    }

    /**
     * The rank of the Participant
     *
     * @param position  The order that participant has finished in
     * @param horseName The participant's name
     * @param raceTime  The simulated time of completion
     */
    public Rank(int position, String horseName, double raceTime) {
        this.position = position;
        this.horseName = horseName;
        this.raceTime = raceTime;
    }

    /**
     * Returns the position of the participant
     *
     * @return int Order of race completion
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set the rank of the participant
     *
     * @param position Order of race completion
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Returns the name of the participant
     *
     * @return String Participant's name
     */
    public String getHorseName() {
        return horseName;
    }

    /**
     * Returns the representative value quantifying the order of completion
     *
     * @return double  value for the time the race was completed in
     */
    public double getRaceTime() {
        return raceTime;
    }

    /**
     * Sets the representative value quantifying the order of completion
     *
     * @param time value for the time the race was completed in
     */
    public void setRaceTime(double time) {
        this.raceTime = time;
    }

    /**
     * Comparison logic for sorting rank by race completion time
     *
     * @param otherHorseRank Rank object of another participant to be compared with
     * @return int Value signifying greater than, less than, or equal to ranking based on time
     */
    @Override
    public int compareTo(Rank otherHorseRank) {
        if (this.getRaceTime() == otherHorseRank.getRaceTime()) {
            return 0;
        }
        if (this.getRaceTime() > otherHorseRank.getRaceTime()) {
            return 1;
        } else
            return -1;
    }
}
