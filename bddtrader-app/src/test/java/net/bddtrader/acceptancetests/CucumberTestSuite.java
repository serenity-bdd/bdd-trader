package net.bddtrader.acceptancetests;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        glue = "net.bddtrader.acceptancetests.stepdefinitions",
        features = "src/test/resources/features"
)
public class CucumberTestSuite {}
