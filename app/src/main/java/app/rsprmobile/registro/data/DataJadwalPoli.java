package app.rsprmobile.registro.data;

public class DataJadwalPoli {
    String namaKlinik, jamAwal, jamAkhir, hariPraktek;

    public DataJadwalPoli(String namaKlinik, String jamAwal, String jamAkhir, String hariPraktek){
        this.namaKlinik = namaKlinik;
        this.jamAwal = jamAwal;
        this.jamAkhir = jamAkhir;
        this.hariPraktek = hariPraktek;
    }

    public DataJadwalPoli() {

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
