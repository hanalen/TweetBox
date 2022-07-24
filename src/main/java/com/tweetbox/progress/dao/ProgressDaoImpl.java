package com.tweetbox.progress.dao;

import com.tweetbox.progress.entities.Progress;
import com.tweetbox.progress.repositories.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ProgressDaoImpl implements ProgressDao {
    private final ProgressRepository progressRepository;

    @Autowired
    ProgressDaoImpl(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @Override
    public Progress saveProgress(Progress progress) {
        try {
            return this.progressRepository.save(progress);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Progress updateProgress(Progress progress) {
        return this.progressRepository.save(progress);
    }

    @Override
    public Progress findByUserId(Long userId) {
        return this.progressRepository.findByUserId(userId);
    }
}
