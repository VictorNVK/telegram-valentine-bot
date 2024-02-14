package com.example.ValentineBot.Entity;

import com.example.ValentineBot.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "valentine")
public class Valentine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String owner;

    @Column
    private String sender;

    @Column
    private String description;

    @Column
    private String header;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;


}
