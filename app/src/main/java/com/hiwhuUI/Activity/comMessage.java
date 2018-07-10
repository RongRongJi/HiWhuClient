package com.hiwhuUI.Activity;

public class comMessage {
        private String name;
        private int imageId;
        public comMessage(String name,int imageId){
            this.name = name;
            this.imageId = imageId;
        }
        public  String getName(){
            return name;
        }
        public int getImageId(){
            return imageId;
        }
}
