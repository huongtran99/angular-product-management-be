package com.codegym.productmanagements.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ImageForm {
    private Long id;

    private List<MultipartFile> fileName;

    private Product product;
}
