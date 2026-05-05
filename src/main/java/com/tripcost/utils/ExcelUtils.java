package com.tripcost.utils;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    public static FileInputStream fi;
    public static FileOutputStream fo;
    public static XSSFWorkbook wb;
    public static XSSFSheet ws;
    public static XSSFRow row;
    public static XSSFCell cell;

    public static String getCellData(String xlfile, String xlsheet, int rownum, int colnum) throws IOException {
        fi = new FileInputStream(xlfile);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(xlsheet);
        row = ws.getRow(rownum);
        cell = row.getCell(colnum);
        String data = cell.toString();
        wb.close();
        fi.close();
        return data;
    }

    public static void setCellData(String xlfile, String xlsheet, int rownum, int colnum, String data) throws IOException {
        fi = new FileInputStream(xlfile);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(xlsheet);
        row = ws.getRow(rownum);
        if (row == null) {
            row = ws.createRow(rownum);
        }
        cell = row.createCell(colnum);
        cell.setCellValue(data);
        fo = new FileOutputStream(xlfile);
        wb.write(fo);
        wb.close();
        fi.close();
        fo.close();
    }

    public static Object[][] getExcelData(String xlfile, String xlsheet) throws IOException {
        fi = new FileInputStream(xlfile);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(xlsheet);

        int rowCount = ws.getLastRowNum();
        int colCount = ws.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount][colCount];

        for (int i = 1; i <= rowCount; i++) {
            row = ws.getRow(i);
            for (int j = 0; j < colCount; j++) {
                cell = row.getCell(j);
                data[i - 1][j] = (cell == null) ? "" : cell.toString();
            }
        }

        wb.close();
        fi.close();
        return data;
    }
}