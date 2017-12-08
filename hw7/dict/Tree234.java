/* Tree234.java */

package dict;

/**
 *  A Tree234 implements an ordered integer dictionary ADT using a 2-3-4 tree.
 *  Only int keys are stored; no object is associated with each key.  Duplicate
 *  keys are not stored in the tree.
 *
 *  @author Jonathan Shewchuk
 **/
public class Tree234 extends IntDictionary {

  /**
   *  You may add fields if you wish, but don't change anything that
   *  would prevent toString() or find() from working correctly.
   *
   *  (inherited)  size is the number of keys in the dictionary.
   *  root is the root of the 2-3-4 tree.
   **/
  Tree234Node root;

  /**
   *  Tree234() constructs an empty 2-3-4 tree.
   *
   *  You may change this constructor, but you may not change the fact that
   *  an empty Tree234 contains no nodes.
   */
  public Tree234() {
    root = null;
    size = 0;
  }

  /**
   *  toString() prints this Tree234 as a String.  Each node is printed
   *  in the form such as (for a 3-key node)
   *
   *      (child1)key1(child2)key2(child3)key3(child4)
   *
   *  where each child is a recursive call to toString, and null children
   *  are printed as a space with no parentheses.  Here's an example.
   *      ((1)7(11 16)22(23)28(37 49))50((60)84(86 95 100))
   *
   *  DO NOT CHANGE THIS METHOD.  The test code depends on it.
   *
   *  @return a String representation of the 2-3-4 tree.
   **/
  public String toString() {
    if (root == null) {
      return "";
    } else {
      /* Most of the work is done by Tree234Node.toString(). */
      return root.toString();
    }
  }

  /**
   *  printTree() prints this Tree234 as a tree, albeit sideways.
   *
   *  You're welcome to change this method if you like.  It won't be tested.
   **/
  public void printTree() {
    if (root != null) {
      /* Most of the work is done by Tree234Node.printSubtree(). */
      root.printSubtree(0);
    }
  }

  /**
   *  find() prints true if "key" is in this 2-3-4 tree; false otherwise.
   *
   *  @param key is the key sought.
   *  @return true if "key" is in the tree; false otherwise.
   **/
  public boolean find(int key) {
    Tree234Node node = root;
    while (node != null) {
      if (key < node.key1) {
        node = node.child1;
      } else if (key == node.key1) {
        return true;
      } else if ((node.keys == 1) || (key < node.key2)) {
        node = node.child2;
      } else if (key == node.key2) {
        return true;
      } else if ((node.keys == 2) || (key < node.key3)) {
        node = node.child3;
      } else if (key == node.key3) {
        return true;
      } else {
        node = node.child4;
      }
    }
    return false;
  }

  /**
   *  insert() inserts the key "key" into this 2-3-4 tree.  If "key" is
   *  already present, a duplicate copy is NOT inserted.
   *
   *  @param key is the key sought.
   **/
  public void insert(int key) {
    // Fill in your solution here.
	if(null == root) {
		root = new Tree234Node(null, key);
		return;
	}
	
	Tree234Node node = root;
	Tree234Node prev = root;
	while(null != node) {
		//Check if three keys
		if(3 == node.keys) {
			node = pullMidNode(node);
		}

		//Save prev
		prev = node;

		//Find key and related child (duplicate node print msg and return)
		if(key < node.key1) {
			node = node.child1;
		} else if(key == node.key1 || key == node.key2 || key == node.key3) {
			outputDupKeyMsg(key);
			return;
		} else if(1 == node.keys || key < node.key2) {
			node = node.child2;
		} else if(2 == node.keys || key < node.key3) {
			node = node.child3;
		} else {
			node = node.child4;
		}
	}

	//Insert node(Must insert in leaf node?) Must be 1 or 2 keys
	if(1 == prev.keys) {
		if(key < prev.key1) {
			prev.key2 = prev.key1;
			prev.key1 = key;
		} else {
			// key > prev.key1
			prev.key2 = key;
		}
	} else {
	//two keys
		if(key < prev.key1) {
			prev.key3 = prev.key2;
			prev.key2 = prev.key1;
			prev.key1 = key;
		} else if(key > prev.key1 && key < prev.key2) {
			prev.key3 = prev.key2;
			prev.key2 = key;
		} else if(key > prev.key2) {
			prev.key3 = key;
		}
	}
	prev.keys++;
  }

