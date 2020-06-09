/*
 *    Java Program to Implement Karatsuba Multiplication Algorithm
 **/
 
import java.math.BigInteger;
import java.util.Scanner;
 
/** Class Karatsuba **/
public class KaratsubaAns
{
	public  BigInteger karatsuba3(BigInteger i, BigInteger j){
		BigInteger Ten = new BigInteger("10");
		BigInteger Two = new BigInteger("2");
		BigInteger One = new BigInteger("1");
		//System.out.println(i +" " + j);
	    if (i.compareTo(Ten) == -1 && j.compareTo(Ten) == -1)
	        return i.multiply(j);
	    String length = getLength(i.max(j));
	    BigInteger n = new BigInteger(length);
	    if (n.mod(Two).equals(One))
	        n = n.add(One);

	    BigInteger a = i.divide(Ten.pow(n.divide(Two).intValue()));
	    BigInteger b = i.mod(Ten.pow(n.divide(Two).intValue()));
	    BigInteger c = j.divide(Ten.pow(n.divide(Two).intValue()));
	    BigInteger d = j.mod(Ten.pow(n.divide(Two).intValue()));

	    BigInteger first = karatsuba3(a,c);
	    BigInteger second = karatsuba3(b,d);
	    BigInteger third = karatsuba3(a.add(b),c.add(d));

	    return ((first.multiply(Ten.pow(n.intValue()))).add ((((third.subtract(first)).subtract( second))).multiply(Ten.pow(n.divide((Two)).intValue()))).add(second));
	}

	public static String getLength( BigInteger a){
	    String b = a.toString();
	    return Integer.toString(b.length());
	}
    /** Main function **/
    public static void main (String[] args) 
    {
        //Scanner scan = new Scanner(System.in);
        System.out.println("Karatsuba Multiplication Algorithm Test\n");
        /** Make an object of Karatsuba class **/
        KaratsubaAns kts = new KaratsubaAns();
 
        /** Accept two integers **/
        System.out.println("Enter two integer numbers\n");
        BigInteger n1 = new BigInteger("5678");
        BigInteger n2 = new BigInteger("1234");
        /** Call function multiply of class Karatsuba **/
        BigInteger product = kts.karatsuba3(n1, n2);
        System.out.println("\nProduct : "+ product);
    }
}