package util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {

    private static String CHARSET_NAME = "UTF-8";

    public static void writeFileContent(String content, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists())
            file.createNewFile();
        FileOutputStream out = new FileOutputStream(file, false);
        out.write(content.getBytes(CHARSET_NAME));
        out.close();
    }

    public static void writeFileContent(String content, String filePath, boolean append) throws IOException {
        File file = new File(filePath);
        if (!file.exists())
            file.createNewFile();
        FileOutputStream out = new FileOutputStream(file, append);
        out.write(content.getBytes(CHARSET_NAME));
        out.close();
    }

    public static String getFileContent(String filePath) throws IOException {
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader streamReader = new InputStreamReader(new FileInputStream(new File(filePath)), CHARSET_NAME);
        BufferedReader reader = new BufferedReader(streamReader);
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();
        return stringBuilder.toString();
    }

    public static boolean isFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static Map<String, String> getFileProperty(String filePath) {
        Map<String, String> result = new HashMap<String, String>();
        Path path = Paths.get(filePath);
        BasicFileAttributeView basicview = Files.getFileAttributeView(path, BasicFileAttributeView.class);
        BasicFileAttributes basicfile = null;
        try {
            basicfile = basicview.readAttributes();
            result.put("creationTime", String.valueOf(basicfile.creationTime().toMillis()));
            result.put("size", String.valueOf(basicfile.size()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        getFileProperty("C:\\Users\\Administrator\\Desktop\\诡神冢\\369.mp3");
    }

}
