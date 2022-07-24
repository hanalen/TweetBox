package com.tweetbox.progress.dao;

import com.tweetbox.progress.entities.Progress;

public interface ProgressDao {
    Progress saveProgress(Progress progress);

    Progress updateProgress(Progress progress);

    Progress findByUserId(Long userId);

    Progress findById(Long id);
}
