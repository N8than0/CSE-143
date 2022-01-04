//Nathan Gomez
//CSE 143
//Homework #7

//This class uses questions and answers to try
//and eventually guess the thing that a user is thinking
//of. It stores new information each time it is played
//to better guess what the user is thinking next time.

import java.util.*;
import java.io.*;

public class QuestionTree {
   
   private QuestionNode overallRoot;
   private Scanner console;
   
   //Creates the first possible thing that the game can
   //guess and makes a way for the user to talk to
   //the program.
   public QuestionTree() {
      overallRoot = new QuestionNode("computer");
      console = new Scanner(System.in);
   }
   
   //Reads in an input from a scanner to add to the
   //possible answers and questions.
   public void read(Scanner input) {
      overallRoot = buildTree(input);
   }
   
   //Helper method that takes in an input scanner to
   //check if what is being read in is a question, or an
   //answer and returns that outcome as a QuestionNode
   private QuestionNode buildTree(Scanner input) {
      QuestionNode methodRoot = null;
      if (input.nextLine().equals("Q:")) {
         String line = input.nextLine();
         methodRoot = new QuestionNode(line, buildTree(input), buildTree(input));
      } else {
         String line = input.nextLine();
         methodRoot = new QuestionNode(line);
      }
      return methodRoot;
   }
   
   //Takes in a PrintStream output to write the list
   //of possible questions and answers to an output file.
   public void write(PrintStream output) {
      write(overallRoot, output);
   }
   
   //Takes in a QuestionNode and a PrintStream and writes the
   //questions and answers to an output file depending on
   //whether it is an answer or a question and is possible.
   private void write(QuestionNode currentLeaf, PrintStream output) {
      if (currentLeaf.left == null || currentLeaf.right == null) {
         output.println("A:");
         output.println(currentLeaf.data);
      } else {
         output.println("Q:");
         output.println(currentLeaf.data);
         write(currentLeaf.left, output);
         write(currentLeaf.right, output);
      }
   }
   
   // post: asks the user a question, forcing an answer of "y " or "n";
   // returns true if the answer was yes, returns false otherwise
   public boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }
   
   //Asks the user questions to determine what the thing they
   //are thinking of is.
   public void askQuestions() {
      overallRoot = askQuestions(overallRoot);
   }
   
   //Helper method that takes in a QuestionNode and chekcs if the
   //user answered yes or no to certain questions to determine where to
   //go next. Returns the current QuestionNode.
   private QuestionNode askQuestions(QuestionNode current) {
      if (current.left != null || current.right != null) {
         if (!yesTo(current.data)) {
            current.right = askQuestions(current.right);
         } else {
            current.left = askQuestions(current.left);
         }
      } else {
         current = askHelp(current);
      }
      return current;
   }
   
   //Helper method that takes in a QuestionNode and asks the
   //user a series of questions so that the game can get more intelligent
   //for future plays. Returns the current QuesitonNode being used.
   private QuestionNode askHelp(QuestionNode current) {
      String data = current.data;
      boolean yesTwo = yesTo("Would your object happen to be " + data + "?");
      if (!yesTwo) {
         System.out.print("What is the name of your object? ");
         String answer = console.nextLine();
         System.out.println("Please give me a yes/no question that");
         System.out.println("distinguishes between your object");
         System.out.println("and mine--> ");
         String question = console.nextLine();
         QuestionNode answerNode = new QuestionNode(answer);
         if (yesTo("And what is the answer for your object?")) {
            current = new QuestionNode(question, answerNode, current);
         } else {
            current = new QuestionNode(question, current, answerNode);
         }
      } else {
         System.out.println("Great, I got it right!");
      }
      return current;
   }
}