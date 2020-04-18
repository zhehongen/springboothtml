package com.rectrl.springboothtml.service.impl;

import com.rectrl.springboothtml.service.BugService;
import javafx.scene.control.Cell;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

/**
 * @author hongen.zhang
 * time: 2020/4/18 12:14
 * email: hongen.zhang@things-matrix.com
 */
@Service
@Slf4j
public class BugServiceImpl implements BugService {

    @Override
    public String uploadFile(InputStream input) {
        return null;
    }
}
