package org.scada_lts.dao.migration.mysql;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.scada_lts.dao.DAO;
import org.springframework.jdbc.core.JdbcTemplate;

public class V2_7_0_5__UserNames extends BaseJavaMigration {
    private static final Log LOG = LogFactory.getLog(V2_7_0_5__UserNames.class);

    @Override
    public void migrate(Context context) throws Exception {
        final JdbcTemplate jdbcTemplate = DAO.getInstance().getJdbcTemp();
        addFirstAndLastNameToUsersTable(jdbcTemplate);
    }

    void addFirstAndLastNameToUsersTable(JdbcTemplate jdbcTemplate) {
        try {
            String tableName = "users";
            String alterSql = "" +
                    "ALTER TABLE " + tableName +
                    " ADD firstName VARCHAR(255) DEFAULT ''," +
                    " ADD lastName VARCHAR(255) DEFAULT '';";
            jdbcTemplate.execute(alterSql);
        } catch (Exception e) {
            LOG.error("Exception during v2.7.0.5 Migration!");
            LOG.error(e);
        }
    }
}
