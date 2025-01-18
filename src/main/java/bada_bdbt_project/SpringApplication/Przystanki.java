package bada_bdbt_project.SpringApplication;

public class Przystanki {

    private int nrPrzystanku;
    private String nazwaPrzystanku;
    private int nrPrzedsiebiorstwa;
    private int nrAdresu;

    public Przystanki(int nrPrzystanku, int nrAdresu, int nrPrzedsiebiorstwa, String nazwaPrzystanku) {
        this.nrPrzystanku = nrPrzystanku;
        this.nrAdresu = nrAdresu;
        this.nrPrzedsiebiorstwa = nrPrzedsiebiorstwa;
        this.nazwaPrzystanku = nazwaPrzystanku;
    }

    public Przystanki() {
    }

    public int getNrPrzystanku() {
        return nrPrzystanku;
    }

    public void setNrPrzystanku(int nrPrzystanku) {
        this.nrPrzystanku = nrPrzystanku;
    }

    public String getNazwaPrzystanku() {
        return nazwaPrzystanku;
    }

    public void setNazwaPrzystanku(String nazwaPrzystanku) {
        this.nazwaPrzystanku = nazwaPrzystanku;
    }

    public int getNrPrzedsiebiorstwa() {
        return nrPrzedsiebiorstwa;
    }

    public void setNrPrzedsiebiorstwa(int nrPrzedsiebiorstwa) {
        this.nrPrzedsiebiorstwa = nrPrzedsiebiorstwa;
    }

    public int getNrAdresu() {
        return nrAdresu;
    }

    public void setNrAdresu(int nrAdresu) {
        this.nrAdresu = nrAdresu;
    }

    @Override
    public String toString() {
        return "Przystanki{" +
                "nrPrzystanku=" + nrPrzystanku +
                ", nazwaPrzystanku='" + nazwaPrzystanku + '\'' +
                ", nrPrzedsiebiorstwa=" + nrPrzedsiebiorstwa +
                ", nrAdresu=" + nrAdresu +
                '}';
    }
}
