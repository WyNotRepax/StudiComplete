
public class LinkedPriorityQueue {
	private LinkedPriorityQueueElement topElement;

	public int length;

	public LinkedPriorityQueue() {
		length = 0;
	}

	public void insert(Object obj, int priority) {
		LinkedPriorityQueueElement toInsert = new LinkedPriorityQueueElement(obj, priority);
		if (isEmpty()) {
			topElement = toInsert;
		} else {
			if (topElement.priority > toInsert.priority) {
				topElement.insert(toInsert);
			} else {
				toInsert.nextElement = topElement;
				topElement = toInsert;
			}
		}
		length++;
	}

	public Object pop() {
		if (topElement == null) {
			return null;
		}
		LinkedPriorityQueueElement toReturn = topElement;
		topElement = topElement.nextElement;
		length--;
		return toReturn.obj;
	}

	public Object peek() {
		if (isEmpty()) {
			return null;
		}
		return topElement.obj;
	}

	public boolean isEmpty() {
		return topElement == null;
	}

	public Object popLast() {
		if(isEmpty()) {
			return null;
		}
		if(topElement.nextElement == null) {
			LinkedPriorityQueueElement toReturn = topElement;
			topElement = null;
			length--;
			return toReturn.obj;
		}
		length --;
		return topElement.popLast();
	}
	
	public int topPriority() {
		if(isEmpty()) {
			return 0;
		}
		return topElement.priority;
	}

}
