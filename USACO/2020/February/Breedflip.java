import java.util.*;
import java.io.*;

public class Breedflip {
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(new File("breedflip.in"));
		int n = in.nextInt();
		in.nextLine();
		
		String one = in.nextLine(); //First string
		String two = in.nextLine(); //Second string
		
		
		int mismatches = 0; //We haven't found any mismatches yet
		
		PrintWriter outF = new PrintWriter(new FileWriter("breedflip.out"));
		
		
		for (int i = 0; i < n; i++) //Both strings have the same length n
		{
			int k = i;
			boolean isMismatched = false; //Right now, we haven't found a mismatch yet
			while (k < n && !(one.substring(k, k+1).equals(two.substring(k, k+1)))) //If a character (and the characters after the mismatched one) at the start is mismatched between the two strings
			{
				k++; //Keep moving
				isMismatched = true; //We found a mismatch
			}
			
			if (isMismatched) { //If we find a mismatched substring, 
				mismatches++; //Increment the counter of mismatches
			}
			
			i = k; //Set the i value to the k value where the mismatches end
			
		}
		outF.print(mismatches); //Print the mismatches to breedflip.out
		outF.close();
		in.close();
	}
}
