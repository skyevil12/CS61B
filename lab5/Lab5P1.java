public class Lab5P1 {
	public static void main(String... args) {
		X[] xa = new X[3];
		Y[] ya = new Y[3];
		
		//part a, cannot assign xa to ya, add a cast could pass compiler but crash in runtime
		//ya = (Y[])xa;
		// but ya to xa is acceptable
		xa = ya;
		
		// cast only valid when xa is dynamic type of y
		xa = new Y[6];
		ya = (Y[])xa;
		
		//part b
		xa = new X[3];
		ya = new Y[3];
		
		//It is okay
		xa = ya;
		//Must apply cast when assign to sub class(static type)
		ya = (Y[])xa;
		
		//part c
		xa = new X[3];
		ya = new Y[3];
		
		// Cannot assign to ya even apply cast
		//ya = (Y[])xa;
		xa = new Y[6];
		ya = (Y[])xa;
		xa = ya;
	}
}