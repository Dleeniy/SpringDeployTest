package sklep.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WhoAmI {

	@GetMapping("/whoami")
	public String whoAmI(Authentication authentication, Model model) {
    	if(authentication != null && authentication.isAuthenticated()) {
        	model.addAttribute("userName", authentication.getName());
        	model.addAttribute("authorities", authentication.getAuthorities());
    	}
    	return "whoami";
	}
}



