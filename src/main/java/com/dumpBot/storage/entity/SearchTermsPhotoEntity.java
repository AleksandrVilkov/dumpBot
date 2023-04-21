package com.dumpBot.storage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "searchtermsphoto")
@NoArgsConstructor
@Getter
@Setter
public class SearchTermsPhotoEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "telegram_id")
    private String telegramId;
    @ManyToOne
    @JoinColumn(name="search_terms_id", nullable = false)
    private SearchTermsEntity SearchTermsEntity;

}
