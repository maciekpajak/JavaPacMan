package pacman;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



@SuppressWarnings("serial")
public class Board extends JPanel implements Runnable, ActionListener{

	private int scale = 2;
	private int xOffset= 10;
	private int yOffset= 50;
	private int zoom = 18;
	private Map map = new Map(scale, xOffset, yOffset);
	
    private PacMan pacman = new PacMan(scale,zoom,xOffset, yOffset,map);; 
    
    // Ghosts
    private Blinky blinky = new Blinky(scale,zoom,xOffset, yOffset,map, pacman);;
    private Pinky pinky = new Pinky(scale,zoom,xOffset, yOffset,map, pacman);;
    private Inky inky = new Inky(scale,zoom,xOffset, yOffset,map, pacman);;
    private Clyde clyde = new Clyde(scale,zoom,xOffset, yOffset,map, pacman);;
    
    
    private int lvl = 1;
    private int score = 0;
    private int life = 3;
    private Mode global_mode;
    
    public Thread th;
    
    private Color BACKGROUND_COLOR = Color.WHITE;
    private Color MENU_COLOR = new Color(255,255,255,200);
    private Color TEXT_COLOR = Color.BLACK;
    private Font  font = new Font("Verdana", Font.BOLD,20);
    
    private JPanel topBoardBar;
    private JPanel bottomBoardBar;
    private JPanel scorePanel;
	private JPanel lvlPanel;
	private JPanel lifePanel;
	private JLabel scoreLabel;
	private JLabel lvlLabel;
	
	private JLabel []  lifes = new JLabel[3];
	
	
	private JPanel menu;
	private JButton play;
	private JButton highscores;
	private JButton quit;
	
	private JPanel startMenu;
	private String nickname = "unknown";
	private JTextField nicknameField;
	private JButton ok;
	private JButton quit2;
	
	private JPanel highscoresMenu;
	private JButton ok2;
	
	private List<Pair<String, Integer>> highscoresTable = new ArrayList<Pair<String, Integer>>();
	
	private Image imageLife;

	private static final int TOP_BOARD_BAR_HEIGHT = 50;
	private static final int BOTTOM_BOARD_BAR_HEIGHT = 0;
	
    private boolean isRunning = false;
    private boolean newHighscore = false;
    private int combo = 1;
    
    public Board (int x, int y, int x_size, int y_size) {
    	
    	this.loadImage("./Images/pacman_right.png");
    	this.setBoard(x, y, x_size, y_size);
    	
        this.initMenu();
        this.initStartMenu();
        this.initHighscoresMenu();
        this.startMenu.setVisible(true);
        setFocusable(true);
 
        th = new Thread(this,"board");
        th.start();
    }

    private void setBoard(int x, int y,int  x_size,int y_size)
    {
    	this.setBounds(x, y, x_size, y_size);
    	this.setLayout(null);
    	
    	this.drawTopBoardBar(0,0,x_size, TOP_BOARD_BAR_HEIGHT);
    	this.drawBottomBoardBar(0,450,x_size,  BOTTOM_BOARD_BAR_HEIGHT);
    	
    	this.setBackground(BACKGROUND_COLOR);
        //setBorder(BorderFactory.createLineBorder(Color.black));
    }
    
