package com.lib.network.http.api;

import androidx.annotation.NonNull;

import com.airbnb.lottie.L;
import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestHost;

import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/EasyHttp
 *    time   : 2019/11/18
 *    desc   : 每日视频
 */
public final class ReleaseFourApi implements IRequestApi, IRequestHost {
    /** userid*/
    private String date;
    private String page;

    //http://baobab.kaiyanapp.com/api/v4/tabs/selected
    @NonNull
    @Override
    public String getApi() {
        return "api/v4/tabs/selected";
    }
//
//    date	string	选填	毫秒时间戳
//    page	int	选填	页数
    public ReleaseFourApi setDate(String date) {
        this.date = date;
        return this;
    }
    public ReleaseFourApi setPage(String page) {
        this.page = page;
        return this;
    }

    @NonNull
    @Override
    public String getHost() {
        return "http://baobab.kaiyanapp.com";
    }



    public final static class Bean {

        private List<ItemList> itemList;


        public static class ItemList {
            private String type;
            private Data data;
            private Object trackingData;
            private String tag;
            private int id;
            private int adIndex;

            public static class Data {
                private String dataType;
                private String text;
                private String font;
                private Object adTrack;

                public String getDataType() {
                    return dataType;
                }

                public void setDataType(String dataType) {
                    this.dataType = dataType;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getFont() {
                    return font;
                }

                public void setFont(String font) {
                    this.font = font;
                }

                public Object getAdTrack() {
                    return adTrack;
                }

                public void setAdTrack(Object adTrack) {
                    this.adTrack = adTrack;
                }
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Data getData() {
                return data;
            }

            public void setData(Data data) {
                this.data = data;
            }

            public Object getTrackingData() {
                return trackingData;
            }

            public void setTrackingData(Object trackingData) {
                this.trackingData = trackingData;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getAdIndex() {
                return adIndex;
            }

            public void setAdIndex(int adIndex) {
                this.adIndex = adIndex;
            }
        }

        private int count;
        private int total;
        private String nextPageUrl;
        private Boolean adExist;
        private Long date;
        private Long nextPublishTime;
        private String dialog;
        private String topIssue;
        private int refreshCount;
        private int lastStartId;

        public List<ItemList> getItemList() {
            return itemList;
        }

        public void setItemList(List<ItemList> itemList) {
            this.itemList = itemList;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getNextPageUrl() {
            return nextPageUrl;
        }

        public void setNextPageUrl(String nextPageUrl) {
            this.nextPageUrl = nextPageUrl;
        }

        public Boolean getAdExist() {
            return adExist;
        }

        public void setAdExist(Boolean adExist) {
            this.adExist = adExist;
        }

        public Long getDate() {
            return date;
        }

        public void setDate(Long date) {
            this.date = date;
        }

        public Long getNextPublishTime() {
            return nextPublishTime;
        }

        public void setNextPublishTime(Long nextPublishTime) {
            this.nextPublishTime = nextPublishTime;
        }

        public String getDialog() {
            return dialog;
        }

        public void setDialog(String dialog) {
            this.dialog = dialog;
        }

        public String getTopIssue() {
            return topIssue;
        }

        public void setTopIssue(String topIssue) {
            this.topIssue = topIssue;
        }

        public int getRefreshCount() {
            return refreshCount;
        }

        public void setRefreshCount(int refreshCount) {
            this.refreshCount = refreshCount;
        }

        public int getLastStartId() {
            return lastStartId;
        }

        public void setLastStartId(int lastStartId) {
            this.lastStartId = lastStartId;
        }
    }
}