package pandenvio
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Cliente {
    String nombre;
    String apellido;
    String mail;
    Ubicacion ubicacion;
    String telefono;
    static hasMany = [cupones: CuponDescuentoPorcentual]

    Cliente () {
        this.cupones = []
    }
    
    String nombreCompleto() {
        nombre + " " + apellido
    }

    boolean esValidoElMail() {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(this.mail);
        mat.matches();
    }
}