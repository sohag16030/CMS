package com.cms.example.cms.common;

public final class Routes {

    //GEO_SERVICES_GET_BY_ID_ROUTES
    public static final String DIVISION_BY_ID_ROUTE = "/api/division/{divisionId}";
    public static final String DISTRICT_BY_ID_ROUTE = "/api/district/{districtId}";
    public static final String UPAZILA_BY_ID_ROUTE = "/api/upazila/{upazilaId}";

    //GEO_SERVICES_LIST_RETURN_ROUTES
    public static final String DIVISION_LIST_ROUTE = "/api/divisions";
    public static final String DISTRICT_LIST_ROUTE = "/api/districts";
    public static final String UPAZILA_LIST_ROUTE = "/api/upazilas";

    //CMS_USER_SERVICES_ROUTES
    public static final String CMS_USER_SIGN_UP_ROUTE = "/api/cmsUser";
    public static final String CMS_USER_BY_ID_ROUTE = "/api/cmsUser/{userId}";
    public static final String CMS_USER_DELETE_BY_ID_ROUTE = "/api/cmsUser/{userId}";
    public static final String CMS_USER_UPDATE_BY_ID_ROUTE = "/api/cmsUser/{cmsUserId}";
    public static final String CMS_USER_LIST_ROUTE = "/api/cmsUsers";

    //USER_CONTENTS_UPLOAD_SERVICES_ROUTES
    public static final String CONTENT_UPLOAD_ROUTE = "/api/contents";
    public static final String CONTENT_UPDATE_BY_ID_ROUTE = "/api/content/{contentId}";
    public static final String CONTENT_DOWNLOAD_BY_ID_ROUTE = "/api/content/download/{contentId}";
    public static final String CONTENT_BY_ID_ROUTE = "/api/content/{contentId}";
    public static final String CONTENT_LIST_ROUTE = "/api/contents";
    public static final String CONTENT_DELETE_BY_ID_ROUTE = "/api/content/{contentId}";

    //AUTH
    public static final String USER_LOGIN = "/user/accessToken";
    public static final String USER_ACCESS_TOKEN_FROM_REFRESH_TOKEN = "/user/getAccessTokenFromRefreshToken";
    public static final String USER_ROLE_ASSIGNMENT = "/user/{userId}/permission";
    public static final String USER_LOGOUT = "/user/logout";


}

