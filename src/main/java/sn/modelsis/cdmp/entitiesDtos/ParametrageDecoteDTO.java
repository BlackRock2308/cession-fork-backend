package sn.modelsis.cdmp.entitiesDtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
public class ParametrageDecoteDTO implements Serializable {

    private Long borneInf;

    private Long borneSup;

    private Double decoteValue;
}
