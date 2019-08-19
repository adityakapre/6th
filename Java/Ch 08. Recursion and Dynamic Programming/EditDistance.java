/**
 * Date 07/07/2014
 * @author Tushar Roy
 *
 * Given two strings how many minimum edits(update, delete or add) is needed to convert one string to another
 *
 * Time complexity is O(m*n)
 * Space complexity is O(m*n)
 *
 * References:
 * http://www.geeksforgeeks.org/dynamic-programming-set-5-edit-distance/
 * https://en.wikipedia.org/wiki/Edit_distance
 */
public class EditDistance {

    /**
     * Uses bottom up DP to find the edit distance
     *
     *         a  b  c  d  e  f
     *      0  1  2  3  4  5  6
     *   a  1  0  1  2  3  4  5
     *   z  2  1  1  2  3  4  5
     *   d  3  2  2  2  2  3  4
     *   e  4  3  3  3  3  2  3
     *   f  5  4  4  4  4  3  2
     */
    public int dynamicEditDistance(char[] str1, char[] str2){
        //need an extra row and column in DP table
        int T[][] = new int[str1.length+1][str2.length+1];
        //init 1st row
        for(int i=0; i < T[0].length; i++){
            T[0][i] = i;
        }
        //init 1st column
        for(int i=0; i < T.length; i++){
            T[i][0] = i;
        }
        
        for(int i=1;i <=str1.length; i++){
            for(int j=1; j <= str2.length; j++){
                if(str1[i-1] == str2[j-1]){
                    T[i][j] = T[i-1][j-1];
                }else{
                    T[i][j] = 1 + min(T[i-1][j-1], T[i-1][j], T[i][j-1]);
                }
            }
        }
        printActualEdits(T, str1, str2);
        return T[str1.length][str2.length];
    }

    /**
     * Prints the actual edits which needs to be done.
     */
    public void printActualEdits(int T[][], char[] str1, char[] str2) {
        int i = T.length - 1;
        int j = T[0].length - 1;
        while(true) {
            if (i == 0 || j == 0) {
                break;
            }
            if (str1[i-1] == str2[j-1]) {
                i = i-1;
                j = j-1;
            } else if (T[i][j] == T[i-1][j-1] + 1){
                System.out.println("Edit " + str2[j-1] + " in string2 to " + str1[i-1] + " in string1");
                i = i-1;
                j = j-1;
            } else if (T[i][j] == T[i-1][j] + 1) {
                System.out.println("Delete in string1 " + str1[i-1]);
                i = i-1;
            } else if (T[i][j] == T[i][j-1] + 1){
                System.out.println("Delete in string2 " + str2[j-1]);
                j = j -1;
            } else {
                throw new IllegalArgumentException("Some wrong with given data");
            }

        }
    }

    private int min(int a,int b, int c){
        int l = Math.min(a, b);
        return Math.min(l, c);
    }

    public static void main(String args[]){
        String str1 = "azced";
        String str2 = "abcdef";
        EditDistance editDistance = new EditDistance();
        int result = editDistance.dynamicEditDistance(str1.toCharArray(), str2.toCharArray());
        System.out.print(result);
    }

    /**
     * Uses recursion to find minimum edits
     */
    public int recEditDistance(char[]  str1, char str2[], int len1,int len2){
        
        if(len1 == str1.length){
            return str2.length - len2;
        }
        if(len2 == str2.length){
            return str1.length - len1;
        }
        return min(
            recEditDistance(str1, str2, len1 + 1, len2 + 1) + str1[len1] == str2[len2] ? 0 : 1, 
            recEditDistance(str1, str2, len1, len2 + 1) + 1, 
            recEditDistance(str1, str2, len1 + 1, len2) + 1
        );
    }
}
