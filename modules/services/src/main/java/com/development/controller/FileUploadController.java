package com.development.controller;

import com.development.model.entity.User;
import com.development.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.sql.DataSource;
import java.sql.Blob;
import java.util.Base64;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
public class FileUploadController {



    @Autowired
    UserService userService;

    @Autowired
    private DataSource dataSource;


    @GetMapping(value="/files/{account}")
    //@ResponseBody()
    public String serveFile(@PathVariable double account) throws java.sql.SQLException {
        User user = userService.getUser(account);
        int blobLength = (int) user.getUsr_PHOTO().length();
        return Base64.getEncoder().encodeToString(user.getUsr_PHOTO().getBytes(1, blobLength));

    }

    @PostMapping("/file")
    public String handleFileUpload(@RequestParam("photo") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        User user = userService.getUser((double) 234567);


        try{
            byte[] bytes;
            Blob blob = dataSource.getConnection().createBlob();
            System.out.println(file.getOriginalFilename());
            bytes = file.getBytes();
            blob.setBytes(1,bytes);
            user.setUsr_PHOTO(blob);
            userService.saveUser(user);


        } catch (java.io.IOException e) {System.out.println("IO");}
        catch (java.sql.SQLException e) {System.out.println("SQL");}

        return "redirect:/";
    }


}