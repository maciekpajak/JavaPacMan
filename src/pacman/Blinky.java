package pacman;

public class Blinky extends Ghost{

	public Blinky(int scale,int zoom,int xOffset, int yOffset, Map map, PacMan pacman) {
		super(scale,zoom,xOffset, yOffset, map, pacman);
		this.setPos(new Position(213,15));
		this.setCorner(new Position(213,15));
		this.delay = 1;
		this.loadImage("./Images/blinky_left.png","./Images/blinky_right.png","./Images/blinky_up.png","./Images/blinky_down.png");
	}

	
	public void chase()
	{ 	
		
		double d = 100000;
		Direction bestMove = Direction.Right;
		Position target = pacman.getPos();
		
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

}
