/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peopleinmotion.horizonreinicioremoto.services;

import com.peopleinmotion.horizonreinicioremoto.entity.Banco;
import com.peopleinmotion.horizonreinicioremoto.entity.Parametro;
import com.peopleinmotion.horizonreinicioremoto.entity.Usuario;
import com.peopleinmotion.horizonreinicioremoto.jmoordb.JmoordbContext;
import com.peopleinmotion.horizonreinicioremoto.repository.BancoRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.ParametroRepository;
import com.peopleinmotion.horizonreinicioremoto.repository.UsuarioRepository;
import com.peopleinmotion.horizonreinicioremoto.utils.ConsoleUtil;

import com.peopleinmotion.horizonreinicioremoto.utils.JsfUtil;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 *
 * @author avbravo
 */
@Stateless
public class AccessServicesImpl implements AccessServices {

    // static final String LDAP_URL = "ldap://SRV-WIN-19:389/"; //pim
    static final String LDAP_URL = "ldap://tlrddc-p-003:389/";
    static final String contexto = "com.sun.jndi.ldap.LdapCtxFactory";
    static final String tipoAuth = "simple";
// <editor-fold defaultstate="collapsed" desc="inject() ">

    @Inject
    BancoRepository bancoRepository;
    @Inject
    UsuarioRepository usuarioRepository;
    @Inject
    AccessServices accessServices;

