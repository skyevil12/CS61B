package list;

public class LockDList extends DList {
	public void lockNode(DListNode node) {
		if(node instanceof LockDListNode) {
			LockDListNode lockDListNode = (LockDListNode) node;
			lockDListNode.mIsLocked = true;
		}
	}
	
	@Override
	protected DListNode newNode(Object item, DListNode prev, DListNode next) {
		return new LockDListNode(item, prev, next);
	}
	
	@Override
	public void remove(DListNode node) {
		if(node instanceof LockDListNode && ((LockDListNode) node).mIsLocked ) {
			System.out.println("Do nothing!!");
			return;
		}
		
		super.remove(node);
		System.out.println("Do remove!!");
	}
	
	public static void main(String... args) {
		LockDList lockDList = new LockDList();
		lockDList.insertFront("One");
		lockDList.insertBack("Two");
		DListNode first = lockDList.front();		
		lockDList.lockNode(first);
		lockDList.remove(first);
		
		DListNode back = lockDList.back();
		lockDList.remove(back);
		System.out.println(lockDList);
	}
}