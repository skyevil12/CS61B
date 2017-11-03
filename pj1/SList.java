import java.util.NoSuchElementException;

class SList {
	SListNode mHead;
	SListNode mCurrent;
	int mSize;
	
	public SList() {
		mHead = null;
		mSize = 0;
	}
	
	//Insert as the beginning
	public void insertFront(Run item) {
		mHead = new SListNode(item, mHead);
		mCurrent = mHead;
		mSize++;
	}
	
	public void resetCurrent() {
		mCurrent = mHead;
	}
	
	public Run goNext() {
		if(null == mCurrent) {
				throw new NoSuchElementException("No next run exists!");
		}
		Run cur = mCurrent.getRunObj();				
		mCurrent = mCurrent.mNext;
		return cur;
	}
	
	public boolean hasNext() {
		return !(null == mCurrent);
	}
}