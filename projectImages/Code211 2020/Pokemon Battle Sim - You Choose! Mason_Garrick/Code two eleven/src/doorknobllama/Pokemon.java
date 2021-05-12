package doorknobllama;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.*;
import java.util.ArrayList;
import java.io.*;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Pokemon {
	
	String name, sprite;
	String types;
	ArrayList<Move> moveset; 
	ArrayList<String> moves;
	String[] stats;
	int dexNum;
	public Pokemon(int dexNum, String name, String types, String[] stats) {
		super();
		System.out.println(name);
		this.dexNum = dexNum;
		this.name = name;
		this.types = types;
		this.stats = stats;
		
		String sprite;

		if(dexNum<10) {
			sprite = "https://www.serebii.net/xy/pokemon/00" + dexNum + ".png";
		}
		else if(dexNum<100) {
			sprite = "https://www.serebii.net/xy/pokemon/0" + dexNum + ".png";
		}
		else{
			sprite = "https://www.serebii.net/xy/pokemon/" + dexNum + ".png";
		}
		this.sprite = sprite;
		
		String html;
		
		if(dexNum<10) {		
			html = getUrl("https://www.serebii.net/pokedex-xy/00" + dexNum + ".shtml");
		}
		else if(dexNum<100) {
			html = getUrl("https://www.serebii.net/pokedex-xy/0" + dexNum + ".shtml");
		}
		else {
			html = getUrl("https://www.serebii.net/pokedex-xy/" + dexNum + ".shtml");
		}
		
		//String html = getUrl("https://pokemondb.net/pokedex/" + name);
		//String html = getUrl("http://webcache.googleusercontent.com/search?q=cache%3Ahttps%3A%2F%2Fpokemondb.net%2Fpokedex%2F" + name + "&rlz=1C1CHBF_enUS862US862&oq=cache%3Ahttps%3A%2F%2Fpokemondb.net%2Fpokedex%2F" + name + "&aqs=chrome..69i57j69i58j69i60.7247j0j4&sourceid=chrome&ie=UTF-8");
	
		Document doc = Jsoup.parse(html);
		
		ArrayList<String> moves = new ArrayList<String>();
		String move;
		
		
		ArrayList<String> abilities = new ArrayList<String>();
		for(int i = 0; i<doc.select("table.dextable>tbody>tr>td.fooinfo>a>b").size(); i++) {
			abilities.add(doc.select("table.dextable>tbody>tr>td.fooinfo>a>b").get(i).text());
		}
		
		
		
		for(int i = 0; i<doc.select("table.dextable>tbody>tr>td.fooinfo>a").size(); i++) {
			
			
			move = (doc.select("table.dextable>tbody>tr>td.fooinfo>a").get(i).text());
			
			if(!move.substring(0,2).equalsIgnoreCase("TM") && !moves.contains(move) && !abilities.contains(move)) {
				moves.add(move);
			}
		}
		
		for(String x: moves) {
			//System.out.println(x);
		}
		
		ArrayList<Move> moveset = new ArrayList<Move>();
		Move attack;
		int rand;
		
		while(moveset.size()<4) {
			
			rand = ((int)(Math.random()*(moves.size())));
			//System.out.println(moves.size());
			attack = new Move(moves.get(rand));
			
			if(attack.isValid() && !moveset.contains(attack)) {
				moveset.add(attack);
				//System.out.println(attack);
			}
			
		}
		
		sOPLN("");
		//sOPLN(moveset);
		
		this.moveset = moveset;

	}
	
	public String getName() {
		return name;
	}

	public String getSprite() {
		return sprite;
	}

	public String getTypes() {
		return types;
	}

	public ArrayList<Move> getMoveset() {
		return moveset;
	}

	public ArrayList<String> getMoves() {
		return moves;
	}

	public String[] getStats() {
		return stats;
	}

	public int getDexNum() {
		return dexNum;
	}

	public String toString() {
		return ("#" + dexNum + "\nName: " + name + "\nTypes: " + types + "\nHP: " + stats[0] + "\nAttack: " + stats[1] + "\nDefense: " + stats[2] + "\nSpecial Attack: " + stats[3] + 
				"\nSpecial Defense: " + stats[4] + "\nSpeed: " + stats[5] + "\nMoves: " + moveset);
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
	
	public void sOPLN (Object tomat) {
		System.out.println(tomat);
	}
	
	
	
}
