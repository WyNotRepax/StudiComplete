package entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TaskList implements Cloneable{
  private Map<Integer, Task> tasks = new HashMap<>();
  
  public TaskList(){
  }
  
  public static TaskList testdaten(){
    TaskList result = new TaskList();
    Task.idcount = 1; // ertraegliche Nutzung der Paketsichtbarkeit
    result.add("Ziele herausfinden", "Ute");
    result.add("Stakeholder finden", "Urs");
    result.add("UseCases finden", "Urs");
    result.add("Aktivitaetsdiagramme entwerfen", "Leila");
    result.add("funktionale Anforderungen formulieren", "Leila");
    result.add("nicht-funktionale Anforderungen formulieren", "Sergej");
    result.add("Systemarchitektur klaeren", "Sergej");
    result.add("Domain-Model erstellen", "Hans");
    result.add("mit Sequenzdiagrammen validieren", "Leila");
    result.add("Klassenmodel wart- und erweiterbar machen", "Hans");
    return result;
  }
  
  private TaskList(Map<Integer, Task> tasks){
    this.tasks = tasks;
  }
  
  public void add(String text, String responsible){
    Task tmp = new Task(text, responsible);
    this.tasks.put(tmp.getId(), tmp);
  }
  
  @Override
  public TaskList clone() {
    Map<Integer, Task> result = new HashMap<>();
    for (int key: this.tasks.keySet()){
      result.put(key, this.tasks.get(key).clone());
    }
    return new TaskList(result);
  }
  
  public void delete(int id){
    this.tasks.remove(id);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 41 * hash + Objects.hashCode(this.tasks);
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
    final TaskList other = (TaskList) obj;
    if (!Objects.equals(this.tasks, other.tasks)) {
      return false;
    }
    return true;
  }

  public Map<Integer, Task> getTasks() {
    return tasks;
  }
  
  
  
  @Override
  public String toString(){
    StringBuilder sb = new StringBuilder();
    for(Task t: this.tasks.values()) {
      sb.append(t.toString());
      sb.append("\n");
    }
    return sb.toString();
  }
}