    private void drawBottomBoardBar(int x, int y,int  x_size,int y_size)
    {
    	this.bottomBoardBar = new JPanel();
		this.bottomBoardBar.setBounds(x, y, x_size, y_size);
		this.bottomBoardBar.setBackground(BACKGROUND_COLOR);
		this.bottomBoardBar.setLayout(null);
		this.add(this.bottomBoardBar);
    }
    private void drawTopBoardBar(int x, int y,int  x_size,int y_size)
    {
    	this.topBoardBar = new JPanel();
    	this.topBoardBar.setBounds(x, y, x_size, y_size);
    	this.topBoardBar.setBackground(BACKGROUND_COLOR);
    	this.topBoardBar.setLayout(null);
    	this.add(this.topBoardBar);
    	
    	this.scorePanel = new JPanel();
    	this.scorePanel.setBounds(0,20,(int)(x_size / 4), 40);
    	this.scorePanel.setBackground(BACKGROUND_COLOR);
    	this.scoreLabel = new JLabel("000 PTS");
    	this.scoreLabel.setFont(font);
    	this.scoreLabel.setForeground(TEXT_COLOR);
    	this.scorePanel.add(scoreLabel);
    	this.topBoardBar.add(scorePanel);

    	this.lvlPanel = new JPanel();
    	this.lvlPanel.setBounds(150,20,(int)(x_size / 4), 40);
    	this.lvlPanel.setBackground(BACKGROUND_COLOR);
    	this.lvlLabel = new JLabel("LVL 1");
    	this.lvlLabel.setFont(font);
    	this.lvlLabel.setForeground(TEXT_COLOR);
    	this.lvlPanel.add(lvlLabel);
    	this.topBoardBar.add(lvlPanel);
    	
    	this.lifePanel = new JPanel();
    	this.lifePanel.setBounds(300,20,(int)(x_size / 4), 40);
    	this.lifePanel.setBackground(BACKGROUND_COLOR);
    	this.imageLife.getScaledInstance(1, 1, Image.SCALE_REPLICATE);
    	ImageIcon img = new ImageIcon(imageLife);
    	lifes[0] = new JLabel(img);
    	lifes[0].setSize(20, 20);
    	lifes[1] = new JLabel(img);
    	lifes[1].setSize(20, 20);
    	lifes[2] = new JLabel(img);
    	lifes[2].setSize(20, 20);
    	
    	this.lifePanel.add(lifes[0]);
    	this.lifePanel.add(lifes[1]);
    	this.lifePanel.add(lifes[2]);
    	this.topBoardBar.add(lifePanel);
    	
    }
    
    protected void loadImage(String filename) {
		File image= new File(filename);
 		try {
 			Image tmp = ImageIO.read(image);
 			this.imageLife = tmp.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
 		} catch (IOException e) {
 			System.err.println("Blad odczytu obrazka");
 			e.printStackTrace();
 		}
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        map.draw(g,this);
        if(pacman != null)
        	pacman.draw(g,this);
        if(blinky != null)
        	blinky.draw(g,this);
        if(pinky != null)
        	pinky.draw(g,this);
        if(inky != null)
        	inky.draw(g,this);
        if(clyde != null)
        	clyde.draw(g,this);
        
        repaint();

    }
    
    private void restart()
    {
    	//this.isRunning = false;
    	
    	blinky.setRunning(false);
		inky.setRunning(false);
		pinky.setRunning(false);
		clyde.setRunning(false);
		pacman.setRunning(false);
		 
		
    	this.blinky.restart(map);
    	this.clyde.restart(map);
    	this.pinky.restart(map);
    	this.inky.restart(map);
    	this.pacman.restart(map);
		
		this.blinky.setRunning(true);
		this.inky.setRunning(true);
		this.pinky.setRunning(true);
		this.clyde.setRunning(true);
		this.pacman.setRunning(true);
		
		//this.isRunning = true;
    }
    
	private void newGame()
    {
    	this.isRunning = false;
    	this.life = 3;
    	this.pacman.setScore(0);
    	this.imageLife.getScaledInstance(1, 1, Image.SCALE_REPLICATE);
    	ImageIcon img = new ImageIcon(imageLife);
    	this.lifes[0].setIcon(img);
    	this.lifes[1].setIcon(img);
    	this.lifes[2].setIcon(img);

    	this.isRunning = false;
    	
    	this.writeHighscores(this.score);
    	this.readHighscores();
    	this.initHighscoresMenu();
    	this.highscoresMenu.setVisible(true);
    	
		//this.isRunning = true;
    }
    
