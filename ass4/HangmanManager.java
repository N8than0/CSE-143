//Nathan Gomez
//CSE 143
//Homework #4

//This class sets up the traits of the EvilHangman game, which
//cheats by waiting to choose a word until it absolutely has to.
//This class lets you see the guesses left, the guesses inputted so far,
//the pattern of the word being chosen, the options of words left
//and the amount of times a character is seen. 
import java.util.*;

public class HangmanManager {

   private Set<String> wordsLeft;
   private Set<Character> guessAdd;
   private String patternOfChars;
   private int guessNum;
   
   //Pre: an lowercase word is given.   
   //Sets up the game by taking in a dictionary with words, how long the word 
   //will be, and the maximum number of guesses. Also sets up the initial pattern of 
   //correct guesses.
   public HangmanManager(Collection<String> dictionary, int length, 
                         int max) {
      guessNum = max;
      wordsLeft = new TreeSet<>();
      guessAdd = new TreeSet<>();
      if (length < 1 || max < 0) {
         throw new IllegalArgumentException();
      }
      for (String word : dictionary) {
         if (word.length() == length) {
         wordsLeft.add(word);
         }
      }
      patternOfChars = "";
      for (int i = 0; i < length; i++) {
         patternOfChars += "- ";
      }      
   }
   
   //Returns the current set of words being used as options.  
   public Set<String> words() {
      return wordsLeft;   
   }
   
   //Returns the amount of guesses that the user has left.
   public int guessesLeft() {
      return guessNum;
   }
   
   //Returns all of the guesses that the user has tried.
   public Set<Character> guesses() {     
      return guessAdd;
   }
   
   //Returns the current pattern of the word being chosen by the 
   //game. Throws an IllegalStateException if there are no words 
   //in the dictionary being used.
   public String pattern() {
      if (wordsLeft.isEmpty()) {
         throw new IllegalStateException();
      } 
      return patternOfChars;
   }
   
   //Does most of the work by going through all of the options for words,
   //checking if the user's answers match up with any options. Proceeds to cheat by
   //using only options that the user has not chosen as words available for use.
   //Will throw an IllegalStateException if the user has no more guesses, or if there are
   //no more words that can be used as options. Throws an IllegalArgumentException if 
   //the guessed character has already been guessed.
   public int record(char guess) {
      if (guessNum < 1 || wordsLeft.size() < 1) {
         throw new IllegalStateException();
      } else if (guessAdd.contains(guess) && !wordsLeft.isEmpty()) {
         throw new IllegalArgumentException();
      }     
      Map<String, Set<String>> choose = new TreeMap<>();
     
      for (String word : wordsLeft) {
         Set<String> newWords = new TreeSet<>();        
         String currentPat = patternFinder(word, guess);
         newWords.add(word);
         if (!choose.containsKey(currentPat)) {
            choose.put(currentPat, newWords);
         }
         choose.get(currentPat).add(word);         
      }      
      findWord(choose);
      guessAdd.add(guess);       
      return timesSeen(guess);
   }
   
   
   //Takes in a map as a parameter and works with the record method to reset 
   //the list of words that are being used to a new set of words as the game goes on. 
   private void findWord(Map<String, Set<String>> choose) {
      int max = 0;       
      Iterator<String> currentPat = choose.keySet().iterator();
      if (!choose.isEmpty()) {
         while (currentPat.hasNext()) {
            String nextPat = currentPat.next();
            if (choose.get(nextPat).size() > max) {
               wordsLeft.clear();
               wordsLeft.addAll(choose.get(nextPat));
               max = choose.get(nextPat).size();
               patternOfChars = nextPat;
            }
         }
      }   
   }
   
   //Takes in a word from the options and the user's guess to determine whether it will be added
   //to the pattern that is displayed or not. Returns the pattern that is created.
   private String patternFinder(String choices, char guess) {
      String start = "";
      int element = 0;
      for (int i = 0; i < choices.length(); i++) {
         char letter = choices.charAt(i);
         if (letter != guess) {
            start += patternOfChars.substring(element, element + 2);
         } else {
            start +=  guess + " ";
         }
         element += 2;
      }
      return start; 
   } 
   
   //Counts how many times a character is seen in the pattern by 
   //taking in a character and comparing it to the characters seen in the
   //current pattern. Returns the amount of times that character is seen.
   private int timesSeen(char guess) {
      int amount = 0;
      for (int i = 0; i < patternOfChars.length(); i++) {
         if (patternOfChars.charAt(i) == guess) {
            amount++;
         }          
      }
      if (amount == 0) {
            guessNum--;   
      }
      return amount;   
   }  
}