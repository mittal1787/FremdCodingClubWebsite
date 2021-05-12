
package doorknobllama;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.*;

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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
public class Defenestrated extends JFrame{

	private JLabel positionLabel;
    private JButton submitButton;

    public Defenestrated()
    {
        super("Quora");
    }
    

    public static void main(String[] args)
    {
    	
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new Defenestrated().summonScreen();
            }
        });
    }
	public static String scrapePerson(String name){
		
		name = name.replaceAll(" ", "-");
		
		String html = getUrl("https://www.quora.com/profile/"+name);
		
		Document doc = Jsoup.parse(html);
		
		String user = doc.select("span.user").first().text();

		String credential = doc.select("span.UserCredential").first().text();
		
		/*System.out.println(doc.select("ul").size());
		
		for(Element e : doc.select("ul")) {
			System.out.println(e.text());
		}
		
		for(Element e : doc.select("img")) {
			String src = e.attr("src");
			System.out.println(src);
		}*/
		
		Element pic = doc.select("img").first();
		String pfp = pic.attr("src"); 
		
		String stats = doc.select("ul").text();
		//System.out.println(stats);
		
		String[] info = stats.split(" ");
		
		/*for(String s: info) {
			System.out.println(s);
		}*/
		if(info.length > 2) {
		String answers = info[0];
		String questions = info[2];
		String shares = info[4];
		String posts = info[6];
		String followers = info[8];
		String following = info[10];
		
		String profile = pfp + 
				"\nUser: " + user + 
				"\nCredential: " + credential + 
				"\nAnswers: " + answers + 
				"\nQuestions: " + questions + 
				"\nShares: " + shares + 
				"\nPosts: " + posts + 
				"\nFollowers: " + followers + 
				"\nFollowing: " + following;
	
			return profile;
		}
		else {
			return "Error";
		}
	}
	
	public static String scrapeAnswer(String url) {
		
		String html = getUrl(url);
		
		Document doc = Jsoup.parse(html);
		
		/*for(int i = 0; i< doc.select("a").size(); i++) {
			System.out.println("A" + doc.select("a").get(i).text());
		}*/
		String answer;
		if(doc.select("span.ui_qtext_rendered_qtext").size()==2) {
			answer = doc.select("span.ui_qtext_rendered_qtext").get(1).text();
		}
		else {
			return "This answer was deleted.";
		}
		String content = "";
		
		for(int i = 0; i<answer.length(); i++) {
			String temp;
		    if(answer.substring(i, i+1).equalsIgnoreCase(".") && answer.substring(i+1, i+2).equalsIgnoreCase(" ")) {
			    temp = "." + "\n";
			    }
		    else if(answer.substring(i, i+1).equalsIgnoreCase("?") && answer.substring(i+1, i+2).equalsIgnoreCase(" ")) {
			    temp = "?" + "\n";
			    }
		    else if(answer.substring(i, i+1).equalsIgnoreCase("!") && answer.substring(i+1, i+2).equalsIgnoreCase(" ")) {
			    temp = "!" + "\n";
			    }
			else {
				temp = answer.substring(i, i+1);
				}
			content += temp;
		}
		
		content = content.replace("’", "'");
		
		String question = doc.select("a").get(3).text();
		
		String author = doc.select("a").get(5).text();
		
		String date =  doc.select("a").get(6).text();
		
		String upvotes =  doc.select("a").get(10).text();
		upvotes = upvotes.replace("View ", "");
		
		String stats = author + "'s answer to " + question + "\n" + date + "\n" + upvotes + "\n\n" + content;

		
		return stats;
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

	public static String latestAnswer(String name) {
		
		name = name.replaceAll(" ", "-");
		String html = getUrl("https://www.quora.com/profile/"+name+"/answers");
		Document doc = Jsoup.parse(html);
		
		System.out.println("potato");
		
		String temp;
		
		for(int i = 0; i<doc.select("span").size(); i++) {
			temp = doc.select("span").get(i).text();
			if(temp.contains("?")) {
			System.out.println(temp + i);}
			
		}
		String stats = doc.select("a").text();
		//System.out.println(stats + "A");
		String[] info = stats.split(" ");
		String answers = info[0];
		
		return answers;
		
	}
	
	
	
	private void summonScreen() {
		
		JPanel contentPane = new JPanel();
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        contentPane.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        
        String[] choices = { "Quora Stats","Latest Answer", "Answer Information"};

        final JComboBox<String> options = new JComboBox<String>(choices);
        options.setVisible(true);
        contentPane.add(options);
        
        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JTextArea profile = new JTextArea();
        profile.setText("Profile: ");
        rightPanel.add(profile);
        
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        
    	final JTextField input = new JTextField();
    	input.setBounds(0, 0, 100, 100);
    	input.setSize(100,100);
    	input.setColumns(10);
    	
        JPanel labelPanel = new JPanel();
        positionLabel = new JLabel("Who do you want to search for?", JLabel.CENTER);
        JPanel buttonLeftPanel = new JPanel();
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener()
        {	//when submit button clicked, do this
            public void actionPerformed(ActionEvent ae)
            {	
            	if(options.getSelectedItem().equals("Quora Stats")) {
	           		try{
	           			String stats = scrapePerson(input.getText());
	           			System.out.print(stats);
	           			profile.setText(stats.replaceAll("\n", "\r\n"));
	           		}
	           		catch(NullPointerException e) {
	           			profile.setText("Error: please enter a valid user");
	           		}
            	}
            	else if(options.getSelectedItem().equals("Answer Information")) {
            		try{
	           			String stats = scrapeAnswer(input.getText());
	           			System.out.print(stats);
	           			profile.setText(stats.replaceAll("\n", "\r\n"));
	           		}
	           		catch(NullPointerException e) {
	           			profile.setText("Error: please enter a valid user");
	           		}
            	}
            }
        });
        
        labelPanel.add(positionLabel);
        buttonLeftPanel.add(input);
        buttonLeftPanel.add(submitButton);
        leftPanel.add(labelPanel);
        leftPanel.add(buttonLeftPanel);
        
        contentPane.add(leftPanel, BorderLayout.WEST);
        
        contentPane.add(rightPanel, BorderLayout.EAST);
        setContentPane(contentPane);
        pack();
        setLocationByPlatform(true);
        setVisible(true);
        
	}
	


}
