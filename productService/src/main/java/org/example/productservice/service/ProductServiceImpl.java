package org.example.productservice.service;

import org.example.productservice.dto.ProductDTO;
import org.example.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        productRepository.createProduct(productDTO);
        return productDTO;
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        return productRepository.getProductById(id);
    }

    @Override
    public List<ProductDTO> getProductsByCategoryId(Integer categoryId) {
        return productRepository.getProductsByCategoryId(categoryId);
    }

    @Override
    public ProductDTO updateProduct(Integer id, ProductDTO productDTO) {
        productRepository.updateProduct(id, productDTO);
        return productDTO;
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteProduct(id);
    }
}
