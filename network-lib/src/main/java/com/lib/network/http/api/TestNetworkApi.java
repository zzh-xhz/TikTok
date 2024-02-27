package com.lib.network.http.api;

import androidx.annotation.NonNull;

import com.google.gson.internal.LinkedTreeMap;
import com.hjq.http.config.IRequestApi;

import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/EasyHttp
 *    time   : 2019/11/18
 *    desc   : 按照作者昵称搜索文章
 */
public final class TestNetworkApi implements IRequestApi {
    /** userid*/
    private String key;
    private String q;

    //    https://pixabay.com/api/videos/?key=42445463-2b7d14a51075cb646c2011843&q=yellow+flowers
    @NonNull
    @Override
    public String getApi() {
        return "api/videos/?key="+ key +"&q="+q;
    }

    public TestNetworkApi setKey(String key) {
        this.key = key;
        return this;
    }
    public TestNetworkApi setQ(String q) {
        this.q = q;
        return this;
    }


    public final static class Bean {

        private Integer total;
        private Integer totalHits;
        private List<HitsData> hits;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getTotalHits() {
            return totalHits;
        }

        public void setTotalHits(Integer totalHits) {
            this.totalHits = totalHits;
        }

        public List<HitsData> getHits() {
            return hits;
        }

        public void setHits(List<HitsData> hits) {
            this.hits = hits;
        }

        @Override
        public String toString() {
            return "Bean{" +
                    "total=" + total +
                    ", totalHits=" + totalHits +
                    ", hits=" + hits +
                    '}';
        }

        public static class HitsData {
            private Integer id;
            private String pageURL;
            private String type;
            private String tags;
            private Integer duration;
            private String picture_id;
            //  large  medium   small  tiny
            private LinkedTreeMap<String,VideosItemData> videos;
            private Integer views;
            private Integer downloads;
            private Integer likes;
            private Integer comments;
            private Integer user_id;
            private String user;
            private String userImageURL;

            @Override
            public String toString() {
                return "HitsData{" +
                        "id=" + id +
                        ", pageURL='" + pageURL + '\'' +
                        ", type='" + type + '\'' +
                        ", tags='" + tags + '\'' +
                        ", duration=" + duration +
                        ", picture_id='" + picture_id + '\'' +
                        ", videos=" + videos +
                        ", views=" + views +
                        ", downloads=" + downloads +
                        ", likes=" + likes +
                        ", comments=" + comments +
                        ", user_id=" + user_id +
                        ", user='" + user + '\'' +
                        ", userImageURL='" + userImageURL + '\'' +
                        '}';
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getPageURL() {
                return pageURL;
            }

            public void setPageURL(String pageURL) {
                this.pageURL = pageURL;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public Integer getDuration() {
                return duration;
            }

            public void setDuration(Integer duration) {
                this.duration = duration;
            }

            public String getPicture_id() {
                return picture_id;
            }

            public void setPicture_id(String picture_id) {
                this.picture_id = picture_id;
            }

            public LinkedTreeMap<String,VideosItemData> getVideos() {
                return videos;
            }

            public void setVideos(LinkedTreeMap<String,VideosItemData> videos) {
                this.videos = videos;
            }

            public Integer getViews() {
                return views;
            }

            public void setViews(Integer views) {
                this.views = views;
            }

            public Integer getDownloads() {
                return downloads;
            }

            public void setDownloads(Integer downloads) {
                this.downloads = downloads;
            }

            public Integer getLikes() {
                return likes;
            }

            public void setLikes(Integer likes) {
                this.likes = likes;
            }

            public Integer getComments() {
                return comments;
            }

            public void setComments(Integer comments) {
                this.comments = comments;
            }

            public Integer getUser_id() {
                return user_id;
            }

            public void setUser_id(Integer user_id) {
                this.user_id = user_id;
            }

            public String getUser() {
                return user;
            }

            public void setUser(String user) {
                this.user = user;
            }

            public String getUserImageURL() {
                return userImageURL;
            }

            public void setUserImageURL(String userImageURL) {
                this.userImageURL = userImageURL;
            }
            public static class VideosItemData {

                private String url;
                private Integer width;
                private Integer height;
                private Integer size;
                private   String  thumbnail;

                public String getThumbnail() {
                    return thumbnail;
                }

                public void setThumbnail(String thumbnail) {
                    this.thumbnail = thumbnail;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public Integer getWidth() {
                    return width;
                }

                public void setWidth(Integer width) {
                    this.width = width;
                }

                public Integer getHeight() {
                    return height;
                }

                public void setHeight(Integer height) {
                    this.height = height;
                }

                public Integer getSize() {
                    return size;
                }

                public void setSize(Integer size) {
                    this.size = size;
                }
            }
        }
    }
}