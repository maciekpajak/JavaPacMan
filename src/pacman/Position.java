package pacman;

public class Position {

	private int xPos;
	private int yPos;
	
	public Position() {
		super();
		this.xPos = 0;
		this.yPos = 0;
	}
	
	public Position(int xPos, int yPos) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public Position(Position pos) {
		super();
		this.xPos = pos.getxPos();
		this.yPos = pos.getyPos();
	}
	
	public int getxPos() {
		return xPos;
	}
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	public int getyPos() {
		return yPos;
	}
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	
	public void setPos(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
	}
	public void setPos(Position pos) {
		this.xPos = pos.getxPos();
		this.yPos = pos.getyPos();
	}
	
	public double distance(Position pos)
	{
		return Math.sqrt(Math.pow(this.xPos - pos.getxPos(), 2)+Math.pow(this.yPos - pos.getyPos(), 2));
	}

}
