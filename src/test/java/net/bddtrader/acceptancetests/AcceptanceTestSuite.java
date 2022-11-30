package net.bddtrader.acceptancetests;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        glue = "net.bddtrader.acceptancetests",
        features = "classpath:features",
        plugin = {"pretty"}
)
public class AcceptanceTestSuite {
}
