// xa = ya is fine but ya = xa, xa needs to cast
class Ac {
	public static void main(String... args) {
		X[] xa = new Y[1];
		Y[] ya = new Y[3];
		
		xa = ya;//I(a), I(b)
		ya = (Y[])xa;//I(a), cast need applied when assign xa to ya, I(b)
		//I(c)
		xa = new X[1];
		//ya = (Y[])xa; //Run time crash!!
		xa = ya;
		
		//III(a) compile fail if the callee class extends class constant and interface constant. Change the value to the same could not solve this error
		//System.out.println(Y.WINSTON);
		System.out.println(X.WINSTON);
	}
}

class X {
	public static final int WINSTON = 95;
	void sameMethod() {
		System.out.println("Method in parent class X");
	}
}

class Y extends X implements CopyCat {
	//II(a) compiler will pass only if permission is public(because interface is public by default)
	public void sameMethod() {
		System.out.println("Method in Subclass Y");
	}
	
	//II(b) cause compiler error if only change return type
	/*public int sameMethod() {
	
	}*/
	
	//II(c) pass if signature parameter changed
	/*public void sameMethod(int i) {
	
	}*/
	
	//II(d) pass if different parameter name
	public void sameMethod(int iq) {
	
	}
	
	public void superSameMethod() {
		super.sameMethod();
	}
	
	//III(c)
	public static void main(String... args) {
		Y y = new Y();
		((X)y).sameMethod();
		
		y.superSameMethod();
		//X x = new X();
		//((Y)x).sameMethod();
		System.out.println(((X)y).WINSTON);
	}
}

interface CopyCat {
	static final int WINSTON = 100;
	//II(b) cause compiler error if only change return type
	//int sameMethod();
	//void sameMethod();
	//II(c)(d)
	void sameMethod(int i);
}

//IV (a) no
//IV (b) class cast exception
//IV (c) create a method name(name would not be the same) and call super.methodName, then trigger this method in provided object instance