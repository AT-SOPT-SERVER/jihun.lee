package org.sopt.global.common.cache.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.sopt.global.common.exception.BaseException;
import org.sopt.post.exception.ErrorMessage;

public class CacheNotFoundException extends BaseException {
  public CacheNotFoundException() {
    super(NOT_FOUND, ErrorMessage.DUPLICATED_TITLE.getMessage());
  }
}
