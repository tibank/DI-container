package com.epam.rd.spring2019.hwspring01.models;

import lombok.*;

import java.io.Serializable;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements Serializable {

    private static final long serialVersionUID = 4491330219140451379L;

    @NonNull @EqualsAndHashCode.Include public final Long id;
    @NonNull public final String name;
    @NonNull public final String email;
    @NonNull public final String password;
    @NonNull public final Address address;
}
