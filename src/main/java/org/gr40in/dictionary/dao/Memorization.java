package org.gr40in.dictionary.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memorization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "translation_id")
    Translation translation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    private LocalDateTime initDate;

    private Integer successAttempts;
}
