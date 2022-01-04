//Nathan Gomez
//CSE 143
//Homework 2
//This class mimics the vibrational qualities of a guitar string and 
//makes it so that a user can pluck, tic, or sample a string.
import java.util.*;

public class GuitarString {
     
   private Queue<Double> ring;
   private int capacity;
   private final double DECAY_FACTOR = 0.996;
   
   //This constructor takes in a frequency as a double for a parameter and adds it
   //to a ringbuffer queue if the requirements are met.   
   public GuitarString(double frequency) {
      capacity = (int) Math.round(StdAudio.SAMPLE_RATE / frequency);
      ring = new LinkedList<>();
      if (frequency <= 0 || capacity < 2) {
         throw new IllegalArgumentException();
      }
      for (int i = 0; i < capacity; i++) {
         ring.add(0.0);
      }        
   }
   
   //This method adds all of the values inside of the ringbuffer to a type double array
   //that is taken in as the parameter.
   public GuitarString(double[] init) {
      if (init.length < 2) {
         throw new IllegalArgumentException();
      }
      ring = new LinkedList<>();
      for (int i = 0; i < init.length; i++) {
         ring.add(init[i]);
      }
   }
   
   //This method randomly chooses values between -0.5 and 0.5 and adds them to the 
   //ringbuffer queue.
   public void pluck() {
      for (int i = 0; i < capacity; i++) {
         ring.add(Math.random() - 0.5);
         ring.remove();
      }
   }
   
   //This method deletes the sample in the front of the ringbuffer queue
   //and adds the average of the first two samples multiplied by the decay factor.
   public void tic() {
      ring.add(((ring.remove() + ring.peek()) * 0.5) * DECAY_FACTOR);          
   }
   
   //This method returns the current value in the ringbuffer queue.
   public double sample() {
      return ring.peek();
   }
}