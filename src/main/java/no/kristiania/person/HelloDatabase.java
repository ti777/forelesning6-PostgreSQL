package no.kristiania.person;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloDatabase {
    public static void main(String[] args) throws SQLException {

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
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

    public void save(Person person) {
    }

    public Person retrieve(Long id) {
        return null;
    }
}