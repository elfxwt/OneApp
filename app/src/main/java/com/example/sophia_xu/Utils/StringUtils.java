package com.example.sophia_xu.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sophia_xu.oneapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sophia_Xu on 2015/8/24.
 */
public class StringUtils {

    public static SpannableString getWeiboContent(final Context context, final TextView tv, String source) {

        // 使用正则表达式 匹配内容，表情，@内容等的匹配
        String regexAt = "@[\u4e00-\u9fa5\\w]+";  // @引用
        String regexTopic = "#[\u4e00-\u9fa5\\w]+#"; // # 话题
        String regexEmoji = "\\[[\u4e00-\u9fa5\\w]+\\]"; // []的

        String regex = "(" + regexAt + ")|(" + regexTopic + ")|(" + regexEmoji + ")";

        SpannableString spannableString = new SpannableString(source);

        Pattern pattern = Pattern.compile(regex); //?
        Matcher matcher = pattern.matcher(spannableString); //?

        if(matcher.find()){
            tv.setMovementMethod(LinkMovementMethod.getInstance()); // ？
            matcher.reset();
        }


        while (matcher.find()) {
            final String atStr = matcher.group(1);
            final String topicStr = matcher.group(2);
            String emojiStr = matcher.group(3);

            // 匹配是或的连接，所以有可能 其中一个str 有值，其他的没有值
            if (atStr != null) {
                int start = matcher.start(1);
                ClickableSpan clickableSpan = new MyClickableSpan(context) {
                    @Override
                    public void onClick(View widget) {
                        ToastUtils.showToast(context, "user:" + atStr, Toast.LENGTH_SHORT);
                    }
                };
                spannableString.setSpan(clickableSpan, start, start + atStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }


            if (topicStr != null) {
                int start = matcher.start(2);
                ClickableSpan clickableSpan = new MyClickableSpan(context) {
                    @Override
                    public void onClick(View widget) {
                        ToastUtils.showToast(context, "topic:" + topicStr, Toast.LENGTH_SHORT);
                    }
                };
                spannableString.setSpan(clickableSpan, start, start + topicStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (emojiStr != null) {
                int start = matcher.start(3);

                int imgRes = EmotionUtils.getImgByName(emojiStr);
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imgRes);

                if (bitmap != null) {
                    int size = (int) tv.getTextSize();
                    bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
                    ImageSpan imageSpan = new ImageSpan(context,bitmap);
                    spannableString.setSpan(imageSpan,start,start+emojiStr.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

            }

        }
        return spannableString;

    }


    static class MyClickableSpan extends ClickableSpan{

        private Context context;

        public MyClickableSpan(Context context){
            this.context = context;
        }

        @Override
        public void onClick(View widget) {

        }

        @Override
        public void updateDrawState(TextPaint ds) {  // 重写 其默认的方法，蓝色和带下划线
            ds.setColor(context.getResources().getColor(R.color.txt_at_blue));
            ds.setUnderlineText(false);

        }
    }

}
