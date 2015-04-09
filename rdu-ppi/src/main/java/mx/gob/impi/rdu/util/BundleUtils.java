package mx.gob.impi.rdu.util;

import java.util.ResourceBundle;


public class BundleUtils {
   
    public static final String BUNDLE_RESOURCE = "mx.gob.impi.rdu.i18n.generales";
   
    public static ResourceBundle MSG_BUNDLE;
 
    
    static {
        try {
                MSG_BUNDLE = ResourceBundle.getBundle(BUNDLE_RESOURCE);
        } catch (Exception exception) {
            MSG_BUNDLE = null;
        } 
    }

    
    public static String getResource(final String propKey) {
        String toReturn = null;
        try {
            toReturn = MSG_BUNDLE.getString(propKey);
        } catch (Exception exception) {
            toReturn = null;
        } // catch

        return toReturn;
    }

    
    public final static String getResource(final String propKey, final String defaultValue) {
        String value = getResource(propKey);

        if (null == value || value.isEmpty()) {
            value = defaultValue;
        } // if

        return value;
    }

    
}
