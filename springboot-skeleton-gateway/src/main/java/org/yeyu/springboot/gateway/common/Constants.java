package org.yeyu.springboot.gateway.common;

import java.util.HashSet;
import java.util.Set;

/**
 * @author kris
 * @date 2022/9/16
 */
public class Constants {

    public static String JWT_HS256 = "HS256";

    public static String HTTP_HEADER_TOKEN = "token";

    public static String HTTP_CONTENT_JSON_TYPE = "application/json;charset=UTF-8";

    public static String HTTP_HEADER_APPID = "appId";

    public static String HTTP_HEADER_LUCAS_USER_ID = "Lucas-UserId";

    public static String HTTP_HEADER_LUCAS_STAFF_ID = "Lucas-StaffId";
    public static String HTTP_HEADER_LUCAS_TENANT_ID = "Lucas-TenantId";

    public static String HTTP_HEADER_ADMIN_ID = "Lucas-AdminId";

    public static String HTTP_TOKEN_STAFF_ID = "staff_id";
    public static String HTTP_TOKEN_TENANT_ID = "tenant_id";

    public static String JWT_USER_ID = "user_id";




    public static final String ORIGIN = "Origin";
    public static final String Access_Control_Allow_Origin = "Access-Control-Allow-Origin";
    public static final String Access_Control_Allow_Credentials = "Access-Control-Allow-Credentials";
    public static final String Access_Control_Allow_Methods = "Access-Control-Allow-Methods";
    public static final String Access_Control_Request_Headers = "Access-Control-Request-Headers";
    public static final String Access_Control_Allow_Headers = "Access-Control-Allow-Headers";
    public static final String TraceId = "X-B3-TraceId";
    public static final String X_FRAME_OPTIONS = "X-Frame-Options";
    public static final String X_XSS_Protection = "X-XSS-Protection";
    public static final String VARY = "Vary";
    public static final String ACCESS_CONTROL_ALLOW_METHODS_VALUE ="GET,POST,PUT,DELETE,OPTIONS";


    public static Set<String> headers = new HashSet<>();

    static {
        headers.add(Access_Control_Allow_Origin);
        headers.add(Access_Control_Allow_Credentials);
        headers.add(Access_Control_Allow_Methods);
        headers.add(Access_Control_Request_Headers);
        headers.add(Access_Control_Allow_Headers);
        headers.add(VARY);
        headers.add(TraceId);
        headers.add(X_FRAME_OPTIONS);
        headers.add(X_XSS_Protection);
    }

}
