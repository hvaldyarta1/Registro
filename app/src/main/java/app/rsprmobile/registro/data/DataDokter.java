package app.rsprmobile.registro.data;

public class DataDokter {
    private String dokterId;
    private String namaDokter;

    public DataDokter(String id_dokter, String nama_dokter){
        this.dokterId = id_dokter;
        this.namaDokter = nama_dokter;
    }

    public DataDokter() {

    }

    public String getDokterId() {
        return dokterId;
    }

    public void setDokterId(String dokterId) {
        this.dokterId = dokterId;
    }

    public String getNamaDokter() {
        return namaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }
}