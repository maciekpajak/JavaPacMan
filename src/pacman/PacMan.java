package pacman;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

public class PacMan extends Character implements Runnable,KeyListener {

	private int score = 0;
	private Timer timer = new Timer();

	public PacMan(int scale,int zoom,int xOffset,int yOffset,Map map) {
		this.setPos(new Position(114,156));
		this.setStartPos(new Position(114,156));
		this.speed = 30 + this.lvl;
		this.setDirection(Direction.Up);
		this.map = map;
		this.scale = scale;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zoom = zoom;
		this.loadImage("./Images/pacman_left.png","./Images/pacman_right.png","./Images/pacman_up.png","./Images/pacman_down.png");
		this.setNextMove(null);
		
		t = new Thread(this, "pacman");
		t.start();
	}
	
	public boolean eat() {
		if(map.eat(pos.getxPos(), pos.getyPos()))
			return true;
		return false;
	}
	public boolean eatPowerPellet() {
		if(map.eatPowerPellet(pos.getxPos(), pos.getyPos()))
			return true;
		return false;
	}

	@Override
	public void run() {
		while(true)
		{
			if (isRunning) 
			{
				if (this.eat()) {
					this.score+=10;
				}
				if (this.eatPowerPellet()) {
					timer.cancel();
					this.setMode(Mode.Frightened);
					this.Countdown(10);
				}
				this.keepMoving();
				try {
					Thread.sleep(1000 / this.speed);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		
	}

	public void keyPressed(KeyEvent e) {
        Integer key = e.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT) {
        	this.nextMove = Direction.Left;
        	if(canMove3(Direction.Left))
        		this.setDirection(Direction.Left);
        }
        if (key == KeyEvent.VK_RIGHT) {
        	this.nextMove = Direction.Right;
        	if(canMove3(Direction.Right))
        		this.setDirection(Direction.Right);
        }
        if (key == KeyEvent.VK_UP) {
        	this.nextMove = Direction.Up;
        	if(canMove3(Direction.Up))
        		this.setDirection(Direction.Up);
        }
        if (key == KeyEvent.VK_DOWN) {
        	this.nextMove = Direction.Down;
        	if(canMove3(Direction.Down))
        		this.setDirection(Direction.Down);
        }
    }

    public void keyReleased(KeyEvent e) {

    }
    public void keyTyped(KeyEvent e) {

    }

    
	public Direction getNextMove() {
		return nextMove;
	}

	public void setNextMove(Direction nextMove) {
		this.nextMove = nextMove;
	}

	

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	

	public void Countdown(int seconds) {
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds*1000);
    }

    class RemindTask extends TimerTask {
        public void run() {
            mode = Mode.Chase;
            timer.cancel();
        }
    }
	
}