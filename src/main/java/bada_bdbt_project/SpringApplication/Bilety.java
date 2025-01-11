package bada_bdbt_project.SpringApplication;

public class Bilety {




    private int Nr_biletu;
    private String Nazwa;

    public Bilety(int nr_biletu, String nazwa, String czas_waznosci, int cena, int nr_przedsiebiorstwa) {
        Nr_biletu = nr_biletu;
        Nazwa = nazwa;
        Czas_waznosci = czas_waznosci;
        Cena = cena;
        Nr_przedsiebiorstwa = nr_przedsiebiorstwa;
    }

    public Bilety() {
    }

    public int getNr_biletu() {
        return Nr_biletu;
    }

    public void setNr_biletu(int nr_biletu) {
        Nr_biletu = nr_biletu;
    }

    @Override
    public String toString() {
        return "Bilety{" +
                "Nr_biletu=" + Nr_biletu +
                ", Nazwa='" + Nazwa + '\'' +
                ", Czas_waznosci='" + Czas_waznosci + '\'' +
                ", Cena=" + Cena +
                ", Nr_przedsiebiorstwa=" + Nr_przedsiebiorstwa +
                '}';
    }

    public int getNr_przedsiebiorstwa() {
        return Nr_przedsiebiorstwa;
    }

    public void setNr_przedsiebiorstwa(int nr_przedsiebiorstwa) {
        Nr_przedsiebiorstwa = nr_przedsiebiorstwa;
    }

    public int getCena() {
        return Cena;
    }

    public void setCena(int cena) {
        Cena = cena;
    }

    public String getCzas_waznosci() {
        return Czas_waznosci;
    }

    public void setCzas_waznosci(String czas_waznosci) {
        Czas_waznosci = czas_waznosci;
    }

    public String getNazwa() {
        return Nazwa;
    }

    public void setNazwa(String nazwa) {
        Nazwa = nazwa;
    }

    private String Czas_waznosci;
    private int Cena;
    private int Nr_przedsiebiorstwa;
}
