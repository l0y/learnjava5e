package ch09;

/**
 * Manage multiple producers and consumers to demonstrate how
 * threads work in tandem. Creates a pair of producers and a
 * pair of consumers all with access to a shared queue.
 *
 * @see URLQueue
 * @see URLProducer
 * @see URLConsumer
 */
public class URLDemo {
    public static void main(String args[]) {
		// Create our shared queue object
        URLQueue queue = new URLQueue();
		
		// Now create some producers with unique names and a reference to our queue
        URLProducer p1 = new URLProducer("P1", 3, queue);
        URLProducer p2 = new URLProducer("P2", 3, queue);
		
		// And some consumers with their own names and a reference to our queue
        URLConsumer c1 = new URLConsumer("C1", queue);
        URLConsumer c2 = new URLConsumer("C2", queue);
		
		// Get everyone going!
        System.out.println("Starting...");
        p1.start();
        p2.start();
        c1.start();
        c2.start();
		
		// First wait around for the producers to finish
        try {
            p1.join();
            p2.join();
        } catch (InterruptedException ie) {
            System.err.println("Interrupted waiting for producers to finish");
        }
		
		// OK, we know there won't be any more URLs made, so let the consumers
		// finish once the queue is empty
        c1.setKeepWorking(false);
        c2.setKeepWorking(false);
        try {
            c1.join();
            c2.join();
        } catch (InterruptedException ie) {
            System.err.println("Interrupted waiting for consumers to finish");
        }
		
        System.out.println("Done");
    }
}
