package com.ilacad.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String title;
    private String author;
    private String ISBN;
}
