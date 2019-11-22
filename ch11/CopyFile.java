package ch11;

import java.nio.channels.*;
import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;

/**
 * A quick demo of copying files using the FileChannel class.
 * The names of the source and destination are passed as
 * command line arguments.
 */
public class CopyFile {
    public static void main( String [] args ) throws Exception {
        FileSystem fs = FileSystems.getDefault();
        Path fromFile = fs.getPath( args[0] );
        Path toFile = fs.getPath( args[1] );

		// By using the try-with-resources pattern, our channels
		// will automagically be cleaned up when we're done.
        try (
            FileChannel in = FileChannel.open( fromFile );
            FileChannel out = FileChannel.open( toFile, CREATE, WRITE ); )
        {
            in.transferTo( 0, (int)in.size(), out );
        }
    }
}