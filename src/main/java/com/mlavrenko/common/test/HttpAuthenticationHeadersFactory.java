package com.mlavrenko.common.test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Headers;
import java.util.Arrays;
import java.util.Locale;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public final class HttpAuthenticationHeadersFactory {

    private static final String FORMAT_PATTERN = "E, d MMM y HH:m:s ZZZ";

    private HttpAuthenticationHeadersFactory() {
    }

    public static Headers createHeaders() {
        DateTime requestTime = new DateTime().withZone(DateTimeZone.UTC).toDateTime();
        String dateString = DateTimeFormat.forPattern(FORMAT_PATTERN).withLocale(Locale.ENGLISH).print(requestTime);
        return new Headers(
                Arrays.asList(
                        new Header("Date", dateString),
                        new Header(HttpHeaders.CONTENT_TYPE, ContentType.JSON.toString()),
                        new Header(HttpHeaders.CONTENT_ENCODING, "gzip"),
                        new Header(HttpHeaders.ACCEPT, MediaType.ALL.getType())
                )
        );
    }
}
