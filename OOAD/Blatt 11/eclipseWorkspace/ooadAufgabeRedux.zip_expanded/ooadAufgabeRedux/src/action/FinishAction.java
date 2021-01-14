package action;

public class FinishAction extends Action {

  private int finishId;
  
  public FinishAction(int id){
    this.finishId = id;
  }

  public int getCompleteId() {
    return finishId;
  }

  @Override
  public String toString() {
    return "FinishAction{" + "finishId=" + finishId + '}';
  }


}
