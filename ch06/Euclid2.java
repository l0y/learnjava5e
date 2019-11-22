package ch06;

/**
 * A basic implementation of Euclid's greatest common denominator
 * algorithm. This version build on ch04 allowing you to pass the
 * numbers to use as command line arguments.
 */
public class Euclid2 {
    public static void main(String args[]) {
        int a = 2701;
        int b = 222;
		// Only try to parse arguments if we have exactly 2
		if (args.length == 2) {
			try {
				a = Integer.parseInt(args[0]);
				b = Integer.parseInt(args[1]);
			} catch (NumberFormatException nfe) {
				System.err.println("Arguments were not both numbers. Using defaults.");
			}
		} else {
			System.err.println("Wrong number of arguments (expected 2). Using defaults.");
		}
		System.out.print("The GCD of " + a + " and " + b + " is ");
        while (b != 0) {
            if (a > b) {
                a = a - b;
            } else {
                b = b - a;
            }
        }
        System.out.println(a);
    }
}
