package util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

/**
 * 获得MP3文件的信息
 * <p>
 * Mp3 最后128字节存储信息格式：
 * 字节     长度 (字节)  说       明
 * 1-3     3       存放“TAG”字符，表示ID3 V1.0标准，紧接其后的是歌曲信息。
 * 4-33    30      歌名
 * 34-63   30      作者
 * 64-93   30      专辑名
 * 94-97   4       年份
 * 98-127  30      附注
 * 128     1       MP3音乐类别，共147种。
 */
public class MP3Util {

    public static class MP3Info {

        private String charset = "UTF-8";//解析MP3信息时用的字符编码

        private byte[] buf;//MP3的标签信息的byte数组

        /**
         * 实例化一个MP3文件的信息的类
         *
         * @param mp3 MP3文件
         * @throws IOException 读取MP3出错则MP3文件不存在
         */
        public MP3Info(File mp3) throws IOException {

            buf = new byte[128];//初始化标签信息的byte数组

            RandomAccessFile raf = new RandomAccessFile(mp3, "r");//随机读写方式打开MP3文件
            raf.seek(raf.length() - 128);//移动到文件MP3末尾
            raf.read(buf);//读取标签信息

            raf.close();//关闭文件

            if (buf.length != 128) {//数据是否合法
                System.out.println("MP3标签信息数据长度不合法!");
                throw new IOException("MP3标签信息数据长度不合法!");
            }

            if (!"TAG".equalsIgnoreCase(new String(buf, 0, 3))) {//信息格式是否正确
                System.out.println("MP3标签信息数据格式不正确!");
                throw new IOException("MP3标签信息数据格式不正确!");
            }

        }

        /**
         * 得到歌曲名称
         *
         * @return
         */
        public String getSongName() {
            try {
                return new String(buf, 3, 30, charset).trim();
            } catch (UnsupportedEncodingException e) {
                return new String(buf, 3, 30).trim();
            }
        }

        /**
         * 得到专辑名称
         *
         * @return
         */
        public String getArtist() {
            try {
                return new String(buf, 33, 30, charset).trim();
            } catch (UnsupportedEncodingException e) {
                return new String(buf, 33, 30).trim();
            }
        }

        public String getAlbum() {
            try {
                return new String(buf, 63, 30, charset).trim();
            } catch (UnsupportedEncodingException e) {
                return new String(buf, 63, 30).trim();
            }
        }

        /**
         * 获取年份
         *
         * @return
         */
        public String getYear() {
            try {
                return new String(buf, 93, 4, charset).trim();
            } catch (UnsupportedEncodingException e) {
                return new String(buf, 93, 4).trim();
            }
        }

        /**
         * 获取歌曲描述
         *
         * @return
         */
        public String getComment() {
            try {
                return new String(buf, 97, 30, charset).trim();
            } catch (UnsupportedEncodingException e) {
                return new String(buf, 97, 30).trim();
            }
        }

        /**
         * 获得目前解析时用的字符编码
         *
         * @return 目前解析时用的字符编码
         */
        public String getCharset() {
            return charset;
        }

        /**
         * 设置解析时用的字符编码
         *
         * @param charset 解析时用的字符编码
         */
        public void setCharset(String charset) {
            this.charset = charset;
        }
    }

    public static MP3Info getMP3Info(String path) {
        try {
            File file = new File(path);
            return new MP3Info(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        MP3Info info = MP3Util.getMP3Info("C:\\Users\\Administrator\\Desktop\\诡神冢\\369.mp3");
        System.out.println(info.getSongName());
    }
}