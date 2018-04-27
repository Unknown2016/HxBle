package com.hexin.apicloud.ble.util;
import java.io.*;
import java.security.MessageDigest;

/**
 * <p>类: StringUtils</p>
 * <p>说明</p>
 *
 * @author ender 创建于 16-4-29
 */
public final class StringUtil{
	private StringUtil() {
	}

	// 16进制数组
	private final static String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

	/**
	 * 读取流
	 *
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readInputStreamToBytes(InputStream inputStream) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			// 将输入流转移到内存输出流中
			byte[] buffer = new byte[1024];
			int len;
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			return out.toByteArray();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static String readInputStreamReader(InputStreamReader inputStream) throws IOException {
		StringBuffer out = new StringBuffer();
		// 将输入流转移到内存输出流中
		char[] buffer = new char[1024];
		int len;
		while ((len = inputStream.read(buffer)) != -1) {
			out.append(new String(buffer, 0, len));
		}
		return out.toString();
	}

	/**
	 * 读取流字符串
	 *
	 * @param inputStream
	 * @return
	 */
	public static String readInputStream(InputStream inputStream, String charset) {
		InputStreamReader isr = new InputStreamReader(inputStream);
		try {
			return readInputStreamReader(isr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 读取流字符串
	 *
	 * @param inputStream
	 * @return
	 */
	public static String readInputStream(InputStream inputStream) {
		try {
			return new String(readInputStreamToBytes(inputStream));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 判断字符串是否是空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 创建MD5码
	 *
	 * @param source
	 * @return
	 */
	public static String MD5Encode(String source) {
		return MD5Encode(source, "UTF-8");
	}

	/**
	 * 创建MD5码
	 *
	 * @param source  字符串
	 * @param charset 字符集
	 * @return
	 */

	public static String MD5Encode(String source, String charset) {
		try {
			return MD5Encode(source.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			return source;
		}
	}

	/**
	 * 创建MD5码
	 *
	 * @param bytes 字节
	 * @return MD5
	 */
	public static String MD5Encode(byte[] bytes) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return byteArrayToHexString(md.digest(bytes));
		} catch (Exception ex) {
			return new String(bytes);
		}
	}

	/**
	 * 转换字节数组为16进制字串
	 *
	 * @param b -字节数组
	 * @return 16-进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 将字节转换为16进制字符串
	 *
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return HEX_DIGITS[d1] + HEX_DIGITS[d2];
	}

	/**
	 * 数组连接
	 *
	 * @param delimiter
	 * @param elements
	 * @return
	 */
	public static String join(CharSequence delimiter, Iterable<? extends CharSequence> elements) {
		StringBuilder sb = new StringBuilder();
		boolean isOld = false;
		for (CharSequence c : elements) {
			if (isOld) {
				sb.append(delimiter);
			}
			sb.append(c);
			isOld = true;
		}
		return sb.toString();
	}

	/**
	 * 将宝贝标题模糊化
	 *
	 * @param title
	 * @return 王->王**王；王五->王**五；王小五->王**五
	 */
	public static String fuzzyTitle(String title) {
		return fuzzyNick(title);
	}

	/**
	 * 将昵称模糊化
	 *
	 * @param nick
	 * @return 王->王**王；王五->王**五；王小五->王**五
	 */
	public static String fuzzyNick(String nick) {
		if (StringUtil.isEmpty(nick)) {
			return "";
		}
		String n = nick.trim();
		return n.substring(0, 1) + "**" + n.substring(n.length() - 1, n.length());
	}

	/**
	 * 将大于6位的电话号码的4~7中的4位模糊
	 *
	 * @param tel
	 * @return
	 */
	public static String fuzzyTel(String tel) {
		if (StringUtil.isEmpty(tel)) {
			return "";
		}
		String t = tel.trim();
		return t.substring(0, 3) + "****" + t.substring(7, t.length());
	}

	/**
	 * 编辑距离计算
	 *
	 * @param a 字符串
	 * @param b 字符串
	 * @return 距离
	 */
	public static int levenshtein(String a, String b) {
		if (a.equals(b))
			return 0;
		if (isEmpty(a))
			return b.length();
		if (isEmpty(b))
			return a.length();

		int[] previous = new int[a.length() + 1];
		previous[0] = 0;
		for (int i = 0; i < a.length(); i++) {
			previous[i + 1] = i + 1;
		}
		int[][] matrix = new int[a.length() + 1][a.length() + 1];
		matrix[0] = previous;
		int[] current = new int[a.length() + 1];
		for (int i = 0; i < b.length(); i++) {
			current = new int[a.length() + 1];
			current[0] = i + 1;
			for (int f = 0; f < a.length(); f++) {
				if (a.charAt(f) == b.charAt(i)) {
					current[f + 1] = previous[f];
				} else {
					current[f + 1] = Math.min(previous[f + 1] + 1, Math.min(current[f] + 1, previous[f] + 1));
				}
			}
			previous = current;
			matrix[i] = previous;
		}
		return current[current.length - 1];
	}

	/**
	 * 复制对象
	 *
	 * @param str
	 * @return
	 */
	public static String copy(String str) {
		return isEmpty(str) ? str : new String(str.toCharArray());
	}

	/**
	 * 字符串合并
	 *
	 * @param str
	 * @return
	 */
	public static String merge(String... str) {
		StringBuilder sb = new StringBuilder();
		for (String s : str) {
			if (!isEmpty(s)) {
				sb.append(s);
			}
		}
		return sb.toString();
	}

	public static String[] split(String str) {
		return str.split("\\s*[;|,|\\s]\\s*");
	}
}
