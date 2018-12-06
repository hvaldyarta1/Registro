package app.rsprmobile.registro.data;

public class DataKlinik {
    private String namaKlinik, klinikDokter, idKlinikDokter, dokterId, hariPraktek,
    jamAwal, jamAkhir, klinikId, kuotaPasien, kuotaPerjam, waktuJadwal,
    keteranganJadwal, ruangTetap, statusPasien, tracerTetap;

    public DataKlinik(String namaKlinik, String klinikDokter, String idKlinikDokter,
                      String dokterId, String hariPraktek, String jamAwal, String jamAkhir,
                      String klinikId, String kuotaPasien, String kuotaPerjam,
                      String waktuJadwal, String keteranganJadwal, String ruangTetap,
                      String statusPasien, String tracerTetap){
        this.namaKlinik = namaKlinik;
        this.klinikDokter = klinikDokter;
        this.idKlinikDokter = klinikDokter;
        this.dokterId = dokterId;
        this.hariPraktek = hariPraktek;
        this.jamAwal = jamAwal;
        this.jamAkhir = jamAkhir;
        this.klinikId = klinikId;
        this.kuotaPasien = kuotaPasien;
        this.kuotaPerjam = kuotaPerjam;
        this.waktuJadwal =waktuJadwal;
        this.keteranganJadwal = keteranganJadwal;
        this.ruangTetap = ruangTetap;
        this.statusPasien = statusPasien;
        this.tracerTetap = tracerTetap;
    }

    public DataKlinik() {

    }

    public String getNamaKlinik() {
        return namaKlinik;
    }

    public void setNamaKlinik(String namaKlinik) {
        this.namaKlinik = namaKlinik;
    }

    public String getKlinikDokter() {
        return klinikDokter;
    }

    public void setKlinikDokter(String klinikDokter) {
        this.klinikDokter = klinikDokter;
    }

    public String getIdKlinikDokter() {
        return idKlinikDokter;
    }

    public void setIdKlinikDokter(String idKlinikDokter) {
        this.idKlinikDokter = idKlinikDokter;
    }

    public String getDokterId() {
        return dokterId;
    }

    public void setDokterId(String dokterId) {
        this.dokterId = dokterId;
    }

    public String getHariPraktek() {
        return hariPraktek;
    }

    public void setHariPraktek(String hariPraktek) {
        this.hariPraktek = hariPraktek;
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

    public String getKlinikId() {
        return klinikId;
    }

    public void setKlinikId(String klinikId) {
        this.klinikId = klinikId;
    }

    public String getKuotaPasien() {
        return kuotaPasien;
    }

    public void setKuotaPasien(String kuotaPasien) {
        this.kuotaPasien = kuotaPasien;
    }

    public String getKuotaPerjam() {
        return kuotaPerjam;
    }

    public void setKuotaPerjam(String kuotaPerjam) {
        this.kuotaPerjam = kuotaPerjam;
    }

    public String getWaktuJadwal() {
        return waktuJadwal;
    }

    public void setWaktuJadwal(String waktuJadwal) {
        this.waktuJadwal = waktuJadwal;
    }

    public String getKeteranganJadwal() {
        return keteranganJadwal;
    }

    public void setKeteranganJadwal(String keteranganJadwal) {
        this.keteranganJadwal = keteranganJadwal;
    }

    public String getRuangTetap() {
        return ruangTetap;
    }

    public void setRuangTetap(String ruangTetap) {
        this.ruangTetap = ruangTetap;
    }

    public String getStatusPasien() {
        return statusPasien;
    }

    public void setStatusPasien(String statusPasien) {
        this.statusPasien = statusPasien;
    }

    public String getTracerTetap() {
        return tracerTetap;
    }

    public void setTracerTetap(String tracerTetap) {
        this.tracerTetap = tracerTetap;
    }
}
