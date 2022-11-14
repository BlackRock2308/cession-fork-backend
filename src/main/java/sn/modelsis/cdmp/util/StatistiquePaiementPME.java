package sn.modelsis.cdmp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatistiquePaiementPME {
    private double solde;
    private double montantCreance;
    private double debource;


    public  void jsonToObeject(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        StatistiquePaiementPME statistiquePaiementPME = objectMapper.readValue(json, StatistiquePaiementPME.class);
        this.setDebource(statistiquePaiementPME.getDebource());
        this.setMontantCreance(statistiquePaiementPME.getMontantCreance());
        this.setSolde(statistiquePaiementPME.getSolde());

    }

}
