package nz.ac.ara.ayreye.finalassignment;

import java.util.Map;

public class MinotaurPartBuilder extends PartBuilder {

	public MinotaurPartBuilder(Loadable gameLoadable, Map<String, Object> map) {
		super(gameLoadable, map);
	}
	
	@Override
	protected String getKey() {
		return "M";
	}

	@Override
	protected void execute() {
		super.gameLoadable.addMinotaur(super.point);
	}

}
