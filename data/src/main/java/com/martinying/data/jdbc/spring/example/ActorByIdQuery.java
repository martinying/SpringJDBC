package com.martinying.data.jdbc.spring.example;

import com.martinying.model.example.Actor;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ActorByIdQuery extends MappingSqlQuery<Actor> {

    ActorByIdQuery(DataSource ds) {
        super(ds, "select * from t_actor where id = ?");
        declareParameter(new SqlParameter("id", Types.INTEGER));
        compile();
    }

    @Override
    protected Actor mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Actor(resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"));

    }
}
