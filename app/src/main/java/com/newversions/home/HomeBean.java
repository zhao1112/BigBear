package com.newversions.home;

import java.util.List;

/**
 * Create By Master
 * On 2019/1/3 16:25
 */
public class HomeBean {

    private String msg;
    private int code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {


        private GroupPurchasingRecordBean groupPurchasingRecord;
        private int has_more;
        private BargainRecordBean bargainRecord;
        private List<SpecialPriceProductListBean> specialPriceProductList;
        private List<ProductListBean> productList;

        public GroupPurchasingRecordBean getGroupPurchasingRecord() {
            return groupPurchasingRecord;
        }

        public void setGroupPurchasingRecord(GroupPurchasingRecordBean groupPurchasingRecord) {
            this.groupPurchasingRecord = groupPurchasingRecord;
        }

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public BargainRecordBean getBargainRecord() {
            return bargainRecord;
        }

        public void setBargainRecord(BargainRecordBean bargainRecord) {
            this.bargainRecord = bargainRecord;
        }

        public List<SpecialPriceProductListBean> getSpecialPriceProductList() {
            return specialPriceProductList;
        }

        public void setSpecialPriceProductList(List<SpecialPriceProductListBean> specialPriceProductList) {
            this.specialPriceProductList = specialPriceProductList;
        }

        public List<ProductListBean> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListBean> productList) {
            this.productList = productList;
        }

        public static class GroupPurchasingRecordBean {
            private List<GroupPurchasingAdListBean> groupPurchasingAdList;
            private List<GroupPurchasingListBean> groupPurchasingList;

            public List<GroupPurchasingAdListBean> getGroupPurchasingAdList() {
                return groupPurchasingAdList;
            }

            public void setGroupPurchasingAdList(List<GroupPurchasingAdListBean> groupPurchasingAdList) {
                this.groupPurchasingAdList = groupPurchasingAdList;
            }

            public List<GroupPurchasingListBean> getGroupPurchasingList() {
                return groupPurchasingList;
            }

            public void setGroupPurchasingList(List<GroupPurchasingListBean> groupPurchasingList) {
                this.groupPurchasingList = groupPurchasingList;
            }

            public static class GroupPurchasingAdListBean {
                /**
                 * type : 0
                 * source_id : 11111
                 * img : asdddasddsa
                 * orders : 1
                 * skipType : 0
                 */

                private int type;
                private int source_id;
                private String img;
                private int orders;
                private int skipType;

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getSource_id() {
                    return source_id;
                }

                public void setSource_id(int source_id) {
                    this.source_id = source_id;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public int getOrders() {
                    return orders;
                }

                public void setOrders(int orders) {
                    this.orders = orders;
                }

                public int getSkipType() {
                    return skipType;
                }

                public void setSkipType(int skipType) {
                    this.skipType = skipType;
                }
            }

            public static class GroupPurchasingListBean {
                /**
                 * groupPurchasing_id : 1202
                 * cost : 1.00
                 * productVideoImage : null
                 * needCount : 1
                 * isCrossBorder : false
                 * isNew : false
                 * productCount : 12111
                 * totalCount : 5
                 * groupNumber : 1
                 * specifications : []
                 * productName : 小米电视4A
                 * productImages : {"source":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/a394c1ba-4549-41f0-99b4-6f4ca339116f_source.jpg","large":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/a394c1ba-4549-41f0-99b4-6f4ca339116f_large.jpg","medium":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/a394c1ba-4549-41f0-99b4-6f4ca339116f_medium.jpg","thumbnail":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/a394c1ba-4549-41f0-99b4-6f4ca339116f_thumbnail.jpg","order":0}
                 * createdDate : 2018-11-08 14:31:32
                 * price : 63331
                 * isSurportMsp : false
                 * product_id : 10901
                 * personalCount : 0
                 * productVideo : null
                 * orders : 1
                 */

                private String membershipPrice;

                public String getMembershipPrice() {
                    return membershipPrice;
                }

                public void setMembershipPrice(String membershipPrice) {
                    this.membershipPrice = membershipPrice;
                }

