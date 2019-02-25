package cn.wangan.common.data;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GeneratingFullTextData {

    private String toFilePath(File file, int fileIndex) {
        String path = file.getAbsolutePath();
        String extension = FilenameUtils.getExtension(path);
        String pathNoExtension = FilenameUtils.removeExtension(path);
        return pathNoExtension + "_" + fileIndex + "." + extension;
    }

    public void generateFullTextData(long size, File file, int fileNum) {

        String urlHead = "https://www.huxiu.com/autoGenerate/";
        String content = "其实不止苹果耳塞，很多耳塞上都会有莫名其妙的孔洞。我们常常能看到不明真相的用户对着耳塞说话，把这些洞当成麦克风使，这是错误的——拿苹果来说，麦克风在线控部分，而不在耳塞上。  还有人误导新手说那是某个频率声音的出口，比如低音或高音……对这种言论，笑笑就得了。仔细看的话，这个孔的直径一般不会超过0.5mm，这对传声能有什么影响呢？  但实际上，没有这个孔的话，耳机的发声还真的会受到影响。这与耳塞的工作模式有关。扬声器振动发出声音，但扬声器的振动也使耳塞腔体内的气压上升。当内压高到一定程度时，扬声器自身的振动就会受阻——这当然会影响耳塞的正常发声。  解决方案可谓再简单不过：就是这些洞了。随着振动，空气从洞内流入流出，调节内外的气压平衡。扬声器不再受到气压的干扰，发声就更加自然流畅了。";

        long fileLineNum = size / fileNum;
        int fileIndex = 1;
        BufferedWriter br = null;
        try {
            if (fileNum == 1) {
                br = new BufferedWriter(new FileWriter(file), 10240);
            } else {
                String newPath = toFilePath(file, fileIndex);
                br = new BufferedWriter(new FileWriter(newPath), 10240);
            }

            for (long i = 1; i < size; i++) {

                String url = urlHead + i;
                br.write(url + "\t" + content + "\n");

                if ((i + 1) % fileLineNum == 0 && fileIndex < fileNum) {
                    IOUtils.closeQuietly(br);
                    ++ fileIndex;
                    br = new BufferedWriter(new FileWriter(toFilePath(file, fileIndex)), 10240);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(br);
        }

    }

    public void generateFullTextData30M(String file) {
        generateFullTextData(30000, new File(file), 1);
    }

    public void generateFullTextData3G(String file) {
        generateFullTextData(3072000, new File(file), 10);
    }

    public void generateFullTextData30G(String file) {
        generateFullTextData(30720000, new File(file), 40);
    }

    public static void main(String[] args) {
        GeneratingFullTextData generatingFullTextData = new GeneratingFullTextData();
        String dataSize = args[0];
        String filePath = args[1];
        if (StringUtils.equals(dataSize, "30M")) {
            generatingFullTextData.generateFullTextData30M(filePath);

        } else if (StringUtils.equals(dataSize, "3G")) {
            generatingFullTextData.generateFullTextData3G(filePath);

        } else if (StringUtils.equals(dataSize, "30G")) {
            generatingFullTextData.generateFullTextData30G(filePath);

        }
    }

}
