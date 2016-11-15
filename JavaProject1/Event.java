/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompetitionRegister;

/**
 *
* @author Sebastian Bäckström Pino, sebc5325
 */
public class Event {
    
   
    private String eventName;
    private int attempts;
    private String biggerBetter;

    public Event(String eventName, int attempts, String biggerBetter) {

        this.eventName = eventName;
        this.attempts = attempts;
        this.biggerBetter = biggerBetter;
    }

    public void setEventName(String newEventName) {

        eventName = newEventName;
    }

    public String getEventName() {

        return eventName;
    }

    public void setAttempt(int newAttempt) {

        attempts = newAttempt;
    }

    public int getAttempt() {

        return attempts;
    }
    
    public void setBiggerBetter(String newBiggerBetter) {

        biggerBetter = newBiggerBetter;
    }

    public String getBiggerBetter () {

        return biggerBetter;
    }
   

    public String toString() {

        return eventName + " " + attempts;

    }

}