                private int groupPurchasing_id;
                private String cost;
                private Object productVideoImage;
                private int needCount;
                private boolean isCrossBorder;
                private boolean isNew;
                private int productCount;
                private int totalCount;
                private int groupNumber;
                private String productName;
                private ProductImagesBean productImages;
                private String createdDate;
                private String price;
                private boolean isSurportMsp;
                private int product_id;
                private int personalCount;
                private Object productVideo;
                private int orders;
                private List<?> specifications;

                public int getGroupPurchasing_id() {
                    return groupPurchasing_id;
                }

                public void setGroupPurchasing_id(int groupPurchasing_id) {
                    this.groupPurchasing_id = groupPurchasing_id;
                }

                public String getCost() {
                    return cost;
                }

                public void setCost(String cost) {
                    this.cost = cost;
                }

                public Object getProductVideoImage() {
                    return productVideoImage;
                }

                public void setProductVideoImage(Object productVideoImage) {
                    this.productVideoImage = productVideoImage;
                }

                public int getNeedCount() {
                    return needCount;
                }

                public void setNeedCount(int needCount) {
                    this.needCount = needCount;
                }

                public boolean isIsCrossBorder() {
                    return isCrossBorder;
                }

                public void setIsCrossBorder(boolean isCrossBorder) {
                    this.isCrossBorder = isCrossBorder;
                }

                public boolean isIsNew() {
                    return isNew;
                }

                public void setIsNew(boolean isNew) {
                    this.isNew = isNew;
                }

                public int getProductCount() {
                    return productCount;
                }

                public void setProductCount(int productCount) {
                    this.productCount = productCount;
                }

                public int getTotalCount() {
                    return totalCount;
                }

                public void setTotalCount(int totalCount) {
                    this.totalCount = totalCount;
                }

                public int getGroupNumber() {
                    return groupNumber;
                }

                public void setGroupNumber(int groupNumber) {
                    this.groupNumber = groupNumber;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public ProductImagesBean getProductImages() {
                    return productImages;
                }

                public void setProductImages(ProductImagesBean productImages) {
                    this.productImages = productImages;
                }

                public String getCreatedDate() {
                    return createdDate;
                }

                public void setCreatedDate(String createdDate) {
                    this.createdDate = createdDate;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public boolean isIsSurportMsp() {
                    return isSurportMsp;
                }

                public void setIsSurportMsp(boolean isSurportMsp) {
                    this.isSurportMsp = isSurportMsp;
                }

                public int getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(int product_id) {
                    this.product_id = product_id;
                }

                public int getPersonalCount() {
                    return personalCount;
                }

                public void setPersonalCount(int personalCount) {
                    this.personalCount = personalCount;
                }

                public Object getProductVideo() {
                    return productVideo;
                }

                public void setProductVideo(Object productVideo) {
                    this.productVideo = productVideo;
                }

                public int getOrders() {
                    return orders;
                }

                public void setOrders(int orders) {
                    this.orders = orders;
                }

                public List<?> getSpecifications() {
                    return specifications;
                }

                public void setSpecifications(List<?> specifications) {
                    this.specifications = specifications;
                }

                public static class ProductImagesBean {
                    /**
                     * source : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/a394c1ba-4549-41f0-99b4-6f4ca339116f_source.jpg
                     * large : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/a394c1ba-4549-41f0-99b4-6f4ca339116f_large.jpg
                     * medium : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/a394c1ba-4549-41f0-99b4-6f4ca339116f_medium.jpg
                     * thumbnail : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/a394c1ba-4549-41f0-99b4-6f4ca339116f_thumbnail.jpg
                     * order : 0
                     */

                    private String source;
                    private String large;
                    private String medium;
                    private String thumbnail;
                    private int order;

                    public String getSource() {
                        return source;
                    }

                    public void setSource(String source) {
                        this.source = source;
                    }

                    public String getLarge() {
                        return large;
                    }

                    public void setLarge(String large) {
                        this.large = large;
                    }

                    public String getMedium() {
                        return medium;
                    }

                    public void setMedium(String medium) {
                        this.medium = medium;
                    }

                    public String getThumbnail() {
                        return thumbnail;
                    }

                    public void setThumbnail(String thumbnail) {
                        this.thumbnail = thumbnail;
                    }

                    public int getOrder() {
                        return order;
                    }

                    public void setOrder(int order) {
                        this.order = order;
                    }
                }
            }
        }

