package nz.ac.ara.ayreye.finalassignment;

import java.util.ArrayList;
import java.util.List;

public class Game implements Playable, Loadable, Saveable, Saver, Loader {

	private Loader loader;
	private Saver saver;
	private List<List<Cell>> level = new ArrayList<List<Cell>>();
	private int width = 0;
	private int depth = 0;
	
	public Game(Loader loader, Saver saver) {
		this.loader = loader;
		this.saver = saver;
	}

	/*
	 * Private Methods
	 */

	private Cell getCell(Point where) {
		List<Cell> row = level.get(where.down());
		Cell cell = row.get(where.across());
		return cell;
	}

	private void setCellInfo(Point where, Object object, String key) {
		int rowIndex = where.down();
		int cellIndex = where.across();
		List<Cell> rowCopy = level.get(rowIndex);
		Cell cellCopy = rowCopy.get(cellIndex);

		cellCopy.set(key, object);

		rowCopy.set(cellIndex, cellCopy);
		level.set(rowIndex, rowCopy);
	}

	private void build() {
		if (level.isEmpty()) {
			for (int i = 0; i < this.depth; i++) {
				List<Cell> row = new ArrayList<Cell>();
				for (int j = 0; j < this.width; j++) {
					row.add(new Cell());
				}
				level.add(row);
			}
		}	
	}

	private Point findObject(Object object, String key) {
		Point result = null;
		List<Point> points = new ArrayList<Point>();
		
		for (int i = 0; i < this.depth; i++) {
			for (int j = 0; j < this.width; j++) {
				Point here = new Pointer(j, i);
				if (this.getCell(here).get(key) == object) {
					result = here;
					points.add(result);
				}
			}
		}
		
		// TODO: Replace with an Exception??
		if (points.size() > 1) {
			System.out.println("Too many " + object + " in LEVEL.");
		}
		// returns last occurrence
		return result;
	}

	private boolean isBlocked(Direction direction, Point current, Point destination) {
		boolean result = false;

		if (direction == Direction.LEFT) {
			if ((Wall) this.getCell(current).get("left") == Wall.SOMETHING) {
				result = true;
			}
		} else if (direction == Direction.RIGHT) {
			if ((Wall) this.getCell(destination).get("left") == Wall.SOMETHING) {
				result = true;
			}
		} else if (direction == Direction.UP) {
			if ((Wall) this.getCell(current).get("top") == Wall.SOMETHING) {
				result = true;
			}
		} else if (direction == Direction.DOWN) {
			if ((Wall) this.getCell(destination).get("top") == Wall.SOMETHING) {
				result = true;
			}
		}

		return result;
	}

	private Direction findDirection(Point theseus, Point minotaur, String flag) {
		Direction result = null;

		if (flag == "horizontal") {
			if (theseus.across() > minotaur.across()) {
				result = Direction.RIGHT;
			} else if (theseus.across() < minotaur.across()) {
				result = Direction.LEFT;
			}
		} else if (flag == "vertical") {
			if (theseus.down() > minotaur.down()) {
				result = Direction.DOWN;
			} else if (theseus.down() < minotaur.down()) {
				result = Direction.UP;
			}
		}

		return result;
	}

	/*
	 * <<Interface>> Saveable
	 */

	@Override
	public int getWidthAcross() {
		return this.level.get(0).size(); // this.width;
	}

	@Override
	public int getDepthDown() {
		return this.level.size(); // this.depth;
	}

	@Override
	public Wall whatsAbove(Point where) {
		return (Wall) this.getCell(where).get("top");
	}

	@Override
	public Wall whatsLeft(Point where) {
		return (Wall) this.getCell(where).get("left");
	}

	@Override
	public Point wheresTheseus() {
		return this.findObject(Part.THESEUS, "character");
	}

	@Override
	public Point wheresMinotaur() {
		return this.findObject(Part.MINOTAUR, "character");
	}

	@Override
	public Point wheresExit() {
		return this.findObject(Part.EXIT, "objective");
	}

	/*
	 * <<Interface>> Loadable
	 */

