package site.linkway.slago.http;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

//glide 请求时添加cookie: id= SlagoSession
public class ImageLoad {
    public static GlideUrl getGlideURL(String url){
        return new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader("Cookie",Utils.APICookie()).build());
    }
}
