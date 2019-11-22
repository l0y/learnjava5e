package ch11;

import java.io.*;

/**
 * A quick demo of copying files using basic streams.
 * The names of the source and destination are passed as
 * command line arguments.
 */
public class CopyStreams {
	public static void main( String [] args ) throws Exception {
		String fromFileName = args[0];
		String toFileName = args[1];
		BufferedInputStream in = new BufferedInputStream(
			new FileInputStream( fromFileName ) );
		BufferedOutputStream out = new BufferedOutputStream(
			new FileOutputStream( toFileName ) );
		byte [] buff = new byte [ 32*1024 ];
		int len;
		while ( (len = in.read( buff )) > 0 )
			out.write( buff, 0, len );
		in.close();
		out.close();
	}
}
