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
		// �ļ������ڻ��ļ�Ϊ��,��ʱ�ǵ�һ��д���ļ�������Ҫ��ͷ����Ϣд�롣
		if (!f.exists() || (f.exists() && f.length() == 0)) {
			super.writeStreamHeader();
		} else {
			// ����Ҫ���κ�����
		}
	}

}
