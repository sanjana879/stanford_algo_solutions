import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Dijskstra 
{

	public static void main(String[] args) throws FileNotFoundException 
	{
		// TODO Auto-generated method stub
		Dijskstra min = new Dijskstra();
		Scanner s = new Scanner(new File("dijskstra.txt"));
		WeightedGraph g = min.makeGraph(s);
		System.out.println(g);
		Hashtable<Vertex, Integer> table = (min.algorithm(g));
		System.out.println(table);
		System.out.println(table.get(min.findNode(7,g.g.keySet())));
		System.out.println(table.get(min.findNode(37,g.g.keySet())));
		System.out.println(table.get(min.findNode(59,g.g.keySet())));
		System.out.println(table.get(min.findNode(82,g.g.keySet())));
		System.out.println(table.get(min.findNode(99,g.g.keySet())));
		System.out.println(table.get(min.findNode(115,g.g.keySet())));
		System.out.println(table.get(min.findNode(133,g.g.keySet())));
		System.out.println(table.get(min.findNode(165,g.g.keySet())));
		System.out.println(table.get(min.findNode(188,g.g.keySet())));
		System.out.println(table.get(min.findNode(197,g.g.keySet())));
		
		
	}
	
	public Hashtable<Vertex, Integer> algorithm(WeightedGraph g)
	{
		Set<Vertex> x = new HashSet<Vertex>();
		Set<Vertex> keySet = g.g.keySet();
		Hashtable<Vertex, Integer> table = new Hashtable<Vertex, Integer>();
		Vertex curr = findNode(1,keySet);
		x.add(curr);
		table.put(curr, 0);
		int num = 0;
		while(!x.equals(keySet))
		{
			/* go through X 
			 * find adjacent edges
			 * if adjacent edge isn't in X (boolean) -> compare w/ smallest and save smallest Edge 
			 * once smallest edge found
			 * 		change boolean and add to X 
			 * 		table.put(smallest, curr.weight + smallest.weight)
			 */
			Iterator<Vertex> iter = x.iterator();
			Edge smallest = null;
			Vertex smallVertex = null;
			int smallestWeight = Integer.MAX_VALUE;
			while(iter.hasNext())
			{
				Vertex currVertex = iter.next();
				List<Edge> list = g.g.get(currVertex);
				for(int i = 0;i<list.size();i++)
				{
					Edge value = list.get(i);
					
					if(!value.inX)
					{
						if(smallest == null || smallestWeight > value.weight +table.get(currVertex) )
						{
							smallest = value;
							smallVertex = currVertex;
							smallestWeight = value.weight + table.get(currVertex);
						}
						
					}
				}
				
			}
			if(smallest == null)
				break;
			x.add(smallest.dest);
			if(table.get(smallest.dest)==null)
				table.put(smallest.dest, table.get(smallVertex) + smallest.weight);
			else
			{
				table.put(smallest.dest, Math.min(table.get(smallVertex) + smallest.weight, table.get(smallest.dest)));
			}
			smallest.inX = true;
			curr = smallest.dest;
			num++;
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

class WeightedGraph
{
	HashMap<Vertex, List<Edge>> g = new HashMap<Vertex, List<Edge>>();
	int numEdges = 0;
	public WeightedGraph(HashMap<Vertex, List<Edge>> e, int n)
	{
		g = e;
		numEdges = n;
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
