package controllers;

import play.*;
import play.mvc.*;
import play.data.Form;
//import static play.data.Form.*;
import java.util.Map;
//import play.api.libs.Crypto;
import java.util.Arrays;
//import java.text.DecimalFormat;
//import java.math.*;
import play.api.libs.json.Json;
import play.mvc.BodyParser;
import play.libs.Json.*;
import static play.libs.Json.toJson;                          
//import org.codehaus.jackson.JsonNode;           
//import org.codehaus.jackson.node.ObjectNode; 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import models.*;

import views.html.*;

public class Procesos extends Controller {

    //Cuestionario_Gestion de Catalogo de Servicios
    public static Result cuestionarioGCS() {
    	return ok(proc1_gcs_q.render());
    }

    //Cuestionario_Gestion de Nivel de Servicios
    public static Result cuestionarioGNS() {
    	return ok(proc1_gcs_q.render());
    }

    //Cuestionario_Gestion de Incidentes
    public static Result cuestionarioGIN() {
    	return ok(proc1_gcs_q.render());
    }

    //GESTION DE CATALOGO DE SERVICIOS
    //Guardar datos de cuestionario y generar metricas
    public static Result evaluacionGCS() {
        Map<String, String[]> formData = request().body().asFormUrlEncoded();
        String[] tipo_servicio = formData.get("tipo_servicio");
        String numero_servicios = String.valueOf(formData.get("numero_servicios")[0]);
        String[] doc_servicio = formData.get("doc_servicio");
        String numero_descritos = String.valueOf(formData.get("numero_descritos")[0]);
        String[] tipo_almacen = formData.get("tipo_almacen");
        String[] clientes_acc = formData.get("clientes_acc");
        String[] tec_acc = formData.get("tec_acc");
        String[] adm_acc = formData.get("adm_acc");
        String[] doc_completo = formData.get("doc_completo");
        String numero_cumplen = String.valueOf(formData.get("numero_cumplen")[0]);
        String periodicidad = formData.get("periodicidad")[0];
        String num_revisados = formData.get("num_revisados")[0];
        String adm_doc = formData.get("adm_doc")[0];
        String reuniones = formData.get("reuniones")[0];
        String actualizado = formData.get("actualizado")[0];

        String respuestas = new String();
        respuestas =    Arrays.toString(tipo_servicio)+"_"+
                        numero_servicios+"_"+
                        Arrays.toString(doc_servicio)+"_"+
                        numero_descritos+"_"+
                        Arrays.toString(tipo_almacen)+"_"+
                        Arrays.toString(clientes_acc)+"_"+
                        Arrays.toString(tec_acc)+"_"+
                        Arrays.toString(adm_acc)+"_"+
                        Arrays.toString(doc_completo)+"_"+
                        numero_cumplen+"_"+
                        periodicidad+"_"+
                        num_revisados+"_"+
                        adm_doc+"_"+
                        reuniones+"_"+
                        actualizado;

        //Grabar evaluacion
        Evaluacion evaluacion = new Evaluacion(
                Usuario.getUserByUsername(session("username")),
                "Gestion de Catalogo de Servicios",
                respuestas);
        evaluacion.save();
                
        //Generar metricas
        float l_nivel_documentacion = 
            (Float.parseFloat(numero_descritos))/(Float.parseFloat(numero_servicios))*100.0f;
        
        float max = 0.0f;
        for (int i=0; i < tipo_almacen.length; i++) {
            if (max < Float.parseFloat(tipo_almacen[i]) )
                max = Float.parseFloat(tipo_almacen[i]);
        }
        float l_nivel_disponibilidad_repo = (max);

        float val1 = 0.0f;
        for (int i=0; i < clientes_acc.length; i++) {
            if(clientes_acc[i].equals("1")){
                val1 = 1.0f;
            }
        }
        float val2 = 0.0f;
        for (int i=0; i < tec_acc.length; i++) {
            if(tec_acc[i].equals("1")){
                val2 = 1.0f;
            }
        }
        float val3 = 0.0f;
        for (int i=0; i < adm_acc.length; i++) {
            if(adm_acc[i].equals("1")){
                val3 = 1.0f;
            }
        }
        float l_accesibilidad = val1*50 + val2*25 + val3*25;

        float sum = 0.0f;
        for (int i=0; i < doc_completo.length; i++) {
            sum += Float.parseFloat(doc_completo[i]);
        }
        float l_nivel_documentacion_mejor = (sum/8)*100.0f;

        float l_nivel_documentacion_todos =
            ((Float.parseFloat(numero_cumplen)/Float.parseFloat(numero_servicios))) * 100.0f;

        float l_periodicidad = 
            ((Float.parseFloat(periodicidad))*(Float.parseFloat(num_revisados))) / 100.0f;

        float l_responsable = (
            (Float.parseFloat(adm_doc)) *
            (Float.parseFloat(reuniones)) *
            (Float.parseFloat(actualizado)) ) / 10000.0f;

        float l_total = 
            l_nivel_documentacion * 0.10f +
            l_nivel_disponibilidad_repo * 0.10f +
            l_accesibilidad * 0.15f +
            l_nivel_documentacion_mejor * 0.20f +
            l_nivel_documentacion_todos * 0.10f +
            l_periodicidad * 0.20f +
            l_responsable * 0.15f;

        //Evaluacion CMMI
        int scm01 = 0;
        int scm02 = 0;
        int scm03 = 0;
        int scm04 = 0;
        int scm05 = 0;
        int scm06 = 0;
        int scm07 = 0;
        //><
        if(l_nivel_documentacion == 0)
            scm01 = 1;
        else if(l_nivel_documentacion == 100)
            scm01 = 3;
        else
            scm01 = 2;

        if(l_nivel_disponibilidad_repo == 0)
            scm02 = 1;
        else if(l_nivel_disponibilidad_repo >= 50)
            scm02 = 3;
        else
            scm02 = 2;

        if(l_accesibilidad <= 25)
            scm03 = 1;
        else if(l_accesibilidad >= 51)
            scm03 = 3;
        else
            scm03 = 2;

        if(l_nivel_documentacion_mejor == 0)
            scm04 = 1;
        else if(l_nivel_documentacion_mejor >= 50)
            scm04 = 3;
        else
            scm04 = 2;

        if(l_nivel_documentacion_todos == 0)
            scm05 = 1;
        else if(l_nivel_documentacion_todos >= 50)
            scm05 = 3;
        else
            scm05 = 2;

        if(l_periodicidad <= 25)
            scm06 = 1;
        else if(l_periodicidad >= 51)
            scm06 = 3;
        else
            scm06 = 2;

        if(l_responsable <= 25)
            scm07 = 1;
        else if(l_responsable >= 51)
            scm07 = 3;
        else
            scm07 = 2;
        
        //Truncar a 2 decimales
        //DecimalFormat df = new DecimalFormat("##.##");
        //df.setRoundingMode(RoundingMode.DOWN);
        
        //Grabar a BD
        MetricaGCS metrica = new MetricaGCS(
            evaluacion,
            l_nivel_documentacion,
            l_nivel_disponibilidad_repo,
            l_accesibilidad,
            l_nivel_documentacion_mejor,
            l_nivel_documentacion_todos,
            l_periodicidad,
            l_responsable,
            l_total,
            scm01,
            scm02,
            scm03,
            scm04,
            scm05,
            scm06,
            scm07);
        metrica.save();

        //Guardar evaluacion ID en sesion
        session("evid", String.valueOf((int)(long) evaluacion.getId()));
        
        return ok(proc1_gcs_r.render(metrica));
    }

