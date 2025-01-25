package bada_bdbt_project.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
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
            List<Linie> linie = linieDAO.list();
            List<Przystanki> przystanki = przystankiDAO.list();
            List<Odjazdy> odjazdyList = odjazdyDAO.findAll();

            model.addAttribute("linie", linie);
            model.addAttribute("przystanki", przystanki);
            model.addAttribute("odjazdyList", odjazdyList);
            model.addAttribute("selectedLinie", new ArrayList<String>());
            model.addAttribute("selectedPrzystanki", new ArrayList<Integer>());

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

            int nrTramwaju = odjazd.getNrTramwaju();

            // 1. Check if the tram exists
            if (!tramwajeDAO.existsById(nrTramwaju)) {
                model.addAttribute("errorMessage", "Tramwaj o numerze " + nrTramwaju + " nie istnieje.");
                reloadFormData(model);
                return "admin/add_odjazd";
            }

            // 2. Check if the tram is already assigned to a line
            if (tramwajeDAO.isTramAssigned(nrTramwaju)) {
                // Przekazujemy dane do potwierdzenia
                model.addAttribute("odjazd", odjazd);
                model.addAttribute("tramwajAssigned", true);
                model.addAttribute("errorMessage", "Tramwaj " + nrTramwaju + " jest już przypisany do linii " + tramwajeDAO.getNrLiniiById(nrTramwaju) + ".");
                reloadFormData(model);
                return "admin/confirm_add_odjazd"; // Nowy szablon potwierdzenia
            }

            // 3. Check if the line number matches the tram's line
            String nrLiniiTramwaju = tramwajeDAO.getNrLiniiById(nrTramwaju);
            if (nrLiniiTramwaju != null && !nrLiniiTramwaju.equals(odjazd.getNrLinii())) {
                model.addAttribute("errorMessage",
                        "Wybrany tramwaj (" + nrTramwaju + ") jest przypisany do linii " + nrLiniiTramwaju
                                + ", a próbujesz dodać go do linii " + odjazd.getNrLinii());
                reloadFormData(model);
                model.addAttribute("odjazd", odjazd); // Preserve user input
                return "admin/add_odjazd";
            }

            // 4. Save the new departure
            odjazdyDAO.save(odjazd);
            return "redirect:/rozklad_admin";
        }

        @PostMapping("/admin/confirm_add_odjazd")
        public String confirmAddOdjazd(@ModelAttribute("odjazd") Odjazdy odjazd,
                                       @RequestParam("confirm") boolean confirm,
                                       Model model) {
            if (confirm) {
                // Użytkownik potwierdził przypisanie tramwaju
                odjazdyDAO.save(odjazd);
                return "redirect:/rozklad_admin";
            } else {
                // Użytkownik anulował przypisanie tramwaju
                model.addAttribute("message", "Operacja została anulowana.");
                reloadFormData(model);
                return "admin/add_odjazd";
            }
        }

        private void reloadFormData(Model model) {
            model.addAttribute("tramwaje", tramwajeDAO.list());
            model.addAttribute("przystanki", przystankiDAO.list());
            model.addAttribute("linie", linieDAO.list());
        }

        @PostMapping("/admin/delete_odjazd/{nrOdjazdu}")
        public String deleteOdjazd(@PathVariable("nrOdjazdu") int nrOdjazdu, Model model) {
            try {
                odjazdyDAO.delete(nrOdjazdu);
                // Opcjonalnie: Dodaj komunikat sukcesu
                model.addAttribute("successMessage", "Odjazd został pomyślnie usunięty.");
            } catch (Exception e) {
                // Obsłuż błąd, np. dodaj komunikat błędu
                model.addAttribute("errorMessage", "Wystąpił błąd podczas usuwania odjazdu.");
            }
            return "redirect:/rozklad_admin";
        }

        @GetMapping("/admin/confirm_delete_odjazd/{nrOdjazdu}")
        public String confirmDeleteOdjazd(@PathVariable("nrOdjazdu") int nrOdjazdu, Model model) {
            Odjazdy odjazd = odjazdyDAO.getById(nrOdjazdu);
            model.addAttribute("odjazd", odjazd);
            return "admin/confirm_delete_odjazd";
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

        // ==================== BILETY ==================

        private String calculateTicketDuration(String nazwa) {
            switch (nazwa) {
                case "Jednorazowy":
                    return "20 minut";
                case "Godzinny":
                    return "60 minut";
                case "Całodniowy":
                    return "24H";
                case "Weekendowy":
                    return "72H";
                default:
                    throw new IllegalArgumentException("Nieznana nazwa biletu: " + nazwa);
            }
        }

        @GetMapping("user/bilety_user")
        public String showBuyTicketForm(Model model) {
            model.addAttribute("bilet", new Bilety());
            return "user/bilety_user";
        }


        @PostMapping("user/bilety_user")
        public String saveTicket(@ModelAttribute("bilet") Bilety bilet, Model model) {
            try {
                bilet.setNrBiletu((int) (Math.random() * 100000));
                bilet.setNrPrzedsiebiorstwa(1);

                // Ustawienie ceny i czasu ważności na backendzie
                float calculatedPrice = calculateTicketPrice(bilet.getNazwa(), bilet.getRodzaj());
                String calculatedDuration = calculateTicketDuration(bilet.getNazwa());
                bilet.setCena(calculatedPrice);
                bilet.setCzasWaznosci(calculatedDuration);

                biletydao.save(bilet);

                model.addAttribute("successMessage", "Bilet został pomyślnie zakupiony! Cena: " + calculatedPrice + " zł");
            } catch (Exception e) {
                model.addAttribute("errorMessage", "Wystąpił błąd podczas zakupu biletu.");
            }
            return "user/bilety_user";
        }


        private float calculateTicketPrice(String nazwa, String rodzaj) {
            float price;

            switch (nazwa) {
                case "Jednorazowy":
                    price = 2.20f;
                    break;
                case "Godzinny":
                    price = 4.40f;
                    break;
                case "Całodniowy":
                    price = 12.00f;
                    break;
                case "Weekendowy":
                    price = 25.00f;
                    break;
                default:
                    throw new IllegalArgumentException("Nieznana nazwa biletu: " + nazwa);
            }

            if ("ulgowy".equalsIgnoreCase(rodzaj)) {
                price /= 2; // Bilety ulgowe są o połowę tańsze
            }

            return price;
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
