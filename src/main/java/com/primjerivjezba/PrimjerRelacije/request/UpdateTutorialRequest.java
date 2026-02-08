package com.primjerivjezba.PrimjerRelacije.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTutorialRequest {

    private Long id;

    private  String title;

    private String description;
}
