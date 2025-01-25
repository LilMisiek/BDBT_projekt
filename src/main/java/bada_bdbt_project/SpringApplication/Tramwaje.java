package bada_bdbt_project.SpringApplication;

import java.time.LocalDate;

/**
 * Encja Tramwaje odwzorowująca tabelę TRAMWAJE w bazie.
 */
public class Tramwaje {

    private int nrTramwaju;          // PK
    private LocalDate dataProdukcji;
    private LocalDate dataZakupu;
    private LocalDate dataOstatniegoSerwisu;
    private int nrPrzedsiebiorstwa;  // FK do TPM (Nr_przedsiebiorstwa)
    private Integer nrZajezdni;          // FK do Zajezdnie (Nr_zajezdni)
    private String nrLinii;          // FK do Linie (Nr_linii, varchar2(2) w bazie)
    private Integer nrModelu;           // FK do Modele (Nr_modelu)

    public Tramwaje() {
    }

    public Tramwaje(int nrTramwaju,
                    LocalDate dataProdukcji,
                    LocalDate dataZakupu,
                    LocalDate dataOstatniegoSerwisu,
                    int nrPrzedsiebiorstwa,
                    Integer nrZajezdni,
                    String nrLinii,
                    Integer nrModelu) {
        this.nrTramwaju = nrTramwaju;
        this.dataProdukcji = dataProdukcji;
        this.dataZakupu = dataZakupu;
        this.dataOstatniegoSerwisu = dataOstatniegoSerwisu;
        this.nrPrzedsiebiorstwa = nrPrzedsiebiorstwa;
        this.nrZajezdni = nrZajezdni;
        this.nrLinii = nrLinii;
        this.nrModelu = nrModelu;
    }

    // GETTERY i SETTERY

    public int getNrTramwaju() {
        return nrTramwaju;
    }

    public void setNrTramwaju(int nrTramwaju) {
        this.nrTramwaju = nrTramwaju;
    }

    public LocalDate getDataProdukcji() {
        return dataProdukcji;
    }

    public void setDataProdukcji(LocalDate dataProdukcji) {
        this.dataProdukcji = dataProdukcji;
    }

    public LocalDate getDataZakupu() {
        return dataZakupu;
    }

    public void setDataZakupu(LocalDate dataZakupu) {
        this.dataZakupu = dataZakupu;
    }

    public LocalDate getDataOstatniegoSerwisu() {
        return dataOstatniegoSerwisu;
    }

    public void setDataOstatniegoSerwisu(LocalDate dataOstatniegoSerwisu) {
        this.dataOstatniegoSerwisu = dataOstatniegoSerwisu;
    }

    public int getNrPrzedsiebiorstwa() {
        return nrPrzedsiebiorstwa;
    }

    public void setNrPrzedsiebiorstwa(int nrPrzedsiebiorstwa) {
        this.nrPrzedsiebiorstwa = nrPrzedsiebiorstwa;
    }

    public int getNrZajezdni() {
        return nrZajezdni;
    }

    public void setNrZajezdni(Integer nrZajezdni) {
        this.nrZajezdni = nrZajezdni;
    }

    public String getNrLinii() {
        return nrLinii;
    }

    public void setNrLinii(String nrLinii) {
        this.nrLinii = nrLinii;
    }

    public int getNrModelu() {
        return nrModelu;
    }

    public void setNrModelu(Integer nrModelu) {
        this.nrModelu = nrModelu;
    }

    @Override
    public String toString() {
        return "Tramwaje{" +
                "nrTramwaju=" + nrTramwaju +
                ", dataProdukcji=" + dataProdukcji +
                ", dataZakupu=" + dataZakupu +
                ", dataOstatniegoSerwisu=" + dataOstatniegoSerwisu +
                ", nrPrzedsiebiorstwa=" + nrPrzedsiebiorstwa +
                ", nrZajezdni=" + nrZajezdni +
                ", nrLinii='" + nrLinii + '\'' +
                ", nrModelu=" + nrModelu +
                '}';
    }
}
