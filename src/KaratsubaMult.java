import java.math.BigInteger;

public class KaratsubaMult 
{
	public static void main(String[] args)
	{
		
		KaratsubaMult m = new KaratsubaMult();
		BigInteger x = new BigInteger("5678");
		BigInteger y = new BigInteger("1234");
		int lenX = x.toString().length();
		int lenY = y.toString().length();
		
		
		BigInteger xy = m.mult(x,y);
		System.out.println(xy);
	}
	
	public BigInteger mult(BigInteger x, BigInteger y)
	{
		BigInteger Ten = new BigInteger("10");
		BigInteger One = new BigInteger("1");
		BigInteger Two = new BigInteger("2");

		//System.out.println(x + " " + y);
	    if (x.compareTo(Ten) == -1 && y.compareTo(Ten) == -1)
		{
			return x.multiply(y);
		}
	    
	    String length = getLength(x.max(y));
	    BigInteger len = new BigInteger(length);		
	    BigInteger newA,newB,newC,newD;

		len=len.add(len.mod(Two));
		
		newA = x.divide(Ten.pow(len.divide(Two).intValue()));
		newB = x.subtract((Ten.pow(len.divide(Two).intValue())).multiply(newA));
		newC = y.divide(Ten.pow(len.divide(Two).intValue()));
		newD = y.subtract((Ten.pow(len.divide(Two).intValue())).multiply(newC));

		BigInteger ac = mult(newA, newC);
		BigInteger bd = mult(newB, newD);
		BigInteger abcd = mult(newA.add(newB),newC.add(newD));
		
		BigInteger z0 = ac.multiply(Ten.pow(len.intValue()));
		BigInteger z1 = ((abcd.subtract(ac).subtract(bd)) .multiply(Ten.pow((len.divide(Two)).intValue())));
		
		return z0.add(z1).add(bd);
	}
	public static String getLength( BigInteger a){
	    String b = a.toString();
	    return Integer.toString(b.length());
	}
	/*
	 * 5678 1000
56 10
5 1
6 0
11 1
1 0
1 1
2 1
78 0
7 0
8 0
15 0
1 0
5 0
6 0
134 10
1 0
34 10
3 1
4 0
7 1
35 10
3 1
5 0
8 1
5678000

	 */
	
	/*
	 * if(lenX % 2 == 1)
		{
			if(lenX != 1)
			{
				newA = new BigInteger(x.toString().substring(0,(lenX/2)+1));
				newB = new BigInteger(x.toString().substring((lenX/2)+1));
			}
			else
			{
				newA = new BigInteger("0");
				newB = new BigInteger(x.toString().substring(0,(lenX/2)+1));
			}
		}
		else
		{
			if(lenX != 1)
			{
				newA = new BigInteger(x.toString().substring(0,(lenX/2)));
				newB = new BigInteger(x.toString().substring((lenX/2)));
			}
			else
			{
				newA = new BigInteger("0");
				newB = new BigInteger(x.toString().substring(0,(lenX/2)+1));
			}
		}
		//System.out.println(x + " " + y);
		if(lenY % 2 == 1)
		{
			if(lenY != 1)
			{
				newC = new BigInteger(y.toString().substring(0,(lenY/2)+1));
				newD = new BigInteger(y.toString().substring((lenY/2)+1));
			}
			else
			{
				newC = new BigInteger("0");
				newD = new BigInteger(y.toString().substring(0,(lenY/2)+1));
			}
		}
		else
		{
			if(lenY != 1)
			{
				newC = new BigInteger(y.toString().substring(0,(lenY/2)));
				newD = new BigInteger(y.toString().substring((lenY/2)));
			}
			else
			{
				newC = new BigInteger("0");
				newD = new BigInteger(y.toString().substring(0,(lenY/2)));
			}
		}
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*public static int multiply(int x,int y, int lenX, int lenY)
	{
		if(String.valueOf(x).length() == 1  && String.valueOf(y).length() == 1)
		{
			System.out.println("base " + x + " " + y + " " + lenX + " " + lenY);
			return x * y;
		}
		int newA, newB, newC, newD;
		System.out.println("Lengths " + x + " " + lenX + " " + (int)(lenX/2.0) + " "  + y + " "+ lenY + " " + (int)(lenY/2.0));
		if(String.valueOf(x).length() != 1)
		{
			System.out.println(x + " " + lenX);
			newA = Integer.parseInt(String.valueOf(x).substring(0,(int)(String.valueOf(x).length()/2.0)));
			newB = Integer.parseInt(String.valueOf(x).substring((int)(String.valueOf(x).length()/2.0)));
		}
		else
		{
			newA = x;
			newB = x;
		}
		if(String.valueOf(y).length() != 1)
		{
			newC = Integer.parseInt(String.valueOf(y).substring(0,(int)(String.valueOf(y).length()/2.0)));
			newD = Integer.parseInt(String.valueOf(y).substring((int)(String.valueOf(y).length()/2.0)));
		}
		else
		{
			newC = y;
			newD = y;
		}
		System.out.println(x + " " + y + " " + newA + " " + newB + " " +newC +  " " + newD);
		
		int ac = multiply(newA,newC,(int)(lenX/2.0),(int)(lenY/2.0));
		int bd = multiply(newB,newD,(int)(lenX/2.0),(int)(lenY/2.0));
		int abcd = multiply(newA+newB,newC+newD,(int)(lenX/2.0),(int)(lenY/2.0));
		//System.out.println("end " + ac + " " + bd + " " + abcd);
		return (int) (Math.pow(10, lenX)*ac + 
				(Math.pow(10, (int)(lenX/2.0))*(abcd-ac-bd)) + 
				bd - ac - bd);
		
	}*/
	
	 
}
