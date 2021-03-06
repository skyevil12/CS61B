/* SSet.java, this is for HW5 Afterthought*/

import list.*;

/**
 *  A Set is a collection of Comparable elements stored in sorted order.
 *  Duplicate elements are not permitted in a Set.
 **/
public class SSet {
  /* Fill in the data fields here. */
  private List mList;
  /**
   * Set ADT invariants:
   *  1)  The Set's elements must be precisely the elements of the List.
   *  2)  The List must always contain Comparable elements, and those elements 
   *      must always be sorted in ascending order.
   *  3)  No two elements in the List may be equal according to compareTo().
   **/

  /**
   *  Constructs an empty Set. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public SSet() { 
    // Your solution here.
	//For SList change
	mList = new SList();
  }

  /**
   *  cardinality() returns the number of elements in this Set.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int cardinality() {
    // Replace the following line with your solution.
    return mList.length();
  }

  /**
   *  insert() inserts a Comparable element into this Set.
   *
   *  Sets are maintained in sorted order.  The ordering is specified by the
   *  compareTo() method of the java.lang.Comparable interface.
   *
   *  Performance:  runs in O(this.cardinality()) time.
   **/
  public void insert(Comparable c) {
    // Your solution here.
	if(mList.isEmpty()) {
		mList.insertFront(c);
		return;
	}

	ListNode current = mList.front();;
	int rt = 0;
	
	try {
		while(current.isValidNode()) {
			rt = c.compareTo(current.item());
			if(0 == rt) {
				//Do nothing and return;
				return;
			} else if(rt < 0) {
				//Insert before
				current.insertBefore(c);
				return;
			}

			current = current.next();
		}
	} catch(InvalidNodeException e) {
		e.printStackTrace();
	}

	try {
		//rt > 0
		mList.back().insertAfter(c);
	} catch(InvalidNodeException e) {
		System.out.println("Insert end fail.");
	}
  }

  /**
   *  union() modifies this Set so that it contains all the elements it
   *  started with, plus all the elements of s.  The Set s is NOT modified.
   *  Make sure that duplicate elements are not created.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Your implementation should NOT copy elements of s or "this", though it
   *  will copy _references_ to the elements of s.  Your implementation will
   *  create new _nodes_ for the elements of s that are added to "this", but
   *  you should reuse the nodes that are already part of "this".
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT ATTEMPT TO COPY ELEMENTS; just copy _references_ to them.
   **/
  public void union(SSet s) {
	// Your solution here.
	if(s.mList.isEmpty()) {
		// Input empty set, do nothing
		return;
	}

	try {
		ListNode front = s.mList.front();
		ListNode current = front;
		if(mList.isEmpty()) {
			while(current.isValidNode()) {
				this.mList.insertBack(current.item());
				current = current.next();
			}
			//Traverse finish
			return;
		}

		ListNode myCurrent = this.mList.front();
		ListNode myPrev = null;
		// Include this + s, s + this and "s hybrid this" case
		OuterLoop:
		while(current.isValidNode()) {
			Object currentItem = current.item();
			while(myCurrent.isValidNode()) {
				int rt = ((Comparable) myCurrent.item()).compareTo(currentItem);
				if(rt > 0) {
					break;
				} else if(rt == 0) {
					current = current.next();
					//For SList change
					myPrev = myCurrent;
					myCurrent = myCurrent.next();
					continue OuterLoop;
				} else {
					//For SList change
					myPrev = myCurrent;
					myCurrent = myCurrent.next();
				}
			}

			if(myCurrent.isValidNode()) {
				//For SList change
				myPrev.insertAfter(currentItem);
				//myCurrent.insertBefore(currentItem);
			} else {
				//this set already traverse finish, go on set s
				this.mList.insertBack(currentItem);
			}

			current = current.next();
		}
	} catch(InvalidNodeException e) {
		e.printStackTrace();
	}
  }

  /**
   *  intersect() modifies this Set so that it contains the intersection of
   *  its own elements and the elements of s.  The Set s is NOT modified.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Do not construct any new ListNodes during the execution of intersect.
   *  Reuse the nodes of "this" that will be in the intersection.
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT CONSTRUCT ANY NEW NODES.
   *  DO NOT ATTEMPT TO COPY ELEMENTS.
   **/
  public void intersect(SSet s) {
    // Your solution here.
	ListNode myCurrent = this.mList.front();
	ListNode current = s.mList.front();
	//For SList change
	List newList = new SList();

	try {
		if(this.mList.isEmpty() || s.mList.isEmpty()) {
			//Remove all if one of the set is empty
			while(myCurrent.isValidNode()) {
				ListNode tmpForRemoved = myCurrent;
				myCurrent = myCurrent.next();
				tmpForRemoved.remove();
			}

			return;
		}

		while(current.isValidNode()) {
			Object currentItem = current.item();
			while(myCurrent.isValidNode()) {
				int rt = ((Comparable)myCurrent.item()).compareTo(currentItem);
				if(rt > 0) {
					break;
				} else if(rt < 0) {
					//For SList change
					//ListNode tmpForRemoved = myCurrent;
					myCurrent = myCurrent.next();
					//For SList change
					//tmpForRemoved.remove();
				} else {
					//For SList change
					newList.insertBack(myCurrent.item());
					myCurrent = myCurrent.next();					
				}
			}

			if(!myCurrent.isValidNode()) {
				break;
			}

			current = current.next();
		}
		
		//For SList change
		mList = newList;
	} catch (InvalidNodeException e) {
		e.printStackTrace();
	}
  }

  /**
   *  toString() returns a String representation of this Set.  The String must
   *  have the following format:
   *    {  } for an empty Set.  No spaces before "{" or after "}"; two spaces
   *            between them.
   *    {  1  2  3  } for a Set of three Integer elements.  No spaces before
   *            "{" or after "}"; two spaces before and after each element.
   *            Elements are printed with their own toString method, whatever
   *            that may be.  The elements must appear in sorted order, from
   *            lowest to highest according to the compareTo() method.
   *
   *  WARNING:  THE AUTOGRADER EXPECTS YOU TO PRINT SETS IN _EXACTLY_ THIS
   *            FORMAT, RIGHT UP TO THE TWO SPACES BETWEEN ELEMENTS.  ANY
   *            DEVIATIONS WILL LOSE POINTS.
   **/
  public String toString() {
    // Replace the following line with your solution.
    return new StringBuilder().append(mList).toString();
  }

  public static void main(String[] argv) {
    SSet s = new SSet();
    s.insert(new Integer(3));
    s.insert(new Integer(4));
    s.insert(new Integer(3));
    System.out.println("Set s = " + s);

    SSet s2 = new SSet();
    s2.insert(new Integer(4));
    s2.insert(new Integer(5));
    s2.insert(new Integer(5));
    System.out.println("Set s2 = " + s2);

    SSet s3 = new SSet();
    s3.insert(new Integer(5));
    s3.insert(new Integer(3));
    s3.insert(new Integer(8));
    System.out.println("Set s3 = " + s3);

    s.union(s2);
    System.out.println("After s.union(s2), s = " + s);

    s.intersect(s3);
    System.out.println("After s.intersect(s3), s = " + s);

    System.out.println("s.cardinality() = " + s.cardinality());
    // You may want to add more (ungraded) test code here.
  }
}
