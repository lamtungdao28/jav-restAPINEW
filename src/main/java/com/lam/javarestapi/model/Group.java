package com.lam.javarestapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_group")
public class Group extends AbstractEntity<Integer> {
    private String name;
    private String description;
    @OneToOne
    private Role role;

    @OneToMany(mappedBy = "group")
    private Set<UserHasGroup> users = new HashSet<>();

}
