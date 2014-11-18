package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class MetricaGIN extends Model {

  @Id
  @GeneratedValue
  public Long id;

  @OneToOne(cascade = {CascadeType.ALL})
  public Evaluacion evaluacion;
  
  public float procedimientos;

  public float herramienta_gin;

  public float clasificacion;

  public float detalle_registro;

  public float reportes;  

  public float periodicidad;

  public float responsable;

  public float total;

  public int im01;

  public int im02;

  public int im03;

  public int im04;

  public int im05;

  public int im06;

  public int im07;
  
  @Formats.DateTime(pattern="dd/MM/yyyy")
  public Date dueDate = new Date();
  public static Finder<Long,Evaluacion> find = new Finder<Long,Evaluacion>(
    Long.class, Evaluacion.class
  );

  //Busqueda metrica por id de Evaluacion
  
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

  public float getProcedimientos() {
    return procedimientos;
  }

  public void setProcedimientos(float procedimientos) {
    this.procedimientos = procedimientos;
  }

  public float getHerramienta_gin() {
    return herramienta_gin;
  }

  public void setHerramienta_gin(float herramienta_gin) {
    this.herramienta_gin = herramienta_gin;
  }

  public float getClasificacion() {
    return clasificacion;
  }

  public void setClasificacion(float clasificacion) {
    this.clasificacion = clasificacion;
  }

  public float getDetalle_registro() {
    return detalle_registro;
  }

  public void setDetalle_registro(float detalle_registro) {
    this.detalle_registro = detalle_registro;
  }

  public float getReportes() {
    return reportes;
  }

  public void setReportes(float reportes) {
    this.reportes = reportes;
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

  public int getIm01() {
    return im01;
  }

  public void setIm01(int im01) {
    this.im01 = im01;
  }

  public int getIm02() {
    return im02;
  }

  public void setIm02(int im02) {
    this.im02 = im02;
  }

  public int getIm03() {
    return im03;
  }

  public void setIm03(int im03) {
    this.im03 = im03;
  }

  public int getIm04() {
    return im04;
  }

  public void setIm04(int im04) {
    this.im04 = im04;
  }

  public int getIm05() {
    return im05;
  }

  public void setIm05(int im05) {
    this.im05 = im05;
  }

  public int getIm06() {
    return im06;
  }

  public void setIm06(int im06) {
    this.im06 = im06;
  }

  public int getIm07() {
    return im07;
  }

  public void setIm07(int im07) {
    this.im07 = im07;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  //Constructor
  public MetricaGIN(Evaluacion evaluacion, float procedimientos,
      float herramienta_gin, float clasificacion, float detalle_registro,
      float reportes, float periodicidad, float responsable, float total,
      int im01, int im02, int im03, int im04, int im05, int im06, int im07) {
    super();
    this.evaluacion = evaluacion;
    this.procedimientos = procedimientos;
    this.herramienta_gin = herramienta_gin;
    this.clasificacion = clasificacion;
    this.detalle_registro = detalle_registro;
    this.reportes = reportes;
    this.periodicidad = periodicidad;
    this.responsable = responsable;
    this.total = total;
    this.im01 = im01;
    this.im02 = im02;
    this.im03 = im03;
    this.im04 = im04;
    this.im05 = im05;
    this.im06 = im06;
    this.im07 = im07;
  }

}