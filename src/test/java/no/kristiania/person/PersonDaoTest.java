package no.kristiania.person;

import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonDaoTest {
    @Test
    void shouldRetrieveSavedPerson() throws SQLException {
        PersonDao dao = new PersonDao(createDataSource()); //connecter til databasen

        Person person = randomPerson(); //lager en tilfeldig person
        dao.save(person); //lagrer den personen i databasen

        //hente ut personen.
        assertThat(dao.retrieve(person.getId()))
                .hasNoNullFieldsOrProperties() //henter ut data fra db, testdatene jeg har, skal ha feltene sine satt
                .usingRecursiveComparison() //hvis du bruker reksusivt comparison, så skal de være like
                .isEqualTo(person); //skal være lik den personen jeg la inn
    }

    private DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource(); //bruker en datasource
        dataSource.setUrl("jdbc:postgresql://localhost:5432/person_db");
        dataSource.setUser("person_dbuser");
        dataSource.setPassword(",a2{k^8!+HX/;,9cF6");
        return dataSource;
    }

    private Person randomPerson() { //opprette en random person
        Person person = new Person();
        person.setFirstName(pickOne("Johannes", "Jane", "Jamal", "joe", "josefine"));
        person.setLastName(pickOne("Brodal", "Janesen", "Jamalsen", "joesson", "josefinesen"));
        return person;
    }

    private String pickOne(String... alternatives) {
        return alternatives[new Random().nextInt(alternatives.length)];
    }
}
