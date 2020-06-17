package pacman;

import java.util.Random;

public enum Direction {
	Up,
	Down,
	Left,
	Right;
	
	
	public static Direction getRandomDirection() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
