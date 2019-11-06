package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.bean
 * @DATE 2019/11/6
 */
public class ContenGoods {

    /**
     * api : mtop.taobao.detail.getdesc
     * v : 6.0
     * ret : ["SUCCESS::调用成功"]
     * data : {"sellerId":"3441344877","pcDescContent":"<p style=\"text-align:center;\"><img src=\"//img.alicdn
     * .com/imgextra/i1/3441344877/O1CN01vEK0Mk1lti7mhkGLk_!!3441344877.jpg\" align=\"absmiddle\" size=\"790x284\"><img src=\"//img
     * .alicdn.com/imgextra/i1/3441344877/O1CN01i8ZvVl1lti7lDK7Vw_!!3441344877.jpg\" align=\"absmiddle\" size=\"790x788\"><img
     * src=\"//img.alicdn.com/imgextra/i1/3441344877/O1CN01EIzmuN1lti7lDLvnn_!!3441344877.jpg\" align=\"absmiddle\"
     * size=\"790x1095\"><img src=\"//img.alicdn.com/imgextra/i3/3441344877/O1CN01BnFFIe1lti7k3esyW_!!3441344877.jpg\"
     * align=\"absmiddle\" size=\"790x860\"><img src=\"//img.alicdn.com/imgextra/i4/3441344877/O1CN01sPaCg21lti7lTy7A8_!!3441344877.jpg\"
     * align=\"absmiddle\" size=\"790x860\"><img src=\"//img.alicdn.com/imgextra/i4/3441344877/O1CN01umFYo61lti7kPRpks_!!3441344877.jpg\"
     * align=\"absmiddle\" size=\"790x860\"><img src=\"//img.alicdn.com/imgextra/i1/3441344877/O1CN01SHWLu81lti7o9zOHE_!!3441344877.jpg\"
     * align=\"absmiddle\" size=\"790x860\"><img src=\"//img.alicdn.com/imgextra/i3/3441344877/O1CN01PZUD2n1lti7jHyPwi_!!3441344877.jpg\"
     * align=\"absmiddle\" size=\"790x860\"><img src=\"//img.alicdn.com/imgextra/i4/3441344877/O1CN017l3o3p1lti7mgKz2I_!!3441344877.jpg\"
     * align=\"absmiddle\" size=\"790x860\"><img src=\"//img.alicdn.com/imgextra/i2/3441344877/O1CN01MSRryl1lti7mnfxLw_!!3441344877.jpg\"
     * align=\"absmiddle\" size=\"790x860\"><img src=\"//img.alicdn.com/imgextra/i4/3441344877/O1CN0193IzJl1lti7oT1DFt_!!3441344877.jpg\"
     * align=\"absmiddle\" size=\"790x860\"><img src=\"//img.alicdn.com/imgextra/i1/3441344877/O1CN01FVbNIi1lti7mhj3ZT_!!3441344877.jpg\"
     * align=\"absmiddle\" size=\"790x860\"><img src=\"//img.alicdn.com/imgextra/i1/3441344877/O1CN01c4aKKe1lti7lDMOvw_!!3441344877.jpg\"
     * align=\"absmiddle\" size=\"790x860\"><img src=\"//img.alicdn.com/imgextra/i3/3441344877/O1CN01WFsIEf1lti7kPRtxM_!!3441344877.jpg\"
     * align=\"absmiddle\" size=\"790x860\" /><\/p>","itemProperties":[{"name":"品牌","value":"Aigo/爱国者 "},{"name":"型号",
     * "value":"vivoY系列亚克力镜面 "},{"name":"保护套质地","value":"亚克力 "},{"name":"风格","value":"简约 "},{"name":"适用手机机型","value":"vivo "},{"name
     * ":"颜色分类","value":"vivo v11i/z3i/z3 -【亚克力镜面】 vivo y97 -【亚克力镜面】 vivo y95 -【亚克力镜面】 vivo y93/y93s -【亚克力镜面】 vivo y85/z1/z1i -【亚克力镜面】
     * vivo y83/Y83a -【亚克力镜面】 vivo y79/y79a -【亚克力镜面】 vivo y75/y75a -【亚克力镜面】 vivo y71/y71a -【亚克力镜面】 vivo y67/y67L -【亚克力镜面】 vivo y66/y66L
     * -【亚克力镜面】 vivo y55 -【亚克力镜面】 vivo y51 -【亚克力镜面】 "},{"name":"生产企业","value":"深圳市汇世杰网络科技有限公司 "},{"name":"款式","value":"保护壳 "}],"anchors":[]}
     */

    private String api;
    private String v;
    private DataBean data;
    private List<String> ret;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public List<String> getRet() {
        return ret;
    }

    public void setRet(List<String> ret) {
        this.ret = ret;
    }

