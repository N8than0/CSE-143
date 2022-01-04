//Nathan Gomez
//CSE 143
//Homework 2
//This class uses the GuitarString object to map notes to a set of keys so that
//They can be played with different notes for different keys on your keyboard.

public class Guitar37 implements Guitar {
   
   public static final String KEYBOARD =
      "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  // keyboard layout
   private GuitarString[] strings = new GuitarString[37];
   private int tics;
   
   //This constructor implements a formula given so that different frequencies can 
   //be put into an array of GuitarString objects.     
   public Guitar37() {     
      for (int i = 0; i < KEYBOARD.length(); i++) {
         double pitches = (double) 440 * Math.pow(2, (i - 24.0) / 12.0) ; 
         strings[i] = new GuitarString(pitches);                  
      }
   }
     
   //This method offsets the pitch by 12 so that the indexes of the GuitarString array 
   //will line up when they are plucked with the int pitch given as a parameter. 
   public void playNote(int pitch) {
      if (pitch + 12 > KEYBOARD.length() && pitch + 12 > 0) {
         strings[pitch + 12].pluck();
       }   
   }
   
   //Pre: a key is entered to play a note with the char key parameter
   //Post: The method determines if the key exists within the keys that work
   //and returns if it exists or not with a true or false statement.
   public boolean hasString(char key) {
      return KEYBOARD.indexOf(key) != -1;
   }
   
   //Pre: A key within the given range of options is entered with the parameter char string.
   //Post: The note corresponding to the key chosen is plucked.
   public void pluck(char string) {
      strings[KEYBOARD.indexOf(string)].pluck();
   }
   
   //This method samples all of the sounds plaued by the keybouts and retruns them all added
   //up
   public double sample() {
      double addedSamples = 0.0;
      for (int i = 0; i < KEYBOARD.length(); i++) {
         addedSamples += strings[i].sample();
      }
      return addedSamples;
   }
   
   //This method counts the amount of tics for each string.
   public void tic() {      
      for (int i = 0; i < KEYBOARD.length(); i++) {
         strings[i].tic();
      }
      tics++;
   }
   
   //This method returns the amount of tics for each string.
   public int time() {
      return tics;
   }
}