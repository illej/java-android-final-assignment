package nz.ac.ara.ayreye.finalassignment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver implements Saver {

	@Override
	public void save(Saveable saveable) {
		this.save(saveable, "default.txt");
	}

	@Override
	public void save(Saveable saveable, String fileName) {
		this.save(saveable, fileName, "default-level");
	}

	@Override
	public void save(Saveable saveable, String fileName, String levelName) {
		int width = saveable.getWidthAcross();
		int depth = saveable.getDepthDown();
		String level = "";
		StringBuilder str = new StringBuilder();
		
		// TODO: Template Method

		// build 'U'
		str.append("U=");
		for (int i = 0; i < depth; i++) {
			for (int j = 0; j < width; j++) {
				Wall wall = saveable.whatsAbove(new Pointer(j, i));
				if (wall == Wall.SOMETHING) {
					str.append("x");
				} else {
					str.append("o");
				}
			}
			if (i == depth - 1) {
				str.append(";");
			} else {
				str.append(",");
			}
		}
		// build 'L'
		str.append("L=");
		for (int i = 0; i < depth; i++) {
			for (int j = 0; j < width; j++) {
				Wall wall = saveable.whatsLeft(new Pointer(i, j));
				if (wall == Wall.SOMETHING) {
					str.append("x");
				} else {
					str.append("o");
				}
			}
			if (i == depth - 1) {
				str.append(";");
			} else {
				str.append(",");
			}
		}
		// build 'M'
		Point minotaurAt = saveable.wheresMinotaur();
		str.append("M=" + minotaurAt.across() + "," + minotaurAt.down() + ";");
		// build 'T'
		Point theseusAt = saveable.wheresTheseus();
		str.append("T=" + theseusAt.across() + "," + theseusAt.down() + ";");
		// build 'E'
		Point exitAt = saveable.wheresExit();
		str.append("E=" + exitAt.across() + "," + exitAt.down() + ":");
		// build levelname
		//str.append(levelName);
		
		level = str.toString();
		this.write(level, fileName);

	}

	protected void write(String level, String fileName) {
		BufferedWriter bWriter = null;

		try {
			bWriter = new BufferedWriter(new FileWriter(fileName));
			bWriter.write(level);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
