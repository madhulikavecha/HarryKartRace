package se.atg.service.harrykart.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Class representation of the racers by lane number, horse name, and initial speed
 *
 * @author Sudha Madhulika
 * @version 1.0
 * @date 15/11/2020
 */
@JacksonXmlRootElement(localName = "participant")
public class Participant {

    @JacksonXmlProperty
    int lane;

    @JacksonXmlProperty
    String name;

    @JacksonXmlProperty
    int baseSpeed;

    public Participant() {
    }

    /**
     * Harry Kart race participant
     *
     * @param lane      Lane number
     * @param name      Horse name
     * @param baseSpeed Speed that the initial loop is run at
     */
    public Participant(int lane, String name, int baseSpeed) {
        this.baseSpeed = baseSpeed;
        this.name = name;
        this.lane = lane;
    }

    /**
     * Returns the lane the participant is in
     *
     * @return int Lane value
     */
    public int getLane() {
        return lane;
    }

    /**
     * Returns the participant's name
     *
     * @return String  Name of the participant
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the base speed of the participant
     *
     * @return int Initial speed of the participant
     */
    public int getBaseSpeed() {
        return baseSpeed;
    }

    /**
     * Sets the initial speed of the participant
     *
     * @param baseSpeed Speed value to be set
     */
    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }
}
