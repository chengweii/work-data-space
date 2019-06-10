package pdf;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.IOException;

/**
 * 内存不够时 设置一下启动参数 -Xmx1024m
 *
 * @author chengwei11
 * @date 2019/6/10
 */
public class PdfMerger {
    /**
     * @param folder
     */
    private static String[] getFiles(String folder) throws IOException {
        File _folder = new File(folder);
        String[] filesInFolder;

        if (_folder.isDirectory()) {
            filesInFolder = _folder.list();
            return filesInFolder;
        } else {
            throw new IOException("Path is not a directory");
        }
    }

    public static void main(String[] args) throws Exception {
        PDFMergerUtility mergePdf = new PDFMergerUtility();

        String folder = "C:\\Users\\chengwei11\\Desktop\\并发编程";
        String destinationFileName = "合并文件.pdf";

        String[] filesInFolder = getFiles(folder);

        for (int i = 0; i < filesInFolder.length; i++) {
            mergePdf.addSource(folder + File.separator + filesInFolder[i]);
        }

        mergePdf.setDestinationFileName(folder + File.separator + destinationFileName);
        mergePdf.mergeDocuments();

        System.out.print("done");
    }
}