	private void newLevel() {
		
//this.isRunning = false;
		map = new Map(scale, xOffset, yOffset);
		lvl++;
    	blinky.setRunning(false);
		inky.setRunning(false);
		pinky.setRunning(false);
		clyde.setRunning(false);
		pacman.setRunning(false);
		
		blinky.setLvl(lvl);
		inky.setLvl(lvl);
		pinky.setLvl(lvl);
		clyde.setLvl(lvl);
		pacman.setLvl(lvl);
		
    	this.blinky.restart(map);
    	this.clyde.restart(map);
    	this.pinky.restart(map);
    	this.inky.restart(map);
    	this.pacman.restart(map);
		
		this.blinky.setRunning(true);
		this.inky.setRunning(true);
		this.pinky.setRunning(true);
		this.clyde.setRunning(true);
		this.pacman.setRunning(true);
		
	}
	
    
    
	public void start()
	{
		map = new Map(scale, xOffset, yOffset);
		
		pacman.restart(map);
		addKeyListener(pacman);
		
		
		blinky.restart(map);
	    pinky.restart(map);
	    inky.restart(map);
	    clyde.restart(map);
	   
	    this.isRunning = true;
	}
    
	public boolean pacmanCaught() {
        
    	if(blinky.catchPacman()) 
    		if(blinky.getMode() == Mode.Frightened){
    			blinky.restart(map);
    			pacman.setScore(pacman.getScore() + combo * 100);
    			combo*=2;
    		} else {
    			return true;
    		}
    	if(inky.catchPacman()) 
    		if(inky.getMode() == Mode.Frightened){
    		inky.restart(map);
    		pacman.setScore(pacman.getScore() + combo * 100);
			combo*=2;
		} else {
			return true;
		}
    	if(pinky.catchPacman()) 
    		if(pinky.getMode() == Mode.Frightened) {
    		pinky.restart(map);
    		pacman.setScore(pacman.getScore() + combo * 100);
    		combo*=2;
    	} else {
    		return true;
    	}
    		
    	if(clyde.catchPacman()) 
    		if(clyde.getMode() == Mode.Frightened){
    		clyde.restart(map);
    		pacman.setScore(pacman.getScore() + combo * 100);
    		combo*=2;
		} else {
			return true;
		}
	    
    	return false;
    }

	@Override
	public void run() {
		
		System.out.println("Thread run");
		while (true) 
		{
			//System.out.println(this.isRunning);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (this.isRunning) 
			{
				//System.out.println("running");
				if(this.global_mode != this.pacman.getMode())
					{
						this.global_mode = this.pacman.getMode();
						this.broadcastMode(this.pacman.getMode());
					}
				if(map.bullets == 0) newLevel();
				this.score = this.pacman.getScore();
				String formatted = String.format("%03d PTS", score);
				this.scoreLabel.setText(formatted);
				this.lvlLabel.setText("LVL " + lvl);
				if(life == 2)//2
					lifes[0].setIcon(null);
				if(life == 1)
					lifes[1].setIcon(null);
				if(life == 0)//0
					{
						lifes[2].setIcon(null);
						this.isRunning = false;
						newGame();
						continue;
					}
				if (this.pacmanCaught() && isRunning)
				{
					life--;
					System.out.println("Pacman c");
					
					this.isRunning = false;
					
					restart();
					System.out.println("Restarted");
					
					this.isRunning = true;
				} 
			}
			
		}
		
	}

	
	

