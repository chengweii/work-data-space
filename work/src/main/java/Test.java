import util.MD5Util;

import java.io.IOException;
import java.util.*;

public class Test {
	public static void main(String[] args) throws Exception {
		String[] Cmd  = new String[]{"wscript", "E:\\alert.vbs"};
		Process	process = Runtime.getRuntime().exec(Cmd);
		process.waitFor();
	}
}
