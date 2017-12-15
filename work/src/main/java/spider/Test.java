package spider;

import com.geccocrawler.gecco.GeccoEngine;

public class Test {
	public static void main(String[] args) {
		GeccoEngine.create().classpath("spider").start("http://www.panduoduo.net/s/name/mp4/1").thread(1).loop(false)
				.mobile(false).interval(2000).start();
	}
}
