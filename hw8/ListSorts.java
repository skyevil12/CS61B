/* ListSorts.java */

import list.*;

public class ListSorts {

  private final static int SORTSIZE = 1000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    // Replace the following line with your solution.
	LinkedQueue rt = new LinkedQueue();

	try {
		while(!q.isEmpty()) {
			Object content = q.dequeue();
			LinkedQueue contentQueue = new LinkedQueue();
			contentQueue.enqueue(content);
			rt.enqueue(contentQueue);
		}
	} catch(QueueEmptyException e) {
		e.printStackTrace();
	}
    return rt;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    // Replace the following line with your solution.
	LinkedQueue rt = new LinkedQueue();
	// Loop to get individual object and stop when either queue empty
	try {
		while(true) {
			Object obj1 = q1.isEmpty() ? null : q1.front();
			Object obj2 = q2.isEmpty() ? null : q2.front();

			if(null == obj1 && null == obj2) {
				break;
			} else if(null == obj1) {
				while(!q2.isEmpty()) rt.enqueue(q2.dequeue());
			} else if(null == obj2) {
				while(!q1.isEmpty()) rt.enqueue(q1.dequeue());
			} else {
				rt.enqueue(((Comparable)obj1).compareTo((Comparable)obj2) <= 0 ? q1.dequeue() : q2.dequeue());
			}
		}
	} catch(QueueEmptyException e) {
		e.printStackTrace();
	}
    return rt;
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    // Your solution here.
	//Handle qIn empty case
	int rt = 0;
	Object contentObj = null;
	try {
		while(!qIn.isEmpty()) {
			//Compare and fill three queue
			contentObj = qIn.dequeue();
			rt = ((Comparable)contentObj).compareTo(pivot);
			if(rt < 0) {
				qSmall.enqueue(contentObj);
			} else if(rt > 0) {
				qLarge.enqueue(contentObj);
			} else {
				qEquals.enqueue(contentObj);
			}
		}
	} catch(QueueEmptyException e) {
		e.printStackTrace();
	}
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
	if(q.isEmpty()) {
		return;
	}
    // Your solution here.
	LinkedQueue qoqs = makeQueueOfQueues(q);
	try {
		while(qoqs.size() > 1) {
			LinkedQueue item1 = (LinkedQueue) qoqs.dequeue();
			LinkedQueue item2 = (LinkedQueue) qoqs.dequeue();
			qoqs.enqueue(mergeSortedQueues(item1, item2));
		}

		LinkedQueue rt = (LinkedQueue) qoqs.dequeue();
		q.append(rt);
	} catch(QueueEmptyException e) {
		e.printStackTrace();
	}
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    // Your solution here.
	if(q.isEmpty() || 1 == q.size()) {
		return;
	}

	sQueue = q;
	quickSortInner(q);
  }

  public static void quickSortInner(LinkedQueue q) {
	if(q.isEmpty()) {
		return;
	}

	//Base case
	if(1 == q.size()) {
		sQueue.append(q);
		return;
	}

	//Random select pivot
	int pivotIndex = (int)(Math.random() * q.size()) + 1;

	//Recursive case
	LinkedQueue qSmall = new LinkedQueue();
	LinkedQueue qEquals = new LinkedQueue();
    LinkedQueue qLarge = new LinkedQueue();
	partition(q, (Comparable)q.nth(pivotIndex), qSmall, qEquals, qLarge);
	quickSortInner(qSmall);
	sQueue.append(qEquals);
	quickSortInner(qLarge);
  }

  private static LinkedQueue sQueue = null;

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  public static void main(String [] args) {
    LinkedQueue q = new LinkedQueue();
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());

    q = makeRandom(1);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());

    q = makeRandom(10);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());

    q = new LinkedQueue();
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());

    q = makeRandom(1);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());

    q = makeRandom(10);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());

    /* Remove these comments for Part III.
    Timer stopWatch = new Timer();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");
    */
  }

}
