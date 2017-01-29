package chatengine;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/*
 * Stream format
 * 
 * [DATA SIZE][UTF8-DATA][NULL]
 */

public class BufferedUTF8Writer
{
	private BufferedOutputStream out;
	
	public BufferedUTF8Writer(OutputStream out)
	{
		this.out = new BufferedOutputStream(out);
	}

	public void write(String messageContent) throws IOException
	{
		byte[] data = messageContent.getBytes(StandardCharsets.UTF_8);
		byte[] dataSize = ByteBuffer.allocate(4).putInt(data.length).array();
		out.write(dataSize);
		out.write(data);
		out.flush();
	}
}
