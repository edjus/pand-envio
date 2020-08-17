package pandenvio

class Cliente {
    String nombre;
    String apellido;
    String mail;
    Ubicacion ubicacion;
    String telefono;

    String nombreCompleto() {
        return nombre + " " + apellido
    }
}