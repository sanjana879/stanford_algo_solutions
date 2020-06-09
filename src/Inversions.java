import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Inversions 
{
	public static void main(String[] args) throws FileNotFoundException 
	{
		Scanner s = new Scanner(new File("input.txt"));
		
		
		long[] arr = array(s);
		//int[] arr = { 4, 80, 70, 23, 9, 60, 68, 27, 66, 78, 12, 40, 52, 53, 44, 8, 49, 28, 18, 46, 21, 39, 51, 7, 87, 99, 69, 62, 84, 6, 79, 67, 14, 98, 83, 0, 96, 5, 82, 10, 26, 48, 3, 2, 15, 92, 11, 55, 63, 97, 43, 45, 81, 42, 95, 20, 25, 74, 24, 72, 91, 35, 86, 19, 75, 58, 71, 47, 76, 59, 64, 93, 17, 50, 56, 94, 90, 89, 32, 37, 34, 65, 1, 73, 41, 36, 57, 77, 30, 22, 13, 29, 38, 16, 88, 61, 31, 85, 33, 54 };
		//System.out.println(Arrays.toString(arr));
		System.out.println(sort_count(arr,0,arr.length-1));
		//System.out.println(Arrays.toString(arr));
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
			//System.out.println("here " + left + " " + right);
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
//				System.out.println((n/2) + " " + leftP);
//				System.out.println(Arrays.toString(arr));
//				System.out.println(Arrays.toString(temp));
//				System.out.println("add "  + add + " " + arr[rightP]);
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
//			int add = (n/2) - leftP + 1;
//			count += add;
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
