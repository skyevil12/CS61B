package dict;
/* HashTableChained.java */
import list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
  //Array contains Entry or SList with Entry
  private Object[] mData;
  private static final int DEFAULT_BUCKET_SIZE = 101;
  private static int mCompress_Para_N = DEFAULT_BUCKET_SIZE;
  private static final int COMPRESS_PARA_P = 16908799;
  private static final int COMPRESS_PARA_A = (int)((COMPRESS_PARA_P - 1) * Math.random());
  private static final int COMPRESS_PARA_B = (int)((COMPRESS_PARA_P - 1) * Math.random());
  private int mDataSize = 0;


  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    // Your solution here.
	// load factor is 0.5
	int bucketSize = sizeEstimate >> 1;
	System.out.println("bucketSize is " + bucketSize);
	mData = new Object[bucketSize];
	mCompress_Para_N = bucketSize;
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    // Your solution here.
	System.out.println("bucketSize is " + DEFAULT_BUCKET_SIZE);
	mData = new Object[DEFAULT_BUCKET_SIZE];
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    // Replace the following line with your solution.
	if(code < 0) {
		code += Integer.MAX_VALUE;
	}

    return ((COMPRESS_PARA_A * code + COMPRESS_PARA_B) % COMPRESS_PARA_P) % mCompress_Para_N;
  }

  int compFunction(Object key) {
	return compFunction(key.hashCode());
  }
  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
    return mDataSize;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
    return 0 == mDataSize;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    // Replace the following line with your solution.
	int bucketIndex = compFunction(key);
	Object curEntry = mData[bucketIndex];
	Entry newEntry = wrapEntry(key, value);
	do {
		//Assign Entry if empty
		if(null == curEntry) {
			mData[compFunction(key)] = newEntry;
			break;
		}

		if(curEntry instanceof List) {
			((List)curEntry).insertFront(newEntry);
		} else if(curEntry instanceof Entry) {
			List chainSList = new SList();
			chainSList.insertFront(curEntry);
			chainSList.insertFront(newEntry);
			mData[bucketIndex] = chainSList;
		}
	}while(false);
	mDataSize++;

    return newEntry;
  }

  private Entry wrapEntry(Object key, Object value) {
	Entry entry = new Entry();
	entry.key = key;
	entry.value = value;
	return entry;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    // Replace the following line with your solution.
	int bucketIndex = compFunction(key);
	Object curEntry = mData[bucketIndex];
	Entry target = null;
	if(curEntry instanceof List) {
		target = findEntryFromList(key, (List)curEntry);
	} else if(curEntry instanceof Entry) {
		target = (Entry)curEntry;
	} else if(null == curEntry) {
		//Do nothing
	}
    return target;
  }

  private Entry removeEntryFromList(Object key, List list) {
	Entry removeEntry = findEntryFromList(key, list);
	do {
		if(null == removeEntry) {
			break;
		}

		ListNode curNode = list.front();
		while(null != curNode) {
			Entry curEntry = (Entry)curNode.item();
			if(key.equals(curEntry.key())) {
				curNode.remove();
				break;
			}
			curNode = curNode.next();
		}
	} while(false);
	return removeEntry;
  }

  //Get the only matched Entry or randomly select from list
  private Entry findEntryFromList(Object key, List list) {
	List subList = new SList();
	ListNode curNode = list.front();
	while(null != curNode) {
		Entry curEntry = (Entry)curNode.item();
		if(key.equals(curEntry.key())) {
			subList.insertFront(curEntry);
		}
		curNode = curNode.next();
	}
	return subList.isEmpty() ? null : randomGetEntryFromList(subList);
  }

  private Entry randomGetEntryFromList(List list) {
	int selIndex = (int)((list.length() - 1) * Math.random());
	int curIndex = 0;
	System.out.println("selIndex is " + selIndex);
	ListNode curNode = list.front();
	while(null != curNode) {
		if(curIndex == selIndex) {
			break;
		}
		curNode = curNode.next();
		curIndex++;
	}

	return (Entry)curNode.item();
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    // Replace the following line with your solution.
	int bucketIndex = compFunction(key);
	Object curEntry = mData[bucketIndex];
	Entry target = null;
	do {
		if(curEntry instanceof List) {
			List entryList = (List)curEntry;
			if(entryList.isEmpty()) {
				mData[bucketIndex] = null;
				break;
			}
			target = removeEntryFromList(key, entryList);
		} else if(curEntry instanceof Entry) {
			target = (Entry)curEntry;
			mData[bucketIndex] = null;
		} else if(null == curEntry) {
			//Do nothing
			break;
		}
		// Only execute when found!
		mDataSize--;
	} while(false);
    return target;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
	// GC issue?
	for(Object obj: mData) {
		obj = null;
	}
  }

  public static void main(String... args) {
	//System.out.println((int)(10 * Math.random()));
  }
}
