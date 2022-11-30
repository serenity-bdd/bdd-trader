package net.bddtrader.testdata;

import net.bddtrader.tradingdata.services.MissingTestDataFileException;

import java.io.File;
import java.net.URISyntaxException;

public class JsonTestData {
    public static File from(String source) {
        try {
            return new File(JsonTestData.class.getResource("/sample_data/" + source).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalStateException("No test data found for " + source);
        } catch(NullPointerException npe) {
            throw new MissingTestDataFileException(source);
        }
    }
}
