package com.ximalife.library.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static float negativeDp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return  -(dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    /**
     * 全半角替换
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    /**
     * 获取assets文件str
     *
     * @param context
     * @param name
     * @return
     */
    public static String getAssetsFileStr(Context context, String name) {
        StringBuilder newstringBuilder = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().getAssets().open(name);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                newstringBuilder.append(jsonLine);
            }
            reader.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newstringBuilder.toString();
    }

    /**
     * 关闭键盘
     *
     * @param activity
     */
    public static void closeKeyboard(Activity activity) {
        if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (activity.getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    /**
     * 验证手机号码 匹配所有的手机号，不区分哪个运营商，不考虑卫星通信、物联网等特殊号段
     *
     * @param mobiles
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^134[0-8]\\d{7}$|^13[^4]\\d{8}$|^14[5-9]\\d{8}$|^15[^4]\\d{8}$|^16[6]\\d{8}$|^17[0-8]\\d{8}$|^18[\\d]{9}$|^19[8,9]\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isPhone(String mobiles) {
        Pattern p = Pattern
                .compile("^0\\d{2,3}-[1-9]\\d{6,7}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isEmail(String mobiles) {
        Pattern p = Pattern
                .compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isBankCard(String mobiles) {
        Pattern p = Pattern
                .compile("^([1-9]{1})(\\d{14}|\\d{18})$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    //验证邮政编码
    public static boolean isZidCode(String post) {
        if (post.matches("[1-9]\\d{5}(?!\\d)")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 隐藏手机中间四位
     */
    public static String HidePhone(String phone) {
        if (phone != null && !phone.equals("")) {
            if (phone.length() > 4) {
                phone = phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length());
                return phone;
            } else {
                return "****";
            }
        } else {
            return "用户";
        }
    }


    /**
     * 校验过程：
     * 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
     * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
     * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
     * 校验银行卡卡号
     */
    public static boolean checkBankCard(String bankCard) {
        if (bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhn 校验算法获得校验位
     */
    public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }


    /**
     * 身份证号校验 （支持18位）
     */
    public static boolean checkIdentityCode(String identityCode) {
        if (!identityCode.matches("\\d{17}(\\d|x|X)$")) {
            return false;
        }
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        int year = Integer.parseInt(df.format(d));
        if (Integer.parseInt(identityCode.substring(6, 10)) < 1900 || Integer.parseInt(identityCode.substring(6, 10)) > year) {// 7-10位是出生年份，范围应该在1900-当前年份之间
            return false;
        }
        if (Integer.parseInt(identityCode.substring(10, 12)) < 1 || Integer.parseInt(identityCode.substring(10, 12)) > 12) {// 11-12位代表出生月份，范围应该在01-12之间
            return false;
        }
        if (Integer.parseInt(identityCode.substring(12, 14)) < 1 || Integer.parseInt(identityCode.substring(12, 14)) > 31) {// 13-14位是出生日期，范围应该在01-31之间
            return false;
        }
        // 校验第18位
        // S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
        // Ai:表示第i位置上的身份证号码数字值
        // Wi:表示第i位置上的加权因子
        // Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
        String[] tempA = identityCode.split("|");
        int[] a = new int[18];
        for (int i = 0; i < tempA.length - 2; i++) {
            a[i] = Integer.parseInt(tempA[i + 1]);
        }
        int[] w = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2}; // 加权因子
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum = sum + a[i] * w[i];
        }
        // Y = mod(S, 11)
        // 通过模得到对应的校验码
        // Y: 0 1 2 3 4 5 6 7 8 9 10
        // 校验码: 1 0 X 9 8 7 6 5 4 3 2
        String[] v = {"1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2"}; // 校验码
        int y = sum % 11;
        if (!v[y].equalsIgnoreCase(identityCode.substring(17))) {// 第18位校验码错误
            return false;
        }
        return true;
    }


    /*--------------------------------------------统一社会信用代码--------------------------------------*/
    static String isCreditCode = "true";
    static String error_CreditCode = "社会信用代码有误";
    static String error_CreditCode_min = "社会信用代码不足18位，请核对后再输！";
    static String error_CreditCode_max = "社会信用代码大于18位，请核对后再输！";
    static String error_CreditCode_empty = "社会信用代码不能为空！";
    private static Map<String, Integer> datas = null;
    private static char[] pre17s;
    static int[] power = {1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};
    // 社会统一信用代码不含（I、O、S、V、Z） 等字母
    static char[] code = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'T', 'U', 'W', 'X', 'Y'};

    /**
     * 判断是否是一个有效的社会信用代码
     *
     * @param creditCode
     * @return
     */
    public static boolean isCreditCode(String creditCode) {



        if ("".equals(creditCode) || " ".equals(creditCode)) {
            System.out.println(error_CreditCode_empty);
            return false;
        } else if (creditCode.length() < 18) {
            System.out.println(error_CreditCode_min);
            return false;
        } else if (creditCode.length() > 18) {
            System.out.println(error_CreditCode_max);
            return false;
        } else {
            initDatas(code.length);
            pre17(creditCode);

            int sum = sum(pre17s);
            int temp = sum % 31;
            temp = temp == 0 ? 31 : temp;//  谢谢 whhitli的帮助
            System.out.println(code[31 - temp] + " " + (creditCode.substring(17, 18).equals(code[31 - temp] + "") ? isCreditCode : error_CreditCode));
            return creditCode.substring(17, 18).equals(code[31 - temp] + "") ? true : false;
        }
    }

    /**
     * @param chars
     * @return
     */
    private static int sum(char[] chars) {
        int sum = 0;
        for (int i = 0; i < chars.length; i++) {
            int code = datas.get(chars[i] + "");
            sum += power[i] * code;
        }
        return sum;

    }

    /**
     * 获取前17位字符
     *
     * @param creditCode
     */
    static void pre17(String creditCode) {
        String pre17 = creditCode.substring(0, 17);
        pre17s = pre17.toCharArray();
    }

    /**
     * 初始化数据
     *
     * @param count
     */
    static void initDatas(int count) {
        datas = new HashMap<>();
        for (int i = 0; i < code.length; i++) {
            datas.put(code[i] + "", i);
        }
        System.out.println();
    }



    public static String getURI(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        url = url.trim();
        if (!url.contains("?")) {
            return url;
        }
        int index = url.indexOf("?");
        return url.substring(0,index);
    }

    public static Map<String, String> getParams(String url) {
        Map<String, String> result = new HashMap<>();
        if (TextUtils.isEmpty(url) || !url.contains("?")) {
            return result;
        }
        url = url.trim();
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        if (!temp.contains("=")) {
            return result;
        }
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains("=") && !str.startsWith("=")) {
                if(str.endsWith("=")) {
                    result.put(handleParams(str.replace("=","")), "");
                } else {
                    String[] pa = str.split("=");
                    if (pa.length == 2) {
                        result.put(handleParams(pa[0]), handleParams(pa[1]));
                    }
                }
            }
        }
        return result;
    }

    private static String handleParams(String str) {
        str = str.replace("%2B", "+");
        str = str.replace("%20", " ");
        str = str.replace("%2F", "/");
        str = str.replace("%3F", "?");
        str = str.replace("%25", "%");
        str = str.replace("%23", "#");
        str = str.replace("%26", "&");
        str = str.replace("%3D", "=");
        return str;
    }
}
