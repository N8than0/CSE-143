//Nathan Gomez
//CSE 143 
//Homework #3
//This class creates tools that can be used in the assassin game.
//The assassin game is a game of people stalking and killing 
//eachother. This class creates a way to see who is alive, who is stalking who, and 
//who is already dead. It also gives the ability to kill. 

import java.util.*;

public class AssassinManager {
   
   private AssassinNode ring;
   private AssassinNode graveYard;
   
   //Constructs a kill ring with the names of the people in a list
   //passed in as a parameter. These names are all placed into a ring
   //in order which determines how they will kill eachother later on.
   //Throws an exception if the list passed in is empty. 
   public AssassinManager(List<String> names) {            
      if (names.isEmpty()) {
         throw new IllegalArgumentException();
      }
      for (int i = 0; i < names.size(); i++) {
         AssassinNode next = new AssassinNode(names.get(i)); 
         if (ring == null) {
            ring = next;
         } else {
            AssassinNode current = ring;
            while (current.next != null) {
               current = current.next;
            }
            current.next = next;
         }                     
      }
   }
   
   //Prints the current kill ring with the people that are still alive.
   public void printKillRing() {      
      AssassinNode current = ring;
      while (current.next != null) {
         System.out.println("    " + current.name + " is stalking " 
                                          + current.next.name + ".");
         current = current.next;               
      } 
      System.out.println("    " + current.name + " is stalking "
                                               + ring.name + ".");
   }
   
   //Prints the people who were killed along with who killed them.
   public void printGraveyard() {
      AssassinNode current = graveYard;
      while (current != null) {
         System.out.println("    " + current.name + " was killed by " 
                                              + current.killer + ".");
         current = current.next;
      }       
   }
   
   //Checks if the current kill ring contains the name passed in as a
   //parameter and returns the boolean value of that outcome.
   public boolean killRingContains(String name) {
      return contains(name, ring);
   }
   
   //Checks if the graveyard contains the name passed in as a parameter
   //and returns the boolean value of that outcome.
   public boolean graveyardContains(String name) {
      return contains(name, graveYard);
   }
   
   //Checks how many people are left and ends the game by returning whether
   //or not there are more people in the kill ring left.
   public boolean gameOver() {
      return (ring.next == null);
   }
   
   //Checks whether or not the game is over and then returns the name of the 
   //last person, otherwise returns null.
   public String winner() {
      if (gameOver()) {
         return ring.name;
      }
      return null;
   }
   
   //This method takes in a name and deletes that persons name from the 
   //current kill ring and adds that person to the graveyard along with 
   //the name of the person that killed them. This method will throw an
   //exception if the game is already over, or if the name does not exist
   //in the kill ring.
   public void kill(String name) {
      AssassinNode current = ring;
      AssassinNode grave = graveYard;
      if (gameOver()) {
         throw new IllegalStateException();
      } else if (!killRingContains(name)) {
         throw new IllegalArgumentException();
      }
      String currentName = current.name;
      if (currentName.equalsIgnoreCase(name)) {
         grave = current;
         while (current.next != null) {
            current = current.next;
         }
         ring = ring.next;
      } else {
         String nextName = current.next.name;
         while (!nextName.equalsIgnoreCase(name)) {
            current = current.next;
         }
         grave = current.next;
         current.next = current.next.next;
      }
      grave.next = graveYard;
      grave.killer = current.name;
      graveYard = grave;    
   }
   
   //Method that takes in a name and an assassin node and checks
   //whether or not the assassin node contains the name passed in. returns
   //true if the name is found, otherwise returns false.
   private boolean contains(String name, AssassinNode ringOrGrave) {
      AssassinNode current = ringOrGrave;
      while (current != null) {
         String currentName = current.name;
         if (currentName.equalsIgnoreCase(name)) {
            return true;
         }
         current = current.next;
      }
      return false; 
   }
}