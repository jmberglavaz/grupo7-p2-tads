package um.edu.uy.entities;

import java.util.Date;

public class Evaluacion {
    private int idUsuario;
    private float calificacion;
    private Date fecha;

    public Evaluacion(int idUsuario, float calificacion, Date fecha) {
        this.idUsuario = idUsuario;
        this.calificacion = calificacion;
        this.fecha = fecha;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
