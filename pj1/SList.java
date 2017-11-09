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
		return null == mCurrent ? null : mCurrent.getRunObj();
	}
	
	public Run getPrevRun() {
		return null == mPrev ? null : mPrev.getRunObj();
	}
	
	public Run getPrevPrevRun() {
		return null == mPrevPrev ? null : mPrevPrev.getRunObj();
	}
	
	public void increasePrevPrevLen() {
		Run oldOne = mPrevPrev.getRunObj();
		Run curOne = null;
		if(null != mCurrent) {
			curOne = mCurrent.getRunObj();
		}
		
		if(null != curOne && cmpColor(oldOne, curOne)) {
			//Fix prev and current the same color issue
			mPrevPrev.toggleRunObj(new Run(oldOne.mIntensity_R, oldOne.mIntensity_G, oldOne.mIntensity_B, oldOne.mLength + 1 + curOne.mLength));
			mPrev.mNext = null;
			mPrevPrev.mNext = mCurrent.mNext;
			mCurrent.mNext = null;
		} else {
			mPrevPrev.toggleRunObj(new Run(oldOne.mIntensity_R, oldOne.mIntensity_G, oldOne.mIntensity_B, oldOne.mLength + 1));
			
			//Remove prev node regardless the mPrev, mPrevPrev and mPrevPrevPrev
			mPrev.mNext = null;
			mPrevPrev.mNext = mCurrent;		
		}
	}
	
	private boolean cmpColor(Run target, Run reference) {
		return (target.mIntensity_R == reference.mIntensity_R &&
		   target.mIntensity_G == reference.mIntensity_G &&
		   target.mIntensity_B == reference.mIntensity_B);
	}
	
	public void increaseCurLen() {
		Run prevPrevOne = null;
		if(null != mPrevPrev) {
			prevPrevOne = mPrevPrev.getRunObj();
		}
		Run oldOne = mCurrent.getRunObj();
		if(null != prevPrevOne && cmpColor(oldOne, prevPrevOne)) {
			//Fix prev and current the same color issue
			mPrevPrev.toggleRunObj(new Run(oldOne.mIntensity_R, oldOne.mIntensity_G, oldOne.mIntensity_B, prevPrevOne.mLength + 1 + oldOne.mLength));
			mPrev.mNext = null;
			mPrevPrev.mNext = mCurrent.mNext;
			mCurrent.mNext = null;
		} else {		
			mCurrent.toggleRunObj(new Run(oldOne.mIntensity_R, oldOne.mIntensity_G, oldOne.mIntensity_B, oldOne.mLength + 1));
			
			//Remove prev node regardless the mPrev, mPrevPrev and mPrevPrevPrev
			mPrev.mNext = null;
			if(null != mPrevPrev) {
				mPrevPrev.mNext = mCurrent;
			} else if(mPrev == mHead) {
				mHead = mCurrent;
				mPrev = null;
			}
		}			
	}
	
	public boolean hasNext() {
		return !(null == mCurrent);
	}
}