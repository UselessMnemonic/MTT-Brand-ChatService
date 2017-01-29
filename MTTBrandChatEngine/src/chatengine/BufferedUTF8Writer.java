package chatengine;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * 
 * @author Christopher Madrigal
 * The BufferedUTF8Writer is an implementation of a BufferedWriter which is capable of sending strings
 * by encoding them as UTF-8 characters.
 */
public class BufferedUTF8Writer
{
	private BufferedOutputStream out;
	
	/**
	 * Constructs a BufferedUTF8Writer from an OutputStream.
	 * @param out The OutputStream that will provide data.
	 */
	public BufferedUTF8Writer(OutputStream out)
	{
		this.out = new BufferedOutputStream(out);
	}
	
	/**
	 * Writes a stream of bytes to the underlying OutputStream from a String object.
	 * 
	 * <p>The stream consists of a header that specifies the length of the data in bytes,
	 * and the actual data, where the data is UTF-8 encoded.
	 * It first writes 4 bytes, the integer value of the number of bytes in the actual data.
	 * Then, it constructs an array of bytes of proper size, and writes until the array has been completely written.
	 * Finally, it flushes the stream to ensure that all data has been passed through.</p>
	 * 
	 * @param messageContent The String that will provide data.
	 * @throws IOException Thrown from the underlying stream.
	 * @see BufferedOutputStream
	 */
	public void write(String messageContent) throws IOException
	{
		byte[] data = messageContent.getBytes(StandardCharsets.UTF_8); //get data to send, consists of the String's characters encoded in UTF-8
		byte[] dataSize = ByteBuffer.allocate(4).putInt(data.length).array(); //get the number of bytes to send from an int to an array of bytes
		out.write(dataSize); //write the number of bytes we are sending to the stream
		out.write(data); //write the actual data
		out.flush(); //make sure we've sent everything
	}
}
