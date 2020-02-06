package com.spark.anubhav.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Match {
    @Id
    private UUID id;
    private String displayName;
    private Integer age;
    private String jobTitle;
    @ManyToOne(cascade = CascadeType.ALL)
    private City city;
    private Integer height;
    private URL mainPhoto;
    private BigDecimal compatibilityScore;
    private Integer numberOfContactsExchanged;
    private Boolean favourite;
    private String religion;
    private UUID userId;

    public void setId(UUID id) {
        this.id = id;
    }
}
