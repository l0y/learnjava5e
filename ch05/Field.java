package ch05;

/**
 * The playing field for our game. This class will be undergoing quite a few
 * changes as we learn about more of Java's features in coming chapters.
 * For now, we can demonstrate how to work with member variables like a1 and a2
 * as well as how to create new methods like setupApples() and detectCollisions().
 */
public class Field {
    Apple a1 = new Apple();
    Apple a2 = new Apple();

    public void setupApples() {
        a1.diameter = 3.0f;
        a1.mass = 5.0f;
        a1.x = 20;
        a1.y = 40;
        a2.diameter = 8.0f;
        a2.mass = 10.0f;
        a2.x = 70;
        a2.y = 200;
    }

    public void detectCollisions() {
        if (a1.isTouching(a2)) {
            System.out.println("Collision detected!");
        } else {
            System.out.println("Apples are not touching.");
        }
    }
}
