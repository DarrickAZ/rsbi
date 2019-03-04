//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.ruisitech.bi.entity.common.DSColumn;
import com.ruisitech.bi.entity.common.RequestStatus;
import com.ruisitech.bi.entity.common.Result;
import com.ruisitech.bi.entity.etl.ImpConfigDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.springframework.stereotype.Service;

@Service
public class HadoopService {
    public HadoopService() {
    }

    public Result testHdfs(String hdfsAddress) {
        Result ret = new Result();
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", hdfsAddress);
        conf.set("fs.hdfs.impl", DistributedFileSystem.class.getName());

        try {
            FileSystem.get(conf);
            ret.setResult(RequestStatus.SUCCESS.getStatus());
        } catch (Exception var5) {
            var5.printStackTrace();
            ret.setMsg(var5.getMessage());
            ret.setResult(RequestStatus.FAIL_FIELD.getStatus());
        }

        return ret;
    }

    public List<Object> queryTopN(ImpConfigDto dto, int top) throws IOException {
        List<Object> ret = new ArrayList();
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", dto.getHdfsAddress());
        conf.set("fs.hdfs.impl", DistributedFileSystem.class.getName());
        FileSystem fs = FileSystem.get(conf);
        RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path(dto.getHdfsAddress() + dto.getPath()), false);
        int idx = 0;

        while(files.hasNext()) {
            FSDataInputStream is = null;

            try {
                LocatedFileStatus file = (LocatedFileStatus)files.next();
                is = fs.open(file.getPath());
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;

                while((line = br.readLine()) != null) {
                    String[] cols = line.split(dto.getSplitWord());
                    Map<String, Object> dt = new HashMap();

                    for(int i = 0; i < cols.length; ++i) {
                        String col = cols[i];
                        dt.put(String.valueOf(i), col);
                    }

                    ret.add(dt);
                    ++idx;
                    if (idx >= top) {
                        break;
                    }
                }

                br.close();
                isr.close();
            } catch (Exception var20) {
                var20.printStackTrace();
            } finally {
                is.close();
            }

            if (idx >= top) {
                break;
            }
        }

        return ret;
    }

    public List<DSColumn> readHdfsFileHead(ImpConfigDto dto) throws Exception {
        List<DSColumn> ret = new ArrayList();
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", dto.getHdfsAddress());
        conf.set("fs.hdfs.impl", DistributedFileSystem.class.getName());
        FileSystem fs = FileSystem.get(conf);
        RemoteIterator files = fs.listFiles(new Path(dto.getHdfsAddress() + dto.getPath()), false);

        while(files.hasNext()) {
            FSDataInputStream is = null;

            try {
                LocatedFileStatus file = (LocatedFileStatus)files.next();
                is = fs.open(file.getPath());
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                if ((line = br.readLine()) != null) {
                    String[] cols = line.split(dto.getSplitWord());

                    for(int i = 0; i < cols.length; ++i) {
                        String col = cols[i];
                        DSColumn dscol = new DSColumn();
                        dscol.setIdx(i);
                        dscol.setName(col);
                        dscol.setType("String");
                        dscol.setLength(50);
                        ret.add(dscol);
                    }
                }

                br.close();
                isr.close();
            } catch (Exception var18) {
                var18.printStackTrace();
            } finally {
                is.close();
            }
        }

        return ret;
    }
}
