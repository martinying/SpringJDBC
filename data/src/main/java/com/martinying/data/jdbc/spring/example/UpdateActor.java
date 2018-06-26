package com.martinying.data.jdbc.spring.example;

import com.martinying.model.example.Actor;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

class UpdateActor extends SqlUpdate {

    UpdateActor(DataSource ds) {
        setDataSource(ds);
        setSql("update t_actor set first_name = ?, last_name = ? where id = ?");
        declareParameter(new SqlParameter("first_name", Types.VARCHAR));
        declareParameter(new SqlParameter("last_name", Types.VARCHAR));
        declareParameter(new SqlParameter("id", Types.NUMERIC));
        compile();
    }

    void execute(Actor actor) {
        update(actor.getFirst_name(), actor.getLast_name(), actor.getId());
    }
}

