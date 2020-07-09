package course1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Inversions 
{
	public static void main(String[] args) throws FileNotFoundException 
	{
		Scanner s = new Scanner(new File("inversionss.txt"));
		long[] arr = array(s);
		
		System.out.println(sort_count(arr,0,arr.length-1));

	}
	public static long[] array(Scanner s)
	{
		ArrayList<Long> temp = new ArrayList<Long>();
		while(s.hasNextInt())
		{
			temp.add(s.nextLong());
		}
		long[] arr = new long[temp.size()];
		for(int i = 0;i<temp.size();i++)
		{
			arr[i] = temp.get(i);
		}
		return arr;
	}
	
	public static long sort_count(long[] arr, int left, int right)
	{
		long n = right - left + 1;
		long x = 0,y = 0,z = 0;

		if(n == 0)
		{
			System.out.println(left + " " + right + " shouldn't come here");
		}
		if(n == 1)
			return 0;
		
		if(n == 2)
		{
			//count inversions
			long count = 0;
			if(arr[left] > arr[right])
			{
				count++;
				//sort 
				long temp = arr[left];
				arr[left] = arr[right];
				arr[right] = temp;
			}
			
			return count;
		}
		if(n > 2)
		{
			int leftEnd = (right+left)/2;

			x = sort_count(arr, left, leftEnd);
			y = sort_count(arr, leftEnd+1, right);
			z = merge_count(arr,left,right);
		}
		
		return x + y + z;
	}

	private static long merge_count(long[] arr, int left, int right) 
	{
		int n = right - left + 1;
		long[]temp = new long[right-left+1];
		long count = 0;
		int leftP = left;
		int rightP = ((right+left)/2) + 1;
		int i = 0;

		while(leftP <= ((right+left)/2) && rightP <= right)
		{
			if(arr[leftP] <= arr[rightP])
			{
				temp[i] = arr[leftP];
				leftP++;
			}
			else
			{
				temp[i] = arr[rightP];
				int add;
				if(n % 2 == 0)
					add = (((right+left)/2)) - (leftP) + 1;
				else
					add = (((right+left)/2)) - (leftP) + 1;
				count += add;
				rightP++;

			}
			i++;
		}
		//end cases
		if(rightP <= right)
		{
			for(int j = rightP;j <= right && i < temp.length;j++)
			{
				temp[i] = arr[j];
				i++;
			}
		}
		if(leftP <= (right+left)/2)
		{
			for(int j = leftP;j <= (right+left)/2 && i < temp.length;j++)
			{
				temp[i] = arr[j];
				i++;
			}
		}
		i = 0;
		for(int j = left;j<=right;j++)
		{
			arr[j] = temp[i];
			i++;
		}

		return count;
	}

}
