package com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT) // makes Spring send a response status, a CONFLICT meaning already created the resource
public class ResourceCreationException extends RuntimeException{ // extending RunTimeException because we don't want to listen to a regular exception
    public ResourceCreationException(String message) {
        super(message);
    }
}
