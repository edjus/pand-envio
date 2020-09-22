package pandenvio;

public class NoSePuedeMarcarComoNoEntregadoException extends RuntimeException {
    public NoSePuedeMarcarComoNoEntregadoException(String message) {
        super(message);
    }
}
