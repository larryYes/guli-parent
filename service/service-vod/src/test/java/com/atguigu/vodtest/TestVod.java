package com.atguigu.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

import java.util.List;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/3/12
 */
public class TestVod {

    /**
     * 根据视频ID获取视频凭证
     * @param args
     */
    public static void main(String[] args) throws ClientException {
        DefaultAcsClient client = InitObject.initVodClient("LTAI4G1ZwUNibksQQqinPHHv","gfCFtADKNYDhOCvQiBVBCpCX52hHO3");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        //向request设置视频ID
        request.setVideoId("61579e8dafae48c4ac5753e8a9b7e620");
        response = client.getAcsResponse(request);
        System.out.println("playAuth:" + response.getPlayAuth());

    }

    /**
     * 1根据视频ID获取视频播放地址
     * @throws ClientException
     */
    public static void getPlayUrl() throws ClientException {

        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI4G1ZwUNibksQQqinPHHv","gfCFtADKNYDhOCvQiBVBCpCX52hHO3");
        //创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //向request对象里面设置视频id

        request.setVideoId("61579e8dafae48c4ac5753e8a9b7e620");
        //调用初始化对象里面的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
