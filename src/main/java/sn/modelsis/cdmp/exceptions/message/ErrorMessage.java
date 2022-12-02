package sn.modelsis.cdmp.exceptions.message;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter

//@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@SuperBuilder
public class ErrorMessage {
    private int status;
    private Date timestamp;
    private String message;
    private String description;

    public ErrorMessage(int status, Date timestamp, String message, String description) {
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }
}
