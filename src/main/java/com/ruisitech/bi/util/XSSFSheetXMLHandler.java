//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.util;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.model.CommentsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTComment;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XSSFSheetXMLHandler extends DefaultHandler {
    private static final POILogger logger = POILogFactory.getLogger(XSSFSheetXMLHandler.class);
    private StylesTable stylesTable;
    private CommentsTable commentsTable;
    private ReadOnlySharedStringsTable sharedStringsTable;
    private final XSSFSheetXMLHandler.SheetContentsHandler output;
    private boolean vIsOpen;
    private boolean fIsOpen;
    private boolean isIsOpen;
    private boolean hfIsOpen;
    private XSSFSheetXMLHandler.xssfDataType nextDataType;
    private short formatIndex;
    private String formatString;
    private int rowNum;
    private int nextRowNum;
    private String cellRef;
    private boolean formulasNotResults;
    private StringBuffer value = new StringBuffer();
    private StringBuffer formula = new StringBuffer();
    private StringBuffer headerFooter = new StringBuffer();
    private Queue<CellAddress> commentCellRefs;
    private int maxCnt;

    public XSSFSheetXMLHandler(StylesTable styles, CommentsTable comments, ReadOnlySharedStringsTable strings, XSSFSheetXMLHandler.SheetContentsHandler sheetContentsHandler, boolean formulasNotResults, int max) {
        this.stylesTable = styles;
        this.commentsTable = comments;
        this.sharedStringsTable = strings;
        this.output = sheetContentsHandler;
        this.formulasNotResults = formulasNotResults;
        this.nextDataType = XSSFSheetXMLHandler.xssfDataType.NUMBER;
        this.maxCnt = max;
        this.init();
    }

    private void init() {
        if (this.commentsTable != null) {
            this.commentCellRefs = new LinkedList();
            CTComment[] var1 = this.commentsTable.getCTComments().getCommentList().getCommentArray();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                CTComment comment = var1[var3];
                this.commentCellRefs.add(new CellAddress(comment.getRef()));
            }
        }

    }

    private boolean isTextTag(String name) {
        if ("v".equals(name)) {
            return true;
        } else if ("inlineStr".equals(name)) {
            return true;
        } else {
            return "t".equals(name) && this.isIsOpen;
        }
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (this.maxCnt == -1 || this.rowNum < this.maxCnt && this.nextRowNum < this.maxCnt) {
            if (uri == null || uri.equals("http://schemas.openxmlformats.org/spreadsheetml/2006/main")) {
                if (this.isTextTag(localName)) {
                    this.vIsOpen = true;
                    this.value.setLength(0);
                } else if ("is".equals(localName)) {
                    this.isIsOpen = true;
                } else {
                    String cellType;
                    String cellStyleStr;
                    if ("f".equals(localName)) {
                        this.formula.setLength(0);
                        if (this.nextDataType == XSSFSheetXMLHandler.xssfDataType.NUMBER) {
                            this.nextDataType = XSSFSheetXMLHandler.xssfDataType.FORMULA;
                        }

                        cellType = attributes.getValue("t");
                        if (cellType != null && cellType.equals("shared")) {
                            cellStyleStr = attributes.getValue("ref");
                            String si = attributes.getValue("si");
                            if (cellStyleStr != null) {
                                this.fIsOpen = true;
                            } else if (this.formulasNotResults) {
                                logger.log(5, new Object[]{"shared formulas not yet supported!"});
                            }
                        } else {
                            this.fIsOpen = true;
                        }
                    } else if (!"oddHeader".equals(localName) && !"evenHeader".equals(localName) && !"firstHeader".equals(localName) && !"firstFooter".equals(localName) && !"oddFooter".equals(localName) && !"evenFooter".equals(localName)) {
                        if ("row".equals(localName)) {
                            cellType = attributes.getValue("r");
                            if (cellType != null) {
                                this.rowNum = Integer.parseInt(cellType) - 1;
                            } else {
                                this.rowNum = this.nextRowNum;
                            }

                            this.output.startRow(this.rowNum);
                        } else if ("c".equals(localName)) {
                            this.nextDataType = XSSFSheetXMLHandler.xssfDataType.NUMBER;
                            this.cellRef = attributes.getValue("r");
                            cellType = attributes.getValue("t");
                            cellStyleStr = attributes.getValue("s");
                            if ("b".equals(cellType)) {
                                this.nextDataType = XSSFSheetXMLHandler.xssfDataType.BOOLEAN;
                            } else if ("e".equals(cellType)) {
                                this.nextDataType = XSSFSheetXMLHandler.xssfDataType.ERROR;
                            } else if ("inlineStr".equals(cellType)) {
                                this.nextDataType = XSSFSheetXMLHandler.xssfDataType.INLINE_STRING;
                            } else if ("s".equals(cellType)) {
                                this.nextDataType = XSSFSheetXMLHandler.xssfDataType.SST_STRING;
                            } else if ("str".equals(cellType)) {
                                this.nextDataType = XSSFSheetXMLHandler.xssfDataType.FORMULA;
                            } else {
                                XSSFCellStyle style = null;
                                if (this.stylesTable != null) {
                                    if (cellStyleStr != null) {
                                        int styleIndex = Integer.parseInt(cellStyleStr);
                                        style = this.stylesTable.getStyleAt(styleIndex);
                                    } else if (this.stylesTable.getNumCellStyles() > 0) {
                                        style = this.stylesTable.getStyleAt(0);
                                    }
                                }

                                if (style != null) {
                                    this.formatIndex = style.getDataFormat();
                                    this.formatString = style.getDataFormatString();
                                    if (this.formatString == null) {
                                        this.formatString = BuiltinFormats.getBuiltinFormat(this.formatIndex);
                                    }
                                }
                            }
                        }
                    } else {
                        this.hfIsOpen = true;
                        this.headerFooter.setLength(0);
                    }
                }

            }
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (this.maxCnt == -1 || this.rowNum < this.maxCnt && this.nextRowNum < this.maxCnt) {
            if (uri == null || uri.equals("http://schemas.openxmlformats.org/spreadsheetml/2006/main")) {
                Object thisStr = null;
                if (this.isTextTag(localName)) {
                    this.vIsOpen = false;
                    switch(this.nextDataType) {
                    case BOOLEAN:
                        char first = this.value.charAt(0);
                        thisStr = first == '0' ? new Boolean(false) : new Boolean(true);
                        break;
                    case ERROR:
                        thisStr = "ERROR:" + this.value;
                        break;
                    case FORMULA:
                        if (this.formulasNotResults) {
                            thisStr = this.formula.toString();
                        } else {
                            String fv = this.value.toString();

                            try {
                                if (fv.indexOf(".") >= 0) {
                                    thisStr = Double.parseDouble(fv);
                                } else {
                                    thisStr = Integer.parseInt(fv);
                                }
                            } catch (Exception var12) {
                                thisStr = fv;
                            }
                        }
                        break;
                    case INLINE_STRING:
                        XSSFRichTextString rtsi = new XSSFRichTextString(this.value.toString());
                        thisStr = rtsi.toString();
                        break;
                    case SST_STRING:
                        String sstIndex = this.value.toString();

                        try {
                            int idx = Integer.parseInt(sstIndex);
                            XSSFRichTextString rtss = new XSSFRichTextString(this.sharedStringsTable.getEntryAt(idx));
                            thisStr = rtss.toString();
                        } catch (NumberFormatException var11) {
                            logger.log(7, new Object[]{"Failed to parse SST index '" + sstIndex, var11});
                        }
                        break;
                    case NUMBER:
                        String n = this.value.toString();
                        if (DateUtil.isADateFormat(this.formatIndex, this.formatString)) {
                            Date dt = DateUtil.getJavaDate(Double.parseDouble(n));
                            thisStr = dt;
                        } else {
                            try {
                                if (n.indexOf(".") >= 0) {
                                    thisStr = Double.parseDouble(n);
                                } else {
                                    thisStr = Integer.parseInt(n);
                                }
                            } catch (Exception var10) {
                                thisStr = n;
                            }
                        }
                        break;
                    default:
                        thisStr = "(TODO: Unexpected type: " + this.nextDataType + ")";
                    }

                    this.output.cell(this.cellRef, thisStr, (String)null);
                } else if ("f".equals(localName)) {
                    this.fIsOpen = false;
                } else if ("is".equals(localName)) {
                    this.isIsOpen = false;
                } else if ("row".equals(localName)) {
                    this.checkForEmptyCellComments(XSSFSheetXMLHandler.EmptyCellCommentsCheckType.END_OF_ROW);
                    this.output.endRow(this.rowNum);
                    this.nextRowNum = this.rowNum + 1;
                } else if ("sheetData".equals(localName)) {
                    this.checkForEmptyCellComments(XSSFSheetXMLHandler.EmptyCellCommentsCheckType.END_OF_SHEET_DATA);
                } else if (!"oddHeader".equals(localName) && !"evenHeader".equals(localName) && !"firstHeader".equals(localName)) {
                    if ("oddFooter".equals(localName) || "evenFooter".equals(localName) || "firstFooter".equals(localName)) {
                        this.hfIsOpen = false;
                        this.output.headerFooter(this.headerFooter.toString(), false, localName);
                    }
                } else {
                    this.hfIsOpen = false;
                    this.output.headerFooter(this.headerFooter.toString(), true, localName);
                }

            }
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (this.vIsOpen) {
            this.value.append(ch, start, length);
        }

        if (this.fIsOpen) {
            this.formula.append(ch, start, length);
        }

        if (this.hfIsOpen) {
            this.headerFooter.append(ch, start, length);
        }

    }

    private void checkForEmptyCellComments(XSSFSheetXMLHandler.EmptyCellCommentsCheckType type) {
        if (this.commentCellRefs != null && !this.commentCellRefs.isEmpty()) {
            if (type == XSSFSheetXMLHandler.EmptyCellCommentsCheckType.END_OF_SHEET_DATA) {
                while(!this.commentCellRefs.isEmpty()) {
                    this.outputEmptyCellComment((CellAddress)this.commentCellRefs.remove());
                }

                return;
            }

            if (this.cellRef == null) {
                if (type == XSSFSheetXMLHandler.EmptyCellCommentsCheckType.END_OF_ROW) {
                    while(!this.commentCellRefs.isEmpty()) {
                        if (((CellAddress)this.commentCellRefs.peek()).getRow() != this.rowNum) {
                            return;
                        }

                        this.outputEmptyCellComment((CellAddress)this.commentCellRefs.remove());
                    }

                    return;
                }

                throw new IllegalStateException("Cell ref should be null only if there are only empty cells in the row; rowNum: " + this.rowNum);
            }

            CellAddress nextCommentCellRef;
            do {
                CellAddress cellRef = new CellAddress(this.cellRef);
                CellAddress peekCellRef = (CellAddress)this.commentCellRefs.peek();
                if (type == XSSFSheetXMLHandler.EmptyCellCommentsCheckType.CELL && cellRef.equals(peekCellRef)) {
                    this.commentCellRefs.remove();
                    return;
                }

                int comparison = peekCellRef.compareTo(cellRef);
                if (comparison > 0 && type == XSSFSheetXMLHandler.EmptyCellCommentsCheckType.END_OF_ROW && peekCellRef.getRow() <= this.rowNum) {
                    nextCommentCellRef = (CellAddress)this.commentCellRefs.remove();
                    this.outputEmptyCellComment(nextCommentCellRef);
                } else if (comparison < 0 && type == XSSFSheetXMLHandler.EmptyCellCommentsCheckType.CELL && peekCellRef.getRow() <= this.rowNum) {
                    nextCommentCellRef = (CellAddress)this.commentCellRefs.remove();
                    this.outputEmptyCellComment(nextCommentCellRef);
                } else {
                    nextCommentCellRef = null;
                }
            } while(nextCommentCellRef != null && !this.commentCellRefs.isEmpty());
        }

    }

    private void outputEmptyCellComment(CellAddress cellRef) {
        this.output.cell(cellRef.formatAsString(), (Object)null, (String)null);
    }

    public interface SheetContentsHandler {
        void startRow(int var1);

        void endRow(int var1);

        void cell(String var1, Object var2, String var3);

        void headerFooter(String var1, boolean var2, String var3);
    }

    private static enum EmptyCellCommentsCheckType {
        CELL,
        END_OF_ROW,
        END_OF_SHEET_DATA;

        private EmptyCellCommentsCheckType() {
        }
    }

    static enum xssfDataType {
        BOOLEAN,
        ERROR,
        FORMULA,
        INLINE_STRING,
        SST_STRING,
        NUMBER;

        private xssfDataType() {
        }
    }
}
