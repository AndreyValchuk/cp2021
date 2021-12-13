package com.siard.movielibrary.dal.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @ManyToOne(fetch = EAGER)
    private User reviewer;

    @ManyToOne
    private Movie movie;

    private String text;
    private Short score;
}
