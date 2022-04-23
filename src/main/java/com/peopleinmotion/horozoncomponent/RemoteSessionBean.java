/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horozoncomponent;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author martin
 */
@Stateless
@LocalBean
public class RemoteSessionBean {

    public void businessMethod() {
        System.out.println("Hola mundo desde ejb");
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

}
