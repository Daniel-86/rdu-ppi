/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.exposition.flujosGenerales.reporte;

import com.itextpdf.text.FontFactoryImp;
import com.itextpdf.text.Font;
import com.itextpdf.text.BaseColor;

/**
 *
 * @author JHR
 */
public class rduFontProvider extends FontFactoryImp{
    
    private String _default;

    public rduFontProvider(String def) {
        _default = def;
    }
//
    public Font getFont(String fontName, String encoding, boolean embedded, float size, int style, BaseColor color, boolean cached) {
        if (fontName == null || size == 0) {
            fontName = _default;
        }

        return super.getFont(fontName, encoding, embedded, 10, style, color, cached);
    }
    
}
