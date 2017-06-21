package nz.ac.ara.ayreye.finalassignment;

import java.util.Map;

public class WallLeftBuilder extends WallBuilder {

	public WallLeftBuilder(Loadable gameLoadable, Map<String, Object> map) {
		super(gameLoadable, map);
	}

	@Override
	protected String getKey() {
		return "L";
	}
	
	@Override
	protected void looper() {
		for (int i = 0; i < super.width; i++) {
			String row = super.rows[i];
			for (int j = 0; j < super.depth; j++) {
				if (row.charAt(j) == 'x') {
					super.point = new Pointer(i, j);
					this.addHook();
				}
			}
		}
	}

	@Override
	public void addHook() {
		super.gameLoadable.addWallLeft(super.point);
		System.out.println("wall added left @ " + super.point.across() + ", " + super.point.down());
	}

}
