package edu.unimagdalena.verificador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/*
 * Un verificador representa el tipo de datos encargado de verificar la identificad de un usuario
 *  en el acceso a un sistema. Para ello se ofrece un proceso de verificación en dos pasos.
 *  En el primer paso (loginPaso1) se comprueba que las credenciales del usuario (login y password) 
 * sean correctas. Si se supera el primer paso, 
 * el usuario deberá superar un desafío, esto es, debe dar respuesta a una petición. 
 * En el segundo paso, el usuario establecerá la respuesta al desafío propuesto (loginPaso2). 
 * Los dos pasos del proceso están vinculados a través de un token. 
 * Si el primer paso es superado, se obtiene un token que deberá utilizarse para establecer 
 * la respuesta al desafío
 */
public abstract class Verificador {
    /*
     * - usuarios: colección de usuarios que gestiona.
            Requisito: la colección será representada como un mapa, 
            donde la clave será la propiedad login del Usuario y el valor asociado el objeto Usuario. 
            El método de consulta de la propiedad retornará una colección de usuarios (los valores del mapa).
        - petición del desafío: es una cadena de texto con la información necesaria 
          para que el usuario responda al segundo paso del proceso de verificación. 
          Por ejemplo, “Introduzca el día del mes de su último acceso”. 
          Esta propiedad no cambia una vez establecida en la construcción.
     */
    private Map<String, Usuario> usuarios;

    @Override
    public String toString() {
        return "Verificador{" +
                "usuarios=" + usuarios +
                ", peticionDesafio='" + peticionDesafio + '\'' +
                ", mapaDesafios=" + mapaDesafios +
                '}';
    }

    public String getPeticionDesafio() {
        return peticionDesafio;
    }
    public Map<String, String> getMapaDesafios() {
        return mapaDesafios;
    }
    private String peticionDesafio;
    private Map<String, String> mapaDesafios;
    
    //Un verificador se construye estableciendo la petición del desafío.
    public Verificador(String peticionDesafio) {
        this.peticionDesafio = peticionDesafio;
        usuarios = new HashMap<>();
        mapaDesafios = new HashMap<>();
    }
    /*
     * - Añadir usuarios. Esta operación tiene como propósito añadir nuevos usuarios al conjunto de usuarios gestionado por el verificador. 
     * Tiene como parámetro un argumento de tamaño variable de tipo Usuario.
     */
    public void anadirUsuario(Usuario... nuevosUsuarios){
        for(Usuario usuario: nuevosUsuarios){
            this.usuarios.put(usuario.getLogin(), usuario);
        }
    }
    /*
     * Borrar un usuario. El método tiene como parámetro el objeto usuario que se desea eliminar del verificador. 
     * Retorna un valor booleano informando si la acción ha sido efectiva.
     */
    public boolean borrarUsuario(Usuario usuario){
        return this.usuarios.remove(usuario.getLogin(), usuario);
    }

    public List<Usuario> getUsuarios(){
        return new ArrayList<>(usuarios.values());
    }

    /*
     * Primer paso del proceso de verificación (loginPaso1). Esta operación tiene como parámetros dos cadenas que representan las credenciales 
     * del usuario: login y password. 
     * La operación retorna una cadena (token, necesario para el paso 2). 
     * Es requisito para implementar esta operación aplicar el concepto de método plantilla. 
     * Los pasos que realiza esta operación son los siguientes:
• Comprueba que el usuario esté incluido en el mapa de usuarios. Si no está incluido, se retorna un valor nulo (no hay token).
• Comprueba que las credenciales sean válidas, es decir, que el password sea válido para el usuario. Si no es así, retorna un valor nulo.
• Genera el token que será retornado por la operación.
Nota: utiliza UUID.randomUUID().toString() para obtenerlos.
• Genera la respuesta correcta al desafío (cadena). El modo de generar esta respuesta depende del tipo de verificador (se explica más adelante).
• Registra en un mapa de desafíos la asociación entre el token y la respuesta correcta.
Nota: el mapa de desafíos es un atributo de implementación, es decir, no es una propiedad del tipo de datos. Por tanto, 
no debe ofrecerse un método para su consulta.
• Por último, retorna el token.
     */
    public String loginPaso1(String login, String password){
        String newToken;
        Usuario usuario;
        String respuesta;
        if(!this.usuarios.containsKey(login)) {
           return null;
        }
        usuario = this.usuarios.get(login);
        if(!usuario.validar(password))
            return null;
        
        newToken = UUID.randomUUID().toString();
        respuesta = generarRespuesta();

        mapaDesafios.put(newToken, respuesta);

        return newToken;
    }

    public abstract String generarRespuesta();
    /**
     * Segundo paso del proceso de verificación (loginPaso2). Esta operación se encarga de finalizar el proceso de verificación. 
     * Los parámetros de la operación son: una cadena que representa un token (obtenido en el paso 1) 
     * y una cadena con la respuesta del usuario al desafío. La operación retorna un valor booleano indicando si el proceso de verificación 
     * ha sido superado. Los pasos que realiza esta operación son los siguientes:
• Comprueba que el token se encuentre registrado en el mapa de desafíos. Si no es así, retorna el valor falso (no existe el desafío).
• Comprueba que la respuesta del usuario sea correcta. 
Para ello, se obtiene la respuesta correcta asociada al token del mapa de desafíos y comprueba si coinciden. 
Si no son iguales, se elimina el token del mapa de desafíos y se retorna el valor falso (no supera el desafío).
• Una vez superadas las comprobaciones anteriores, el desafío ha sido superado. 
Se elimina la entrada asociada al token del mapa de desafíos y finaliza retornando el valor verdadero.
     */
    public boolean loginPaso2(String token, String respuestaUsuarioDesafio){
        String respuestaCorrecta;
        if(!this.mapaDesafios.containsKey(token)) return false;
        respuestaCorrecta = this.mapaDesafios.get(token);
        if(!respuestaCorrecta.equals(respuestaUsuarioDesafio)) {
           this.mapaDesafios.remove(token);
            return false;
        }
        this.mapaDesafios.remove(token);
        return true;

    }
}
