package org.scada_lts.dao.migration.mysql;

import com.serotonin.mango.vo.DataPointVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.scada_lts.dao.DAO;
import org.scada_lts.dao.SerializationData;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Objects;

public class V2_6__ extends BaseJavaMigration {

    private static final Log LOG = LogFactory.getLog(V2_6__.class);

    @Override
    public void migrate(Context context) throws Exception {

        final JdbcTemplate jdbcTmp = DAO.getInstance().getJdbcTemp();

        updateDataPointsTable(jdbcTmp);

        jdbcTmp.execute("ALTER TABLE events ADD shortMessage LONGTEXT;");
        jdbcTmp.update("UPDATE events SET message = CONCAT(message, '||') WHERE typeId = 1;");
      
        String corectHistoryAlarms = ""+
          "CREATE OR REPLACE VIEW " +
          " historyAlarms AS " +
          " SELECT " +
          "  func_fromats_date(activeTime) AS 'activeTime', " +
          "  func_fromats_date(inactiveTime) AS 'inactiveTime', " +
          "  func_fromats_date(acknowledgeTime) AS 'acknowledgeTime', " +
          "  level, " +
          "  dataPointName AS 'name', " +
          "  dataPointId AS dataPointId " +
          "FROM plcAlarms " +
          "ORDER BY " +
          " inactiveTime = 0 DESC, " +
          " inactiveTime DESC, " +
          " id DESC;";

        jdbcTmp.execute(corectHistoryAlarms);

    }

    private void updateDataPointsTable(JdbcTemplate jdbcTmp) throws Exception {

        try {
            List<DataPointVO> dataPoints = jdbcTmp.query("SELECT id, data FROM dataPoints", (resultSet, i) -> {
                try (InputStream inputStream = resultSet.getBinaryStream("data");
                     ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                    DataPointVO dataPointVO = (DataPointVO) objectInputStream.readObject();
                    dataPointVO.setId(resultSet.getInt("id"));
                    return dataPointVO;
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                    return null;
                }
            });

            boolean isNull = dataPoints.stream().anyMatch(Objects::isNull);
            if (isNull) {
                throw new IllegalStateException("DataPointVO is null!");
            }

            for (DataPointVO dataPoint : dataPoints) {
                jdbcTmp.update("UPDATE dataPoints set data = ? WHERE id = ?",
                        new SerializationData().writeObject(dataPoint), dataPoint.getId());
            }

        } catch (EmptyResultDataAccessException empty) {
            LOG.warn(empty);
        }
    }
    
}