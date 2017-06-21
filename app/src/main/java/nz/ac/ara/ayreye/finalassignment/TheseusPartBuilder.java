package nz.ac.ara.ayreye.finalassignment;

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
		super.gameLoadable.addTheseus(super.point);
	}

}
