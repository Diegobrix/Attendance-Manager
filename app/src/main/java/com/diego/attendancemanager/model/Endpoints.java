package com.diego.attendancemanager.model;

public class Endpoints {
    private static final String baseUrl = "http://192.168.0.13/eventosFatec/";
    public static final String userLogin = baseUrl + "app/user/credentials/login.php";
    public static final String userReg = baseUrl + "app/user/credentials/register.php";
    public static final String getAvatar = baseUrl + "app/user/avatar/getAvatar.php";
    public static final String getEvent = baseUrl + "app/events/getEvents/getEvent.php";
    public static final String updateAvatar = baseUrl + "app/user/avatar/setAvatar.php";
    public static final String getComingEvents = baseUrl + "app/events/getEvents/getComingEvents.php";
    public static final String getUserData = baseUrl + "app/user/getUser.php";
    public static final String userSubscriptionInEvent = baseUrl + "app/events/subscription/eventSubscription.php";
}
