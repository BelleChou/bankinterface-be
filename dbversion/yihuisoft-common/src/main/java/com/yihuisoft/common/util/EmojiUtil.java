package com.yihuisoft.common.util;

//import com.github.binarywang.java.emoji.EmojiConverter;
import org.apache.commons.lang3.StringUtils;

/**
 * 表情处理类
 * @author tonywu
 * @version v1.0.0
 */
public class EmojiUtil {

//    private static EmojiConverter emojiConverter = EmojiConverter.getInstance();
//
//    /**
//     * 将emojiStr转为 带有表情的字符
//     * @param emojiStr
//     * @return
//     */
//    public static String emojiConverterUnicodeStr(String emojiStr){
//        String result = emojiConverter.toUnicode(emojiStr);
//        return result;
//    }
//
//    /**
//     * 带有表情的字符串转换为编码
//     * @param str
//     * @return
//     */
//    public static String emojiConverterToAlias(String str){
//        String result=emojiConverter.toAlias(str);
//        return result;
//    }

    /**
     * 评价emoji表情替换
     *
     * @param source 原字符串
     * @param slipStr emoji表情替换成的字符串
     * @return 过滤后的字符串
     */
    public static String commentfilterEmoji(String source,String slipStr) {
        String result = "";
        if(StringUtils.isNotBlank(source)){
            result = source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);

        }else{
            result =  source;
        }
        if(StringUtils.isEmpty(result))
            result = ComConst.DEFAULT_COMMENT_CONTENT;
        return result;
    }
}
