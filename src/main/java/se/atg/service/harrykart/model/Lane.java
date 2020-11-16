package se.atg.service.harrykart.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * Class representation of a lane within a loop--lane number and power value
 *
 * @author Sudha Madhulika
 * @version 1.0
 * @date 15/11/2020
 */
public class Lane {

    @JacksonXmlProperty
    int number;

    @JacksonXmlText
    int powerValue;

    public Lane() {
    }

    /**
     * A lane within a loop
     *
     * @param number     Lane number
     * @param powerValue Power-Up value
     */
    public Lane(int number, int powerValue) {
        this.number = number;
        this.powerValue = powerValue;
    }

    /**
     * Returns the lane number
     *
     * @return int  The lane number
     */
    public int getNumber() {
        return number;
    }


    /**
     * Returns the power-up value
     *
     * @return int  The power-up value
     */
    public int getPowerValue() {
        return powerValue;
    }

}
