import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class MedianMaintPQ 
{
	PriorityQueue<Integer> high = new PriorityQueue<Integer>();
	PriorityQueue<Integer> low = new PriorityQueue<Integer>(Collections.reverseOrder()); 
	int size;
	public static void main(String[] args) throws FileNotFoundException 
	{
		MedianMaintPQ m = new MedianMaintPQ();
		Scanner s = new Scanner(new File("median.txt"));
		m.takeNumbers(s);
	}
	
	public void takeNumbers(Scanner s)
	{
		int sum = 0;
		while(s.hasNextInt())
		{
			int num = s.nextInt();
			int median = findMedian(num);
			sum += median;
		}
		System.out.println(sum % 10000);
	}

	public int findMedian(int num) 
	{
		size++;
		
		//figure out if its first/second half 
		if(size == 1 || num <= low.peek())
		{
			//put in maxHeap 
			low.add(num);
			if((low.size() - high.size()) > 1)
			{
				high.add(low.poll());
			}
				
		}
		else
		{
			//put in minHeap
			high.add(num);
			//remove head of min and add to max
			if((high.size() - low.size()) >= 1)
				low.add(high.poll());
			
		}

		return low.peek();

	}

}
