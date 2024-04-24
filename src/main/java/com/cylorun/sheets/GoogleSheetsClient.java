package com.cylorun.sheets;


import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GoogleSheetsClient {

    private static final String sheetId = "";
    private static final String sheetName = "Coords";
    public static void appendRowTop(List<Object> rowData) throws IOException, GeneralSecurityException {
        insert(rowData, 3, false);
    }

    public static void insert(List<Object> rowData, int row, boolean overwrite) throws GeneralSecurityException, IOException {
        Sheets sheetsService = GoogleSheetsService.getSheetsService();
        String range = String.format("A%s:CG", row);

        if (overwrite) {
            ValueRange newRow = new ValueRange().setValues(Arrays.asList(rowData));
            UpdateValuesResponse res = sheetsService.spreadsheets().values()
                    .update(sheetId, range, newRow)
                    .setValueInputOption("RAW")
                    .execute();
            return;
        }

        ValueRange response = sheetsService.spreadsheets().values()
                .get(sheetId, sheetName + "!" + range)
                .execute();
        List<List<Object>> values = response.getValues();

        List<List<Object>> newValues = new ArrayList<>();
        newValues.add(rowData);
        if (values != null) {
            newValues.addAll(values);
        }

        ValueRange body = new ValueRange().setValues(newValues);
        UpdateValuesResponse result = sheetsService.spreadsheets().values()
                .update(sheetId, sheetName + "!" + range.split(":")[0], body)
                .setValueInputOption("RAW")
                .execute();
    }
}