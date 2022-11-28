package sklep.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import sklep.basket.Basket;
import sklep.model.Product;
import sklep.photo.PhotoUtil;
import sklep.repository.ProductRepository;
import sklep.utils.ExceptionUtils;

@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private PhotoUtil photoUtil;

	@GetMapping
	public String wszystkieProdukty(Model model) {
		List<Product> products = productRepository.findAll();
		model.addAttribute("products", products);
		return "products";
	}

	@GetMapping("/{id}")
	public String jedenProdukt(@PathVariable int id, Model model) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			model.addAttribute("product", product.get());
			return "product";
		} else {
			model.addAttribute("product_id", id);
			return "missing_product";
		}
	}

	@GetMapping("/szukaj")
	public String wyszukiwarka(Model model, String name, BigDecimal min, BigDecimal max) {

		List<Product> products = List.of();
		if (name != null && !name.isEmpty() && min == null && max == null) {
			products = productRepository.findByProductNameContainingIgnoringCase(name);
		} else if ((name == null || name.isEmpty()) && (min != null || max != null)) {
			if (min == null) {
				min = BigDecimal.ZERO;
			}
			if (max == null) {
				max = BigDecimal.valueOf(1000_000_000);
			}
			products = productRepository.findByPriceBetween(min, max);
		} else if (name != null && !name.isEmpty() && (min != null || max != null)) {
			if (min == null) {
				min = BigDecimal.ZERO;
			}
			if (max == null) {
				max = BigDecimal.valueOf(1000_000_000);
			}
			products = productRepository.findByProductNameContainingIgnoringCaseAndPriceBetween(name, min, max);
		}
		model.addAttribute("products", products);
		return "wyszukiwarka";
	}

	@GetMapping("/new")
	public String newProduct(Product product) {
		return "product_form";
	}

	@GetMapping("/{id}/edit")
	public String editProduct(@PathVariable int id, Model model) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			model.addAttribute("product", product.get());
			return "product_form";
		} else {
			model.addAttribute("product_id", id);
			return "missing_product";
		}
	}

	@PostMapping({ "/new", "/{id}/edit" })
	public String saveProduct(Model model, @Valid Product product, BindingResult bindingResult) {
		// W tej wersji dane z wypełnionego formularza odbieramy w postaci jednego
		// obiektu Product.
		// Spring sam wpisze dane do pól o takich samych nazwach.
		// Taki parametr od razu staje się częścią modelu (to jest tzw. ModelAttribute)
		// i nie trzeba dodawać go w osobnym poleceniu.

		// Kwestia Bean Validation:
		// W tej wersji przed parametrem Product jest adnotacja @Valid
		// i dodatkowo do metoda posiada parametr BindingResult.
		// Wówczas Spring dokonuje walidacji obiektu przed wywołaniem tej metody
		// i informacje o wyniku walidacji przekazuje w parametrze BindingResult.
		// Metoda jest wywoływana zawsze, a to programista ma sprawdzić czy walidacja
		// się powiodła.
		// W BindingResult znajdują się też informacje o błędach.
		if (bindingResult.hasErrors()) {
			// model.addAttribute("errors", bindingResult.getAllErrors());
			// błędów już nie dodajemy oddzielne, bo będą wyświeltlone przez f:error
			// ponownie wyświetlamy formularz
			return "product_form";
		} else
			try {
				// Gdy próbujemy wywołać save, a obiekt nie spełnia wymagań validation, to wtedy
				// Hibernate zablokuje taki zapis (wyrzuci wyjątek).
				productRepository.save(product);
				// Po pomyślnym zapisaniu przechodzimy na stronę tego produktu
				return "redirect:/products/" + product.getProductId();
			} catch (Exception e) {
				model.addAttribute("errors", ExceptionUtils.allMessages(e));
				return "product_form";
			}
	}

	@GetMapping(path = "/{id}/photo", produces = "image/jpeg")
	@ResponseBody
	public byte[] getPhoto(@PathVariable("id") int productId) {
		return photoUtil.readBytes(productId);
	}

	@GetMapping("/{id}/add-to-basket")
	public String addToBasket(@PathVariable("id") int productId, @SessionAttribute Basket basket) {
		Optional<Product> product = productRepository.findById(productId);
		if (product.isPresent()) {
			basket.addProduct(product.get());
		}

		return "redirect:/products";
	}

}
