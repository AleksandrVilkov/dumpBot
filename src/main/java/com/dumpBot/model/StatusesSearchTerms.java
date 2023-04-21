package com.dumpBot.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusesSearchTerms {
    boolean approved;
    boolean rejected;
    boolean topical;
}
