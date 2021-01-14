package action;

public class ShowResponsibilitiesAction extends Action {
	private String responsible;
	ShowResponsibilitiesAction(String responsible){
		this.responsible = responsible;
	}
	public String getResponsible() {
		return responsible;
	}
	@Override
	public String toString() {
		return "ShowResponsibilitiesAction{ responsible=" + responsible + "}";
	}
	
	
}
