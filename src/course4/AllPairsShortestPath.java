package course4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;



public class AllPairsShortestPath {

	public static void main(String[] args) throws FileNotFoundException 
	{
		AllPairsShortestPath a = new AllPairsShortestPath();
		Scanner s = new Scanner(new File("allpairs.txt"));
		WeightedGraph g = a.makeGraph(s);
		
		//Floyd Warshall (O(n^3))
		//int shortest = a.algo(g);
		//System.out.println((shortest == Integer.MIN_VALUE ? null : shortest));
		
		//Johnson's Algo (O(nmlog(n)))
		int shortest = a.johnsonsAlgo(g);
		System.out.println((shortest == Integer.MIN_VALUE ? null : shortest));
	}
	
	public int johnsonsAlgo(WeightedGraph g)
	{
		int min = Integer.MAX_VALUE;
		return step1(g);
	}
	
	public int step1(WeightedGraph g)
	{
		Vertex v = new Vertex(0);
		HashMap<Vertex, HashMap<Vertex, Integer>> map = g.g;
		HashMap<Vertex, Integer> table = new HashMap<Vertex, Integer>();
		g.g.keySet().forEach((k) -> {
			table.put(k, 0);
		});
		map.put(v, table);
		g.verticies.put(0, v);
		return bellmanFord(g,new int[g.numVertices+1][g.numVertices+1]);
		
	}
	
	public int bellmanFord(WeightedGraph g, int[][] A)
	{
		for(int i = 0; i < A.length;i++)
		{
			if(i == 0)
				A[0][i] = 0;
			else
				A[0][i] = Integer.MAX_VALUE;
		}
		for(int i = 1; i < A.length;i++)
		{
			int i1 = i;
			g.g.keySet().forEach((v) -> {
				if(g.inEdges.get(v) != null)
				{
					g.inEdges.get(v).forEach((w) -> {
						if(A[i1-1][w.src.val] == Integer.MAX_VALUE)
							A[i1][v.val] = A[i1-1][v.val];
						else
						{
							A[i1][v.val] = Math.min(A[i1][v.val], Math.min(A[i1-1][v.val], A[i1-1][w.src.val] + w.weight));
						}
					});
				}
			});
		}

		return step3(g,A);
	}
	
	public int step3(WeightedGraph g, int[][] A)
	{ 
		g.g.forEach((k,v) -> {
			int srcCost = A[g.numVertices][k.val];
			v.forEach((dest, c) -> {
				c = c + srcCost - A[g.numVertices][dest.val];
				v.replace(dest, c);
			});
		});
		return step4(g, A);
	}

	public int step4(WeightedGraph g, int[][] A)
	{
		HashMap<Vertex, Hashtable<Vertex, Integer>> distances = new HashMap<Vertex, Hashtable<Vertex, Integer>>();
		g.g.keySet().forEach((k) -> {
			distances.put(k, dijsktra(g, k));
		});
		int[] min = {Integer.MAX_VALUE};
		distances.forEach((k,v) -> {
			int[] shortest = {Integer.MAX_VALUE};
			int srcCost = A[g.numVertices][k.val];
			v.forEach((dest, c) -> {
				int cost = c - srcCost + A[g.numVertices][dest.val];
				shortest[0] = Math.min(cost, shortest[0]);
			});
			min[0] = Math.min(min[0], shortest[0]);
		});
		
		return min[0];
		
	}
	public Hashtable<Vertex, Integer> dijsktra(WeightedGraph g, Vertex source)
	{
		Hashtable<Vertex, Integer> table = new Hashtable<Vertex, Integer>();
		Set<Vertex> x = new HashSet<Vertex>();
		PriorityQueue<QueueNode> min = new PriorityQueue<QueueNode>();
		min.add(new QueueNode(source, 0));
		while(!min.isEmpty())
		{
			/*
			 * extract minimum vertex, 
			 * 		update distance in Hashtable
			 * loop through adjacent vertices, if not in heap, add. if in heap, update distance 
			 */
			QueueNode curr = min.poll();
			while(curr != null && x.contains(curr.v))
				curr = min.poll();
			if(curr == null)
				break;
			x.add(curr.v);
			if(table.get(curr.v) == null)
			{
				table.put(curr.v, curr.weight);
			}
			else
			{
				table.put(curr.v, Math.min(curr.weight, table.get(curr.v)));
			}
			
			HashMap<Vertex, Integer> list = g.g.get(curr.v);
			QueueNode curr1 = curr;
			list.forEach((v, cost) -> {
	
				Vertex currV = v;
				if(table.get(currV) == null || table.get(currV) > curr1.weight + cost)
				{
					min.add(new QueueNode(currV, curr1.weight + cost));
				}
			});
			
			
		}
		return table;
	}
	public int algo(WeightedGraph g)
	{
		int[][] A = new int[g.numVertices+1][g.numVertices+1];
		HashMap<Vertex, HashMap<Vertex, Integer>> map = g.g;
		HashMap<Integer, Vertex> nodes = g.verticies;
		int min = Integer.MAX_VALUE;
		for(int i = 0; i <= g.numVertices;i++)
		{
			for(int j = 0; j <= g.numVertices;j++)
			{
				Vertex iVer = nodes.get(i);
				Vertex jVer = nodes.get(j);
				
				if(i == j)
					A[i][j] = 0;
				else if(map.containsKey(iVer) && map.get(iVer).containsKey(jVer))
				{
					A[i][j] = map.get(iVer).get(jVer);
				}
				else
				{
					A[i][j] = Integer.MAX_VALUE;
				}
				if(A[i][j] < min)
					min = A[i][j];
				
			}
		}
		
		for(int k = 1; k <= g.numVertices;k++)
		{
			for(int i = 1; i <= g.numVertices;i++)
			{
				for(int j = 1; j <= g.numVertices;j++)
				{
					if(A[i][k] != Integer.MAX_VALUE && A[k][j] != Integer.MAX_VALUE)
						A[i][j] = Math.min(A[i][j], A[i][k] + A[k][j]);
					else
						A[i][j] = A[i][j];
					if(A[i][j] < min)
						min = A[i][j];
					if(A[i][i] < 0)
						min = Integer.MIN_VALUE;
				}
				
			}
		}
		return min;
	}
	
