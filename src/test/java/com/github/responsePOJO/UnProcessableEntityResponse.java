package com.github.responsePOJO;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public @Data class UnProcessableEntityResponse {
	public String message;
    public ArrayList<RepoError> errors;
    public String documentation_url;
    public String status;
}




	    



