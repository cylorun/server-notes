package com.cylorun.sheets;


import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleSheetsService {

    private static final String CREDENTIALS_FILE = "credentials.json";

    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        InputStream inputStream = GoogleSheetsService.class.getClassLoader().getResourceAsStream(CREDENTIALS_FILE);

        GoogleCredential credential = GoogleCredential
                .fromStream(inputStream)
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                credential
        ).setApplicationName("LiveTracker").build();
    }
}