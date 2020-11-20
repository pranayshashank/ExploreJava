package in.pks.journal.java.excel;

import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

class XSSFSheetEventHandler extends DefaultHandler {
    private StylesTable stylesTable;
    private ReadOnlySharedStringsTable sharedStringsTable;
    private List<?> list = new ArrayList();
    private SheetModel sheetModel;
    private final int minColumnCount;
    private boolean vIsOpen;
    private String sqref;
    private XSSFDataTypes nextDataType;
    private short formatIndex;
    private String formatString;
    private final DataFormatter formatter;
    private int lastColumnNumber = -1;
    private StringBuffer value;
    private int dataType;
    public XSSFSheetEventHandler(StylesTable styles,
                                 ReadOnlySharedStringsTable strings, int cols, SheetModel sheetModel) {
        this.stylesTable = styles;
        this.sharedStringsTable = strings;
        this.minColumnCount = cols;
        this.value = new StringBuffer();
        this.nextDataType = XSSFDataTypes.NUMBER;
        this.formatter = new DataFormatter();
        this.sheetModel = sheetModel;
    }
    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {
        if ("inlineStr".equals(name) || "v".equals(name)) {
            vIsOpen = true;
            value.setLength(0);
        }
        else if ("c".equals(name)) {
            String r = attributes.getValue("r");
            this.nextDataType = XSSFDataTypes.NUMBER;
            this.formatIndex = -1;
            this.formatString = null;
            String cellType = attributes.getValue("t");
            String cellStyleStr = attributes.getValue("s");
            if ("b".equals(cellType))
                nextDataType = XSSFDataTypes.BOOL;
            else if ("e".equals(cellType))
                nextDataType = XSSFDataTypes.ERROR;
            else if ("inlineStr".equals(cellType))
                nextDataType = XSSFDataTypes.INLINESTR;
            else if ("s".equals(cellType))
                nextDataType = XSSFDataTypes.SSTINDEX;
            else if ("str".equals(cellType))
                nextDataType = XSSFDataTypes.FORMULA;
            else if (cellStyleStr != null) {
                int styleIndex = Integer.parseInt(cellStyleStr);
                XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
                this.formatIndex = style.getDataFormat();
                this.formatString = style.getDataFormatString();
                if (this.formatString == null)
                    this.formatString = BuiltinFormats
                            .getBuiltinFormat(this.formatIndex);
            }
        }else if ("dataValidation".equals(name)){
            String dvXmlType = attributes.getValue("type");
            sqref = attributes.getValue("sqref");
            if(dvXmlType.equals("list")){
                dataType = DataValidationConstraint.ValidationType.LIST;
                System.out.println("List Type data ");
            }
            System.out.println("At data validation start");
        }else if(name.startsWith("formula")){
            vIsOpen = true;
            value = new StringBuffer();
            System.out.println("At Formula start");
        }
    }
    public void endElement(String uri, String localName, String name)
            throws SAXException {
        String thisStr = null;
        if (vIsOpen) {
            if ("v".equals(name) || "is".equals(name)) {
                vIsOpen = false;
            } else if (name.startsWith("formula")) {
                char last = name.charAt(name.length() - 1);
                switch (last) {
                    case '1':
                        System.out.println(""+value.toString());
                        break;
                    case '2':
                        System.out.println(value.toString());
                        break;
                }
                vIsOpen = false;
                value = null;
            }
        }
        if ("v".equals(name)) {
            System.out.println("    "+value);
            switch (nextDataType) {
                case BOOL:
                    char first = value.charAt(0);
                    thisStr = first == '0' ? "FALSE" : "TRUE";
                    break;
                case ERROR:
                    thisStr = "\"ERROR:" + value.toString() + '"';
                    break;
                case FORMULA:
                    thisStr = '"' + value.toString() + '"';
                    break;
                case INLINESTR:
                    XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
                    thisStr = '"' + rtsi.toString() + '"';
                    break;
                case SSTINDEX:
                    String sstIndex = value.toString();
                    try {
                        int idx = Integer.parseInt(sstIndex);
                        XSSFRichTextString rtss = new XSSFRichTextString(
                                sharedStringsTable.getEntryAt(idx));
                        thisStr = '"' + rtss.toString() + '"';
                    } catch (NumberFormatException ex) {
                    }
                    break;
                case NUMBER:
                    String n = value.toString();
                    if (this.formatString != null)
                        thisStr = formatter.formatRawCellContents(Double
                                        .parseDouble(n), this.formatIndex,
                                this.formatString);
                    else
                        thisStr = n;
                    break;
                default:
                    thisStr = "(TODO: Unexpected type: " + nextDataType + ")";
                    break;
            }
            if (lastColumnNumber == -1) {
                lastColumnNumber = 0;
            }
        } else if ("row".equals(name)) {
            sheetModel.setNumberOfRows(sheetModel.getNumberOfRows()+1);
            System.out.println();
        }
        else if ("dataValidation".equals(name)) {
            System.out.println("At datavalidation end");
        }
    }
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if (vIsOpen)
            value.append(ch, start, length);
    }
}