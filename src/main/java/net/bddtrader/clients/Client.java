package net.bddtrader.clients;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Client {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;

    @JsonCreator
    public Client(@JsonProperty("id") Long id,
                  @JsonProperty("firstName") String firstName,
                  @JsonProperty("lastName") String lastName,
                  @JsonProperty("email") String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public static AndLastName withFirstName(String firstName) {
        return new ClientBuilder(firstName);
    }

    public interface AndLastName { AndEmail andLastName(String lastName); }
    public interface AndEmail { Client andEmail(String email); }


    public static class ClientBuilder implements AndLastName, AndEmail {
        private final String firstName;
        private String lastName;

        public ClientBuilder(String firstName) {
            this.firstName = firstName;
        }

        public AndEmail andLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Client andEmail(String email) {
            return new Client(null, firstName, lastName, email);
        }
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
