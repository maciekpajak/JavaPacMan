package pacman;

public class Inky extends Ghost{

	public Inky(int scale,int zoom,int xOffset, int yOffset, Map map, PacMan pacman) {
		super(scale,zoom,xOffset, yOffset, map, pacman);
		this.setPos(new Position(213,201));
		this.setCorner(new Position(213,201));
		this.delay = 15;
		this.loadImage("./Images/inky_left.png","./Images/inky_right.png","./Images/inky_up.png","./Images/inky_down.png");
		
	}

	
	
	public void chase( )
	{ 	
		double d = 100000;
		Direction bestMove = null;
		Position target = new Position();
		if(pacman.direction == Direction.Right)
			target.setPos(
					this.pos.getxPos() + 2 * (pacman.getPos().getxPos() + 2 - this.pos.getxPos()), 
					this.pos.getyPos() + 2 * (pacman.getPos().getyPos() - this.pos.getyPos()));
		if(pacman.direction == Direction.Left)
			target.setPos(
					this.pos.getxPos() + 2 * (pacman.getPos().getxPos() - 2 - this.pos.getxPos()), 
					this.pos.getyPos() + 2 * (pacman.getPos().getyPos() - this.pos.getyPos()));
		if(pacman.direction == Direction.Up)
			target.setPos(
					this.pos.getxPos() + 2 * (pacman.getPos().getxPos() - this.pos.getxPos()), 
					this.pos.getyPos() + 2 * (pacman.getPos().getyPos() -2 - this.pos.getyPos()));
		if(pacman.direction == Direction.Down)
			target.setPos(
					this.pos.getxPos() + 2 * (pacman.getPos().getxPos() - this.pos.getxPos()), 
					this.pos.getyPos() + 2 * (pacman.getPos().getyPos() + 2 - this.pos.getyPos()));
		
		Position tmp = new Position(pos.getxPos()+1,pos.getyPos());
		double d2 = pacman.getPos().distance(tmp);
		if(d2 < d && canMove(tmp))
		{
			d = d2;
			bestMove = Direction.Right;
		}
		tmp = new Position(pos.getxPos()-1,pos.getyPos());
		d2 = pacman.getPos().distance(tmp);
		if(d2 < d && canMove(tmp) )
		{
			d = d2;
			bestMove = Direction.Left;
		}
		tmp = new Position(pos.getxPos(),pos.getyPos()+1);
		d2 = pacman.getPos().distance(tmp);
		if(d2 < d && canMove(tmp))
		{
			d = d2;
			bestMove = Direction.Down;
		}
		tmp = new Position(pos.getxPos(),pos.getyPos()-1);
		d2 = pacman.getPos().distance(tmp);
		if(d2 < d && canMove(tmp))
		{
			d = d2;
			bestMove = Direction.Up;
		}
		
		this.direction = bestMove;
	}

}
