package com.tweetbox.progress.dao;

import com.tweetbox.progress.entities.Progress;
import com.tweetbox.progress.repositories.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProgressDaoImpl implements ProgressDao {
    private final ProgressRepository progressRepository;

    @Autowired
    ProgressDaoImpl(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @Override
    public Progress saveProgress(Progress progress) {
        this.progressRepository.save(progress);
        return progress;
    }

    @Override
    public Progress findByUserId(Long userId) {
        return this.progressRepository.findByUserId(userId);
    }
}
