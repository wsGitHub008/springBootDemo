package cn.itws.mallmanage.service.impl;

import cn.hutool.json.JSONUtil;
import cn.itws.mallmanage.dto.OssCallbackParam;
import cn.itws.mallmanage.dto.OssCallbackResult;
import cn.itws.mallmanage.dto.OssPolicyResult;
import cn.itws.mallmanage.service.OssService;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author swj
 * @date 2020/4/6 13:49
 */
@Service
public class OssServiceImpl implements OssService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OssServiceImpl.class);
    @Value("${aliyun.oss.policy.expire}")
    private int ALIYUN_OSS_EXPIRE;
    @Value("${aliyun.oss.maxSize}")
    private int ALIYUN_OSS_MAX_SIZE;
    @Value("${aliyun.oss.callback}")
    private String ALIYUN_OSS_CALLBACK;
    @Value("${aliyun.oss.bucketName}")
    private String ALIYUN_OSS_BUCKET_NAME;
    @Value("${aliyun.oss.endpoint}")
    private String ALIYUN_OSS_ENDPOINT;
    @Value("${aliyun.oss.dir.prefix}")
    private String ALIYUN_OSS_DIR_PREFIX;

    //注入自定义的Oss对象
    @Autowired
    private OSSClient ossClient;

    //1、oss上传策略生成(签名的生成)
    @Override
    public OssPolicyResult policy() {
        OssPolicyResult result = new OssPolicyResult();
        //储存目录
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dir = ALIYUN_OSS_DIR_PREFIX + sdf.format(new Date());

        //签名有效期
        long expireEndTime = System.currentTimeMillis() + ALIYUN_OSS_EXPIRE * 1000;
        Date expiration = new Date(expireEndTime);

        //文件大小
        long maxSize = ALIYUN_OSS_MAX_SIZE * 1024 * 1024;
        //回调
        OssCallbackParam callBack = new OssCallbackParam();
        callBack.setCallbackUrl(ALIYUN_OSS_CALLBACK);
        //回调是传入request中的参数
        callBack.setCallbackBody("filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
        //回调时传入参数的格式，比如表单提交形式
        callBack.setCallbackBodyType("application/x-www-form-urlencoded");
        //提交节点
        String action = "http://" + ALIYUN_OSS_BUCKET_NAME + "." + ALIYUN_OSS_ENDPOINT;
        try {
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String policy = BinaryUtil.toBase64String(binaryData);
            String signature = ossClient.calculatePostSignature(postPolicy);
            String callbackData = BinaryUtil.toBase64String(JSONUtil.parse(callBack).toString().getBytes("utf-8"));
            //返回结果
            result.setAccessKeyId(ossClient.getCredentialsProvider().getCredentials().getAccessKeyId());
            result.setPolicy(policy);
            result.setSignature(signature);
            result.setDir(dir);
            result.setCallback(callbackData);
            result.setHost(action);

        } catch (Exception e) {
           LOGGER.error("签名生成失败",e);
        }
        return result;
    }
    //2、oss上传成功回调
    @Override
    public OssCallbackResult callback(HttpServletRequest request) {
        OssCallbackResult result = new OssCallbackResult();
        String filename = request.getParameter("filename");
        filename = "http://".concat(ALIYUN_OSS_ENDPOINT).concat("/").concat(filename);
        result.setFilename(filename);
        result.setSize(request.getParameter("size"));
        result.setMimeType(request.getParameter("mimeType"));
        result.setWidth(request.getParameter("width"));
        result.setHeight(request.getParameter("height"));
        return result;
    }
}
