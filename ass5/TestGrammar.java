import java.util.*;
import java.io.*;

public class TestGrammar {
   public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the cse143 random sentence generator.");
        System.out.println();

        // open grammar file
        System.out.print("What is the name of the grammar file? ");
        String fileName = console.nextLine();
        Scanner input = new Scanner(new File(fileName));

        // read the grammar file and construct the grammar solver
        List<String> grammar = new ArrayList<>();
        while (input.hasNextLine()) {
            String next = input.nextLine().trim();
            if (next.length() > 0) {
                grammar.add(next);
            }
        }
        GrammarSolver solver = 
            new GrammarSolver(Collections.unmodifiableList(grammar));
            
        System.out.println(solver.getSymbols());
        }
}
   



