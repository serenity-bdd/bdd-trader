package net.bddtrader.acceptancetests;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        format = {"json:target/registering_a_new_client.json"},
        features = "src/test/resources/features/clients/registering_a_new_client.feature"
)
public class RegisteringAClient {
}
