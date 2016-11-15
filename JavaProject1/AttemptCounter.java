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
public class AttemptCounter {

    private Run tempRun = new Run();
    private Event attemptEvent;
    private int attempt;

    public AttemptCounter(Event attemptEvent) {
        this.attemptEvent = attemptEvent;
        this.attempt = attemptEvent.getAttempt();

    }

    public void setAttempt() {

        attempt -= 1;

    }

    public Event getAttemptEvent() {

        return attemptEvent;

    }

    public int getAttempt() {

        return attempt;

    }
}
