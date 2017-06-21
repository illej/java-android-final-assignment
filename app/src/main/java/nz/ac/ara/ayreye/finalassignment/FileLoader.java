package nz.ac.ara.ayreye.finalassignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileLoader implements Loader {
	
	/*
	 * private methods
	 */

	protected Map<String, Object> parse(String level) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String trimmed = level.substring(0, level.length() - 1);
		String[] sections = trimmed.split(";");

		for (String section : sections) {
			String[] pair = section.split("=");
			String key = pair[0];
			String[] values = pair[1].split(",");
			map.put(key, values);
		}
		
		/* TEST */
		System.out.println("> map contents:");
		for (Map.Entry<String, Object> entry : map.entrySet()) {
		    String key = entry.getKey();
		    String[] value = (String[]) entry.getValue();
		    System.out.println(key + " : " + Arrays.toString(value));
		}
		System.out.println();
		
		return map;
	}
	
	protected Map<String, Object> loadFile(String filename) {
		String level = "";

		// only 1 line per file anyway?
		try {
			BufferedReader bufferedReader =
					new BufferedReader(new FileReader(filename));

			StringBuilder strBuilder = new StringBuilder();
			String line = bufferedReader.readLine();
			
			while (line != null) { 
				strBuilder.append(line); 
				line = bufferedReader.readLine();
				if (line != null) { 
					strBuilder.append("\n"); 
				}
			}
			
			level = strBuilder.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("> file contents:");
		System.out.println(level);
		return this.parse(level);
	}
	
	/*
	 * <<interface>> Loader
	 */
	
	@Override
	public void load(Loadable loadable, String filename) {
		Map<String, Object> levelInfo = this.loadFile(filename);		
		
		List<Builder> builders = new ArrayList<Builder>(Arrays.asList(
				new WallAboveBuilder(loadable, levelInfo),
				new WallLeftBuilder(loadable, levelInfo),
				new TheseusPartBuilder(loadable, levelInfo),
				new MinotaurPartBuilder(loadable, levelInfo),
				new ExitPartBuilder(loadable, levelInfo)));
		
		for (Builder builder : builders) {
			builder.build();
		}
	}

}
