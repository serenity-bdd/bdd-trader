package net.bddtrader.news;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsItem {
    private ZonedDateTime datetime;
    private String headline;
    private String source;
    private String url;
    private String summary;
    private String related;

    public NewsItem() {}

    public NewsItem(ZonedDateTime datetime, String headline, String source, String url, String summary, String related) {
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

    @Override
    public String toString() {
        return "NewsItem{" +
                "datetime=" + datetime +
                ", headline='" + headline + '\'' +
                ", source='" + source + '\'' +
                ", url='" + url + '\'' +
                ", summary='" + summary + '\'' +
                ", related=" + related +
                '}';
    }
}