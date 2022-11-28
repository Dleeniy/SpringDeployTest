package sklep.alternatywne_wersje_bazy_danych;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import sklep.model.Product;

@Controller
@RequestMapping(path="/alt3", produces="text/plain")
public class ProductController_v3 {
	@Autowired
	private EntityManager em;
	
	@GetMapping
	@ResponseBody
	public String wszystkieProdukty() {
		StringBuilder sb = new StringBuilder();
		TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
		List<Product> products = query.getResultList();
		for(Product product : products) {
			sb.append("* produkt ")
				.append(product.getProductName())
				.append(" za cenę ")
				.append(product.getPrice())
				.append('\n');
		}
		return sb.toString();
	}

	@GetMapping("/{id}")
	@ResponseBody
	public String jedenProdukt(@PathVariable int id) {
		StringBuilder sb = new StringBuilder();
		Product product = em.find(Product.class, id);
		if(product != null) {
			sb.append("Znaleziony produkt:\n")
				.append(product.getProductName())
				.append(" za cenę ")
				.append(product.getPrice())
				.append('\n');
		} else {
			sb.append("Nie ma produktu o numerze ").append(id);
		}
		return sb.toString();
	}
	
	@GetMapping("/szukaj")
	@ResponseBody
	public String wyszukajProdukty(@RequestParam String name) {
		StringBuilder sb = new StringBuilder();
		
		final String sql = "SELECT p FROM Product p WHERE p.productName = :name";
		TypedQuery<Product> query = em.createQuery(sql, Product.class);
		query.setParameter("name", name);
		List<Product> products = query.getResultList();
		for(Product product : products) {
			sb.append("* produkt ")
				.append(product.getProductName())
				.append(" za cenę ")
				.append(product.getPrice())
				.append('\n');
		}
		return sb.toString();
	}
}
