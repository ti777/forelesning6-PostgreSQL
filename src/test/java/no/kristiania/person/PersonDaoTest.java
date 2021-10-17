package no.kristiania.person;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonDaoTest {
    @Test
    void shouldRetrieveSavedPerson() {
        HelloDatabase dao = new HelloDatabase(); //connecter til databasen

        Person person = randomPerson(); //lager en tilfeldig person
        dao.save(person); //lagrer den personen i databasen

        //hente ut personen.
        assertThat(dao.retrieve(person.getId()))
                .isEqualTo(person); //skal være lik den personen jeg la inn
    }

    private Person randomPerson() { //opprette en random person
        Person person = new Person();
        person.setFirstName(pickOne("Johannes", "Jane", "Jamal", "joe", "josefine"));
        return person;
    }

    private String pickOne(String... alternatives) {
        return alternatives[new Random().nextInt(alternatives.length)];
    }
}
