
/**
 * Write a description of class yesOrNoArrays here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class yesOrNoArrays {
   private List<String> yesBox = new ArrayList<>();
   private List<String> noBox = new ArrayList<>();
   private String randomNote;
   
   
   public void recordAnswer(boolean userSelectsYes) {
       if (userSelectsYes) {
           yesBox.add(randomNote);
        
       } else {
           noBox.add(randomNote);
       }
   }
}
