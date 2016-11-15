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
public class Result {

    private Participant resultParticipant;
    private double result;
    private Event resultEvent;
//  int attempt = ev.getAttempt();

    public Result(Participant par, double result, Event ev) {

        this.result = result;
        this.resultEvent = ev;
        this.resultParticipant = par;

    }

    public void setResult(double newResult) {


        result = newResult;
    }

    public double getResult() {

        return result;
    }

    public void setResultEvent(Event newReEv) {

        resultEvent = newReEv;
    }

    public Event getResultEvent() {

        return resultEvent;
    }

    public void setResultParticipant(Participant newReEv) {

        resultParticipant = newReEv;
    }

    public Participant getResultParticipant() {

        return resultParticipant;
    }
//    public int getAttempt() {
//       
//       
//        return attempt;
//    }

    public String toString() {

        return result + " " + resultEvent + " " + resultParticipant;

    }

}

