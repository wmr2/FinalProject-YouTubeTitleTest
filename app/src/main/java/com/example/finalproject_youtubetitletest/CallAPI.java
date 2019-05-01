package com.example.finalproject_youtubetitletest;

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

/**
 * This is the code which calls the API which should be run before running the app if you want to
 * most up to date data. The reason for not implementing this inside the app is the google API
 * limits daily use and we no longer be able to call the API after repeated launches of the app.
 * Some of this code in the class is sample code provided by google on how to impliment their API.
 * This code was heavily modified the orginal sample code started with can be found here:
 * https://developers.google.com/youtube/v3/docs/videos/list
 */
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

        FileWriter fileWriter = new FileWriter("./app/src/main/assets/Data.txt");
        int[] validCategorys = {0, 1, 2, 10, 15, 17, 20, 23, 24, 26, 28};

        for (int j = 0; j < validCategorys.length; j++) {
            String nextPageToken = null;
            int totalResults = 200;
            fileWriter.write("feregeretesqws Category: ");
            fileWriter.write(Integer.toString(validCategorys[j]));
            fileWriter.write("\n\n");

            for (int i = 0; i < pageCount; i++) {
                YouTube youtubeService = getService();
                // Define and execute the API request
                YouTube.Videos.List request = youtubeService.videos()
                        .list("snippet,contentDetails,statistics");
                String out = null;

                if (nextPageToken == null) { // first page
                    VideoListResponse response = request.setKey(DEVELOPER_KEY)
                            .setChart("mostPopular")
                            .setRegionCode("US")
                            .setMaxResults(new Long(50))
                            .setVideoCategoryId(Integer.toString(validCategorys[j]))
                            .execute();
                    out = response.toString();
                } else { // other pages
                    VideoListResponse response = request.setKey(DEVELOPER_KEY)
                            .setChart("mostPopular")
                            .setPageToken(nextPageToken)
                            .setRegionCode("US")
                            .setMaxResults(new Long(50))
                            .setVideoCategoryId(Integer.toString(validCategorys[j]))
                            .execute();
                    out = response.toString();
                }

                fileWriter.write(out);
                fileWriter.write("\n");
                if (i + 1 == pageCount) { // if last pag
                    break;
                }
                String totalResultsString = out.split("totalResults")[1];
                totalResultsString = totalResultsString.split(",\"resultsPerPage\":")[0];
                totalResultsString = totalResultsString.split("},\"prevPageToken")[0];
                totalResultsString = totalResultsString.split("\":")[1];
                totalResultsString = totalResultsString.split("}}")[0];
                //System.out.println(totalResultsString);
                totalResults = Integer.parseInt(totalResultsString);
                // get total results so you go over it by mistake
                if  ((i+1)*50 > totalResults) {
                    System.out.println("less than 200 results");
                    break;
                }
                try { // try to get next page token will fail if no last page.
                    nextPageToken = out.split("nextPageToken")[1];
                    nextPageToken = nextPageToken.split(":\"")[1];
                    nextPageToken = nextPageToken.split("\",")[0];
                    System.out.println(nextPageToken);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
        fileWriter.close();
    }
}