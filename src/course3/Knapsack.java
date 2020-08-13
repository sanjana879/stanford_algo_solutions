package course3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Knapsack 
{

	public static void main(String[] args) throws FileNotFoundException 
	{
		Knapsack obj = new Knapsack();
		Scanner s = new Scanner(new File("knapsack.txt"));
		Knap k = obj.make(s);
		System.out.println(obj.algoOptimized(k));
	}
	
	public Knap make(Scanner s)
	{
		ArrayList<Integer> values = new ArrayList<Integer>();
		ArrayList<Integer> weights = new ArrayList<Integer>();
		values.add(0);
		weights.add(0);
		int capacity = s.nextInt();
		int items = s.nextInt();
		while(s.hasNext())
		{
			s.nextLine();
			int val = s.nextInt();
			int weight = s.nextInt();
			values.add(val);
			weights.add(weight);
		}
		return new Knap(values, weights, capacity, items);
	}
	
	public int algoOptimized(Knap k) 
	{
		int[][] A = new int[2][k.capacity + 1];
		int row = 1;
		for(int i = 1;i <= k.items;i++)
		{
			int weight = k.weights.get(i);
			row = (row == 1) ? 0 : 1;
			for(int x = 0; x <= k.capacity;x++)
			{
				if(weight <= x)
				{
					if(row == 1)
						A[row][x] = Math.max(A[row-1][x], A[row-1][x-weight] + k.values.get(i));
					else
						A[row][x] = Math.max(A[row+1][x], A[row+1][x-weight] + k.values.get(i));
				}
				else
				{
					A[row][x] = (row == 1) ? A[row-1][x] : A[row+1][x];
				}
			}	
		}
		return A[row][A[0].length - 1];
	}
	
	public int algo(Knap k) 
	{
		int[][] A = new int[k.items + 1][k.capacity + 1];
		for(int i = 1;i <= k.items;i++)
		{
			int weight = k.weights.get(i);
			for(int x = 0; x <= k.capacity;x++)
			{
				if(weight <= x)
				{
					A[i][x] = Math.max(A[i-1][x], A[i-1][x-weight] + k.values.get(i));
				}
				else
					A[i][x] = A[i-1][x];
			}
			System.out.println(Arrays.toString(A[i]));
		}
		return A[A.length - 1][A[0].length - 1];
	}
}

class Knap
{
	ArrayList<Integer> values;
	int capacity;
	ArrayList<Integer> weights;
	int items; 
	
	public Knap(ArrayList<Integer> v,ArrayList<Integer> w, int c, int i)
	{
		values = v;
		weights = w;
		capacity = c;
		items = i;
	}
}