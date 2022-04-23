/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author avbravo
 */


@Entity
@Table(name = "PARAMETRO")
@NamedQueries({
    @NamedQuery(name = "Parametro.findAll", query = "SELECT p FROM Parametro p"),
    @NamedQuery(name = "Parametro.findByParametroId", query = "SELECT p FROM Parametro p WHERE p.PARAMETROID = :PARAMETROID"),
    @NamedQuery(name = "Parametro.findByClave", query = "SELECT p FROM Parametro p WHERE p.CLAVE = :CLAVE")

})
@Cacheable(false)
public class Parametro implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "PARAMETRO_GEN", sequenceName = "PARAMETRO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARAMETRO_GEN")
    @NotNull
    @Column(name = "PARAMETROID")
    private BigInteger PARAMETROID;
   
    @Basic(optional = false)
    @NotNull
    @Size(max = 100)
    @Column(name = "CLAVE")
    private String CLAVE;
    
    @Basic(optional = false)
    @NotNull
    @Size(max = 100)
    @Column(name = "VALOR")
    private String VALOR;
    
   
    public Parametro() {
    }

    public BigInteger getPARAMETROID() {
        return PARAMETROID;
    }

    public void setPARAMETROID(BigInteger PARAMETROID) {
        this.PARAMETROID = PARAMETROID;
    }

    public String getCLAVE() {
        return CLAVE;
    }

    public void setCLAVE(String CLAVE) {
        this.CLAVE = CLAVE;
    }

    public String getVALOR() {
        return VALOR;
    }

    public void setVALOR(String VALOR) {
        this.VALOR = VALOR;
    }

    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (PARAMETROID != null ? PARAMETROID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametro)) {
            return false;
        }
        Parametro other = (Parametro) object;
        if ((this.PARAMETROID == null && other.PARAMETROID != null) || (this.PARAMETROID != null && !this.PARAMETROID.equals(other.PARAMETROID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Parametro{" + "PARAMETROID=" + PARAMETROID + ", CLAVE=" + CLAVE + ", VALOR=" + VALOR + '}';
    }

   public String toJSON() {
    
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\n  \"PARAMETROID\":\"").append(PARAMETROID).append("\"");
        sb.append("\n, \"CLAVE\":\"").append(CLAVE).append("\"");
        sb.append("\n, \"VALOR\":\"").append(VALOR).append("\"");
        sb.append("\n}");
        return sb.toString();
    }


}
