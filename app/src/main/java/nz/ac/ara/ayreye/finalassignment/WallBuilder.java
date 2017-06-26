package nz.ac.ara.ayreye.finalassignment;

import java.util.Map;

public abstract class WallBuilder extends Builder {

	protected String[] rows;
	protected int depth;
	protected int width;
	
	public WallBuilder(Loadable gameLoadable, Map<String, Object> map) {
		super(gameLoadable, map);
	}

	@Override
	protected void execute() {
		this.looper();
	}
	
	protected abstract void looper();
	
	protected abstract void addHook();

}
