package pacman;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

/**
 * 
 */

/**
 * @author maciek
 *
 */

public class Ghost extends Character implements Runnable, IGhost{

	   
	protected PacMan pacman;
	protected BufferedImage imageFrightened;
	protected Thread t2;
	protected Timer timer = new Timer();
	protected boolean isTimerRunning = false;

	public Position getCorner() {
		return corner;
	}

	public void setCorner(Position corner) {
		this.corner = corner;
	}

	protected Position corner;

	
	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		if(this.mode != Mode.Prison)
			this.mode = mode;
	}

	public Ghost(int scale,int zoom,int xOffset, int yOffset, Map map, PacMan pacman)
	{
		
		this.setDirection(Direction.Right);
		this.setStartPos(new Position(114,94));
		this.map = map;
		this.pacman = pacman;
		this.scale = scale;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zoom = zoom;
		
		this.speed = 30;
		File imageFileDown = new File("./Images/ghost_frightened.png");
 		try {
 			this.imageFrightened = ImageIO.read(imageFileDown);
 		} catch (IOException e) {
 			System.err.println("Blad odczytu obrazka");
 			e.printStackTrace();
 		}
 		t2 = new Thread(this, "ghost");
		t2.start();
		
		
		
	}
	
	public boolean catchPacman()
	{
		if(pacman.pos.distance(this.pos) < 5 )
		{
			return true;
		}
			
		return false;
		
	}
	
	public void draw(Graphics g, ImageObserver imo) {
        Graphics2D g2d = (Graphics2D) g;
        if(this.mode == Mode.Frightened)
        {
        	g2d.drawImage(this.imageFrightened, pos.getxPos()*scale+ xOffset - zoom / 2, pos.getyPos()*scale+ yOffset - zoom / 2,scale+ zoom,scale+ zoom, imo);
        }
        else
        {
        	if(this.direction == Direction.Right)
                g2d.drawImage(this.imageRight, pos.getxPos()*scale + xOffset - zoom / 2, pos.getyPos()*scale + yOffset - zoom / 2,scale + zoom,scale + zoom, imo);
    		if(this.direction == Direction.Left)
    	        g2d.drawImage(this.imageLeft, pos.getxPos()*scale + xOffset - zoom / 2, pos.getyPos()*scale + yOffset - zoom / 2,scale+ zoom,scale+ zoom, imo);
    		if(this.direction == Direction.Up)
    	        g2d.drawImage(this.imageUp, pos.getxPos()*scale + xOffset - zoom / 2, pos.getyPos()*scale + yOffset - zoom / 2,scale+ zoom,scale+ zoom, imo);
    		if(this.direction == Direction.Down)
    	        g2d.drawImage(this.imageDown, pos.getxPos()*scale + xOffset - zoom / 2, pos.getyPos()*scale + yOffset - zoom / 2,scale+ zoom,scale+ zoom, imo);
        }
        
        
    }
	
	
	public synchronized void random()
	{
		int tmp = new Random().nextInt(3);
		if(tmp == 0) this.direction = Direction.Right;
		if(tmp == 1) this.direction = Direction.Left;
		if(tmp == 2) this.direction = Direction.Up;
		if(tmp == 3) this.direction = Direction.Down;
		
	}
	public synchronized void frightened() {
		
		Direction bestMove = Direction.Right;
		Position target = pacman.getPos();
		double d = 0;
		Position tmp = new Position(pos.getxPos()+1,pos.getyPos());
		double d2 = target.distance(tmp);
		if(d2 > d && canMove(tmp))
		{
			d = d2;
			bestMove = Direction.Right;
		}
		tmp = new Position(pos.getxPos()-1,pos.getyPos());
		d2 = target.distance(tmp);
		if(d2 > d && canMove(tmp) )
		{
			d = d2;
			bestMove = Direction.Left;
		}
		tmp = new Position(pos.getxPos(),pos.getyPos()+1);
		d2 = target.distance(tmp);
		if(d2 > d && canMove(tmp))
		{
			d = d2;
			bestMove = Direction.Down;
		}
		tmp = new Position(pos.getxPos(),pos.getyPos()-1);
		d2 = target.distance(tmp);
		if(d2 > d && canMove(tmp))
		{
			d = d2;
			bestMove = Direction.Up;
		}
		
		this.direction = bestMove;
	}

	public synchronized void scatter()
	{
		double d = 100000;
		Direction bestMove = Direction.Right;
		Position target = this.corner;
		
		Position tmp = new Position(pos.getxPos()+1,pos.getyPos());
		double d2 = target.distance(tmp);
		if(d2 < d && canMove(tmp))
		{
			d = d2;
			bestMove = Direction.Right;
		}
		tmp = new Position(pos.getxPos()-1,pos.getyPos());
		d2 = target.distance(tmp);
		if(d2 < d && canMove(tmp) )
		{
			d = d2;
			bestMove = Direction.Left;
		}
		tmp = new Position(pos.getxPos(),pos.getyPos()+1);
		d2 = target.distance(tmp);
		if(d2 < d && canMove(tmp))
		{
			d = d2;
			bestMove = Direction.Down;
		}
		tmp = new Position(pos.getxPos(),pos.getyPos()-1);
		d2 = target.distance(tmp);
		if(d2 < d && canMove(tmp))
		{
			d = d2;
			bestMove = Direction.Up;
		}
		
		this.direction = bestMove;
	}
	
	
	@Override
	public void run() {
		while(true)
		{
			
			if (isRunning) 
			{
				
				if (canChangeDirection()) {
					if (this.mode == Mode.Chase) {
						this.speed = 30 + this.lvl;
						this.chase();
					}
					if (this.mode == Mode.Frightened) {
						timer.cancel();
						timer.purge();
						this.speed = 20 + this.lvl;
						this.frightened();
					}
					if (this.mode == Mode.Scatter) {
						this.speed = 30 + this.lvl;
						this.scatter();
					}
					if (this.mode == Mode.Prison) {
						this.speed = 20 + this.lvl;
						this.random();
					}
				}
				if(!this.isTimerRunning && ( this.mode == Mode.Chase || this.mode == Mode.Scatter)) {
					int tmpSec = new Random().nextInt(5) + 7;
					this.Countdown(tmpSec, new ChangeMode());
					this.isTimerRunning = true;
				}
				keepMoving();
				try {
					Thread.sleep(1000 / this.speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
			
		}
	}
	
	@Override
	public void restart(Map map){
		this.mode = Mode.Prison;
		this.setPos(new Position(this.getStartPos()));
		this.setDirection(Direction.Up);
		this.map = map;
		this.timer.cancel();
		this.timer.purge();
		this.Countdown(this.delay, new Release());
    }
	
	
	public void Countdown(int seconds, TimerTask task) {
        timer = new Timer();
        timer.schedule(task, seconds*1000);
    }

    class Release extends TimerTask {
        public void run() {
            System.out.format("Time's up!%n");
            setPos(new Position(114,82));
            mode = Mode.Chase;
            isTimerRunning = false;
            timer.cancel();
        }
    }
    
    class ChangeMode extends TimerTask {
        public void run() {
            if(mode == Mode.Scatter) 
            	mode = Mode.Chase;
            else 
            	mode = Mode.Scatter;
            isTimerRunning = false;
            timer.cancel();
        }
    }
}