        public static class BargainRecordBean {
            private List<BargainAdListBean> bargainAdList;
            private List<BargainListBean> bargainList;

            public List<BargainAdListBean> getBargainAdList() {
                return bargainAdList;
            }

            public void setBargainAdList(List<BargainAdListBean> bargainAdList) {
                this.bargainAdList = bargainAdList;
            }

            public List<BargainListBean> getBargainList() {
                return bargainList;
            }

            public void setBargainList(List<BargainListBean> bargainList) {
                this.bargainList = bargainList;
            }

            public static class BargainAdListBean {
                /**
                 * type : 0
                 * source_id : 11111
                 * img : asdddasddsa
                 * orders : 1
                 * skipType : 0
                 */

                private int type;
                private int source_id;
                private String img;
                private int orders;
                private int skipType;

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getSource_id() {
                    return source_id;
                }

                public void setSource_id(int source_id) {
                    this.source_id = source_id;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public int getOrders() {
                    return orders;
                }

                public void setOrders(int orders) {
                    this.orders = orders;
                }

                public int getSkipType() {
                    return skipType;
                }

                public void setSkipType(int skipType) {
                    this.skipType = skipType;
                }
            }

            public static class BargainListBean {
                /**
                 * productVideoImage : null
                 * restCount : 208
                 * isCrossBorder : false
                 * partBtAmount : 10.00
                 * isNew : false
                 * store : {"store_id":2,"logo":"http://image.demo.b2b2c.shopxx.net/6.0/3de84479-78db-4c1b-aadc-af90fac6c5bd.png","storeName":"办公家电旗舰店","type":0}
                 * bargainProduct_id : 1553
                 * productName : 苹果(Apple) MacBook Pro
                 * isSuperBargain : true
                 * productImages : {"source":"http://image.demo.b2b2c.shopxx.net/6.0/e95b153e-e8b1-49a9-856d-ad6858835586_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/e95b153e-e8b1-49a9-856d-ad6858835586_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/e95b153e-e8b1-49a9-856d-ad6858835586_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/e95b153e-e8b1-49a9-856d-ad6858835586_thumbnail.jpg","order":0}
                 * specificationItems : [{"name":"内存容量","entries":[{"id":1,"value":"4GB","isSelected":true},{"id":2,"value":"8GB","isSelected":false},{"id":3,"value":"16GB","isSelected":false}]},{"name":"硬盘容量","entries":[{"id":4,"value":"128GB","isSelected":true},{"id":5,"value":"256GB","isSelected":false},{"id":6,"value":"1TB","isSelected":true},{"id":7,"value":"2TB","isSelected":false}]}]
                 * createdDate : 2018-12-10 15:46:29
                 * isSurportMsp : true
                 * price : 11980.00
                 * product_id : 38
                 * personalCount : 21
                 * productVideo : null
                 * orders : 2
                 */

                private Object productVideoImage;
                private int restCount;
                private boolean isCrossBorder;
                private String partBtAmount;
                private boolean isNew;
                private StoreBean store;
                private int bargainProduct_id;
                private String productName;
                private boolean isSuperBargain;
                private ProductImagesBeanX productImages;
                private String createdDate;
                private boolean isSurportMsp;
                private String price;
                private int product_id;
                private int personalCount;
                private Object productVideo;
                private int orders;
                private List<SpecificationItemsBean> specificationItems;

                public Object getProductVideoImage() {
                    return productVideoImage;
                }

                public void setProductVideoImage(Object productVideoImage) {
                    this.productVideoImage = productVideoImage;
                }

                public int getRestCount() {
                    return restCount;
                }

                public void setRestCount(int restCount) {
                    this.restCount = restCount;
                }

                public boolean isIsCrossBorder() {
                    return isCrossBorder;
                }

                public void setIsCrossBorder(boolean isCrossBorder) {
                    this.isCrossBorder = isCrossBorder;
                }

                public String getPartBtAmount() {
                    return partBtAmount;
                }

                public void setPartBtAmount(String partBtAmount) {
                    this.partBtAmount = partBtAmount;
                }

                public boolean isIsNew() {
                    return isNew;
                }

                public void setIsNew(boolean isNew) {
                    this.isNew = isNew;
                }

                public StoreBean getStore() {
                    return store;
                }

