package bada_bdbt_project.SpringApplication;

public class Odjazdy {
    private int Nr_odjazdu;
    private String Godzina;
    private String Czy_na_zadanie;
    private int Nr_przystanku;
    //private int Nr_tramwaju; w sumie nwm czy jest nam potrzebny

    public Odjazdy(int nr_odjazdu, int nr_przystanku, String czy_na_zadanie, String godzina /* , int nr_tramwaju*/) {
        Nr_odjazdu = nr_odjazdu;
        Nr_przystanku = nr_przystanku;
        Czy_na_zadanie = czy_na_zadanie;
        Godzina = godzina;
     //   Nr_tramwaju = nr_tramwaju;
    }

    public Odjazdy() {
    }

    public int getNr_odjazdu() {
        return Nr_odjazdu;
    }

    public void setNr_odjazdu(int nr_odjazdu) {
        Nr_odjazdu = nr_odjazdu;
    }

 //   public int getNr_tramwaju() {
   //     return Nr_tramwaju;
   // }

   // public void setNr_tramwaju(int nr_tramwaju) {
     //   Nr_tramwaju = nr_tramwaju;
   // }

    public int getNr_przystanku() {
        return Nr_przystanku;
    }

    public void setNr_przystanku(int nr_przystanku) {
        Nr_przystanku = nr_przystanku;
    }

    public String getCzy_na_zadanie() {
        return Czy_na_zadanie;
    }

    public void setCzy_na_zadanie(String czy_na_zadanie) {
        Czy_na_zadanie = czy_na_zadanie;
    }

    public String getGodzina() {
        return Godzina;
    }

    public void setGodzina(String godzina) {
        Godzina = godzina;
    }

    @Override
    public String toString() {
        return "Odjazdy{" +
                "Nr_odjazdu=" + Nr_odjazdu +
                ", Godzina='" + Godzina + '\'' +
                ", Czy_na_zadanie='" + Czy_na_zadanie + '\'' +
                ", Nr_przystanku=" + Nr_przystanku +
               // ", Nr_tramwaju=" + Nr_tramwaju +
                '}';
    }
}
