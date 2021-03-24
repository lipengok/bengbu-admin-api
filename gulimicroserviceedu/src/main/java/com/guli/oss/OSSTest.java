package com.guli.oss;

import com.aliyun.oss.OSSClient;
import org.junit.Test;

public class OSSTest {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    String endpoint = "oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    String accessKeyId = "LTAI4GETACkfiijVx1RiCsk8";
    String accessKeySecret = "IJYJmCwLXk4PhRbdEhY5qCyrMllCze";
    String bucketName = "guli-bengbu";
    @Test
    public void testCreateBucket() {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 创建存储空间。
        ossClient.createBucket(bucketName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
