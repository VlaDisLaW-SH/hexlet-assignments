package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> index(
        @RequestParam(defaultValue = "0") Integer min,
        @RequestParam(defaultValue = Integer.MAX_VALUE + "") Integer max
    ) {
        var productSort = productRepository.findAllByPriceBetween(min, max);

        return productSort.stream()
            .sorted(Comparator.comparing(Product::getPrice))
            .toList();
    }
    /*@GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> index(
        @RequestParam(required = false) Integer min,
        @RequestParam(required = false) Integer max
    ) {
        min = min == null ? 0 : min;
        max = max == null ? Integer.MAX_VALUE : max;


        var productSort = productRepository.findAllByPriceBetween(min, max);

        return productSort.stream()
            .sorted(Comparator.comparing(Product::getPrice))
            .toList();
    }*/
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product =  productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
