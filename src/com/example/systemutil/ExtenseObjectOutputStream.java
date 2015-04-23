package com.example.systemutil;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ExtenseObjectOutputStream extends ObjectOutputStream {

	private static File f;
	
	protected ExtenseObjectOutputStream(OutputStream out, File file) throws IOException {
		super(out);
		// TODO Auto-generated constructor stub
	}
	
	public static ExtenseObjectOutputStream newInstance(File file, OutputStream out)
	throws IOException{
		f = file;
		return new ExtenseObjectOutputStream(out,f);
	}

	@Override
	protected void writeStreamHeader() throws IOException {
		// 文件不存在或文件为空,此时是第一次写入文件，所以要把头部信息写入。
		if (!f.exists() || (f.exists() && f.length() == 0)) {
			super.writeStreamHeader();
		} else {
			// 不需要做任何事情
		}
	}

}
