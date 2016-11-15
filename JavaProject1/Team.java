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

import java.util.ArrayList;

public class Team {

    private ArrayList<Participant> memberList;
    private String teamName;
    private int gold = 0;
    private int silver = 0;
    private int bronze = 0;

    public Team(String teamName) {

        this.teamName = teamName;
        this.memberList = new ArrayList<>();
    }

    public void setGold(int newGold) {

        gold += newGold;

    }

    public int getGold() {

        return gold;

    }

    public void setSilver(int newSilver) {

        silver += newSilver;

    }

    public int getSilver() {

        return silver;

    }

    public void setBronze(int newBronze) {

        bronze += newBronze;

    }

    public int getBronze() {

        return bronze;

    }

    public void setTeamName(String newTeam) {

        teamName = newTeam;

    }

    public String getTeamName() {

        return teamName;

    }

    public Participant getMemberListPos(int i) {

        return memberList.get(i);

    }

    public void addToMemberList(Participant addedMember) {

        memberList.add(addedMember);

    }

    public ArrayList<Participant> getMemberList() {

        return memberList;

    }

    public void removeParticipant(Participant participant) {

        memberList.remove(participant);

    }

    public void zeroTrophy() {
        gold = 0;
        silver = 0;
        bronze = 0;

    }

    public String toString() {

        return teamName;
    }
}
