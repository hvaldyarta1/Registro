package app.rsprmobile.registro.data;

public class DataJadwalPoli {
    private String namaKlinik, jamAwal, jamAkhir, hariPraktek, kuotaPasien, kuotaPerJam;

    public DataJadwalPoli(String namaKlinik, String jamAwal, String jamAkhir, String hariPraktek,
                          String kuotaPasien, String kuotaPerJam){
        this.namaKlinik = namaKlinik;
        this.jamAwal = jamAwal;
        this.jamAkhir = jamAkhir;
        this.hariPraktek = hariPraktek;
        this.kuotaPasien = kuotaPasien;
        this.kuotaPerJam = kuotaPerJam;
    }

    public DataJadwalPoli() {

    }

    public String getKuotaPasien() {
        return kuotaPasien;
    }

    public void setKuotaPasien(String kuotaPasien) {
        this.kuotaPasien = kuotaPasien;
    }

    public String getKuotaPerJam() {
        return kuotaPerJam;
    }

    public void setKuotaPerJam(String kuotaPerJam) {
        this.kuotaPerJam = kuotaPerJam;
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

    public String getHariPraktek() {
        return hariPraktek;
    }

    public void setHariPraktek(String hariPraktek) {
        this.hariPraktek = hariPraktek;
    }
}
