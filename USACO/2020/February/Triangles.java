import java.util.*;
import java.io.*;

public class Triangles {
	public static void main(String[] args) throws IOException, FileNotFoundException
	{
		Scanner in = new Scanner(new File("triangles.in")); //Read the triangles file
		int n = in.nextInt();
		int[][] coords = new int[n][2]; //This stores the coordinates of the fences. We have n locations where the coordinates are stored
		in.nextLine(); 
		for (int i = 0; i < n; i++) //Do this n times
		{ 
			coords[i][0] = in.nextInt(); //x coordinate
			coords[i][1] = in.nextInt(); //y coordinate
			if (in.hasNextLine())
			{
				in.nextLine();
			}
		}
		double area = 2*largestTriangleArea(coords); //We are getting 2 times the area of the triangle (as our area can be a decimal and we want an integer result)
		PrintWriter outF = new PrintWriter(new FileWriter("triangles.out")); //Start writing to the triangles.out file
		outF.print((int)area); //Put the integer form of the area in the triangles.out file
		in.close();
		outF.close();
		//Close both files
		
	}
	
	/**
	 * This takes the coordinate points and figures out the maximum area of the triangle produced by three of the points in the 2D array of points
	 * 
	 * @param points -- The set of possible coordinates that are used to create the triangle
	 * @return the maximum possible area calculated from the triangle created from the set of points
	 */
	public static double largestTriangleArea(int[][] points) {
        double max_area = 0;
        for (int i = 0; i < points.length; i++)
        {
        	for (int j = i+1; j < points.length; j++) //Go to next points
        	{
        		for (int k = j+1; k < points.length; k++) //Check points after the second point
        		{
        			double area = calculateArea(points[i], points[j], points[k]); //We first calculate the area
        			max_area = Math.max(area, max_area); //The maximum area is the maximum between the original maximum area and the area calculated above
        		}
        	}
        }
        return max_area;
        
    }
	
	/**
	 * This takes the three points and sees if the triangle is a right triangle. The triangle is a right triangle if one point's x coordinate equals the other point's x coordinate and that one point's y coordinate equals the third point's y coordinate. 
	 * It then uses the formula 0.5*length*width, where the length is the difference in the x coordinate between two points of the same y coordinate and width is the difference in the y coordinate between two points of the same x coordinate.
	 * 
	 * @param p -- first point
	 * @param j -- second point
	 * @param k -- Third point
	 * @return If the three points form a right triangle (where two of the points have the same x coordinate and another two points in the set have the same y coordinate), then it returns the area of the triangle. Else it returns -1.
	 */
	public static double calculateArea(int[] p, int[] j, int[] k)
	{
		if (p[0] == j[0]) //If the x coordinate of point p = the x coordinate of point q
		{
			if (p[1] == k[1])  
				return 0.5 * Math.abs((p[0]-k[0])*(p[1] - j[1])); //If the y coordinate of point p = the y coordinate of point k, then the area is the product of 0.5, the difference of the x coordinates of points p and k, and the difference of the y coordinates of points p and j. This forms a right triangle
			else if (j[1] == k[1])
				return 0.5 * Math.abs((k[0]-j[0])*(p[1] - j[1])); //If the y coordinate of point j = the y coordinate of point k, then the area is the product of 0.5, the difference of the x coordinates of points j and k, and the difference of the y coordinates of points p and j. This forms a right triangle
			else
				return -1; //Else return -1 as the triangle is not a right triangle
		}
		else if (p[0] == k[0]) //If the x coordinate of point p = the x coordinate of point k
		{
			if (p[1] == j[1]) 
				return 0.5 * Math.abs((p[0]-j[0])*(p[1] - k[1])); //If the y coordinate of point p = the y coordinate of point j, then the area is the product of 0.5, the difference of the x coordinates of points p and j, and the difference of the y coordinates of points p and k. This forms a right triangle
			else if (k[1] == j[1])
				return 0.5 * Math.abs((k[0]-j[0])*(p[1] - k[1])); //If the y coordinate of point j = the y coordinate of point k, then the area is the product of 0.5, the difference of the x coordinates of points j and k, and the difference of the y coordinates of points p and k. This forms a right triangle
			return -1; //Else return -1 as the triangle is not a right triangle
		}
		else if (j[0] == k[0]) //If the x coordinate of point j = the x coordinate of point k
		{
			if (p[1] == j[1])
				return 0.5 * Math.abs((p[0]-j[0])*(k[1] - j[1])); //If the y coordinate of point p = the y coordinate of point j, then the area is the product of 0.5, the difference of the x coordinates of points p and j, and the difference of the y coordinates of points j and k. This forms a right triangle
			else if (p[1] == k[1])
				return 0.5 * Math.abs((p[0]-k[0])*(k[1] - j[1])); //If the y coordinate of point p = the y coordinate of point k, then the area is the product of 0.5, the difference of the x coordinates of points p and k, and the difference of the y coordinates of points k and j. This forms a right triangle
			else
				return -1; //Else return -1 as the triangle is not a right triangle
		}
		else
		{
			return -1; //Else return -1 as the triangle is not a right triangle
		}
		
	}
    
	
}
