//Nathan Gomez
//CSE 143
//Homwork #8

//This program encodes and decodes files using the huffman
//encoding method to save space.
import java.util.*;
import java.io.*;

public class HuffmanTree {
   
   private HuffmanNode overallRoot;
   
   //Takes in an an array of integers for all of the counts of characters
   //and reorganizes them together based on importance.
   public HuffmanTree(int[] count) {
      Queue<HuffmanNode> queue = new PriorityQueue<>();
      for (int i = 0; i < count.length; i++) {
         if (count[i] > 0) {
            queue.add(new HuffmanNode(i, count[i]));
         }
      }
      queue.add(new HuffmanNode(count.length, 1));      
      while (queue.size() != 1) {
         HuffmanNode low = queue.remove();
         HuffmanNode lowTwo = queue.remove();
         int addedFreq = low.freq + lowTwo.freq;          
         HuffmanNode newParent = new HuffmanNode(-1, addedFreq, low, lowTwo);
         queue.add(newParent);
         }
      overallRoot = queue.remove();
   }
   
   //Takes in an Scanner a scanner of texts and reads it in so that
   //it can be decoded later.
   public HuffmanTree(Scanner input) {
      while(input.hasNextLine()) {
         int intVal = Integer.parseInt(input.nextLine());
         String stringValTwo = input.nextLine();
         overallRoot = buildTree(overallRoot, intVal, stringValTwo);
      }
   }
   
   //Takes in a printstream for an output to write into another file.
   public void write(PrintStream output) {
      if (overallRoot != null) {
         writeHelp(output, overallRoot, "");
      }
   }
   
   //Takes in a printstream, a node, and a string in order to group the
   //tree to be used with binary numbers.
   private void writeHelp(PrintStream output, HuffmanNode methodRoot,
                                                       String binary) {
      String leftSide = "0";
      String rightSide = "1";
      if (methodRoot.right != null && methodRoot.left != null) {
         writeHelp(output, methodRoot.left, binary + leftSide);
         writeHelp(output, methodRoot.right, binary + rightSide);
         
      } else {
         output.println(methodRoot.letter);
         output.println(binary);
      }
   }
   
   //helper method that takes in a node, an integer,
   //and a string value and builds up the tree of characters. returns 
   //the root node of the method once it is done building.
   private HuffmanNode buildTree(HuffmanNode methodRoot, int numVal, 
                                                   String stringVal) {
      if (methodRoot == null) {
         methodRoot = new HuffmanNode(-1, 0);
      }
      if (stringVal.length() != 1) {         
         if (stringVal.charAt(0) == '0') {
            methodRoot.left = buildTree(methodRoot.left, numVal, 
                                         stringVal.substring(1));
         } else {
            methodRoot.right = buildTree(methodRoot.right, numVal,
                                           stringVal.substring(1));
         }
      } else {
         if (stringVal.charAt(0) == '0') {
            methodRoot.left = new HuffmanNode(numVal, 0);
         } else {
            methodRoot.right = new HuffmanNode(numVal, 0);
         }
         
      }
      return methodRoot;
   }
   
   //Decodes a previously encoded file in order to print the decoded version
   //to an output file. Takes in a bitinputstream input, a printstream output,
   //and a special eof character in order to do this.
   public void decode(BitInputStream input, PrintStream output, int eof) {
      int stop = input.readBit();
      while(stop != 1) {
         stop = runThrough(stop, input, output, overallRoot, eof);
      }
   }
   
   //Traverses through the tree that was built by taking a bitinputstream input,
   //a printstream output, a huffman node, and a special eof character. Writes to
   //the text to the printstream and returns a -1 when it is done.
   private int runThrough(int stop, BitInputStream input, PrintStream output,
                                             HuffmanNode methodRoot, int eof) {
      int tempVal = 0;
      int readBit = input.readBit();
      if(methodRoot.letter != eof) {
         if(methodRoot.right == null && methodRoot.left == null) {
            output.write(methodRoot.letter);
            return stop;
         } else if (stop == 0) {
            tempVal = runThrough(readBit, input, output, methodRoot.left, eof);
            return tempVal;
         } else {
            tempVal = runThrough(readBit, input, output, methodRoot.right, eof);
            return tempVal;
         }
      }
      return -1;
   }
}