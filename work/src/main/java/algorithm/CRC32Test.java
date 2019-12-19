package algorithm;

import java.util.zip.CRC32;

/**
 * 字符串压缩为数字
 *
 * @author chengwei11
 * @date 2019/12/19
 */
public class CRC32Test {
    public static void main(String[] args) {
        CRC32 crc32 = new CRC32();
        crc32.update("记录压缩列表表尾距离起始位置有多少字节".getBytes());
        System.out.println(crc32.getValue());
    }
}
