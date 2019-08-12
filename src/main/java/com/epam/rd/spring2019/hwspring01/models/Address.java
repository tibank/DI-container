package com.epam.rd.spring2019.hwspring01.models;

import lombok.*;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Address {
    @NonNull @EqualsAndHashCode.Include public final Long id;
    @NonNull public final City city;
    @NonNull public final String street;
    @NonNull public final String numberHouse;
}
