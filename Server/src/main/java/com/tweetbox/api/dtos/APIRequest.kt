package com.tweetbox.api.dtos

import java.util.SortedMap

open class APIRequest(var url: String, var data: SortedMap<String, String>) {
    var oauth_token: String?
        get() {
            return data.get("oauth_token");
        }
        set(value) {
            data.put("oauth_token", value);
        }
    var user_secret_key: String?
        get() {
            return data.get("user_secret_key");
        }
        set(value) {
            data.put("user_secret_key", value);
        }
}