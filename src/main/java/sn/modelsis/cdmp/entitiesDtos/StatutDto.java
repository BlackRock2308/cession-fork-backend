package sn.modelsis.cdmp.entitiesDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatutDto {
    private Long idStatut;
    private String code;
    private String libelle;
}
