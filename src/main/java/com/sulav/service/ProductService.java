package com.sulav.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sulav.dto.CategoryDTO;
import com.sulav.dto.ProductDTO;
import com.sulav.dto.ReviewDTO;
import com.sulav.entity.Category;
import com.sulav.entity.Product;
import com.sulav.entity.Review;
import com.sulav.repository.CategoryRepo;
import com.sulav.repository.ProductRepo;

@Service
public class ProductService {

	@Value("${file.upload.dir}")
	private String uploadDir;

	private final ProductRepo productRepository;
	private final CategoryRepo categoryRepository;

	public ProductService(ProductRepo productRepository, CategoryRepo categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	
	// converts product to dto
	private ProductDTO convertToDTO(Product product) {
		return new ProductDTO(product.getProductId(), product.getProductName(), product.getPDescription(),
				product.getPrice(),
				product.getReviews().stream().map(this::convertToReviewDTO).collect(Collectors.toList()),
				product.getProductImgPath(), product.getQuantity(), product.getSeller().getUsername(),
				product.getCategory().getCategoryName());
	}

	
	// convert review to dto
	private ReviewDTO convertToReviewDTO(Review review) {
		return new ReviewDTO(review.getReviewId(), review.getComment(), review.getRating(),
				review.getUser().getUsername(), review.getProduct().getProductName());
	}

	// convert category to dto
	private CategoryDTO convertToCategoryDTO(Category category) {
		return new CategoryDTO(category.getCategoryId(), category.getCategoryName(),
				category.getProducts().stream().map(this::convertToDTO).collect(Collectors.toList()));
	}

	// view all products
	public List<ProductDTO> getAllProducts() {
		List<Product> products = productRepository.findAll();
		List<ProductDTO> productDetails = products.stream().map(this::convertToDTO).collect(Collectors.toList());
		return productDetails;
	}

	// view specific product
	public ProductDTO getProductById(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found!"));
		return convertToDTO(product);
	}

	// search product
	public ProductDTO getProductByName(String name) {
		Product product = productRepository.findByProductName(name)
				.orElseThrow(() -> new RuntimeException("Product not found!"));

		return convertToDTO(product);
	}

	// view all products in particular category
	public CategoryDTO getProductInCategory(Long categoryId) {

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Category not found!"));
		return convertToCategoryDTO(category);
	}

	// create new product
	public ProductDTO createProduct(Product product) {
		return convertToDTO(productRepository.save(product));
	}
	
	
	// create new category
	public CategoryDTO createCategory(Category category) {
		return convertToCategoryDTO(categoryRepository.save(category));
	}

	// upload product image
	public String uploadProductImg(Long productId, MultipartFile file) {
		String[] allowedExtensions = { "png", "jpg", "jpeg" };
		try {
			Product product = productRepository.findById(productId)
					.orElseThrow(() -> new RuntimeException("Product not found!"));

			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			String originalFileName = file.getOriginalFilename();

			String fileExtension = "";
			int dotIndex = originalFileName.lastIndexOf('.');
			if (dotIndex > 0) {
				fileExtension = originalFileName.substring(dotIndex + 1).toLowerCase();
			}
			boolean isValidExtension = false;
			for (String extension : allowedExtensions) {
				if (extension.equals(fileExtension)) {
					isValidExtension = true;
					break;
				}
			}

			if (!isValidExtension) {
				return "Invalid file type.  Only .jpg, .png and .jpeg are allowed";
			}

			String baseName = originalFileName.substring(0, dotIndex);
			Path filePath = uploadPath.resolve(originalFileName);
			int count = 1; // to differentiate same file name
			while (Files.exists(filePath)) {
				filePath = uploadPath.resolve(baseName + "_" + count + "." + fileExtension);
				count++;
			}

			file.transferTo(filePath.toFile());
			product.setProductImgPath(filePath.toString());
			productRepository.save(product);

			return "Product image uploaded";

		} catch (Exception e) {
			return "Error: " + e.getMessage();
		}
	}

	// update product
	public ProductDTO updateProduct(Long id, Product product) {
		Product existingProduct = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found!"));
		existingProduct.setProductName(product.getProductName());
		existingProduct.setPrice(product.getPrice());
		existingProduct.setCategory(product.getCategory());
		existingProduct.setPDescription(product.getPDescription());
		existingProduct.setQuantity(product.getQuantity());
		existingProduct.setReviews(product.getReviews());
		existingProduct.setSeller(product.getSeller());
		existingProduct.setProductImgPath(product.getProductImgPath());
		existingProduct.setCarts(product.getCarts());
		existingProduct.setOrders(product.getOrders());
		return convertToDTO(productRepository.save(existingProduct));
	}

	// delete product
	public String deleteProduct(Long id) {
		productRepository.deleteById(id);
		return "Delete was Successful!";
	}

}
