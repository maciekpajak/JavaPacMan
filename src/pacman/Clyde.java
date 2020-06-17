package pacman;

public class Clyde extends Ghost {

	
	public Clyde(int scale,int zoom,int xOffset, int yOffset, Map map, PacMan pacman) {
		super(scale,zoom,xOffset, yOffset, map, pacman);
		this.setPos(new Position(15,201));
		this.setCorner(new Position(15,201));
		this.delay = 10;
		this.loadImage("./Images/clyde_left.png","./Images/clyde_right.png","./Images/clyde_up.png","./Images/clyde_down.png");
	}

	public synchronized void chase()
	{ 	
		Direction bestMove = null;
		double distaceFromPacman = pacman.getPos().distance(this.pos);
		Position target = pacman.getPos();
		
		if(distaceFromPacman > 80)
		{
			double d = 100000;
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
			
		}
		else if (distaceFromPacman < 40)
		{
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
			
		}
		else
		{
			Position tmp = new Position(pos.getxPos()+1,pos.getyPos());
			if(canMove(tmp))
				bestMove = Direction.Right;
			tmp = new Position(pos.getxPos()-1,pos.getyPos());
			if(canMove(tmp) )
				bestMove = Direction.Left;
			tmp = new Position(pos.getxPos(),pos.getyPos()+1);
			
			if(canMove(tmp))
				bestMove = Direction.Down;
			tmp = new Position(pos.getxPos(),pos.getyPos()-1);
			
			if(canMove(tmp))
				bestMove = Direction.Up;
		}
		
		this.direction = bestMove;
	}
}
