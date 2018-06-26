package com.martinying.data.jdbc.spring.example;

import com.martinying.model.example.Actor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ActorMapper implements RowMapper<Actor> {
    @Override
    public Actor mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Actor(resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"));
    }
}