                public void setStore(StoreBean store) {
                    this.store = store;
                }

                public int getBargainProduct_id() {
                    return bargainProduct_id;
                }

                public void setBargainProduct_id(int bargainProduct_id) {
                    this.bargainProduct_id = bargainProduct_id;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public boolean isIsSuperBargain() {
                    return isSuperBargain;
                }

                public void setIsSuperBargain(boolean isSuperBargain) {
                    this.isSuperBargain = isSuperBargain;
                }

                public ProductImagesBeanX getProductImages() {
                    return productImages;
                }

                public void setProductImages(ProductImagesBeanX productImages) {
                    this.productImages = productImages;
                }

                public String getCreatedDate() {
                    return createdDate;
                }

                public void setCreatedDate(String createdDate) {
                    this.createdDate = createdDate;
                }

                public boolean isIsSurportMsp() {
                    return isSurportMsp;
                }

                public void setIsSurportMsp(boolean isSurportMsp) {
                    this.isSurportMsp = isSurportMsp;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public int getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(int product_id) {
                    this.product_id = product_id;
                }

                public int getPersonalCount() {
                    return personalCount;
                }

                public void setPersonalCount(int personalCount) {
                    this.personalCount = personalCount;
                }

                public Object getProductVideo() {
                    return productVideo;
                }

                public void setProductVideo(Object productVideo) {
                    this.productVideo = productVideo;
                }

                public int getOrders() {
                    return orders;
                }

                public void setOrders(int orders) {
                    this.orders = orders;
                }

                public List<SpecificationItemsBean> getSpecificationItems() {
                    return specificationItems;
                }

                public void setSpecificationItems(List<SpecificationItemsBean> specificationItems) {
                    this.specificationItems = specificationItems;
                }

                public static class StoreBean {
                    /**
                     * store_id : 2
                     * logo : http://image.demo.b2b2c.shopxx.net/6.0/3de84479-78db-4c1b-aadc-af90fac6c5bd.png
                     * storeName : 办公家电旗舰店
                     * type : 0
                     */

                    private int store_id;
                    private String logo;
                    private String storeName;
                    private int type;

                    public int getStore_id() {
                        return store_id;
                    }

                    public void setStore_id(int store_id) {
                        this.store_id = store_id;
                    }

                    public String getLogo() {
                        return logo;
                    }

                    public void setLogo(String logo) {
                        this.logo = logo;
                    }

                    public String getStoreName() {
                        return storeName;
                    }

                    public void setStoreName(String storeName) {
                        this.storeName = storeName;
                    }

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }
                }

                public static class ProductImagesBeanX {
                    /**
                     * source : http://image.demo.b2b2c.shopxx.net/6.0/e95b153e-e8b1-49a9-856d-ad6858835586_source.jpg
                     * large : http://image.demo.b2b2c.shopxx.net/6.0/e95b153e-e8b1-49a9-856d-ad6858835586_large.jpg
                     * medium : http://image.demo.b2b2c.shopxx.net/6.0/e95b153e-e8b1-49a9-856d-ad6858835586_medium.jpg
                     * thumbnail : http://image.demo.b2b2c.shopxx.net/6.0/e95b153e-e8b1-49a9-856d-ad6858835586_thumbnail.jpg
                     * order : 0
                     */

                    private String source;
                    private String large;
                    private String medium;
                    private String thumbnail;
                    private int order;

                    public String getSource() {
                        return source;
                    }

                    public void setSource(String source) {
                        this.source = source;
                    }

                    public String getLarge() {
                        return large;
                    }

                    public void setLarge(String large) {
                        this.large = large;
                    }

                    public String getMedium() {
                        return medium;
                    }

                    public void setMedium(String medium) {
                        this.medium = medium;
                    }

                    public String getThumbnail() {
                        return thumbnail;
                    }

                    public void setThumbnail(String thumbnail) {
                        this.thumbnail = thumbnail;
                    }

                    public int getOrder() {
                        return order;
                    }

                    public void setOrder(int order) {
                        this.order = order;
                    }
                }

                public static class SpecificationItemsBean {
                    /**
                     * name : 内存容量
                     * entries : [{"id":1,"value":"4GB","isSelected":true},{"id":2,"value":"8GB","isSelected":false},{"id":3,"value":"16GB","isSelected":false}]
                     */

                    private String name;
                    private List<EntriesBean> entries;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public List<EntriesBean> getEntries() {
                        return entries;
                    }

                    public void setEntries(List<EntriesBean> entries) {
                        this.entries = entries;
                    }

                    public static class EntriesBean {
                        /**
                         * id : 1
                         * value : 4GB
                         * isSelected : true
                         */

                        private int id;
                        private String value;
                        private boolean isSelected;

                        public int getId() {
                            return id;
                        }

                        public void setId(int id) {
                            this.id = id;
                        }

                        public String getValue() {
                            return value;
                        }

                        public void setValue(String value) {
                            this.value = value;
                        }

                        public boolean isIsSelected() {
                            return isSelected;
                        }

                        public void setIsSelected(boolean isSelected) {
                            this.isSelected = isSelected;
                        }
                    }
                }
            }
        }

