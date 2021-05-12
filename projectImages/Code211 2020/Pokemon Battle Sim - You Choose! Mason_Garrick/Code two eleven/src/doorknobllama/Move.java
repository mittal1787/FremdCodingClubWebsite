package doorknobllama;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Move {
	private String dmg, acc;
	private String name, type;
	private String category;
	private boolean valid;
	
	public Move(String name) {
		System.out.println(name);
		this.name = name;
		
		name = name.toLowerCase();
		name = name.replaceAll("'", "");
		name = name.replaceAll(" ", "");
		
		//System.out.println(name);
		
		String url = "https://www.serebii.net/attackdex-sm/" + name + ".shtml";
		
		//System.out.println(url);
		
		String html = getUrl(url);
		
		Document doc = Jsoup.parse(html);
		
		for(int x = 0; x<doc.select("tr>td.cen").size(); x++) {
			//System.out.println(doc.select("tr>td.cen").get(x).text());
		}
		
		String pp = doc.select("tr>td.cen").get(3).text();
		dmg = doc.select("tr>td.cen").get(4).text();
		acc = doc.select("tr>td.cen").get(5).text();
		
		Element img = doc.select("img").get(3);
		String type = (img.attr("src"));
		type = type.replace("/pokedex-bw/type/", "");
		type = type.replace(".gif", "");
		this.type = type;
		
		Element image = doc.select("img").get(4);
		String category = (image.attr("src"));
		category = category.replace("/pokedex-bw/type/", "");
		category = category.replace(".png", "");
		this.category = category;
		
		if(dmg.equals("1") || pp.equals("1") || category.equals("other")) {
			this.valid = false;
		}
		else {
			this.valid = true;
		}
	}
	
	public static String getUrl(String url){

		//System.out.println(url);
		
		URL urlObj = null;
		try{
			urlObj = new URL(url);
		}
		catch(MalformedURLException e){
			System.out.println("The url was malformed!");
			return "";
		}
		
		URLConnection urlCon = null;
		BufferedReader in = null;
		String outputText = "";
		
		try{
			urlCon = urlObj.openConnection();
			in = new BufferedReader(new
			InputStreamReader(urlCon.getInputStream()));
			String line = "";
			while((line = in.readLine()) != null){
				outputText += line;
			}
			in.close();
		}
		catch(IOException e){
			System.out.println("There was an error connecting to the URL");
			return "";
		}
		return outputText;
	}
	
	public String toString() {
		return("\nName: " + name + "\nType: " + type + "\nCategory: " + category + "\nDamage: " + dmg + "\nAccuracy: "+ acc + "%\nValid: " + valid);
	}

	public String getDmg() {
		return dmg;
	}

	public String getAcc() {
		return acc;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getCategory() {
		return category;
	}

	public boolean isValid() {
		return valid;
	}
	
	
}
