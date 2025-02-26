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
import com.sulav.entity.UserProfile;
import com.sulav.model.CategoryRequest;
import com.sulav.model.ProductRequest;
import com.sulav.repository.CategoryRepo;
import com.sulav.repository.ProductRepo;
import com.sulav.repository.UserRepo;

@Service
public class ProductService {

	@Value("${file.upload.dir}")
	private String uploadDir;

	private final ProductRepo productRepository;
	private final CategoryRepo categoryRepository;
	private final UserRepo userRepository;

	public ProductService(ProductRepo productRepository, CategoryRepo categoryRepository, UserRepo userRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.userRepository = userRepository;
	}

	// converts product to dto
	private ProductDTO convertToDTO(Product product) {
		return new ProductDTO(product.getProductId(), product.getProductName(), product.getDescription(),
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
		List<ProductDTO> products = category.getProducts().stream().map(this::convertToDTO)
				.collect(Collectors.toList());

		return new CategoryDTO(category.getCategoryId(), category.getCategoryName(), products);
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


	// create product
	public ProductDTO createProduct(ProductRequest productDto) {
	    UserProfile seller = userRepository.findById(productDto.getSellerId())
	            .orElseThrow(() -> new RuntimeException("Seller not found!"));
	    
	    Category category = categoryRepository.findById(productDto.getCategoryId())
	            .orElseThrow(() -> new RuntimeException("Category not found!"));
	    
	    Product product = new Product();
	    product.setProductName(productDto.getProductName());
	    product.setDescription(productDto.getDescription());
	    product.setPrice(productDto.getPrice());
	    product.setQuantity(productDto.getQuantity());
	    product.setCategory(category);
	    product.setSeller(seller);
	    product.setProductImgPath(productDto.getProductImgPath());

	    return convertToDTO(productRepository.save(product));
	}
	

	// create category (only admin)
	public CategoryDTO createCategory(CategoryRequest categoryDto) {
	    Category category = new Category();
	    category.setCategoryName(categoryDto.getCategoryName());

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

	// updates product
	public ProductDTO updateProduct(Long id, ProductRequest productDto) {
	    Product existingProduct = productRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Product not found!"));

	    // Update fields only if they are provided in DTO
	    if (productDto.getProductName() != null) {
	        existingProduct.setProductName(productDto.getProductName());
	    }
	    if (productDto.getPrice() != null) {
	        existingProduct.setPrice(productDto.getPrice());
	    }
	    if (productDto.getCategoryId() != null) {
	        Category category = categoryRepository.findById(productDto.getCategoryId())
	                .orElseThrow(() -> new RuntimeException("Category not found!"));
	        existingProduct.setCategory(category);
	    }
	    if (productDto.getDescription() != null) {
	        existingProduct.setDescription(productDto.getDescription());
	    }
	    if (productDto.getQuantity() != null && productDto.getQuantity() > 0) {
	        existingProduct.setQuantity(productDto.getQuantity());
	    }
	    if (productDto.getProductImgPath() != null) {
	        existingProduct.setProductImgPath(productDto.getProductImgPath());
	    }

	    // Save updated product
	    return convertToDTO(productRepository.save(existingProduct));
	}


	// delete product
	public String deleteProduct(Long id) {
		productRepository.deleteById(id);
		return "Delete was Successful!";
	}

}
