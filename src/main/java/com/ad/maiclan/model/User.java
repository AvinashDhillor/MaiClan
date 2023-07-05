package com.ad.maiclan.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@ToString
public class User {

    @Id
    @JsonIgnore
    private String id;

    private String userId;

    @DocumentReference
    @JsonIgnore
    private Server server;

    @DocumentReference
    @JsonIgnore
    private Clan clan;

    private double totalScore;

    private boolean isActive;

    @JsonIgnore
    private LocalDateTime sessionStartTime;

    public void setTotalScore(double score) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        String roundedValue = decimalFormat.format(score);
        this.totalScore = Double.parseDouble(roundedValue) * 10.0;
    }
}
