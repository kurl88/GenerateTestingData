package cn.wangan.common.data;

import org.junit.Before;
import org.junit.Test;

public class GeneratingFullTextDataTest {

    private GeneratingFullTextData generatingFullTextData;

    @Before
    public void before() {
        generatingFullTextData = new GeneratingFullTextData();
    }

    @Test
    public void testGenerateFullTextData30M() {
        generatingFullTextData.generateFullTextData30M("G:\\fullText_30M.txt");
    }

    @Test
    public void testGenerateFullTextData3G() {
        generatingFullTextData.generateFullTextData3G("G:\\fullText_3G.txt");
    }
}
