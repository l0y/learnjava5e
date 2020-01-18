package ch12;

import java.io.*;
import java.net.*;

/**
 * A simple command line demonstration of reading from a URL.
 * The URL to read must be passed as the only argument on the command line.
 */
public class Read {
  public static void main(String args[]) {
    // Did we get an argument to use as the URL?
    if (args.length != 1) {
      System.err.println("Must specify URL on command line. Exiting.");
      System.exit(1);
    }
    // Great! Let's try to read it and dump the contents to the terminal.
    try {
      URL url = new URL(args[0]);

      BufferedReader bin = new BufferedReader (
          new InputStreamReader( url.openStream() ));
      String line;
      while ( (line = bin.readLine()) != null ) {
        System.out.println( line );
      }
      bin.close();
    } catch (Exception e) { 
      System.err.println("Error occurred while reading:" + e);
    }
  }
}
