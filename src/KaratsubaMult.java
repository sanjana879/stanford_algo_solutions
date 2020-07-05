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
	 
}
