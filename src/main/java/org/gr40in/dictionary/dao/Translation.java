package org.gr40in.dictionary.dao;

import com.sun.net.httpserver.Authenticator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "translation")
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String englishExpression;
    private String russianExpression;
    private String englishTranscription;

    private LocalDateTime initDate;

    private Integer successAttempts;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> users;

}