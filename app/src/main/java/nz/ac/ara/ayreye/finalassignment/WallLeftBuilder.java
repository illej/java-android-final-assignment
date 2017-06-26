package nz.ac.ara.ayreye.finalassignment;

import java.util.Map;

public class WallLeftBuilder extends WallBuilder {

    protected String[] cols;

	public WallLeftBuilder(Loadable gameLoadable, Map<String, Object> map) {
		super(gameLoadable, map);
	}

	@Override
	protected String getKey() {
		return "L";
	}

    @Override
    protected void setup() {
        String key = this.getKey();
        this.cols = (String[]) map.get(key);
        this.width = cols.length;
        this.depth = cols[0].length();
        super.gameLoadable.setDepthDown(this.depth);
        super.gameLoadable.setWidthAcross(this.width);
    }

	@Override
	protected void looper() {
		for (int j = 0; j < super.width; j++) {
            String col = this.cols[j];
            for (int i = 0; i < super.depth; i++) {
				if (col.charAt(i) == 'x') {
					super.point = new Pointer(j, i);
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
