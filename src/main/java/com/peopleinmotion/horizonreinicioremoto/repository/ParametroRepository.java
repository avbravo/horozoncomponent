/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.repository;

import com.peopleinmotion.horizonreinicioremoto.entity.AccionReciente;
import com.peopleinmotion.horizonreinicioremoto.entity.Parametro;
import com.peopleinmotion.horizonreinicioremoto.paginator.QuerySQL;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author avbravo
 */
public interface ParametroRepository {

    public List<Parametro> findAll();

    public Optional<Parametro> find(BigInteger id);

    public Optional<Parametro> findByParametroId(BigInteger PARAMETROID);

    public Optional<Parametro> findByClave(String CLAVE);

    
    public Boolean create(Parametro banco);

    public Boolean update(Parametro banco);

    public Boolean delete(Parametro banco);

    /*
    Query
     */

    public Boolean changed(Parametro banco);

    public List<Parametro> sql(QuerySQL querySQL);

    public List<Parametro> pagination(QuerySQL querySQL, Integer pageNumber, Integer rowForPage);

    public int count(QuerySQL querySQL);

}
