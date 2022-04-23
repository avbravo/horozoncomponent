/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.repository;

import com.peopleinmotion.horizonreinicioremoto.entity.Parametro;
import com.peopleinmotion.horizonreinicioremoto.facade.ParametroFacade;
import com.peopleinmotion.horizonreinicioremoto.paginator.QuerySQL;
import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author avbravo
 */
@Stateless
public class ParametroRepositoryImpl implements ParametroRepository {

    @Inject
    ParametroFacade parametroFacade;

    @Override
    public List<Parametro> findAll() {
        return parametroFacade.findAll();
    }

    @Override

    public Optional<Parametro> findByParametroId(BigInteger PARAMETROID) {
        return parametroFacade.findByParametroId(PARAMETROID);
    }

    // <editor-fold defaultstate="collapsed" desc="Boolean create(Parametro parametro) ">
    @Override
    public Boolean create(Parametro parametro) {
        try {
            parametroFacade.create(parametro);
            return true;
        } catch (Exception e) {
            // System.out.println("create() " + e.getLocalizedMessage());
        }
        return false;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Optional<Parametro> findByClave(String CLAVE) ">
    @Override
    public Optional<Parametro> findByClave(String CLAVE) {
        return parametroFacade.findByClave(CLAVE);
    }
    // </editor-fold>
   

    @Override
    public Boolean update(Parametro parametro) {
        try {
            parametroFacade.edit(parametro);
            return true;
        } catch (Exception e) {
            // System.out.println("update() " + e.getLocalizedMessage());
        }
        return false;
    }

    @Override
    public Boolean delete(Parametro parametro) {
        try {
            parametroFacade.remove(parametro);
            return true;
        } catch (Exception e) {
            // System.out.println("update() " + e.getLocalizedMessage());
        }
        return false;
    }

    
// <editor-fold defaultstate="collapsed" desc="Boolean changed(Parametro parametro)>

    @Override
    public Boolean changed(Parametro parametro) {
        try {
            
            Optional<Parametro> live = parametroFacade.find(parametro.getPARAMETROID());
            if (!live.isPresent()) {
                return Boolean.TRUE;
            }
            String jsonLive = live.get().toJSON();

            String json =parametro.toJSON();

            if (!json.equals(jsonLive)) {
                //Otro usuario lo cambio mientras se estaba procesando
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }
    // </editor-fold>

    @Override
    public Optional<Parametro> find(BigInteger id) {
        return parametroFacade.find(id);
    }

    
    @Override
    public List<Parametro> sql(QuerySQL querySQL) {
        return parametroFacade.sql(querySQL);
    }
    @Override
    public List<Parametro> pagination(QuerySQL querySQL, Integer pageNumber, Integer rowForPage) {
        return parametroFacade.pagination(querySQL, pageNumber, rowForPage);
    }

    @Override
    public int count(QuerySQL querySQL) {
       return parametroFacade.count(querySQL);
    }

   

  
   
}
