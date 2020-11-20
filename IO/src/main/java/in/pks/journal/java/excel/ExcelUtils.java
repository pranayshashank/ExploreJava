package in.pks.journal.java.excel;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelUtils {

    public static void main(String[] args) throws OpenXML4JException, ParserConfigurationException, SAXException, org.apache.poi.openxml4j.exceptions.OpenXML4JException, IOException {
        ExcelUtils.readFile("/Users/pranay/Desktop/Test.xlsx");
    }

    private static void readFile(String fileName)
            throws IOException, OpenXML4JException, ParserConfigurationException, SAXException, org.apache.poi.openxml4j.exceptions.OpenXML4JException {
        File file = new File(fileName);
        OPCPackage opcPackage = OPCPackage.open(file);
        ParseExcelFile parsedFile = new ParseExcelFile(opcPackage,10,null);
        boolean isValid = parsedFile.process();
        System.out.println("File is valid ? "+isValid);
    }

    private static void readFile2(String fileName) throws IOException {
        FileInputStream excelInputStream = new FileInputStream(new File(fileName));
        Workbook workbook = new XSSFWorkbook(excelInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowItr = sheet.iterator();
        int rowNum = 0;
        while (rowItr.hasNext()) {
            Row row = rowItr.next();
            Iterator <Cell> cellItr = row.iterator();
            System.out.print(rowNum + ". ");
            while (cellItr.hasNext()) {
                Cell cell = cellItr.next();
                if (cell.getCellTypeEnum() == CellType.STRING) {
                    System.out.print(cell.getStringCellValue() + "\t");
                } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                    System.out.print(cell.getNumericCellValue() + "\t");
                }
            }
            System.out.println();
            rowNum++;
        }
        workbook.close();
        excelInputStream.close();
    }
}
