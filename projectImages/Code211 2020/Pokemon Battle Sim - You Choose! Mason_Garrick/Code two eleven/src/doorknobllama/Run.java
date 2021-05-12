package doorknobllama;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.ArrayList;
import java.util.List;
import java.net.*;
 
public class Run {
	
	private static final String COMMA_DELIMITER = ",";
	
	public static void main(String[] args) {
		
		String[] stats = new String[6];
		for(int x = 0; x<6; x++) {
			stats[x] = "1";
		}
		Pokemon orlando = new Pokemon(3, "Venusaur", "lemonade", stats);
		
		System.out.println(orlando);               
		
		/*
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("Pokemon.csv"));
			
			List<Pokemon> pkmnList = new ArrayList<Pokemon>();
			
			String line = "";
			br.readLine();
			
			while((line = br.readLine()) != null) {
				String[] pkmnDetails = line.split(COMMA_DELIMITER);
				
				if(pkmnDetails.length>0) {
					
					String[] stats = new String[6];
					for(int i = 0; i<6; i++) {
						stats[i] = pkmnDetails[i+5];
					}
					
					Pokemon pkmn = new Pokemon(Integer.parseInt(pkmnDetails[0]),
							pkmnDetails[1], pkmnDetails[2] + " " + pkmnDetails[3], 
							stats);
					
					pkmnList.add(pkmn);
				}
			}
			
			for(Pokemon p: pkmnList) {
				System.out.println(p);
			}
			
		}
		catch(Exception ee) {
			ee.printStackTrace();
		}
		finally {
			try {
				br.close();
			}
			catch(IOException ie) {
				 System.out.println("Error occured while closing the BufferedReader");
	               ie.printStackTrace();
			}
		}*/
			
		
	}
	
	
}
