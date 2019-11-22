package ch09;

import java.util.Random;

/**
 * A threaded client for our URL producer. Uses a synchronized queue
 * to safely read a URL for processing. (Our simple demo "processes"
 * by printing out the ID of this consumer and the url it consumed.)
 */
public class URLConsumer extends Thread {
    String consumerID;
    URLQueue queue;
    boolean keepWorking;

    Random delay;

	/**
	 * Creates a new consumer with the given ID and reference to the shared queue.
	 *
	 * @param id A unique (unenforced) name for this consumer
	 * @param queue The shared queue for storing and distributing URLs
	 */
    public URLConsumer(String id, URLQueue queue) {
        if (queue == null) {
            throw new IllegalArgumentException("Shared queue cannot be null");
        }
        consumerID = id;
        this.queue = queue;
        keepWorking = true;
        delay = new Random();
    }

	/**
	 * Our working method for the thread. Watches the boolean flag
	 * keepWorking as well as the state of the queue to determine
	 * whether or not to complete the loop. While working, grab a
	 * URL and print it out then repeat.
	 */
    public void run() {
        while (keepWorking || !queue.isEmpty()) {
            String url = queue.getURL();
            if (url != null) {
                System.out.println(consumerID + " consumed " + url);
            } else {
                System.out.println(consumerID + " skipped empty queue");
            }
            try {
                Thread.sleep(delay.nextInt(1000));
            } catch (InterruptedException ie) {
                System.err.println("Consumer " + consumerID + " interrupted. Quitting.");
                break;
            }
        }
    }

	/**
	 * Allow for politely halting this consumer.
	 * Watched in the run() method.
	 * 
	 * @see #run()
	 */
    public void setKeepWorking(boolean keepWorking) {
        this.keepWorking = keepWorking;
    }
}
