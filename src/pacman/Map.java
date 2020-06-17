package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {

	private List<List<java.lang.Character>> map3= new ArrayList<>();
	
	public int bullets = 200;
	
	protected BufferedImage image;
	private int scale;
	private int xOffset;
	private int yOffset;
	
	private int leftTeleport;
	private int rightTeleport;
	
	public Map(int scale, int xOffset,int yOffset)
	{
		try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader("./Images/map.txt")));
			
		     while(sc.hasNextLine()) {
		    	 char[] line = sc.nextLine().toCharArray();
				    List<java.lang.Character> tmp = new ArrayList<java.lang.Character>();
				    for (int j=0; j<line.length; j++) {
				    	
				    	tmp.add(line[j]);
				    	if(line[j] == 'o')
			 				bullets++;
				    	if(line[j] == 'L')
			 				leftTeleport = j;
			 			if(line[j] == 'R')
			 				rightTeleport = j;
				    }
				    map3.add(tmp);
		     }
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
 		this.scale = scale;
 		this.xOffset = xOffset;
 		this.yOffset = yOffset;
 		
	}
	
	
	public void draw(Graphics g, ImageObserver imo) {
		for(int i=0;i<map3.size();i++)
			for(int j=0;j<map3.get(i).size();j++)
				{
					if(map3.get(i).get(j) == 'x')
					{
						g.setColor(Color.BLACK);
						g.fillRect(j*scale + xOffset, i*scale + yOffset , scale, scale);
						
					}
					if(map3.get(i).get(j) == 'o')
					{
						g.setColor(Color.BLACK);
						g.fillOval(j*scale+scale/2 + xOffset, i*scale+scale/2 + yOffset, 2, 2);
					}
					if(map3.get(i).get(j) == 'O')
					{
						g.setColor(Color.BLACK);
						g.drawOval(j*scale + xOffset -3, i*scale + yOffset - 3, 8, 8);
					}
				}
		
    }
	
	public boolean isWall(int x, int y)
	{
		if(map3.get(y).get(x) == '_')
			return true;
		else
			return false;
	}
	
	public boolean isWall(Position pos)
	{
		if(map3.get(pos.getyPos()).get(pos.getxPos()) == '_')
			return true;
		else
			return false;
	}
	
	public boolean isTeleport(int x, int y){
		
		if(map3.get(y).get(x) == 'L' || map3.get(y).get(x) == 'R')
			return true;
		else
			return false;
	}
	
	public boolean eat(int x, int y)
	{
		if(map3.get(y).get(x) == 'o')
		{
			bullets--;
			map3.get(y).set(x, '7');
			return true;
		}
		return false;
	}
	
	
	public boolean eatPowerPellet(int x, int y)
	{
		if(map3.get(y).get(x) == 'O')
		{
			bullets--;
			map3.get(y).set(x, '7');
			return true;
		}
		return false;
	}


	public int getLeftTeleport() {
		return leftTeleport;
	}


	public void setLeftTeleport(int leftTeleport) {
		this.leftTeleport = leftTeleport;
	}


	public int getRightTeleport() {
		return rightTeleport;
	}


	public void setRightTeleport(int rightTeleport) {
		this.rightTeleport = rightTeleport;
	}
	
	
	
	
	
}
