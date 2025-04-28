package org.sopt.post.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.sopt.global.common.exception.BaseException;

public class TagsNotFoundException extends BaseException {

    public TagsNotFoundException() {
        super(NOT_FOUND, ErrorMessage.NOT_FOUND_TAG.getMessage());
    }

}
