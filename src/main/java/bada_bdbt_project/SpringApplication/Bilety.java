package bada_bdbt_project.SpringApplication;

public class Bilety {


    private int nrBiletu;
    private String nazwa;
    private String czasWaznosci;
    private float cena;
    private int nrPrzedsiebiorstwa;
    private String imie;
    private String nazwisko;
    private String rodzaj;

    public Bilety(){

    }

    public Bilety(int nrBiletu, String nazwa, String czasWaznosci, float cena, int nrPrzedsiebiorstwa, String imie, String nazwisko, String rodzaj) {
        this.nrBiletu = nrBiletu;
        this.nazwa = nazwa;
        this.czasWaznosci = czasWaznosci;
        this.cena = cena;
        this.nrPrzedsiebiorstwa = nrPrzedsiebiorstwa;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.rodzaj = rodzaj;
    }

    public int getNrBiletu() {
        return nrBiletu;
    }

    public void setNrBiletu(int nrBiletu) {
        this.nrBiletu = nrBiletu;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getCzasWaznosci() {
        return czasWaznosci;
    }

    public void setCzasWaznosci(String czasWaznosci) {
        this.czasWaznosci = czasWaznosci;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }

    public int getNrPrzedsiebiorstwa() {
        return nrPrzedsiebiorstwa;
    }

    public void setNrPrzedsiebiorstwa(int nrPrzedsiebiorstwa) {
        this.nrPrzedsiebiorstwa = nrPrzedsiebiorstwa;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }

    @Override
    public String toString() {
        return "Bilety{" +
                "nrBiletu=" + nrBiletu +
                ", nazwa='" + nazwa + '\'' +
                ", czasWaznosci='" + czasWaznosci + '\'' +
                ", cena=" + cena +
                ", nrPrzedsiebiorstwa=" + nrPrzedsiebiorstwa +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", rodzaj='" + rodzaj + '\'' +
                '}';
    }
}