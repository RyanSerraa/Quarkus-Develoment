package org;

import org.acme.Follower;

public class FollowerResponse {
    private long id;
    private String name;
    public FollowerResponse(Follower follower) {
        this(follower.getId(), follower.getFollower().getName());
    }
    public FollowerResponse(long id, String name) {
        this.id = id;
        this.name = name;
    }
    public FollowerResponse() {
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    
}
