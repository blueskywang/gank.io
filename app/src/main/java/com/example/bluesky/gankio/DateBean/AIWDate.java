package com.example.bluesky.gankio.DateBean;

import java.util.List;

/**
 * Created by localhost on 2016/11/16.
 */

public class AIWDate {
    /**
     * error : false
     * results : [{"_id":"5829b5b2421aa911e32d87e3","createdAt":"2016-11-14T21:01:38.860Z","desc":"动画插值器的编辑器","images":["http://img.gank.io/fa3bb06d-1bfb-41a7-8bfe-78adeb55c049"],"publishedAt":"2016-11-16T11:37:18.947Z","source":"chrome","type":"Android","url":"https://github.com/MartinRGB/RapidInterpolator","used":true,"who":"Jason"},{"_id":"582bb5f6421aa93a61577f2d","createdAt":"2016-11-16T09:27:18.173Z","desc":"Android MP3录制，波形显示，音频权限兼容与播放","images":["http://img.gank.io/b740d072-3c32-4c8f-aa7a-b0dd45968e19"],"publishedAt":"2016-11-16T11:37:18.947Z","source":"web","type":"Android","url":"http://www.jianshu.com/p/2448e2903b07","used":true,"who":"Shuyu Guo"},{"_id":"582bc15e421aa93a518777ac","createdAt":"2016-11-16T10:15:58.132Z","desc":"一行代码完成图片高斯模糊","images":["http://img.gank.io/d9a60d1c-714d-4bf7-8de9-0b6601b1c110"],"publishedAt":"2016-11-16T11:37:18.947Z","source":"chrome","type":"Android","url":"https://github.com/jrvansuita/GaussianBlur","used":true,"who":"代码家"},{"_id":"582bc1b4421aa93a61577f2f","createdAt":"2016-11-16T10:17:24.383Z","desc":"Android 画板 View，随心所欲的画画。","images":["http://img.gank.io/fc1b996a-228c-4553-8cb5-159c046a4531"],"publishedAt":"2016-11-16T11:37:18.947Z","source":"chrome","type":"Android","url":"https://github.com/ByoxCode/DrawView","used":true,"who":"代码家"},{"_id":"582962f9421aa911d3bb7edb","createdAt":"2016-11-14T15:08:41.874Z","desc":"史上最强的 Android 日志库 XLog","images":["http://img.gank.io/bbd6baf4-01ba-4040-956c-32b5f7d9c2e8"],"publishedAt":"2016-11-15T11:26:11.821Z","source":"web","type":"Android","url":"https://github.com/elvishew/xLog","used":true,"who":"Elvis Hew"},{"_id":"58298adf421aa911cf2e1561","createdAt":"2016-11-14T17:58:55.173Z","desc":"A simple spannable string helper","images":["http://img.gank.io/a4e727da-6b91-414a-9f8b-6c0704b0f573"],"publishedAt":"2016-11-15T11:26:11.821Z","source":"chrome","type":"Android","url":"https://github.com/jaychang0917/SimpleText","used":true,"who":"jp"},{"_id":"582a6e78421aa9140a8ec98c","createdAt":"2016-11-15T10:10:00.749Z","desc":"AS2.2使用CMake方式进行JNI/NDK开发","publishedAt":"2016-11-15T11:26:11.821Z","source":"web","type":"Android","url":"http://blog.csdn.net/yulianlin/article/details/53168350","used":true,"who":"FMVP"},{"_id":"582a7112421aa914099ae5e1","createdAt":"2016-11-15T10:21:06.153Z","desc":"自动通过用户启动 App 的次数，弹出 Rating 页面引导用户评价。","images":["http://img.gank.io/664f2a7d-53c0-4e7c-a873-02d23fda01cb"],"publishedAt":"2016-11-15T11:26:11.821Z","source":"chrome","type":"Android","url":"https://github.com/codemybrainsout/smart-app-rate","used":true,"who":"代码家"},{"_id":"582a7cd1421aa917bc4aa9af","createdAt":"2016-11-15T11:11:13.287Z","desc":"apk-deguard 在线APK反混淆工具，理论上ProGurad 混淆的代码 80% 都可以恢复出来","publishedAt":"2016-11-15T11:26:11.821Z","source":"chrome","type":"Android","url":"http://www.apk-deguard.com/","used":true,"who":"Dear宅学长"},{"_id":"5827f41b421aa911d3bb7ecb","createdAt":"2016-11-13T13:03:23.38Z","desc":"独立全端开发的开源小作：简诗 2.0","images":["http://img.gank.io/b6be7a85-4035-437f-9521-65593fdbc48e"],"publishedAt":"2016-11-14T11:36:49.680Z","source":"web","type":"Android","url":"https://www.v2ex.com/t/320154","used":true,"who":"wingjay"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 5829b5b2421aa911e32d87e3
         * createdAt : 2016-11-14T21:01:38.860Z
         * desc : 动画插值器的编辑器
         * images : ["http://img.gank.io/fa3bb06d-1bfb-41a7-8bfe-78adeb55c049"]
         * publishedAt : 2016-11-16T11:37:18.947Z
         * source : chrome
         * type : Android
         * url : https://github.com/MartinRGB/RapidInterpolator
         * used : true
         * who : Jason
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
