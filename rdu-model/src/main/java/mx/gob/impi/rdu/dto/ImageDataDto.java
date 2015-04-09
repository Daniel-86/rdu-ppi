/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

/**
 *
 * @author oracle
 */
public class ImageDataDto {
    
    private float anchoPx;
    private float altoPx;
    private float anchoCm;
    private float altoCm;
    private float anchoDpi;
    private float altoDpi;
    private boolean cumpleReq;

    public float getAnchoPx() {
        return anchoPx;
    }

    public void setAnchoPx(float anchoPx) {
        this.anchoPx = anchoPx;
    }

    public float getAltoPx() {
        return altoPx;
    }

    public void setAltoPx(float altoPx) {
        this.altoPx = altoPx;
    }

    public float getAnchoCm() {
        return anchoCm;
    }

    public void setAnchoCm(float anchoCm) {
        this.anchoCm = anchoCm;
    }

    public float getAltoCm() {
        return altoCm;
    }

    public void setAltoCm(float altoCm) {
        this.altoCm = altoCm;
    }

    public float getAnchoDpi() {
        return anchoDpi;
    }

    public void setAnchoDpi(float anchoDpi) {
        this.anchoDpi = anchoDpi;
    }

    public float getAltoDpi() {
        return altoDpi;
    }

    public void setAltoDpi(float altoDpi) {
        this.altoDpi = altoDpi;
    }

    public boolean isCumpleReq() {
        return cumpleReq;
    }

    public void setCumpleReq(boolean cumpleReq) {
        this.cumpleReq = cumpleReq;
    }  
}