	private void initMenu() {
		this.menu = new JPanel();
		this.menu.setVisible(false);
		this.menu.setBounds(0, 50, 500, 500);
	 	this.menu.setBackground(MENU_COLOR);
	 	this.menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
	 	
	 	
	 	JLabel menuText = new JLabel("Main menu");
	 	menuText.setFont(font);
	 	menuText.setForeground(TEXT_COLOR);
	 	this.play = new JButton("PLAY");
	 	play.setContentAreaFilled(false);
	 	play.setBorderPainted(false);
	 	this.highscores = new JButton("HIGHSCORES");
	 	highscores.setContentAreaFilled(false);
	 	highscores.setBorderPainted(false);
	 	this.quit = new JButton("QUIT");
	 	quit.setContentAreaFilled(false);
	 	quit.setBorderPainted(false);
	 	
	 	play.addActionListener(this);
	 	highscores.addActionListener(this);
	 	quit.addActionListener(this);
	 	
	 	menuText.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	play.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	highscores.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	quit.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	
	 	menu.add(Box.createRigidArea(new Dimension(0, 100)));
	 	menu.add(menuText);
	 	menu.add(Box.createRigidArea(new Dimension(0, 20)));
	 	menu.add(play);
	 	menu.add(Box.createRigidArea(new Dimension(0, 20)));
	 	menu.add(highscores);
	 	menu.add(Box.createRigidArea(new Dimension(0, 20)));
	 	menu.add(quit);
	 	this.add(this.menu);
	}

	private void initStartMenu() {
		this.startMenu = new JPanel();
		this.startMenu.setVisible(false);
		this.startMenu.setBounds(0, 50, 500, 500);
	 	this.startMenu.setBackground(MENU_COLOR);
	 	this.startMenu.setLayout(new BoxLayout(startMenu, BoxLayout.Y_AXIS));
	 	
	 	
	 	JLabel startMenuText = new JLabel("WELCOME");
	 	startMenuText.setFont(font);
	 	startMenuText.setForeground(TEXT_COLOR);
	 	this.nicknameField = new JTextField("nickname");
	 	nicknameField.setColumns(20);
	 	this.ok = new JButton("OK");
	 	ok.setContentAreaFilled(false);
	 	ok.setBorderPainted(false);
	 	this.quit2 = new JButton("QUIT");
	 	quit2.setContentAreaFilled(false);
	 	quit2.setBorderPainted(false);
	 	
	 	nicknameField.setPreferredSize( new Dimension( 100, 24 ) );
	 	nicknameField.setMaximumSize(new Dimension( 100, 24 ));
	 	
	 	ok.addActionListener(this);
	 	quit2.addActionListener(this);
	 	
	 	startMenuText.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	nicknameField.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	ok.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	quit2.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	
	 	startMenu.add(Box.createRigidArea(new Dimension(0, 100)));
	 	startMenu.add(startMenuText);
	 	startMenu.add(Box.createRigidArea(new Dimension(0, 20)));
	 	startMenu.add(nicknameField);
	 	startMenu.add(Box.createRigidArea(new Dimension(0, 20)));
	 	startMenu.add(ok);
	 	startMenu.add(Box.createRigidArea(new Dimension(0, 20)));
	 	startMenu.add(quit2);
	 	this.add(this.startMenu);
	}

