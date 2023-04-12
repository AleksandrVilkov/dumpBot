package model;

public enum ChatMemberStatus {
    MEMBER,      //пользователь является подписчиком;
    LEFT,      //пользователь не подписан;
    KICKED,     // пользователь заблокирован;
    ADMIN,//админ
    CREATOR,      //создатель
}
