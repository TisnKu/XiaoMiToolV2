package com.xiaomitool.v2.xiaomi.unlock;

import com.xiaomitool.v2.inet.CustomHttpException;
import com.xiaomitool.v2.utility.utils.StrUtils;
import com.xiaomitool.v2.xiaomi.XiaomiKeystore;
import com.xiaomitool.v2.xiaomi.XiaomiProcedureException;
import org.json.JSONObject;

import java.util.Base64;
import java.util.HashMap;

public class UnlockCommonRequests {
    private static final String SID = "miui_unlocktool_client";
    private static final String CLIENT_VERSION = "3.3.525.23";
    private static final String NONCEV2 = "/api/v2/nonce";
    private static final String USERINFOV3 = "/api/v3/unlock/userinfo";
    private static final String DEVICECLEARV3 = "/api/v2/unlock/device/clear";
    private static final String AHAUNLOCKV3 = "/api/v3/ahaUnlock";
    private static final String FLASHUSERSTATUS = "/api/v2/flash/userStatus";
    private static final String AHAFLASH = "/api/v1/flash/ahaFlash";

    public static String nonceV2() throws XiaomiProcedureException, CustomHttpException {
        UnlockRequest request = new UnlockRequest(NONCEV2);
        request.addParam("r", StrUtils.randomWord(16).toLowerCase());
        request.addParam("sid",SID);
        return  request.exec();

    }

    public static String userInfo() throws XiaomiProcedureException, CustomHttpException {
        XiaomiKeystore keystore = XiaomiKeystore.getInstance();
        UnlockRequest request = new UnlockRequest(USERINFOV3);

        HashMap<String, String> pp = new HashMap<>();
        pp.put("clientId","1");
        pp.put("clientVersion", CLIENT_VERSION);
        pp.put("language","en");
        pp.put("pcId",keystore.getPcId());
        pp.put("region","");
        pp.put("uid",keystore.getUserId());
        String data = new JSONObject(pp).toString();
        data = Base64.getEncoder().encodeToString(data.getBytes());
        request.addParam("data",data);
        request.addNonce();
        request.addParam("sid",SID);
        return request.exec();
    }



    public static String deviceClear(String product) throws XiaomiProcedureException, CustomHttpException {
        XiaomiKeystore keystore = XiaomiKeystore.getInstance();
        UnlockRequest request = new UnlockRequest(DEVICECLEARV3);
        HashMap<String, String> pp = new HashMap<>();
        pp.put("clientId","1");
        pp.put("clientVersion", CLIENT_VERSION);
        pp.put("language","en");
        pp.put("pcId",keystore.getPcId());
        pp.put("product",product);
        pp.put("region","");
        String data = new JSONObject(pp).toString();
        data = Base64.getEncoder().encodeToString(data.getBytes());
        request.addParam("appId","1");
        request.addParam("data",data);
        request.addNonce();
        request.addParam("sid",SID);
        return request.exec();
    }

    public static String flashUserStatus() throws XiaomiProcedureException, CustomHttpException {
        XiaomiKeystore keystore = XiaomiKeystore.getInstance();
        UnlockRequest request = new UnlockRequest(FLASHUSERSTATUS);
        HashMap<String, String> pp = new HashMap<>();
        pp.put("clientId","qcomFlash");
        pp.put("clientVersion",CLIENT_VERSION);
        pp.put("pcId",keystore.getPcId());
        JSONObject object = new JSONObject(pp);
        String data = object.toString();
        data = Base64.getEncoder().encodeToString(data.getBytes());
        request.addParam("data",data);
        request.addNonce();
        request.addParam("sid",SID);
        return request.exec();
    }


    public static String test() throws XiaomiProcedureException, CustomHttpException {
        XiaomiKeystore keystore = XiaomiKeystore.getInstance();
        UnlockRequest request = new UnlockRequest("/api/v1/postsale/deviceInfo");
        HashMap<String, String> pp = new HashMap<>();

        pp.put("uid",keystore.getUserId());

        pp.put("clientId","2");
        pp.put("clientVersion",CLIENT_VERSION);
        pp.put("pcId",keystore.getPcId());
        pp.put("sn","0x417b45e6");
        pp.put("region","CN");
        HashMap<String, String> pp2 = new HashMap<>();
        pp2.put("product","dipper");
        pp2.put("deviceName","MI 8");

        JSONObject object = new JSONObject(pp);
        object.put("deviceInfo",new JSONObject(pp2));
        String data = object.toString();
        data = Base64.getEncoder().encodeToString(data.getBytes());
        request.addParam("data",data);
        request.addNonce();
        request.addParam("sid",SID);
        return request.exec();
    }

    public static String ahaFlash(String flashToken) throws XiaomiProcedureException, CustomHttpException {
        XiaomiKeystore keystore = XiaomiKeystore.getInstance();
        UnlockRequest request = new UnlockRequest(AHAFLASH);
        HashMap<String, String> pp = new HashMap<>();
        pp.put("pcId",keystore.getPcId());
        pp.put("clientVersion", CLIENT_VERSION);
        pp.put("clientId","qcomFlash");
        pp.put("flashToken",flashToken);
        String data = new JSONObject(pp).toString();
        data = Base64.getEncoder().encodeToString(data.getBytes());
        request.addParam("data",data);
        request.addNonce();
        request.addParam("sid",SID);
        return request.exec();
    }

    public static String ahaUnlock(String token, String product, String boardVersion, String deviceName, String socId) throws XiaomiProcedureException, CustomHttpException {
        XiaomiKeystore keystore = XiaomiKeystore.getInstance();
        UnlockRequest request = new UnlockRequest(AHAUNLOCKV3);
        HashMap<String, String> p2 = new HashMap<>();
        p2.put("boardVersion",boardVersion);
        p2.put("deviceName",deviceName);
        p2.put("product",product);
        p2.put("socId",socId);
        HashMap<String, String> pp = new HashMap<>();
        pp.put("clientId","2");
        pp.put("clientVersion",CLIENT_VERSION);
        pp.put("deviceToken",token);
        pp.put("language","en");
        pp.put("operate","unlock");
        pp.put("pcId",keystore.getPcId());
        pp.put("region","");
        pp.put("uid",keystore.getUserId());
        JSONObject object = new JSONObject(p2);
        JSONObject obj = new JSONObject(pp);
        obj.put("deviceInfo",object);
        String data = obj.toString();
        data = Base64.getEncoder().encodeToString(data.getBytes());
        request.addParam("appId","1");
        request.addParam("data",data);
        request.addNonce();
        request.addParam("sid",SID);
        return request.exec();
    }
}