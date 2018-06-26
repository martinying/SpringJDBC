package com.martinying.data.jdbc.spring.example;

import com.martinying.model.example.Actor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;

public class ActorDao {

    private SimpleJdbcInsert simpleJdbcInsert;
    private ActorByIdQuery actorByIdQuery;
    private AllActorsQuery allActorsQuery;
    private UpdateActor updateActor;
    private DeleteActor deleteActor;

    public void setDataSource(DataSource dataSource) {
        this.actorByIdQuery = new ActorByIdQuery(dataSource);
        this.allActorsQuery = new AllActorsQuery(dataSource);
        this.updateActor = new UpdateActor(dataSource);
        this.deleteActor = new DeleteActor(dataSource);

        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("t_actor").
                usingColumns("first_name", "last_name"). //so insert doesn't try to insert id
                usingGeneratedKeyColumns("id");

    }

    Actor createActor(Actor newActor) {
        Number newId = simpleJdbcInsert.executeAndReturnKey(
                new BeanPropertySqlParameterSource(newActor)
        );
        return new Actor(newId.intValue(), newActor.getFirst_name(), newActor.getLast_name());
    }

    Actor getActorById(int id) {
        return actorByIdQuery.findObject(id);
    }

    List<Actor> getAllActors() {
        return allActorsQuery.execute();
    }

    void updateActor(Actor actor) {
        updateActor.execute(actor);
    }

    void deleteActor(Actor actor) {
        deleteActor.delete(actor.getId());
    }

}
