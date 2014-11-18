package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import java.util.List;

@Entity 
public class Evaluacion extends Model {

  @Id
  @GeneratedValue
  public Long id;

  @OneToOne(cascade = {CascadeType.ALL})
  public MetricaGCS metricas_gcs;

  @OneToOne(cascade = {CascadeType.ALL})
  public MetricaGNS metricas_gns;

  @OneToOne(cascade = {CascadeType.ALL})
  public MetricaGIN metricas_gin;

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

  //Busqueda todas las evaluaciones de un usuario
  public static List<Evaluacion> getEvaluacionAll(Long uid){
    List<Evaluacion> evlist = find.where().eq("usuario_id",uid).findList();
    return evlist;
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
  public MetricaGNS getMetricas_gns() {
    return metricas_gns;
  }
  public void setMetricas_gns(MetricaGNS metricas_gns) {
    this.metricas_gns = metricas_gns;
  }
  public MetricaGIN getMetricas_gin() {
    return metricas_gin;
  }
  public void setMetricas_gin(MetricaGIN metricas_gin) {
    this.metricas_gin = metricas_gin;
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