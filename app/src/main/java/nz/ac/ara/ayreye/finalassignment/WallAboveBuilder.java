package nz.ac.ara.ayreye.finalassignment;

import java.util.Map;

public class WallAboveBuilder extends WallBuilder {

	public WallAboveBuilder(Loadable gameLoadable, Map<String, Object> map) {
		super(gameLoadable, map);
	}
	
	@Override
	protected String getKey() {
		return "U";
	}
	
	@Override
	protected void looper() {
		for (int i = 0; i < super.depth; i++) {
			String row = super.rows[i];
			for (int j = 0; j < super.width; j++) {
				if (row.charAt(j) == 'x') {
					super.point = new Pointer(j, i);
					this.addHook();
				}
			}
		}
	}
	
	@Override
	protected void addHook() {
		super.gameLoadable.addWallAbove(super.point);
		System.out.println("wall added above @ " + super.point.across() + ", " + super.point.down());
	}

}
