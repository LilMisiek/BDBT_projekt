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
                @RequestParam(required = false) List<String> selectedLinie,
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


            if (!tramwajeDAO.existsById(nrTramwaju)) {
                model.addAttribute("errorMessage", "Tramwaj o numerze " + nrTramwaju + " nie istnieje.");
                reloadFormData(model);
                return "admin/add_odjazd";
            }


            if (tramwajeDAO.isTramAssigned(nrTramwaju)) {
                model.addAttribute("odjazd", odjazd);
                model.addAttribute("tramwajAssigned", true);
                model.addAttribute("errorMessage", "Tramwaj " + nrTramwaju + " jest już przypisany do linii " + tramwajeDAO.getNrLiniiById(nrTramwaju) + ".");
                reloadFormData(model);
                return "admin/confirm_add_odjazd";
            }


            String nrLiniiTramwaju = tramwajeDAO.getNrLiniiById(nrTramwaju);
            if (nrLiniiTramwaju != null && !nrLiniiTramwaju.equals(odjazd.getNrLinii())) {
                model.addAttribute("errorMessage",
                        "Wybrany tramwaj (" + nrTramwaju + ") jest przypisany do linii " + nrLiniiTramwaju
                                + ", a próbujesz dodać go do linii " + odjazd.getNrLinii());
                reloadFormData(model);
                model.addAttribute("odjazd", odjazd);
                return "admin/add_odjazd";
            }


            odjazdyDAO.save(odjazd);
            return "redirect:/rozklad_admin";
        }

        @PostMapping("/admin/confirm_add_odjazd")
        public String confirmAddOdjazd(@ModelAttribute("odjazd") Odjazdy odjazd,
                                       @RequestParam("confirm") boolean confirm,
                                       Model model) {
            if (confirm) {
                odjazdyDAO.save(odjazd);
                return "redirect:/rozklad_admin";
            } else {
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
                model.addAttribute("successMessage", "Odjazd został pomyślnie usunięty.");
            } catch (Exception e) {
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
            p.setNrAdresu(1);
            p.setNrPrzedsiebiorstwa(1);

            if (przystankiDAO.existsByNazwaPrzystanku(p.getNazwaPrzystanku())) {
                model.addAttribute("errorMessage",
                        "Przystanek o nazwie '" + p.getNazwaPrzystanku() + "' już istnieje!");
                return "admin/add_przystanek";
            }

            przystankiDAO.save(p);

            return "redirect:/rozklad_admin";
        }

        // ==================== BILETY ==================
        @GetMapping("/bilety_admin")
        public String showAllTickets(Model model) {
            List<Bilety> biletyList = biletydao.list();

            model.addAttribute("listBilety", biletyList);

            return "admin/bilety_admin";
        }




        private String calculateTicketDuration(String nazwa) {
            switch (nazwa) {
                case "Jednorazowy":
                    return "20";
                case "Godzinny":
                    return "60";
                case "Całodniowy":
                    return "24H";
                case "Trzydniowy":
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
            if (bilet.getImie() == null || bilet.getImie().trim().isEmpty()) {
                model.addAttribute("errorMessage", "Pole 'Imię' jest wymagane.");
                return "user/bilety_user";
            }

            if (bilet.getNazwisko() == null || bilet.getNazwisko().trim().isEmpty()) {
                model.addAttribute("errorMessage", "Pole 'Nazwisko' jest wymagane.");
                return "user/bilety_user";
            }

            bilet.setNrPrzedsiebiorstwa(1);
            bilet.setCena(calculateTicketPrice(bilet.getNazwa(), bilet.getRodzaj()));
            bilet.setCzasWaznosci(calculateTicketDuration(bilet.getNazwa()));

            biletydao.save(bilet);

            int nrBiletu = biletydao.getLastInsertedId();

            return "redirect:/user/confirmation?nrBiletu=" + nrBiletu;
        }

        @GetMapping("/user/confirmation")
        public String showConfirmation(@RequestParam("nrBiletu") int nrBiletu, Model model) {
            Bilety bilet = biletydao.get(nrBiletu);
            model.addAttribute("bilet", bilet);
            return "user/confirmation";
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
                case "Trzydniowy":
                    price = 25.00f;
                    break;
                default:
                    throw new IllegalArgumentException("Nieznana nazwa biletu: " + nazwa);
            }

            if ("ulgowy".equalsIgnoreCase(rodzaj)) {
                price /= 2;
            }

            return price;
        }



        // =================== LINIE ===================
        @GetMapping("/admin/add_linie")
        public String showAddLinieForm(Model model) {
            Linie nowaLinia = new Linie();
            nowaLinia.setNrPrzedsiebiorstwa(1);

            model.addAttribute("linia", nowaLinia);
            return "admin/add_linie"; 
        }

        @PostMapping("/admin/add_linie")
        public String saveNewLinie(@ModelAttribute("linia") Linie linia,
                                   Model model) {
            linia.setNrPrzedsiebiorstwa(1);

            if (linieDAO.existsByNrLinii(linia.getNrLinii())) {
                model.addAttribute("errorMessage",
                        "Linia o numerze '" + linia.getNrLinii() + "' już istnieje!");
                return "admin/add_linie";
            }

            linieDAO.save(linia);
            return "redirect:/rozklad_admin";
        }

        @PostMapping("/rozklad_admin")
        public String filterRozkladAdmin(
                @RequestParam(required = false) List<String> selectedLinie,
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
            Odjazdy odjazd = odjazdyDAO.getById(nrOdjazdu);

            List<Tramwaje> tramwaje = tramwajeDAO.list();
            List<Przystanki> przystanki = przystankiDAO.list();
            List<Linie> linie = linieDAO.list();

            model.addAttribute("odjazd", odjazd);
            model.addAttribute("tramwaje", tramwaje);
            model.addAttribute("przystanki", przystanki);
            model.addAttribute("linie", linie);

            return "admin/edit_rozklad";
        }

        @GetMapping("/admin/edit/{nrOdjazdu}")
        public String showEditForm(@PathVariable("nrOdjazdu") Integer nrOdjazdu, Model model) {
            Odjazdy odjazd = odjazdyDAO.getById(nrOdjazdu);

            List<Linie> linie = linieDAO.list();

            model.addAttribute("odjazd", odjazd);
            model.addAttribute("linie", linie);

            return "admin/edit_rozklad";
        }



        @PostMapping("/admin/editRozklad")
        public String updateOdjazd(@ModelAttribute("odjazd") Odjazdy odjazd) {
            odjazdyDAO.update(odjazd);

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
