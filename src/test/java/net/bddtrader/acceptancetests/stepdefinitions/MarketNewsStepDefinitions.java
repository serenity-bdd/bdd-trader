package net.bddtrader.acceptancetests.stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.bddtrader.acceptancetests.tasks.ViewNewsAbout;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import java.util.List;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class MarketNewsStepDefinitions {

    Actor theTrader;

    @Given("^(.*) (?:is interested in|wants to know about) (.*)")
    public void interested_in_a_topic(String traderName, String topic) throws Exception {
        theTrader = Actor.named(traderName).whoCan(CallAnApi.at("http://localhost:8080"));
    }


    @When("^(.*) views the news about (.*)")
    public void view_the_news_about(String traderName, String share) throws Exception {
        theTrader.attemptsTo(
                ViewNewsAbout.theShare(share)
        );
    }

    @Then("^(.*) should only see articles related to (.*)")
    public void should_only_see_articles_related_to(String traderName, String share) throws Exception {

        List<String> relatedTopics = lastResponse().jsonPath().getList("related");

        assertThat(relatedTopics).allMatch(
                relatedTopic -> relatedTopic.contains(share)
        );
    }

}