    //Javascript Routing
    public static Result javascriptRoutes() {
    response().setContentType("text/javascript");
    return ok(
        Routes.javascriptRouter("myJsRoutes",
            routes.javascript.Procesos.getEvaluacion())
        );
    }

    public static Result getEvaluacion() {
        //obtener la ultima evaluacion guardada en sesion
        Long evid = Long.parseLong(session("evid"));
        Evaluacion eval = Evaluacion.getEvaluacionById(evid);
        MetricaGCS met = eval.getMetricas_gcs();
        //crear el objeto json con los datos de evaluacion
        JSONObject jo = new JSONObject();
        try {
            jo.put("year", "SCM01");
            jo.put("value", met.getScm01());
            jo.put("year", "SCM02");
            jo.put("value", met.getScm02());
            jo.put("year", "SCM03");
            jo.put("value", met.getScm03());
            jo.put("year", "SCM04");
            jo.put("value", met.getScm04());
            jo.put("year", "SCM05");
            jo.put("value", met.getScm05());
            jo.put("year", "SCM06");
            jo.put("value", met.getScm06());
            jo.put("year", "SCM07");
            jo.put("value", met.getScm07());
         } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        JSONArray ja = new JSONArray();
        ja.put(jo);
        return ok(ja.toString());
    }

    //GESTION DE NIVEL DE SERVICIO
    public static Result evaluacionGNS() {
        return ok(proc1_gcs_q.render());
    }

    //GESTION DE INCIDENTES
    public static Result evaluacionGIN() {
        return ok(proc1_gcs_q.render());
    }

}
