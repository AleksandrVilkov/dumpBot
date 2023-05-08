package com.dumpBot.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Photo {
    @Value("${resources.msgs.photo.noRegistration}")
    private String noRegistration;
    @Value("${resources.msgs.photo.withOrWithoutPhoto}")
    private String withOrWithoutPhoto;
    @Value("${resources.msgs.photo.givePhoto}")
    private String givePhoto;
    @Value("${resources.msgs.photo.info}")
    private String info;
    @Value("${resources.msgs.photo.noPhoto}")
    private String noPhoto;
    @Value("${resources.msgs.photo.noAction}")
    private String noAction;
    @Value("${resources.msgs.photo.successSavedToLastCallBack}")
    private String successSavedToLastCallBack;
}
