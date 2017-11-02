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
		mSize++;
	}
}