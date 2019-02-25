package cn.wangan.common.data;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GeneratingGraphData {

    private String toFilePath(File file, int fileIndex) {
        String path = file.getAbsolutePath();
        String extension = FilenameUtils.getExtension(path);
        String pathNoExtension = FilenameUtils.removeExtension(path);
        return pathNoExtension + "_" + fileIndex + "." + extension;
    }

    public void generateGraphData(long size, File file, int fileNum) {

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

            br.write("Person.id|Person.id|creationDate\n");


            Random r = new Random();
            for (long i = 1; i < size; i++) {
                long startNode = Math.abs(r.nextLong());
                long endNode = Math.abs(r.nextLong());

                br.write(startNode + "|" + endNode + "|" + "2010-09-20T09:42:43.187+0000\n");

                if ((i + 1) % fileLineNum == 0 && fileIndex < fileNum) {
                    IOUtils.closeQuietly(br);
                    ++ fileIndex;
                    br = new BufferedWriter(new FileWriter(toFilePath(file, fileIndex)), 10240);

                    br.write("Person.id|Person.id|creationDate\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(br);
        }

    }

    public void generateGraphData50M(String file) {
        generateGraphData(770000, new File(file), 1);
    }

    public void generateGraphData5G(String file) {
        generateGraphData(78848000, new File(file), 10);
    }

    public void generateGraphData500G(String file) {
        generateGraphData(7884800000L, new File(file), 50);
    }

    public static void main(String[] args) {
        GeneratingGraphData generatingFullTextData = new GeneratingGraphData();
        String dataSize = args[0];
        String filePath = args[1];
        if (StringUtils.equals(dataSize, "50M")) {
            generatingFullTextData.generateGraphData50M(filePath);

        } else if (StringUtils.equals(dataSize, "5G")) {
            generatingFullTextData.generateGraphData5G(filePath);

        } else if (StringUtils.equals(dataSize, "500G")) {
            generatingFullTextData.generateGraphData500G(filePath);

        }
    }

}
