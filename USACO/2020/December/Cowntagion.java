import java.util.Scanner;
import java.lang.Math;


public class Cowntagion {

	public static void main(String[] args){
		Scanner in = new Scanner(System.in); //This time read values from the scanner
		int n = in.nextInt(); //Our first input is n, the number of farms
		in.nextLine();
		int[] nodes = new int[n]; //Our farms represent our nodes
		
		for (int i = 0; i < n-1; i++) {
			//We now go through the next lines 
			int one = in.nextInt(); //This represents the starting farm of the road
			int two = in.nextInt(); //This represents the ending farm of the road
			
			nodes[one-1] += 1; //We add one cow in the first farm
			
			//We subtract 1 from the index due to arrays starting from 0 to n-1 rather than from 1 to n
			
			nodes[two-1] += 1; //We add another cow in the next farm
			
			//Think of this like a tree, where there is a branch (road) between the two nodes (farms) 
			in.nextLine();
		}
		
		/*
		 * Now we calculate the number of days that our first cow takes to infect everybody in farm 1 (represented by nodes[0])
		 * This is mathematically defined by the equation: (int) (Math.ceil(Math.log(nodes[0]+1)/Math.log(2)) + nodes[0]),
		 * We are taking the base 2 logarithm of nodes[0] + 1. nodes[0] can be 0, and log(0) is undefined, so we add the 1. 
		 * Our days is also changed by the number of cows in the first farm that go to the other farms
		 */
		int days = (int) (Math.ceil(Math.log(nodes[0]+1)/Math.log(2)) + nodes[0]); 
		
		for (int i = 1; i < n; i++) {
			
			if (nodes[i] > 1) { //If there is more than 1 cow at the farm
				
				/*
				 * Now we calculate the number of days that our first cow takes to infect everybody in farm i+1 (represented by nodes[i])
				 * This is mathematically defined by the equation: (int) (Math.ceil(Math.log(nodes[i])/Math.log(2)) + nodes[0]-1), where nodes[i] > 1
				 * We are taking the base 2 logarithm of nodes[i].
				 * Our days is also changed by the number of cows in the i+1st farm that go to the other farms. 
				 * We don't count the farm that the cows came from (for instance farm 1 if the cows came from farm 1), so we are subtracting 1.
				 */
				days += (int) (Math.ceil(Math.log(nodes[i])/Math.log(2)) + nodes[i]-1);
			}
		}
		System.out.print(days);
	}
	
	
}
