package nz.ac.ara.ayreye.finalassignment;

public enum Direction {

	UP(0, -1),
	RIGHT(1, 0),
	DOWN(0, 1),
	LEFT(-1, 0);

	public int xAdjust;
	public int yAdjust;
	
	private Direction(int x, int y) {
		xAdjust = x;
		yAdjust = y;
	}
	
}
