package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class MetricaGNS extends Model {

  @Id
  @GeneratedValue
  public Long id;

  @OneToOne(cascade = {CascadeType.ALL})
  public Evaluacion evaluacion;
  
  public float nivel_documentacion;

  public float nivel_disponibilidad_repo;

  public float nivel_documentacion_mejor;

  public float nivel_documentacion_todos;  

  public float periodicidad;

  public float condiciones;

  public float evaluacion_reportes;

  public float requisitos_nivel_servicio;

  public float responsable_nivel_servicio;

  public float total;

  public int slm01;

  public int slm02;

  public int slm03;

  public int slm04;

  public int slm05;

  public int slm06;

  public int slm07;

  public int slm08;

  public int slm09;
  
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

  public float getCondiciones() {
    return condiciones;
  }

  public void setCondiciones(float condiciones) {
    this.condiciones = condiciones;
  }

  public float getEvaluacion_reportes() {
    return evaluacion_reportes;
  }

  public void setEvaluacion_reportes(float evaluacion_reportes) {
    this.evaluacion_reportes = evaluacion_reportes;
  }

  public float getRequisitos_nivel_servicio() {
    return requisitos_nivel_servicio;
  }

  public void setRequisitos_nivel_servicio(float requisitos_nivel_servicio) {
    this.requisitos_nivel_servicio = requisitos_nivel_servicio;
  }
  
  public float getResponsable_nivel_servicio() {
    return responsable_nivel_servicio;
  }

  public void setResponsable_nivel_servicio(float responsable_nivel_servicio) {
    this.responsable_nivel_servicio = responsable_nivel_servicio;
  }

  public float getTotal() {
    return total;
  }

  public void setTotal(float total) {
    this.total = total;
  }

  public int getSlm01() {
    return slm01;
  }

  public void setSlm01(int slm01) {
    this.slm01 = slm01;
  }

  public int getSlm02() {
    return slm02;
  }

  public void setSlm02(int slm02) {
    this.slm02 = slm02;
  }

  public int getSlm03() {
    return slm03;
  }

  public void setSlm03(int slm03) {
    this.slm03 = slm03;
  }

  public int getSlm04() {
    return slm04;
  }

  public void setSlm04(int slm04) {
    this.slm04 = slm04;
  }

  public int getSlm05() {
    return slm05;
  }

  public void setSlm05(int slm05) {
    this.slm05 = slm05;
  }

  public int getSlm06() {
    return slm06;
  }

  public void setSlm06(int slm06) {
    this.slm06 = slm06;
  }

  public int getSlm07() {
    return slm07;
  }

  public void setSlm07(int slm07) {
    this.slm07 = slm07;
  }

  public int getSlm08() {
    return slm08;
  }

  public void setSlm08(int slm08) {
    this.slm08 = slm08;
  }

  public int getSlm09() {
    return slm09;
  }

  public void setSlm09(int slm09) {
    this.slm09 = slm09;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  //Constructor
  public MetricaGNS(Evaluacion evaluacion, float nivel_documentacion,
      float nivel_disponibilidad_repo, float nivel_documentacion_mejor,
      float nivel_documentacion_todos, float periodicidad,
      float condiciones, float evaluacion_reportes,
      float requisitos_nivel_servicio, float responsable_nivel_servicio,
      float total, int slm01, int slm02,
      int slm03, int slm04, int slm05, int slm06, int slm07, int slm08,
      int slm09) {
    super();
    this.evaluacion = evaluacion;
    this.nivel_documentacion = nivel_documentacion;
    this.nivel_disponibilidad_repo = nivel_disponibilidad_repo;
    this.nivel_documentacion_mejor = nivel_documentacion_mejor;
    this.nivel_documentacion_todos = nivel_documentacion_todos;
    this.periodicidad = periodicidad;
    this.condiciones = condiciones;
    this.evaluacion_reportes = evaluacion_reportes;
    this.requisitos_nivel_servicio = requisitos_nivel_servicio;
    this.responsable_nivel_servicio = responsable_nivel_servicio;
    this.total = total;
    this.slm01 = slm01;
    this.slm02 = slm02;
    this.slm03 = slm03;
    this.slm04 = slm04;
    this.slm05 = slm05;
    this.slm06 = slm06;
    this.slm07 = slm07;
    this.slm08 = slm08;
    this.slm09 = slm09;
  }
  
}