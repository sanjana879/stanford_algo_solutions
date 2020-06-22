import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class QuickSortComparisons 
{
	int count;
	
	public long countCompareFirst(long[] arr, long start, long end)
	{
		long temp = arr[(int) start];
		long length = (end-start) + 1;
		count += length - 1;
		//System.out.println(Arrays.toString(arr) + " " + length + " " + start + " " + end);
		long i = start + 1;
		for(long j = start + 1; j <= end;j++)
		{
			if(arr[(int) j] < temp)
			{
				swap(arr,j,i);
				i = i +1;
				//System.out.println(Arrays.toString(arr));
			}
		}
		swap(arr,start,i-1);
		//System.out.println(Arrays.toString(arr));
		return i-1;
	}
	
	public long countCompareLast(long[] arr, long start, long end)
	{
		//System.out.println(Arrays.toString(arr) + " " + start + " " + end);
		long temp = arr[(int) end];
		swap(arr,start,end);
		long length = (end-start) + 1;
		count += length - 1;
		//System.out.println(Arrays.toString(arr) + " " + start + " " + end + " " + temp);
		long i = start + 1;
		for(long j = start + 1; j <= end;j++)
		{
			if(arr[(int) j] < temp)
			{
				swap(arr,j,i);
				i = i +1;
			}
		}
		swap(arr,start,i-1);
		//System.out.println(Arrays.toString(arr) );
		return i-1;
	}
	
	public long countCompareMiddle(long[] arr, long start, long end)
	{
		
		//choosing pivot
		int intStart = (int) start;
		int intEnd = (int) end;
		int medianInd = (int) ((start+end)/2);
		long temp;
		int pivot;
		long[] tempArr = {arr[intStart],arr[medianInd],arr[intEnd]};
		Arrays.sort(tempArr);
		temp = tempArr[1];
		if(arr[intStart] == temp)
			pivot = intStart;
		else if(arr[medianInd] == temp)
			pivot = medianInd;
		else 
			pivot = intEnd;
		
		swap(arr,pivot, start);
		//System.out.println(Arrays.toString(arr) + " " + start + " " + end + " " + pivot + " " + temp);

		//long temp = arr[(int) end];
		long length = (end-start) + 1;
		count += length - 1;
		
		long i = start + 1;
		for(long j = start + 1; j <= end;j++)
		{
			if(arr[(int) j] < temp)
			{
				swap(arr,j,i);
				i = i +1;
			}
		}
		swap(arr,start,i-1);
		
		return i-1;
	}
	
	private void swap(long[] arr, long j, long i) 
	{
		// TODO Auto-generated method stub
		long temp = arr[(int) i];
		arr[(int) i] = arr[(int) j];
		arr[(int) j] = temp;
	}

	void sortFirst(long[] arr, long start, long end)
	{
		
		if(start < end)
		{
			long m = countCompareFirst(arr,start,end);
			//System.out.println(start + " " + end + " " + m);
			sortFirst(arr,start,m-1);
			sortFirst(arr,m+1,end);
		}
	}
	void sortLast(long[] arr, long start, long end)
	{
		
		if(start < end)
		{
			long m = countCompareLast(arr,start,end);
			//System.out.println(Arrays.toString(arr) + " " + start + " " + end + " " + m);
			sortLast(arr,start,m-1);
			sortLast(arr,m+1,end);
		}
	}
	
	void sortMiddle(long[] arr, long start, long end)
	{
		//System.out.println(Arrays.toString(arr) + " " + start + " " + end );
		if(start < end)
		{
			long m = countCompareMiddle(arr,start,end);
			//System.out.println(Arrays.toString(arr) + " " + start + " " + end + " " + m);
			sortMiddle(arr,start,m-1);
			sortMiddle(arr,m+1,end);
		}
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
	
	public static void main(String[] args) throws FileNotFoundException 
	{
		// TODO Auto-generated method stub
		QuickSortComparisons q = new QuickSortComparisons();
		Scanner s = new Scanner(new File("input1.txt"));
		long[] arr = q.array(s);
		long[] copy = Arrays.copyOf(arr, arr.length);
		q.sortFirst(copy, 0, arr.length-1);
		System.out.println(q.count);//+ " " + Arrays.toString(copy));
		q.count = 0;
		copy = Arrays.copyOf(arr, arr.length);
		q.sortLast(copy, 0, arr.length-1);
		System.out.println(q.count);// + " " + Arrays.toString(copy));
		q.count = 0;
		copy = Arrays.copyOf(arr, arr.length);
		q.sortMiddle(copy, 0, arr.length-1);
		System.out.println(q.count );//+ " " + Arrays.toString(copy));
		q.count = 0;
	}

}
