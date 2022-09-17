package com.tweetbox.reader.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record RequestReadArchive(@JsonProperty("userId") Long userId,
                              @JsonProperty("fileName") String fileName,
                              @JsonProperty("progressId") Long progressId)
        implements Serializable {
}