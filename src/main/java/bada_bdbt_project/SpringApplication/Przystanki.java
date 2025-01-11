package bada_bdbt_project.SpringApplication;

public class Przystanki {

    public Przystanki(int nr_przystanku, int nr_adresu, int nr_przedsiebiorstwa, String nazwa_przystanku) {
        Nr_przystanku = nr_przystanku;
        Nr_adresu = nr_adresu;
        Nr_przedsiebiorstwa = nr_przedsiebiorstwa;
        Nazwa_przystanku = nazwa_przystanku;
    }

    public Przystanki() {
    }

    private int Nr_przystanku;
    private String Nazwa_przystanku;
    private int Nr_przedsiebiorstwa;
    private int Nr_adresu;

    public int getNr_przystanku() {
        return Nr_przystanku;
    }

    public void setNr_przystanku(int nr_przystanku) {
        Nr_przystanku = nr_przystanku;
    }

    public int getNr_adresu() {
        return Nr_adresu;
    }

    public void setNr_adresu(int nr_adresu) {
        Nr_adresu = nr_adresu;
    }

    public int getNr_przedsiebiorstwa() {
        return Nr_przedsiebiorstwa;
    }

    public void setNr_przedsiebiorstwa(int nr_przedsiebiorstwa) {
        Nr_przedsiebiorstwa = nr_przedsiebiorstwa;
    }

    public String getNazwa_przystanku() {
        return Nazwa_przystanku;
    }

    public void setNazwa_przystanku(String nazwa_przystanku) {
        Nazwa_przystanku = nazwa_przystanku;
    }

    @Override
    public String toString() {
        return "Przystanki{" +
                "Nr_przystanku=" + Nr_przystanku +
                ", Nazwa_przystanku='" + Nazwa_przystanku + '\'' +
                ", Nr_przedsiebiorstwa=" + Nr_przedsiebiorstwa +
                ", Nr_adresu=" + Nr_adresu +
                '}';
    }
}
