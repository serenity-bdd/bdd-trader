package net.bddtrader.acceptancetests;

import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.bddtrader.portfolios.Position;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        format = {"json:target/cucumber-report-composite.json"},
        features = "src/test/resources/features/portfolios/buying_and_selling_shares.feature"
)
public class BuyingAndSellingShares {}
