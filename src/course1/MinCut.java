package course1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class MinCut 
{
	int smallestNum = Integer.MAX_VALUE;
	Graph smallest;
	
	public Vertex findVertex(HashMap<Vertex, List<Vertex>> adjList, int val)
	{
		Iterator<Vertex> list = adjList.keySet().iterator();
		while(list.hasNext())
		{
			Vertex c = list.next();
			if(c.val == val)
				return c;
		}
		return null;
	}
	
	public void repeat(Graph g)
	{
		int times = g.adjList.keySet().size();
		
		for(int i = 0; i < times*times;i++)
		{
			Graph original = new Graph();
			original.adjList.putAll(g.adjList);
			
			Graph newG = new Graph( g.copy(g.adjListCloned));
			findMin(newG);
			
		}
		System.out.println(smallestNum);
	}
	
	public Graph findMin(Graph g)
	{
		int size = g.adjList.size();
		int numEdges = countEdges(g.adjList);
		while(size > 2)
		{
			 int rand = (int) ((Math.random() * (numEdges - 1)) + 1);
			 Vertex[] returned = findVertices(g.adjList,rand);
			 g.merge(returned[0], findVertex(g.adjList,returned[1].val));
			 size --;
			 numEdges = countEdges(g.adjList);
		}
		if(numEdges < smallestNum)
		{
			smallestNum = numEdges;
			smallest = new Graph(g.adjList);
		}
		
		return new Graph(g.adjList);
	}
	
	public Vertex[] findVertices(HashMap<Vertex, List<Vertex>> adjList, int num)
	{
		int copy = num;
		Vertex[] returned = new Vertex[2];
		Iterator<Vertex> keySet = adjList.keySet().iterator();
		Iterator<List<Vertex>> valSet = adjList.values().iterator();
		Vertex curr = keySet.next();
		List<Vertex> currList = valSet.next();
		num -= currList.size();
		while(keySet.hasNext() && valSet.hasNext()  && num > 0)
		{
			curr = keySet.next();
			currList = valSet.next();
			num -= currList.size();

			
		}
		if(num <= 0)
		{
			returned[0] = curr;
			returned[1] = (currList.get(currList.size()+num-1));
		}
		return returned;
	}
	

	
	public int countEdges(HashMap<Vertex, List<Vertex>> adjList)
	{
		Iterator<List<Vertex>> valSet =adjList.values().iterator();
		int count = 0;
		while(valSet.hasNext())
		{
			int size = valSet.next().size();
			count += size;	
		}
		return count/2;
	}
	
	public Graph makeGraph(Scanner s)
	{
		HashMap<Vertex, List<Vertex>> list = new HashMap<Vertex, List<Vertex>>();
		while(s.hasNextLine())
		{
			String[] input = s.nextLine().split(" " );
			Vertex key = new Vertex(Integer.parseInt(input[0]));
			List<Vertex> values = new ArrayList<Vertex>();
			for(int i = 1; i < input.length;i++)
			{
				values.add(new Vertex(Integer.parseInt(input[i])));
			}
			list.put(key, values);
		}
		Graph g = new Graph(list);
		return g;
	}
	
	public static void main(String[] arg) throws FileNotFoundException
	{
		MinCut min = new MinCut();
		Scanner s = new Scanner(new File("minCut.txt"));
		Graph g = min.makeGraph(s);
		min.repeat(g);
		
	}
}

class Graph
{
	HashMap<Vertex, List<Vertex>> adjList;
	HashMap<Vertex, List<Vertex>> adjListCloned;
	int edges;
	public String toString()
	{
		return adjList.toString();
	}
	
	public Vertex findVertex(int val)
	{
		Iterator<Vertex> list = adjList.keySet().iterator();
		while(list.hasNext())
		{
			Vertex c = list.next();
			if(c.val == val)
				return c;
		}
		return null;
	}
	public HashMap <Vertex, List<Vertex>> copy(HashMap<Vertex, List<Vertex>> original)
	{
	    HashMap<Vertex, List<Vertex>> copy = new HashMap<Vertex, List<Vertex>>();
	    for (Vertex entry : original.keySet())
	    {
	    		
	        copy.put(new Vertex(entry.val),
	           new ArrayList<Vertex>(original.get(entry)));
	    }
	    return (copy);
	}
	public void merge(Vertex a, Vertex b)
	{
		List<Vertex> aList = adjList.get(a);
		List<Vertex> bList = adjList.get(b);

		bList.forEach((value) -> {
			if(value.val != b.val) 
				aList.add(value);
			List<Vertex> temp = adjList.get(findVertex(value.val));
			temp.add(a);
		});
		
		Iterator<Vertex> keySet = adjList.keySet().iterator();
		while(keySet.hasNext())
		{
			Vertex v = keySet.next();
			List<Vertex> currList = adjList.get(v);
			ArrayList<Vertex> toRemove = new ArrayList<Vertex>();
			currList.forEach((value) -> {
				//remove self loops
				if(value.val == b.val || value.val == v.val) 
					toRemove.add(value);
			});
			for(int i = 0; i < toRemove.size();i++)
				currList.remove(toRemove.get(i));
		}
		adjList.remove(b);
	}
	public void countEdges(Graph g)
	{
		Iterator<List<Vertex>> valSet = g.adjList.values().iterator();
		int count = 0;
		while(valSet.hasNext())
		{
			count += valSet.next().size();
		}
		edges = count;
	}
	public Graph(HashMap<Vertex, List<Vertex>> a)
	{
		adjList = a;
		adjListCloned = copy(a);
	}
	public Graph()
	{
		adjList = new HashMap<Vertex, List<Vertex>>();
	}
	
}
class Vertex
{
	int val;
	public Vertex(int v)
	{
		val = v;
	}
	public String toString()
	{
		return String.valueOf(val);
	}
}