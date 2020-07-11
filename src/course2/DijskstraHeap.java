package course2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class DijskstraHeap 
{
	public static void main(String[] arg) throws FileNotFoundException
	{
		DijskstraHeap d = new DijskstraHeap();
		Scanner s = new Scanner(new File("dijskstraheap.txt"));
		WeightedGraph g = d.makeGraph(s);
		Hashtable<Vertex, Integer> table = d.algorithm(g);
		System.out.println(table.get(d.findNode(7,g.g.keySet())));
		System.out.println(table.get(d.findNode(37,g.g.keySet())));
		System.out.println(table.get(d.findNode(59,g.g.keySet())));
		System.out.println(table.get(d.findNode(82,g.g.keySet())));
		System.out.println(table.get(d.findNode(99,g.g.keySet())));
		System.out.println(table.get(d.findNode(115,g.g.keySet())));
		System.out.println(table.get(d.findNode(133,g.g.keySet())));
		System.out.println(table.get(d.findNode(165,g.g.keySet())));
		System.out.println(table.get(d.findNode(188,g.g.keySet())));
		System.out.println(table.get(d.findNode(197,g.g.keySet())));
	}
	
	public Hashtable<Vertex, Integer> algorithm(WeightedGraph g)
	{
		Hashtable<Vertex, Integer> table = new Hashtable<Vertex, Integer>();
		Set<Vertex> x = new HashSet<Vertex>();
		Set<Vertex> keySet = g.g.keySet();
		PriorityQueue<QueueNode> min = new PriorityQueue<QueueNode>();
		Vertex start = findNode(1, keySet);
		min.add(new QueueNode(start, 0));
		while(!min.isEmpty())
		{
			/*
			 * extract minimum vertex, 
			 * 		update distance in Hashtable
			 * loop through adjacent vertices, if not in heap, add. if in heap, update distance 
			 */
			QueueNode curr = min.poll();
			while(curr != null && curr.v.popped)
				curr = min.poll();
			if(curr == null)
				break;
			curr.v.popped = true;
			if(table.get(curr.v) == null)
			{
				table.put(curr.v, curr.weight);
			}
			else
			{
				table.put(curr.v, Math.min(curr.weight, table.get(curr.v)));
			}
			
			List<Edge> list = g.g.get(curr.v);
			for(int i = 0; i < list.size();i++)
			{
				Edge e = list.get(i);
				Vertex currV = e.dest;
				//use inHeap to determine if in Heap
				if(table.get(currV) == null || table.get(currV) > curr.weight + e.weight)
				{
					min.add(new QueueNode(currV, curr.weight + e.weight));
				}
			}
		}
		return table;
	}
	
	
	public Vertex findNode(int key, Set<Vertex> keySet)
	{
		Iterator<Vertex> keys = keySet.iterator();
		while(keys.hasNext())
		{
			Vertex k = keys.next();
			if(k.val == key)
				return k;
		}
		return null;
	}
	
	public WeightedGraph makeGraph(Scanner s)
	{
		HashMap<Vertex, List<Edge>> map = new HashMap<Vertex, List<Edge>>();
		int numEdge = 0;
		while(s.hasNextLine())
		{
			String[] input = s.nextLine().split(("\\s+"));
			Vertex v = findNode(Integer.parseInt(input[0]), map.keySet());
			if(v == null)
			{
				v = new Vertex(Integer.parseInt(input[0]));
				map.put(v, new LinkedList<Edge>());
			}
			List<Edge> edges = map.get(v);
			for(int i = 1;i < input.length;i++)
			{
				String[] eg = input[i].split(",");
				Vertex dest = findNode(Integer.parseInt(eg[0]), map.keySet());
				if(dest == null)
				{
					dest = new Vertex(Integer.parseInt(eg[0]));
					map.put(dest, new LinkedList<Edge>());
				}
				Edge edge = new Edge(dest,Integer.parseInt(eg[1]));
				edges.add(edge);
				Edge edge1 = new Edge(v,Integer.parseInt(eg[1]));
				map.get(dest).add(edge1);
				numEdge+=2;
			}
		}
		
		
		return new WeightedGraph(map,numEdge);
	}
}

class QueueNode implements Comparable<QueueNode>
{
	Vertex v;
	int weight;
	public QueueNode(Vertex v, int w)
	{
		this.v = v;
		this.weight = w;
	}
	public String toString()
	{
		return String.valueOf(v.val +  " " + weight);
	}
	@Override
	public int compareTo(QueueNode node) {
	  if(this.weight > node.weight)
		  return 1;
	  else 
		  return -1;
	}
}
