package course4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class AllPairsShortestPath {

	public static void main(String[] args) throws FileNotFoundException 
	{
		AllPairsShortestPath a = new AllPairsShortestPath();
		Scanner s = new Scanner(new File("allpairs.txt"));
		WeightedGraph g = a.makeGraph(s);
		System.out.println(g);
		int shortest = a.algo(g);
		System.out.println((shortest == Integer.MIN_VALUE ? null : shortest));

	}
	
	public int algo(WeightedGraph g)
	{
		int[][][] A = new int[g.numVertices+1][g.numVertices+1][g.numVertices+1];
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
					A[i][j][0] = 0;
				else if(map.containsKey(iVer) && map.get(iVer).containsKey(jVer))
				{
					A[i][j][0] = map.get(iVer).get(jVer);
				}
				else
				{
					A[i][j][0] = Integer.MAX_VALUE;
				}
				if(A[i][j][0] < min)
					min = A[i][j][0];
				
			}
		}
		
		for(int k = 1; k <= g.numVertices;k++)
		{
			for(int i = 1; i <= g.numVertices;i++)
			{
				for(int j = 1; j <= g.numVertices;j++)
				{
					if(A[i][k][k-1] != Integer.MAX_VALUE && A[k][j][k-1] != Integer.MAX_VALUE)
						A[i][j][k] = Math.min(A[i][j][k-1], A[i][k][k-1] + A[k][j][k-1]);
					else
						A[i][j][k] = A[i][j][k-1];
					if(A[i][j][k] < min)
						min = A[i][j][k];
					if(A[i][i][k] < 0)
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
			
			
		}
		
		
		return new WeightedGraph(map,numEdge,nodes,numV);
	}

}

class WeightedGraph
{
	HashMap<Vertex, HashMap<Vertex, Integer>> g = new HashMap<Vertex, HashMap<Vertex, Integer>>();
	HashMap<Integer, Vertex> verticies = new HashMap<Integer, Vertex>();
	int numEdges = 0;
	int numVertices;
	public WeightedGraph(HashMap<Vertex, HashMap<Vertex, Integer>> e, int n, HashMap<Integer, Vertex> v, int numV)
	{
		g = e;
		numEdges = n;
		verticies = v;
		numVertices = numV;
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