  private Tree234Node pullMidNode(final Tree234Node node) {
	Tree234Node parent = null;
	if(null == node.parent) {
		parent = new Tree234Node(null, node.key2);
		root = parent;
		
		Tree234Node lNode = new Tree234Node(parent, node.key1);
		lNode.child1 = node.child1;
		lNode.child2 = node.child2;

		Tree234Node rNode = new Tree234Node(parent, node.key3);
		rNode.child1 = node.child3;
		rNode.child2 = node.child4;

		parent.child1 = lNode;
		parent.child2 = rNode;
	} else {
		parent = node.parent;
		int key = node.key2;
		Tree234Node lNode = new Tree234Node(parent, node.key1);
		lNode.child1 = node.child1;
		lNode.child2 = node.child2;

		Tree234Node rNode = new Tree234Node(parent, node.key3);
		rNode.child1 = node.child3;
		rNode.child2 = node.child4;
		//Compare and update key1, 2, 3 value, Ori parent must have one or two keys
		//one key
		if(1 == parent.keys) {
			if(key < parent.key1) {
				parent.key2 = parent.key1;
				parent.key1 = key;
				
				parent.child3 = parent.child2;
				parent.child1 = lNode;
				parent.child2 = rNode;
			} else {
				// key > parent.key1
				parent.key2 = key;
				parent.child2 = lNode;
				parent.child3 = rNode;
			}
		} else {
		//two keys
			if(key < parent.key1) {
				parent.key3 = parent.key2;
				parent.key2 = parent.key1;
				parent.key1 = key;
				
				parent.child4 = parent.child3;
				parent.child3 = parent.child2;
				parent.child1 = lNode;
				parent.child2 = rNode;
				
			} else if(key > parent.key1 && key < parent.key2) {
				parent.key3 = parent.key2;
				parent.key2 = key;
				
				parent.child4 = parent.child3;
				parent.child2 = lNode;
				parent.child3 = rNode;
			} else if(key > parent.key2) {
				parent.key3 = key;
				
				parent.child3 = lNode;
				parent.child4 = rNode;
			}
		}
		parent.keys++;
	}

	return parent;
  }

  private void outputDupKeyMsg(int key) {
	StringBuilder sb = new StringBuilder();
	System.out.println(sb.append(key).append(" already present, return!").toString());
  }

  /**
   *  testHelper() prints the String representation of this tree, then
   *  compares it with the expected String, and prints an error message if
   *  the two are not equal.
   *
   *  @param correctString is what the tree should look like.
   **/
  public void testHelper(String correctString) {
    String treeString = toString();
    System.out.println(treeString);
    if (!treeString.equals(correctString)) {
      System.out.println("ERROR:  Should be " + correctString);
    }
  }

  /**
   *  main() is a bunch of test code.  Feel free to add test code of your own;
   *  this code won't be tested or graded.
   **/
  public static void main(String[] args) {
    Tree234 t = new Tree234();

    System.out.println("\nInserting 84.");
    t.insert(84);
    t.testHelper("84");

    System.out.println("\nInserting 7.");
    t.insert(7);
    t.testHelper("7 84");

    System.out.println("\nInserting 22.");
    t.insert(22);
    t.testHelper("7 22 84");

    System.out.println("\nInserting 95.");
    t.insert(95);
    t.testHelper("(7)22(84 95)");

    System.out.println("\nInserting 50.");
    t.insert(50);
    t.testHelper("(7)22(50 84 95)");

    System.out.println("\nInserting 11.");
    t.insert(11);
    t.testHelper("(7 11)22(50 84 95)");

    System.out.println("\nInserting 37.");
    t.insert(37);
    t.testHelper("(7 11)22(37 50)84(95)");

    System.out.println("\nInserting 60.");
    t.insert(60);
    t.testHelper("(7 11)22(37 50 60)84(95)");

    System.out.println("\nInserting 1.");
    t.insert(1);
    t.testHelper("(1 7 11)22(37 50 60)84(95)");

    System.out.println("\nInserting 23.");
    t.insert(23);
    t.testHelper("(1 7 11)22(23 37)50(60)84(95)");

    System.out.println("\nInserting 16.");
    t.insert(16);
    t.testHelper("((1)7(11 16)22(23 37))50((60)84(95))");

    System.out.println("\nInserting 100.");
    t.insert(100);
    t.testHelper("((1)7(11 16)22(23 37))50((60)84(95 100))");

    System.out.println("\nInserting 28.");
    t.insert(28);
    t.testHelper("((1)7(11 16)22(23 28 37))50((60)84(95 100))");

    System.out.println("\nInserting 86.");
    t.insert(86);
    t.testHelper("((1)7(11 16)22(23 28 37))50((60)84(86 95 100))");

    System.out.println("\nInserting 49.");
    t.insert(49);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((60)84(86 95 100))");

    System.out.println("\nInserting 81.");
    t.insert(81);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((60 81)84(86 95 100))");

    System.out.println("\nInserting 51.");
    t.insert(51);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51 60 81)84(86 95 100))");

    System.out.println("\nInserting 99.");
    t.insert(99);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51 60 81)84(86)95(99 100))");

    System.out.println("\nInserting 75.");
    t.insert(75);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51)60(75 81)84(86)95" +
                 "(99 100))");

    System.out.println("\nInserting 66.");
    t.insert(66);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51)60(66 75 81))84((86)95" +
                 "(99 100))");

    System.out.println("\nInserting 4.");
    t.insert(4);
    t.testHelper("((1 4)7(11 16))22((23)28(37 49))50((51)60(66 75 81))84" +
                 "((86)95(99 100))");

    System.out.println("\nInserting 80.");
    t.insert(80);
    t.testHelper("(((1 4)7(11 16))22((23)28(37 49)))50(((51)60(66)75" +
                 "(80 81))84((86)95(99 100)))");

    System.out.println("\nFinal tree:");
    t.printTree();
  }

}
