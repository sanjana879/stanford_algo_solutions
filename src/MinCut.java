import java.util.Arrays;

public class MinCut 
{
	public int countElements(Node[][] arr)
	{
		int count = 0;
		for(int i = 0; i < arr.length; i++)
		{
			count+= arr[i].length-1;
		}
		return count;
	}

	public static String print(Node[][] arr)
	{
		String output = "";
		for(int i = 0;i<arr.length;i++)
		{
			//System.out.println(output);
			for(int j = 0;j<arr[i].length;j++)
				output += arr[i][j].toString();
			output += '\n';
		}
		return output;
	}
	public static Node[][] contract(Node[][] arr, int big, int small, int ind)
	{
		System.out.println(print(arr));
		int newLen = arr[big].length-1+arr[small].length-2;
		Node smallVal = arr[small][0];
		Node bigVal = arr[big][0];
		Node[][] newArr = new Node[arr.length-1][];
		int newRow = 0;
		for(int i =0;i<arr.length;i++)
		{
			if(i != big && i != small)
			{
				newArr[newRow] = new Node[arr[i].length];
				newArr[newRow][0] = arr[i][0];
				int newInd = 1;
				//copy old values
				for(int j = 1; j < arr[i].length;j++)
				{
					if(arr[i][j].val != smallVal.val)
					{
						newArr[newRow][newInd] = arr[i][j];
						newInd++;
					}
					else if(arr[i][j].val == smallVal.val)
					{
						newArr[newRow][newInd] = new Node(-1);
						newInd++;
					}
				}
				newRow++;
			}
			else if(i == big)
			{
				newArr[newRow] = new Node[newLen];
				newArr[newRow][0] = arr[i][0];
				int newInd = 1;
				//copy old values
				for(int j = 1; j < arr[i].length;j++)
				{
					if(arr[i][j].val != smallVal.val && arr[i][j].val != -1)
					{
						newArr[newRow][newInd] = arr[i][j];
						newInd++;
					}
				}
				//copy new values
				for(int j = 1; j < arr[small].length;j++)
				{
					if(arr[small][j].val != bigVal.val)
					{
						newArr[newRow][newInd] = arr[small][j];
						newInd++;
					}
				}
				System.out.println("fngkafsa " + big + " " + Arrays.toString(newArr[newRow]) + smallVal.val);
				newRow++;
			}
		}
		System.out.println(print(newArr));
		return newArr;
	}
	public Node[][] calcCut(Node[][] arr)
	{
		int total = countElements(arr);
		while(arr.length > 2)
		{
			int ind = (int) ((Math.random() * (total - 1)) + 1);
			int row = 0;
			while(ind / arr[row].length > 0)
			{
				ind %= arr[row].length;
				row++;
			}
			
			
		}
		
		
		return arr;
	}
	public String copy(int n, int k)
    {
        StringBuilder ans = new StringBuilder();
        ArrayList<Integer> list = populate(n);
        int factor = factorial(n);
        int copyN = n;
        int first = 0;
        while(copyN > ((factor)/n)) //6
        {
            first++; 
            copyN -= (factor/n);
        }
        ans.append(String.valueOf(list.get(first)));
        System.out.println(first);
        list.remove(first);
        System.out.println(ans.toString());
        int second = 0;
        while(copyN > ((factor)/(n*(n-1)))) //2
        {
            second++;
            copyN -= factor/(n*(n-1));
        }     
        ans.append(String.valueOf(list.get(second)));
        list.remove(second);
        System.out.println(ans.toString());
        int third = 0;
        while(copyN > ((factor)/(n*(n-1)*(n-2))))
        {
            third++;
            copyN -= factor/(n*(n-1)*(n-2));
        }
        ans.append(String.valueOf(list.get(third)));
        list.remove(third);
        System.out.println(ans.toString());
        for (int s : list)
        {
            ans.append(String.valueOf(s));
            //sb.append("\t");
        }

        //ans += String.join("", list);
        
        /*
            n = 4. 1st - 6, 
            1st - n! / n
            1234, 1243, 1324, 1342, 1423, 1432, 2134, 2143, 2314, 2341, 2413, 2431
            4*3*2 = 24
            
        */
        
        
        
        
        return ans;
    }
 	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
 		Node[][] arr = {{new Node(1),new Node(2),new Node(3)},
 						{new Node(2),new Node(1),new Node(3),new Node(4)},
 						{new Node(3),new Node(1),new Node(2),new Node(4)},
 						{new Node(4),new Node(2),new Node(3)},
 						};
 				/*1 2 3
 				2 1 3 4 
 				3 1 2 4
 				4 2 3 */
 		contract(arr,0,2,2);
 		contract(arr,0,1,1);
 	}
}




class Node {
	int val;
	public Node(int v)
	{
		val = v;
	}
	public String toString()
	{
		return String.valueOf(val) + " ";
	}
}