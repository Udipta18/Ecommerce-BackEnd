package com.back.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.back.config.AppConstants;
import com.back.models.Product;
import com.back.payload.ApiResponse;
import com.back.payload.ProductDto;
import com.back.payload.ProductResponse;
import com.back.service.FileUpload;
import com.back.service.ProductService;

/**
 * This class contains all the urls to Create,Update,Fetch and Delete Products;
 * 
 * @since 1.0
 * @author admin
 *
 */
@RestController
@RequestMapping("/")
public class ProductController {
	@Autowired
	private ProductService service;
	
	@Autowired
	private FileUpload fileUpload;
	
	@Value("${product.images.path}")
    private String imagePath;


	/**
	 * This method provide url to create product
	 * 
	 * @since 1.0
	 * @param product
	 * @return Product
	 * @see first.Rest.Services.ProductServiceImpl
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/categories/{categoryId}/products")
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto p, @PathVariable int categoryId) {
		ProductDto createProduct = service.createProduct(p, categoryId);
		return new ResponseEntity<ProductDto>(createProduct, HttpStatus.CREATED);

	}

	/**
	 * This method provide url to get all product
	 * 
	 * @since 1.0
	 * @return List of Product
	 * @see first.Rest.Services.ProductServiceImpl
	 */

// @RequestMapping(value = "/all-product",method = RequestMethod.GET)
	@GetMapping("/products")
	public ResponseEntity<ProductResponse> getAllProduct(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NUMBER_STRING, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE_STRING, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_STRING, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_STRING, required = false) String sortDir
) {
		ProductResponse allProduct = service.getAllProduct(pageNo, pageSize,sortBy,sortDir);
		return new ResponseEntity<ProductResponse>(allProduct, HttpStatus.OK);
	}

	/**
	 * This method provide url to get single product to given product id
	 * 
	 * @since 1.0
	 * @param productId
	 * @return Product
	 * @see first.Rest.Services.ProductServiceImpl
	 */
	@GetMapping("/products/{productId}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable int productId) {
		ProductDto productDto = service.getProduct(productId);
		return new ResponseEntity<ProductDto>(productDto, HttpStatus.OK);

	}

	/**
	 * This method provide url to delete product
	 * 
	 * @since 1.0
	 * @param productId
	 * @return message in the form of String
	 * @see first.Rest.Services.ProductServiceImpl
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable int productId) {
		service.deleteProduct(productId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted successfullt", true), HttpStatus.OK);
	}

	/**
	 * This method provide url to update product
	 * 
	 * @since 1.0
	 * @param productId
	 * @param Product
	 * @return Product
	 * @see first.Rest.Services.ProductServiceImpl
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/products/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable("productId") int pid,
			@RequestBody ProductDto newProduct) {
		ProductDto updateProduct = service.updateProduct(newProduct, pid);
		return new ResponseEntity<ProductDto>(updateProduct, HttpStatus.OK);
	}

	// categories wise product fetch
	@GetMapping("/categories/{categoryId}/products")
	public ResponseEntity<ProductResponse> getProductByCategory(@PathVariable int categoryId,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) {
		ProductResponse productByCategory = service.getProductByCategory(categoryId, pageNo, pageSize);
		return new ResponseEntity<ProductResponse>(productByCategory, HttpStatus.OK);
	}
	
	
	//upload the file for product image

    @PostMapping("/products/images/{productId}")
    public ResponseEntity<?> uploadImageOfProduct(
            @PathVariable int productId,
            @RequestParam("product_image") MultipartFile file
    ) {

        ProductDto product = this.service.getProduct(productId);
        String imageName = null;
        try {
            imageName = this.fileUpload.uploadFile(imagePath, file);
            product.setImageName(imageName);
            ProductDto productDto = this.service.updateProduct(product, productId);
            return new ResponseEntity<>(productDto, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("message", "File not uploaded on server !!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    
  //get the image of given product
    @GetMapping("/products/images/{productId}")
    public void downloadImage(@PathVariable int productId, HttpServletResponse response) throws IOException {
        ProductDto product = this.service.getProduct(productId);
        String imageName = product.getImageName();
        String fullPath = imagePath + File.separator + imageName;
        InputStream resource = this.fileUpload.getResource(fullPath);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        OutputStream outputStream = response.getOutputStream();
        StreamUtils.copy(resource, outputStream);

    }

}
