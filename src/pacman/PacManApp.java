package pacman;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PacManApp extends JFrame {

	private final Color BACKGROUND_COLOR = Color.WHITE;
	private static final int WINDOW_HEIGHT = 600;
	private static final int WINDOW_WIDTH = 600;
	private static final int TOP_BAR_HEIGHT = 20;
	private static final int TOP_BAR_WIDTH = WINDOW_WIDTH;
	private static final int BOTTOM_BAR_HEIGHT = 20;
	private static final int BOTTOM_BAR_WIDTH = WINDOW_WIDTH;
	private static final int LEFT_PADDING_HEIGHT =  WINDOW_HEIGHT - TOP_BAR_HEIGHT - BOTTOM_BAR_HEIGHT;
	private static final int LEFT_PADDING_WIDTH = 50;
	private static final int RIGHT_PADDING_HEIGHT = WINDOW_HEIGHT - TOP_BAR_HEIGHT - BOTTOM_BAR_HEIGHT;
	private static final int RIGTH_PADDING_WIDTH = 50;
	private static final int BOARD_HEIGHT = WINDOW_HEIGHT - TOP_BAR_HEIGHT - BOTTOM_BAR_HEIGHT;
	private static final int BOARD_WIDTH = WINDOW_WIDTH - LEFT_PADDING_WIDTH - RIGTH_PADDING_WIDTH;
	private JPanel topBar;
	private JPanel bottomBar;
	private JPanel leftPadding;
	private JPanel rightPadding;
	
    public PacManApp() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH + 15, WINDOW_HEIGHT+35);
        setLocation(0, 0);
        setLayout(null);
        
        createWindow();
        Container container = getContentPane();
        container.add(this.topBar);
        container.add(this.leftPadding);
        container.add(new Board(LEFT_PADDING_WIDTH,TOP_BAR_HEIGHT,BOARD_WIDTH, BOARD_HEIGHT));
        container.add(this.rightPadding);
        container.add(this.bottomBar);
        
    }

    private void createWindow()
    {
    	this.topBar = new JPanel();
    	this.topBar.setBounds(0,0,TOP_BAR_WIDTH, TOP_BAR_HEIGHT);
    	this.topBar.setBackground(BACKGROUND_COLOR);
    	this.leftPadding = new JPanel();
    	this.leftPadding.setBounds(0,TOP_BAR_HEIGHT,LEFT_PADDING_WIDTH, LEFT_PADDING_HEIGHT);
    	this.leftPadding.setBackground(BACKGROUND_COLOR);
    	this.rightPadding = new JPanel();
    	this.rightPadding.setBounds(LEFT_PADDING_WIDTH + BOARD_WIDTH,TOP_BAR_HEIGHT,RIGTH_PADDING_WIDTH, RIGHT_PADDING_HEIGHT);
    	this.rightPadding.setBackground(BACKGROUND_COLOR);
    	this.bottomBar = new JPanel();
    	this.bottomBar.setBounds(0,TOP_BAR_HEIGHT + BOARD_HEIGHT,BOTTOM_BAR_WIDTH, BOTTOM_BAR_HEIGHT );
    	this.bottomBar.setBackground(BACKGROUND_COLOR);
    	//createGameArea();
    }



    
	
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PacManApp frame = new PacManApp();
                    frame.setVisible(true);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
}





