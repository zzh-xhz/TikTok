package com.lib.network.http.api;

import androidx.annotation.NonNull;

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
    private String userId;
    @NonNull
    @Override
    public String getApi() {
        return "users/{"+userId+"}/videos";
    }

    public TestNetworkApi setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public final static class Bean {

        /**
         * page
         */
        private Integer page;
        /**
         * per_page
         */
        private Integer per_page;
        /**
         * total_results
         */
        private Integer total_results;
        /**
         * url
         */
        private String url;
        /**
         * videos
         */
        private List<VideosDTO> videos;

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getPer_page() {
            return per_page;
        }

        public void setPer_page(Integer per_page) {
            this.per_page = per_page;
        }

        public Integer getTotal_results() {
            return total_results;
        }

        public void setTotal_results(Integer total_results) {
            this.total_results = total_results;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<VideosDTO> getVideos() {
            return videos;
        }

        public void setVideos(List<VideosDTO> videos) {
            this.videos = videos;
        }

        public static class VideosDTO {
            /**
             * id
             */
            private Integer id;
            /**
             * width
             */
            private Integer width;
            /**
             * height
             */
            private Integer height;
            /**
             * url
             */
            private String url;
            /**
             * image
             */
            private String image;
            /**
             * duration
             */
            private Integer duration;
            /**
             * user
             */
            private UserDTO user;
            /**
             * video_files
             */
            private List<VideoFilesDTO> video_files;
            /**
             * video_pictures
             */
            private List<VideoPicturesDTO> video_pictures;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public Integer getDuration() {
                return duration;
            }

            public void setDuration(Integer duration) {
                this.duration = duration;
            }

            public UserDTO getUser() {
                return user;
            }

            public void setUser(UserDTO user) {
                this.user = user;
            }

            public List<VideoFilesDTO> getVideo_files() {
                return video_files;
            }

            public void setVideo_files(List<VideoFilesDTO> video_files) {
                this.video_files = video_files;
            }

            public List<VideoPicturesDTO> getVideo_pictures() {
                return video_pictures;
            }

            public void setVideo_pictures(List<VideoPicturesDTO> video_pictures) {
                this.video_pictures = video_pictures;
            }

            public static class UserDTO {
                /**
                 * id
                 */
                private Integer id;
                /**
                 * name
                 */
                private String name;
                /**
                 * url
                 */
                private String url;

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class VideoFilesDTO {
                /**
                 * id
                 */
                private Integer id;
                /**
                 * quality
                 */
                private String quality;
                /**
                 * file_type
                 */
                private String file_type;
                /**
                 * width
                 */
                private Integer width;
                /**
                 * height
                 */
                private Integer height;
                /**
                 * link
                 */
                private String link;

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                }

                public String getQuality() {
                    return quality;
                }

                public void setQuality(String quality) {
                    this.quality = quality;
                }

                public String getFile_type() {
                    return file_type;
                }

                public void setFile_type(String file_type) {
                    this.file_type = file_type;
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

                public String getLink() {
                    return link;
                }

                public void setLink(String link) {
                    this.link = link;
                }
            }

            public static class VideoPicturesDTO {
                /**
                 * id
                 */
                private Integer id;
                /**
                 * picture
                 */
                private String picture;
                /**
                 * nr
                 */
                private Integer nr;

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                }

                public String getPicture() {
                    return picture;
                }

                public void setPicture(String picture) {
                    this.picture = picture;
                }

                public Integer getNr() {
                    return nr;
                }

                public void setNr(Integer nr) {
                    this.nr = nr;
                }
            }
        }
    }
}