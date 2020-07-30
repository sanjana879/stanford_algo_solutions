package course3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Clustering 
{
	public static void main(String[] args) throws FileNotFoundException 
	{
		// TODO Auto-generated method stub
		Clustering obj = new Clustering();
		Scanner s = new Scanner(new File("cluster.txt"));
		Union_Find g = obj.create(s);
		obj.algo(g);
		System.out.println(g.edges.get(0).cost);
	}
	
	public Union_Find create(Scanner s)
	{
		HashSet<Cluster> graph = new HashSet<Cluster>();
		HashMap<VertexPt, Cluster> map = new HashMap<VertexPt, Cluster>();
		HashMap<Integer, VertexPt> vertices = new HashMap<Integer, VertexPt>();
		ArrayList<EdgePt> edges = new ArrayList<EdgePt>();
		
		int size = s.nextInt();
		s.nextLine();
		while(s.hasNextLine())
		{
			String[] input = s.nextLine().split(" ");
			int src = Integer.parseInt(input[0]);
			int dest = Integer.parseInt(input[1]);
			int cost = Integer.parseInt(input[2]);
			if(vertices.get(src) == null)
			{
				VertexPt source = new VertexPt(src);
				VertexPt end ;
				if(vertices.get(dest) == null)
				{
					end = new VertexPt(dest);
					source.distances.put(end, cost);
					end.distances.put(source, cost);
					Cluster c2 = new Cluster(new LinkedList<VertexPt>(), end.leader);
					c2.list.add(end);
					
					//insert 
					map.put(end, c2);
					vertices.put(dest, end);
					graph.add(c2);
				}
				else
				{
					end = vertices.get(dest);
					source.distances.put(end, cost);
					end.distances.put(source, cost);
				}
				Cluster c1 = new Cluster(new LinkedList<VertexPt>(), source.leader);
				c1.list.add(source);
				//insert
				map.put(source, c1);
				vertices.put(src, source);
				graph.add(c1);
				EdgePt edge = new EdgePt(source,end,cost);
				edges.add(edge);
				
			}
			else
			{
				VertexPt source = vertices.get(src);
				VertexPt end;
				if(vertices.get(dest) == null)
				{
					end = new VertexPt(dest);
					source.distances.put(end, cost);
					end.distances.put(source, cost);
					Cluster c2 = new Cluster(new LinkedList<VertexPt>(), end.leader);
					c2.list.add(end);
					
					//insert 
					map.put(end, c2);
					vertices.put(dest, end);
					graph.add(c2);
					
				}
				else
				{
					end = vertices.get(dest);
					source.distances.put(end, cost);
					end.distances.put(source, cost);
				}
				EdgePt edge = new EdgePt(source,end,cost);
				edges.add(edge);
				
			}
		}
		return new Union_Find(graph, map, vertices, edges);
	}
	
	public void algo(Union_Find g)
	{
		//sort edges 
		Collections.sort(g.edges);
		
		while(g.graph.size() > 4)
		{
			EdgePt curr = g.edges.remove(0);
			while(curr.v1.leader.equals(curr.v2.leader))
			{
				curr = g.edges.remove(0);
			}
			Cluster c1 = g.map.get(curr.v1);
			Cluster c2 = g.map.get(curr.v2);
			g.union(c1,c2);
		}
		EdgePt curr = g.edges.get(0);
		while(curr.v1.leader.equals(curr.v2.leader))
		{
			g.edges.remove(0);
			curr = g.edges.get(0);
		}
	}

}

class Union_Find
{
	HashSet<Cluster> graph;
	HashMap<VertexPt, Cluster> map;
	HashMap<Integer, VertexPt> vertices;
	ArrayList<EdgePt> edges;
	
	public Union_Find(HashSet<Cluster> c, HashMap<VertexPt, Cluster> m, HashMap<Integer, VertexPt> v, ArrayList<EdgePt> e)
	{
		graph  = c;
		map = m;
		vertices = v;
		edges = e;
	}
	
	public Cluster find(VertexPt v)
	{
		return map.get(v);
	}
	
	public void union(Cluster c1, Cluster c2)
	{
		Cluster smaller = (c1.list.size() > c2.list.size()) ? c2 : c1;
		Cluster larger = (c1.list.size() <= c2.list.size()) ? c2 : c1;
		List<VertexPt> smallList = smaller.list;
		for(int i = 0; i < smallList.size();i++)
		{
			VertexPt curr = smallList.get(i);
			curr.leader = larger.leader;
			larger.list.add(curr);
			map.replace(curr, larger);
		}
		graph.remove(smaller);
	}
	
	public String toString()
	{
		return String.valueOf(graph) + "\n" + edges;
	}
}

class EdgePt implements Comparable<EdgePt>
{
	VertexPt v1;
	VertexPt v2;
	int cost;
	public EdgePt(VertexPt v1, VertexPt v2, int c)
	{
		this.v1 = v1;
		this.v2 = v2;
		this.cost = c;
	}
	@Override
	public int compareTo(EdgePt node) {
	  if(this.cost >= node.cost)
		  return 1;
	  else 
		  return -1;
	}
	
	public String toString()
	{
		return String.valueOf(v1.val) + " " + String.valueOf(v2.val) + " " + cost;
	}
}

class Cluster
{
	LinkedList<VertexPt> list = new LinkedList<VertexPt>();
	VertexPt leader;
	public Cluster(LinkedList<VertexPt> list, VertexPt l)
	{
		this.list = list;
		leader = l;
	}
	public String toString()
	{
		return String.valueOf(leader.val) + " " + list;
	}
}

class VertexPt
{
	int val;
	VertexPt leader;
	HashMap<VertexPt, Integer> distances;
	public VertexPt(int val)
	{
		this.val = val;
		this.leader = this;
		this.distances = new HashMap<VertexPt, Integer> ();
	}
	public VertexPt(int val, HashMap<VertexPt, Integer> distances)
	{
		this.val = val;
		this.leader = this;
		this.distances = distances;
	}
	public String toString()
	{
		return String.valueOf(val) + " " + distances.values();
	}
}
