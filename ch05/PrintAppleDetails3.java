package ch05;

/**
 * A variation on PrintAppleDetails2 where we have moved the printing code
 * to the Apple class. Notice that this file is smaller than PrintAppleDetails2
 * meaning fewer lines to make mistakes!
 */
public class PrintAppleDetails3 {
    public static void main(String args[]) {
        Apple a1 = new Apple();
        System.out.println("Apple a1:");
        a1.printDetails();
        // fill in some information on a1
        a1.mass = 10.0f;
        a1.x = 20;
        a1.y = 42;
        System.out.println("Updated a1:");
        a1.printDetails();
    }
}
