package nz.ac.ara.ayreye.finalassignment;

public class Pointer implements Point {
	
	private int x;
	private int y;
	
	public Pointer(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int across() {
		return this.x;
	}

	@Override
	public int down() {
		return this.y;
	}

}
