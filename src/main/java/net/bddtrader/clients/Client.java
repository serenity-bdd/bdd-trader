package net.bddtrader.clients;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Client {

    private final Long id;
    private final String firstName;
    private final String lastName;

    @JsonCreator
    public Client(@JsonProperty("id") Long id,
                  @JsonProperty("firstName") String firstName,
                  @JsonProperty("lastName") String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public static ClientBuilder withFirstName(String firstName) {
        return new ClientBuilder(firstName);
    }

    public static class ClientBuilder {
        private final String firstName;

        public ClientBuilder(String firstName) {
            this.firstName = firstName;
        }

        public Client andLastName(String lastName) {
            return new Client(null, firstName, lastName);
        }
    }
}
