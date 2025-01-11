package bada_bdbt_project.SpringApplication;

public class Uklad_linii {
    public Uklad_linii(int nr_linii, int nr_przystanku) {
        Nr_linii = nr_linii;
        Nr_przystanku = nr_przystanku;
    }

    public Uklad_linii() {
    }

    private int Nr_linii;
    private int Nr_przystanku;

    public int getNr_linii() {
        return Nr_linii;
    }

    public void setNr_linii(int nr_linii) {
        Nr_linii = nr_linii;
    }

    public int getNr_przystanku() {
        return Nr_przystanku;
    }

    public void setNr_przystanku(int nr_przystanku) {
        Nr_przystanku = nr_przystanku;
    }

    @Override
    public String toString() {
        return "Uklad_linii{" +
                "Nr_linii=" + Nr_linii +
                ", Nr_przystanku=" + Nr_przystanku +
                '}';
    }
}
