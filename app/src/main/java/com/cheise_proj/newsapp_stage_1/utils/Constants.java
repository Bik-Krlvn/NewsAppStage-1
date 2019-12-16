package com.cheise_proj.newsapp_stage_1.utils;

public class Constants {
    static final String BASE_URL = "http://content.guardianapis.com/search?";
    static final String MAX_NEWS = "20";

    public static final String JAVASCRIPT_DATA_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String LOCAL_DATE_FORMAT = "EEEE, dd.MM.yyyy";

    static final int READ_TIMEOUT = 10000;
    static final int CONNECT_TIMEOUT = 15000;
    static final int HTTP_RESPONSE_CODE_CODE = 200;
    static final String REQUEST_METHOD = "GET";

    static final String KEY_SHOW_TAGS = "show-tags";
    static final String KEY_CONTRIBUTOR = "contributor";
    static final String KEY_SHOW_FIELD = "show-fields";
    static final String KEY_ALL = "all";
    static final String KEY_PAGE_SIZE = "page-size";
    static final String API_KEY = "api-key";
    static final String API_KEY_VALUE = "7362288d-2544-4762-95a2-71abcdf09dbb";

    static final String JSON_KEY_RESPONSE = "response";
    static final String JSON_KEY_RESULTS = "results";
    static final String JSON_KEY_WEB_TITLE = "webTitle";
    static final String JSON_KEY_SECTION_NAME = "sectionName";
    static final String JSON_KEY_WEB_PUBLICATION_DATE = "webPublicationDate";
    static final String JSON_KEY_WEB_URL = "webUrl";
    static final String JSON_KEY_TAGS = "tags";
    static final String JSON_KEY_AUTHOR = "webTitle";
    static final String JSON_KEY_FIELDS = "fields";
    static final String JSON_KEY_THUMBNAIL = "thumbnail";

    public static final int Feed_LOADER_ID = 101;
}
