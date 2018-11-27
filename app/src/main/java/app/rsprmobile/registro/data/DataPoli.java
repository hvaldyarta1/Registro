package app.rsprmobile.registro.data;

public class DataPoli {
    private String keterangan, fotoKlinik, namaKlinik, idKlinik, ruanganKlinik;

    public DataPoli(String keterangan, String fotoKlinik, String namaKlinik,
                    String idKlinik, String ruanganKlinik) {
        this.keterangan = keterangan;
        this.fotoKlinik = fotoKlinik;
        this.namaKlinik = namaKlinik;
        this.idKlinik = idKlinik;
        this.ruanganKlinik = ruanganKlinik;
    }

    public DataPoli() {

    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getFotoKlinik() {
        return fotoKlinik;
    }

    public void setFotoKlinik(String fotoKlinik) {
        this.fotoKlinik = fotoKlinik;
    }

    public String getNamaKlinik() {
        return namaKlinik;
    }

    public void setNamaKlinik(String namaKlinik) {
        this.namaKlinik = namaKlinik;
    }

    public String getIdKlinik() {
        return idKlinik;
    }

    public void setIdKlinik(String idKlinik) {
        this.idKlinik = idKlinik;
    }

    public String getRuanganKlinik() {
        return ruanganKlinik;
    }

    public void setRuanganKlinik(String ruanganKlinik) {
        this.ruanganKlinik = ruanganKlinik;
    }
}
