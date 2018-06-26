package com.martinying.data.jdbc.spring.example;

import com.martinying.model.example.Actor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.List;

public class ActorDaoNamedParameterJdbcTemplate {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    Actor createActor(Actor newActor) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("first_name", newActor.getFirst_name());
        namedParameters.addValue("last_name", newActor.getLast_name());
        String sql = "insert into t_actor (first_name, last_name) values (:first_name, :last_name)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder, new String[]{"id"});
        int newID = (Integer) keyHolder.getKey();
        return new Actor(newID, newActor.getFirst_name(), newActor.getLast_name());
    }

    Actor getActorById(int id) {
        return this.namedParameterJdbcTemplate.queryForObject("select * from t_actor where id=:id",
                new MapSqlParameterSource("id", id),
                new ActorMapper()
        );
    }

    List<Actor> getAllActors() {
        return this.namedParameterJdbcTemplate.query(
                "select * from t_actor",
                new ActorMapper()
        );
    }

    void updateActor(Actor actor) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("first_name", actor.getFirst_name());
        namedParameters.addValue("last_name", actor.getLast_name());
        namedParameters.addValue("id", actor.getId());

        this.namedParameterJdbcTemplate.update(
                "update t_actor set first_name = :first_name, last_name= :last_name where id = :id",
                namedParameters
        );
    }

    void updateActorBeanPropertySqlPamaterSource(Actor actor) {
        this.namedParameterJdbcTemplate.update(
                "update t_actor set first_name = :first_name, last_name= :last_name where id = :id",
                new BeanPropertySqlParameterSource(actor)
        );
    }

    void deleteActor(Actor actor) {
        this.namedParameterJdbcTemplate.update(
                "delete from t_actor where id = :id",
                new MapSqlParameterSource("id", actor.getId())
        );
    }
}
