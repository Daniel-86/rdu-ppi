/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.notifications;

import java.util.List;
import mx.gob.impi.rdu.dto.PromoventeDto;
import mx.gob.impi.sigappi.persistence.model.Notification;

/**
 *
 * @author daniel
 */
public class Utils {
    
    public static boolean isEmptyString(String s) {
        return s == null || s.trim().equals("") || s.trim().equals("null");
    }

    static boolean isAlreadyPresent(List<Notification> notificationsInView, String recordId) {
        boolean result = false;
        for (Notification notification : notificationsInView) {
            if (notification.getTitle().equals(recordId) || notification.getPc().equals(recordId)) {
                result = true;
                break;
            }
        }
        return result;
    }

    static String getFullName(PromoventeDto user) {
        return capitalize(user.getNombre()) + " " + capitalize(user.getApaterno()) + " " + capitalize(user.getAmaterno());
    }

    static String getFullAddress(PromoventeDto user) {
        String fullAddress = user.getCalle_numero()
                + " " + user.getNumero_exterior()
                + (isEmptyString(user.getNumero_interior()) ? "" : " No. ext " + user.getNumero_interior())
                + ", Colonia " + capitalize(user.getColonia())
                + ", Código postal " + user.getCodigo_postal()
                + ", en " + capitalize(user.getDescMunicipio())
                + ", " + capitalize(user.getDescEstado());
        return fullAddress;
    }

    static String getFullContact(PromoventeDto user) {
        String fullContact = (isEmptyString(user.getEmail()) ? "" : "<strong>Email: </strong>" + user.getEmail())
                + (isEmptyString(user.getTelefono()) ? "" : " <strong>Teléfono: </strong>" + user.getTelefono())
                + (isEmptyString(user.getFax()) ? "" : " <strong>Fax: </strong>" + user.getFax());
        return fullContact;
    }
    
    private static String capitalize(String s) {
        return s.length() == 0 ? "" : s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
    
    public static Notification[] makeArray(List<Notification> nots) {
        Notification[] select;
        select = nots != null ? new Notification[nots.size()] : null;
        int i = 0;
        for (Notification n : nots) {
            select[i++] = n;
        }
        return select;
    }
    
}
