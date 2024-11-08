package org.example.productservice.service;

import org.example.productservice.dto.ProductDTO;
import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO getProductById(Integer id);
    List<ProductDTO> getProductsByCategoryId(Integer categoryId);
    ProductDTO updateProduct(Integer id, ProductDTO productDTO);
    void deleteProduct(Integer id);
}
