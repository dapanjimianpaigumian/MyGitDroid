package com.yulu.zhaoxinpeng.mygitdroid.content.repositories.model;

import android.support.annotation.NonNull;

import com.yulu.zhaoxinpeng.mygitdroid.content.favorite.model.LocalRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/14.
 * 将Repo转换为LocalRepo
 */

public class RepoConvert {

    /**
     "id": 22374063,
     "name": "android-best-practices",
     "full_name": "futurice/android-best-practices",
     "avatar_url": "https://avatars.githubusercontent.com/u/852157?v=3",
     "description": "Do's and Don'ts for Android development, by Futurice developers",
     "stargazers_count": 10469,
     "forks_count": 1974
     */

    /**
     * 仓库转换为本地仓库
     * @param repo
     * @return
     */
    public static @NonNull
    LocalRepo convert(@NonNull Repo repo){
        LocalRepo localRepo = new LocalRepo();
        localRepo.setId(repo.getId());
        localRepo.setName(repo.getName());
        localRepo.setFullName(repo.getFullName());
        localRepo.setAvatarUrl(repo.getOwner().getAvatar());
        localRepo.setDescription(repo.getDescription());
        localRepo.setStargazersCount(repo.getStarCount());
        localRepo.setForksCount(repo.getForksCount());
        localRepo.setGroup(null);

        return localRepo;
    }

    //将仓库集合转换为本地仓库集合
    public static @NonNull
    List<LocalRepo> convertAll(@NonNull List<Repo> repos){
        List<LocalRepo> localRepos=new ArrayList<>();
        for (Repo repo :
                repos) {
            localRepos.add(convert(repo));
        }

        return localRepos;
    }
}
