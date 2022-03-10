//This class creates a node to be used in a Huffman tree
public class HuffmanNode implements Comparable<HuffmanNode> {
   
   public int freq;
   public int letter;
   public HuffmanNode left;
   public HuffmanNode right;
   
   //takes in two ints for frequency and letter and 
   //makes them available for the class.
   public HuffmanNode(int letter, int freq) {
      this(letter, freq, null, null);
   }
   
   //takes in a two ints for letter and frequency and 
   //a left and right leaf to use in the tree.
   public HuffmanNode(int letter, int freq, HuffmanNode left,
                                           HuffmanNode right) {
      this.freq = freq;
      this.letter = letter;
      this.left = left;
      this.right = right;
   }
   
   //compares two nodes, one taken in as a parameter,
   //by subtracting them from eachoher and returning that value.
   public int compareTo(HuffmanNode node) {
      int subtracted = this.freq - node.freq;
      return subtracted;
   }
        
   

}