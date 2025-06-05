package org.sopt.post.domain.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    public static List<Tags> toList(List<String> befores) {
        if (befores == null) return List.of();
        return befores.stream()
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .map(Tags::to)
                .toList();
    }
}
