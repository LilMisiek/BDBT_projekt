package bada_bdbt_project.SpringApplication;

public class Linie
{

    public Linie(int nr_linii, int nr_przedsiebiorstwa, String czy_nocna, int liczba_przystankow) {
        Nr_linii = nr_linii;
        Nr_przedsiebiorstwa = nr_przedsiebiorstwa;
        Czy_nocna = czy_nocna;
        Liczba_przystankow = liczba_przystankow;
    }

    public Linie() {
    }

    private int Nr_linii;
    private int Liczba_przystankow;
    private String Czy_nocna;
    private int Nr_przedsiebiorstwa;

    public int getNr_linii() {
        return Nr_linii;
    }

    public void setNr_linii(int nr_linii) {
        Nr_linii = nr_linii;
    }

    public int getNr_przedsiebiorstwa() {
        return Nr_przedsiebiorstwa;
    }

    public void setNr_przedsiebiorstwa(int nr_przedsiebiorstwa) {
        Nr_przedsiebiorstwa = nr_przedsiebiorstwa;
    }

    public String getCzy_nocna() {
        return Czy_nocna;
    }

    public void setCzy_nocna(String czy_nocna) {
        Czy_nocna = czy_nocna;
    }

    public int getLiczba_przystankow() {
        return Liczba_przystankow;
    }

    public void setLiczba_przystankow(int liczba_przystankow) {
        Liczba_przystankow = liczba_przystankow;
    }

    @Override
    public String toString() {
        return "Linie{" +
                "Nr_linii=" + Nr_linii +
                ", Liczba_przystankow=" + Liczba_przystankow +
                ", Czy_nocna='" + Czy_nocna + '\'' +
                ", Nr_przedsiebiorstwa=" + Nr_przedsiebiorstwa +
                '}';
    }
}
