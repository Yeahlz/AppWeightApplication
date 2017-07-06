package cn.eoe.app.entity;

/**
 * Created by Administrator on 2017/5/9.
 */

public class Databean {        //根据JSON解析内容写相应的容器
        private  String text;
        private  String code;
        private  String temperature;
        private  String feels_like;
        private  String wind_scale;
        public  String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public   String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public  String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public  String getFeels_like() {
            return feels_like;
        }

        public void setFeels_like(String feels_like) {
            this.feels_like = feels_like;
        }

        public  String getWind_scale() {
            return wind_scale;
        }
        public void setWind_scale(String wind_scale) {
            this.wind_scale = wind_scale;
        }
}
