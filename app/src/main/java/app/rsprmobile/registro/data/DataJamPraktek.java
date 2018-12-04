package app.rsprmobile.registro.data;

public class DataJamPraktek {
    private String jamAwal, jamAkhir;

    public DataJamPraktek (String jamAwal, String jamAkhir){
        this.jamAwal = jamAwal;
        this.jamAkhir = jamAkhir;
    }

    public DataJamPraktek() {
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
}
