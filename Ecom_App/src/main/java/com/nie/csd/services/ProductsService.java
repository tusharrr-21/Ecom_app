package com.nie.csd.services;

import com.nie.csd.exceptions.IdNotPresentException;
import com.nie.csd.models.Products;
import com.nie.csd.repositories.ProductRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
// for logger
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


@Service
public class ProductsService {

    Logger logger=LoggerFactory.getLogger(ProductsService.class);

    @Autowired
    ProductRepository repository;

    // retriving all products
    public List<Products> getAllProducts() {
        logger.info("Retriving all products from the database of collection products mdg from service");
        return repository.findAll();
    }

    // retriving product by id
    public Products getByProductId(String id)throws IdNotPresentException {
        logger.debug("Retriving product by id: "+id+" from the databaseof collection products msg from service");
            return repository.findById(id)
                .orElseThrow(()->{
                    logger.error("Product not found with id: "+id);
                    return new IdNotPresentException("Product not found with id: "+id); 
                });
    }

    // adding new product
    public Products addProduct(Products products) {
        logger.info("Adding new product to the database of collection products msg from service");
        return repository.save(products);
    }

    // updating product by id
    public Products updateProduct(String id, Products products) {
        logger.info("Updating product by id: "+id+" to the database of collection products msg from service");
        Products existingproduct=repository.findById(id).get();
        if(existingproduct!=null){
            logger.info("Product not found with id: "+id+" adding the product details");
            existingproduct.setName(products.getName());
            existingproduct.setDescription(products.getDescription());
            existingproduct.setCategory(products.getCategory());
            existingproduct.setTags(products.getTags());
            existingproduct.setPrice(products.getPrice());
            existingproduct.setStock(products.getStock());
            return repository.save(existingproduct);
        }
        return repository.save(products);
    }

    // deleting product by id
    public void deleteProduct(String id) {
        Products existingproduct=repository.findById(id).get();
        if(existingproduct!=null){
            logger.info("Deleting product by id: "+id+" from the database of collection products msg from service");
            repository.delete(existingproduct);
            System.out.println("Product deleted successfully");
        }
        else{
            logger.error("Product not found with id: "+id);
            System.out.println("Product not found");
        }
    }
}
