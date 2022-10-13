package sn.modelsis.cdmp.entitiesDtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString
public class PaiementDto {

    private Long idPaiement;

    private int montant;

    private double montantRecuCDMP;

    private double soldePME;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime datePaiement;

    private StatutDto statutdto;
}
