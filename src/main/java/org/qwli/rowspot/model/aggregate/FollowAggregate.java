package org.qwli.rowspot.model.aggregate;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liqiwen
 * @since 1.2
 * FollowAggregate 聚合根
 */
public class FollowAggregate implements Serializable {

    private Long followUserId;

    private String followUserAvatar;

    private String followUsername;

    private Long followUserPrestige = 0L;

    private Date createAt;

    public Long getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(Long followUserId) {
        this.followUserId = followUserId;
    }

    public String getFollowUserAvatar() {
        return followUserAvatar;
    }

    public void setFollowUserAvatar(String followUserAvatar) {
        this.followUserAvatar = followUserAvatar;
    }

    public String getFollowUsername() {
        return followUsername;
    }

    public void setFollowUsername(String followUsername) {
        this.followUsername = followUsername;
    }

    public Long getFollowUserPrestige() {
        return followUserPrestige;
    }

    public void setFollowUserPrestige(Long followUserPrestige) {
        this.followUserPrestige = followUserPrestige;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
