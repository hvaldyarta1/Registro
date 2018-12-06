package app.rsprmobile.registro.data;

public class DataHistori {
    private String noAntrean, namaKlinik, dokterJadwal, waktuCheckin, ruang;

    public DataHistori(String noAntrean, String namaKlinik, String dokterJadwal, String waktuCheckin, String ruang){
        this.noAntrean = noAntrean;
        this.namaKlinik = namaKlinik;
        this.dokterJadwal = dokterJadwal;
        this.waktuCheckin = waktuCheckin;
        this.ruang = ruang;
    }

    public DataHistori() {

    }

    public String getNoAntrean() {
        return noAntrean;
    }

    public void setNoAntrean(String noAntrean) {
        this.noAntrean = noAntrean;
    }

    public String getNamaKlinik() {
        return namaKlinik;
    }

    public void setNamaKlinik(String namaKlinik) {
        this.namaKlinik = namaKlinik;
    }

    public String getDokterJadwal() {
        return dokterJadwal;
    }

    public void setDokterJadwal(String dokterJadwal) {
        this.dokterJadwal = dokterJadwal;
    }

    public String getWaktuCheckin() {
        return waktuCheckin;
    }

    public void setWaktuCheckin(String waktuCheckin) {
        this.waktuCheckin = waktuCheckin;
    }

    public String getRuang() {
        return ruang;
    }

    public void setRuang(String ruang) {
        this.ruang = ruang;
    }
}
