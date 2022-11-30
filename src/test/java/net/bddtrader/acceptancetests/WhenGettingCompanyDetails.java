package net.bddtrader.acceptancetests;

import io.restassured.RestAssured;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class WhenGettingCompanyDetails {
    @Test
    public void should_return_name_and_sector() {
        RestAssured.get("http://localhost:9000/api/stock/aapl/company")
                .then()
                .body("companyName", equalTo("Apple, Inc."))
                .body("sector", equalTo("Electronic Technology"));
    }
}
