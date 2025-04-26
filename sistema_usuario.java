import java.io.*;
import java.util.*;

class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombreUsuario;
    private String contraseña;

    public Usuario(String nombreUsuario, String contraseña) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public boolean verificarContraseña(String contraseña) {
        return this.contraseña.equals(contraseña);
    }
}

public class SistemaUsuarios {
    private static final String ARCHIVO_USUARIOS = "usuarios.dat";
    private static List<Usuario> listaUsuarios = new ArrayList<>();

    public static void main(String[] args) {
        cargarUsuarios();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("1. Registrar usuario");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt(); 
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    registrarUsuario(scanner);
                    break;
                case 2:
                    iniciarSesion(scanner);
                    break;
                case 3:
                    guardarUsuarios();
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void registrarUsuario(Scanner scanner) {
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        
        for (Usuario u : listaUsuarios) {
            if (u.getNombreUsuario().equals(nombreUsuario)) {
                System.out.println("El nombre de usuario ya está en uso.");
                return;
            }
        }
        
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();
        listaUsuarios.add(new Usuario(nombreUsuario, contraseña));
        guardarUsuarios();
        System.out.println("Usuario registrado exitosamente.");
    }

    private static void iniciarSesion(Scanner scanner) {
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();
        
        for (Usuario u : listaUsuarios) {
            if (u.getNombreUsuario().equals(nombreUsuario) && u.verificarContraseña(contraseña)) {
                System.out.println("Acceso concedido.");
                return;
            }
        }
        System.out.println("Nombre de usuario o contraseña incorrectos.");
    }

    private static void cargarUsuarios() {
        File archivo = new File(ARCHIVO_USUARIOS);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                listaUsuarios = (List<Usuario>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("No se pudieron cargar los usuarios.");
            }
        }
    }

    private static void guardarUsuarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_USUARIOS))) {
            oos.writeObject(listaUsuarios);
        } catch (IOException e) {
            System.out.println("Error al guardar los usuarios.");
        }
    }
}
