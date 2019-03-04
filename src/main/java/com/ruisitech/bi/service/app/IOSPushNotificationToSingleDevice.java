//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.app;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import net.sf.json.JSONObject;

public class IOSPushNotificationToSingleDevice {
    public IOSPushNotificationToSingleDevice() {
    }

    public int push(String msg, String channelId) throws PushClientException, PushServerException {
        String apiKey = "0zhV9TDwBtsg3dPZrMMy50aw";
        String secretKey = "czvOOGjYk1SGxITlbkwrpvXiRs6p5GKQ";
        PushKeyPair pair = new PushKeyPair(apiKey, secretKey);
        BaiduPushClient pushClient = new BaiduPushClient(pair, "api.tuisong.baidu.com");
        pushClient.setChannelLogHandler(new YunLogHandler() {
            public void onHandle(YunLogEvent event) {
            }
        });

        try {
            JSONObject notification = new JSONObject();
            JSONObject jsonAPS = new JSONObject();
            jsonAPS.put("alert", msg);
            jsonAPS.put("sound", "default");
            jsonAPS.put("badge", 1);
            notification.put("aps", jsonAPS);
            PushMsgToSingleDeviceRequest request = (new PushMsgToSingleDeviceRequest()).addChannelId(channelId).addMsgExpires(new Integer(3600)).addMessageType(1).addMessage(notification.toString()).addDeployStatus(2).addDeviceType(4);
            PushMsgToSingleDeviceResponse response = pushClient.pushMsgToSingleDevice(request);
            System.out.println("msgId: " + response.getMsgId() + ",sendTime: " + response.getSendTime());
            return 0;
        } catch (PushClientException var11) {
            var11.printStackTrace();
            return 1;
        } catch (PushServerException var12) {
            System.out.println(String.format("requestId: %d, errorCode: %d, errorMessage: %s", var12.getRequestId(), var12.getErrorCode(), var12.getErrorMsg()));
            return 1;
        }
    }
}
