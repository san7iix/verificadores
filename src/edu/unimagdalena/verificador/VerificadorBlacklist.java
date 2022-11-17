package edu.unimagdalena.verificador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * Un verificador blacklist se caracteriza por tener una colección de usuarios (logins de usuarios) 
 * que han sido bloqueados en el sistema. 
 * Para la gestión de esta colección se ofrecen un par de operaciones para darlos de alta y baja. 
 * Por tanto, no podrá superarse el primer paso del proceso de verificación (loginPaso1) 
 * si el usuario ha sido bloqueado.
Por otra parte, un verificador blacklist tiene como petición del desafío: “Introduzca el número del mes de su último acceso”. 
Por tanto, la respuesta correcta al desafío será el texto que corresponda al día del mes del último acceso del usuario al sistema.
Nota: la clase java.time.LocalDate permite consultar el día del mes de una fecha con el método getMonthValue.
 */
public class VerificadorBlacklist extends Verificador{
    private List<String> loginsUsuariosBloqueados;

    
    public VerificadorBlacklist() {
        super("Introduzca el número del mes de su último acceso");
        this.loginsUsuariosBloqueados = new ArrayList<>();
    }

    @Override
    public String generarRespuesta() {
        List<Usuario> usuarios = getUsuarios();
        LocalDate fechaMayor = usuarios.get(0).getFechaUltimoAcceso();
        for(Usuario u: usuarios){
            if(fechaMayor.isBefore(u.getFechaUltimoAcceso())){
                fechaMayor = u.getFechaUltimoAcceso();
            }
        }
        return String.valueOf(fechaMayor.getMonthValue());
    }
    
    public void altaUsuariosBloqueados(String login){
        loginsUsuariosBloqueados.add(login);
    }
    public void bajaUsuarioBloqueados(String login){
        loginsUsuariosBloqueados.remove(login);
    }

    @Override
    public String loginPaso1(String login, String password) {
        if(loginsUsuariosBloqueados.contains(login))
            return null;
        return super.loginPaso1(login, password);
    }

    
}
