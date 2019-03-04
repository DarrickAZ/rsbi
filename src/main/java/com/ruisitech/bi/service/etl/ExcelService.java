//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.ruisitech.bi.entity.common.DSColumn;
import com.ruisitech.bi.entity.etl.ImpConfigDto;
import com.ruisitech.bi.util.XLSX2CSV;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

@Service
public class ExcelService {
    public ExcelService() {
    }

    public List<DSColumn> listColumns(ImpConfigDto dto) throws Exception {
        List<DSColumn> ret = new ArrayList();
        File file = new File(dto.getRealPath());
        InputStream stream = null;
        HSSFWorkbook wb = null;

        try {
            String name = file.getName();
            stream = new FileInputStream(file);
            FormulaEvaluator evaluator = null;
            int k;
            String head;
            if (name.endsWith("xls")) {
                HSSFWorkbook hwb = new HSSFWorkbook(stream);
                wb = hwb;
                evaluator = new HSSFFormulaEvaluator(hwb);
                Sheet sheet = hwb.getSheetAt(dto.getSheetIndex() - 1);
                Row row;
                if ("y".equals(dto.getNameinhead())) {
                    row = sheet.getRow(1);
                } else {
                    row = sheet.getRow(0);
                }

                Row first = sheet.getRow(0);

                for(k = 0; k < first.getLastCellNum(); ++k) {
                    Cell cell = row.getCell(k);
                    Object headName = first.getCell(k);
                    if (headName != null) {
                        DSColumn dscol = new DSColumn();
                        if ("y".equals(dto.getNameinhead())) {
                            head = headName.toString();
                            dscol.setName(head);
                            dscol.setDispName(head);
                        } else {
                            dscol.setName("c" + (k + 1));
                        }

                        dscol.setIdx(k + 1);
                        int tp = cell == null ? 1 : cell.getCellType();
                        if (tp == 0) {
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                dscol.setType("Datetime");
                            } else {
                                dscol.setType("Double");
                                dscol.setLength(12);
                                dscol.setScale(2);
                            }
                        } else if (tp == 2) {
                            String vtp = "String";
                            CellValue cellValue = evaluator.evaluate(cell);
                            switch(cellValue.getCellType()) {
                            case 0:
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    vtp = "Datetime";
                                } else {
                                    vtp = "Double";
                                    dscol.setLength(12);
                                    dscol.setScale(2);
                                }
                                break;
                            case 1:
                                vtp = "String";
                                dscol.setLength(200);
                                break;
                            case 2:
                                vtp = "String";
                                dscol.setLength(200);
                                break;
                            case 3:
                                vtp = "String";
                                dscol.setLength(50);
                                break;
                            case 4:
                                vtp = "String";
                                dscol.setLength(200);
                                break;
                            case 5:
                                vtp = "String";
                                dscol.setLength(50);
                            }

                            dscol.setType(vtp);
                        } else {
                            dscol.setType("String");
                            dscol.setLength(200);
                        }

                        ret.add(dscol);
                    }
                }

                return ret;
            } else {
                List<Object> dts = new ArrayList();
                OPCPackage p = OPCPackage.open(file, PackageAccess.READ);
                XLSX2CSV xlsx2csv = new XLSX2CSV(p, dto, dts);
                xlsx2csv.setControlMapOrder(true);
                xlsx2csv.process(2);
                p.close();
                if (dts.size() == 0) {
                    List<DSColumn> var27 = ret;
                    return var27;
                } else {
                    k = 0;
                    Map<String, Object> first = (Map)dts.get(0);

                    DSColumn dscol;
                    for(Iterator var13 = first.entrySet().iterator(); var13.hasNext(); ret.add(dscol)) {
                        Entry<String, Object> entity = (Entry)var13.next();
                        ++k;
                        head = entity.getValue().toString();
                        dscol = new DSColumn();
                        dscol.setIdx(k);
                        if ("y".equals(dto.getNameinhead())) {
                            dscol.setName(head);
                        } else {
                            dscol.setName("c" + k);
                        }
                    }

                    Map<String, Object> sec = (Map)dts.get(1);
                    k = 0;

                    for(Iterator var31 = sec.entrySet().iterator(); var31.hasNext(); ++k) {
                        Entry<String, Object> entity = (Entry)var31.next();
                        Object col = entity.getValue();
                        dscol = (DSColumn)ret.get(k);
                        if (col instanceof String) {
                            dscol.setType("String");
                            dscol.setLength(400);
                        } else if (col instanceof Double) {
                            dscol.setType("Double");
                            dscol.setLength(16);
                            dscol.setScale(4);
                        } else if (col instanceof Integer) {
                            dscol.setType("Int");
                            dscol.setLength(10);
                        } else if (col instanceof Date) {
                            dscol.setType("Datetime");
                        } else {
                            dscol.setType("String");
                            dscol.setLength(400);
                        }
                    }

                    List<DSColumn> var32 = ret;
                    return var32;
                }
            }
        } catch (Exception var21) {
            var21.printStackTrace();
            throw var21;
        } finally {
            if (wb != null) {
                wb.close();
            }

            if (stream != null) {
                stream.close();
            }

        }
    }

    public List<Object> queryTop100(ImpConfigDto dto) throws Exception {
        return this.readXls(dto, "data", 100);
    }

    public void delXls(String xlsPath) {
        File path = new File(xlsPath);
        File[] files = path.listFiles(new FileFilter() {
            @Override
            public boolean accept(File arg0) {
                return arg0.getName().endsWith("xls") || arg0.getName().endsWith("xlsx");
            }
        });

        for(int i = 0; files != null && files.length > 0 && i < 1; ++i) {
            files[i].delete();
        }

    }

    public List<Object> readXls(ImpConfigDto dto, String readType, int top) throws Exception {
        File file = new File(dto.getRealPath());
        List<Object> ret = new ArrayList();
        InputStream stream = null;
        Workbook wb = null;
        HSSFFormulaEvaluator evaluator = null;

        try {
            String name = file.getName();
            stream = new FileInputStream(file);
            BufferedInputStream bi = new BufferedInputStream(stream);
            if (!name.endsWith("xls")) {
                OPCPackage p = OPCPackage.open(file, PackageAccess.READ);
                XLSX2CSV xlsx2csv = new XLSX2CSV(p, dto, ret);
                xlsx2csv.process(top);
                p.close();
                List<Object> var36 = ret;
                return var36;
            }

            HSSFWorkbook hwb = new HSSFWorkbook(bi);
            wb = hwb;
            evaluator = new HSSFFormulaEvaluator(hwb);
            Sheet sheet = hwb.getSheetAt(dto.getSheetIndex() - 1);
            int j;
            if ("head".equals(readType) && sheet.getLastRowNum() > 0) {
                Row row = sheet.getRow(0);

                for(j = 0; j < row.getLastCellNum(); ++j) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        throw new Exception("表头有空字段。");
                    }

                    String col = cell.toString();
                    DSColumn dscol = new DSColumn();
                    dscol.setName(col);
                    int tp = cell.getCellType();
                    if (tp == 0) {
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            dscol.setType("Datetime");
                        } else {
                            dscol.setType("Double");
                            dscol.setLength(12);
                        }
                    } else if (tp == 2) {
                        String vtp = "String";
                        CellValue cellValue = evaluator.evaluate(cell);
                        switch(cellValue.getCellType()) {
                        case 0:
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                vtp = "Datetime";
                            } else {
                                vtp = "Double";
                                dscol.setLength(12);
                            }
                            break;
                        case 1:
                            vtp = "String";
                            dscol.setLength(200);
                            break;
                        case 2:
                            vtp = "String";
                            dscol.setLength(200);
                            break;
                        case 3:
                            vtp = "String";
                            dscol.setLength(50);
                            break;
                        case 4:
                            vtp = "String";
                            dscol.setLength(200);
                            break;
                        case 5:
                            vtp = "String";
                            dscol.setLength(50);
                        }

                        dscol.setType(vtp);
                    } else {
                        dscol.setType("String");
                        dscol.setLength(200);
                    }

                    ret.add(dscol);
                }
            } else if ("data".equals(readType) && sheet.getLastRowNum() > 0) {
                int rowCnt = sheet.getRow(0).getLastCellNum();

                for(j = 0; j <= sheet.getLastRowNum(); ++j) {
                    Map<String, Object> data = new HashMap();
                    Row datarow = sheet.getRow(j);
                    if (datarow != null) {
                        for(int k = 0; k < rowCnt; ++k) {
                            String key = String.valueOf(k);
                            Cell cell = datarow.getCell(k);
                            if (cell == null) {
                                data.put(key, (Object)null);
                            } else {
                                int tp = cell.getCellType();
                                if (tp == 1) {
                                    data.put(key, cell.getStringCellValue());
                                } else if (tp == 4) {
                                    data.put(key, String.valueOf(cell.getBooleanCellValue()));
                                } else if (tp == 0) {
                                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                        data.put(key, cell.getDateCellValue());
                                    } else {
                                        double doubleVal = cell.getNumericCellValue();
                                        long longVal = Math.round(doubleVal);
                                        Object inputValue = null;
                                        if (Double.parseDouble(longVal + ".0") == doubleVal) {
                                            inputValue = longVal;
                                        } else {
                                            inputValue = doubleVal;
                                        }

                                        data.put(key, inputValue);
                                    }
                                } else if (tp == 3) {
                                    data.put(key, "");
                                } else if (tp == 5) {
                                    data.put(key, "");
                                } else if (tp == 2) {
                                    Object value = "";

                                    try {
                                        CellValue cellValue = evaluator.evaluate(cell);
                                        switch(cellValue.getCellType()) {
                                        case 0:
                                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                                value = cell.getDateCellValue();
                                            } else {
                                                value = cellValue.getNumberValue();
                                            }
                                            break;
                                        case 1:
                                            value = cellValue.getStringValue();
                                            break;
                                        case 2:
                                            value = "";
                                            break;
                                        case 3:
                                            value = "";
                                            break;
                                        case 4:
                                            value = String.valueOf(cellValue.getBooleanValue());
                                            break;
                                        case 5:
                                            value = "";
                                        }
                                    } catch (Exception var29) {
                                        value = cell.getStringCellValue();
                                    }

                                    data.put(key, value);
                                } else {
                                    data.put(key, cell.toString());
                                }
                            }
                        }

                        ret.add(data);
                        if (j == top) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception var30) {
            var30.printStackTrace();
            throw var30;
        } finally {
            if (wb != null) {
                wb.close();
            }

            if (stream != null) {
                stream.close();
            }

        }

        return ret;
    }
}
