package com.dumpBot.processor;

import com.dumpBot.common.Util;
import com.dumpBot.model.Callback;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
@Setter
@Getter
public class BaseProcess {
    @Autowired
    ResourcesHelper resourcesHelper;

    private String saveTempWithToken(Callback callback) {
        String token = Util.newMd5FromCalBack(callback);
        resourcesHelper.getStorage().saveTempData(token, callback);
        return token;
    }
}
