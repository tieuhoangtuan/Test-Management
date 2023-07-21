package com.example.testmgmt.service.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
public class CSVServiceImpl implements CSVService {

    @Override
    public String[] getHeader(MultipartFile file) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        // read first line of csv file
        String headerLine = reader.readLine();

        String[] headers = StringUtils.split(headerLine, ",");

        reader.close();

        return headers;
    }

    @Override
    public List<String[]> getRowData(MultipartFile file) throws IOException, CsvException {

        // create a CSV reader object
        Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        // skip header row
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();

        List<String[]> rowsData = csvReader.readAll();

        return rowsData;
    }

    @Override
    public Map<Integer, String> getFieldOrderMap(List<String> fieldNames) {

        Map<Integer, String> fieldOrderMap = new LinkedHashMap<>();

        for (int i=0; i < fieldNames.size(); i++) {
            String fieldName = fieldNames.get(i);

            switch (fieldName) {
                case "title" -> fieldOrderMap.put(i + 1, "title");
                case "step" -> fieldOrderMap.put(i + 1, "step");
                case "expectedResult" -> fieldOrderMap.put(i + 1, "expectedResult");
                case "type" -> fieldOrderMap.put(i + 1, "type");
                case "priority" -> fieldOrderMap.put(i + 1, "priority");
                case "estimate" -> fieldOrderMap.put(i + 1, "estimate");
                case "precondition" -> fieldOrderMap.put(i + 1, "precondition");
            }
        }

        return fieldOrderMap;
    }


}
