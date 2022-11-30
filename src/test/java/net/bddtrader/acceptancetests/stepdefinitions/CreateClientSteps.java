package net.bddtrader.acceptancetests.stepdefinitions;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.bddtrader.model.YesNo;
import net.serenitybdd.rest.SerenityRest;

import java.util.List;
import java.util.Map;

public class CreateClientSteps {
    @Given("no client exists for with email of {string}")
    public void no_client_exists_for_with_email_of(String email) {
        // Don't care about this just yet
    }

    @When("a new client is created with the following details:")
    public void a_new_client_is_created_with(List<Map<String,String>> newClients) {
        newClients.forEach(
                newClientData -> {
                    SerenityRest.given()
                            .body(newClientData)
                            .contentType(ContentType.JSON)
                            .accept("*/*")
                            .post("http://localhost:9000/api/client");
                }
        );
    }

    @ParameterType("Yes|No")
    public Boolean yesNo(String value) {
        return "yes".equalsIgnoreCase(value);
    }

    @Then("the client should be created or not: {yesNo}")
    public void the_client_should_be_created_or_not(boolean created) {
        int expectedCode = created ? 200: 412;
        SerenityRest.lastResponse().then().statusCode(expectedCode);
    }
}
