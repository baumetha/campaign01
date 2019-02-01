package edu.isu.cs.cs3308;

import edu.isu.cs.cs3308.structures.impl.CircularlyLinkedList;
import edu.isu.cs.cs3308.structures.impl.SinglyLinkedList;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
// Author Ethan Baumgartner with an example from online, started to run out of time
public class SolitaireMain {

    private String deck;
    private CircularlyLinkedList<Integer> deckStorage;

    public SolitaireMain(String deck) {
        deck = deck;
        try {
            getDeck();
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    // Reads from a file and stores it in a CircularlyLinkedList
    // Code learned from https://stackoverflow.com/questions/3806062/how-to-open-a-txt-file-and-read-numbers-in-java
    public void getDeck() throws IOException {
        Scanner scanner = new Scanner(Paths.get(deck));
        deckStorage = new CircularlyLinkedList<>();
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                deckStorage.addLast(scanner.nextInt());
            } else {
                scanner.next();
            }
        }
        if (scanner != null)
            scanner.close();
    }
// Finds JokerA, removes it and reinserts it behind one card and refinds it in the deck
// Idea of moving elements around in a list from https://stackoverflow.com/questions/3173154/move-an-item-inside-a-list
    public void stepOne() {
        // Find JokerA
        int jokerA = deckStorage.indexOf(27);
        // Remove JokerA
        deckStorage.remove(jokerA);
        // Reinsert one space after original position
        deckStorage.insert(27, jokerA + 1);
        // Refind JokerA
        jokerA = deckStorage.indexOf(27);
        stepTwo();
    }
// Finds JokerB, removes it and reinserts it behind two cards and refinds it in the deck
    public void stepTwo() {
        // Find JokerB
        int jokerB = deckStorage.indexOf(28);
        // Remove JokerB
        deckStorage.remove(jokerB);
        // Reinsert JokerB two spaces after original
        deckStorage.insert(28, jokerB + 2);
        // Refind JokerB
        jokerB = deckStorage.indexOf(28);
        stepThree();
    }
// Triple cut, having trouble with this
    public void stepThree() {
        // Front end of the cards before JokerA
        CircularlyLinkedList<Integer> frontEnd = new CircularlyLinkedList<>();
        // Cards after Joker B
        CircularlyLinkedList<Integer> backEnd = new CircularlyLinkedList<>();
        // JokerA location
        int jokerA = deckStorage.indexOf(27);
        //JokerB location
        int jokerB = deckStorage.indexOf(28);
        int i = 0;
        int j = 0;
        int l = 0;
        // Removes the cards before JokerA from the deck and store it in a temp deck?
        while (i < jokerA){
            frontEnd.addFirst(deckStorage.removeFirst());
            i++;
        }
        // Remove the cards before JokerB from the deck and stores it in a temp deck?
        while ( j < jokerB){
            backEnd.addLast(deckStorage.removeLast());
            j++;
        }
        // Adds the cards from after JokerB into the original deck until the temp is empty?
        while (!backEnd.isEmpty()){
            int back = backEnd.remove(backEnd.first());
            deckStorage.addFirst(back);
        }
        // Adds the cards from before JokerA into the original deck until the temp is empty?
        while (!frontEnd.isEmpty()){
            int front = frontEnd.remove(frontEnd.last());
            deckStorage.addLast(front);
        }
        stepFour();
    }
// Looks at the card at the bottom of the deck, uses its value to count down deck and
// removes that group of cards and reinserts them at the bottom of the deck
    public void stepFour() {
        int botttomCard = deckStorage.last();
        int i = 0;
        // Loops through the cards based on the value of the last card
        while (i < botttomCard) {
            deckStorage.insert(deckStorage.first(), deckStorage.size() - 2);
            deckStorage.removeFirst();
            i++;
        }
        stepFive();
    }
// Looks at the value of the card at the top of the deck, counts down by that value
// Looks at the next card and records its value
    public int stepFive() {
        // Finds the value of the topcard in deck
        int topCard = deckStorage.first();
        // If it is a joker, the value is 27
        if (topCard == 28){
            topCard = 27;
        }
        // Looks at the card after counting down based on top card
        int nextCard =  topCard + 1;
        // If the nex card is a joker, repeat the algorithim
        if (nextCard == 27 || nextCard == 28){
            stepOne();
        }
        //Returns the final value
        int finalNum = nextCard;
        return finalNum;
    }
    // Starts the keystream process
    public void keyStream(){
        for (int i = 0; i < deckStorage.size(); i++){
            stepOne();
        }
    }
}