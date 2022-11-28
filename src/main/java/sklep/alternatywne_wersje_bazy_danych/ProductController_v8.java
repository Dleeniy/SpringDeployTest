package sklep.alternatywne_wersje_bazy_danych;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sklep.model.Product;
import sklep.repository.ProductRepository;

@Controller
@RequestMapping("/alt8")
public class ProductController_v8 {
	@Autowired
	private ProductRepository_v8 productRepository;
	
	@GetMapping
	public String wszystkieProdukty(Model model) {
		List<Product> products = productRepository.findAll();
		model.addAttribute("products", products);
		return "products";
	}

	@GetMapping("/{id}")
	public String jedenProdukt(@PathVariable int id, Model model) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()) {
			model.addAttribute("product", product.get());
			return "product";
		} else {
			model.addAttribute("product_id", id);
			return "missing_product";
		}
	}
	
	@GetMapping("/szukaj")
	public String wyszukiwarka(Model model,
			String name,
			BigDecimal min,
			BigDecimal max) {

		List<Product> products  = List.of();
		if(name != null && !name.isEmpty() && min == null && max == null) {
			products = productRepository.findByProductNameContainingIgnoringCase(name);
		} else if((name == null || name.isEmpty()) && (min != null || max != null)) {
			if(min == null) {
				min = BigDecimal.ZERO;
			}
			if(max == null) {
				max = BigDecimal.valueOf(1000_000_000);
			}
			products = productRepository.findByPriceBetween(min, max);
		} else if(name != null && !name.isEmpty() && (min != null || max != null)) {
			if(min == null) {
				min = BigDecimal.ZERO;
			}
			if(max == null) {
				max = BigDecimal.valueOf(1000_000_000);
			}
			products = productRepository.findByProductNameContainingIgnoringCaseAndPriceBetween(name, min, max);			
		}
		model.addAttribute("products", products);
		return "wyszukiwarka";
	}
}