	public WeightedGraph makeGraph(Scanner s)
	{
		HashMap<Vertex, HashMap<Vertex, Integer>> map = new HashMap<Vertex, HashMap<Vertex, Integer>>();
		HashMap<Integer, Vertex> nodes = new HashMap<Integer, Vertex>();
		HashMap<Vertex, HashSet<Edge>> inEdges=  new HashMap<Vertex, HashSet<Edge>>();
		int numV = s.nextInt();
		int numEdge = s.nextInt();
		s.nextLine();
		while(s.hasNextLine())
		{
			String[] input = s.nextLine().split(("\\s+"));
			int start = Integer.parseInt(input[0]);
			Vertex v = nodes.get(start);
			if(v == null)
			{
				v = new Vertex(start);
				nodes.put(start, v);
				map.put(v, new HashMap<Vertex, Integer>());
			}
			HashMap<Vertex, Integer> edges = (map.get(v) != null) ? map.get(v) : new HashMap<Vertex, Integer>();
			int finish = Integer.parseInt(input[1]);
			Vertex dest = nodes.get(finish);
			if(dest == null)
			{
				dest = new Vertex(finish);
				nodes.put(finish, dest);
				map.put(dest, new HashMap<Vertex, Integer>());
			}
			int w = Integer.parseInt(input[2]);
			if(edges.containsKey(dest))
				edges.replace(dest, Math.min(w, edges.get(dest)));
			else
				edges.put(dest, w);
			
			if(!inEdges.containsKey(dest))
			{
				HashSet<Edge> q = new HashSet<Edge>();
				q.add(new Edge(v, w));
				inEdges.put(dest, q);
			}
			else
			{
				HashSet<Edge> q = inEdges.get(dest);
				q.add(new Edge(v, w));
				inEdges.replace(dest, q);
			}
		}
		
		
		return new WeightedGraph(map,numEdge,nodes,numV,inEdges);
	}

}

class WeightedGraph
{
	HashMap<Vertex, HashMap<Vertex, Integer>> g = new HashMap<Vertex, HashMap<Vertex, Integer>>();
	HashMap<Integer, Vertex> verticies = new HashMap<Integer, Vertex>();
	HashMap<Vertex, HashSet<Edge>> inEdges=  new HashMap<Vertex, HashSet<Edge>>();

	int numEdges = 0;
	int numVertices;
	public WeightedGraph(HashMap<Vertex, HashMap<Vertex, Integer>> e, int n, HashMap<Integer, Vertex> v, int numV, HashMap<Vertex, HashSet<Edge>> in)
	{
		g = e;
		numEdges = n;
		verticies = v;
		numVertices = numV;
		inEdges = in;
	}
	
	public String toString()
	{
		return g.toString();
	}
}

class Edge implements Comparable<Edge>
{
	Vertex src;
	int weight;
	boolean inX;
	public Edge(Vertex v, int val)
	{
		src = v;
		weight = val;
		inX = false;
	}
	public String toString()
	{
		return String.valueOf("[" + src + "," + weight + "]");
	}
	@Override
	public int compareTo(Edge o) 
	{
		if(this.weight > o.weight)
			return 1;
		else if(this.weight < o.weight)
			return -1;
		else
			return 0;
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