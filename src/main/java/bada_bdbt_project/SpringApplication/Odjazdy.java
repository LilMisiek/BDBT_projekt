package bada_bdbt_project.SpringApplication;

import java.time.LocalDateTime;

public class Odjazdy {

    private Integer nrOdjazdu;
    private String nrLinii; // Zgodnie z tabelą Linie
    private String czyNocna;
    private Integer nrPrzystanku;
    private String nazwaPrzystanku;
    private LocalDateTime godzina;
    private String czyNaZadanie;

    // Konstruktor bezparametrowy
    public Odjazdy() {
    }

    // Konstruktor z parametrami
    public Odjazdy(Integer nrOdjazdu, String nrLinii, String czyNocna, Integer nrPrzystanku, String nazwaPrzystanku, LocalDateTime godzina, String czyNaZadanie) {
        this.nrOdjazdu = nrOdjazdu;
        this.nrLinii = nrLinii;
        this.czyNocna = czyNocna;
        this.nrPrzystanku = nrPrzystanku;
        this.nazwaPrzystanku = nazwaPrzystanku;
        this.godzina = godzina;
        this.czyNaZadanie = czyNaZadanie;
    }

    // Gettery i Settery

    public Integer getNrOdjazdu() {
        return nrOdjazdu;
    }

    public void setNrOdjazdu(Integer nrOdjazdu) {
        this.nrOdjazdu = nrOdjazdu;
    }

    public String getNrLinii() {
        return nrLinii;
    }

    public void setNrLinii(String nrLinii) {
        this.nrLinii = nrLinii;
    }

    public String getCzyNocna() {
        return czyNocna;
    }

    public void setCzyNocna(String czyNocna) {
        this.czyNocna = czyNocna;
    }

    public Integer getNrPrzystanku() {
        return nrPrzystanku;
    }

    public void setNrPrzystanku(Integer nrPrzystanku) {
        this.nrPrzystanku = nrPrzystanku;
    }

    public String getNazwaPrzystanku() {
        return nazwaPrzystanku;
    }

    public void setNazwaPrzystanku(String nazwaPrzystanku) {
        this.nazwaPrzystanku = nazwaPrzystanku;
    }

    public LocalDateTime getGodzina() {
        return godzina;
    }

    public void setGodzina(LocalDateTime godzina) {
        this.godzina = godzina;
    }

    public String getCzyNaZadanie() {
        return czyNaZadanie;
    }

    public void setCzyNaZadanie(String czyNaZadanie) {
        this.czyNaZadanie = czyNaZadanie;
    }

    // Metoda toString
    @Override
    public String toString() {
        return "Odjazdy{" +
                "nrOdjazdu=" + nrOdjazdu +
                ", nrLinii='" + nrLinii + '\'' +
                ", czyNocna='" + czyNocna + '\'' +
                ", nrPrzystanku=" + nrPrzystanku +
                ", nazwaPrzystanku='" + nazwaPrzystanku + '\'' +
                ", godzina=" + godzina +
                ", czyNaZadanie='" + czyNaZadanie + '\'' +
                '}';
    }
}
