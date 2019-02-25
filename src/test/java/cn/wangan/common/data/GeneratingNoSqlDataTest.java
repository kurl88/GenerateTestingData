package cn.wangan.common.data;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class GeneratingNoSqlDataTest {

    private GeneratingNoSqlData generatingNoSqlData;

    @Before
    public void before() {
        generatingNoSqlData = new GeneratingNoSqlData();
    }

    @Test
    public void testGenerate5GData() {
        File file = new File("C:\\Users\\Shadon\\Desktop\\no_sql_5G.txt");
        if (file.exists()) {
            file.delete();
        }
        generatingNoSqlData.generate5GData(file);
    }

    @Test
    public void testGenerate500GData() {
        File file = new File("C:\\Users\\Shadon\\Desktop\\no_sql_500G.txt");
        if (file.exists()) {
            file.delete();
        }
        generatingNoSqlData.generate500GData(file);
    }

    @Test
    public void testGenerate1TData() {
        File file = new File("C:\\Users\\Shadon\\Desktop\\no_sql_1T.txt");
        if (file.exists()) {
            file.delete();
        }
        generatingNoSqlData.generate1TData(file);
    }

}
