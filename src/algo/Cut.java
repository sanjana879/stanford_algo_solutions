package course4;
import java.util.*;

public class Cut {
    
  public static void main(String args[]) {
    int[][] prices = new int[6][6];
    for(int i = 0; i <= 5;i++) {
      for(int j = 0; j <= 5; j++) {
        prices[i][j] = 1;
      }
    }
    prices[3][3] = 10;
    prices[2][3] = 7;
    prices[2][2] = 5;
    int[][] dp = max(prices);
    System.out.println(dp[5][5]);
  }

  public static int[][] max(int[][] prices) {
    int[][] dp = new int[prices.length][prices.length];
    for(int i = 1; i <= 5;i++) {
      for(int j = 1; j <= 5; j++) {
        dp[i][j] = prices[i][j];
      }
    }

    int m = dp.length - 1;
    int n = dp[0].length - 1;
    for(int i = 1; i <= m;i++) {
      for(int j = 1; j <= n;j++) {
        
        int ans = 0;
        int row = i;
        for(int c = 1; c <= j;c++) {
          ans = Math.max(ans, dp[row][c] + dp[row][j - c]);
        }
        int col = j;
        for(int r = 1; r <= i;r++) {
          ans = Math.max(ans, dp[r][col] + dp[i - r][col]);
        }
        
        dp[i][j] = Math.max(ans, dp[i][j]);
        System.out.println(i + " " + j + " " + dp[i][j]);
      }
    }
    
    return dp;


  }
  
}
