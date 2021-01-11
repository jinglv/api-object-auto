package com.api.object.apiobject;

import lombok.Data;

import java.util.HashMap;

/**
 * @author jingLv
 * @date 2021/01/11
 */
@Data
public class Restful {

    public String url;
    public String method;
    public HashMap<String, Object> header = new HashMap<>();
    public HashMap<String, Object> query = new HashMap<>();
    public HashMap<String, Object> pathQuery = new HashMap<>();
    public String body;
}
