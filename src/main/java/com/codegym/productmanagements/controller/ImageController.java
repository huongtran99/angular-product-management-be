package com.codegym.productmanagements.controller;

import com.codegym.productmanagements.model.Image;
import com.codegym.productmanagements.model.ImageForm;
import com.codegym.productmanagements.model.Product;
import com.codegym.productmanagements.service.Image.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("images")
public class ImageController {
    @Autowired
    private IImageService imageService;

    @Value("${file-upload}")
    private String fileUpload;

    @GetMapping
    public ResponseEntity<Iterable<Image>> showAll() {
        return new ResponseEntity<>(imageService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Image> createImage(ImageForm imageForm) {
        List<MultipartFile> multipartFiles = imageForm.getFileName();
        for (MultipartFile file : multipartFiles) {
            String fileName = file.getOriginalFilename();
            try {
                FileCopyUtils.copy(file.getBytes(), new File(fileUpload + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image image = new Image();
            image.setFileName(fileName);
            image.setProduct(imageForm.getProduct());
            imageService.save(image);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