    public static class DataBean {
        /**
         * sellerId : 3441344877
         * pcDescContent : <p style="text-align:center;"><img src="//img.alicdn
         * .com/imgextra/i1/3441344877/O1CN01vEK0Mk1lti7mhkGLk_!!3441344877.jpg" align="absmiddle" size="790x284"><img src="//img.alicdn
         * .com/imgextra/i1/3441344877/O1CN01i8ZvVl1lti7lDK7Vw_!!3441344877.jpg" align="absmiddle" size="790x788"><img src="//img.alicdn
         * .com/imgextra/i1/3441344877/O1CN01EIzmuN1lti7lDLvnn_!!3441344877.jpg" align="absmiddle" size="790x1095"><img src="//img.alicdn
         * .com/imgextra/i3/3441344877/O1CN01BnFFIe1lti7k3esyW_!!3441344877.jpg" align="absmiddle" size="790x860"><img src="//img.alicdn
         * .com/imgextra/i4/3441344877/O1CN01sPaCg21lti7lTy7A8_!!3441344877.jpg" align="absmiddle" size="790x860"><img src="//img.alicdn
         * .com/imgextra/i4/3441344877/O1CN01umFYo61lti7kPRpks_!!3441344877.jpg" align="absmiddle" size="790x860"><img src="//img.alicdn
         * .com/imgextra/i1/3441344877/O1CN01SHWLu81lti7o9zOHE_!!3441344877.jpg" align="absmiddle" size="790x860"><img src="//img.alicdn
         * .com/imgextra/i3/3441344877/O1CN01PZUD2n1lti7jHyPwi_!!3441344877.jpg" align="absmiddle" size="790x860"><img src="//img.alicdn
         * .com/imgextra/i4/3441344877/O1CN017l3o3p1lti7mgKz2I_!!3441344877.jpg" align="absmiddle" size="790x860"><img src="//img.alicdn
         * .com/imgextra/i2/3441344877/O1CN01MSRryl1lti7mnfxLw_!!3441344877.jpg" align="absmiddle" size="790x860"><img src="//img.alicdn
         * .com/imgextra/i4/3441344877/O1CN0193IzJl1lti7oT1DFt_!!3441344877.jpg" align="absmiddle" size="790x860"><img src="//img.alicdn
         * .com/imgextra/i1/3441344877/O1CN01FVbNIi1lti7mhj3ZT_!!3441344877.jpg" align="absmiddle" size="790x860"><img src="//img.alicdn
         * .com/imgextra/i1/3441344877/O1CN01c4aKKe1lti7lDMOvw_!!3441344877.jpg" align="absmiddle" size="790x860"><img src="//img.alicdn
         * .com/imgextra/i3/3441344877/O1CN01WFsIEf1lti7kPRtxM_!!3441344877.jpg" align="absmiddle" size="790x860" /></p>
         * itemProperties : [{"name":"品牌","value":"Aigo/爱国者 "},{"name":"型号","value":"vivoY系列亚克力镜面 "},{"name":"保护套质地","value":"亚克力 "},{
         * "name":"风格","value":"简约 "},{"name":"适用手机机型","value":"vivo "},{"name":"颜色分类","value":"vivo v11i/z3i/z3 -【亚克力镜面】 vivo y97
         * -【亚克力镜面】 vivo y95 -【亚克力镜面】 vivo y93/y93s -【亚克力镜面】 vivo y85/z1/z1i -【亚克力镜面】 vivo y83/Y83a -【亚克力镜面】 vivo y79/y79a -【亚克力镜面】 vivo
         * y75/y75a -【亚克力镜面】 vivo y71/y71a -【亚克力镜面】 vivo y67/y67L -【亚克力镜面】 vivo y66/y66L -【亚克力镜面】 vivo y55 -【亚克力镜面】 vivo y51 -【亚克力镜面】 "},
         * {"name":"生产企业","value":"深圳市汇世杰网络科技有限公司 "},{"name":"款式","value":"保护壳 "}]
         * anchors : []
         */

        private String sellerId;
        private String pcDescContent;
        private List<ItemPropertiesBean> itemProperties;
        private List<?> anchors;

        public String getSellerId() {
            return sellerId;
        }

        public void setSellerId(String sellerId) {
            this.sellerId = sellerId;
        }

        public String getPcDescContent() {
            return pcDescContent;
        }

        public void setPcDescContent(String pcDescContent) {
            this.pcDescContent = pcDescContent;
        }

        public List<ItemPropertiesBean> getItemProperties() {
            return itemProperties;
        }

        public void setItemProperties(List<ItemPropertiesBean> itemProperties) {
            this.itemProperties = itemProperties;
        }

        public List<?> getAnchors() {
            return anchors;
        }

        public void setAnchors(List<?> anchors) {
            this.anchors = anchors;
        }

        public static class ItemPropertiesBean {
            /**
             * name : 品牌
             * value : Aigo/爱国者
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
