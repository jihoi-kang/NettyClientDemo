package com.example.kjh.nettyclientdemo.netty.serializer;

import com.google.gson.Gson;

/**
 * 객체를 JSON으로
 * JSON을 객체로 편리하게 변환해주는 클래스
 * @author 강지회
 * @version 1.0.0
 * @since 2019. 4. 13. PM 10:02
 **/
public class Serializer {

    private static Gson gson;

    private static Gson getGson() {
        if (gson == null) {
            synchronized (Serializer.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }

    public static String serialize(Object object) {
        return getGson().toJson(object);
    }

    public static <T> T deserialize(String json, Class<T> clazz) {
        return getGson().fromJson(json, clazz);
    }
}
