//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.csvreader.CsvReader;
import com.ruisitech.bi.entity.common.DSColumn;
import com.ruisitech.bi.entity.etl.ImpConfigDto;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CsvService {
    public CsvService() {
    }

    public List<Object> queryTopN(ImpConfigDto dto, int top) throws Exception {
        return this.readCsv(dto, "data", top);
    }

    public List listColumns(ImpConfigDto dto) throws Exception {
        return this.readCsv(dto, "head", -1);
    }

    public void delCsv(String csvPath) {
        File path = new File(csvPath);
        if (path.exists()) {
            path.delete();
        }

    }

    public List<Object> readCsv(ImpConfigDto dto, String readType, int top) throws Exception {
        File path = new File(dto.getRealPath());
        FileInputStream input = null;
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;
        List<Object> ret = new ArrayList();
        CsvReader reader = null;

        try {
            input = new FileInputStream(path);
            read = new InputStreamReader(input, dto.getEncode());
            bufferedReader = new BufferedReader(read);
            char cr = true;
            char cr;
            if ("\t".equals(dto.getSplitWord())) {
                cr = '\t';
            } else if (",".equals(dto.getSplitWord())) {
                cr = ',';
            } else if ("@".equals(dto.getSplitWord())) {
                cr = '@';
            } else {
                cr = dto.getSplitWord().toCharArray()[0];
            }

            reader = new CsvReader(bufferedReader, cr);
            if ("head".equalsIgnoreCase(readType)) {
                String[] vls;
                int i;
                if (reader.readHeaders()) {
                    vls = reader.getHeaders();

                    for(i = 0; i < vls.length; ++i) {
                        String col = vls[i] == null ? vls[i] : vls[i].replaceAll("\"", "");
                        DSColumn dscol = new DSColumn();
                        dscol.setIdx(i + 1);
                        dscol.setName(col);
                        dscol.setType("String");
                        ret.add(dscol);
                    }
                }

                if (reader.readRecord()) {
                    vls = reader.getValues();

                    for(i = 0; i < vls.length; ++i) {
                        DSColumn c = (DSColumn)ret.get(i);

                        try {
                            if (vls[i].indexOf(".") >= 0) {
                                Double.parseDouble(vls[i]);
                                c.setType("Double");
                                c.setLength(16);
                                c.setScale(4);
                            } else {
                                Integer.parseInt(vls[i]);
                                c.setType("Int");
                                c.setLength(11);
                            }
                        } catch (Exception var18) {
                            c.setType("String");
                            c.setLength(500);
                        }
                    }
                }
            } else if (!"headdata".equalsIgnoreCase(readType)) {
                int idx = 0;

                while(reader.readRecord()) {
                    String[] vls = reader.getValues();
                    Map<String, Object> m = new HashMap();

                    for(int l = 0; l < vls.length; ++l) {
                        m.put(String.valueOf(l), vls[l] == null ? null : vls[l].replaceAll("\"", ""));
                    }

                    ret.add(m);
                    ++idx;
                    if (top == idx) {
                        break;
                    }
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }

            if (bufferedReader != null) {
                bufferedReader.close();
            }

            if (read != null) {
                read.close();
            }

            if (input != null) {
                input.close();
            }

        }

        return ret;
    }

    public List<DSColumn> createEmptyHead(int size) {
        List<DSColumn> ls = new ArrayList();

        for(int i = 0; i < size; ++i) {
            DSColumn ds = new DSColumn();
            ds.setDispName("c" + (i + 1));
            ds.setName("c" + (i + 1));
            ds.setIdx(i + 1);
            ls.add(ds);
        }

        return ls;
    }
}
