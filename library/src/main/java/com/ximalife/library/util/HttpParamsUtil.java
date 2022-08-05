package com.ximalife.library.util;

import android.text.TextUtils;
import android.util.Log;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HttpParamsUtil {

    private static final String APPKEY = "XOlkEA4sL88XNsMgMW3onYVCwFgyeqQW";

    public static void handleParams(Map<String, String> params) {
        params.put("timestamp", String.valueOf(new Date().getTime()));
        params.put("sign", getSign(params));
    }

    public static String handleParamsMultparts(String params, String devid, String time, String os) {
        Map<String, String> map = getSortParams(params);
        map.put("timestamp", time);
        map.put("device_id", devid);
        map.put("system_os", os);
        return getSign(map);
    }

    public static String handleParamsURLAndMap(String params, Map<String, String> pm) {
        Map<String, String> map = getSortParams(params);
        map.putAll(pm);
        return getSign(map);
    }

    public static String handleParamsNo(Map<String, String> params) {
        return getSign(params);
    }

    private static String getSign(Map<String, String> map) {
        Object[] keys = map.keySet().toArray();
        Arrays.sort(keys);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < keys.length; i++) {
            String key = keys[i].toString();
            if (key == null) {
                continue;
            }

            sb.append(key).append("=").append(URLEncoder.encode(map.get(key).trim()));

            //URLDecoder.decode(map.get(key).trim(), "UTF-8").replaceAll("\r|\n", "")
//            try {
//                sb.append(key).append("=").append(URLDecoder.decode(map.get(key).trim(), "UTF-8").replaceAll("\r|\n", ""));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            if (i != keys.length - 1) {
                sb.append("&");
            }
        }
        sb.append("&appkey=").append(APPKEY);


        String encodedParams = sb.toString().replace(" ", "%20");
        Log.d("authstr", encodedParams);
        return Md5(encodedParams);
    }


    /**
     * 生成MD5加密32位字符串
     *
     * @param MStr :需要加密的字符串
     * @return 生成MD5加密32位字符串
     */
    private static String Md5(String MStr) {
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(MStr.getBytes());
            return bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            return String.valueOf(MStr.hashCode());
        }
    }

    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    //拿到参数转成map
    public static Map<String, String> getSortParams(String data) {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(data)) {
            if (data.contains("&")) {
                String[] params = data.split("&");
                for (String p : params) {
                    if (p.contains("=")) {
                        String[] pms = p.split("=", -1);
                        if (pms.length == 2) {
                            map.put(pms[0], pms[1]);
                        }
                    }
                }
            } else if (data.contains("=")) {
                String[] pms = data.split("=", -1);
                if (pms.length == 2) {
                    map.put(pms[0], pms[1]);
                }
            }
        }
        return map;
    }
}
