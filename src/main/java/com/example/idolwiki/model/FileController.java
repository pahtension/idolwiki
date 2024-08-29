package com.example.idolwiki.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.ResourceLoader;

import java.net.MalformedURLException;

@RestController
public class FileController {
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping(value = "/images/{filename}")
    @ResponseBody
    public Resource showImage(@PathVariable("filename") String filename) throws MalformedURLException {
        // Load file as Resource
        Resource resource = resourceLoader.getResource("file:" + uploadDir + filename);
        return resource;
    }
}
