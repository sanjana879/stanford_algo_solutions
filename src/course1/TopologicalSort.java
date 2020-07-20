package course1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class TopologicalSort {
	HashMap<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
    boolean[] flags;
    int[] order;
    int current;
    boolean isCycle; 
    
    public static void main(String[] args) throws FileNotFoundException 
	{
    		TopologicalSort t = new TopologicalSort();
    		Scanner s = new Scanner(new File("top.txt"));
    		int[][] nums = t.graph(s);
    		System.out.println(Arrays.toString(t.findOrder(4, nums)));
	}
    
    public int[][] graph(Scanner s)
    {
    		int size = s.nextInt();
    		s.nextLine();
    		int[][] nums = new int[size][2];
    		int i = 0;
    		while(s.hasNextLine())
    		{
    			String[] str = s.nextLine().split(" ");
    			nums[i][0] = Integer.parseInt(str[0]);
    			nums[i][1] = Integer.parseInt(str[1]);
    			i++;
    		}
    		return nums;
    		
    }
    
    public int[] findOrder(int numCourses, int[][] prerequisites) 
    {
        flags = new boolean[numCourses];
        order = new int[numCourses];
        isCycle = false;
        for(int i =0; i < prerequisites.length;i++)
        {
            int key = prerequisites[i][0];
            if(map.containsKey(key))
            {
                List<Integer> list = map.get(key);
                list.add(prerequisites[i][1]);
            }
            else
            {
                List<Integer> list = new ArrayList<Integer>();
                list.add(prerequisites[i][1]);
                map.put(key, list);
            }
            int val = prerequisites[i][1];
            if(!map.containsKey(val))
            {
                List<Integer> list = new ArrayList<Integer>();
                map.put(val, list);
            }
            
        }

        loop(numCourses);
        if(isCycle)
            return new int[0];
        
        for(int i = 0; i < flags.length;i ++)
        {
            
            if(!flags[i])
            {
                order[current] = i;
                current++;
            }
        }
        return order;
    }
    
    public void loop(int n)
    {
        current = 0;
        Set<Integer> keySet = map.keySet();
        Iterator<Integer> iter = keySet.iterator();

        boolean[] onStack = new boolean[n];
        while(iter.hasNext() && !isCycle)
        {
            int num = iter.next();
            if(!flags[num])
                DFS(num,onStack);
        }
    }
    
    public void DFS(int start, boolean[] onStack)
    {
        flags[start] = true;
        onStack[start] = true;
        List<Integer> edges = map.get(start);
        for(int i = 0; i < edges.size(); i++)
        {
            if(onStack[edges.get(i)])
                isCycle = true;
            if(!flags[edges.get(i)])
            {
                DFS(edges.get(i), onStack);
            }
        }
        onStack[start] = false;
        order[current] = start;
        current ++ ;
    }
	

}
