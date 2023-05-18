package com.dumpBot.storage.storage;

import com.dumpBot.model.UserAccommodation;
import com.dumpBot.storage.IAccommodationStorage;
import com.dumpBot.storage.entity.UserAccommodationEntity;
import com.dumpBot.storage.repository.UserAccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccommodationStorage implements IAccommodationStorage {

    @Autowired
    UserAccommodationRepository accommodationRepository;

    @Override
    public boolean saveAccommodation(UserAccommodation accommodation) {
        try {
            accommodationRepository.save(convertToEntity(accommodation));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private UserAccommodationEntity convertToEntity(UserAccommodation accommodation) {
        UserAccommodationEntity result = new UserAccommodationEntity();
        result.setCreatedDate(accommodation.getCreatedDate());
        result.setClientLogin(accommodation.getClientLogin());
        result.setClientId(accommodation.getClientId());
        result.setMinPrice(accommodation.getPrice());
        result.setMaxPrice(accommodation.getPrice());
        result.setApproved(accommodation.isApproved());
        result.setRejected(accommodation.isRejected());
        result.setTopical(accommodation.isTopical());
        result.setDescription(accommodation.getDescription());
        result.setType(accommodation.getType().toString());
        //TODO посмотреть как связать фото
        return result;
    }
}
