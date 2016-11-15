/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompetitionRegister;


import java.util.ArrayList;

/**
 *
* @author Sebastian Bäckström Pino, sebc5325
 */
public class Participant {

    private String personName;
    private String personLastName;
    private Team participantTeam;
    private ArrayList<AttemptCounter> attemptList = new ArrayList<AttemptCounter>();
    private int partNumber;
    private static int startPartNumber = 99;

    public Participant(String personName, String personLastName, Team te) {

        this.personName = personName;
        this.personLastName = personLastName;
        this.participantTeam = te;
        startPartNumber += 1;
        this.partNumber = startPartNumber;

    }

    public void setAttempt(Event event) {
        boolean found = false;
        for (AttemptCounter a : attemptList) {

            if (a.getAttemptEvent().equals(event)) {
                found = true;
                a.setAttempt();
            }

        }
        if (!found) {
                
            AttemptCounter tempAttemptCounter= new AttemptCounter(event);
            
            attemptList.add(tempAttemptCounter);
            
        }
    }
    
    public int getAttemptNr(Event event) { 
        boolean found = false;
         AttemptCounter tempAttempt = (null);
        for (AttemptCounter a : attemptList) {

            if (a.getAttemptEvent().equals(event)) {
                found = true;
                tempAttempt = a;
            }

        }
        return tempAttempt.getAttempt();
    }
    
    public ArrayList getAttemptList() {

        return attemptList;
    }

    public void setPersonName(String newPersonName) {

        personName = newPersonName;
    }

    public String getPersonName() {

        return personName;
    }

    public void setPersonLastName(String newPersonLastName) {

        personLastName = newPersonLastName;
    }

    public String getPersonLastName() {

        return personLastName;
    }

    public void setTeam(String newPersonLastName) {

        personLastName = newPersonLastName;
    }

    public Team getTeam() {

        return participantTeam;
    }

    public void setPartNumber(int newPartNumber) {

        partNumber += 1;
    }

    public int getPartNumber() {

        return partNumber;
    }

   

    public static void resetPartNumber() {
        startPartNumber = 99;

    }

    public String toString() {

        return personName + " " + personLastName + " from " + participantTeam + " with number " + partNumber;

    }

}

