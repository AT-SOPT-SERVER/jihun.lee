package org.sopt.post.domain.enums;

import java.util.Arrays;
import org.sopt.post.exception.TagsNotFoundException;

public enum Tags {

    BACKEND("백엔드"),
    DATABASE("데이터베이스"),
    INFRA("인프라");

    private final String value;

    Tags(String value) {
        this.value = value;
    }

    public static Tags to(String before) {
        return Arrays.stream(Tags.values())
                .filter(Tags -> Tags.getValue().equals(before))
                .findAny()
                .orElseThrow(TagsNotFoundException::new);
    }

    public String getValue() {
        return value;
    }

}
