package com.example.attestation_security.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Table(name="goods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goodId;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    @Positive
    private double price;

    @Column(nullable=false)
    @Positive
    private int count;

    @Column(nullable = false)
    private String info;
    @Column(nullable = false, unique = true)
    private String vendorCode;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;
}

