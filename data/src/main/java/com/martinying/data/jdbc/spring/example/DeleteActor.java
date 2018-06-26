package com.martinying.data.jdbc.spring.example;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

class DeleteActor extends SqlUpdate {
    DeleteActor(DataSource ds) {
        this.setDataSource(ds);
        this.setSql("delete from t_actor where id = ?");
        declareParameter(new SqlParameter("id", Types.NUMERIC));
        compile();
    }

    void delete(int id) {
        update(id);
    }
}
