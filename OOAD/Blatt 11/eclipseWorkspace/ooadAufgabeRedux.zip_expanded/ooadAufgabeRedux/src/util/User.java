package util;

import java.util.Arrays;
import java.util.List;

public class User {
  private static List<String> allowed = Arrays.asList(new String[]{
    "Ute", "Urs", "Leila", "Sergej", "Hans", "Ana"});
  
  public static boolean allowed (String toCheck){
    return  allowed.contains(toCheck);
  }
}
