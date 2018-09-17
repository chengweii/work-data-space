package vbs;

public class Test {
	public static void main(String[] args) throws Exception {
		String[] Cmd  = new String[]{"wscript", "work-data-space\\work\\src\\main\\java\\vbs\\alert.vbs"};
		Process	process = Runtime.getRuntime().exec(Cmd);
		process.waitFor();
	}
}
