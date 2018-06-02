package net.bddtrader.acceptancetests.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class ViewNewsAbout {
    public static Performable theShare(String stockid) {
        return Task.where("{0} gets news about #share",
                Get.resource("/api/stock/{stockid}/news").with( request -> request.pathParam("stockid", stockid))
        ).with("share").of(stockid);
    }
}
