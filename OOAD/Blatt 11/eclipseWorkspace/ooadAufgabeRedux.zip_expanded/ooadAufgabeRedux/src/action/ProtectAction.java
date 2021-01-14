package action;

import java.util.Arrays;

public class ProtectAction extends Action {
	private int[] protectIds;
	
	ProtectAction(int[] ids){
		this.protectIds = ids;
	}
	
	public int[] getProtectIds() {
		return protectIds;
	}
	
	@Override
	public String toString() {
		return "ProtectAction{protectIds=" + Arrays.toString(protectIds) + "}";
	}
}
