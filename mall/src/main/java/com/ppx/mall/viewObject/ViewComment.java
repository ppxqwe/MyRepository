package com.ppx.mall.viewObject;

import lombok.Data;

@Data
public class ViewComment {
    Integer type;
    Long id;
    Long time;
    String comment;
    String avatar="https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png";
    String userName;
    Integer thumb;
    String replaiedName="";
    Integer replayCount;
    Boolean isLiked=false;
}
