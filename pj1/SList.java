import java.util.NoSuchElementException;

class SList {
	SListNode mHead;
	SListNode mCurrent;
	SListNode mPrev;
	int mSize;
	
	public SList() {
		mHead = null;
		mSize = 0;
	}
	
	//Insert as the beginning
	public void insertFront(Run item) {
		mHead = new SListNode(item, mHead);
		resetCurrent();
		mSize++;
	}
	
	public void resetCurrent() {
		mCurrent = mHead;
		mPrev = null;
	}
	
	public void insertBeforeCurrent(Run item) {
		mPrev.mNext = new SListNode(item, mCurrent);
		mSize++;
	}
	
	public Run goNext() {
		if(null == mCurrent) {
				throw new NoSuchElementException("No next run exists!");
		}
		Run cur = mCurrent.getRunObj();	
		mPrev = mCurrent;
		mCurrent = mCurrent.mNext;
		return cur;
	}
	
	public boolean hasNext() {
		return !(null == mCurrent);
	}
}