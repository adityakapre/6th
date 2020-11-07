public class ARaisedToB {

  int power(int a, int b) {
    if (b < 0) {
        return 0; // error
    } else if (b == 0) {
        return 1;
    } else {
        return a* power(a, b - 1);
    }
  }
}

/*
What is the time complexity ?
0(b). The recursive code iterates through b calls, since it subtracts one at each level.
*/
public static void main(String[] args) {
		int a = 2;
		int power = 3;
		int ans = a;
		for(int i=1; i<power; i++) {
			ans=ans*a;
		}
		System.out.println("answer = "+ans);
}
