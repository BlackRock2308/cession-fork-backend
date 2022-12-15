package sn.modelsis.cdmp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

    @Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name="text_convention")
    public class TextConvention implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "var1")
        private String var1;

        @Column(name = "var2")
        private String var2;

        @Column(name = "var3")
        private String var3;

        @Column(name = "var4")
        private String var4;

        @Column(name = "var5")
        private String var5;

        @Column(name = "var6")
        private String var6;
}
