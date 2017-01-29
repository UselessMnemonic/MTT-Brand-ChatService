package chatengine;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
/**
 * 
 * @author Christopher Madrigal
 * The BufferedUTF8Reader is an implementation of a BufferedWriter which is capable of receiving strings
 * encoded as UTF-8 characters.
 */
public class BufferedUTF8Reader
{
	private BufferedInputStream in;
	
	/**
	 * Constructs a BufferedUTF8Reader from an InputStream.
	 * @param in InputStream that will provide stream access.
	 */
	public BufferedUTF8Reader(InputStream in)
	{
		this.in = new BufferedInputStream(in);
		
	}

	/**
	 * Returns the state of stream for reading.
	 * 
	 * @return true if the underlying stream has bytes buffered for reading.
	 * @throws IOException from the underlying stream.
	 */
	public boolean ready() throws IOException
	{
		return in.available() > 0;
	}

	/**
	 * Waits until there are bytes available to read.
	 * 
	 * <p>The stream consists of a header that specifies the length of the data in bytes,
	 * and the actual data, where the data is UTF-8 encoded.
	 * It first reads 4 bytes, the integer value of the number of bytes in the actual data.
	 * Then, it constructs an array of bytes of proper size, and reads until the array is filled.
	 * Finally, it returns a String reconstructed from the UTF-8 data. </p>
	 * @return A String reconstructed from the data stream.
	 * @throws IOException from the underlying stream.
	 * @see BufferedInputStream
	 */
	public String read() throws IOException
	{
		while(!ready());
		byte[] dataSize = new byte[4];
		in.read(dataSize);
		byte[] data = new byte[ByteBuffer.wrap(dataSize).getInt()];
		in.read(data);
		return new String(data, StandardCharsets.UTF_8);
	}
}
