package course2;

import java.io.*;
import java.util.*;

public class DijskstraCleanHeap {

    public static void main(String[] args) 
    {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner s = new Scanner(System.in);
        int N = s.nextInt();
        HashMap<Integer, HashMap<Integer, Double>> map = makeGraph(s,N);
        System.out.println(Math.round(algo(map).get(N)));
        
        
    }
    
    public static Hashtable<Integer, Double> algo(HashMap<Integer, HashMap<Integer, Double>> map) {
        
        Hashtable<Integer, Double> table = new Hashtable<Integer, Double>();
        Set<Integer> x = new HashSet<Integer>();
        PriorityQueue<QueueNode> min = new PriorityQueue<QueueNode>();
        min.add(new QueueNode(1, 0.0));
        while(!min.isEmpty())
        {
            /*
             * extract minimum vertex, 
             *         update distance in Hashtable
             * loop through adjacent vertices, if not in heap, add. if in heap, update distance 
             */
            QueueNode curr = min.poll();
            while(curr != null && x.contains(curr.v))
                curr = min.poll();
            if(curr == null)
                break;
            //curr.v.popped = true;
            x.add(curr.v);
            if(table.get(curr.v) == null)
            {
                table.put(curr.v, curr.weight);
            }
            else
            {
                table.put(curr.v, Math.min(curr.weight, table.get(curr.v)));
            }
            
            final QueueNode currV = curr;
            HashMap<Integer, Double> list = map.get(curr.v);
            list.forEach((k,v) -> {
                
                //final double currW = curr.weight;
                int key = k;
                double value = v;
                if(table.get(k) == null || table.get(k) > currV.weight + value)
                {
                    min.add(new QueueNode(k, currV.weight + value));
                }
            });
            
            
            
        }
        return table;
        
    }
    
    
    public static HashMap<Integer, HashMap<Integer, Double>> makeGraph(Scanner s, int N)
    {
        HashMap<Integer, HashMap<Integer, Double>> map = new HashMap<Integer, HashMap<Integer, Double>> ();
        //int numRoads = s.nextInt();
        int numEdges = s.nextInt(); s.nextLine();
        for(int i = 1; i <= N;i++)
            map.put(i, new HashMap<Integer, Double>());
        for(int i = 0; i < numEdges;i++) {
            int src = s.nextInt();
            int dest = s.nextInt();
            int speed = s.nextInt();
            int len = s.nextInt();
            if(map.get(src).get(dest) == null) {
                HashMap<Integer,Double> edges = map.get(src);
                double time = ((double)len)/((double)(speed));
                edges.put(dest,time);
            } else {
                HashMap<Integer,Double> edges = map.get(src);
                double curr = map.get(src).get(dest);
                double time = ((double)len)/((double)(speed));
                edges.replace(dest,Math.min(curr,time));
            }
        }
        return map;
    }
}


class QueueNode implements Comparable<QueueNode>
{
    int v;
    double weight;
    public QueueNode(int v, double w)
    {
        this.v = v;
        this.weight = w;
    }
    /*public String toString()
    {
        return String.valueOf(v.val +  " " + weight);
    }*/
    @Override
    public int compareTo(QueueNode node) {
      if(this.weight > node.weight)
          return 1;
      else 
          return -1;
    }
}
