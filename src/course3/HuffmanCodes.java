package course3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class HuffmanCodes 
{
	int minDepth = Integer.MAX_VALUE;
	int maxDepth = Integer.MIN_VALUE;
	
	public static void main(String[] args) throws FileNotFoundException 
	{
		// TODO Auto-generated method stub
		HuffmanCodes h = new HuffmanCodes();
		Scanner s = new Scanner(new File("huffman.txt"));
		PriorityQueue<TreeNode> heap = h.makeNodes(s);
		h.iterative(heap);
		System.out.println(h.maxDepth);
		System.out.println(h.minDepth);
	}
	
	public PriorityQueue<TreeNode> makeNodes(Scanner s)
	{
		int size = s.nextInt();
		PriorityQueue<TreeNode> minHeap = new PriorityQueue<TreeNode>();
		while(s.hasNextInt())
		{
			s.nextLine();
			long currW = s.nextInt();
			TreeNode curr = new TreeNode(currW, -1, 0,0);
			minHeap.add(curr);
		}
		return minHeap;
	}
	
	public TreeNode iterative(PriorityQueue<TreeNode> heap)
	{
		while(heap.size() > 1)
		{
			TreeNode v1 = heap.poll();
			TreeNode v2 = heap.poll(); //larger weight
			TreeNode combine = new TreeNode(v1.weight + v2.weight, -1, Math.max(v1.depth, v2.depth) + 1,Math.min(v1.minDepth, v2.minDepth) + 1);
			combine.left = v1;
			combine.right = v2;
			heap.add(combine);
		}
		maxDepth = heap.peek().depth;
		minDepth = heap.peek().minDepth;
		return heap.peek();
	}

}

class TreeNode implements Comparable<TreeNode>
{
	long weight;
	Stack<TreeNode> meta;
	TreeNode left;
	TreeNode right;
	int edgeVal;
	int depth;
	int minDepth;
	public TreeNode(long w, int e, int d, int m)
	{
		weight = w;
		//meta = s;
		edgeVal = e;
		depth = d;
		minDepth = m;
	}
	public TreeNode(TreeNode left, TreeNode right)
	{
		weight = -1;
		meta = null;
		this.left = left;
		this.right = right;
		edgeVal = -1;
		depth = 0;
	}
	@Override
	public int compareTo(TreeNode o) 
	{
		if(this.weight > o.weight)
			return 1;
		else
			return -1;
	}
	public String toString()
	{
		return String.valueOf(weight);
	}
	
	
}