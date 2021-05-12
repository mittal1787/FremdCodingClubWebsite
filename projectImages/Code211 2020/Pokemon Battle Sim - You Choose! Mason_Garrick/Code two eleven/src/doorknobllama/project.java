package doorknobllama;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.*;

@SuppressWarnings("serial")
public class project extends JFrame
{
  

	private JLabel positionLabel;
    private JButton submitButton;
    private static int gridSize;
    private String name;
    private boolean isNamed,pkmnChosen, fighting, isChoosen = false;
    private int defeated = 0;
    private Pokemon joeyPkmn, redPkmn;
	private static final String COMMA_DELIMITER = ",";
	private JComboBox<String> moveOptions;
	private int jHP, rHP = 999;
	private JLabel hp, hp2 = null;
    
    public project()
    {
        super("Jeopardy Game");
    }
    

    public static void main(String[] args)
    {
    	
    	gridSize = 4;
        if (args.length > 0)
        {
            gridSize = Integer.parseInt(args[0]);
        }
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                try {
					new project().createAndDisplayGUI();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }
    /**
     * displays the jeopardy screen with buttons, labels, etc.
     * @throws IOException 
     */
    private void createAndDisplayGUI() throws IOException{
    	

        final String RULES = "these are rules";
        BufferedImage myPicture;
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//Text area with rules
        final JTextArea rules = new JTextArea();
        rules.setBounds(0, 0, 100, 100);
    	rules.setText(RULES);
    	rules.setEditable(false);
    	rules.setLayout(null);
    	
    	String icicle ="\nNumber Defeated: " + defeated;
    	myPicture = ImageIO.read(new File("red.png"));
    	JLabel red = new JLabel(new ImageIcon(myPicture));
    	JLabel joey = new JLabel(new ImageIcon(ImageIO.read(new File("joey.png"))));
    	int rand = ((int)(Math.random()*721+1));
    	sopln(rand);
    	
    	
    	if(rand < 10) {
    		saveImage("https://www.serebii.net/xy/pokemon/00" + rand +".png", "randpokemon.png");
    	}
    	else if(rand < 100) {
    		saveImage("https://www.serebii.net/xy/pokemon/0" + rand +".png", "randpokemon.png");
    	}
    	else {
    		saveImage("https://www.serebii.net/xy/pokemon/" + rand +".png", "randpokemon.png");
    	}
    	
    	redPkmn = pkmnFromDex(rand);
    	
    	myPicture = ImageIO.read(new File("randpokemon.png"));
    	JLabel randomPokemon2 = new JLabel(new ImageIcon(myPicture));
    	
    	
    	JPanel rulesPanel = new JPanel();
    	//the whole tHING
    	JPanel LPokePanel = new JPanel();
        JPanel contentPane = new JPanel();
    	JPanel RPokePanel = new JPanel();
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        contentPane.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        contentPane.add(rulesPanel);
        
        
       
        
        //User panel
        JPanel rightPanel = new JPanel();
        JPanel farRightPanel = new JPanel();
        rightPanel.add(red);
        //rightPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        //rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JLabel userA = new JLabel("\n", JLabel.CENTER);
        JLabel userB = new JLabel("\n", JLabel.CENTER);
        userB.setText(icicle);
    	rightPanel.add(userB);
        JPanel leftPanel = new JPanel();
        
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
    	final JTextField input = new JTextField();
    	input.setBounds(0, 0, 100, 100);
    	input.setSize(100,100);
    	input.setColumns(10);
        JPanel labelPanel = new JPanel();
        positionLabel = new JLabel("What is your name?", JLabel.CENTER);
        JPanel buttonLeftPanel = new JPanel();
        
        String[] choices = {"Charizard", "Venusaur", "Blastoise", "Lapras", "Snorlax"};

        JComboBox<String> options = new JComboBox<String>(choices);
        options.setVisible(false);
        
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener()
        {	//when submit button clicked, do this
            public void actionPerformed(ActionEvent ae)
            {	
            	
            	if(isNamed == false) {
            	name = input.getText();
            	sopln(name);
            	
            	userA.setText(name);
            	isNamed = true;
            	input.setText("");
            	input.setEnabled(false);
            	input.setVisible(false);

        		options.setVisible(true);
            	
            	RPokePanel.add(randomPokemon2);
            	rHP = Integer.parseInt(redPkmn.getStats()[0]) + 100;
            	hp2 = new JLabel(rHP + "");
				
				RPokePanel.add(hp2);
            	
            	positionLabel.setText("Choose your Pokemon");
            	
            	}
            	                
            	else if(pkmnChosen == false) {

            			try {
            				String choice = (String) options.getSelectedItem();
							JLabel blastoise = new JLabel(new ImageIcon(ImageIO.read(new File(options.getSelectedItem() + ".png"))));
							LPokePanel.add(blastoise);
							
							if(choice.equals("Venusaur")) {
								joeyPkmn = pkmnFromDex(3);
							}
							if(choice.equals("Charizard")) {
								joeyPkmn = pkmnFromDex(6);
							}
							if(choice.equals("Blastoise")) {
								joeyPkmn = pkmnFromDex(9);
							}
							if(choice.equals("Pikachu")) {
								joeyPkmn = pkmnFromDex(25);
							}
							if(choice.equals("Snorlax")) {
								joeyPkmn = pkmnFromDex(143);
							}
							if(choice.equals("Lapras")) {
								joeyPkmn = pkmnFromDex(131);
							}
							
							jHP = Integer.parseInt((joeyPkmn.getStats()[0])) + 100;
							hp = new JLabel("" + jHP);
							
							LPokePanel.add(hp);
							pack();
							options.setVisible(false);
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            		pkmnChosen = true;
            		//BufferedImage myPic = ImageIO.read(new File(options.getSelectedItem() + ".png"));
                	//JLabel userPokemon = new JLabel(new ImageIcon(myPic));

            		String[] choices = {joeyPkmn.getMoveset().get(0).getName(), joeyPkmn.getMoveset().get(1).getName(), joeyPkmn.getMoveset().get(2).getName(), joeyPkmn.getMoveset().get(3).getName()};
            		
            		moveOptions = new JComboBox<String>(choices);
            		moveOptions.setVisible(true);
            		buttonLeftPanel.add(moveOptions);
            		submitButton.setText("Attack!");
            		fighting = true;
            		//enter something here
            		//RPokePanel.add(randomPokemon);
            		
            	}
         
            	else if(fighting) {
            		Move yourMove = new Move((String)moveOptions.getSelectedItem());
            		int dmg = (22*Integer.parseInt(yourMove.getDmg())*(Integer.parseInt(joeyPkmn.getStats()[1])/Integer.parseInt(redPkmn.getStats()[2])))/50;
            		
            		if(dmg<10) {
            			dmg = (int)(Math.random()*40+2);
            		}
            		rHP -= dmg;
            		sopln("You did " + dmg + " damage!");
            		int rand = (int)(Math.random()*4);
            		Move theirMove = new Move(redPkmn.getMoveset().get(rand).getName());
            		int dmga = (22*Integer.parseInt(theirMove.getDmg())*(Integer.parseInt(redPkmn.getStats()[1])/Integer.parseInt(joeyPkmn.getStats()[2])))/50;
            		
            		if(dmga<10) {
            			dmga = (int)(Math.random()*40+2);
            		}
            		jHP -= dmga;
            		sopln("They did " + dmga + " damage to you!");
            		hp.setText(jHP + "");
            		hp2.setText(rHP + "");
            		
            		if(rHP <= 0) {
                		hp2.setText("0");
                		positionLabel.setText("Congrats! You won!");
                		submitButton.setEnabled(false);
                		moveOptions.setVisible(false);
                	}
                	if(jHP <= 0) {
                		hp.setText("0");
                		positionLabel.setText(":c you lost...");
                		submitButton.setEnabled(false);
                		moveOptions.setVisible(false);
                	}
            		
            	}
            
              }
        });
        //rulesPanel.add(rules, BorderLayout.NORTH);

    	rulesPanel.add(userA);
        rulesPanel.add(joey);

        contentPane.add(LPokePanel, BorderLayout.WEST);
        
        labelPanel.add(positionLabel);
        buttonLeftPanel.add(input);
        buttonLeftPanel.add(options);
        buttonLeftPanel.add(submitButton);
        leftPanel.add(labelPanel);

        leftPanel.add(buttonLeftPanel);
        
        contentPane.add(leftPanel, BorderLayout.WEST);
        //BUTTONS
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(gridSize+1, gridSize, 10, 10));
        
        
        contentPane.add(buttonPanel);
        contentPane.add(RPokePanel, BorderLayout.EAST);
        contentPane.add(rightPanel, BorderLayout.EAST);
        contentPane.add(farRightPanel, BorderLayout.EAST);
        setContentPane(contentPane);
        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }
    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }
    public void sopln(Object imLazy) {
    	System.out.println(imLazy);
    }
    
    public Pokemon pkmnFromDex(int dex) {
    	
    	
    	Pokemon pkmn = null;
    	
    	BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("Pokemon.csv"));
			
			
			String line = "";
			br.readLine();
			
			ArrayList<Integer> dexes = new ArrayList<Integer>();
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<String> tyeps = new ArrayList<String>();
			ArrayList<String[]> bstates = new ArrayList<String[]>();
			
			while((line = br.readLine()) != null) {
				String[] pkmnDetails = line.split(COMMA_DELIMITER);
				
				if(pkmnDetails.length>0) {
					
					String[] stats = new String[6];
					for(int i = 0; i<6; i++) {
						stats[i] = pkmnDetails[i+5];
					}
					
					dexes.add(Integer.parseInt(pkmnDetails[0]));
					names.add(pkmnDetails[1]);
					tyeps.add(pkmnDetails[2] + " " + pkmnDetails[3]);
					bstates.add(stats);
					
				}
			}
			
			sopln("done.");
			pkmn = new Pokemon(dexes.get(dex-1), names.get(dex-1), tyeps.get(dex-1), bstates.get(dex-1));
			sopln(pkmn);
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
		}
	
	
	return pkmn;
    	
    }
    
    
}

