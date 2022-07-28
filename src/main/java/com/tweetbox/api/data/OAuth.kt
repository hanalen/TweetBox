package com.tweetbox.api.data

import java.lang.System.currentTimeMillis
import kotlin.math.ceil
import kotlin.math.floor

class OAuth {
    val oauth_version: String = "1.0";
    var oauth_consumer_key: String = "";
    var oauth_signature_method: String = "HMAC-SHA1";
    var oauth_timestamp: String = "";
    var oauth_nonce: String = "";
    var oauth_consumer_secret: String = "";
    var oauth_signature: String = "";
    var oauth_token: String = ""; //사용자 공개키, 매번 입력 받는다
    var user_secret_key: String = ""; //사용자 비밀키, 매번 입력 받는다

    inline fun <reified T:Any> T.getProperties(): List<String> = T::class.java.getProperties()
        .map { it.getDelegate(this) }
        .filter(T::class::isInstance)
        .map(T::class::safeCast)
        .filterNotNull()
        .distinct()
        .toList()

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
            var loops = ceil((text.length / limit) as Double) as Int;
            var i = 0;
            for (i in 0..loops - 1) {
                str += encodeURIComponent(text.substring(100 * i, (i + 1) * limit));
            }
        } else {
            str += encodeURIComponent(text);
        }
        str = this.UrlEncode(str);
        return str;
    }

    fun <T> createBody(params: APIRequest<T>?): String {
        if (params == null || params.data != null) return "";

        var str = "";

        Object.entries(params.data) //params 오브젝트의 파라메터 이름, 값을 얻는 코드
            .sort()
            .forEach(([key, value]) => {
                if (value || value === 0) str += `${key}=${this.CalcParamUri(value)}&`;
            });
        str += '&';
        return str.substring(0, str.length - 1); //마지막& 지우기
    }

    fun <T> getUrl(params: APIRequest<T>?, url: String, isQueryParam: Boolean): String {
        if (!isQueryParam) {
            return url;
        } else {
            if (params?.data != null) {
                var str = "$url?";
                Object.entries(params.data) //params 오브젝트의 파라메터 이름, 값을 얻는 코드
                    .sort()
                    .forEach(([key, value]) => {
                        // console.log('key: ' + key + 'value: ' + value);
                        if ((value || value === 0) && this.isCalcKey(key))
                            str += `${key}=${encodeURIComponent(value)}&`;
                    });
                return str.substring(0, str.length - 1); //마지막& 지우기
            } else {
                return url;
            }
        }
        return url;
    }


    fun <T> GetHeader(params: APIRequest<T>?, method: String, url: String): String {
        this.calcSignature(params?.data, method, url);

        var parseObj = Object.assign(this, params?.data);

        var str = "OAuth ";

        Object.entries(parseObj) //params 오브젝트의 파라메터 이름, 값을 얻는 코드
            .forEach(([key, value]) => {
                if ((value || value === 0) && this.isCalcKey(key)) {
                    str += `${key}="${this.CalcParamUri(value)}",`;
                }
            });
        str = str.substring(0, str.length - 1); //마지막, 지우기
        return str;
    }

    fun createTimeStamp() {
        this.oauth_timestamp = floor(currentTimeMillis() / 1000 as Double).toString(); //timestamp용 계산
    }

    fun createOAuthNonce() {
        var tick = currentTimeMillis() * 10000 + 62135596800;
        this.oauth_nonce = this.stringToBase64(tick.toString());
    }

    fun stringToBase64(str: String): String {
        var arr = ArrayList<Int>();
        for (i in 0..str.length - 1) {
            arr.add(str[i].code.toByte().toInt());
        }
        return btoa(String.fromCharCode.apply(null, arr));
    }
    fun <T> calcSignature(params: APIRequest<T>?, method: String, url: String) {
        this.createTimeStamp();
        this.createOAuthNonce();

        var parseObj = Object.assign(this, params);

        var str = "";
        Object.entries(parseObj) //params 오브젝트의 파라메터 이름, 값을 얻는 코드
            .sort()
            .forEach(([key, value]) => {
                if ((value || value === 0) && this.isCalcKey(key)) {
                    str += `${key}=${this.CalcParamUri(value)}&`;
                }
            });

        str = str.substring(0, str.length - 1); //마지막& 지우기
        var baseStr = this.calcBaseString(method, url, str);
        var signKey ="";
        if(this.user_secret_key.isNullOrEmpty()){
          signKey="$oauth_consumer_secret&$user_secret_key";
        }else{
            signKey="$oauth_consumer_secret&";
        }
        var hash = CryptoJS.HmacSHA1(baseStr, signKey);
        var strHash = CryptoJS.enc.Base64.stringify(hash);
        this.oauth_signature = strHash;
    }

    fun calcBaseString(method: String, url: String, paramStr: String): String {
        return method + '&' + this.calcParamUri(url) + '&' + this.calcParamUri(paramStr);
    }
}