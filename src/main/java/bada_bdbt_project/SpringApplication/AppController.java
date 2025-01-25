package bada_bdbt_project.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

        @Autowired
        private TramwajeDAO tramwajeDAO;

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


        @GetMapping("/rozklad_admin")
        public String showRozkladAdminForm(Model model) {
            // pobranie danych
            List<Linie> linie = linieDAO.list();
            List<Przystanki> przystanki = przystankiDAO.list();
            List<Odjazdy> odjazdyList = odjazdyDAO.findAll();

            // przekazanie do modelu
            model.addAttribute("linie", linie);
            model.addAttribute("przystanki", przystanki);
            model.addAttribute("odjazdyList", odjazdyList);

            // widok admina: admin/rozklad_admin.html
            return "admin/rozklad_admin";
        }

        @GetMapping("/admin/add_odjazd")
        public String showAddOdjazdForm(Model model) {
            Odjazdy nowyOdjazd = new Odjazdy();

            // zamiast pobierać TYLKO tramwaje z nie-null nr_linii,
            // pobierz WSZYSTKIE tramwaje
            List<Tramwaje> tramwaje = tramwajeDAO.list();
            List<Przystanki> przystanki = przystankiDAO.list();
            List<Linie> linie = linieDAO.list();

            model.addAttribute("odjazd", nowyOdjazd);
            model.addAttribute("tramwaje", tramwaje);
            model.addAttribute("przystanki", przystanki);
            model.addAttribute("linie", linie);

            return "admin/add_odjazd";
        }

        @PostMapping("/admin/add_odjazd")
        public String saveNewOdjazd(@ModelAttribute("odjazd") Odjazdy odjazd,
                                    Model model) {

            // 1) Pobierz info o tramwaju
            Tramwaje tram = tramwajeDAO.getById(odjazd.getNrTramwaju());

            // 2) Sprawdź, czy tramwaj jest już przypisany do jakiejś linii
            if (tramwajeDAO.isTramAssigned(odjazd.getNrTramwaju())) {
                // 2A) Jeśli tak, wyświetlamy komunikat w widoku
                model.addAttribute("errorMessage", "Tramwaj " + tram.getNrTramwaju()
                        + " jest już przypisany do linii " + tram.getNrLinii());
                // Wczytujemy listy, żeby formularz się poprawnie wyświetlił
                model.addAttribute("tramwaje", tramwajeDAO.list());
                model.addAttribute("przystanki", przystankiDAO.list());
                model.addAttribute("linie", linieDAO.list());
                // Zachowujemy wartości wpisane przez użytkownika
                model.addAttribute("odjazd", odjazd);

                return "admin/add_odjazd";
            }

            // 3) Sprawdź, czy numer linii tramwaju z bazy != odjazd.nrLinii
            if (tram.getNrLinii() != null && !tram.getNrLinii().equals(odjazd.getNrLinii())) {
                // 3A) Komunikat, że to inna linia...
                model.addAttribute("errorMessage",
                        "Wybrany tramwaj (" + tram.getNrTramwaju() + ") jest przypisany do linii "
                                + tram.getNrLinii() + ", a próbujesz dodać go do linii " + odjazd.getNrLinii()
                );
                model.addAttribute("tramwaje", tramwajeDAO.list());
                model.addAttribute("linie", linieDAO.list());
                model.addAttribute("odjazd", odjazd);
                return "admin/add_odjazd";
            }

            // 4) Jeśli OK, to zapisujemy nowy odjazd
            odjazdyDAO.save(odjazd);
            return "redirect:/rozklad_admin";
        }



        // =================== PRZYSTANKI ===================
        @GetMapping("/admin/add_przystanek")
        public String showAddPrzystanekForm(Model model) {
            Przystanki p = new Przystanki();  // pusty obiekt
            model.addAttribute("przystanek", p);
            return "admin/add_przystanek";
        }

        @PostMapping("/admin/add_przystanek")
        public String saveNewPrzystanek(@ModelAttribute("przystanek") Przystanki p, Model model) {
            // Ustawiamy na sztywno nrAdresu = 1 i nrPrzedsiebiorstwa = 1
            p.setNrAdresu(1);
            p.setNrPrzedsiebiorstwa(1);

            // Sprawdzenie, czy już istnieje w bazie
            if (przystankiDAO.existsByNazwaPrzystanku(p.getNazwaPrzystanku())) {
                // Wstawienie komunikatu błędu do modelu
                model.addAttribute("errorMessage",
                        "Przystanek o nazwie '" + p.getNazwaPrzystanku() + "' już istnieje!");
                // Ponownie wyświetlamy formularz
                return "admin/add_przystanek";
            }

            // Zapis
            przystankiDAO.save(p);

            // Powrót do listy odjazdów / przystanków
            return "redirect:/rozklad_admin";
        }

        // =================== LINIE ===================
        @GetMapping("/admin/add_linie")
        public String showAddLinieForm(Model model) {
            // Tworzymy pusty obiekt Linie, ustawiamy domyślne wartości
            Linie nowaLinia = new Linie();
            // nrPrzedsiebiorstwa zawsze 1, więc można to ustawić z góry
            nowaLinia.setNrPrzedsiebiorstwa(1);

            model.addAttribute("linia", nowaLinia);
            return "admin/add_linie";  // nazwa pliku w templates/admin/add_linie.html
        }

        @PostMapping("/admin/add_linie")
        public String saveNewLinie(@ModelAttribute("linia") Linie linia,
                                   Model model) {
            // Upewniamy się, że nrPrzedsiebiorstwa = 1
            linia.setNrPrzedsiebiorstwa(1);

            // Sprawdzamy, czy w bazie nie istnieje już linia o takim nr_linii
            if (linieDAO.existsByNrLinii(linia.getNrLinii())) {
                // Jeśli istnieje, to wracamy do formularza z komunikatem błędu
                model.addAttribute("errorMessage",
                        "Linia o numerze '" + linia.getNrLinii() + "' już istnieje!");
                return "admin/add_linie";
            }

            // Jeśli nie istnieje, to zapisujemy w bazie
            linieDAO.save(linia);
            // Po zapisaniu przekierowanie do /rozklad_admin
            return "redirect:/rozklad_admin";
        }

        @PostMapping("/rozklad_admin")
        public String filterRozkladAdmin(
                @RequestParam(required = false) List<String> selectedLinie,
                @RequestParam(required = false) List<Integer> selectedPrzystanki,
                Model model) {

            // filtruj
            List<Odjazdy> odjazdyList = odjazdyDAO.findRozklad(selectedLinie, selectedPrzystanki);

            // pobierz ponownie listę linii, przystanków
            List<Linie> linie = linieDAO.list();
            List<Przystanki> przystanki = przystankiDAO.list();

            // dodaj do modelu
            model.addAttribute("linie", linie);
            model.addAttribute("przystanki", przystanki);
            model.addAttribute("odjazdyList", odjazdyList);

            model.addAttribute("selectedLinie", selectedLinie);
            model.addAttribute("selectedPrzystanki", selectedPrzystanki);

            // wróć do widoku admina
            return "admin/rozklad_admin";
        }

        @GetMapping("/admin/editRozklad/{nrOdjazdu}")
        public String showEditOdjazdForm(@PathVariable("nrOdjazdu") int nrOdjazdu, Model model) {
            // 1) Pobierz z bazy danych obiekt Odjazdy
            Odjazdy odjazd = odjazdyDAO.getById(nrOdjazdu);

            // 2) Pobierz listy tramwajów, przystanków i linii
            List<Tramwaje> tramwaje = tramwajeDAO.list();
            List<Przystanki> przystanki = przystankiDAO.list();
            List<Linie> linie = linieDAO.list();

            // 3) Dodaj wszystko do modelu
            model.addAttribute("odjazd", odjazd);
            model.addAttribute("tramwaje", tramwaje);
            model.addAttribute("przystanki", przystanki);
            model.addAttribute("linie", linie);

            // 4) Zwróć widok
            return "admin/edit_rozklad";
        }

        @GetMapping("/admin/edit/{nrOdjazdu}")
        public String showEditForm(@PathVariable("nrOdjazdu") Integer nrOdjazdu, Model model) {
            // 1. Znajdź w bazie odjazd o danym nrOdjazdu
            Odjazdy odjazd = odjazdyDAO.getById(nrOdjazdu);

            // 2. Pobierz listę wszystkich linii (Linie) z bazy
            List<Linie> linie = linieDAO.list();

            // 3. Przekaż obiekt odjazd i listę linii do modelu
            model.addAttribute("odjazd", odjazd);
            model.addAttribute("linie", linie);

            return "admin/edit_rozklad";
        }



        @PostMapping("/admin/editRozklad")
        public String updateOdjazd(@ModelAttribute("odjazd") Odjazdy odjazd) {
            // Metoda update w DAO zaktualizuje rekord w bazie na podstawie danych z formularza
            odjazdyDAO.update(odjazd);

            // Po zapisaniu przekieruj do /rozklad_admin (lub innej strony), aby zobaczyć listę
            return "redirect:/rozklad_admin";
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
