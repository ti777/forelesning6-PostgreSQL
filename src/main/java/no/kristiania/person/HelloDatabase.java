package no.kristiania.person;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloDatabase {
    private Person person;
    private DataSource dataSource;

    public HelloDatabase(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args) throws SQLException {

        PGSimpleDataSource dataSource = new PGSimpleDataSource(); //bruker en datasource
        dataSource.setUrl("jdbc:postgresql://localhost:5432/person_db");
        dataSource.setUser("person_dbuser");
        dataSource.setPassword(",a2{k^8!+HX/;,9cF6");

        Connection connection = dataSource.getConnection(); //trenger en connection. Det er forbindelsen til databasen. Ligger i database sql pakken

        //må lukke ressursene som du bruker med try, eller .close
        //har en statement for å utføre kommando fra database
        try (PreparedStatement statement = connection.prepareStatement("select*from people")) {

            //lese resultatet fra databasen
            try (ResultSet rs = statement.executeQuery()) { //
                while (rs.next()){
                    System.out.println(rs.getString("first_name"));
                }
            }
        }


    }

    public void save(Person person) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("insert into people(first_name) values (?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, person.getFirstName());
                statement.executeUpdate();

                //etter å ha insertet ting inni databasen --> be db om å få genererte nøkler
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                person.setId(rs.getLong("id"));//får ut id som en kolonne

            }
        }
        this.person = person;
    }

    public Person retrieve(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select first_name from people where id = ?")) {
                statement.setLong(1, id); //vet ikke ID til personen hvis vi putter en person i databasen
                ResultSet rs = statement.executeQuery();

                if (rs.next()) { //hvis jeg fant en rad, så kan jeg returnere en person i den raden
                    Person.person = new Person();
                    person.setFirstName(rs.getString("first_name")); //denne personen skal ha satt firstName til first_name
                    return person;

                }
            }
        }
        return person;
    }
}
