package com.project.together.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name")
    private String name;

    @Column(name = "item_price")
    private int price;

    @Column(name = "item_contents")
    private String contents;

    @Column(name = "item_level")
    private String itemLevel; //아이템 등급

    @Enumerated(EnumType.STRING)
    private Enul enul; // 에눌 가능

    @Enumerated(EnumType.STRING)
    private DealForm dealForm; //1이면 택배 2면 직거래 3이면 둘다 가능

    @Column(name = "item_seller")
    private String seller;

    @Embedded
    private Address address;

    private LocalDateTime createdAt;

    private int wishCount = 0;

    @Column(name = "item_buyer")
    private String buyer;

    @Column(name = "buy_date")
    private LocalDateTime buyDate;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "manner_item")
    private Long mannerItem;//1이면 매너인이 등록한 아이템 아니면 0

    @OneToMany(mappedBy = "item")
    private List<ItemCategory> itemCategories = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    public void addWishCount() {
        this.wishCount++;
    }

    public void removeWishCount() {
        this.wishCount--;
    }

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus; //ENUM [SELLING(판매중), SOLD(판매 완료)]

}
