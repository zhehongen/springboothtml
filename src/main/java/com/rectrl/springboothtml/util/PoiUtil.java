package com.rectrl.springboothtml.util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author hongen.zhang
 * time: 2019/12/1 16:49
 * email: hongen.zhang@things-matrix.com
 */
@SuppressWarnings("DuplicatedCode")
public class PoiUtil {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // Excel文件路径
    private String excelPath;

    // MultipartFile
    private MultipartFile multipartFile;

    // 设定开始读取的位置，默认为0
    private int startReadPos = 0;

    // 设定结束读取的位置，默认为0，用负数来表示倒数第n行
    private int endReadPos = 0;

    private Workbook wb;

    public PoiUtil() {

    }

    public PoiUtil(String excelPath) {
        this.excelPath = excelPath;
    }

    public PoiUtil(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public Workbook getWorkbook() throws IOException {
        if (null != this.wb) {
            return this.wb;
        }

        // 文件io
        InputStream is;
        // 文件扩展名
        String extension;

        if (null != this.multipartFile && !this.multipartFile.isEmpty()) {
            extension = this.multipartFile.getOriginalFilename().substring(this.multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
            is = this.multipartFile.getInputStream();
        } else {
            extension = this.excelPath.substring(this.excelPath.lastIndexOf(".") + 1);
            File file = new File(this.excelPath);
            is = new FileInputStream(file);
        }

        if ("xls".equals(extension)) { //使用xls方式读取
            wb = new HSSFWorkbook(is);
        } else if ("xlsx".equals(extension)) { //使用xlsx方式读取
            wb = new XSSFWorkbook(is);
        }

        return wb;
    }

    public List<String> getSheets() throws IOException {
        List<String> list = new ArrayList<>();
        Workbook wb = this.getWorkbook();
        int i = wb.getNumberOfSheets();
        for (int j = 0; j < i; j++) {
            list.add(wb.getSheetName(j));
        }

        return list;
    }

    /**
     * 自动根据文件扩展名，调用对应的读取方法
     *
     * @throws IOException
     * @Title: writeExcel
     * @Date : 2014-9-11 下午01:50:38
     */
    public List<Row> readExcel() throws IOException {
        Workbook wb = this.getWorkbook();
        List<Row> rowList = new ArrayList<>();
        Sheet sheet = wb.getSheetAt(0);

        //获取最后行号  
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum < 1) {
            return rowList;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("[PoiUtil]reading begin, fileName: {}", sheet.getSheetName());
        }

        Row row = null;
        int start = startReadPos;
        int end;
        if (endReadPos == 0) {
            end = lastRowNum;
        } else {
            end = endReadPos;
        }
        for (int i = start; i <= end; i++) {
            row = sheet.getRow(i);
            if (row != null) {
                rowList.add(row);
            }
        }

        return rowList;
    }

    public List<Row> readExcel(int sheetIdx) throws IOException {
        Workbook wb = this.getWorkbook();
        List<Row> rowList = new ArrayList<>();
        Sheet sheet = wb.getSheetAt(sheetIdx);

        //获取最后行号  
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum < 1) {
            return rowList;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("[PoiUtil]reading begin, fileName: {}", sheet.getSheetName());
        }

        Row row = null;
        int start = startReadPos;
        int end;
        if (endReadPos == 0) {
            end = lastRowNum;
        } else {
            end = endReadPos;
        }
        for (int i = start; i <= end; i++) {
            row = sheet.getRow(i);
            if (row != null) {
                rowList.add(row);
            }
        }

        return rowList;
    }

    public List<Row> readExcel(String sheetName) throws IOException {
        Workbook wb = this.getWorkbook();
        List<Row> rowList = new ArrayList<>();
        Sheet sheet = wb.getSheet(sheetName);

        //获取最后行号  
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum < 1) {
            return rowList;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("[PoiUtil]reading begin, fileName: {}", sheet.getSheetName());
        }

        Row row = null;
        int start = startReadPos;
        int end;
        if (endReadPos == 0) {
            end = lastRowNum;
        } else {
            end = endReadPos;
        }
        for (int i = start; i <= end; i++) {
            row = sheet.getRow(i);
            if (row != null) {
                rowList.add(row);
            }
        }

        return rowList;
    }

    /**
     * 获取当前sheet的总行数
     *
     * @param sheetName
     * @return
     * @throws IOException
     */
    public int getSheetLastRowNum(String sheetName) throws IOException {
        return this.getWorkbook().getSheet(sheetName).getLastRowNum();
    }

    /*** 
     * 读取单元格的值 
     *
     * @Title: getCellValue
     * @Date : 2014-9-11 上午10:52:07 
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellTypeEnum()) {
                case STRING:
                    result = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    boolean cellDateFormatted = HSSFDateUtil.isCellDateFormatted(cell);
                    if (cellDateFormatted) {
                        Date dateCellValue = cell.getDateCellValue();
                        result = DateUtils.format(dateCellValue, DateUtils.DATE_PATTERN);
                    } else {
                        long longVal = Math.round(cell.getNumericCellValue());
                        double doubleVal = cell.getNumericCellValue();
                        if (Double.parseDouble(longVal + ".0") == doubleVal) {
                            DecimalFormat df = new DecimalFormat("0");
                            result = df.format(cell.getNumericCellValue());
                        } else {
                            result = doubleToBigDecimal(cell.getNumericCellValue()).toPlainString();
                        }
                    }
                    break;
                case BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case FORMULA:
                    result = cell.getCellFormula();
                    break;
                case ERROR:
                    result = cell.getErrorCellValue();
                    break;
                default:
                    break;
            }
        }
        return result.toString();
    }

    private static BigDecimal doubleToBigDecimal(double d) {
        String doubleStr = String.valueOf(d);
        if (doubleStr.contains(".")) {
            int pointLen = doubleStr.replaceAll("\\d+\\.", "").length();    // 取得小数点后的数字的位数
            pointLen = Math.min(pointLen, 16);    // double最大有效小数点后的位数为16
            double pow = Math.pow(10, pointLen);
            long tmp = (long) (d * pow);
            return new BigDecimal(tmp).divide(new BigDecimal(pow));
        }

        return new BigDecimal(d);
    }
}
