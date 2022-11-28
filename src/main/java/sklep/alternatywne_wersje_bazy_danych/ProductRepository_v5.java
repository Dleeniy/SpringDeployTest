package sklep.alternatywne_wersje_bazy_danych;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sklep.model.Product;

@Repository
public class ProductRepository_v5 {
	@Autowired
	private EntityManager em;
	
	public List<Product> findAll() {
		TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
		return query.getResultList();
	}
	
	public Optional<Product> findById(int productId) {
		return Optional.ofNullable(em.find(Product.class, productId));
	}
	
	public List<Product> findByProductName(String name) {
		final String sql = "SELECT p FROM Product p WHERE p.productName = :name";
		TypedQuery<Product> query = em.createQuery(sql, Product.class);
		query.setParameter("name", name);
		return query.getResultList();
	}
}
