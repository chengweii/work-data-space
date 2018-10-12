import util.MD5Util;

import java.io.IOException;
import java.util.*;

public class Test {
	public static void main(String[] args) throws Exception {
		System.out.println(random(1000L,10000L));
	}

	private static long random(long begin, long end) {
		return (long) (begin + Math.random() * (end - begin + 1));
	}
}
