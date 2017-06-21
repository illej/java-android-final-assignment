package nz.ac.ara.ayreye.finalassignment;

public interface Saver {
	
	void save(Saveable saveable);
	void save(Saveable saveable, String fileName);
	void save(Saveable saveable, String fileName, String levelName);
	
}
