package sklep.basket;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasketConfiguration {

    @Bean
    public HttpSessionListener getSessionListener() {
   	 return new HttpSessionListener() {
   		 @Override
   		 public void sessionCreated(HttpSessionEvent se) {
   			 HttpSession session = se.getSession();
   			 session.setAttribute("basket", new Basket());
   		 }
   	 };
    }

}



