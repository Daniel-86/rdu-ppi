package mx.gob.impi.sagpat.persistence.model;

public class ThisControl implements java.io.Serializable { 

    public ThisControl() {
    }

    public ThisControl(String tablename) {
        this.tablename = tablename;
    }
    
    
    private Integer rowVersion;

    private String tablename;

    private Long maxnumber;

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename == null ? null : tablename.trim();
    }

    public Long getMaxnumber() {
        return maxnumber;
    }

    public void setMaxnumber(Long maxnumber) {
        this.maxnumber = maxnumber;
    }
}