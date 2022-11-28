package sklep.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RootController {
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "Hello Spring";
	}
	
	@RequestMapping(path="/time", produces="text/plain")
	public String ktoraGodzina(Model model) {
		LocalDateTime dt = LocalDateTime.now();
		model.addAttribute("dt", dt);
		return "wyswietl_czas";
        // Pliki JSP umieszczamy w projekcie w src/main/webapp a nie w templates
        // Tu docelowo będzie to /WEB-INF/templates :-)
        // ale nie musimy pisać pełnej ścieżki w return, bo mamy to skonfigurowane w prefix/suffix
	}

}
