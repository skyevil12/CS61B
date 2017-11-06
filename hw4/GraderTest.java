import list.*;
class GraderTest {
	public static void main(String... args) {
		DList dlist = new DList();
		dlist.insertFront("One");
		DListNode first = dlist.front();
		dlist.remove(first);
		dlist.insertAfter("Two", first);
		System.out.println("Ok!!!");
	}
}