/*
Q. Detect if a string is a palindrome. Follow-up: if there is whitespaces in the string.  
QF. What if the string has a bunch of spaces, but if the same letters are still in the string, 
it is still a palindrome? ie) _ _ _ a _ b _ _ _ a _ -&gt; true Constraint: you can't trim the spaces out 
or pull anything out of the string. Must be checked… 

Answer:
https://leetcode.com/problems/valid-palindrome/submissions/

*/

import java.util.*;
 
public class Palindrome {
  public static void main(String args[]) {
    String inputString;
    Scanner in = new Scanner(System.in);
    System.out.println("Input a string");
    
    inputString = in.nextLine();
    int length  = inputString.length();
    int i, start, end, mid;
 
    start  = 0;
    end    = length - 1;
    mid = (start + end)/2;
    //check only till half/mid
    for (i = start; i <= mid; i++) {
      if (inputString.charAt(start) == inputString.charAt(end)) {
         start++; 
         end--;
      } else {
         break;
      }
    }
    if (i == mid + 1) {
      System.out.println("Palindrome");
    }
    else {
      System.out.println("Not a palindrome");
    } 	
  }
}
