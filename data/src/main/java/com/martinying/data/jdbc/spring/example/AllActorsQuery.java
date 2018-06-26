package com.martinying.data.jdbc.spring.example;

import com.martinying.model.example.Actor;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AllActorsQuery extends MappingSqlQuery<Actor> {

    AllActorsQuery(DataSource ds) {
        super(ds, "select * from t_actor");
        compile();
    }

    @Override
    protected Actor mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Actor(resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"));
    }
}
