import java.util.*;
import java.io.*;

public class TestHangman { 
   private static final String DICTIONARY_FILE = "dictionary.txt";
   public static void main(String[] args) throws FileNotFoundException {
      Scanner input = new Scanner(new File("dictionary.txt"));
        Collection<String> dictionary = new ArrayList<>();
        while (input.hasNext()) {
            dictionary.add(input.next().toLowerCase());
        }
        HangmanManager h = new HangmanManager(dictionary, 5, 4);
   }
   
}