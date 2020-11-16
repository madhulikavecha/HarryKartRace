package se.atg.service.harrykart.exception;

/**
 * Generic Exception class
 *
 * @author Sudha Madhulika
 * @version 1.0
 * @date 15/11/2020
 */
public class HarryKartException  extends Exception{

    /**
     * The exception displays the message passed to it.
     * @param message   The exception message
     */
    public HarryKartException(String message){
        super(message);
    }
}
