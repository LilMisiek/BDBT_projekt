package bada_bdbt_project.SpringApplication;

import java.time.LocalTime;

public class Odjazdy {

    private Integer nrOdjazdu;
    private String nrLinii;
    private String czyNocna;
    private Integer nrPrzystanku;
    private String nazwaPrzystanku;
    private LocalTime godzina;
    private String czyNaZadanie;
    private int nrTramwaju;
    public Odjazdy() {
    }

    public Odjazdy(Integer nrOdjazdu, String nrLinii, String czyNocna, Integer nrPrzystanku,
                   String nazwaPrzystanku, LocalTime godzina, String czyNaZadanie, int nrTramwaju) {
        this.nrOdjazdu = nrOdjazdu;
        this.nrLinii = nrLinii;
        this.czyNocna = czyNocna;
        this.nrPrzystanku = nrPrzystanku;
        this.nazwaPrzystanku = nazwaPrzystanku;
        this.godzina = godzina;
        this.czyNaZadanie = czyNaZadanie;
        this.nrTramwaju = nrTramwaju;
    }


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

    public LocalTime getGodzina() {
        return godzina;
    }

    public void setGodzina(LocalTime godzina) {
        this.godzina = godzina;
    }

    public String getCzyNaZadanie() {
        return czyNaZadanie;
    }

    public void setCzyNaZadanie(String czyNaZadanie) {
        this.czyNaZadanie = czyNaZadanie;
    }

    public int getNrTramwaju() {
        return nrTramwaju;
    }

    public void setNrTramwaju(int nrTramwaju) {
        this.nrTramwaju = nrTramwaju;
    }

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
                ", nrTramwaju=" + nrTramwaju +
                '}';
    }
}
