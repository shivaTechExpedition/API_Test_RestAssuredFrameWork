package com.github.responsePOJO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public @Data class GetNonExisitingRepoResponse {
	
		@JsonProperty(value="message")
	    public String message;
		
		@JsonProperty(value="documentation_url")
	    public String documentation_url;
		
		@JsonProperty(value="status")
	    public String status;
	
}
