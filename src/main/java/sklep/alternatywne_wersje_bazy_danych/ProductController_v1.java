package sklep.alternatywne_wersje_bazy_danych;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController_v1 {
	
	@RequestMapping(path="/alt1", produces="text/plain")
	@ResponseBody
	public String wszystkieProdukty() {
		StringBuilder sb = new StringBuilder();
		
		try(Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sklep", "kurs", "abc123");
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM products ORDER BY product_id")) {
			
			while(rs.next()) {
				sb.append("* produkt ")
					.append(rs.getString("product_name"))
					.append(" za cenę ")
					.append(rs.getBigDecimal("price"))
					.append('\n');
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			sb.append("Błąd: " + e);
		}
		return sb.toString();
	}

}
