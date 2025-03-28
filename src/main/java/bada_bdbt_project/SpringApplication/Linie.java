package bada_bdbt_project.SpringApplication;

public class Linie {
    private String nrLinii; 
    private Integer nrPrzedsiebiorstwa; 
    private String czyNocna;
    private Integer liczbaPrzystankow;


    public Linie() {
    }


    public Linie(String nrLinii, Integer nrPrzedsiebiorstwa, String czyNocna, Integer liczbaPrzystankow) {
        this.nrLinii = nrLinii;
        this.nrPrzedsiebiorstwa = nrPrzedsiebiorstwa;
        this.czyNocna = czyNocna;
        this.liczbaPrzystankow = liczbaPrzystankow;
    }


    public String getNrLinii() {
        return nrLinii;
    }

    public void setNrLinii(String nrLinii) {
        this.nrLinii = nrLinii;
    }

    public Integer getNrPrzedsiebiorstwa() {
        return nrPrzedsiebiorstwa;
    }

    public void setNrPrzedsiebiorstwa(Integer nrPrzedsiebiorstwa) {
        this.nrPrzedsiebiorstwa = nrPrzedsiebiorstwa;
    }

    public String getCzyNocna() {
        return czyNocna;
    }

    public void setCzyNocna(String czyNocna) {
        this.czyNocna = czyNocna;
    }

    public Integer getLiczbaPrzystankow() {
        return liczbaPrzystankow;
    }

    public void setLiczbaPrzystankow(Integer liczbaPrzystankow) {
        this.liczbaPrzystankow = liczbaPrzystankow;
    }


    @Override
    public String toString() {
        return "Linie{" +
                "nrLinii='" + nrLinii + '\'' +
                ", nrPrzedsiebiorstwa=" + nrPrzedsiebiorstwa +
                ", czyNocna='" + czyNocna + '\'' +
                ", liczbaPrzystankow=" + liczbaPrzystankow +
                '}';
    }
}
