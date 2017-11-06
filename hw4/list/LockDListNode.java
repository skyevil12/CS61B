package list;

public class LockDListNode extends DListNode {

	protected boolean mIsLocked = false;

	protected LockDListNode(Object i, DListNode p, DListNode n) {
		super(i, p, n);
	}

}