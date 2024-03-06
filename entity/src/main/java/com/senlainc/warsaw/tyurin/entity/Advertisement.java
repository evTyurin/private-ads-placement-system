package com.senlainc.warsaw.tyurin.entity;

import com.senlainc.warsaw.tyurin.enums.AdvertisementStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "advertisement")
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "customer_id")
    private User customer;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "seller_id")
    private User seller;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private double price;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AdvertisementStatus status;
    @Column(name = "created_at")
    private LocalDateTime creationDateTime;
    @Column(name = "is_paid")
    private boolean isPaid;
    @Column(name = "paid_till")
    private LocalDateTime paidTillDateTime;
    @Column(name = "paid_sum")
    private double paidSum;
    @Column(name = "sold_at")
    private LocalDateTime soldDateTime;
    @Column(name = "sold_sum")
    private double soldSum;
    @OneToMany(mappedBy="advertisement")
    private Set<Comment> comments;
}
