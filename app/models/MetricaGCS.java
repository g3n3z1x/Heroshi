package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class MetricaGCS extends Model {

  @Id
  @GeneratedValue
  public Long id;

  @OneToOne(cascade = {CascadeType.ALL})
  public Evaluacion evaluacion;
  
  public float nivel_documentacion;

  public float nivel_disponibilidad_repo;

  public float accesibilidad;

  public float nivel_documentacion_mejor;

  public float nivel_documentacion_todos;  

  public float periodicidad;

  public float responsable;

  public float total;

  public int scm01;

  public int scm02;

  public int scm03;

  public int scm04;

  public int scm05;

  public int scm06;

  public int scm07;
  
  @Formats.DateTime(pattern="dd/MM/yyyy")
  public Date dueDate = new Date();
  public static Finder<Long,Evaluacion> find = new Finder<Long,Evaluacion>(
    Long.class, Evaluacion.class
  );
  
  //GyS
public Long getId() {
  return id;
}
public void setId(Long id) {
  this.id = id;
}
public Evaluacion getEvaluacion() {
  return evaluacion;
}
public void setEvaluacion(Evaluacion evaluacion) {
  this.evaluacion = evaluacion;
}
public float getNivel_documentacion() {
  return nivel_documentacion;
}
public void setNivel_documentacion(float nivel_documentacion) {
  this.nivel_documentacion = nivel_documentacion;
}
public float getNivel_disponibilidad_repo() {
  return nivel_disponibilidad_repo;
}
public void setNivel_disponibilidad_repo(float nivel_disponibilidad_repo) {
  this.nivel_disponibilidad_repo = nivel_disponibilidad_repo;
}
public float getAccesibilidad() {
  return accesibilidad;
}
public void setAccesibilidad(float accesibilidad) {
  this.accesibilidad = accesibilidad;
}
public float getNivel_documentacion_mejor() {
  return nivel_documentacion_mejor;
}
public void setNivel_documentacion_mejor(float nivel_documentacion_mejor) {
  this.nivel_documentacion_mejor = nivel_documentacion_mejor;
}
public float getNivel_documentacion_todos() {
  return nivel_documentacion_todos;
}
public void setNivel_documentacion_todos(float nivel_documentacion_todos) {
  this.nivel_documentacion_todos = nivel_documentacion_todos;
}
public float getPeriodicidad() {
  return periodicidad;
}
public void setPeriodicidad(float periodicidad) {
  this.periodicidad = periodicidad;
}
public float getResponsable() {
  return responsable;
}
public void setResponsable(float responsable) {
  this.responsable = responsable;
}
public float getTotal() {
  return total;
}
public void setTotal(float total) {
  this.total = total;
}
public Date getDueDate() {
  return dueDate;
}
public void setDueDate(Date dueDate) {
  this.dueDate = dueDate;
}
  public int getScm01() {
    return scm01;
  }

  public void setScm01(int scm01) {
    this.scm01 = scm01;
  }

  public int getScm02() {
    return scm02;
  }

  public void setScm02(int scm02) {
    this.scm02 = scm02;
  }

  public int getScm03() {
    return scm03;
  }

  public void setScm03(int scm03) {
    this.scm03 = scm03;
  }

  public int getScm04() {
    return scm04;
  }

  public void setScm04(int scm04) {
    this.scm04 = scm04;
  }

  public int getScm05() {
    return scm05;
  }

  public void setScm05(int scm05) {
    this.scm05 = scm05;
  }

  public int getScm06() {
    return scm06;
  }

  public void setScm06(int scm06) {
    this.scm06 = scm06;
  }

  public int getScm07() {
    return scm07;
  }

  public void setScm07(int scm07) {
    this.scm07 = scm07;
  }

  //Constructor
public MetricaGCS(Evaluacion evaluacion, float nivel_documentacion, float nivel_disponibilidad_repo,
    float accesibilidad, float nivel_documentacion_mejor,
    float nivel_documentacion_todos, float periodicidad,
    float responsable, float total, int scm01, int scm02,
    int scm03, int scm04, int scm05, int scm06, int scm07) {
  super();
  this.evaluacion = evaluacion;
  this.nivel_documentacion = nivel_documentacion;
  this.nivel_disponibilidad_repo = nivel_disponibilidad_repo;
  this.accesibilidad = accesibilidad;
  this.nivel_documentacion_mejor = nivel_documentacion_mejor;
  this.nivel_documentacion_todos = nivel_documentacion_todos;
  this.periodicidad = periodicidad;
  this.responsable = responsable;
  this.total = total;
  this.scm01 = scm01;
  this.scm02 = scm02;
  this.scm03 = scm03;
  this.scm04 = scm04;
  this.scm05 = scm05;
  this.scm06 = scm06;
  this.scm07 = scm07;            
  }

}