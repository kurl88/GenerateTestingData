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
 * 生成NWordCount数据文件
 * @author Mengfl
 */
public class GeneratingWordSData {



//    public void generate5GData(File file) {//5220条数据相当于50MB数据
//        generateNoSqlDataAndWriteToFile(1, 534528, file, 1);
//    }
    public void generate5GData(File file) {//5220条数据相当于50MB数据
        generateNoSqlDataAndWriteToFile(1, 534528, file, 1);
    }

    public void generate500GData(File file) {
        generateNoSqlDataAndWriteToFile(1000000000000000000L, 53452800, file, 100);
    }

    public void generate3TData(File file) {
        generateNoSqlDataAndWriteToFile(2000000000000000000L, 320716800, file, 600);
    }

    private void generateNoSqlDataAndWriteToFile(long startRowKeyIndex, long size, File file, int fileNum) {

        long fileLineNum = size / fileNum;
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
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
                noSqlData.append(generateFileValue());
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
       return generateRandomLineWord();
    }

    public StringBuilder generateRandomLineWord() {
        Random r = new Random(); // Intialize a Random Number Generator with SysTime as the seed
        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < 1280; j++) {
            int wordLength = r.nextInt(15);
            for(int i = 0; i < wordLength; i++) { // For each letter in the word
                char c = (char)(Math.random() * 26 + 'a');
                sb.append(c); // Add it to the String
            }
            sb.append(" "); // Add it to the String

        }

        return sb;
    }
    public static void main(String[] args) {
        String dataSize = args[0];
        String filePath = args[1];
//        String  dataSize ="5G";
//        String filePath="E:\\data\\noSql\\nosql_500G.txt";
        GeneratingWordSData generatingNoSqlData = new GeneratingWordSData();
        if (StringUtils.equals(dataSize, "5G")) {
            generatingNoSqlData.generate5GData(new File(filePath));

        } else if (StringUtils.equals(dataSize, "500G")) {
            generatingNoSqlData.generate500GData(new File(filePath));

        } else if (StringUtils.equals(dataSize, "1T")) {
            generatingNoSqlData.generate3TData(new File(filePath));

        }
    }

}
