package sklep.alternatywne_wersje_bazy_danych;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sklep.model.Product;

@Repository
public interface ProductRepository_v7 extends JpaRepository<Product, Integer> {

}