        public static class SpecialPriceProductListBean {
            /**
             * isNew : false
             * isDiscount : 1
             * productName : cs商品
             * sales : 0
             * sourcePrice : 998.00
             * productImages : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201901/17ed75a8-9307-4f7b-96e3-852b9a66b205.jpg
             * isSurportMsp : true
             * price : 798.00
             * product_id : 12551
             * model : 0
             * membershipPrice : 600.00
             * sourceMembershipPrice : 23.00
             */

            private boolean isNew;
            private int isDiscount;
            private String productName;
            private int sales;
            private String sourcePrice;
            private String productImages;
            private boolean isSurportMsp;
            private String price;
            private int product_id;
            private int model;
            private String membershipPrice;
            private String sourceMembershipPrice;

            public boolean isIsNew() {
                return isNew;
            }

            public void setIsNew(boolean isNew) {
                this.isNew = isNew;
            }

            public int getIsDiscount() {
                return isDiscount;
            }

            public void setIsDiscount(int isDiscount) {
                this.isDiscount = isDiscount;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public int getSales() {
                return sales;
            }

            public void setSales(int sales) {
                this.sales = sales;
            }

            public String getSourcePrice() {
                return sourcePrice;
            }

            public void setSourcePrice(String sourcePrice) {
                this.sourcePrice = sourcePrice;
            }

            public String getProductImages() {
                return productImages;
            }

            public void setProductImages(String productImages) {
                this.productImages = productImages;
            }

            public boolean isIsSurportMsp() {
                return isSurportMsp;
            }

            public void setIsSurportMsp(boolean isSurportMsp) {
                this.isSurportMsp = isSurportMsp;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getModel() {
                return model;
            }

            public void setModel(int model) {
                this.model = model;
            }

            public String getMembershipPrice() {
                return membershipPrice;
            }

            public void setMembershipPrice(String membershipPrice) {
                this.membershipPrice = membershipPrice;
            }

            public String getSourceMembershipPrice() {
                return sourceMembershipPrice;
            }

            public void setSourceMembershipPrice(String sourceMembershipPrice) {
                this.sourceMembershipPrice = sourceMembershipPrice;
            }
        }

        public static class ProductListBean {
            /**
             * productVideoImage : null
             * attributeValue3 : null
             * isCrossBorder : false
             * attributeValue4 : null
             * attributeValue1 : null
             * isNew : false
             * attributeValue2 : null
             * isDiscount : 0
             * attributeValue0 : null
             * productName : 额热热热
             * productImages : {"source":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201812/ea970b23-a74e-4b58-82c0-ed71c89191ff_source.jpg","large":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201812/ea970b23-a74e-4b58-82c0-ed71c89191ff_large.jpg","medium":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201812/ea970b23-a74e-4b58-82c0-ed71c89191ff_medium.jpg","thumbnail":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201812/ea970b23-a74e-4b58-82c0-ed71c89191ff_thumbnail.jpg","order":0}
             * createdDate : 2018-12-25 16:11:24
             * isSurportMsp : true
             * price : 555.00
             * product_id : 12451
             * productVideo : null
             * model : null
             * membershipPrice : 553.00
             * sourcePrice : 998.00
             * sourceMembershipPrice : 23.00
             */

            private Object productVideoImage;
            private Object attributeValue3;
            private boolean isCrossBorder;
            private Object attributeValue4;
            private Object attributeValue1;
            private boolean isNew;
            private Object attributeValue2;
            private int isDiscount;
            private Object attributeValue0;
            private String productName;
            private ProductImagesBeanXX productImages;
            private String createdDate;
            private boolean isSurportMsp;
            private String price;
            private int product_id;
            private Object productVideo;
            private int model;
            private String membershipPrice;
            private String sourcePrice;
            private String sourceMembershipPrice;

            public Object getProductVideoImage() {
                return productVideoImage;
            }

            public void setProductVideoImage(Object productVideoImage) {
                this.productVideoImage = productVideoImage;
            }

            public Object getAttributeValue3() {
                return attributeValue3;
            }

            public void setAttributeValue3(Object attributeValue3) {
                this.attributeValue3 = attributeValue3;
            }

            public boolean isIsCrossBorder() {
                return isCrossBorder;
            }

            public void setIsCrossBorder(boolean isCrossBorder) {
                this.isCrossBorder = isCrossBorder;
            }

            public Object getAttributeValue4() {
                return attributeValue4;
            }

            public void setAttributeValue4(Object attributeValue4) {
                this.attributeValue4 = attributeValue4;
            }

            public Object getAttributeValue1() {
                return attributeValue1;
            }

            public void setAttributeValue1(Object attributeValue1) {
                this.attributeValue1 = attributeValue1;
            }

            public boolean isIsNew() {
                return isNew;
            }

            public void setIsNew(boolean isNew) {
                this.isNew = isNew;
            }

            public Object getAttributeValue2() {
                return attributeValue2;
            }

            public void setAttributeValue2(Object attributeValue2) {
                this.attributeValue2 = attributeValue2;
            }

            public int getIsDiscount() {
                return isDiscount;
            }

            public void setIsDiscount(int isDiscount) {
                this.isDiscount = isDiscount;
            }

            public Object getAttributeValue0() {
                return attributeValue0;
            }

            public void setAttributeValue0(Object attributeValue0) {
                this.attributeValue0 = attributeValue0;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public ProductImagesBeanXX getProductImages() {
                return productImages;
            }

            public void setProductImages(ProductImagesBeanXX productImages) {
                this.productImages = productImages;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public boolean isIsSurportMsp() {
                return isSurportMsp;
            }

            public void setIsSurportMsp(boolean isSurportMsp) {
                this.isSurportMsp = isSurportMsp;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public Object getProductVideo() {
                return productVideo;
            }

            public void setProductVideo(Object productVideo) {
                this.productVideo = productVideo;
            }

            public int getModel() {
                return model;
            }

            public void setModel(int model) {
                this.model = model;
            }

            public String getMembershipPrice() {
                return membershipPrice;
            }

            public void setMembershipPrice(String membershipPrice) {
                this.membershipPrice = membershipPrice;
            }

            public String getSourcePrice() {
                return sourcePrice;
            }

            public void setSourcePrice(String sourcePrice) {
                this.sourcePrice = sourcePrice;
            }

            public String getSourceMembershipPrice() {
                return sourceMembershipPrice;
            }

            public void setSourceMembershipPrice(String sourceMembershipPrice) {
                this.sourceMembershipPrice = sourceMembershipPrice;
            }

            public static class ProductImagesBeanXX {
                /**
                 * source : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201812/ea970b23-a74e-4b58-82c0-ed71c89191ff_source.jpg
                 * large : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201812/ea970b23-a74e-4b58-82c0-ed71c89191ff_large.jpg
                 * medium : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201812/ea970b23-a74e-4b58-82c0-ed71c89191ff_medium.jpg
                 * thumbnail : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201812/ea970b23-a74e-4b58-82c0-ed71c89191ff_thumbnail.jpg
                 * order : 0
                 */

                private String source;
                private String large;
                private String medium;
                private String thumbnail;
                private int order;

                public String getSource() {
                    return source;
                }

                public void setSource(String source) {
                    this.source = source;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }

                public String getThumbnail() {
                    return thumbnail;
                }

                public void setThumbnail(String thumbnail) {
                    this.thumbnail = thumbnail;
                }

                public int getOrder() {
                    return order;
                }

                public void setOrder(int order) {
                    this.order = order;
                }
            }
        }
    }
}
