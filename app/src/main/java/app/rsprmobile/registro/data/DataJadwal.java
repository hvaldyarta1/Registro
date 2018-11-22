package app.rsprmobile.registro.data;

public class DataJadwal {
    private String klinik_id, kuotaPasienPerjam, namaKlinik, jamAwal, jamAkhir, ruangPeriksa,
            hariPraktek;

    public DataJadwal(String klinik_id, String kuotaPasienPerjam, String namaKlinik,
                      String jamAwal, String jamAkhir, String ruangPeriksa, String hariPraktek){
        this.klinik_id = klinik_id;
        this.kuotaPasienPerjam = kuotaPasienPerjam;
        this.namaKlinik = namaKlinik;
        this.jamAwal = jamAwal;
        this.jamAkhir = jamAkhir;
        this.ruangPeriksa = ruangPeriksa;
        this.hariPraktek = hariPraktek;
    }

    public DataJadwal() {

    }

    public String getKlinik_id() {
        return klinik_id;
    }

    public void setKlinik_id(String klinik_id) {
        this.klinik_id = klinik_id;
    }

    public String getKuotaPasienPerjam() {
        return kuotaPasienPerjam;
    }

    public void setKuotaPasienPerjam(String kuotaPasienPerjam) {
        this.kuotaPasienPerjam = kuotaPasienPerjam;
    }

    public String getNamaKlinik() {
        return namaKlinik;
    }

    public void setNamaKlinik(String namaKlinik) {
        this.namaKlinik = namaKlinik;
    }

    public String getJamAwal() {
        return jamAwal;
    }

    public void setJamAwal(String jamAwal) {
        this.jamAwal = jamAwal;
    }

    public String getJamAkhir() {
        return jamAkhir;
    }

    public void setJamAkhir(String jamAkhir) {
        this.jamAkhir = jamAkhir;
    }

    public String getRuangPeriksa() {
        return ruangPeriksa;
    }

    public void setRuangPeriksa(String ruangPeriksa) {
        this.ruangPeriksa = ruangPeriksa;
    }

    public String getHariPraktek() {
        return hariPraktek;
    }

    public void setHariPraktek(String hariPraktek) {
        this.hariPraktek = hariPraktek;
    }
}