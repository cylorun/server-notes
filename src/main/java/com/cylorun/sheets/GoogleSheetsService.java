package com.cylorun.sheets;


import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleSheetsService {
    private static final String CREDENTIALS_FILE = Paths.get("credentials.json").toString();


    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        GoogleCredential credential = GoogleCredential
                .fromStream(new FileInputStream(CREDENTIALS_FILE))
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                credential
        ).setApplicationName("LiveTracker").build();
    }
}