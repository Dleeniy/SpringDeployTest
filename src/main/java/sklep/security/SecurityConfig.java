package sklep.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Autowired
	private DataSource dataSource;

	// W klasie typu Configuration umieszczamy metody, które w wyniku zwracają
	// obiekty, które dla Springa coś specjalnego znaczą.
	// Te metody oznaczamy adnotacją @Bean.
	// (w ten sposób można też stworzyć "własne" beany, zamiast podejścia z
	// adnotacją @Component)

	// Ta metoda służy ogólnej konfiguracji zabezpieczeń aplikacji webowej.
	// M.in. podaje się tutaj reguły dostępu, czyli autoryzacje.
	// Tutaj też podaje się sposób uwierzytelnienia, np. formLogin.
	@Bean
	SecurityFilterChain setHttpSecurity(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests()
				// .antMatchers(HttpMethod.POST).authenticated()
				.antMatchers("/", "/whoami", "/*.css").permitAll().antMatchers("/hello", "/time").permitAll()
				.antMatchers("/alt?/**").authenticated() // zalogowany jako ktokolwiek
				.antMatchers("/products/new", "/products/*/edit").hasAuthority("ROLE_manager")
				.antMatchers("/products/**").permitAll().antMatchers("/customers/new", "/customers/*/edit")
				.hasAuthority("ROLE_manager").antMatchers("/customers/**").authenticated().anyRequest().denyAll().and()
				.formLogin();
		return httpSecurity.build();
	}

	// W osobnej metodzie określa się źródło wiedzy o użytkownikach: jacy są
	// użytkownicy, jakie mają hasła.
	// Są różne rodzaje takich źródeł: inMemory - użytkownicy wpisani na sztywno w
	// kodzie; jdbc - w bazie danych; ldap - w zewnętrznym systemie; można
	// zaimplementować samemu
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration,
			ApplicationContext applicationContext) throws Exception {
		ObjectPostProcessor<Object> objectPostProcessor = new ObjectPostProcessor<Object>() {
			public <O> O postProcess(O object) {
				return object;
			}
		};

		return authenticationConfiguration.authenticationManagerBuilder(objectPostProcessor, applicationContext)
				.jdbcAuthentication().dataSource(dataSource)
				// mamy podać zapytanie SQL, które pozwoli Springowi odczytać informacje o
				// userze na podstawie nazwy usera
				// w wyniku ma zwrócić rekord z trzeba kolumnami: nazwa, hasło, czy aktywny
				// (0/1)
				.usersByUsernameQuery("SELECT username, password, enabled FROM spring_accounts WHERE username = ?")
				// dla użytkownika zwraca info o uprawnieniach (rolach) danego użytkownika;
				// wynik może składać się z wielu rekordów
				.authoritiesByUsernameQuery("SELECT username, role FROM spring_account_roles WHERE username = ?").and()
				.build();
	}

}

/*
 * .inMemoryAuthentication()
 * .withUser("ala").password("{noop}abc123").roles("manager", "worker").and()
 * .withUser("ola").password("{noop}abc123").roles("worker").and()
 */
