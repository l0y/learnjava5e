package ch13;

import java.io.*;
import java.util.Arrays;

/**
 * An update to ListIt from ch11 with a lambda expression example
 * when listing the contents of a directory.
 */
public class ListItLambda {
    public static void main ( String args[] ) throws Exception {
        File file =  new File( args[0] );

        if ( !file.exists() || !file.canRead(  ) ) {
            System.out.println( "Can't read " + file );
            return;
        }

        if ( file.isDirectory(  ) ) {
            // We can condense the previous explicit loop to a forEach + lambda
            Arrays.asList(file.list()).forEach(f -> System.out.println(f));
        }
        else
            try {
                Reader ir = new InputStreamReader( new FileInputStream( file ) );
                BufferedReader in = new BufferedReader( ir );
                String line;
                while ((line = in.readLine(  )) != null)
                    System.out.println(line);
            }
            catch ( FileNotFoundException e ) {
                System.out.println( "File Disappeared" );
            }
    }
}