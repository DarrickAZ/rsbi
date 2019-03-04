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

public class AndroidPushMsgToSingleDevice {
    public AndroidPushMsgToSingleDevice() {
    }

    public int push(String msg, String channelId) throws PushClientException, PushServerException {
        String apiKey = "UjbESafGszIyuF8ZAcozPkdN";
        String secretKey = "ZGQbCG9BL7hgVb5gk8whOHntttZmwvLq";
        PushKeyPair pair = new PushKeyPair(apiKey, secretKey);
        BaiduPushClient pushClient = new BaiduPushClient(pair, "api.tuisong.baidu.com");
        pushClient.setChannelLogHandler(new YunLogHandler() {
            public void onHandle(YunLogEvent event) {
            }
        });

        try {
            JSONObject notification = new JSONObject();
            notification.put("title", "指标推送");
            notification.put("description", msg);
            notification.put("notification_builder_id", 0);
            notification.put("notification_basic_style", 4);
            notification.put("open_type", 2);
            notification.put("pkg_content", (Object)null);
            PushMsgToSingleDeviceRequest request = (new PushMsgToSingleDeviceRequest()).addChannelId(channelId).addMsgExpires(new Integer(3600)).addMessageType(1).addMessage(notification.toString()).addDeviceType(3);
            PushMsgToSingleDeviceResponse response = pushClient.pushMsgToSingleDevice(request);
            System.out.println("msgId: " + response.getMsgId() + ",sendTime: " + response.getSendTime());
            return 0;
        } catch (PushClientException var10) {
            var10.printStackTrace();
            return 1;
        } catch (PushServerException var11) {
            System.out.println(String.format("requestId: %d, errorCode: %d, errorMessage: %s", var11.getRequestId(), var11.getErrorCode(), var11.getErrorMsg()));
            return 1;
        }
    }
}
