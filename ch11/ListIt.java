package ch11;

import java.io.*;

/**
 * A quick demo of file and directory operations. If the file
 * exists and is a directory, the directory gets listed. If it
 * is a readable file, the content is dumped to the console.
 */
public class ListIt {
    public static void main ( String args[] ) throws Exception {
        File file =  new File( args[0] );

        if ( !file.exists() || !file.canRead(  ) ) {
            System.out.println( "Can't read " + file );
            return;
        }

        if ( file.isDirectory(  ) ) {
            String [] files = file.list(  );
            for (int i=0; i< files.length; i++)
                System.out.println( files[i] );
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
