
package action;

public class DeleteAction extends Action{
  private int deleteId;
  
  public DeleteAction(int id){
    this.deleteId = id;
  }

  public int getDeleteId() {
    return deleteId;
  }

  @Override
  public String toString() {
    return "DeleteAction{" + "deleteId=" + deleteId + '}';
  }
}
