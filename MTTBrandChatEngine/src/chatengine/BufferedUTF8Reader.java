package chatengine;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class BufferedUTF8Reader
{
	private BufferedInputStream in;
	
	public BufferedUTF8Reader(InputStream in)
	{
		this.in = new BufferedInputStream(in);
		
	}

	public boolean ready() throws IOException
	{
		return in.available() > 0;
	}

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
