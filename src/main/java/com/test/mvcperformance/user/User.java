package com.test.mvcperformance.user;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    String id;
    String name;
    String hobby;
}

