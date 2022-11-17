import edu.unimagdalena.verificador.Usuario;
import edu.unimagdalena.verificador.Verificador;
import edu.unimagdalena.verificador.VerificadorBlacklist;
import edu.unimagdalena.verificador.VerificadorPorCodigo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class App {
    public static void main(String[] args) throws Exception {
        Usuario usuario = new Usuario("fperez", "lechemerengada");
        usuario.modificarPassword("lechemerengada", "cr7comeback");
        Usuario usuario2 = new Usuario("mlama", "tururu");

        AtomicReference<String> login = new AtomicReference<>("");
        AtomicReference<String> password = new AtomicReference<>("");
        AtomicReference<String> rtaDesafio = new AtomicReference<>("");


        Scanner scanner = new Scanner(System.in);

        VerificadorBlacklist verificadorBlacklist = new VerificadorBlacklist();
        VerificadorPorCodigo verificadorPorCodigo = new VerificadorPorCodigo(5);

        verificadorPorCodigo.anadirUsuario(usuario,usuario2);
        verificadorBlacklist.anadirUsuario(usuario,usuario2);

        List<Verificador> verificadores = new ArrayList<Verificador>();
        verificadores.add(verificadorBlacklist);
        verificadores.add(verificadorPorCodigo);

        verificadores.forEach(verificador -> {
            if(verificador.getClass() == VerificadorBlacklist.class){
                ((VerificadorBlacklist) verificador).altaUsuariosBloqueados("mlama");
            }
        });

        verificadores.forEach(verificador -> {
            System.out.println("Ingrese su login");
            login.set(scanner.nextLine());
            System.out.println("Ingrese su password");
            password.set(scanner.nextLine());

            String token = verificador.loginPaso1(login.get(), password.get());
            if(token != null){
                System.out.println(verificador.getPeticionDesafio());
                rtaDesafio.set(scanner.nextLine());
                System.out.println(verificador.loginPaso2(token, rtaDesafio.get()));
            }
        });

        List<Verificador> copia = new ArrayList<Verificador>();
        verificadores.forEach(verificador -> {
            copia.add(verificador);
        });

        copia.forEach(verificador -> {
            System.out.println(verificador);
        });
    }
}
