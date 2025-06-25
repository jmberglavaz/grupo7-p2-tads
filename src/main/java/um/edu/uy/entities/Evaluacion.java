package um.edu.uy.entities;


import java.util.Date;

public class Evaluacion {
    private Integer idUsuario;
    private float calificacion;
    private Integer fechaMes;

    public Evaluacion(int idUsuario, float calificacion, Date fechaMes) {
        this.idUsuario = idUsuario;
        this.calificacion = calificacion;
        this.fechaMes = fechaMes.getMonth();
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public int getFechaMes() {
        return fechaMes;
    }

    public void setFechaMes(int fechaMes) {
        this.fechaMes = fechaMes;
    }
}
