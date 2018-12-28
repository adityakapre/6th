/*
Q. Detect if a string is a palindrome. Follow-up: if there is whitespaces in the string.  
QF. What if the string has a bunch of spaces, but if the same letters are still in the string, it is still a palindrome? ie) _ _ _ a _ b _ _ _ a _ -&gt; true Constraint: you can't trim the spaces out or pull anything out of the string. Must be checked… 
*/

import java.util.*;
 
class Palindrome {
  public static void main(String args[]) {
    String inputString;
    Scanner in = new Scanner(System.in);
    System.out.println("Input a string");
    
    inputString = in.nextLine();
    int length  = inputString.length();
    int i, begin, end, middle;
 
    begin  = 0;
    end    = length - 1;
    middle = (begin + end)/2;
    for (i = begin; i <= middle; i++) {
      if (inputString.charAt(begin) == inputString.charAt(end)) {
         begin++; 
         end--;
      } else {
         break;
      }
    }
    if (i == middle + 1) {
      System.out.println("Palindrome");
    }
    else {
      System.out.println("Not a palindrome");
    } 	
  }
}
