package edu.unimagdalena.verificador;

import java.util.Random;

/*
 * Un verificador por código tiene como petición del desafío: “Introduzca el número que ha recibido por SMS”. 
 * La respuesta correcta al desafío es un único valor que se genera de forma aleatoria entre 0 y 999 (ambos incluidos).
Nota 1: utiliza la clase java.util.Random para la generación de número aleatorios.
Nota 2: en un sistema real el verificador por código enviaría el número aleatorio al usuario a través del correo electrónico o como SMS al número de móvil. Con el fin de poder probar la aplicación, se mostrará el código generado por la salida de error (System.err).
Por último, un verificador por código limita el número de intentos para la superación del paso 2 de la verificación. 
Este límite se establece en la construcción y no puede cambiar. 
Si un usuario falla al intentar superar el desafío el número de veces fijado por ese límite, el token será eliminado del mapa de desafíos.
 */
public class VerificadorPorCodigo extends Verificador{
    private final int numeroMaximoIntentos;
    private int intentos = 0;
    public VerificadorPorCodigo(int numeroMaximoIntentos) {
        super("Introduzca el número que ha recibido por SMS");
        this.numeroMaximoIntentos = numeroMaximoIntentos;
        //TODO Auto-generated constructor stub
    }

    @Override
    public String generarRespuesta() {
        String valor = String.valueOf(new Random().nextInt(1000));
        System.err.println(valor);
        return valor;
    }

    @Override
    public boolean loginPaso2(String token, String respuestaUsuarioDesafio) {
        boolean ejecucion = super.loginPaso2(token, respuestaUsuarioDesafio);
        intentos++;
        if(!ejecucion && intentos > numeroMaximoIntentos ){
            getMapaDesafios().remove(token);
            return false;
        }
        return ejecucion;
    }
    
}
