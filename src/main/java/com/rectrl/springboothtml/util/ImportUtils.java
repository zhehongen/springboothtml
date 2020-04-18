package com.rectrl.springboothtml.util;


import com.rectrl.springboothtml.constant.Constants;
import com.rectrl.springboothtml.exception.ServiceException;
import com.rectrl.springboothtml.result.ResultCode;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author hongen.zhang
 * time: 2019/12/1 16:49
 * email: hongen.zhang@things-matrix.com
 */
public class ImportUtils {

    // csv记录split操作
    public static final String CSV_SPLIT = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    public static List<String> excel(MultipartFile multipartFile) throws Exception {
        List<String> lines = new ArrayList<>();

        PoiUtil poiUtil = new PoiUtil(multipartFile);
        List<String> sheets = poiUtil.getSheets();
        int lineNum = poiUtil.getSheetLastRowNum(sheets.get(0));
        if (lineNum == 0 || lineNum > 5001) {
            throw new ServiceException(0 == lineNum ? ResultCode.FILE_DATA_EMPTY : ResultCode.FILE_IMPORT_LIMIT);
        } else {
            List<Row> rows = poiUtil.readExcel(sheets.get(0));
            int rowLength = rows.get(0).getLastCellNum();
            for (Row row : rows) {
                List<Object> list = new ArrayList<>();
                for (int j = 0; j < rowLength; j++) {
                    Object value = PoiUtil.getCellValue(row.getCell(j));
                    list.add(value);
                }
                lines.add(list.stream().map(Object::toString).collect(Collectors.joining(Constants.DELIMITER)));
            }
        }

        return lines;
    }

    public static List<String> csv(MultipartFile multipartFile) throws Exception {
        List<String> lines = new ArrayList<>();

        InputStream is = multipartFile.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) > -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        byteArrayOutputStream.flush();

        InputStream lineNumStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        LineNumberReader lnr = new LineNumberReader(new InputStreamReader(lineNumStream));
        lnr.skip(Long.MAX_VALUE);
        int lineNum = lnr.getLineNumber() + 1;
        if (lineNum == 0 || lineNum > 5001) {
            throw new ServiceException(0 == lineNum ? ResultCode.FILE_DATA_EMPTY : ResultCode.FILE_IMPORT_LIMIT);
        } else {
            InputStream dataStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(dataStream)));
            lines = reader.lines().collect(Collectors.toList());
            dataStream.close();
        }
        lineNumStream.close();
        byteArrayOutputStream.close();
        is.close();

        return lines;
    }

    public static String[] csvAnalysis(String line, int limit) {
        return Arrays.stream(line.split(CSV_SPLIT, limit)).map(s -> {
            s = s.trim();
            if (s.startsWith("\"") && s.endsWith("\"")) {
                s = s.trim().substring(1, s.length() - 1);
            }
            if (s.contains("\"\"")) {
                s = s.replaceAll("\"\"", "\"");
            }
            return s;
        }).toArray(String[]::new);
    }
}
