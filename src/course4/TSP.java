package course4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

public class TSP {

  public static void main(String[] args) throws FileNotFoundException  {
    Scanner s = new Scanner(new File("tsp.txt"));
    int size = s.nextInt();
    ArrayList<Pair> list = new ArrayList<Pair>();
    int temp = size;
    while(temp-- > 0) {
      list.add(new Pair(s.nextInt(), s.nextInt()));
    } 
    double[][] graph = new double[size][size];
    for(int i = 0; i < size;i++) { 
      for(int j = 0; j < size;j++) {
        graph[i][j] = Double.MAX_VALUE;
      }
    }

    for(int i = 0; i < size;i++) {
      Pair first = list.get(i);
      for(int j = i+1; j < size;j++) {
        Pair second = list.get(j);
        double part1 = Math.pow(first.x - second.x, 2);
        double part2 = Math.pow(first.y - second.y, 2);
        graph[i][j] = Math.sqrt(part1 + part2);
        graph[j][i] =  Math.sqrt(part1 + part2);
      }
      System.out.println(Arrays.toString(graph[i]));
    }
    tsp(graph);
    /*
      find total size of array sum of (n choose 2...n)
      for each m 
        -find all S's of size m and add to HashMap 
        for all Ss of size m (range of above + last_ind)
          for each j (not 1) in S
            -A[S, j] = {
              Minimize { A[S-j, k] + Ckj } 
              Find A[S-j] by brute force search?
            } 
      return brute force search ... 
    */


  }

  public static void tsp(double[][] graph) {
    int len = findLen(graph.length - 1);
    int n = graph.length;
    System.out.println(len);
    double[][] A = new double[len][n];
    HashMap<Integer, List<HashSet<Integer>> S = new HashMap<Integer, List<HashSet<Integer>>();
    for(int m = 2; m <= n; m++) {

    }


  } 

  public static void makeSet(int m, int n) {

  }

  public static int factorial(int n, int[] factorials) {
    if(n <= 1) {
      factorials[0] = 1;
      factorials[1] = 1;
      return 1;
    }
    factorials[n] = n*factorial(n-1, factorials);
    return factorials[n];
  }

  public static int findLen(int n) {
    int[] factorials = new int[n+1];
    factorial(n, factorials);
    int len = 0;
    for(int i = 1; i <= n;i++) {
      int nChooseK = factorials[n] / (factorials[n-i]*factorials[i]);
      len += nChooseK;
    }
    return len;
  }


}

class Pair {
  double x;
  double y;
  public Pair(double a, double b) {
    x = a;
    y = b;
  }
}