	private void initHighscoresMenu() {
		this.highscoresMenu = new JPanel();
		this.highscoresMenu.setVisible(false);
		this.highscoresMenu.setBounds(0, 50, 500, 500);
	 	this.highscoresMenu.setBackground(MENU_COLOR);
	 	this.highscoresMenu.setLayout(new BoxLayout(highscoresMenu, BoxLayout.Y_AXIS));
	 	
	 	
	 	JLabel highscoresMenuText = new JLabel("HIGHSCORES");
	 	highscoresMenuText.setFont(font);
	 	highscoresMenuText.setForeground(TEXT_COLOR);
	 	
	 	JLabel newHighscoreText = new JLabel("NEW HIGHSCORE !!!");
	 	newHighscoreText.setFont(font);
	 	newHighscoreText.setForeground(TEXT_COLOR);
	 	
	 	
	 	this.ok2 = new JButton("OK");
	 	ok2.setContentAreaFilled(false);
	 	ok2.setBorderPainted(false);
	 	
	 	ok2.addActionListener(this);
	 	
	 	newHighscoreText.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	highscoresMenuText.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	ok2.setAlignmentX(Component.CENTER_ALIGNMENT);
	 	
	 	highscoresMenu.add(Box.createRigidArea(new Dimension(0, 100)));
	 	highscoresMenu.add(highscoresMenuText);
	 	
	 	if(this.newHighscore == true)
	 	{
	 		highscoresMenu.add(Box.createRigidArea(new Dimension(0, 20)));
	 		highscoresMenu.add(newHighscoreText);
	 		
	 		this.newHighscore = false;
	 	}
	 	highscoresMenu.add(Box.createRigidArea(new Dimension(0, 20)));
	 	
	 	JLabel[] scores = new JLabel[10]; 
	 	
	 	this.readHighscores();
	 	this.sortHighscores();
	 	for(int i = 0; i < 10; i++) {
	 		if(i < this.highscoresTable.size()) {
	 			scores[i]=new JLabel( String.format( "%-2s. %-20s   %-10s",( i+ 1),this.highscoresTable.get(i).getL(),this.highscoresTable.get(i).getR()));
	 		}
	 		else{
	 			scores[i]=new JLabel(String.format( "%-2s. %-20s   %-10s",( i+ 1) ,"---------","---"));
	 		}
	 		scores[i].setAlignmentX(Component.CENTER_ALIGNMENT);
	 		scores[i].setForeground(TEXT_COLOR);
	 		highscoresMenu.add(scores[i]);
	 	}
	 	
	 	
	 	highscoresMenu.add(ok2);
	 	this.add(this.highscoresMenu);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		 
 		if(source == this.play) {
 			System.out.println("PLAY");
 			this.menu.setVisible(false);
 			this.start();
 		}
 
 		else if(source == this.highscores){
 			System.out.println("HIGHSORES");
 			this.menu.setVisible(false);
 			this.highscoresMenu.setVisible(true);
		}
 
 		else if(source == this.quit || source == this.quit2)
 		{
 			System.out.println("QUIT");
	 		System.exit(0);
	 	}
 		
 		else if(source == this.ok)
 		{
 			System.out.println("ok");
 			this.nickname = this.nicknameField.getText();
 			this.startMenu.setVisible(false);
 			this.menu.setVisible(true);
 			System.out.println(this.nickname);
	 	}
 		
 		else if(source == this.ok2)
 		{
 			System.out.println("ok2");
 			this.menu.setVisible(true);
 			this.highscoresMenu.setVisible(false);
	 	}
 		
	}
	
	private void writeHighscores(int score)
	{
		this.readHighscores();
		if(!this.highscoresTable.isEmpty()){
			if(this.highscoresTable.get(0).getR() < score)
				this.newHighscore = true;
		}
		else{
			this.newHighscore = true;
		}
		 try {
			  FileWriter myWriter = new FileWriter("./.highscores.txt", true);
		      myWriter.write(this.nickname + " " +  score + System.lineSeparator());
		      myWriter.close();
		      
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		 
	}
	
	private void readHighscores()
	{
		File file = new File("./.highscores.txt");
	    Scanner input = null;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    highscoresTable = new ArrayList<Pair<String, Integer>>();
	    while (input.hasNextLine()) {
	    	String[] line = input.nextLine().trim().split(" ");
	    	Pair<String, Integer> p = new Pair<String,Integer>(line[0],Integer.parseInt(line[1]));
	    	highscoresTable.add(p);
	    }
	    
	}

	
	private void sortHighscores() {
		
		highscoresTable.sort((Comparator<? super Pair<String, Integer>>) new Comparator<Pair<String, Integer>>() {
			
	        @Override
	        public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
	            if (o1.getR() > o2.getR()) {
	                return -1;
	            } else if (o1.getR().equals(o2.getR())) {
	                return 0; 
	            } else {
	                return 1;
	            }
	        }
	    });
		
		
		
	}


	private void broadcastMode(Mode mode) {
		System.out.println(mode);
		blinky.setMode(mode);
		inky.setMode(mode);
		clyde.setMode(mode);
		pinky.setMode(mode);
	}
}


