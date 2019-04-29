package com.example.finalproject_youtubetitletest;

/**
 * Sample Java code for youtube.videos.list
 * See instructions for running these code samples locally:
 * https://developers.google.com/explorer-help/guides/code_samples#java
 */
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;
import java.io.FileWriter;

public class CallAPI {
    // You need to set this value for your code to compile.
    // For example: ... DEVELOPER_KEY = "YOUR ACTUAL KEY";
    private static final String DEVELOPER_KEY = "AIzaSyAy2sCc93AJISAWiwXQtZtTfMTtm7qbSkM";

    private static final String APPLICATION_NAME = "API code samples";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    private static final int pageCount = 4;
    /**
     * Call function to create API service object. Define and
     * execute API request. Print API response.
     *
     * @throws GeneralSecurityException, IOException, GoogleJsonResponseException
     */
    public static void main(String[] args)
            throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        FileWriter fileWriter = new FileWriter("C:/Users/Will/Desktop/samplefile2.txt");
        int[] validCatagorys = {0, 1, 2, 10, 15, 17, 20, 23, 24, 26, 28};
        for (int j = 0; j < validCatagorys.length; j++) {
            String nextPageToken = null;
            int totalResults = 200;
            fileWriter.write("feregeretesqws Category: ");
            fileWriter.write(Integer.toString(validCatagorys[j]));
            fileWriter.write("\n\n");
            for (int i = 0; i < pageCount; i++) {
                YouTube youtubeService = getService();
                // Define and execute the API request
                YouTube.Videos.List request = youtubeService.videos()
                        .list("snippet,contentDetails,statistics");
                String out = null;
                if (nextPageToken == null) {
                    VideoListResponse response = request.setKey(DEVELOPER_KEY)
                            .setChart("mostPopular")
                            .setRegionCode("US")
                            .setMaxResults(new Long(50))
                            .setVideoCategoryId(Integer.toString(validCatagorys[j]))
                            .execute();
                    out = response.toString();
                } else {
                    VideoListResponse response = request.setKey(DEVELOPER_KEY)
                            .setChart("mostPopular")
                            .setPageToken(nextPageToken)
                            .setRegionCode("US")
                            .setMaxResults(new Long(50))
                            .setVideoCategoryId(Integer.toString(validCatagorys[j]))
                            .execute();
                    out = response.toString();
                }
                System.out.println(out);
                fileWriter.write(out);
                fileWriter.write("\n");
                if (i + 1 == pageCount) {
                    break;
                }
                String totalResultsString = out.split("totalResults")[1];
                totalResultsString = totalResultsString.split(",\"resultsPerPage\":")[0];
                totalResultsString = totalResultsString.split("},\"prevPageToken")[0];
                totalResultsString = totalResultsString.split("\":")[1];
                totalResultsString = totalResultsString.split("}}")[0];
                System.out.println(totalResultsString);
                totalResults = Integer.parseInt(totalResultsString);
                if  ((i+1)*50 > totalResults) {
                    System.out.println("hi 1");
                    break;
                }
                try {
                    nextPageToken = out.split("nextPageToken")[1];
                    nextPageToken = nextPageToken.split(":\"")[1];
                    nextPageToken = nextPageToken.split("\",")[0];
                    System.out.println(nextPageToken);
                } catch (Exception e) {
                    System.out.println("hi");
                    break;
                }
            }
        }
        fileWriter.close();
    }
}