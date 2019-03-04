//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.util;

import com.ruisitech.bi.entity.etl.ImpConfigDto;
import com.ruisitech.bi.service.etl.DataLoaderService;
import com.ruisitech.bi.util.XSSFSheetXMLHandler.SheetContentsHandler;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFReader.SheetIterator;
import org.apache.poi.xssf.model.CommentsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class XLSX2CSV {
    private final OPCPackage xlsxPackage;
    private final int minColumns;
    private ImpConfigDto dto;
    private List<Object> datas;
    private boolean controlMapOrder = false;
    private Connection targetConn;
    private PreparedStatement exeps;
    private String sql;
    private DataLoaderService service;

    public XLSX2CSV(OPCPackage pkg, ImpConfigDto dto, Connection targetConn, PreparedStatement exeps, String sql, DataLoaderService service) {
        this.targetConn = targetConn;
        this.exeps = exeps;
        this.xlsxPackage = pkg;
        this.dto = dto;
        this.minColumns = -1;
        this.service = service;
        this.sql = sql;
    }

    public XLSX2CSV(OPCPackage pkg, ImpConfigDto dto, List<Object> datas) {
        this.xlsxPackage = pkg;
        this.dto = dto;
        this.datas = datas;
        this.minColumns = -1;
    }

    public void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, SheetContentsHandler sheetHandler, InputStream sheetInputStream, int max) throws IOException, SAXException {
        InputSource sheetSource = new InputSource(sheetInputStream);

        try {
            XMLReader sheetParser = SAXHelper.newXMLReader();
            XSSFSheetXMLHandler handler = new XSSFSheetXMLHandler(styles, (CommentsTable)null, strings, sheetHandler, false, max);
            sheetParser.setContentHandler(handler);
            sheetParser.parse(sheetSource);
        } catch (ParserConfigurationException var9) {
            throw new RuntimeException("SAX parser appears to be broken - " + var9.getMessage());
        }
    }

    public void process(int max) throws IOException, OpenXML4JException, SAXException {
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
        XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
        StylesTable styles = xssfReader.getStylesTable();
        SheetIterator iter = (SheetIterator)xssfReader.getSheetsData();

        for(int index = 0; iter.hasNext(); ++index) {
            InputStream stream = iter.next();
            if (this.dto.getSheetIndex() - 1 == index) {
                XLSX2CSV.SheetToCSV stc = new XLSX2CSV.SheetToCSV();
                this.processSheet(styles, strings, stc, stream, max);
                if (this.targetConn != null && this.exeps != null) {
                    this.service.copydate(this.dto, (ResultSet)null, stc.dt, this.sql, this.targetConn, this.exeps);
                }
                break;
            }
        }

    }

    public void setControlMapOrder(boolean controlMapOrder) {
        this.controlMapOrder = controlMapOrder;
    }

    private class SheetToCSV implements SheetContentsHandler {
        private boolean firstCellOfRow;
        private int currentRow;
        private int currentCol;
        private Map<String, Object> dt;

        private SheetToCSV() {
            this.currentRow = -1;
            this.currentCol = -1;
        }

        public void startRow(int rowNum) {
            this.firstCellOfRow = true;
            this.currentRow = rowNum;
            this.currentCol = -1;
        }

        public void endRow(int rowNum) {
            for(int i = this.currentCol; i < XLSX2CSV.this.minColumns; ++i) {
            }

        }

        public void cell(String cellReference, Object value, String vtp) {
            if (XLSX2CSV.this.service == null || !XLSX2CSV.this.service.isStop()) {
                if (this.firstCellOfRow) {
                    this.firstCellOfRow = false;
                    if (XLSX2CSV.this.targetConn != null && XLSX2CSV.this.exeps != null && (!"y".equals(XLSX2CSV.this.dto.getNameinhead()) || this.currentRow != 1)) {
                        XLSX2CSV.this.service.copydate(XLSX2CSV.this.dto, (ResultSet)null, this.dt, XLSX2CSV.this.sql, XLSX2CSV.this.targetConn, XLSX2CSV.this.exeps);
                        if (this.currentRow != 0 && this.currentRow % 100 == 0) {
                            try {
                                XLSX2CSV.this.exeps.executeBatch();
                                XLSX2CSV.this.targetConn.commit();
                            } catch (SQLException var7) {
                                var7.printStackTrace();
                            }
                        }
                    }

                    this.dt = (Map)(XLSX2CSV.this.controlMapOrder ? new LinkedHashMap() : new HashMap());
                    if (XLSX2CSV.this.datas != null) {
                        XLSX2CSV.this.datas.add(this.dt);
                    }
                }

                if (cellReference == null) {
                    cellReference = (new CellAddress(this.currentRow, this.currentCol)).formatAsString();
                }

                int thisCol = (new CellReference(cellReference)).getCol();
                int missedCols = thisCol - this.currentCol - 1;

                for(int i = 0; i < missedCols; ++i) {
                }

                this.currentCol = thisCol;
                this.dt.put(String.valueOf(this.currentCol), value);
            }
        }

        public void headerFooter(String text, boolean isHeader, String tagName) {
        }
    }
}
