package cn.wangan.common.data;

import org.junit.Before;
import org.junit.Test;

public class GeneratingGraphDataTest {

    private GeneratingGraphData generatingGraphData;

    @Before
    public void before() {
        generatingGraphData = new GeneratingGraphData();
    }

    @Test
    public void testGenerateGraphData50M() {
        generatingGraphData.generateGraphData50M("G:\\graph_50M.csv");
    }
}
