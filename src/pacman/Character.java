package pacman;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Character {

	protected Position pos;
	protected Position startPos;

	protected Direction direction;
	protected Direction nextMove;
	protected int speed = 20;
	protected Map map;
	protected BufferedImage imageLeft;
	protected BufferedImage imageRight;
	protected BufferedImage imageUp;
	protected BufferedImage imageDown;
	protected int scale;
	protected int zoom;
	protected int xOffset;
	protected int yOffset;
	protected boolean isRunning = true;
	protected Thread t;
	protected Mode mode = Mode.Chase;
	protected int delay = 0;
	protected int lvl = 0;
	
	
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	public boolean isRunning() {
		return isRunning;
	}
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public void restart(Map map) {
		
		this.setPos(new Position(this.getStartPos()));
		this.setDirection(Direction.Up);
		this.map = map;
		
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}
	//protected ArrayList<Position> possibleMoves;
	
	public Position getPos() {
		return pos;
	}
	public void setPos(Position pos) {

		this.pos = pos;
	}
	
	public Position getStartPos() {
		return startPos;
	}
	public void setStartPos(Position startPos) {
		this.startPos = startPos;
	}


	
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	protected void loadImage(String filenameLeft,String filenameRight,String filenameUp,String filenameDown) {
		File imageFileLeft = new File(filenameLeft);
		File imageFileRight = new File(filenameRight);
		File imageFileUp = new File(filenameUp);
		File imageFileDown = new File(filenameDown);
 		try {
 			this.imageLeft= ImageIO.read(imageFileLeft);
 			this.imageRight = ImageIO.read(imageFileRight);
 			this.imageUp = ImageIO.read(imageFileUp);
 			this.imageDown = ImageIO.read(imageFileDown);
 		} catch (IOException e) {
 			System.err.println("Blad odczytu obrazka");
 			e.printStackTrace();
 		}
    }
	
	public void draw(Graphics g, ImageObserver imo) {
        Graphics2D g2d = (Graphics2D) g;
        if(this.direction == Direction.Right)
            g2d.drawImage(this.imageRight, pos.getxPos()*scale + xOffset - zoom /2, pos.getyPos()*scale+ yOffset- zoom /2 ,scale + zoom,scale + zoom, imo);
		if(this.direction == Direction.Left)
	        g2d.drawImage(this.imageLeft, pos.getxPos()*scale + xOffset - zoom /2, pos.getyPos()*scale+ yOffset- zoom /2 ,scale + zoom,scale + zoom, imo);
		if(this.direction == Direction.Up)
	        g2d.drawImage(this.imageUp, pos.getxPos()*scale+ xOffset - zoom /2, pos.getyPos()*scale+ yOffset- zoom /2 ,scale + zoom,scale + zoom, imo);
		if(this.direction == Direction.Down)
	        g2d.drawImage(this.imageDown, pos.getxPos()*scale+ xOffset - zoom /2, pos.getyPos()*scale+ yOffset - zoom /2,scale + zoom,scale + zoom, imo);
    }
	
	public void moveLeft() {
		direction = Direction.Left;
		Position tmp = new Position(pos.getxPos()-1, pos.getyPos());
		if(canMove(tmp))
			if(canTeleport(tmp))
				pos.setPos(new Position(map.getRightTeleport(), pos.getyPos()));
			else
				pos.setPos(tmp);
		
	}
	public void moveRight() {
		direction = Direction.Right;
		Position tmp = new Position(pos.getxPos()+1, pos.getyPos());
		if(canMove(tmp))
			if(canTeleport(tmp))
				pos.setPos(new Position(map.getLeftTeleport(), pos.getyPos()));
			else
				pos.setPos(tmp);
			
		
	}
	public void moveUp() {
		direction = Direction.Up;
		Position tmp = new Position(pos.getxPos(), pos.getyPos()-1);
		if(canMove(tmp))
			pos.setPos(tmp);
		
	}
	public void moveDown() {
		direction = Direction.Down;
		Position tmp = new Position(pos.getxPos(), pos.getyPos()+1);
		if(canMove(tmp))
			pos.setPos(tmp);
		
	}
	
	protected void keepMoving()
	{
		if(this.canChangeDirection() && this.nextMove != null && this.canMove3(nextMove))
		{
			this.direction = nextMove;
			this.nextMove = null;
		}
			
		Position pos1 = new Position(this.pos);
		if(this.direction == Direction.Right)
		{
			pos1.setPos(this.pos.getxPos()+1, this.pos.getyPos() );
			if(!map.isWall(pos1))
				this.moveRight();
		}
			
		if(this.direction == Direction.Left)
		{
			pos1.setPos(this.pos.getxPos()-1, this.pos.getyPos() );
			if(!map.isWall(pos1))
				this.moveLeft();
		}
			
		if(this.direction == Direction.Up)
		{
			pos1.setPos(this.pos.getxPos(), this.pos.getyPos()-1 );
			if(!map.isWall(pos1))
				this.moveUp();
		}
		if(this.direction == Direction.Down)
		{
			pos1.setPos(this.pos.getxPos(), this.pos.getyPos()+1 );
			if(!map.isWall(pos1))
				this.moveDown();
		}
	}
	
	protected boolean canMove(Position pos) {
		if(map.isWall(pos))
			return false;
		return true;
	}
	
	protected boolean canChangeDirection()
	{
		if(this.direction == Direction.Right || this.direction == Direction.Left)
		{
			if(this.canMove3(Direction.Up) || this.canMove3(Direction.Down))
				return true;
		}
		if(this.direction == Direction.Up || this.direction == Direction.Down)
		{
			if(this.canMove3(Direction.Left) || this.canMove3(Direction.Right))
				return true;
		}
		return false;
		
	}
	
	protected boolean canMove3(Direction dir) {
		Position pos1 = new Position(this.pos);
		if(dir == Direction.Right)
			pos1.setPos(this.pos.getxPos()+1, this.pos.getyPos() );
		if(dir == Direction.Left)
			pos1.setPos(this.pos.getxPos()-1, this.pos.getyPos() );
		if(dir == Direction.Up)
			pos1.setPos(this.pos.getxPos(), this.pos.getyPos()-1 );
		if(dir == Direction.Down)
			pos1.setPos(this.pos.getxPos(), this.pos.getyPos()+1 );
		
		if(map.isWall(pos1))
			return false;
		return true;
	}
	
	
	
	protected boolean canTeleport(Position pos) {
		if(map.isTeleport(pos.getxPos(), pos.getyPos()))
			return true;
		return false;
	}
	
}
