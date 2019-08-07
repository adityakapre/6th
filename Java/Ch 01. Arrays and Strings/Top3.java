/*
Find top 3 numbers in an input array
*/

public void findTop3 {
    int a[10] = {5,4,3,6,7,8,9,10,1,2};
    int h1 = INT_MIN; //TOP 1st
    int h2 = INT_MIN; //TOP 2nd
    int h3 = INT_MIN; //TOP 3rd
    for(int i=0;i<10;i++) {
        if(a[i] > h1) {
            h3 = h2; 
            h2 = h1; 
            h1 = a[i];
        }
        else if (a[i] > h2) {
            h3 = h2; 
            h2 = a[i];
        }
        else if  (a[i] > h3) {
            h3 = a[i];
        }
    }
    printf("TOP 1st is<%d>\n",h1);
    printf("TOP 2nd is<%d>\n",h2);
    printf("TOP 3rd is<%d>\n",h3);
    return 0;
}
