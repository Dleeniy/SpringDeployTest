package sklep.alternatywne_wersje_bazy_danych;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sklep.model.Product;

@Controller
@RequestMapping("/alt4")
public class ProductController_v4 {
	@Autowired
	private EntityManager em;
	
	@GetMapping
	public String wszystkieProdukty(Model model) {
		TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
		List<Product> products = query.getResultList();
		model.addAttribute("products", products);
		return "products";
	}

	@GetMapping("/{id}")
	public String jedenProdukt(@PathVariable int id, Model model) {
		Product product = em.find(Product.class, id);
		if(product != null) {
			model.addAttribute("product", product);
			return "product";
		} else {
			model.addAttribute("product_id", id);
			return "missing_product";
		}
	}
	
	@GetMapping("/szukaj")
	public String wyszukajProdukty(@RequestParam String name, Model model) {
		final String sql = "SELECT p FROM Product p WHERE p.productName = :name";
		TypedQuery<Product> query = em.createQuery(sql, Product.class);
		query.setParameter("name", name);
		List<Product> products = query.getResultList();
		model.addAttribute("products", products);
		return "products";
	}
}
