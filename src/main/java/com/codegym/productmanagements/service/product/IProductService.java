package com.codegym.productmanagements.service.product;

import com.codegym.productmanagements.model.Product;
import com.codegym.productmanagements.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService extends IGeneralService<Product> {
    Page<Product> findProductByNameContaining(String name, Pageable pageable);
}
