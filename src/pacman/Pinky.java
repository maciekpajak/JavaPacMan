package pacman;

public class Pinky extends Ghost{
	
	public Pinky(int scale,int zoom,int xOffset, int yOffset, Map map, PacMan pacman) {
		super(scale,zoom,xOffset, yOffset, map, pacman);
		this.setPos(new Position(15,15));
		this.setCorner(new Position(15,15));
		this.delay = 5;
		this.loadImage("./Images/pinky_left.png","./Images/pinky_right.png","./Images/pinky_up.png","./Images/pinky_down.png");
	}
	
	public void chase()
	{ 	
		
		double d = 100000;
		Direction bestMove = null;
		Position target = new Position();
		if(pacman.direction == Direction.Right)
			target.setPos(pacman.getPos().getxPos()+4, pacman.getPos().getyPos());
		if(pacman.direction == Direction.Left)
			target.setPos(pacman.getPos().getxPos()-4, pacman.getPos().getyPos());
		if(pacman.direction == Direction.Up)
			target.setPos(pacman.getPos().getxPos(), pacman.getPos().getyPos()-4);
		if(pacman.direction == Direction.Down)
			target.setPos(pacman.getPos().getxPos(), pacman.getPos().getyPos()+4);
		
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
