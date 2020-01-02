import java.util.zip.CRC32;

public class Test {
    public static void main(String[] args) throws Exception {
        CRC32 crc32 = new CRC32();
        crc32.update("记录压缩列表表尾距离起始位置有多少字节".getBytes());
        System.out.println(crc32.getValue());
    }
}
