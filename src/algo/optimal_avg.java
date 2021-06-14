package algo;

public class optimal_avg {
  public static void main(String[] args) {
    //int[] arr = {523,521,525,6244,462,246,-233,23};
    int[] arr = {5,1,2,3,6,-2,8,4,-16};
    int[] sums = new int[8];
    /*for(int i = 1;i < arr.length;i++ ) {
      sums[i] = sums[i-1] + arr[i];
    }*/
    double max = 0;
    int start = 0;
    int end = 0;
    for(int i = 0; i < arr.length;i++) {
      double curr = 0;//arr[i];
      for(int j = i; j < arr.length;j++) {
        curr += arr[j];
        if(j - i + 1>= 3) {
        max = Math.max(max, curr/(j - i + 1));
        if(max == curr/(j - i + 1)) {
          start = i; end = j;
        }
      }
      }
    }
    System.out.println(max + " " + start + " " + end);

    
    

  }
}
