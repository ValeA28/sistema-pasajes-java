package Modelo;

/**
 * Clase para manejar al usuario logueado en sesión actual.
 */
public class SesionUsuario {

    private static Pasajero usuarioActual;

    public static void iniciarSesion(Pasajero p) {
        usuarioActual = p;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }

    public static Pasajero getUsuarioActual() {
        return usuarioActual;
    }

    public static boolean haySesionActiva() {
        return usuarioActual != null;
    }
}

