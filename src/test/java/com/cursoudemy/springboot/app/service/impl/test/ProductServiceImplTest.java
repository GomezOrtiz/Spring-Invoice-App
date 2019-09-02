package com.cursoudemy.springboot.app.service.impl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.cursoudemy.springboot.app.model.entity.Product;
import com.cursoudemy.springboot.app.service.ProductService;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductServiceImplTest {
	
	@Autowired 
	private ProductService productService;

	@Test
	public void shouldFindAllProducts() {
		
		// GIVEN 
		@SuppressWarnings("serial")
		List<Product> expected = new ArrayList<Product>() {{ 
			add(new Product("Panasonic Pantalla LCD", 259990D)); 
			add(new Product("Sony Camara digital DSC-W320B", 123490D));
			add(new Product("Apple iPod shuffle", 1499990D));
			add(new Product("Sony Notebook Z110", 37990D));
			}};
						
		//WHEN
		List<Product> result = productService.findAll();
		
		//THEN
		assertNotNull(result);
		assertEquals(4, result.size(), "El número de productos debería ser el esperado");
        assertEquals(expected.get(0).getName(), result.get(0).getName(), "El nombre del primer producto debería ser el esperado");
        assertEquals(expected.get(expected.size() -1).getName(), result.get(result.size() -1).getName(), "El nombre del último producto debería ser el esperado");
	}
	
	@Test
	public void shouldFindOneProductById() {
		
		//GIVEN
		Long id = 3L;
		Product expected = new Product("Apple iPod shuffle", 1499990D);
		
		//WHEN
		Product result = productService.findById(id);
		
		//THEN
		assertNotNull(result, "Debería haber encontrado un producto");
		assertEquals(expected.getName(), result.getName(), "El nombre del producto debería ser el esperado");
		assertEquals(expected.getPrice(), result.getPrice(), "El precio del producto debería ser el esperado");
	}
	
	@Test 
	public void shouldNotFindProductByIdIfNotExists() {
		
		//GIVEN
		Long id = 66666666L;
		
		//WHEN
		Product result = productService.findById(id);
		
		//THEN
		assertNull(result, "No debe encontrar producto por una ID que no existe");
	}
	
	@Test
	public void shouldCreateNewProduct() {
		
		//GIVEN
		Product newProduct = new Product("Hewlett Packard Multifuncional F2280", 69990D);
		
		//WHEN
		productService.create(newProduct);
		List<Product> result = productService.findAll();
				
		//THEN
		assertEquals(5, result.size(), "El número de productos debería ser el esperado");
        assertEquals(newProduct.getName(), result.get(result.size() -1).getName(), "El nombre del último producto debería ser el esperado");
        assertNotNull(result.get(result.size() -1).getCreatedAt(), "La fecha de creación no debería estar vacía"); 
	}
	
	@Test
	public void shouldUpdateProduct() {
		
		//GIVEN
		Long id = 3L;
		Product oldProduct = productService.findById(id);
		Product newProduct = new Product("Xiaomi Redmi Note 5", 11670D);
		newProduct.setId(id);
		
		//WHEN
		productService.update(newProduct);
		Product updatedProduct = productService.findById(id);
		
		//THEN
		assertNotEquals(oldProduct.getName(), updatedProduct.getName(), "El nombre del producto debería haber cambiado");
		assertNotEquals(oldProduct.getPrice(), updatedProduct.getPrice(), "El precio del producto debería haber cambiado");
        assertNull(oldProduct.getUpdatedAt(), "La fecha de actualización debería estar vacía antes del cambio");
        assertNotNull(updatedProduct.getUpdatedAt(), "La fecha de actualización no debería estar vacía después del cambio");
	}
	
	@Test 
	public void shouldNotUpdateProductIfNotExists() {
		
		//GIVEN
		Long id = 66666666L;
		Product oldProduct = productService.findById(id);
		int oldSize = productService.findAll().size();
		
		Product newProduct = new Product("Xiaomi Redmi Note 5", 11670D);
		newProduct.setId(id);
				
		//WHEN
		productService.update(newProduct);
		Product updatedProduct = productService.findById(id);		
		
		//THEN
		assertNull(oldProduct, "El producto no debe existir antes del update");
		assertNull(updatedProduct, "El producto no debe existir después del update");
		assertEquals(oldSize, productService.findAll().size(), "No debe haber creado el producto");
	}
	
	@Test
	public void shouldDeleteProduct() throws Exception {

		//GIVEN
		Long id = 2L;
		Product expected = new Product("Sony Notebook Z110", 37990D);

		//WHEN
		productService.delete(id);
		List<Product> result = productService.findAll();
		
		//THEN
		assertEquals(3, result.size(), "El número de productos debería ser el esperado");
        assertEquals(expected.getName(), result.get(result.size() -1).getName(), "El nombre del último producto tras la eliminación debería ser el esperado");
        assertNull(productService.findById(id), "No debería encontrar el producto borrado");
	}
	
	@Test
	public void shouldNotDeleteProductByIdIfNotExists() {
		
		//GIVEN
		Long id = 66666666L;
		int oldSize = productService.findAll().size();
		
		//WHEN
		assertThrows(IllegalArgumentException.class, ()-> {
			productService.delete(id);
		}, "Debería producirse un error");
		
		//THEN
		assertEquals(oldSize, productService.findAll().size(), "El número de productos no debe haber cambiado");
	}	
}
