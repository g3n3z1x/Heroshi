package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class Evaluacion extends Model {

  @Id
  @GeneratedValue
  public Long id;

  @OneToOne(cascade = {CascadeType.ALL})
  public MetricaGCS metricas_gcs;

  @ManyToOne
  public Usuario usuario;
  
  @Constraints.Required
  public String tipo;

  public String respuestas;
  
  @Formats.DateTime(pattern="dd/MM/yyyy")
  public Date dueDate = new Date();
  public static Finder<Long,Evaluacion> find = new Finder<Long,Evaluacion>(
    Long.class, Evaluacion.class
  );

  //Busqueda evaluacion en BD
  public static Evaluacion getEvaluacionById(Long id){
    Evaluacion evaluacion = find.where().eq("id",id).findUnique();
    return evaluacion;
  }

  //GyS
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public MetricaGCS getMetricas_gcs() {
    return metricas_gcs;
  }
  public void setMetricas_gcs(MetricaGCS metricas_gcs) {
    this.metricas_gcs = metricas_gcs;
  }
  public Usuario getUsuario() {
    return usuario;
  }
  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }
  public String getTipo() {
    return tipo;
  }
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
  public String getRespuestas() {
    return respuestas;
  }
  public void setRespuestas(String respuestas) {
    this.respuestas = respuestas;
  }
  public Date getDueDate() {
    return dueDate;
  }
  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  //Constructor
  public Evaluacion(Usuario usuario, String tipo, String respuestas) {
    super();
    this.usuario = usuario;
    this.tipo = tipo;
    this.respuestas = respuestas;
  }

}