/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.facade;

import com.peopleinmotion.horizonreinicioremoto.entity.Parametro;
import com.peopleinmotion.horizonreinicioremoto.utils.ConsoleUtil;
import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author avbravo
 */
@Stateless

public class ParametroFacade extends AbstractFacade<Parametro> {

    @PersistenceContext(unitName = "com.people-inmotion_horizonreinicioremotoejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParametroFacade() {
        super(Parametro.class);
    }
    // <editor-fold defaultstate="collapsed" desc="Optional<Parametro> find(BigInteger id)">

    /**
     * Busca por la llave primaria se usa solo el nombre find() para simplificar
     * su uso
     *
     * @param PARAMETROID
     * @return
     */
    public Optional<Parametro> find(BigInteger id) {
        try {
            Query query = em.createNamedQuery("Parametro.findByParametroId");
            Parametro parametro = (Parametro) query.setParameter("PARAMETROID", id).getSingleResult();
            return Optional.of(parametro);
        } catch (Exception e) {
             ConsoleUtil.error(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return Optional.empty();

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Optional<Parametro> findById(BigInteger PARAMETROID)">

    public Optional<Parametro> findById(BigInteger PARAMETROID) {
        try {
            Query query = em.createNamedQuery("Parametro.findByParametroId");
            Parametro parametro = (Parametro) query.setParameter("PARAMETROID", PARAMETROID).getSingleResult();
            return Optional.of(parametro);
        } catch (Exception e) {
            // System.out.println("findByParametroId() "+e.getLocalizedMessage());
        }
        return Optional.empty();

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Optional<Parametro> findByParametroId(BigInteger PARAMETROID)">

    public Optional<Parametro> findByParametroId(BigInteger PARAMETROID) {
        try {
            Query query = em.createNamedQuery("Parametro.findByParametroId");
            Parametro parametro = (Parametro) query.setParameter("PARAMETROID", PARAMETROID).getSingleResult();
            return Optional.of(parametro);
        } catch (Exception e) {
            // System.out.println("findByParametroId() "+e.getLocalizedMessage());
        }
        return Optional.empty();

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Optional<Parametro> findByClave(String CLAVE) ">
    public Optional<Parametro> findByClave(String CLAVE) {
        try {
            Query query = em.createQuery("SELECT p FROM Parametro p WHERE p.CLAVE = :CLAVE  ORDER BY p.CLAVE");

            Parametro parametro = (Parametro) query.setParameter("CLAVE", CLAVE).getSingleResult();
            return Optional.of(parametro);
        } catch (Exception e) {
            // System.out.println("findByEsControlActivo() "+e.getLocalizedMessage());
        }
        return Optional.empty();

    }
    // </editor-fold>
   

    
    

}
