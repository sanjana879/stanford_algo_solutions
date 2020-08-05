package course3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class MaxWeightIS 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		MaxWeightIS m = new MaxWeightIS();
		Scanner s = new Scanner(new File("max_is.txt"));
		PathGraph p = m.makeGraph(s);
		System.out.println(m.algo(p));
	}
	
	public String algo(PathGraph p)
	{
		int[] arr = new int[p.size];
		arr[0] = 0;
		arr[1] = p.list.get(0).val;
		for(int i = 2; i < p.size;i++)
		{
			//System.out.println(p.list.get(i-1).val);
			arr[i] = Math.max(arr[i-1], arr[i-2] + p.list.get(i-1).val);
		}
		//System.out.println(p.list);
		//System.out.println(Arrays.toString(arr));
		HashSet<Integer> set = reconstruct(p, arr);
		StringBuffer result = new StringBuffer();
		int[] check = {1, 2, 3, 4, 17, 117, 517, 997};
		//System.out.println(set);
		for(int i = 0; i < check.length;i++)
		{
			if(set.contains(check[i]))
				result.append('1');
			else
				result.append('0');
				
		}
		return result.toString();
	}
	
	public HashSet<Integer> reconstruct(PathGraph p, int[] arr)
	{
		HashSet<Integer> nums = new HashSet<Integer>();
		int i = arr.length - 1;
		while(i >= 1)
		{
			//System.out.println(i);
			if(i > 1 && arr[i-1] >= arr[i-2] + p.list.get(i-1).val)
			{
				i--;
			}
			else
			{
				nums.add(i);
				i -= 2;
			}
		}
		
		return nums;
	}
	
	public PathGraph makeGraph(Scanner s)
	{
		ArrayList<PathVertex> list = new ArrayList<PathVertex>();
		int size = s.nextInt();
		//list.add(new PathVertex(0));
		while(s.hasNextInt())
		{
			s.nextLine();
			int num = s.nextInt();
			PathVertex v = new PathVertex(num);
			list.add(v);
		}
		return new PathGraph(list, size);
	}
}

class PathGraph
{
	ArrayList<PathVertex> list;
	int size;
	public PathGraph(ArrayList<PathVertex> l, int s)
	{
		list = l;
		size = s;
	}
}

class PathVertex
{
	int val;
	public PathVertex(int v)
	{
		val = v;
	}
	public String toString()
	{
		return String.valueOf(val);
	}
}