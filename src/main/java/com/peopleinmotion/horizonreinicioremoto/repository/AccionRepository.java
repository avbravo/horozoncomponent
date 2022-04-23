/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.repository;

import com.peopleinmotion.horizonreinicioremoto.entity.Accion;
import com.peopleinmotion.horizonreinicioremoto.entity.AccionReciente;
import com.peopleinmotion.horizonreinicioremoto.entity.GrupoAccion;
import com.peopleinmotion.horizonreinicioremoto.paginator.QuerySQL;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author avbravo
 */
public interface AccionRepository {

    public List<Accion> findAll();

    public Optional<Accion> findByAccionId(BigInteger ACCIONID);
    public Optional<Accion> find(BigInteger id);

    public List<Accion> findByAccionIdAndActivo(BigInteger ACCIONID, String ACTIVO);

    public List<Accion> findByGrupoAccionId(GrupoAccion GRUPOACCIONID);

    public List<Accion> findByGrupoAccionIdAndPredeterminado(GrupoAccion GRUPOACCIONID, String PREDETERMINADO);

    public Boolean create(Accion accion);

    public Boolean update(Accion accion);
  public Boolean changed(Accion accion);

    public List<Accion> sql(QuerySQL querySQL);

    public List<Accion> pagination(QuerySQL querySQL, Integer pageNumber, Integer rowForPage);

    public int count(QuerySQL querySQL);
      
}
