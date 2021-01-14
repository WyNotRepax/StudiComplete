package entity;

import java.util.Objects;

public class Task  implements Cloneable{
  static int idcount = 1;
  private int id;
  private String text;
  private String responsible;
  private boolean finished = false;
  
  public Task(){
  }

  public Task(String text, String responsible) {
    this.id = Task.idcount++;
    this.text = text;
    this.responsible = responsible;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getResponsible() {
    return responsible;
  }

  public void setResponsible(String responsible) {
    this.responsible = responsible;
  }

  public boolean getFinished() {
    return finished;
  }

  public void setFinished(boolean finished) {
    this.finished = finished;
  }

  @Override
  public String toString() {
    return "Task{" + "id=" + id + ", text=" + text + ", responsible=" + responsible + ", finished=" + finished + '}';
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 79 * hash + this.id;
    hash = 79 * hash + Objects.hashCode(this.text);
    hash = 79 * hash + Objects.hashCode(this.responsible);
    hash = 79 * hash + (this.finished ? 1 : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Task other = (Task) obj;
    if (this.id != other.id) {
      return false;
    }
    if (this.finished != other.finished) {
      return false;
    }
    if (!Objects.equals(this.text, other.text)) {
      return false;
    }
    if (!Objects.equals(this.responsible, other.responsible)) {
      return false;
    }
    return true;
  }

  @Override
  public Task clone() {
    Task result = new Task(this.text, this.responsible);
    result.setId(this.id);
    result.setFinished(this.finished);
    return result;
  } 
}
