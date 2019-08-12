package com.epam.rd.spring2019.hwspring01.models;

import lombok.*;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class City {
    @NonNull @EqualsAndHashCode.Include public final Long id;
    @NonNull public final String name;
    @NonNull public final Country country;
}
