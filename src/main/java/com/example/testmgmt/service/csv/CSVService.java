package com.example.testmgmt.service.csv;

import com.opencsv.exceptions.CsvException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CSVService {

    String[] getHeader(MultipartFile file) throws IOException;

    List<String[]> getRowData(MultipartFile file) throws IOException, CsvException;

    Map<Integer, String> getFieldOrderMap(List<String> fieldNames);

}
