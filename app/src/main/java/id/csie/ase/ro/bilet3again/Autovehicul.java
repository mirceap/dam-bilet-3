package id.csie.ase.ro.bilet3again;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Autovehicul implements Serializable {
    private String numarAuto;
    private SimpleDateFormat formater = new SimpleDateFormat("MM-DD-YYYY HH:MM");
    private Date dataInregistrarii;
    private int idLocParcare;
    private boolean aPlatit;

    public Autovehicul(String numarAuto, Date dataInregistrarii, int idLocParcare, boolean aPlatit) {
        this.numarAuto = numarAuto;
        this.dataInregistrarii = dataInregistrarii;
        this.idLocParcare = idLocParcare;
        this.aPlatit = aPlatit;
    }

    public Autovehicul() {
    }

    public String getNumarAuto() {
        return numarAuto;
    }

    public Date getDataInregistrarii() {
        return dataInregistrarii;
    }

    public int getIdLocParcare() {
        return idLocParcare;
    }

    public boolean isaPlatit() {
        return aPlatit;
    }

    public void setNumarAuto(String numarAuto) {
        this.numarAuto = numarAuto;
    }

    public void setDataInregistrarii(Date dataInregistrarii) {
        this.dataInregistrarii = dataInregistrarii;
    }

    public void setDataInregistrarii(String dataInregistrarii) throws ParseException {
        this.dataInregistrarii = formater.parse(dataInregistrarii);
    }

    public void setIdLocParcare(int idLocParcare) {
        this.idLocParcare = idLocParcare;
    }

    public void setaPlatit(boolean aPlatit) {
        this.aPlatit = aPlatit;
    }

    @Override
    public String toString() {
        return "Autovehicul{" +
                "numarAuto='" + numarAuto + '\'' +
                ", dataInregistrarii=" + dataInregistrarii +
                ", idLocParcare=" + idLocParcare +
                ", aPlatit=" + aPlatit +
                '}';
    }
}
