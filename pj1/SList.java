import java.util.NoSuchElementException;

class SList {
	SListNode mHead;
	SListNode mCurrent;
	SListNode mPrev;
	SListNode mPrevPrev;
	SListNode mPrevPrevPrev;
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
		mPrevPrev = null;
		mPrevPrevPrev = null;
	}
	
	public void insertBeforeCurrent(Run item) {
		SListNode node = new SListNode(item, mCurrent);
		mPrev.mNext = node;
		mPrev = node;
		mSize++;
	}
	
	public void togglePrev(Run item) {
		if(null == mPrev) {
			return;
		}
		
		mPrev.toggleRunObj(item);
	}	
	
	public Run goNext() {
		if(null == mCurrent) {
				throw new NoSuchElementException("No next run exists!");
		}
		Run cur = mCurrent.getRunObj();	
		mPrevPrevPrev = mPrevPrev;
		mPrevPrev = mPrev;
		mPrev = mCurrent;
		mCurrent = mCurrent.mNext;
		return cur;
	}
	
	public Run getCurrentRun() {
		return mCurrent.getRunObj();
	}
	
	public Run getPrevRun() {
		return mPrev.getRunObj();
	}
	
	public Run getPrevPrevRun() {
		return mPrevPrev.getRunObj();
	}
	
	public void increasePrevPrevLen() {
		Run oldOne = mPrevPrev.getRunObj();
		mPrevPrev.toggleRunObj(new Run(oldOne.mIntensity_R, oldOne.mIntensity_G, oldOne.mIntensity_B, oldOne.mLength + 1));
	}
	
	public void increaseCurLen() {
		Run oldOne = mCurrent.getRunObj();
		mCurrent.toggleRunObj(new Run(oldOne.mIntensity_R, oldOne.mIntensity_G, oldOne.mIntensity_B, oldOne.mLength + 1));
	}
	
	public boolean hasNext() {
		return !(null == mCurrent);
	}
}