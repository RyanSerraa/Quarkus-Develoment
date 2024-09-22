package org;

import java.util.List;

public class FollowerPerUserResponse {
    private Integer followerCount;
    private List<FollowerResponse> content;
    public FollowerPerUserResponse(Integer followerCount, List<FollowerResponse> content) {
        this.followerCount = followerCount;
        this.content = content;
    }
    public Integer getFollowerCount() {
        return followerCount;
    }
    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }
    public List<FollowerResponse> getContent() {
        return content;
    }
    public void setContent(List<FollowerResponse> content) {
        this.content = content;
    }
    
}
