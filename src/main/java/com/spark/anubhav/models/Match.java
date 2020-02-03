package com.spark.anubhav.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Match {
    @Id
    private UUID id;
    private String displayName;
    private Integer age;
    private String jobTitle;
    @ManyToOne(cascade = CascadeType.ALL)
    private City city;
    private Integer height;
    private String name;
    private String mainPhoto;
    private BigDecimal compatibilityScore;
    private Integer numberOfContactsExchanged;
    private Boolean favourite;
    private String religion;
    private UUID userId;

}
