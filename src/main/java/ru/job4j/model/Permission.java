package ru.job4j.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

@Builder(builderMethodName = "of")
@ToString
@Getter
public class Permission {
    private int id;
    private String name;
    @Singular("rules")
    private List<String> rules;


    public static void main(String[] args) {
        var permission = Permission.of()
                .id(1)
                .name("ADMIN")
                .rules("add")
                .rules("update")
                .rules("red")
                .rules("delete");
        System.out.println(permission);
    }
}
