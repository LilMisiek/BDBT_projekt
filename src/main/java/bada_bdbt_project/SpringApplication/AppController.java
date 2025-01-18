package bada_bdbt_project.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Configuration
public class AppController implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/bilety").setViewName("bilety");
        registry.addViewController("/rozklad").setViewName("rozklad");


        registry.addViewController("/main_admin").setViewName("admin/main_admin");
        registry.addViewController("/index_admin").setViewName("admin/index_admin");
        registry.addViewController("/bilety_admin").setViewName("admin/bilety_admin");
        registry.addViewController("/rozklad_admin").setViewName("admin/rozklad_admin");
        registry.addViewController("/main_user").setViewName("user/main_user");
        registry.addViewController("/index_user").setViewName("user/index_user");
        registry.addViewController("/bilety_user").setViewName("user/bilety_user");
        registry.addViewController("/rozklad_user").setViewName("user/rozklad_user");

    }

    @Controller
    public class DashboardController
    {

        @Autowired
        private LinieDAO linieDAO;

        @Autowired
        private PrzystankiDAO przystankiDAO;

        @Autowired
        private OdjazdyDAO odjazdyDAO;

        @Autowired
        private BiletyDAO biletydao;

        // Wyświetlanie rozkładu z możliwością filtrowania
        @GetMapping({"/rozklad","/rozklad_user"})
        public String showRozkladForm(Model model, HttpServletRequest request) {
            List<Linie> linie = linieDAO.list();
            List<Przystanki> przystanki = przystankiDAO.list();
            List<Odjazdy> odjazdyList = odjazdyDAO.findAll();

            model.addAttribute("linie", linie);
            model.addAttribute("przystanki", przystanki);
            model.addAttribute("odjazdyList", odjazdyList);
            String uri = request.getRequestURI();
            if (uri.endsWith("rozklad_user")) {
                return "user/rozklad_user";
            }

            return "rozklad";
        }


        @PostMapping({"/rozklad","/rozklad_user"})
        public String filterRozklad(
                @RequestParam(required = false) List<String> selectedLinie, // Zmieniono na List<String>
                @RequestParam(required = false) List<Integer> selectedPrzystanki,
                Model model, HttpServletRequest request) {

            List<Odjazdy> odjazdyList = odjazdyDAO.findRozklad(selectedLinie, selectedPrzystanki);

            List<Linie> linie = linieDAO.list();
            List<Przystanki> przystanki = przystankiDAO.list();

            model.addAttribute("linie", linie);
            model.addAttribute("przystanki", przystanki);
            model.addAttribute("odjazdyList", odjazdyList);
            model.addAttribute("selectedLinie", selectedLinie);
            model.addAttribute("selectedPrzystanki", selectedPrzystanki);
            String uri = request.getRequestURI();
            if (uri.endsWith("rozklad_user")) {
                return "user/rozklad_user";
            }

            return "rozklad";
        }

        @RequestMapping
                ("/main"
                )
        public String defaultAfterLogin
                (HttpServletRequest request) {
            if
            (request.isUserInRole
                    ("ADMIN")) {
                return "redirect:/main_admin";
            }
            else if
            (request.isUserInRole
                            ("USER")) {
                return "redirect:/main_user";
            }
            else
            {
                return "redirect:/index";
            }
        }
    }

    @RequestMapping(value={"/main_admin"})
    public String showAdminPage(Model model) {
        return "admin/main_admin";
    }
    @RequestMapping(value={"/main_user"})
    public String showUserPage(Model model) {
        return "user/main_user";
    }
}
