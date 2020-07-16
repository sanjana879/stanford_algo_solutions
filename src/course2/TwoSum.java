package course2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class TwoSum 
{
	static Hashtable<Long, Long> nums = new Hashtable<Long, Long>();
	static Hashtable<Integer, Boolean> res = new Hashtable<Integer, Boolean>();
	static int low = -10000;
	static int high = 10000;
	
	public static void main(String[] args) throws FileNotFoundException 
	{
		// TODO Auto-generated method stub
		Scanner s = new Scanner(new File("2sum.txt"));
		ArrayList<Long> arr= new ArrayList<Long>();
		while(s.hasNextLong())
		{
			long n = s.nextLong();
			nums.put(n,n);
			arr.add(n);
		}
		System.out.println(algorithm(arr));
	}

	public static int algorithm(ArrayList<Long> arr)
	{
		int total = 0;
		
		for(int i = low; i <= high;i++)
		{
			int tar = i;
			for(int j = 0; j < arr.size();j++)
			{
				long x = arr.get(j);
				if(nums.get(tar-x) != null && (tar-x) != x && res.get(tar) == null) 
				{
					res.put(tar, true);
					total++;
				}
			}
		}
		
		return total;
	}
}
