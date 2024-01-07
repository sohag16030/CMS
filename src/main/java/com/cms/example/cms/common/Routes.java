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
    public static final String CMS_USER_CREATE_ROUTE = "/api/cmsUser/create";
    public static final String CMS_USER_UPDATE_ROUTE = "/api/cmsUser/update";
    public static final String CMS_USER_BY_ID_ROUTE = "/api/cmsUser/{cmsUserId}";
    public static final String CMS_USER_LIST_ROUTE = "/api/cmsUsers";

    //USER_RATING_SERVICES_ROUTES
    public static final String USER_RATING_BY_ID_ROUTE = "/api/userRating/{userRatingId}";

}

