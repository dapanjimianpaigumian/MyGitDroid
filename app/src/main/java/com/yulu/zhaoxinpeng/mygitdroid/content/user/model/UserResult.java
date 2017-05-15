package com.yulu.zhaoxinpeng.mygitdroid.content.user.model;

import com.google.gson.annotations.SerializedName;
import com.yulu.zhaoxinpeng.mygitdroid.login.model.User;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class UserResult {
    /**
     * total_count : 12
     * incomplete_results : false
     * items : [{"login":"mojombo","id":1,"avatar_url":"https://secure.gravatar.com/avatar/25c7c18223fb42a4c6ae1c8db6f50f9b?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png","type":"User","score":105.47857}]
     */

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private List<User> users;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

