package pandenvio

class Cliente {
    String nombre;
    String apellido;
    String mail;
    Ubicacion ubicacion;
    String telefono;
    static hasMany = [cupones: CuponDescuentoPorcentual]
    
    String nombreCompleto() {
        nombre + " " + apellido
    }

    boolean esValido() {
      return true
   }
}