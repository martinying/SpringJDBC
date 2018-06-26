package com.martinying.data.jdbc.spring.example;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.martinying.model.example.Actor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(locations = "/dao-context.xml")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DirtiesContext
public class ActorDaoJdbcTemplateTest {
    @Autowired
    ActorDaoJdbcTemplate actorDaoJdbcTemplate;

    @Test
    @DatabaseSetup("/initData.xml")
    @ExpectedDatabase("/createExpectedDatabaseResult.xml")
    public void createTest() {
        Actor newActor = new Actor(null, "Luke", "Skywalker");
        Actor expectedActor = new Actor(3, "Luke", "Skywalker");
        newActor = actorDaoJdbcTemplate.createActor(newActor);
        assertEquals(expectedActor, newActor);
    }

    @Test
    @DatabaseSetup("/initData.xml")
    public void readListTest() {
        Actor[] expected = new Actor[3];
        expected[0] = new Actor(0, "James", "Bond");
        expected[1] = new Actor(1, "Captain", "America");
        expected[2] = new Actor(2, "Charlie", "Brown");
        assertArrayEquals(expected, actorDaoJdbcTemplate.getAllActors().toArray());
    }

    @Test
    @DatabaseSetup("/initData.xml")
    public void readByIdTest() {
        Actor actualActor = actorDaoJdbcTemplate.getActorById(1);
        Actor expectedActor = new Actor(1, "Captain", "America");
        assertEquals(expectedActor, actualActor);
    }

    @Test
    @DatabaseSetup("/initData.xml")
    @ExpectedDatabase("/updateExpectedDatabaseResult.xml")
    public void updateTest() {
        Actor actor = new Actor(1, "Captain", "Underpants");
        actorDaoJdbcTemplate.updateActor(actor);
    }

    @Test
    @DatabaseSetup("/initData.xml")
    @ExpectedDatabase("/deleteExpectedDatabaseResult.xml")
    public void deleteTest() {
        actorDaoJdbcTemplate.deleteActor(new Actor(2, null, null));
    }
}
