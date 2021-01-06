
public class LinkedPriorityQueueElement {
	public Object obj;
	public LinkedPriorityQueueElement nextElement;
	public int priority;
	
	public LinkedPriorityQueueElement(Object obj, int priority) {
		this.obj = obj;
		this.priority = priority;
	}
	
	public void insert(LinkedPriorityQueueElement toInsert) {
		if(nextElement == null) {
			nextElement = toInsert;
		}
		else {
			if(nextElement.priority > toInsert.priority) {
				nextElement.insert(toInsert);
				
			}
			else {
				toInsert.nextElement = nextElement;
				nextElement = toInsert;
			}
		}
	}
	
	public Object popLast() {
		if(nextElement.nextElement == null) {
			LinkedPriorityQueueElement toReturn = nextElement;
			nextElement = null;
			return toReturn.obj;
		}
		return nextElement.popLast();
	}
}
