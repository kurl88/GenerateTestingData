package cn.wangan.common.data;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * 生成NoSql数据文件
 * @author Peng Xiaodong
 */
public class GeneratingNoSqlData {

    private final char[] a2zLetters = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public void generate5GData(File file) {//5220条数据相当于50MB数据
        generateNoSqlDataAndWriteToFile(1, 534528, file, 1);
    }

    public void generate500GData(File file) {
        generateNoSqlDataAndWriteToFile(1000000000000000000L, 53452800, file, 100);
    }

    public void generate1TData(File file) {
        generateNoSqlDataAndWriteToFile(2000000000000000000L, 106905600, file, 200);
    }

    private void generateNoSqlDataAndWriteToFile(long startRowKeyIndex, long size, File file, int fileNum) {

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

            for (long i = startRowKeyIndex; i < startRowKeyIndex + size; i++) {
                StringBuilder noSqlData = new StringBuilder();
                String rowKey = String.format("%026d", i);
                noSqlData.append(rowKey);
                noSqlData.append("$[");

                for (int j = 0; j < 10; j++) {
                    noSqlData.append(j);
                    noSqlData.append("#");
                    noSqlData.append(generateFileValue());

                    if ((j + 1) < 10) {
                        noSqlData.append(",");
                    }
                }

                noSqlData.append("]");
                noSqlData.append("\n");

                br.write(noSqlData.toString());

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

    private String toFilePath(File file, int fileIndex) {
        String path = file.getAbsolutePath();
        String extension = FilenameUtils.getExtension(path);
        String pathNoExtension = FilenameUtils.removeExtension(path);
        return pathNoExtension + "_" + fileIndex + "." + extension;
    }

    private StringBuilder generateFileValue() {
        StringBuilder fileValue = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 125; i++) {
            int index = r.nextInt(a2zLetters.length);
            fileValue.append(StringUtils.repeat(a2zLetters[index], 8));
        }
        return fileValue;
    }

    public static void main(String[] args) {
        String dataSize = args[0];
        String filePath = args[1];
        GeneratingNoSqlData generatingNoSqlData = new GeneratingNoSqlData();
        if (StringUtils.equals(dataSize, "5G")) {
            generatingNoSqlData.generate5GData(new File(filePath));

        } else if (StringUtils.equals(dataSize, "500G")) {
            generatingNoSqlData.generate500GData(new File(filePath));

        } else if (StringUtils.equals(dataSize, "1T")) {
            generatingNoSqlData.generate1TData(new File(filePath));

        }
    }

}
