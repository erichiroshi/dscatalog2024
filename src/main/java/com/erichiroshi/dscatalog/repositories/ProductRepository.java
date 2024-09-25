package com.erichiroshi.dscatalog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.erichiroshi.dscatalog.entities.Category;
import com.erichiroshi.dscatalog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT DISTINCT obj FROM Product obj INNER JOIN obj.categories cats WHERE "
			+ "(COALESCE(:categories) IS NULL OR cats IN :categories) AND "
			+ "(LOWER(obj.name) LIKE LOWER(CONCAT('%',:name,'%'))) ")
	Page<Product> find(List<Category> categories, String name, Pageable pageable);

	@Query("SELECT p FROM Product p INNER JOIN p.categories")
	Page<Product> findAllPaged(Pageable pageable);

}
