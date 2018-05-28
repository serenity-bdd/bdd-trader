package net.bddtrader.acceptancetests.stepdefinitions;

import cucumber.api.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

public class SetupSteps {

    @Before({"@web"})
    public void setStage() {
        OnStage.setTheStage(new OnlineCast());
    }
}
