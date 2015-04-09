/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.service;

import mx.gob.impi.rdu.persistence.model.Anexos;

import java.util.List;

/**
 *
 * @author winter
 */
public interface AnexosService {
    public List<Anexos> insert(Anexos anexo);
}