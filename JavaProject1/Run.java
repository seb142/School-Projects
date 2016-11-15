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
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class Run {

    private ArrayList<Event> eventList = new ArrayList<>();
    private ArrayList<Team> teamList = new ArrayList<>();
    private ArrayList<Participant> partList = new ArrayList<>();
    private ArrayList<Result> resultList = new ArrayList<>();
    private String alternatives;
    private Scanner input = new Scanner(System.in);

    public ArrayList getEventList() {

        return eventList;
    }

    public void addEvent() {

        System.out.println("event name: ");
        String eventName = doubletCheck();

        System.out.println("number of attempts: ");
        int attempts = input.nextInt();
        input.nextLine();
        while (attempts < 1) {
            System.out.println("the number of attempts are too small\nnumber of attempts: ");
            attempts = input.nextInt();
            input.nextLine();
        }

        System.out.println("bigger better ?");
        String biggerBetter = input.nextLine();

        boolean körs2 = true;
        while (körs2) {

            if (biggerBetter.equalsIgnoreCase("y") || biggerBetter.equalsIgnoreCase("yes")) {
                biggerBetter = "yes";
                körs2 = false;
                break;

            }
            if (biggerBetter.equalsIgnoreCase("n") || biggerBetter.equalsIgnoreCase("no")) {
                biggerBetter = "no";
                körs2 = false;
                break;
            } else {
                System.out.println("wrong awnser, please awnser with y/yes or n/no \nbigger better ?");
                biggerBetter = input.nextLine();

            }
        }

        Event ten = new Event(eventName, attempts, biggerBetter);
        eventList.add(ten);

        System.out.println(eventName + " " + "added.");

        menu();
    }

    public void addPerson() {
        Team tempTeam = null;
        boolean teamExists = false;
        System.out.println("first name: ");
        String personName = formWord();
        System.out.println("last name: ");
        String personLastName = formWord();
        System.out.println("team: ");
        String team = formWord();

        for (Team t : teamList) {
            if (t.getTeamName().equalsIgnoreCase(team)) {
                teamExists = true;
                tempTeam = t;

            }

        }

        if (!teamExists) {

            tempTeam = new Team(team);
            teamList.add(tempTeam);
        }

        Participant tempPart = new Participant(personName, personLastName, tempTeam);

        partList.add(tempPart);

        tempTeam.addToMemberList(tempPart);

        for (int i = 0; i < tempTeam.getMemberList().size(); i++) {

            if (tempTeam.getMemberListPos(i).equals(tempPart)) {
                System.out.println(tempTeam.getMemberListPos(i) + " " + "has been added");

            }

        }

        menu();

    }

    public void removePart() {
        System.out.println("number: ");
        int scanNum = input.nextInt();
        input.nextLine();
        Participant tempPart2 = null;
        boolean found = false;
        Team tempTeam = null;
        Participant tempPart3 = null;

        for (int i = 0; i < partList.size(); i++) {

            tempPart2 = partList.get(i);

            if (scanNum == tempPart2.getPartNumber()) {

                partList.remove(i);
                found = true;
                System.out.println(tempPart2 + " has been removed");
                tempPart3 = tempPart2;
            }

        }

        if (found) {

            tempTeam = tempPart3.getTeam();

            tempTeam.removeParticipant(tempPart3);

            if (tempTeam.getMemberList().isEmpty()) {

                teamList.remove(tempTeam);

            }
            Result resultObject;
            for (Iterator<Result> iterator = resultList.iterator(); iterator.hasNext();) {
                resultObject = (Result) iterator.next();

                if (scanNum == (resultObject.getResultParticipant().getPartNumber())) {

                    iterator.remove();
                }
            }

        } else if (!found) {
            System.out.println(" a Participant with that number could not be found");

        }

        menu();

    }

    public void addResult() {
        System.out.println("participant number");
        double result;
        boolean partFound = false;
        boolean eventFound = false;

        int scanNum2 = input.nextInt();
        input.nextLine();

        Result tempRes = new Result(null, 0, null);

        for (int i = 0; i < partList.size(); i++) {

            Participant tempPart3 = partList.get(i);

            if (scanNum2 == tempPart3.getPartNumber()) {

                tempRes.setResultParticipant(partList.get(i));
                partFound = true;

            }

        }

        if (partFound == false) {
            System.out.println("there is no participant with that number");
            menu();
        }
        System.out.println("event name: ");
        String tempEvent = input.nextLine();
        for (int i = 0; i < eventList.size(); i++) {
            if (tempEvent.equalsIgnoreCase(eventList.get(i).getEventName())) {

                tempRes.setResultEvent(eventList.get(i));
                eventFound = true;

            }

        }
        if (eventFound == false) {
            System.out.println("there is no such event");
            menu();
        }

        if (eventFound && partFound) {

            System.out.println("type result: ");
            result = input.nextDouble();
            input.nextLine();
            while (result < 0) {
                System.out.println("the result can't be negative");
                result = input.nextDouble();
                input.nextLine();
            }

            tempRes.setResult(result);

            tempRes.getResultParticipant().setAttempt(tempRes.getResultEvent());

            if (tempRes.getResultParticipant().getAttemptNr(tempRes.getResultEvent()) > 0) {
                resultList.add(tempRes);

            } else {
                System.out.println("the maximum ammout of results are\n registered for this participant");
            }

        } else {
            System.out.println("typed info is incorrect");
        }

        menu();
    }

    public void resultListNumber() {
        boolean found = false;
        int tempNumber = input.nextInt();
        input.nextLine();

        for (int i = 0; i < partList.size(); i++) {

            Participant tempPart = partList.get(i);

            if (tempNumber == tempPart.getPartNumber()) {

                for (int j = 0; j < resultList.size(); j++) {

                    if (resultList.get(j).getResultParticipant() == tempPart) {

                        System.out.println(resultList.get(j));

                    }

                }

            }

        }
        if (!found) {
            System.out.println("no participant with number " + tempNumber);

        }

    }

    public void resultListTeam() {

        if (teamList.isEmpty() && resultList.isEmpty()) {
            System.out.println("no available teams");
            menu();
        }

        for (Event e : eventList) {

            String eventName = e.getEventName();
            int skipCounter2 = 0;
            int arrLength = 0;
            int place = 0;
            int realPlace = 0;
            int place2 = 0;

            for (int i = 0; i < resultList.size(); i++) {

                if (eventName.equalsIgnoreCase(resultList.get(i).getResultEvent().getEventName())) {

                    arrLength += 1;
                }

            }

            int antal = 0;
            Result tempor;
            Result[] resLi = new Result[arrLength];
            boolean flag = true;
            int[] resultint = new int[resultList.size()];
            for (int i = 0; i < resultList.size(); i++) {

                Result tempResult = resultList.get(i);

                if (tempResult.getResultEvent().getEventName().equalsIgnoreCase(eventName)) {

                    resLi[antal] = tempResult;

                    antal++;
                }

            }

            sortArray(resLi);

            if (e.getBiggerBetter().equalsIgnoreCase("yes")) {
                ArrayList<Participant> maxResultPar = new ArrayList<>();
                ArrayList<Double> maxResult = new ArrayList<>();

                for (int i = 0; i < resLi.length; i++) {
                    Result tempResult2 = resLi[i];

                    if (!maxResultPar.contains(tempResult2.getResultParticipant())) {
                        if (!maxResult.contains(tempResult2.getResult())) {
                            place++;
                            realPlace = place + skipCounter2;
                        } else {

                            realPlace = place + skipCounter2;
                            skipCounter2++;

                        }

                        if (realPlace == 1) {
                            tempResult2.getResultParticipant().getTeam().setGold(1);
                        }
                        if (realPlace == 2) {
                            tempResult2.getResultParticipant().getTeam().setSilver(1);
                        }
                        if (realPlace == 3) {
                            tempResult2.getResultParticipant().getTeam().setBronze(1);

                        }
                        maxResult.add(tempResult2.getResult());
                        maxResultPar.add(tempResult2.getResultParticipant());
                    }

                }

                maxResultPar.removeAll(maxResultPar);
                maxResult.removeAll(maxResult);
            } else if (e.getBiggerBetter().equalsIgnoreCase("no")) {

                ArrayList<Participant> maxResultPar2 = new ArrayList<>();
                ArrayList<Double> maxResult2 = new ArrayList<>();

                for (int i = arrLength - 1; i >= 0; i--) {

                    Result tempResult3 = resLi[i];

                    if (!maxResultPar2.contains(tempResult3.getResultParticipant())) {
                        if (!maxResult2.contains(tempResult3.getResult())) {
                            place++;
                            realPlace = place + skipCounter2;
                        } else {

                            realPlace = place + skipCounter2;
                            skipCounter2++;

                        }

                        if (realPlace == 1) {
                            tempResult3.getResultParticipant().getTeam().setGold(1);
                        }
                        if (realPlace == 2) {
                            tempResult3.getResultParticipant().getTeam().setSilver(1);
                        }
                        if (realPlace == 3) {
                            tempResult3.getResultParticipant().getTeam().setBronze(1);

                        }
                        maxResultPar2.add(tempResult3.getResultParticipant());
                        maxResult2.add(tempResult3.getResult());

                    }

                }

                maxResult2.removeAll(maxResult2);
                maxResultPar2.removeAll(maxResultPar2);

            }
            resLi = null;
        }

        System.out.println("1st 2nd 3rd TeamName");
        for (Team t : teamList) {
            System.out.println(t.getGold() + "   " + t.getSilver() + "   " + t.getBronze() + "   " + t.getTeamName());
            t.zeroTrophy();
        }

    }

    public void sortArray(Result[] list) {
        Result tempor2;
        boolean flag = true;
        if (list.length > 1) {
            while (flag) {
                flag = false;
                for (int j = 0; j < list.length - 1; j++) {
                    if (list[j].getResult() < list[j + 1].getResult()) {
                        tempor2 = list[j];
                        list[j] = list[j + 1];
                        list[j + 1] = tempor2;
                        flag = true;
                    }
                }
            }
        }

    }

    public void resultListEvent(Event event) {
        int skipCounter = 0;
        int arrLength = 0;
        int place = 0;
        int place2 = 0;
        Event tempEvent = null;
        String eventName = event.getEventName();
        for (int i = 0; i < resultList.size(); i++) {

            tempEvent = resultList.get(i).getResultEvent();

            if (eventName.equalsIgnoreCase(tempEvent.getEventName())) {

                arrLength += 1;
            }

        }

        int antal = 0;

        Result[] resLi = new Result[arrLength];

        for (int i = 0; i < resultList.size(); i++) {

            Result tempResult = resultList.get(i);

            if (tempResult.getResultEvent().getEventName().equalsIgnoreCase(eventName)) {

                resLi[antal] = tempResult;

                antal++;
            }

        }

        sortArray(resLi);

        if (event.getBiggerBetter().equalsIgnoreCase("yes")) {
            ArrayList<Participant> maxResultPar = new ArrayList<>();
            ArrayList<Double> maxResult = new ArrayList<>();

            for (int i = 0; i < resLi.length; i++) {
                Result tempResult2 = resLi[i];

                if (!maxResultPar.contains(tempResult2.getResultParticipant())) {
                    if (!maxResult.contains(tempResult2.getResult())) {
                        place2++;
                        System.out.print((place2 + skipCounter) + " ");
                    } else {

                        System.out.print((place2 + skipCounter) + " ");
                        skipCounter++;

                    }

                    System.out.println(tempResult2);

                    maxResult.add(tempResult2.getResult());
                    maxResultPar.add(tempResult2.getResultParticipant());

                }
            }

            maxResultPar.removeAll(maxResultPar);
            maxResult.removeAll(maxResult);

        } else if (event.getBiggerBetter().equalsIgnoreCase("no")) {

            ArrayList<Participant> maxResultPar2 = new ArrayList<>();
            ArrayList<Double> maxResult2 = new ArrayList<>();

            for (int i = arrLength - 1; i >= 0; i--) {

                Result tempResult3 = resLi[i];

                if (!maxResultPar2.contains(tempResult3.getResultParticipant())) {
                    if (!maxResult2.contains(tempResult3.getResult())) {
                        place++;
                        System.out.print((place + skipCounter) + " ");
                    } else {

                        System.out.print((place + skipCounter) + " ");
                        skipCounter++;

                    }
                    System.out.println(tempResult3);

                    maxResultPar2.add(tempResult3.getResultParticipant());
                    maxResult2.add(tempResult3.getResult());
                }

            }

            maxResult2.removeAll(maxResult2);
            maxResultPar2.removeAll(maxResultPar2);
        }

    }

    public String doubletCheck() {
        String eventName = formWord();
        for (int i = 0; i < eventList.size(); i++) {
            String tempEvName = eventList.get(i).getEventName();
            if (tempEvName.equals(eventName)) {
                System.out.println("the event " + eventName + " already exists");
                menu();
            }
        }
        return eventName;
    }

    public String formWord() {
        //  System.out.println("type>");
        String newWord = input.nextLine();

        newWord = newWord.trim();
        while (newWord == null || newWord.isEmpty()) {
            System.out.println("the system won't accept empty och blank names");
            System.out.println("type name again");
            newWord = input.nextLine();
            newWord = newWord.trim();
        }

        String i = newWord.substring(0, 1).toUpperCase();
        newWord = i + newWord.substring(1).toLowerCase();

        return newWord;

    }

    public void message(String message) {

        String[] stringArray = new String[60];

        stringArray[0] = "*";
        stringArray[1] = " ";
        stringArray[58] = " ";
        stringArray[59] = "*";

        for (int i = 2; i < 58; i++) {
            stringArray[i] = " ";
        }

        for (int i = 7; i < message.length(); i++) {

            if (i < 64) {
                String stringValueOf = String.valueOf(message.charAt(i));
                stringArray[i - 6] = stringValueOf;
            }

        }

        for (int j = 0; j < 60; j++) {
            System.out.print("*");
        }
        System.out.println(" ");
        stringArray[58] = " ";
        stringArray[59] = "*";
        for (int i = 0; i < 60; i++) {

            System.out.print(stringArray[i].toUpperCase());
        }
        System.out.println(" ");
        for (int j = 0; j < 60; j++) {

            System.out.print("*");
        }
        System.out.println(" ");

        menu();
    }

    public void removeAllData() {

        eventList.clear();
        partList.clear();
        resultList.clear();
        teamList.clear();
        Participant.resetPartNumber();
        String print = "message all data has been removed";
        message(print);
    }

    public void exit() {

        System.exit(-1);
    }

    public void menu() {

        boolean körs = true;

        while (körs) {

            System.out.println("\nAlternatives:");
            System.out.println("add event \nadd participant\nremove participant\nadd "
                    + "result\nparticipant\ntype event name for event results\nteams\nreinitialize\nexit ");
            alternatives = input.nextLine();
            Event tempEvName;
            String msgContent = alternatives;

            if (alternatives.contains("message")) {
                System.out.println("test");
                alternatives = "message";
            }
            for (Event x : eventList) {

                tempEvName = x;
                if (tempEvName.getEventName().equalsIgnoreCase(alternatives)) {

                    resultListEvent(tempEvName);
                    return;
                }
            }

            switch (alternatives.toLowerCase()) {

                case "add event":

                    addEvent();
                    break;
                case "teams":

                    resultListTeam();
                    break;

                case "add participant":

                    addPerson();
                    break;

                case "remove participant":

                    removePart();
                    break;

                case "add result":

                    addResult();
                    break;

                case "participant":

                    resultListNumber();
                    break;

                case "exit":

                    exit();
                    break;

                case "message":

                    message(msgContent);
                    break;

                case "reinitialize":

                    removeAllData();
                    break;
                default:
                    System.out.println("unknown command");
                    break;
            }
        }

    }

}
