package com.tweetbox.api.data

import com.tweetbox.TweetBoxConfig
import com.tweetbox.api.dtos.APIRequest
import java.lang.System.currentTimeMillis
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.math.ceil


class OAuth(var mapValues: SortedMap<String, String>, var tweetBoxConfig: TweetBoxConfig) {
    init {
        mapValues.put("oauth_version", "1.0");
        mapValues.put("oauth_consumer_key", tweetBoxConfig.consumerKey);
        mapValues.put("oauth_signature_method", "HMAC-SHA1");
        mapValues.put("oauth_timestamp", "");
        mapValues.put("oauth_nonce", "");
        mapValues.put("oauth_consumer_secret", tweetBoxConfig.consumerSecretKey);
        mapValues.put("oauth_signature", "");
        mapValues.put("oauth_token", "");//사용자 공개키, 매번 입력 받는다
        mapValues.put("user_secret_key", "");//사용자 비밀키, 매번 입력 받는다
        mapValues.put("oauth_callback", tweetBoxConfig.callbackUrl);

    }

    var user_secret_key: String?
        get() {
            return this.mapValues.get("user_secret_key");
        }
        set(value) {
            this.mapValues["user_secret_key"] = value;
        }

    var oauth_token: String?
        get() {
            return this.mapValues.get("oauth_token");
        }
        set(value) {
            this.mapValues["oauth_token"] = value;
        }

    var oauth_consumer_secret: String?
        get() {
            return this.mapValues.get("oauth_consumer_secret");
        }
        set(value) {
            this.mapValues["oauth_consumer_secret"] = value;
        }

    var oauth_timestamp: String?
        get() {
            return this.mapValues.get("oauth_timestamp");
        }
        set(value) {
            this.mapValues["oauth_timestamp"] = value;
        }

    var oauth_nonce: String?
        get() {
            return this.mapValues.get("oauth_nonce");
        }
        set(value) {
            this.mapValues["oauth_nonce"] = value;
        }

    var oauth_signature: String?
        get() {
            return this.mapValues.get("oauth_signature");
        }
        set(value) {
            this.mapValues["oauth_signature"] = value;
        }

    fun encodeURIComponent(value: String): String? {
        return java.net.URLEncoder.encode(value, "utf-8")
    }

    fun setKey(publicKey: String, secretKey: String) {
        this.user_secret_key = secretKey;
        this.oauth_token = publicKey;
    }

    fun urlEncode(value: String): String {
        if (value == "") return value;
        var ret = value;
        ret = ret.replace(Regex("/!/gi"), "%21");
        ret = ret.replace(Regex("/\\(/gi"), "%28");
        ret = ret.replace(Regex("/\\)/gi"), "%29");
        ret = ret.replace(Regex("/\\*/gi"), "%2A");
        ret = ret.replace(Regex("/\\'/gi"), "%27");

        return ret;
    }


    fun isCalcKey(key: String): Boolean {
        //oauth계산에 써야되는 값인지 체크
        return key.indexOf("secret") == -1 && key !== "media" && key !== "media_data";
    }


    fun calcParamUri(text: String): String {
        var str = "";
        var limit = 100;
        if (text == null) return str;
        if (text.length > limit) {
            //media등은 길어서 나눠서 해야됨
            var loops = ceil((text.length.toDouble() / limit.toDouble())).toInt();
            var i = 0;
            for (i in 0..loops - 1) {
                var startIndex = 100 * i;
                var endIndex = (i + 1) * limit;
                if (endIndex >= text.length) {
                    endIndex = text.length;
                }
                str += encodeURIComponent(text.substring(startIndex, endIndex));
            }
        } else {
            str += encodeURIComponent(text);
        }
        str = this.urlEncode(str);
        return str;
    }

    fun createBody(params: APIRequest?): String {
        if (params == null || params.data != null) return "";

        var str = "";

        for (item in mapValues) {
            str += "$item.key=${this.calcParamUri(item.value)}&";
        }
        return str.substring(0, str.length - 1); //마지막& 지우기
    }

    fun getUrl(params: APIRequest?, url: String, isQueryParam: Boolean): String {
        if (!isQueryParam) {
            return url;
        } else {
            if (params?.data != null) {
                var str = "$url?";
                for (item in mapValues) {
                    if (item.value.isNotEmpty() && this.isCalcKey(item.key))
                        str += "${item.key}=${encodeURIComponent(item.value)}&";
                }
                return str.substring(0, str.length - 1); //마지막& 지우기
            } else {
                return url;
            }
        }
        return url;
    }


    fun getHeader(params: APIRequest?, method: String, url: String): String {
        this.calcSignature(params, method, url);

        var str = "OAuth ";

        for (item in mapValues) {
            if (item.value.isNotEmpty() && this.isCalcKey(item.key)) {
                str += "${item.key}=${this.calcParamUri(item.value)},"
            }
        }
        str = str.substring(0, str.length - 1); //마지막, 지우기
        return str;
    }

    fun createTimeStamp() {
        var a = currentTimeMillis().toBigDecimal();
        var b = 1000.toBigDecimal();
        this.oauth_timestamp = (a / b).toString();
    }

    fun createOAuthNonce() {
        var tick = currentTimeMillis();
        val encoder: Base64.Encoder = Base64.getEncoder()
        val encoded: String = encoder.encodeToString(tick.toString().toByteArray());
        this.oauth_nonce = encoded;
//        this.oauth_nonce = "MTY2MjQ2MTA4Mjc0NzY4MDA=";
    }

    fun calcSignature(params: APIRequest?, method: String, url: String) {
        this.createTimeStamp();
        this.createOAuthNonce();

        var str = "";

        for (item in mapValues) {
            if (item.value.isNotEmpty() && this.isCalcKey(item.key)) {
                str += "${item.key}=${this.calcParamUri(item.value)}&";
            }
        }

        str = str.substring(0, str.length - 1); //마지막& 지우기
        var baseStr = this.calcBaseString(method, url, str);
        var signKey = "";
        if (!this.user_secret_key.isNullOrEmpty()) {
            signKey = "$oauth_consumer_secret&$user_secret_key";
        } else {
            signKey = "$oauth_consumer_secret&";
        }
        var hash = hmacSha1(baseStr, signKey);
        val encoder: Base64.Encoder = Base64.getEncoder()
        val encoded: String = encoder.encodeToString(hash);
        this.oauth_signature = encoded;
    }

    fun calcBaseString(method: String, url: String, paramStr: String): String {
        return method + '&' + this.calcParamUri(url) + '&' + this.calcParamUri(paramStr);
    }

    private fun hmacSha1(value: String, key: String): ByteArray {
        val type = "HmacSHA1"
        val secret = SecretKeySpec(key.toByteArray(), type)
        val mac = Mac.getInstance(type)
        mac.init(secret)
        val bytes = mac.doFinal(value.toByteArray())
        return bytes;
//        return bytesToHex(bytes)
    }

    private val hexArray = "0123456789abcdef".toCharArray()

    private fun bytesToHex(bytes: ByteArray): String? {
        val hexChars = CharArray(bytes.size * 2)
        var v: Int
        for (j in bytes.indices) {
            v = bytes[j].toInt() and 0xFF
            hexChars[j * 2] = hexArray[v ushr 4]
            hexChars[j * 2 + 1] = hexArray[v and 0x0F]
        }
        return String(hexChars)
    }
}