/*
Find a way to separate money into n parts. For example, if total money is 121, 
find a way to separate 121 into 4 closest parts and the solution would be an 
array with elements 30, 30, 30, 31
*/

public void separateMoney(int amount, int parts) {
  int eachPart = amount/parts;
  int remainder = amount%parts;
  List<Integer> lsParts = new ArrayList<Integer>();
  for(int i=0; i<parts; i++) {
     lsParts.add(eachPart);
  }
  for(int i=0; i<remainder; i++) {
      lsParts.set(i, lsParts.get(i)+1); 
  }
}
