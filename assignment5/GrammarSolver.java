//Nathan Gomez
//CSE 143
//Homework #5

//This class will use a text to determine terminal symbols and 
//nonterminal symbols to store them. Those symbols are then used to 
//create random sentences with the attributes that are chosen. This 
//class also includes funtions that return the current symbols and check
//if the stored values contain a certain symbol.
import java.util.*;

public class GrammarSolver {
   
   private SortedMap<String, List<String[]>> type;
   
   //This method organizes between the terminal and nonterminal symbols which 
   //then stored to be used later. Will throw an IllegalArgumentException if the 
   //List passed in is empty or if a symbol given is seen more than once.
   public GrammarSolver(List<String> grammar) {
      if (grammar.isEmpty()) {
         throw new IllegalArgumentException();
      }
      type = new TreeMap<>();
      for (String words : grammar) {
         String[] split = words.split("::=");
         String nonTerminal = split[0];
         String[] category = split[1].split("[|]");
         List<String[]> terminal = new ArrayList<>();
         for (int i = 0; i < category.length; i++) {
            category[i] = category[i].trim();
            String[] terminals = category[i].split("[ \t]+"); 
            terminal.add(terminals);       
         }
         if (!type.containsKey(nonTerminal)) {
            type.put(nonTerminal, terminal);
         } else {
            throw new IllegalArgumentException();
         }        
      }
   }
   
   //Checks if the symbol passed in is in the current nonterminal values that are stored 
   //and returns whether that check is true or false.
   public boolean grammarContains(String symbol) {
      return type.keySet().contains(symbol);
   }
   
   //Puts all of the nonterminal symbols together neatly and returns that 
   //value.
   public String getSymbols() {
      Set<String> symbols = type.keySet();
      String finalString = "[";
      int iterator = 0;
      for (String nonT : symbols) {
         if (iterator < symbols.size() - 1) {
            finalString += nonT + ",";
         } else {
            finalString += nonT;
         }
         iterator++;
      }   
      return finalString + "]";  
   }
   
   //Takes in a given nonterminal symbol and the amount of times it
   //wants to make a new line and uses that to place randomly generated values
   //together and returns all of those values in a String array. Returns an
   //IllegalArgumentException if times is less than zero or if the nonterminal
   //symbol does not match the stored nonterminal symbols.
   public String[] generate(String symbol, int times) {
      if (!type.containsKey(symbol) || times < 0) {
         throw new IllegalArgumentException();
      }
      
      String[] generated = new String[times];
      for (int i = 0; i < times; i++) {
         String indexVal = generate(symbol);
         generated[i] = indexVal;
      }
      return generated;   
   }
   
   //Works with the other generate method to pseudorandomly place values together and 
   //return all of those values as long as the nonterminal symbols are not seen more than once
   //as a string of text.
   private String generate(String symbol) {
      Random r = new Random();
      String text = "";
      if (!grammarContains(symbol)) {
         return symbol;
      } else {
         List<String[]> symbols = type.get(symbol);
         int random = r.nextInt(symbols.size());
         int length = symbols.get(random).length;         
         for (int i = 0; i < length; i++) {
            text += generate(symbols.get(random)[i]) + " ";  
         }        
      }
      text = text.trim();
      return text;
   } 
}