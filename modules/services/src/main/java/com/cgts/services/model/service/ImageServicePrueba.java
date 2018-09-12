package com.cgts.services.model.service;

import com.cgts.services.db.AttributeSql;
import com.cgts.services.db.Recordset;
import com.cgts.services.db.SQLRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServicePrueba {

    @Autowired
    private DataSource dataSource;

    public void saveImage(MultipartFile file) throws Throwable{
        SQLRunner db = null;

        try{
            db = new SQLRunner(dataSource.getConnection());
            String sql = "insert into TESTTABLE(img) values (?)";
            List<AttributeSql> attrs = new ArrayList<AttributeSql>();
            byte[] img = file.getBytes();
            attrs.add(new AttributeSql("VOTING_CERTIFICATE", img, Types.BLOB));
            db.execBin(attrs, sql);


        }catch (Throwable e){
            System.out.println(e.getMessage());
        }finally {

            db.closeConnection();

        }



    }
}
