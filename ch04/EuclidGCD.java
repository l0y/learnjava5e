package ch04;

/**
 * A basic implementation of Euclid's greatest common denominator
 * algorithm.
 *
 * https://en.wikipedia.org/wiki/Algorithm
 */
public class EuclidGCD {
    public static void main(String args[]) {
		// For now, just "hard code" the two numbers to compare
        int a = 2701;
        int b = 222;
        while (b != 0) {
            if (a > b) {
                a = a - b;
            } else {
                b = b - a;
            }
        }
        System.out.println("GCD is " + a);
    }
}
