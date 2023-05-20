package com.dumpBot.storage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "searchannouncementphoto")
@NoArgsConstructor
@Getter
@Setter
public class AnnouncementPhotoEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "telegram_id")
    private int telegramId;
    @ManyToOne
    @JoinColumn(name = "user_accommodation_id", nullable = false)
    private UserAccommodationEntity userAccommodationEntity;
}
