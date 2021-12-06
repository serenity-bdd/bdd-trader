package net.bddtrader.news;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsItem {
    private ZonedDateTime datetime;
    private String headline;
    private String source;
    private String url;
    private String summary;
    private String related;

    @JsonCreator
    public NewsItem(
            @JsonProperty("datetime") ZonedDateTime datetime,
            @JsonProperty("headline") String headline,
            @JsonProperty("source") String source,
            @JsonProperty("url") String url,
            @JsonProperty("summary") String summary,
            @JsonProperty("related") String related) {
        this.datetime = datetime;
        this.headline = headline;
        this.source = source;
        this.url = url;
        this.summary = summary;
        this.related = related;
    }

    public ZonedDateTime getDatetime() {
        return datetime;
    }

    public String getHeadline() {
        return headline;
    }

    public String getSource() {
        return source;
    }

    public String getUrl() {
        return url;
    }

    public String getSummary() {
        return summary;
    }

    public String getRelated() {
        return related;
    }
}