package com.erichiroshi.dscatalog.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erichiroshi.dscatalog.controllers.ProductController;
import com.erichiroshi.dscatalog.entities.Category;
import com.erichiroshi.dscatalog.entities.Product;
import com.erichiroshi.dscatalog.entities.dtos.CategoryDTO;
import com.erichiroshi.dscatalog.entities.dtos.ProductDTO;
import com.erichiroshi.dscatalog.mappers.ProductMapper;
import com.erichiroshi.dscatalog.repositories.ProductRepository;
import com.erichiroshi.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	private final ProductRepository repository;
	private final ProductMapper mapper;
	private final CategoryService categoryService;
    private final PagedResourcesAssembler<ProductDTO> pagedResourcesAssembler;


	public ProductService(ProductRepository productRepository, ProductMapper mapper, CategoryService categoryService, PagedResourcesAssembler<ProductDTO> pagedResourcesAssembler) {
		this.repository = productRepository;
		this.mapper = mapper;
		this.categoryService = categoryService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;

	}

	@Transactional(readOnly = true)
	public PagedModel<EntityModel<ProductDTO>> findAllPaged(Pageable pageable) {
		Page<Product> productsPage = repository.findAllPaged(pageable);
		Page<ProductDTO> productsDtoPage = productsPage.map(mapper::toDto);

		return pagedResourcesAssembler.toModel(productsDtoPage, product -> {
			EntityModel<ProductDTO> entityModel = EntityModel.of(product);
			entityModel.add(WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).findById(product.id())).withSelfRel());
			return entityModel;
		}, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).findAll(pageable))
				.withSelfRel());
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product product = find(id);
		return mapper.toDto(product);
	}

	@Transactional
	public ProductDTO create(ProductDTO productDto) {
		Product product = mapper.toEntity(productDto);

		for (CategoryDTO categoryDto : productDto.categories()) {
			CategoryDTO category = categoryService.findById(categoryDto.id());
			product.getCategories().add(Category.builder().id(category.id()).name(category.name()).build());
		}

		product = repository.save(product);
		return mapper.toDto(product);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		Product product = find(id);
		for (CategoryDTO categoryDto : dto.categories()) {
			categoryService.findById(categoryDto.id());
		}
		product = mapper.partialUpdate(dto, product);
		product = repository.save(product);
		return mapper.toDto(product);
	}

	@Transactional
	public void deleteById(Long id) {
		find(id);
		repository.deleteById(id);
	}

	private Product find(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found. Id: " + id));
	}
}
