package ch05;

/**
 * Apple
 *
 * This class sums up everything we know about the apples our physicists will be lobbing.
 * We keep the size and weight details. We'll expand this class as we cover more topics.
 */
public class Apple {
    float mass;
    float diameter = 1.0f;
    int x, y;

	// Setup some size constants
    public static final int SMALL = 0;
    public static final int MEDIUM = 1;
    public static final int LARGE = 2;

	/**
	 * Determine whether or not this apple is touching another apple
	 */
    public boolean isTouching(Apple other) {
        double xdiff = x - other.x;
        double ydiff = y - other.y;
        double distance = Math.sqrt(xdiff * xdiff + ydiff * ydiff);
        if (distance < diameter) {
            return true;
        } else {
            return false;
        }
    }

    public void printDetails() {
        System.out.println("  mass: " + mass);
        // Print the exact diameter:
        //System.out.println("  diameter: " + diameter);
        // Or a nice, human-friendly approximate:
        String niceNames[] = getAppleSizes();
        if (diameter < 5.0f) {
            System.out.println(niceNames[SMALL]);
        } else if (diameter < 10.0f) {
            System.out.println(niceNames[MEDIUM]);
        } else {
            System.out.println(niceNames[LARGE]);
        }
        System.out.println("  position: (" + x + ", " + y +")");
    }

    public static String[] getAppleSizes() {
        // Return names for our constants
        // The index of the name should match the value of the constant
        return new String[] { "SMALL", "MEDIUM", "LARGE" };
    }
}
