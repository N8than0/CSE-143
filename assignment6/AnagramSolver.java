//Nathan Gomez
//CSE 143
//Homework #6

//This program searches through a dictionary and finds all of the
//anagrams possible for a word chosen and a max per line.
import java.util.*;

public class AnagramSolver {
   
   private List<String> dictionary;
   private Map<String, LetterInventory> letters = new HashMap<String, LetterInventory>();
   
   /*Pre: The dictionary passed in is a nonempty collection
   *      of nonempty sequences of letters, has no duplicates
   *      and does not change in state throughout the program.
   */ 
   // Takes in a group of words and sets the dictionary for words up so that they 
   // can be used throughout the program in a way where the words are matched up 
   // with their corresponding letters.   
   public AnagramSolver(List<String> list) {
      dictionary = list;
      for (String words : list) {
         letters.put(words, new LetterInventory(words));
      }        
   }
   
   // Uses the given user input as words chosen and max words per line.
   // Takes those two parameters and adds words from the dictionary
   // to a new set of words if they fit the letters chosen and if possible. Throws
   // an IllegalArgumentException if the max passed in is less than zero.
   public void print(String s, int max) {
      if (max < 0) {
         throw new IllegalArgumentException();
      } 
      LetterInventory input = new LetterInventory(s);
      Stack<String> wordsFound = new Stack<String>();
      List<String> listOfWords = new ArrayList<String>();
      Iterator<String> dict = dictionary.iterator();
      while (dict.hasNext()) {
         String word = dict.next();
         LetterInventory chosen = letters.get(word);
         if (input.subtract(chosen) != null) {
            listOfWords.add(word);
         }     
      }
      printHelp(listOfWords, input, max, wordsFound);     
   }
   
   // Uses recursive backtracking to loop through all of the values in the current
   // dictionary and checks if the letters match and if the words should be chosen.
   // Takes in a new dictionary, the user input as letters, the max words per group
   // and the stack of the words chosen in as parameters. 
   private void printHelp(List<String> newList, LetterInventory input, int max,
                                                          Stack<String> ordered) {     
      if (input.isEmpty()) {
         System.out.println(ordered);
      } else {
         if (max != ordered.size() || max == 0) {
            Iterator<String> loop = newList.iterator();
            while (loop.hasNext()) {
               String word = loop.next();
               LetterInventory tempOne = letters.get(word);
               LetterInventory tempMain = input.subtract(tempOne);
               if (tempMain != null) {
                  ordered.push(word);
                  printHelp(newList, tempMain, max, ordered);
                  ordered.pop();
               }
            }         
         }  
      }
   }
      
}
   
