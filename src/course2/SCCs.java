package course2;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class SCCs 
{
	Stack<Node> stack = new Stack<Node>();
	public static void main(String[] args) throws FileNotFoundException 
	{
		SCCs min = new SCCs();
		Scanner s = new Scanner(new File("scc.txt"));
		DirectedGraph g = min.makeGraph(s);
		min.algorithm(g);
		
	}
	public Node findNode(int key, Set<Node> keySet)
	{
		Iterator<Node> keys = keySet.iterator();
		while(keys.hasNext())
		{
			Node k = keys.next();
			if(k.val == key)
				return k;
		}
		return null;
	}
	
	public DirectedGraph makeGraph(Scanner s)
	{
		HashMap<Node, List<Node>> edges = new HashMap<Node, List<Node>>();
		HashSet<Integer> v = new HashSet<Integer>();
		while(s.hasNextLine())
		{
			String[] input = s.nextLine().split(" " );
			Node key = new Node(Integer.parseInt(input[0]));
			List<Node> values = edges.get(findNode(key.val,edges.keySet()));
			if(values == null)
			{
				values = new ArrayList<Node>();
				Node added = findNode(Integer.parseInt(input[1]),edges.keySet());
				if(added == null)
				{
					added = new Node(Integer.parseInt(input[1]));
					edges.put(added, new ArrayList<Node>());
				}
				values.add(added);
				edges.put(key, values);
			}
			else
			{
				Node added = findNode(Integer.parseInt(input[1]),edges.keySet());
				if(added == null)
				{
					added = new Node(Integer.parseInt(input[1]));
					edges.put(added, new ArrayList<Node>());
				}
				values.add(added);
			}
		}
		
		DirectedGraph d = new DirectedGraph(edges,v);
		return d;
	}
	int t;
	Node s;
	public void algorithm(DirectedGraph g)
	{
		System.out.println(g.edges);
		DirectedGraph rev = g.reverse();
		System.out.println(rev);
		DFSLoop(rev);
		Iterator<Node> nodes = rev.edges.keySet().iterator();
		while(nodes.hasNext())
		{
			nodes.next().visited = false;
		}
		DFSUtil(g);
	}
	
	public void DFSLoop(DirectedGraph g)
	{
		Iterator<Node> nodes = g.edges.keySet().iterator();
		while(nodes.hasNext())
		{
			Node curr = nodes.next();
			if(!curr.visited)
			{
				s = curr;
				DFS(g,curr);
			}
		}
		
	}
	public void DFSUtil(DirectedGraph g)
	{
		ArrayList<Integer> highest= new ArrayList<Integer>(5);
		//System.out.println(stack);
		while(!stack.isEmpty())
		{
			Node n = stack.pop();//findNode(stack.pop().val,g.edges.keySet());
			if(!n.visited)
			{
				int cnt = DFS2(g,n,0);
				System.out.println("count " + cnt);
			}
		}
	}
	private int DFS2(DirectedGraph g, Node node, int count) 
	{
		node.visited = true;
		count++;
		List<Node> list = g.edges.get(node);
		for(int i = 0;i<list.size();i++)
		{
			Node j = list.get(i);
			if(!j.visited)
			{
				count = DFS2(g,j,count);
			}
		}
		return count;
		
	}
	public void DFS(DirectedGraph g, Node n)
	{
		n.visited = true;
		n.leader = s;
		List<Node> list = g.edges.get(n);
		
		for(int i = 0;i<list.size();i++)
		{
			Node j = list.get(i);
			if(!j.visited)
			{
				DFS(g,j);
			}
		}
		t++;
		n.finish = t;
		stack.push(n);
	}
	
	

}

class DirectedGraph 
{
	HashMap<Node, List<Node>> edges = new HashMap<Node, List<Node>>();
	HashSet<Integer> vertices = new HashSet<Integer>();
	public DirectedGraph()
	{
		edges = new HashMap<Node, List<Node>>();
	}
	public DirectedGraph(HashMap<Node,List<Node>> e)
	{
		edges = e;
	}
	public DirectedGraph(HashMap<Node,List<Node>> e,HashSet<Integer> v)
	{
		edges = e;
		vertices = v;
	}
	
	public DirectedGraph reverse()
	{
		DirectedGraph rev = new DirectedGraph();
		HashMap<Node, List<Node>> edgesNew = rev.edges;
		HashSet<Integer> v =rev.vertices;
		Set<Node> ogKeys = this.edges.keySet();
		Iterator<Node> ogKeyIter = ogKeys.iterator();
		while(ogKeyIter.hasNext())
		{
			Node key = ogKeyIter.next();
			boolean newKey = edgesNew.containsKey(key);
			if(newKey == false)
				edgesNew.put(key, new ArrayList<Node>());
			List<Node> vals = this.edges.get(key);
			vals.forEach((value) -> {
				List<Node> newList = edgesNew.get(value);
				if(newList == null)
				{
					newList = new ArrayList<Node>();
					newList.add(key);
					edgesNew.put(value, newList);
				}
				else
				{
					newList.add(key);
				}
				v.add(value.val);
				
			});
		}
		
		return rev;
	}
	public String toString()
	{
		return edges.toString();
	}
	
}


class Node 
{
	int val;
	int finish;
	boolean visited;
	Node leader;
	public Node(int v)
	{
		val = v;
		visited = false;
	}
	public String toString()
	{
		return String.valueOf(val);
	}
}