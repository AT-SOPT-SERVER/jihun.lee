package org.sopt.post.domain.enums;

import java.util.Arrays;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sopt.post.exception.TagsNotFoundException;

@Getter
@AllArgsConstructor
public enum Tags {

    BACKEND("백엔드"),
    DATABASE("데이터베이스"),
    INFRA("인프라"),
    ETC("기타");

    private final String value;

    public static Tags to(String before) {
        return Arrays.stream(Tags.values())
                .filter(Tags -> Tags.getValue().equals(before))
                .findAny()
                .orElseThrow(TagsNotFoundException::new);
    }

    public static Optional<Tags> from(String before) {
        if (before == null) return Optional.empty();
        return Arrays.stream(Tags.values())
                .filter(tags -> tags.getValue().equals(before))
                .findAny();
    }
}
