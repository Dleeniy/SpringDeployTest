package sklep.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sklep.model.Product;


/* Gdy w projekcie umieścimy interfejs rozszerzający interfejs JpaRepository (albo podobny)
 * i oznaczymy go adnotacją @Repository (albo skonfigurujemy w inny sposób...),
 * to Spring AUTOMATYCZNIE UTWORZY IMPLEMENTACJĘ tego interfejsu.
 * Dzięki temu "za darmo" mamy metody dający podstawowy dostęp do tabel.

 * Dodatkowo w interfejsie można dopisać własne metody, w których nazwie kryje się zasada działania.
 * Np. findByPriceBetween szuka produktów o cenie między min i max.
 *
 * findByEmail - szuka rekordów z polem email równym parametrowi.
 * 
 * https://www.baeldung.com/spring-data-derived-queries
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByProductName(String name);

	List<Product> findByProductNameContains(String name);

	List<Product> findByProductNameContainingIgnoringCase(String name);

	List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

	List<Product> findByProductNameContainingIgnoringCaseAndPriceBetween(String name, BigDecimal min, BigDecimal max);

}