    @Inject
    ParametroRepository ParametroRepository;

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String loadConfigurationPropeties()">
    @Override
    public String loadConfigurationPropeties() {
        String version = "";
        try {
            /**
             * Leer de la base de datos
             */

            buscarClaveEntera("rowForPage");

            /**
             * Asigna las properties al contexto
             */
            buscarClaveEntera("grupoEstadoSolicitadoId");
            buscarClaveEntera("grupoEstadoEnprocesoId");
            buscarClaveEntera("grupoEstadoFinalizadoId");
            buscarClaveEntera("grupoEstadoNoSepuedejecutarId");
            /**
             * GrupoAccion
             */
            buscarClaveEntera("grupoAccionBajarPlantillaId");
            buscarClaveEntera("grupoAccionReinicioRemotoId");
            buscarClaveEntera("grupoAccionEncenderSubirPlantillaId");

            /**
             * Estado
             *
             */
            buscarClaveEntera("estadoEnEsperaDeEjecucionId");
            buscarClaveEntera("estadoProcesandoId");
            buscarClaveEntera("estadoFinalizadoId");

            buscarClaveEntera("estadoAcciónNoSePuedeEjecutarId");
            buscarClaveEntera("estadoRobotNoDisponible");
/**
 * estado bajar plantilla
 */
            buscarClaveEntera("esatadoEnesperadeconfirmacióndelTécnico");
            buscarClaveEntera("estadoSolicituddedeshabilitaciónPlantillaenviadaaTelered");
            buscarClaveEntera("estadoSolicituddedeshabilitacióndePlantillaenProceso");
            buscarClaveEntera("estadoPlantillaDeshabilitada");
       
            

            /*            
            * estado reinicio remoto
            */
               buscarClaveEntera("estadoSolicitudEnviada");
               buscarClaveEntera("estadoSolicituddeReinicioRemotoenProceso");
               buscarClaveEntera("estadoReinicioRemotoCompletado");
               
            /*
               
               */ 
            buscarClaveEntera("estadoSolicituddeHabilitacióndePlantillaEnviada");
            buscarClaveEntera("estadoPlantillaHabilitadaenProceso");
            buscarClaveEntera("estadoPlantillaHabilitada");
               
            /**
             * Cantidad de caracteres
             */
            buscarClaveEntera("numeroCaracteresCortarTexto");

            /**
             * Leer del archivo de configuracion
             */
            InputStream inputStream = getClass()
                    .getClassLoader().getResourceAsStream("configuration.properties");
            Properties prop = new Properties();

            if (inputStream != null) {

                prop.load(inputStream);
                version = prop.getProperty("version");

                /**
                 * Lee del archivo de configuracion
                 */
                JsfUtil.propertiesStringToContext(prop, "prefijo");
                JsfUtil.propertiesStringToContext(prop, "applicativePath");

                /**
                 * Numero de caracteres que se puede cortar
                 */
            } else {
                JsfUtil.errorMessage("No se puede cargar el archivo configuration.properties");
            }
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
            ConsoleUtil.error(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return version;
    }
    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Boolean validateCredentials(Usuario usuario, String username, String password, Banco banco) ">

    @Override
    public Boolean validateCredentials(Usuario usuario, String username, String password, Banco banco) {
        try {
            if (banco == null || banco.getBANCOID() == null) {
                JsfUtil.warningMessage("Seleccione un banco");
                return Boolean.FALSE;
            }

            List<Usuario> list = usuarioRepository.findByUsername(username);
            if (list == null || list.isEmpty()) {

                JsfUtil.warningMessage("Username no está registrado");
                return Boolean.FALSE;
            }
            Usuario u = list.get(0);
            if (u.getACTIVO().toUpperCase().equals("NO")) {
                JsfUtil.warningMessage("El usuario no está activo en el sistema");
                return Boolean.FALSE;
            }
            if (!JsfUtil.desencriptar(u.getPASSWORD()).equals(password)) {
                JsfUtil.warningMessage("La constraseña no es correcta");
                return Boolean.FALSE;
            }

            usuario = u;
            /**
             * Guardar el context
             */
            JmoordbContext.put("user", usuario);
            JmoordbContext.put("banco", banco);

            return Boolean.TRUE;
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
            //ConsoleUtil.error(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Boolean validateCredentials(Usuario usuario, String username, String password) ">
    @Override
    public Boolean validateCredentials(Usuario usuario, String username, String password) {
        try {

            List<Usuario> list = usuarioRepository.findByUsername(username);
            if (list == null || list.isEmpty()) {

                JsfUtil.warningMessage("Username no está registrado");
                return Boolean.FALSE;
            }
            Usuario u = list.get(0);
            if (u.getACTIVO().toUpperCase().equals("NO")) {
                JsfUtil.warningMessage("El usuario no está activo en el sistema");
                return Boolean.FALSE;
            }
            if (!JsfUtil.desencriptar(u.getPASSWORD()).equals(password)) {
                JsfUtil.warningMessage("La contraseña no es correcta");
                return Boolean.FALSE;
            }

            usuario = u;
            /**
             * Guardar el context
             */
            JmoordbContext.put("user", usuario);

            return Boolean.TRUE;
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
            //ConsoleUtil.error(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Boolean validateCredentialsActiveDirectory( String username, String password)">
    @Override
    public Boolean validateCredentialsActiveDirectory(String username, String password) {
        try {
            //String usersContainer = "cn=Users, ou=Blueprism-RPA-Desarrollo, ou=Usuarios Genericos, DC=pim,DC=local,DC=pa";
            String usersContainer = "DC=pim,DC=local";
            ArrayList<String> list = new ArrayList<String>();
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, LDAP_URL);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            //  env.put(Context.SECURITY_PRINCIPAL, "CN=" + username+ "@pim.local,   DC=pim,DC=local");
            env.put(Context.SECURITY_PRINCIPAL, username + "@pim.local");
            env.put(Context.SECURITY_CREDENTIALS, password);
            DirContext ctx = new InitialDirContext(env);
            ctx.addToEnvironment("java.naming.referral", "follow");
            //ConsoleUtil.info("Voy a verificar los miembros");
            SearchControls ctls = new SearchControls();
            String[] attrIDs = {"cn", "memberOf"};
            //ConsoleUtil.info("Verificar atributos");
            ctls.setReturningAttributes(attrIDs);
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            //ConsoleUtil.info(" paso 1");
            String[] returnAttrs = {"cn"};
            ctls.setReturningAttributes(returnAttrs);
            String SEARCH_BASE = "CN=Users,DC=pim,DC=local";
            String FILTER = "(sAMAccountName=" + username + ")";
            //ConsoleUtil.info(" paso 2");
            NamingEnumeration results = ctx.search(SEARCH_BASE, FILTER, ctls);
            //ConsoleUtil.info(" paso 3");

            int count = 0;
            while (results.hasMoreElements()) {
                //ConsoleUtil.info(" paso 4 count " + count);

                count++;
                SearchResult result = (SearchResult) results.next();

                if (isMemberOfADGroup(ctx, "CN=telered,CN=Users,DC=pim,DC=local", "CN=" + username + ",CN=Users,DC=pim,DC=local")) {
                    //ConsoleUtil.info("Grupo Telered");
                }

                //ConsoleUtil.info(" paso 5");
                //print attributes:
                Attributes attrs = result.getAttributes();
                if (null != attrs) {
                    //ConsoleUtil.info(" paso 6");
                    for (NamingEnumeration ae = attrs.getAll(); ae.hasMoreElements();) {
                        Attribute atr = (Attribute) ae.next();
                        String attrId = atr.getID();
                        //ConsoleUtil.info("attrId " + atr.getID());
                        // //ConsoleUtil.info("Name "+atr.getName());
                        for (Enumeration vals = atr.getAll();
                                vals.hasMoreElements();
                                System.out.println(attrId + ": " + vals.nextElement()));
                    }
                }
            } //finish iterating through all users
            System.out.println("number of items: " + count);

            return Boolean.TRUE;
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
            //ConsoleUtil.error(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        //ConsoleUtil.info("Credenciales no validas");
        return Boolean.FALSE;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean validateCredentialsActiveDirectory( String username, String password, Banco banco)">
    @Override
    public Boolean validateCredentialsActiveDirectory(String username, String password, Banco banco) {
        try {
            //String usersContainer = "cn=Users, ou=Blueprism-RPA-Desarrollo, ou=Usuarios Genericos, DC=pim,DC=local,DC=pa";
            //String usersContainer = "DC=pim,DC=local"; //PIM
            String usersContainer = "DC=sclave,DC=com";
            ArrayList<String> list = new ArrayList<String>();
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, LDAP_URL);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            //  env.put(Context.SECURITY_PRINCIPAL, "CN=" + username+ "@pim.local,   DC=pim,DC=local");
            //env.put(Context.SECURITY_PRINCIPAL, username + "@pim.local");//pim
            env.put(Context.SECURITY_PRINCIPAL, username + "@telered.com.pa");
            env.put(Context.SECURITY_CREDENTIALS, password);
            DirContext ctx = new InitialDirContext(env);
            ctx.addToEnvironment("java.naming.referral", "follow");
            //ConsoleUtil.info("Voy a verificar los miembros");
            SearchControls ctls = new SearchControls();
            String[] attrIDs = {"cn", "memberOf"};
            //ConsoleUtil.info("Verificar atributos");
            ctls.setReturningAttributes(attrIDs);
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            //ConsoleUtil.info(" paso 1");
            String[] returnAttrs = {"mail", "name"};
            ctls.setReturningAttributes(returnAttrs);
            //  String SEARCH_BASE = "CN=Users,DC=pim,DC=local"; //PIM
            String SEARCH_BASE = "CN=Users,DC=sclave,DC=com";
            String FILTER = "(sAMAccountName=" + username + ")";
            //ConsoleUtil.info(" paso 2");
            NamingEnumeration results = ctx.search(SEARCH_BASE, FILTER, ctls);
            //ConsoleUtil.info(" paso 3");

            int count = 0;
            String eMail = "", sName = "";
            while (results.hasMoreElements()) {
                //ConsoleUtil.info(" paso 4 count " + count);

                count++;
                SearchResult result = (SearchResult) results.next();

                //ConsoleUtil.info(" paso 5");
                //print attributes:
                Attributes attrs = result.getAttributes();
                if (null != attrs) {
                    //ConsoleUtil.info(" paso 6");
                    for (NamingEnumeration ae = attrs.getAll(); ae.hasMoreElements();) {
                        Attribute atr = (Attribute) ae.next();
                        String attrId = atr.getID();

                        eMail = attrs.get("mail").toString();
                        sName = attrs.get("name").toString();
                        eMail = eMail.replaceAll("mail:", "");
                        sName = sName.replaceAll("name:", "");
                        ConsoleUtil.info("Email: " + eMail);
                        ConsoleUtil.info("Email: " + eMail);
                        ConsoleUtil.info("Name " + sName);
                        for (Enumeration vals = atr.getAll();
                                vals.hasMoreElements();
                                System.out.println(attrId + ": " + vals.nextElement()));
                    }
                }
            } //finish iterating through all users
            ConsoleUtil.info(" Usuario::" + sName);
            /* if (isMemberOfADGroup(ctx, "CN=telered,CN=Users,DC=pim,DC=local", "CN=" + sName.trim() + ",CN=Users,DC=pim,DC=local")) {
                   ConsoleUtil.info("Grupo Telered");
                }else{
                    ConsoleUtil.info("No es miembro de ningun grupo");
                      JsfUtil.warningMessage("No tiene grupo autorizado para el uso del sistema.");
                    return Boolean.FALSE;
                }*/
            if (isMemberOfADGroup(ctx, "OU=Blueprism-RPA-Desarrollo,OU=Usuarios Genericos,OU=Grupos y Usuarios de Aplicaciones o Servicios,OU=OU Dominio Telered (DEPURADO),DC=sclave,DC=com", "CN=" + sName.trim() + ",CN=Users,DC=sclave,DC=com")) {
                ConsoleUtil.info("Grupo Telered");
            } else {
                ConsoleUtil.info("No es miembro de ningun grupo");
                JsfUtil.warningMessage("No tiene grupo autorizado para el uso del sistema.");
                return Boolean.FALSE;
            }
            if (isMemberOfADGroup(ctx, "OU=Monitoreo y Redes,OU=Usuarios Panama Pacifico,OU=Panama Pacifico,OU=Department OU,OU=OU Dominio Telered (DEPURADO),DC=sclave,DC=com", "CN=" + sName.trim() + ",CN=Users,DC=sclave,DC=com")) {
                ConsoleUtil.info("Grupo Telered");
            } else {
                ConsoleUtil.info("No es miembro de ningun grupo");
                JsfUtil.warningMessage("No tiene grupo autorizado para el uso del sistema.");
                return Boolean.FALSE;
            }
            if (isMemberOfADGroup(ctx, "OU=Seguridad,OU=Usuarios Panama Pacifico,OU=Panama Pacifico,OU=Department OU,OU=OU Dominio Telered (DEPURADO),DC=sclave,DC=com", "CN=" + sName.trim() + ",CN=Users,DC=sclave,DC=com")) {
                ConsoleUtil.info("Grupo Telered");
            } else {
                ConsoleUtil.info("No es miembro de ningun grupo");
                JsfUtil.warningMessage("No tiene grupo autorizado para el uso del sistema.");
                return Boolean.FALSE;
            }
            System.out.println("number of items: " + count);
            List<Usuario> listUser = usuarioRepository.findByUsername(username);
            if (listUser == null || listUser.isEmpty()) {

                JsfUtil.warningMessage("Username no está registrado");

                Usuario usuario = new Usuario();
                usuario.setACTIVO("SI");
                usuario.setBANCOID(banco);
                usuario.setCEDULA("ad");
                usuario.setCELULAR("ad");
                usuario.setEMAIL(eMail.trim());
                usuario.setMODULOBANCO("NO");
                usuario.setMODULOCONTROLMANUAL("SI");
                usuario.setMODULOMANTENIMIENTO("SI");
                usuario.setMODULOTECNICO("NO");
                usuario.setNOMBRE(sName.trim());
                usuario.setORDEN(BigInteger.ONE);
                usuario.setPASSWORD(JsfUtil.encriptar(password));
                usuario.setTELEFONO("ad");
                usuario.setUSERNAME(username);
                ConsoleUtil.info("voy a crear usuario");
                if (usuarioRepository.create(usuario)) {
                    ConsoleUtil.info("creo el usuario");
                    JmoordbContext.put("user", usuario);
                    return Boolean.TRUE;
                } else {
                    ConsoleUtil.info("No sea crear usuario");
                    JsfUtil.warningMessage("No se logro ingresar el usuario a la base de datos");
                    return Boolean.FALSE;
                }

            } else {
                ConsoleUtil.info("voy a obtener el get");
                Usuario u = listUser.get(0);
                if (u.getACTIVO().toUpperCase().equals("NO")) {
                    JsfUtil.warningMessage("El usuario no está activo en el sistema");
                    return Boolean.FALSE;
                }
                if (!JsfUtil.desencriptar(u.getPASSWORD()).equals(password)) {
                    JsfUtil.warningMessage("La contraseña no es correcta");
                    return Boolean.FALSE;
                }
                JmoordbContext.put("user", u);
            }

            /**
             * Guardar el context
             */
            return Boolean.TRUE;
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
            //ConsoleUtil.error(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        //ConsoleUtil.info("Credenciales no validas");
        return Boolean.FALSE;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="boolean isMemberOfADGroup(DirContext ctx, String dnADGroup, String dnADUser) ">  
    public static boolean isMemberOfADGroup(DirContext ctx, String dnADGroup, String dnADUser) {
        try {
            DirContext lookedContext = (DirContext) (ctx.lookup(dnADGroup));
            Attribute attrs = lookedContext.getAttributes("").get("member");
            for (int i = 0; i < attrs.size(); i++) {
                String foundMember = (String) attrs.get(i);
                if (foundMember.equals(dnADUser)) {
                    return true;
                }
            }
        } catch (NamingException ex) {
            String msg = "There has been an error trying to determin a group membership for AD user with distinguishedName: " + dnADUser;
            System.out.println(msg);
            ex.printStackTrace();
        }
        return false;
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Boolean validateCredentialsActiveDirectory2(String username, String password, Banco banco)">  
    @Override
    public Boolean validateCredentialsActiveDirectory2(String username, String password, Banco banco) {
        try {
            //String usersContainer = "cn=Users, ou=Blueprism-RPA-Desarrollo, ou=Usuarios Genericos, DC=pim,DC=local,DC=pa";

            List<Usuario> listUser = usuarioRepository.findByUsername(username);
            if (listUser == null || listUser.isEmpty()) {

                JsfUtil.warningMessage("Username no está registrado");

                Usuario usuario = new Usuario();
                usuario.setACTIVO("SI");
                usuario.setBANCOID(banco);
                usuario.setCEDULA("ad");
                usuario.setCELULAR("ad");
                usuario.setEMAIL("ad");
                usuario.setMODULOBANCO("NO");
                usuario.setMODULOCONTROLMANUAL("SI");
                usuario.setMODULOMANTENIMIENTO("SI");
                usuario.setMODULOTECNICO("NO");
                usuario.setNOMBRE(username);
                usuario.setORDEN(BigInteger.ONE);
                usuario.setPASSWORD(JsfUtil.encriptar(password));
                usuario.setTELEFONO("ad");
                usuario.setUSERNAME(username);
                ConsoleUtil.info("voy a crear usuario");
                if (usuarioRepository.create(usuario)) {
                    ConsoleUtil.info("se creo el usuario");
                    JmoordbContext.put("user", usuario);
                    return Boolean.TRUE;
                } else {
                    ConsoleUtil.info("no se creo usuario");
                    JsfUtil.warningMessage("No se logro ingresar el usuario a la base de datos");
                    return Boolean.FALSE;
                }

            } else {
                ConsoleUtil.info("voy a ver el get(0)");
                Usuario u = listUser.get(0);
                if (u.getACTIVO().toUpperCase().equals("NO")) {
                    JsfUtil.warningMessage("El usuario no está activo en el sistema");
                    return Boolean.FALSE;
                }
                if (!JsfUtil.desencriptar(u.getPASSWORD()).equals(password)) {
                    JsfUtil.warningMessage("La contraseña no es correcta");
                    return Boolean.FALSE;
                }
                JmoordbContext.put("user", u);
            }

            /**
             * Guardar el context
             */
            return Boolean.TRUE;
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());

        }

        return Boolean.FALSE;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean buscarClaveEntera(String clave)">
    private Boolean buscarClaveEntera(String clave) {
        try {
            /*
                Desde la base de datos
             */

            Optional<Parametro> optional = ParametroRepository.findByClave(clave);
            if (optional.isPresent()) {

                JmoordbContext.put(clave, Integer.parseInt(optional.get().getVALOR()));
            } else {
                ConsoleUtil.info("No se encontro en la tabla parametros la propiedad " + clave);
                JsfUtil.warningMessage("No se encontro en la tabla parametros la propiedad: " + clave);
                JmoordbContext.put(clave, 0);
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean disableUser(String username)">
    @Override
    public Boolean disableUser(String username) {
        try {
            List<Usuario> list = usuarioRepository.findByUsername(username);
            if (list == null || list.isEmpty()) {
                JsfUtil.warningMessage("No se encontro ningun usuario con ese username");
            } else {
                Usuario usuario = list.get(0);
                usuario.setACTIVO("NO");
                if (usuarioRepository.update(usuario)) {
                    JsfUtil.warningMessage("El usuario fue desactivado. Consulte al administrador");
                    return Boolean.TRUE;
                }
            }

        } catch (Exception e) {
            JsfUtil.errorMessage(JsfUtil.nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }
    // </editor-fold>
}
