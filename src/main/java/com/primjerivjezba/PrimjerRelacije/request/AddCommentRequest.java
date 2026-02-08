package com.primjerivjezba.PrimjerRelacije.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCommentRequest {

    private  Long id;

    private String content;
}
