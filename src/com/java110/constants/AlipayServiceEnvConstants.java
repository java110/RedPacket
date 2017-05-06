

/**

 * Alipay.com Inc.

 * Copyright (c) 2004-2014 All Rights Reserved.

 */

package com.java110.constants;


/**

 * 支付宝服务窗环境常量（demo中常量只是参考，需要修改成自己的常量值）

 * 

 * @author taixu.zqq

 * @version $Id: AlipayServiceConstants.java, v 0.1 2014年7月24日 下午4:33:49 taixu.zqq Exp $

 */

public class AlipayServiceEnvConstants {


    /**支付宝公钥-从支付宝服务窗获取*/

    public static final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";


    /**签名编码-视支付宝服务窗要求*/

    public static final String SIGN_CHARSET      = "GBK";


    /**字符编码-传递给支付宝的数据编码*/

    public static final String CHARSET           = "GBK";


    /**签名类型-视支付宝服务窗要求*/

    public static final String SIGN_TYPE         = "RSA";

    

    

    public static final String PARTNER           = "2088911227080000";


    /** 服务窗appId  */

    //TODO !!!! 注：该appId必须设为开发者自己的服务窗id  这里只是个测试id

    public static final String APP_ID            = "2016020201135253";


    //开发者请使用openssl生成的密钥替换此处  请看文档：https://fuwu.alipay.com/platform/doc.htm#2-1接入指南

    //TODO !!!! 注：该私钥为测试账号私钥  开发者必须设置自己的私钥 , 否则会存在安全隐患 

    public static final String PRIVATE_KEY       = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJfv+0IgvjlExA9CVBgfhX/DiVLaFsgahHuJTYJKpaePcEYd5WpPjQ4u6Bl51kTPwPVluiVmn+f7V2qZSqMJLxNQMyydsX3mh+Qio6c3h/fMfU9Wsd7ZJChIx7aDYai39Xc20lOzzdEhE98r4/3wlj++XNL6NSPzjPhujSHuzpMJAgMBAAECgYBAvyu7imiFzyAdwI5FmWkygQlMHVQIAgTjACfflh/xv9aAiszw+OWLXcHa2WjN+hqoeKlStuD94sXlz11XDNraIABSymFKwjHXujzpl8M30Tg58Hd3C14yAwoVP1sUEx9c0wYLoMLe10l35N86dPPBlt1tirXpnuR66UjCbeL14QJBAMfaM4LakqvMNfcWUfOriv36Hc/tvSSNGCGVdShi+Np8mQy37ETvo2Rf27whLfD/WcF0MQpD825KPJY37AEIGn8CQQDCn6OTW+UcZ7LAqmkYVNbNodEpn1LihVdF9T2s+fZaZbqTrJDgU0rnO4mTMCfia3/i9VoSOsZK6AGajdyfoL53AkAuMK1VIgViNYmHeR4pzk0KlENNqmnbx8x6/pscYXuYq9GH0f6GlEhUS59ypMyEddAd2Sf4cmoR5JCKO5SNTH0rAkEArN1iSuaab0pj6ODzi9r/F5Ic191dnzlUNR0IdPVoLQ6iU7w4yibxJsUpAwUIyAE3i/zkzcBKlAnSMtbCGT6LLQJBAIURYA6jPl2GAKFVn//whfOrhOOsEAVRl46z+mw3+7kHK7T/7RDyNLUMvyURsDRuxSue8zahF1CB+GVKufy4ujQ=";


    //TODO !!!! 注：该公钥为测试账号公钥  开发者必须设置自己的公钥 ,否则会存在安全隐患

    public static final String PUBLIC_KEY        = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCX7/tCIL45RMQPQlQYH4V/w4lS2hbIGoR7iU2CSqWnj3BGHeVqT40OLugZedZEz8D1ZbolZp/n+1dqmUqjCS8TUDMsnbF95ofkIqOnN4f3zH1PVrHe2SQoSMe2g2Got/V3NtJTs83RIRPfK+P98JY/vlzS+jUj84z4bo0h7s6TCQIDAQAB";


    /**支付宝网关*/

    public static final String ALIPAY_GATEWAY    = "https://openapi.alipay.com/gateway.do";


    /**授权访问令牌的授权类型*/

    public static final String GRANT_TYPE        = "authorization_code";
    
    public static final String OPEN_AUTH = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=APPID&scope=SCOPE&redirect_uri=ENCODED_URL";

}