package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Callback {
    String userId;
    CallbackSubsection subsection;
    Action Action;
    UserData userData;
    CarData carData;
}

@Getter
@Setter
class UserData {
    String regionName;
    int regionId;
}

@Getter
@Setter
class CarData {
    String Concern;
    String Brand;
    String Model;
    String EngineName;
    String BoltPatternSize;
}

