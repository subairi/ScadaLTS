package org.scada_lts.scripting;

import com.serotonin.db.IntValuePair;
import com.serotonin.mango.vo.DataPointVO;
import org.junit.Before;
import org.junit.Test;
import org.scada_lts.dao.DataPointDAO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class VarAsXidMigrationTest {

    List<IntValuePair> pointsOnContext = new ArrayList<>();

    private String updateScript(String script, List<IntValuePair> pointsOnContext) {
        String updated = script;
        for (IntValuePair point : pointsOnContext) {
            if (point.getValue().matches("^p[0-9]+$")) {
                String xid = "changed";
                updated = updated.replaceAll("(?<![a-zA-Z0-9])" + point.getValue() + "(?![a-zA-Z0-9])", xid.toLowerCase().trim());
            }
        }
        return updated;
    }

    private void updatePointsOnContext(List<IntValuePair> pointsOnContext) {
        for (IntValuePair point : pointsOnContext) {
            if (point.getValue().matches("^p[0-9]+$")) {
                DataPointVO dataPointVO = new DataPointDAO().getDataPoint(point.getKey());
                point.setValue(dataPointVO.getXid().toLowerCase().trim());
            }
        }
    }

    @Before
    public void initList() {
        pointsOnContext.add(new IntValuePair(1, "p1"));
        pointsOnContext.add(new IntValuePair(2, "p2"));
        pointsOnContext.add(new IntValuePair(3, "p3"));
        pointsOnContext.add(new IntValuePair(4, "p4"));
        pointsOnContext.add(new IntValuePair(5, "p5"));
        pointsOnContext.add(new IntValuePair(6, "p6"));
        pointsOnContext.add(new IntValuePair(7, "p7"));
    }

    @Test
    public void testUpdateScript() {

        String toUpdate = "if (p1 == p3) {\n" +
                "p2 = p4;\n" +
                "p5 = p15;\n" +
                "} else {\n" +
                "return p11 & ap6 & p16;\n" +
                "}\n" +
                "p14 = p13 + p12 + p11 + p10 + p9 + p8 + p7 + p6;\n" +
                "obj.p1 = arr.p8;\n" +
                "open(p3,p7);";

        String expected = "if (changed == changed) {\n" +
                "changed = changed;\n" +
                "changed = p15;\n" +
                "} else {\n" +
                "return p11 & ap6 & p16;\n" +
                "}\n" +
                "p14 = p13 + p12 + p11 + p10 + p9 + p8 + changed + changed;\n" +
                "obj.changed = arr.p8;\n" +
                "open(changed,changed);";

        toUpdate = updateScript(toUpdate, pointsOnContext);
        assertEquals(expected, toUpdate);
    }
}
