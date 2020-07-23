package course3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ScheduleJobs {

	public static void main(String[] args) throws FileNotFoundException 
	{
		// TODO Auto-generated method stub
		Scanner s = new Scanner(new File("jobs.txt"));
		List<JobDiff> list = diffList(s);
		//System.out.println(list);
		System.out.println(sumD(list));
		
		List<JobRatio> list2 = ratioList(new Scanner(new File("jobs.txt")));
		//System.out.println(list2);
		System.out.println(sumR(list2));
	}
	
	public static List<JobDiff> diffList(Scanner s)
	{
		List<JobDiff> list = new ArrayList<JobDiff>();
		int size = s.nextInt();
		s.nextLine();
		for(int i = 1; i <= size;i++)
		{
			String[] input = s.nextLine().split(" ");
			JobDiff j = new JobDiff(i, Integer.parseInt(input[0]), Integer.parseInt(input[1]));
			list.add(j);
		}
		Collections.sort(list);
		return list;
	}
	
	public static List<JobRatio> ratioList(Scanner s)
	{
		List<JobRatio> list = new ArrayList<JobRatio>();
		int size = s.nextInt();
		s.nextLine();
		for(int i = 1; i <= size;i++)
		{
			String[] input = s.nextLine().split(" ");
			JobRatio j = new JobRatio(i, Integer.parseInt(input[0]), Integer.parseInt(input[1]));
			list.add(j);
		}
		Collections.sort(list);
		return list;
	}
	
	public static long sumD(List<JobDiff> list)
	{
		long sum = 0;
		int complete = 0;
		for(int i = 0; i < list.size();i++)
		{
			complete += list.get(i).length;
			sum += list.get(i).weight*complete;
		}
		
		return sum;
	}
	
	public static long sumR(List<JobRatio> list)
	{
		long sum = 0;
		int complete = 0;
		for(int i = 0; i < list.size();i++)
		{
			complete += list.get(i).length;
			sum += list.get(i).weight*complete;
		}
		
		return sum;
	}
	

	
	
}

class JobRatio implements Comparable<JobRatio>
{
	int num;
	double score;
	int weight;
	int length;
	public JobRatio(int n, int w, int l)
	{
		num = n;
		score = ((double)w)/l;
		weight = w;
		length = l;
	}
	@Override
	public int compareTo(JobRatio node) {
	  if(this.score < node.score)
		  return 1;
	  else if(this.score > node.score)
		  return -1;
	  else
		  return (this.weight > node.weight) ? -1 : 1;
	}
	public String toString()
	{
		return String.valueOf(num) +  " " + String.valueOf(score) + " " + String.valueOf(weight) + " " + String.valueOf(length);
	}
}

class JobDiff implements Comparable<JobDiff>
{
	int num;
	int score;
	int weight;
	int length;
	public JobDiff(int n, int w, int l)
	{
		num = n;
		score = w-l;
		weight = w;
		length = l;
	}
	@Override
	public int compareTo(JobDiff node) {
	  if(this.score < node.score)
		  return 1;
	  else if(this.score > node.score)
		  return -1;
	  else
		  return (this.weight > node.weight) ? -1 : 1;
	}
	public String toString()
	{
		return String.valueOf(num) +  " " + String.valueOf(score) + " " + String.valueOf(weight) + " " + String.valueOf(length);
	}
}