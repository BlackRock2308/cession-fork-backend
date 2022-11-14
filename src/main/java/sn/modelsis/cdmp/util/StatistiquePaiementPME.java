package sn.modelsis.cdmp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatistiquePaiementPME {
    private double solde;
    private double montantCreance;
    private double debource;


    public  void jsonToObeject(String json) {
      json = json.replaceAll("null", "0.0");
       String [] objs = json.split(",");
       List<Double> donne = new ArrayList<>();
       for(String obj : objs ){
               donne.add(Double.parseDouble(obj));
       }
            this.setDebource(donne.get(2));
            this.setMontantCreance(donne.get(1));
            this.setSolde(donne.get(0));

    }

}
