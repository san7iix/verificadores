package edu.unimagdalena.verificador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
/*-login: cadena que identifica al usuario en el sistema. Esta propiedad no puede ser modificada una vez ha sido establecida en el constructor.
- fecha último acceso: representa la última vez que el usuario realizó un acceso correcto en el sistema.
- password: cadena que representa la contraseña de acceso.
- historial de passwords: conjunto que almacena las contraseñas de acceso que ha utilizado el usuario.*/
    private final String login;
    private LocalDate fechaUltimoAcceso;
    private String password;
    private List<String> hitorialPassword;

    @Override
    public String toString() {
        return "Usuario{" +
                "login='" + login + '\'' +
                ", fechaUltimoAcceso=" + fechaUltimoAcceso +
                ", password='" + password + '\'' +
                ", hitorialPassword=" + hitorialPassword +
                '}';
    }

    /*En la construcción de un usuario se establecen las propiedades login y password.
        Inicialmente el historial de passwords está formado únicamente por la contraseña
        que se proporciona en la construcción.
        Además, se establece como fecha de último acceso el momento de la creación.
         */
    public Usuario(String login, String password) {
        this.login = login;
        this.password = password;
        hitorialPassword = new ArrayList<>();
        hitorialPassword.add(password);
        fechaUltimoAcceso = LocalDate.now();

    }

/*   La funcionalidad que ofrece este tipo de datos es la siguiente: */
//- Modificar password: esta operación tiene dos parámetros, 
//el password actual y el nuevo que queremos establecer. 
//La modificación del password se hará efectiva si el password actual es correcto y 
//la nueva contraseña no forma parte del historial de passwords. 
//Si se cumplen las dos condiciones, se actualiza la propiedad password 
//y la nueva contraseña se añadirá al historial de passwords.
// La operación retorna un valor booleano indicando si se ha realizado la modificación.
    public boolean modificarPassword(String passwordActual, String passwordNuevo){
        boolean modificado = false;
        if(validar(passwordActual) && !hitorialPassword.contains(passwordNuevo)){
            this.password = passwordNuevo;
            hitorialPassword.add(passwordNuevo);
            modificado = true;
        }
        return modificado;
    }

/*- Validar: esta operación se encarga de comprobar si la contraseña proporcionada como parámetro coincide
 con la contraseña del usuario. 
 La operación retorna un valor booleano indicando si la validación ha sido correcta.
*/
    public boolean validar(String passwordValidar){
        return passwordValidar.equals(this.password);
    }
/*- Establecer la fecha de último acceso. Esta operación no tiene parámetros. 
El objetivo es establecer como fecha de último acceso la fecha actual.
*/
    public void establecerUltimoAcceso(){
        this.fechaUltimoAcceso = LocalDate.now();
    }

    public LocalDate getFechaUltimoAcceso() {
        return fechaUltimoAcceso;
    }

    public List<String> getHitorialPassword() {
        return hitorialPassword;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    
}
