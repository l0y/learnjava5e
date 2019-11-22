package ch05;

/**
 * One final iteration of manipulating and printing apple details.
 * Now the Field class understands apples so we access those apples
 * through the field object. Notice the double dot-notation.
 */
public class PrintAppleDetails4 {
    public static void main(String args[]) {
        Field f = new Field();
        f.setupApples();
        System.out.println("Apple a1:");
        f.a1.printDetails();
        System.out.println("Apple a2:");
        f.a2.printDetails();
        f.detectCollisions();
    }
}
