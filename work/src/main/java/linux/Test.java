package linux;

public class Test {
	public static void main(String[] args) throws Exception {
		Shell ssh =  Shell.newInstance();
		System.out.println("================");
		/*long shell1 = ssh.shell("ls\n","C:\\Users\\hidden\\Desktop\\shell.txt");
		long shell2 = ssh.shell("pwd\n","C:\\Users\\hidden\\Desktop\\shell.txt");
		System.out.println("shell 1 执行了"+shell1+"ms");
		System.out.println("shell 2 执行了"+shell2+"ms");*/
		System.out.println("================");
		int cmd1 = ssh.exec("ls /data\n");
		ssh.close();
	}
}
