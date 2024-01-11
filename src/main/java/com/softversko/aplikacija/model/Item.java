package com.softversko.aplikacija.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="items")
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 3000)
    private String description;

    private String image;

}
