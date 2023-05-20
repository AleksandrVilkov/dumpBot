package com.dumpBot.storage.storage;

import com.dumpBot.common.Util;
import com.dumpBot.model.UserAccommodation;
import com.dumpBot.model.enums.AccommodationType;
import com.dumpBot.storage.IAccommodationStorage;
import com.dumpBot.storage.entity.UserAccommodationEntity;
import com.dumpBot.storage.repository.UserAccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<UserAccommodation> getAll() {
        List<Object[]> data = accommodationRepository.getAll();
        List<UserAccommodation> result = new ArrayList<>();
        for (Object o : data) {
            //TODO
        }
        return result;
    }

    @Override
    public List<UserAccommodation> getAllInconsistent() {
        List<Object[]> data = accommodationRepository.getAllInconsistent();
        List<UserAccommodation> result = new ArrayList<>();
        for (Object[] o : data) {
            UserAccommodation userAccommodation = new UserAccommodation();
            userAccommodation.setId((Integer) o[0]);
            userAccommodation.setCreatedDate((Date) o[1]);
            userAccommodation.setType(Util.findEnumConstant(AccommodationType.class, (String) o[2]));
            userAccommodation.setClientLogin((String) o[3]);
            userAccommodation.setClientId((Integer) o[4]);
            userAccommodation.setPrice((Integer) o[6]);
            userAccommodation.setApproved((Boolean) o[7]);
            userAccommodation.setRejected((Boolean) o[8]);
            userAccommodation.setTopical((Boolean) o[9]);
            userAccommodation.setDescription((String) o[10]);
            //TODO фото
            result.add(userAccommodation);
        }
        return result;
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
