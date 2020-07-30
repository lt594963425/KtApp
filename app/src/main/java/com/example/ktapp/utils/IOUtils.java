package com.example.ktapp.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {
	/** 关闭流 */
	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
			}
		}
		return true;
	}

	public static void copyStream(InputStream in, OutputStream out){
		try {
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = in.read(buffer)) > 0) {
				out.write(buffer, 0, i);
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
}