	@Override
	public int setWidthAcross(int widthAcross) {
		if (widthAcross < 4) {
			throw new IllegalArgumentException();
		}
		
		this.width = widthAcross;
		if (this.depth > 0
				&& this.width > 0) {
			this.build();
		}

		return this.width; // ?
	}

	@Override
	public int setDepthDown(int depthDown) {
		if (depthDown < 4) {
			throw new IllegalArgumentException();
		} 
		
		this.depth = depthDown;
		if (this.width > 0
				&& this.depth > 0) {
			this.build();
		}
		
		return this.depth; // ?
	}

	@Override
	public void addWallAbove(Point where) {
		this.setCellInfo(where, Wall.SOMETHING, "top");
	}

	@Override
	public void addWallLeft(Point where) {
		this.setCellInfo(where, Wall.SOMETHING, "left");
	}

	@Override
	public void addTheseus(Point where) {
		Point check = this.findObject(Part.THESEUS, "character");
		if (check != null) {
			this.setCellInfo(check, Part.NONE, "character");
			System.out.println("found a theseus, but cloned and killed the original.");
		}

		this.setCellInfo(where, Part.THESEUS, "character");
	}

	@Override
	public void addMinotaur(Point where) {
		Point check = this.findObject(Part.MINOTAUR, "character");
		if (check != null) {
			this.setCellInfo(check, Part.NONE, "character");
			System.out.println("found a minotaur, but cloned and killed the original.");
		}
		
		this.setCellInfo(where, Part.MINOTAUR, "character");
	}

	@Override
	public void addExit(Point where) {
		Point check = this.findObject(Part.EXIT, "objective");
		if (check != null) {
			this.setCellInfo(check, Part.NONE, "objective");
			System.out.println("found an exit, but walled it off and built another.");
		}
		
		this.setCellInfo(where, Part.EXIT, "objective");
	}

	/*
	 * <<Interface>> Playable
	 */

	@Override
	public void moveTheseus(Direction direction) {
		Point current = this.wheresTheseus();
		Point destination = 
				new Pointer(
					current.across() + direction.xAdjust,
					current.down() + direction.yAdjust);

		if (!this.isBlocked(direction, current, destination)) {
			this.setCellInfo(current, Part.NONE, "character");
			this.addTheseus(destination);
		} else {
			System.out.println("blocked! ~:(");
		}
	}

	@Override
	public void moveMinotaur() {
		//int moves = 2;
		//while (moves > 0) {
			Point theseusAt = this.wheresTheseus();
			Point minotaurAt = this.wheresMinotaur();
			Point destination;
			
			Direction horizDir = this.findDirection(theseusAt, minotaurAt, "horizontal");
			Direction vertDir = this.findDirection(theseusAt, minotaurAt, "vertical");
	
			if (horizDir != null
					&& !this.isBlocked(
							horizDir, 
							minotaurAt,
							destination = new Pointer(
									minotaurAt.across() + horizDir.xAdjust,
									minotaurAt.down() + horizDir.yAdjust))) {
				this.setCellInfo(minotaurAt, Part.NONE, "character");
				this.addMinotaur(destination);
			} else if (vertDir != null
					&& !this.isBlocked(
							vertDir, 
							minotaurAt,
							destination = new Pointer(
									minotaurAt.across() + vertDir.xAdjust,
									minotaurAt.down() + vertDir.yAdjust))) {
				this.setCellInfo(minotaurAt, Part.NONE, "character");
				this.addMinotaur(destination);
			}
			//moves--;
		//}
	}

	/*
	 * <<Interface>> Loader
	 */
	
	@Override
	public void load(Loadable loadable, String filename) {
		this.loader.load(loadable, filename);
	}

	/*
	 * <<Interface>> Saver
	 */

	@Override
	public void save(Saveable saveable) {
		this.saver.save(saveable);
	}

	@Override
	public void save(Saveable saveable, String fileName) {
		this.saver.save(saveable, fileName);
	}

	@Override
	public void save(Saveable saveable, String fileName, String levelName) {
		this.saver.save(saveable, fileName, levelName);
	}

}
