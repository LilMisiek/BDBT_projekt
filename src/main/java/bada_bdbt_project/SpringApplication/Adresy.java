package bada_bdbt_project.SpringApplication;

public class Adresy {
    public Adresy(int nr_adresu, String kod_pocztowy, String nr_lokalu, String nr_budynku, String ulica, String miasto, String kraj) {
        Nr_adresu = nr_adresu;
        Kod_pocztowy = kod_pocztowy;
        Nr_lokalu = nr_lokalu;
        Nr_budynku = nr_budynku;
        Ulica = ulica;
        Miasto = miasto;
        Kraj = kraj;
    }

    public Adresy() {
    }

    public int getNr_adresu() {
        return Nr_adresu;
    }

    public void setNr_adresu(int nr_adresu) {
        Nr_adresu = nr_adresu;
    }

    public String getKod_pocztowy() {
        return Kod_pocztowy;
    }

    public void setKod_pocztowy(String kod_pocztowy) {
        Kod_pocztowy = kod_pocztowy;
    }

    public String getNr_lokalu() {
        return Nr_lokalu;
    }

    public void setNr_lokalu(String nr_lokalu) {
        Nr_lokalu = nr_lokalu;
    }

    public String getNr_budynku() {
        return Nr_budynku;
    }

    public void setNr_budynku(String nr_budynku) {
        Nr_budynku = nr_budynku;
    }

    public String getUlica() {
        return Ulica;
    }

    public void setUlica(String ulica) {
        Ulica = ulica;
    }

    public String getMiasto() {
        return Miasto;
    }

    public void setMiasto(String miasto) {
        Miasto = miasto;
    }

    public String getKraj() {
        return Kraj;
    }

    public void setKraj(String kraj) {
        Kraj = kraj;
    }

    private int Nr_adresu;
    private String Kraj;
    private String Miasto;
    private String Ulica;
    private String Nr_budynku;
    private String Nr_lokalu;
    private String Kod_pocztowy;

    @Override
    public String toString() {
        return "Adresy{" +
                "Nr_adresu=" + Nr_adresu +
                ", Kraj='" + Kraj + '\'' +
                ", Miasto='" + Miasto + '\'' +
                ", Ulica='" + Ulica + '\'' +
                ", Nr_budynku='" + Nr_budynku + '\'' +
                ", Nr_lokalu='" + Nr_lokalu + '\'' +
                ", Kod_pocztowy='" + Kod_pocztowy + '\'' +
                '}';
    }
}
