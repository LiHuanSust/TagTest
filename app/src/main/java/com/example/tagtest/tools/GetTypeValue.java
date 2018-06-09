package com.example.tagtest.tools;

/**
 * Created by MyFirstPC on 2018/6/8.
 * 该类的作用是根据TypeInfor表中的大类信息，获得该大类的名称，及图片id
 */

public class GetTypeValue {
      private int imageId;
      private String name;
      public GetTypeValue()
      {}
      public GetTypeValue(final int imageId,final String name)
      {
          this.imageId=imageId;
          this.name=name;
      }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
