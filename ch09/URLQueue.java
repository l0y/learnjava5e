package ch09;

import java.util.LinkedList;

/**
 * A manually synchronized wrapper for a LinkedList.
 * Allows for safely adding and removing URLs in order.
 */
public class URLQueue {
    LinkedList<String> urlQueue = new LinkedList<>();

    public synchronized void addURL(String url) {
        urlQueue.add(url);
    }

    public synchronized String getURL() {
        if (!urlQueue.isEmpty()) {
            return urlQueue.removeFirst();
        }
        return null;
    }

    public boolean isEmpty() {
        return urlQueue.isEmpty();
    }
}
