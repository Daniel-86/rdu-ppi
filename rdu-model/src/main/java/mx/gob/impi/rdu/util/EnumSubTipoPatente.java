/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.util;

/**
 *
 * @author
 */
public enum EnumSubTipoPatente {

    
    DISENO_INDUSTRIAL(11, "Diseño industrial", "f"),
    PATENTE(9, "Patente", "a"),
    MODELO_UTILIDAD(10, "Modelo de Utilidad", "u"),
    CIRCUITO_INTEGRADO(12, "Diseño industrial", "t");
    
    
    private EnumSubTipoPatente(int idSubTipoPatente, String descripcion, String code) {
        this.idSubTipoPatente = idSubTipoPatente;
        this.descripcion = descripcion;
        this.code = code;
    }
    
    private int idSubTipoPatente;
    private String descripcion;
    private String code;

    public int getIdSubTipoPatente() {
        return idSubTipoPatente;
    }

    public void setIdSubTipoPatente(int idSubTipoPatente) {
        this.idSubTipoPatente = idSubTipoPatente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    
    
}
