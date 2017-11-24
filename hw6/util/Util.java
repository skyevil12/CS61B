package util;

public class Util {
	  // Find prime larger than input
  public static int findPrime(int minVal) {
	while(true) {
		if(isPrime(minVal)) {
			break;
		}
		minVal++;
	}
	return minVal;
  }
  
  public static boolean isPrime(int num) {
	int sqrtVal = (int)Math.sqrt(num);
	for(int i = 2; i <= sqrtVal; i++) {
		if(i > 2 && 0 == i % 2) {
			continue;
		}
		
		if(0 == num % i) {
			return false;
		}
	}
	
	return true;
  }
  
  public static double expectedCollisions(int n, int N) {
	return n - N + N * (Math.pow((1 - 1.0 / N), n));
  }
}