package app.rsprmobile.registro.data;

public class DataDokterPoli {
    private String namaDokterTetap, idDokterTetap;

    public DataDokterPoli(String namaDokterTetap, String idDokterTetap){
        this.idDokterTetap = idDokterTetap;
        this.namaDokterTetap = namaDokterTetap;
    }

    public DataDokterPoli() {

    }

    public String getNamaDokterTetap() {
        return namaDokterTetap;
    }

    public void setNamaDokterTetap(String namaDokterTetap) {
        this.namaDokterTetap = namaDokterTetap;
    }

    public String getIdDokterTetap() {
        return idDokterTetap;
    }

    public void setIdDokterTetap(String idDokterTetap) {
        this.idDokterTetap = idDokterTetap;
    }
}
