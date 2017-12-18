package spider.panduoduo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.geccocrawler.gecco.GeccoEngine;

import util.FileUtil;

public class Main {
	protected static final String BASE_PATH = "http://www.panduoduo.net";

	public static List<String> keywords = new ArrayList<String>();
	public static int keywordIndex = 0;
	static {
		try {
			String keywordContent = FileUtil.getFileContent("src/main/java/spider/panduoduo/keywords.txt");
			String[] items = keywordContent.split(",");
			for (String item : items) {
				keywords.add(item);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		search();
	}

	public static void search() {
		int currentIndex = keywordIndex;
		keywordIndex++;
		GeccoEngine.create().classpath("spider").start(BASE_PATH + "/s/name/" + keywords.get(currentIndex) + "/1")
				.thread(1).loop(false).mobile(false).interval(2000).start();
	}
}
