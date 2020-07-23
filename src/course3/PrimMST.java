package course3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class PrimMST 
{
	public static void main(String[] args) throws FileNotFoundException 
	{
		// TODO Auto-generated method stub
		PrimMST tree = new PrimMST();
		Scanner s = new Scanner(new File("prim.txt"));
		WeightedGraph g = tree.makeGraph(s);
		System.out.println(tree.algorithm(g));
	}
	
	public int algorithm(WeightedGraph graph)
	{
		int count = 0;
		Set<Vertex> x = new HashSet<Vertex>();
		Set<Vertex> keySet = graph.g.keySet();
		PriorityQueue<QueueNode> min = new PriorityQueue<QueueNode>();
		Vertex start = keySet.iterator().next();
		min.add(new QueueNode(start, 0));
		while(!min.isEmpty())
		{
			QueueNode curr = min.poll();
			while(curr != null && curr.v.popped)
				curr = min.poll();
			if(curr == null)
				break;
			
			count += curr.weight;
			curr.v.popped = true;
			
			List<Edge> list = graph.g.get(curr.v);
			for(int i = 0; i < list.size();i++)
			{
				Edge e = list.get(i);
				Vertex currV = e.dest;
				min.add(new QueueNode(currV, e.weight));
				
			}
			
		}
		return count;
	}
	
	
	public WeightedGraph makeGraph(Scanner s)
	{
		HashMap<Vertex, List<Edge>> map = new HashMap<Vertex, List<Edge>>();
		HashMap<Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
		int numVertices = s.nextInt();
		int numEdges = s.nextInt();
		s.nextLine();
		while(s.hasNextLine())
		{
			String[] input = s.nextLine().split(("\\s+"));
			Vertex v = vertices.get(Integer.parseInt(input[0]));
			if(v == null)
			{
				v = new Vertex(Integer.parseInt(input[0]));
				map.put(v, new LinkedList<Edge>());
				vertices.put(Integer.parseInt(input[0]), v);
			}
			List<Edge> edges = map.get(v);
			Vertex dest =  vertices.get(Integer.parseInt(input[1]));
			if(dest == null)
			{
				dest = new Vertex(Integer.parseInt(input[1]));
				map.put(dest, new LinkedList<Edge>());
				vertices.put(Integer.parseInt(input[1]), dest);
			}
			Edge edge = new Edge(dest,Integer.parseInt(input[2]));
			edges.add(edge);
			Edge edge1 = new Edge(v,Integer.parseInt(input[2]));
			map.get(dest).add(edge1);
		}
		
		
		return new WeightedGraph(map, vertices, numVertices, numEdges);
	} 

}


class WeightedGraph
{
	HashMap<Vertex, List<Edge>> g = new HashMap<Vertex, List<Edge>>();
	HashMap<Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
	int numEdges = 0;
	int numVertices = 0;
	public WeightedGraph(HashMap<Vertex, List<Edge>> g, HashMap<Integer, Vertex> vertices, int v, int e)
	{
		this.g = g;
		numEdges = e;
		numVertices = v;
		this.vertices = vertices;
	}
	
	public String toString()
	{
		return g.toString();
	}
}

class Edge
{
	Vertex dest;
	int weight;
	boolean inX;
	public Edge(Vertex v, int val)
	{
		dest = v;
		weight = val;
		inX = false;
	}
	public String toString()
	{
		return String.valueOf("[" + dest + "," + weight + "]");
	}
}

class Vertex
{
	int val;
	boolean popped = false;
	public Vertex(int v)
	{
		val = v;
	}
	public String toString()
	{
		return String.valueOf(val);
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
