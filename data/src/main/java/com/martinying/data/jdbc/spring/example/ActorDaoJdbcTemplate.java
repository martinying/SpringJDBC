package com.martinying.data.jdbc.spring.example;

import com.martinying.model.example.Actor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ActorDaoJdbcTemplate {
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    Actor createActor(Actor newActor) {
        String sql = "insert into t_actor (first_name, last_name) values (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(sql, new String[]{"id"});
                        ps.setString(1, newActor.getFirst_name());
                        ps.setString(2, newActor.getLast_name());
                        return ps;
                    }
                }, keyHolder);
        int newID = keyHolder.getKey().intValue();
        return new Actor(newID, newActor.getFirst_name(), newActor.getLast_name());
    }

    Actor getActorById(int id) {
        return this.jdbcTemplate.queryForObject("select * from t_actor where id=?",
                new Object[]{id},
                new ActorMapper()
        );
    }

    List<Actor> getAllActors() {
        return this.jdbcTemplate.query(
                "select * from t_actor",
                new ActorMapper()
        );
    }

    void updateActor(Actor actor) {
        this.jdbcTemplate.update(
                "update t_actor set first_name = ?, last_name= ? where id = ?",
                actor.getFirst_name(), actor.getLast_name(), actor.getId()
        );
    }

    void deleteActor(Actor actor) {
        this.jdbcTemplate.update(
                "delete from t_actor where id = ?",
                actor.getId()
        );
    }
}
