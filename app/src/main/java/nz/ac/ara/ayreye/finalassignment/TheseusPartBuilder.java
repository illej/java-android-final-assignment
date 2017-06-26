package nz.ac.ara.ayreye.finalassignment;

import android.util.Log;

import java.util.Map;

public class TheseusPartBuilder extends PartBuilder {

	public TheseusPartBuilder(Loadable gameLoadable, Map<String, Object> map) {
		super(gameLoadable, map);
	}

	@Override
	protected String getKey() {
		return "T";
	}
	
	@Override
	protected void execute() {
		Log.d("theseus at.point", String.valueOf(
				super.point.across())
				+ ", "
				+ String.valueOf(super.point.down()));
		super.gameLoadable.addTheseus(super.point);
	}

}
